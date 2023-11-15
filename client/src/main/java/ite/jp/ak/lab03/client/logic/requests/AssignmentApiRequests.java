package ite.jp.ak.lab03.client.logic.requests;

import ite.jp.ak.lab03.client.dto.AssignmentDto;
import ite.jp.ak.lab03.client.logic.ApiClient;
import org.springframework.http.HttpMethod;

public class AssignmentApiRequests {

    private static final ApiClient apiClient = new ApiClient();

    public static AssignmentDto getAssignmentById(AssignmentDto assignmentDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/assignment/" + assignmentDto.getId(), assignmentDto, AssignmentDto.class, AssignmentDto.class);
    }

    public static AssignmentDto createNewAssignment(AssignmentDto assignmentDto) {
        return apiClient.makeRequest(HttpMethod.POST, "/assignment/new", assignmentDto, AssignmentDto.class, AssignmentDto.class);
    }

    public static AssignmentDto updateAssignment(AssignmentDto assignmentDto) {
        return apiClient.makeRequest(HttpMethod.PUT, "/assignment/update", assignmentDto, AssignmentDto.class, AssignmentDto.class);
    }

    public static AssignmentDto[] getAllAssignments() {
        return apiClient.makeRequest(HttpMethod.GET, "/assignment/all", new AssignmentDto(), AssignmentDto.class, AssignmentDto[].class);
    }

    public static AssignmentDto[] getAssignmentsByControllerId(AssignmentDto assignmentDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/assignment/getByControllerId/" + assignmentDto.getController().getId(), assignmentDto, AssignmentDto.class, AssignmentDto[].class);
    }

    public static AssignmentDto[] getAssignmentsByStatus(AssignmentDto assignmentDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/assignment/getByStatus/" + assignmentDto.getStatus(), assignmentDto, AssignmentDto.class, AssignmentDto[].class);
    }
}
