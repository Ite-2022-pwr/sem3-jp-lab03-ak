package ite.jp.ak.lab03.client.dto;

import ite.jp.ak.lab03.client.enums.AssignmentStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class AssignmentDto {

    private UUID id;

    private ControllerDto controller;

    private SubmissionDto submission;

    private AssignmentStatus status;

}
