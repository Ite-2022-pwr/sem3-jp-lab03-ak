package ite.jp.ak.lab03.client.web.requests;

import ite.jp.ak.lab03.client.dto.ReportDto;
import ite.jp.ak.lab03.client.web.ApiClient;
import org.springframework.http.HttpMethod;

public class ReportApiRequests {

    private static final ApiClient apiClient = new ApiClient();

    public static ReportDto getReportById(ReportDto reportDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/report/" + reportDto.getId(), reportDto, ReportDto.class, ReportDto.class);
    }

    public static ReportDto createNewReport(ReportDto reportDto) {
        return apiClient.makeRequest(HttpMethod.POST, "/report/new", reportDto, ReportDto.class, ReportDto.class);
    }

    public static ReportDto getReportBySubmissionId(ReportDto reportDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/report/getBySubmissionId/" + reportDto.getSubmission().getId(), reportDto, ReportDto.class, ReportDto.class);
    }

    public static ReportDto[] getAllReports() {
        return apiClient.makeRequest(HttpMethod.GET, "/report/all", new ReportDto(), ReportDto.class, ReportDto[].class);
    }

    public static ReportDto[] getReportsByControllerId(ReportDto reportDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/report/getByControllerId/" + reportDto.getController().getId(), reportDto, ReportDto.class, ReportDto[].class);
    }
}
