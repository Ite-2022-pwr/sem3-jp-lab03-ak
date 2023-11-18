package ite.jp.ak.lab03.client.ui.models;

import ite.jp.ak.lab03.client.dto.CitizenDto;
import ite.jp.ak.lab03.client.dto.SubmissionDto;
import ite.jp.ak.lab03.client.dto.TreeDto;
import ite.jp.ak.lab03.client.enums.SubmissionStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class SubmissionTableModel {
    private String citizen;
    private LocalDate date;
    private SubmissionStatus status;
    private TreeDto[] trees;
    private UUID id;

    public static SubmissionTableModel fromDto(SubmissionDto submissionDto) {
        SubmissionTableModel submissionTableModel = new SubmissionTableModel();
        submissionTableModel.setId(submissionDto.getId());
        submissionTableModel.setCitizen(submissionDto.getCitizen().getUsername());
        submissionTableModel.setDate(submissionDto.getDate());
        submissionTableModel.setStatus(submissionDto.getStatus());
        submissionTableModel.setTrees(submissionDto.getTrees());
        return submissionTableModel;
    }

    public static SubmissionDto toDto(SubmissionTableModel submissionTableModel) {
        SubmissionDto submissionDto = new SubmissionDto();
        submissionDto.setId(submissionTableModel.getId());
        submissionDto.setDate(submissionTableModel.getDate());
        submissionDto.setStatus(submissionTableModel.getStatus());
        submissionDto.setTrees(submissionTableModel.getTrees());
        return submissionDto;
    }
}
