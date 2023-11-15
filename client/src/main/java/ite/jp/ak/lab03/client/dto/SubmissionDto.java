package ite.jp.ak.lab03.client.dto;

import ite.jp.ak.lab03.client.enums.SubmissionStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class SubmissionDto {

    private SubmissionStatus status;

    private UUID id;

    private CitizenDto citizen;

    private LocalDate date;

    private TreeDto[] trees;

}
