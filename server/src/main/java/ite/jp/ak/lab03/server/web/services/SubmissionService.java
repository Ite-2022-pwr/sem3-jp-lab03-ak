package ite.jp.ak.lab03.server.web.services;

import ite.jp.ak.lab03.model.entities.Citizen;
import ite.jp.ak.lab03.model.entities.Submission;
import ite.jp.ak.lab03.model.entities.Tree;
import ite.jp.ak.lab03.model.enums.SubmissionStatus;
import ite.jp.ak.lab03.model.repositories.ICitizenRepository;
import ite.jp.ak.lab03.model.repositories.IReportRepository;
import ite.jp.ak.lab03.model.repositories.ISubmissionRepository;
import ite.jp.ak.lab03.model.repositories.ITreeRepository;
import ite.jp.ak.lab03.server.web.dto.SubmissionDto;
import ite.jp.ak.lab03.server.web.dto.TreeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class SubmissionService {

    private final ISubmissionRepository submissionRepository;
    private final ICitizenRepository citizenRepository;
    private final ITreeRepository treeRepository;

    private final TreeService treeService;

    private final CitizenService citizenService;

    public SubmissionDto createDto(Submission submission) {
        SubmissionDto submissionDto = new SubmissionDto();
        submissionDto.setId(submission.getId());
        submissionDto.setCitizen(citizenService.createDto(submission.getCitizen()));
        submissionDto.setDate(submission.getDate());
        submissionDto.setTrees(treeRepository.findAllBySubmissionId(submission.getId()).stream().map(treeService::createDto).toList().toArray(new TreeDto[0]));
        submissionDto.setStatus(submission.getStatus());

        return submissionDto;
    }

    public SubmissionDto getById(UUID id) {
        Submission submission = submissionRepository.findById(id).orElse(null);
        if (submission != null) {
            return createDto(submission);
        }
        return null;
    }

    public Submission create(SubmissionDto submissionDto) {
        if (submissionDto == null) {
            return null;
        }
        Submission newSubmission = new Submission();

        Citizen citizen = citizenRepository.findById(submissionDto.getCitizen().getId()).orElse(null);
        newSubmission.setCitizen(citizen);
        newSubmission.setDate(LocalDate.now());
        newSubmission = submissionRepository.saveAndFlush(newSubmission);

        for(TreeDto treeDto : submissionDto.getTrees()) {
            Tree newTree = treeService.createAndSave(treeDto);
            newTree.setSubmission(newSubmission);
            newSubmission.getTrees().add(newTree);
        }


//        newSubmission.setReport(reportRepository.findById());
        newSubmission.setStatus(submissionDto.getStatus());

//        newSubmission.setFeedback(feedbackService.create(submissionDto.getFeedback()));
        return newSubmission;
    }

    public Submission createAndSave(SubmissionDto submissionDto) {
        Submission newSubmission = create(submissionDto);
        return submissionRepository.saveAndFlush(newSubmission);
    }

    public List<SubmissionDto> getAll() {
        List<SubmissionDto> submissionDtos = new ArrayList<>();
        for (Submission submission : submissionRepository.findAll()) {
            submissionDtos.add(createDto(submission));
        }
        return submissionDtos;
    }

    public Submission update(SubmissionDto submissionDto) {
        Submission submission = submissionRepository.findById(submissionDto.getId()).orElse(null);
        if (submission != null) {
            submission.setCitizen(citizenRepository.findById(submissionDto.getCitizen().getId()).orElse(null));
            submission.setDate(submissionDto.getDate());
            submission.setTrees(Stream.of(submissionDto.getTrees()).map(treeService::create).collect(Collectors.toSet()));
            submission.setStatus(submissionDto.getStatus());
            return submissionRepository.saveAndFlush(submission);
        }
        return null;
    }

    public List<SubmissionDto> getAllByCitizenId(UUID citizenId) {
        return submissionRepository.findAllByCitizenId(citizenId).stream().map(this::createDto).toList();
    }

    public List<SubmissionDto> getAllByStatus(SubmissionStatus status) {
        return submissionRepository.findAllByStatus(status).stream().map(this::createDto).toList();
    }
}
