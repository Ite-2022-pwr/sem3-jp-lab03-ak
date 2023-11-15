package ite.jp.ak.lab03.server.web.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class FeedbackDto {

    private UUID id;

    private String description;

    private ManagerDto manager;

    private SubmissionDto submission;

}
