package ite.jp.ak.lab03.server.web.services;

import ite.jp.ak.lab03.server.model.entities.Assignment;
import ite.jp.ak.lab03.server.model.enums.AssignmentStatus;
import ite.jp.ak.lab03.server.model.repositories.IAssignmentRepository;
import ite.jp.ak.lab03.server.model.repositories.IControllerRepository;
import ite.jp.ak.lab03.server.model.repositories.ISubmissionRepository;
import ite.jp.ak.lab03.server.web.dto.AssignmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AssignmentService {

    private final IAssignmentRepository assignmentRepository;
    private final ISubmissionRepository submissionRepository;
    private final IControllerRepository controllerRepository;

    private final SubmissionService submissionService;
    private final ControllerService controllerService;

    public AssignmentDto createDto(Assignment assignment) {
        AssignmentDto assignmentDto = new AssignmentDto();
        assignmentDto.setId(assignment.getId());
        assignmentDto.setController(controllerService.createDto(assignment.getController()));
        assignmentDto.setSubmission(submissionService.createDto(assignment.getSubmission()));
        assignmentDto.setStatus(assignment.getStatus());
        return assignmentDto;
    }

    public AssignmentDto getById(UUID id) {
        Assignment assignment = assignmentRepository.findById(id).orElse(null);
        if (assignment != null) {
            return createDto(assignment);
        }
        return null;
    }

    public Assignment create(AssignmentDto assignmentDto) {
        if (assignmentDto == null) {
            return null;
        }
        Assignment newAssignment = new Assignment();
        newAssignment.setController(controllerRepository.findById(assignmentDto.getController().getId()).orElse(null));
        newAssignment.setSubmission(submissionRepository.findById(assignmentDto.getSubmission().getId()).orElse(null));
        newAssignment.setStatus(assignmentDto.getStatus());
        return newAssignment;
    }

    public Assignment update(AssignmentDto assignmentDto) {
        if (assignmentDto == null) {
            return null;
        }
        Assignment assignment = assignmentRepository.findById(assignmentDto.getId()).orElse(null);
        if (assignment == null) {
            return null;
        }
        assignment.setController(controllerRepository.findById(assignmentDto.getController().getId()).orElse(null));
        assignment.setSubmission(submissionRepository.findById(assignmentDto.getSubmission().getId()).orElse(null));
        assignment.setStatus(assignmentDto.getStatus());
        return assignmentRepository.saveAndFlush(assignment);
    }

    public Assignment createAndSave(AssignmentDto assignmentDto) {
        return assignmentRepository.saveAndFlush(create(assignmentDto));
    }

    public List<AssignmentDto> getAll() {
        return assignmentRepository.findAll().stream().map(this::createDto).toList();
    }

    public List<AssignmentDto> getAllByControllerId(UUID controllerId) {
        return assignmentRepository.findAllByControllerId(controllerId).stream().map(this::createDto).toList();
    }

    public AssignmentDto getBySubmissionId(UUID submissionId) {
        Assignment assignment = assignmentRepository.findBySubmissionId(submissionId).orElse(null);
        if (assignment != null) {
            return createDto(assignment);
        }
        return null;
    }

    public List<AssignmentDto> getAllByStatus(AssignmentStatus status) {
        return assignmentRepository.findAllByStatus(status).stream().map(this::createDto).toList();
    }
}
