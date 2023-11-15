package ite.jp.ak.lab03.client.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ReportDto {

    private UUID id;

    private ControllerDto controller;

    private SubmissionDto submission;

    private String description;

}
