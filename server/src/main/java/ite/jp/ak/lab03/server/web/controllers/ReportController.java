package ite.jp.ak.lab03.server.web.controllers;

import ite.jp.ak.lab03.server.web.dto.ReportDto;
import ite.jp.ak.lab03.server.web.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportDto> getById(@PathVariable UUID reportId) {
        return ResponseEntity.ok(reportService.getById(reportId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReportDto>> getAll() {
        return ResponseEntity.ok(reportService.getAll());
    }

    @GetMapping("/getByControllerId/{controllerId}")
    public ResponseEntity<List<ReportDto>> getByControllerId(@PathVariable UUID controllerId) {
        return ResponseEntity.ok(reportService.getAllByControllerId(controllerId));
    }

    @GetMapping("/getBySubmissionId/{submissionId}")
    public ResponseEntity<ReportDto> getBySubmissionId(@PathVariable UUID submissionId) {
        return ResponseEntity.ok(reportService.getBySubmissionId(submissionId));
    }

    @PostMapping("/new")
    public ResponseEntity<ReportDto> create(@RequestBody ReportDto reportDto) {
        return ResponseEntity.ok(reportService.createDto(reportService.createAndSave(reportDto)));
    }

}
