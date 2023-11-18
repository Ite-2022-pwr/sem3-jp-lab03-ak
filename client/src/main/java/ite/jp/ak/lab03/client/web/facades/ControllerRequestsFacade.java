package ite.jp.ak.lab03.client.web.facades;

import ite.jp.ak.lab03.client.dto.AssignmentDto;
import ite.jp.ak.lab03.client.dto.ControllerDto;
import ite.jp.ak.lab03.client.dto.ReportDto;
import ite.jp.ak.lab03.client.dto.SubmissionDto;
import ite.jp.ak.lab03.client.enums.AssignmentStatus;
import ite.jp.ak.lab03.client.exceptions.NoReportDescriptionException;
import ite.jp.ak.lab03.client.exceptions.ReportAlreadyExistException;
import ite.jp.ak.lab03.client.web.requests.AssignmentApiRequests;
import ite.jp.ak.lab03.client.web.requests.ReportApiRequests;
import ite.jp.ak.lab03.client.web.requests.SubmissionApiRequests;

public class ControllerRequestsFacade {
    public static void createReportForSubmission(SubmissionDto submission, ControllerDto controller, String reportDescription) throws ReportAlreadyExistException, NoReportDescriptionException {
        submission = SubmissionApiRequests.getSubmissionById(submission);
        ReportDto tempReport = new ReportDto();
        tempReport.setSubmission(submission);
        ReportDto report = ReportApiRequests.getReportBySubmissionId(tempReport);

        if (report != null) {
            throw new ReportAlreadyExistException("Utworzono już raport dla tego zgłoszenia");
        }

        if (reportDescription == null || reportDescription.isEmpty()) {
            throw new NoReportDescriptionException("Proszę napisać treść raportu");
        }

        // utworzenie raportu
        ReportDto newReport = new ReportDto();
        newReport.setSubmission(submission);
        newReport.setDescription(reportDescription);
        newReport.setController(controller);
        ReportApiRequests.createNewReport(newReport);

        AssignmentDto tempAssignment = new AssignmentDto();
        tempAssignment.setSubmission(submission);
        AssignmentDto assignment = AssignmentApiRequests.getAssignmentBySubmissionId(tempAssignment);
        assignment.setStatus(AssignmentStatus.Done);
        AssignmentApiRequests.updateAssignment(assignment);
    }
}
