package ite.jp.ak.lab03.client.web.facades;

import ite.jp.ak.lab03.client.dto.*;
import ite.jp.ak.lab03.client.enums.AssignmentStatus;
import ite.jp.ak.lab03.client.enums.SubmissionStatus;
import ite.jp.ak.lab03.client.exceptions.AssignmentAlreadyExistException;
import ite.jp.ak.lab03.client.exceptions.FeedbackAlreadyExistException;
import ite.jp.ak.lab03.client.exceptions.NoFeedbackDescriptionException;
import ite.jp.ak.lab03.client.exceptions.NoReportForSubmissionException;
import ite.jp.ak.lab03.client.web.requests.*;

public class ManagerRequestsFacade {
    public static void createFeedbackForSubmission(SubmissionDto submission, ManagerDto manager, String feedbackDescription) throws FeedbackAlreadyExistException, NoFeedbackDescriptionException, NoReportForSubmissionException {
        submission = SubmissionApiRequests.getSubmissionById(submission);
        FeedbackDto tempFeedback = new FeedbackDto();
        tempFeedback.setSubmission(submission);
        FeedbackDto feedback = FeedbackApiRequests.getFeedbackBySubmissionId(tempFeedback);

        if (feedback != null) {
            throw new FeedbackAlreadyExistException("Komentarz został już dodany, zgłoszenie zostało już zakończone");
        }

        if (feedbackDescription == null || feedbackDescription.isEmpty()) {
            throw new NoFeedbackDescriptionException("Proszę napisać treść komentarza");
        }

        // Sprawdzenie czy istnieje raport dla tego zgłoszenia
        ReportDto tempReport = new ReportDto();
        tempReport.setSubmission(submission);
        ReportDto report = ReportApiRequests.getReportBySubmissionId(tempReport);
        if (report == null) {
            throw new NoReportForSubmissionException("Zgłoszenie nie zostało jeszcze skontrolowane");
        }

        // utworzenie feedbacku
        FeedbackDto newFeedback = new FeedbackDto();
        newFeedback.setSubmission(submission);
        newFeedback.setDescription(feedbackDescription);
        newFeedback.setManager(manager);
        FeedbackApiRequests.createNewFeedback(newFeedback);

        // zmiana statusu zgłoszenia
        submission.setStatus(SubmissionStatus.Completed);
        SubmissionApiRequests.updateSubmission(submission);
    }

    public static ControllerDto createNewAssignment(SubmissionDto submission, String controllerUsername) throws AssignmentAlreadyExistException {
        submission = SubmissionApiRequests.getSubmissionById(submission);

        ControllerDto tempController = new ControllerDto();
        tempController.setUsername(controllerUsername);
        ControllerDto controller = ControllerApiRequests.getControllerByUsername(tempController);
        AssignmentDto assignment = new AssignmentDto();
        assignment.setController(controller);
        assignment.setSubmission(submission);

        // Sprawdzenie czy zgłoszenie nie zostało już przydzielone
        AssignmentDto tempAssignment = AssignmentApiRequests.getAssignmentBySubmissionId(assignment);
        if (tempAssignment != null) {
            throw new AssignmentAlreadyExistException("Zgłoszenie zostało już przekazane do kontroli");
        }

        // Utworzenie nowego zadania
        assignment.setStatus(AssignmentStatus.Remaining);
        AssignmentApiRequests.createNewAssignment(assignment);

        // Zmiana statusu zgłoszenia
        submission.setStatus(SubmissionStatus.Accepted);
        SubmissionApiRequests.updateSubmission(submission);

        return controller;
    }
}
