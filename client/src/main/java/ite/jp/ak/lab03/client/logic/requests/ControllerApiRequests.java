package ite.jp.ak.lab03.client.logic.requests;

import ite.jp.ak.lab03.client.dto.ControllerDto;
import ite.jp.ak.lab03.client.logic.ApiClient;
import org.springframework.http.HttpMethod;

public class ControllerApiRequests {

    private static final ApiClient apiClient = new ApiClient();

    public static ControllerDto getControllerById(ControllerDto controllerDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/controller/" + controllerDto.getId(), controllerDto, ControllerDto.class, ControllerDto.class);
    }

    public static ControllerDto createNewController(ControllerDto controllerDto) {
        return apiClient.makeRequest(HttpMethod.POST, "/controller/new", controllerDto, ControllerDto.class, ControllerDto.class);
    }

    public static ControllerDto getControllerByUsername(ControllerDto controllerDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/controller/getByUsername/" + controllerDto.getUsername(), controllerDto, ControllerDto.class, ControllerDto.class);
    }

    public static ControllerDto getControllerByPesel(ControllerDto controllerDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/controller/getByPesel/" + controllerDto.getPesel(), controllerDto, ControllerDto.class, ControllerDto.class);
    }

}
