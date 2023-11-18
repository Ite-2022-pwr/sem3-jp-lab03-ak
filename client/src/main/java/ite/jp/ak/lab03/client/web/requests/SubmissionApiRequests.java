package ite.jp.ak.lab03.client.web.requests;

import ite.jp.ak.lab03.client.dto.SubmissionDto;
import ite.jp.ak.lab03.client.web.ApiClient;
import org.springframework.http.HttpMethod;

public class SubmissionApiRequests {

    private static final ApiClient apiClient = new ApiClient();

    public static SubmissionDto getSubmissionById(SubmissionDto submissionDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/submission/" + submissionDto.getId(), submissionDto, SubmissionDto.class, SubmissionDto.class);
    }

    public static SubmissionDto createNewSubmission(SubmissionDto submissionDto) {
        return apiClient.makeRequest(HttpMethod.POST, "/submission/new", submissionDto, SubmissionDto.class, SubmissionDto.class);
    }

    public static SubmissionDto[] getSubmissionsByCitizenId(SubmissionDto submissionDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/submission/getByCitizenId/" + submissionDto.getCitizen().getId(), submissionDto, SubmissionDto.class, SubmissionDto[].class);
    }

    public static SubmissionDto[] getSubmissionsByStatus(SubmissionDto submissionDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/submission/getByStatus/" + submissionDto.getStatus(), submissionDto, SubmissionDto.class, SubmissionDto[].class);
    }

    public static SubmissionDto[] getAllSubmissions() {
        return apiClient.makeRequest(HttpMethod.GET, "/submission/all", new SubmissionDto(), SubmissionDto.class, SubmissionDto[].class);
    }

    public static SubmissionDto updateSubmission(SubmissionDto submissionDto) {
        return apiClient.makeRequest(HttpMethod.PUT, "/submission/update", submissionDto, SubmissionDto.class, SubmissionDto.class);
    }

}
