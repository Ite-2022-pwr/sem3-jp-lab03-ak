package ite.jp.ak.lab03.server.web.services;

import ite.jp.ak.lab03.model.entities.Report;
import ite.jp.ak.lab03.model.repositories.IControllerRepository;
import ite.jp.ak.lab03.model.repositories.IReportRepository;
import ite.jp.ak.lab03.model.repositories.ISubmissionRepository;
import ite.jp.ak.lab03.server.web.dto.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReportService {

    private final IReportRepository reportRepository;
    private final IControllerRepository controllerRepository;
    private final ISubmissionRepository submissionRepository;
    private final ControllerService controllerService;
    private final SubmissionService submissionService;

    public ReportDto createDto(Report report) {
        ReportDto reportDto = new ReportDto();
        reportDto.setId(report.getId());
        reportDto.setController(controllerService.createDto(report.getController()));
        reportDto.setSubmission(submissionService.createDto(report.getSubmission()));
        reportDto.setDescription(report.getDescription());

        return reportDto;
    }

    public ReportDto getById(UUID id) {
        Report report = reportRepository.findById(id).orElse(null);
        if (report != null) {
            return createDto(report);
        }
        return null;
    }

    public List<ReportDto> getAll() {
        return reportRepository.findAll().stream().map(this::createDto).toList();
    }

    public Report create(ReportDto reportDto) {
        if (reportDto == null) {
            return null;
        }
        Report newReport = new Report();
        newReport.setController(controllerRepository.findById(reportDto.getController().getId()).orElse(null));
        newReport.setSubmission(submissionRepository.findById(reportDto.getSubmission().getId()).orElse(null));
        newReport.setDescription(reportDto.getDescription());
        return newReport;
    }

    public Report createAndSave(ReportDto reportDto) {
        Report newReport = create(reportDto);
        return reportRepository.saveAndFlush(newReport);
    }

    public List<ReportDto> getAllByControllerId(UUID controllerId) {
        return reportRepository.findAllByControllerId(controllerId).stream().map(this::createDto).toList();
    }

    public ReportDto getBySubmissionId(UUID submissionId) {
        Report report = reportRepository.findBySubmissionId(submissionId).orElse(null);
        if (report != null) {
            return createDto(report);
        }
        return null;
    }
}
