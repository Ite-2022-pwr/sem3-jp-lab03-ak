package ite.jp.ak.lab03.client.logic.requests;

import ite.jp.ak.lab03.client.dto.ManagerDto;
import ite.jp.ak.lab03.client.logic.ApiClient;
import org.springframework.http.HttpMethod;

public class ManagerApiRequests {

    private static final ApiClient apiClient = new ApiClient();

    public static ManagerDto getManagerById(ManagerDto managerDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/manager/" + managerDto.getId(), managerDto, ManagerDto.class, ManagerDto.class);
    }

    public static ManagerDto createNewManager(ManagerDto managerDto) {
        return apiClient.makeRequest(HttpMethod.POST, "/manager/new", managerDto, ManagerDto.class, ManagerDto.class);
    }

    public static ManagerDto getManagerByUsername(ManagerDto managerDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/manager/getByUsername/" + managerDto.getUsername(), managerDto, ManagerDto.class, ManagerDto.class);
    }

    public static ManagerDto getManagerByPesel(ManagerDto managerDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/manager/getByPesel/" + managerDto.getPesel(), managerDto, ManagerDto.class, ManagerDto.class);
    }

}
