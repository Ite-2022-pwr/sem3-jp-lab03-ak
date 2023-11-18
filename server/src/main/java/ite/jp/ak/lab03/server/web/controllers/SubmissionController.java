package ite.jp.ak.lab03.server.web.controllers;

import ite.jp.ak.lab03.server.model.enums.SubmissionStatus;
import ite.jp.ak.lab03.server.web.dto.SubmissionDto;
import ite.jp.ak.lab03.server.web.services.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/submission")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/new")
    public ResponseEntity<SubmissionDto> create(@RequestBody SubmissionDto submissionDto) {
        return ResponseEntity.ok(submissionService.createDto(submissionService.createAndSave(submissionDto)));
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionDto> getById(@PathVariable UUID submissionId) {
        return ResponseEntity.ok(submissionService.getById(submissionId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubmissionDto>> getAll() {
        return ResponseEntity.ok(submissionService.getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<SubmissionDto> update(@RequestBody SubmissionDto submissionDto) {
        return ResponseEntity.ok(submissionService.createDto(submissionService.update(submissionDto)));
    }

    @GetMapping("/getByCitizenId/{citizenId}")
    public ResponseEntity<List<SubmissionDto>> getByCitizenId(@PathVariable UUID citizenId) {
        return ResponseEntity.ok(submissionService.getAllByCitizenId(citizenId));
    }

    @GetMapping("/getByStatus/{status}")
    public ResponseEntity<List<SubmissionDto>> getByStatus(@PathVariable SubmissionStatus status) {
        return ResponseEntity.ok(submissionService.getAllByStatus(status));
    }

}
