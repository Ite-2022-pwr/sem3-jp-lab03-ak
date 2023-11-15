package ite.jp.ak.lab03.server.web.controllers;

import ite.jp.ak.lab03.model.enums.AssignmentStatus;
import ite.jp.ak.lab03.server.web.dto.AssignmentDto;
import ite.jp.ak.lab03.server.web.services.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assignment")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping("/{assignmentId}")
    public ResponseEntity<AssignmentDto> getById(@PathVariable UUID assignmentId) {
        return ResponseEntity.ok(assignmentService.getById(assignmentId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AssignmentDto>> getAll() {
        return ResponseEntity.ok(assignmentService.getAll());
    }

    @GetMapping("/getByControllerId/{controllerId}")
    public ResponseEntity<List<AssignmentDto>> getByControllerId(@PathVariable UUID controllerId) {
        return ResponseEntity.ok(assignmentService.getAllByControllerId(controllerId));
    }

    @GetMapping("/getBySubmissionId/{submissionId}")
    public ResponseEntity<AssignmentDto> getBySubmissionId(@PathVariable UUID submissionId) {
        return ResponseEntity.ok(assignmentService.getBySubmissionId(submissionId));
    }

    @GetMapping("/getByStatus/{status}")
    public ResponseEntity<List<AssignmentDto>> getByStatus(@PathVariable AssignmentStatus status) {
        return ResponseEntity.ok(assignmentService.getAllByStatus(status));
    }

    @PostMapping("/new")
    public ResponseEntity<AssignmentDto> create(@RequestBody AssignmentDto assignmentDto) {
        return ResponseEntity.ok(assignmentService.createDto(assignmentService.createAndSave(assignmentDto)));
    }

    @PutMapping("/update")
    public ResponseEntity<AssignmentDto> update(@RequestBody AssignmentDto assignmentDto) {
        return ResponseEntity.ok(assignmentService.createDto(assignmentService.update(assignmentDto)));
    }

}
