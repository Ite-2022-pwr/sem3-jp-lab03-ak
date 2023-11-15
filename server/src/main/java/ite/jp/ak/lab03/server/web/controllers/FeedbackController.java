package ite.jp.ak.lab03.server.web.controllers;

import ite.jp.ak.lab03.server.web.dto.FeedbackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ite.jp.ak.lab03.server.web.services.FeedbackService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping("/{feedbackId}")
    public ResponseEntity<FeedbackDto> getById(@PathVariable UUID feedbackId) {
        return ResponseEntity.ok(feedbackService.getById(feedbackId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FeedbackDto>> getAll() {
        return ResponseEntity.ok(feedbackService.getAll());
    }

    @GetMapping("/getByManagerId/{managerId}")
    public ResponseEntity<List<FeedbackDto>> getByManagerId(@PathVariable UUID managerId) {
        return ResponseEntity.ok(feedbackService.getAllByManagerId(managerId));
    }

    @GetMapping("/getBySubmissionId/{submissionId}")
    public ResponseEntity<FeedbackDto> getBySubmissionId(@PathVariable UUID submissionId) {
        return ResponseEntity.ok(feedbackService.getBySubmissionId(submissionId));
    }

    @PostMapping("/new")
    public ResponseEntity<FeedbackDto> create(@RequestBody FeedbackDto feedbackDto) {
        return ResponseEntity.ok(feedbackService.createDto(feedbackService.createAndSave(feedbackDto)));
    }
}
