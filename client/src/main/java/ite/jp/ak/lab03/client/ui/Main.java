package ite.jp.ak.lab03.client.ui;

import ite.jp.ak.lab03.client.dto.*;
import ite.jp.ak.lab03.client.logic.requests.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        CitizenDto citizenDto = new CitizenDto();
        citizenDto.setUsername("krzysiek");
        System.out.println(citizenDto);
        CitizenDto citizenDto2 = CitizenApiRequests.getCitizenByUsername(citizenDto);
        System.out.println(citizenDto2);
        System.out.println();

        ControllerDto controllerDto = new ControllerDto();
        controllerDto.setUsername("areczek");
        System.out.println(controllerDto);
        ControllerDto controllerDto2 = ControllerApiRequests.getControllerByUsername(controllerDto);
        System.out.println(controllerDto2);
        System.out.println();

        ManagerDto managerDto = new ManagerDto();
        managerDto.setUsername("janusz");
        System.out.println(managerDto);
        ManagerDto managerDto2 = ManagerApiRequests.getManagerByUsername(managerDto);
        System.out.println(managerDto2);
        System.out.println();

        SubmissionDto submissionDto = new SubmissionDto();
        submissionDto.setCitizen(citizenDto2);

        SubmissionDto[] submissionDtos = SubmissionApiRequests.getSubmissionsByCitizenId(submissionDto);
        System.out.println(Arrays.toString(submissionDtos));
        System.out.println();

        ReportDto reportDto = new ReportDto();
        reportDto.setSubmission(submissionDtos[0]);
        reportDto.setController(controllerDto2);
        System.out.println(reportDto);

        ReportDto[] reportDtos = ReportApiRequests.getReportsByControllerId(reportDto);
        System.out.println(Arrays.toString(reportDtos));
        System.out.println();

        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setSubmission(submissionDtos[0]);
        feedbackDto.setManager(managerDto2);
        System.out.println(feedbackDto);

        FeedbackDto[] feedbackDtos = FeedbackApiRequests.getFeedbacksByManagerId(feedbackDto);
        System.out.println(Arrays.toString(feedbackDtos));
        System.out.println();

        AssignmentDto assignmentDto = new AssignmentDto();
        assignmentDto.setController(controllerDto2);
        System.out.println(assignmentDto);

        AssignmentDto[] assignmentDtos = AssignmentApiRequests.getAssignmentsByControllerId(assignmentDto);
        System.out.println(Arrays.toString(assignmentDtos));
        System.out.println();

    }
}
