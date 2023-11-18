package ite.jp.ak.lab03.server.web.services;

import ite.jp.ak.lab03.server.model.entities.Feedback;
import ite.jp.ak.lab03.server.model.repositories.IFeedbackRepository;
import ite.jp.ak.lab03.server.model.repositories.IManagerRepository;
import ite.jp.ak.lab03.server.model.repositories.ISubmissionRepository;
import ite.jp.ak.lab03.server.web.dto.FeedbackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FeedbackService {

    private final IFeedbackRepository feedbackRepository;
    private final IManagerRepository managerRepository;
    private final ISubmissionRepository submissionRepository;

    private final ManagerService managerService;
    private final SubmissionService submissionService;

    public FeedbackDto createDto(Feedback feedback) {
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setId(feedback.getId());
        feedbackDto.setDescription(feedback.getDescription());
        feedbackDto.setManager(managerService.createDto(feedback.getManager()));
        feedbackDto.setSubmission(submissionService.createDto(feedback.getSubmission()));
        return feedbackDto;
    }

    public FeedbackDto getById(UUID id) {
        Feedback feedback = feedbackRepository.findById(id).orElse(null);
        if (feedback != null) {
            return createDto(feedback);
        }
        return null;
    }

    public Feedback create(FeedbackDto feedbackDto) {
        if (feedbackDto == null) {
            return null;
        }
        Feedback newFeedback = new Feedback();
        newFeedback.setDescription(feedbackDto.getDescription());
        newFeedback.setManager(managerRepository.findById(feedbackDto.getManager().getId()).orElse(null));
        newFeedback.setSubmission(submissionRepository.findById(feedbackDto.getSubmission().getId()).orElse(null));
        return newFeedback;
    }

    public List<FeedbackDto> getAll() {
        return feedbackRepository.findAll().stream().map(this::createDto).toList();
    }

    public List<FeedbackDto> getAllByManagerId(UUID managerId) {
        return feedbackRepository.findAllByManagerId(managerId).stream().map(this::createDto).toList();
    }

    public FeedbackDto getBySubmissionId(UUID submissionId) {
        Feedback feedback = feedbackRepository.findBySubmissionId(submissionId).orElse(null);
        if (feedback != null) {
            return createDto(feedback);
        }
        return null;
    }

    public Feedback createAndSave(FeedbackDto feedbackDto) {
        Feedback newFeedback = create(feedbackDto);
        return feedbackRepository.saveAndFlush(newFeedback);
    }
}
