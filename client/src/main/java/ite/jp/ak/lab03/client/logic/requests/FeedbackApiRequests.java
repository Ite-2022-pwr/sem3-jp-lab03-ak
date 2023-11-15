package ite.jp.ak.lab03.client.logic.requests;

import ite.jp.ak.lab03.client.dto.FeedbackDto;
import ite.jp.ak.lab03.client.logic.ApiClient;
import org.springframework.http.HttpMethod;

public class FeedbackApiRequests {

    private static final ApiClient apiClient = new ApiClient();

    public static FeedbackDto getFeedbackById(FeedbackDto feedbackDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/feedback/" + feedbackDto.getId(), feedbackDto, FeedbackDto.class, FeedbackDto.class);
    }

    public static FeedbackDto createNewFeedback(FeedbackDto feedbackDto) {
        return apiClient.makeRequest(HttpMethod.POST, "/feedback/new", feedbackDto, FeedbackDto.class, FeedbackDto.class);
    }

    public static FeedbackDto[] getFeedbacksBySubmissionId(FeedbackDto feedbackDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/feedback/getBySubmissionId/" + feedbackDto.getSubmission().getId(), feedbackDto, FeedbackDto.class, FeedbackDto[].class);
    }

    public static FeedbackDto[] getAllFeedbacks() {
        return apiClient.makeRequest(HttpMethod.GET, "/feedback/all", new FeedbackDto(), FeedbackDto.class, FeedbackDto[].class);
    }

    public static FeedbackDto[] getFeedbacksByManagerId(FeedbackDto feedbackDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/feedback/getByManagerId/" + feedbackDto.getManager().getId(), feedbackDto, FeedbackDto.class, FeedbackDto[].class);
    }
}
