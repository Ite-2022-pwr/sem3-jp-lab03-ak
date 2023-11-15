package ite.jp.ak.lab03.client.logic.requests;

import ite.jp.ak.lab03.client.dto.CitizenDto;
import ite.jp.ak.lab03.client.logic.ApiClient;
import org.springframework.http.HttpMethod;

public class CitizenApiRequests {

    private static final ApiClient apiClient = new ApiClient();

    public static CitizenDto getCitizenById(CitizenDto citizenDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/citizen/" + citizenDto.getId(), citizenDto, CitizenDto.class, CitizenDto.class);
    }

    public static CitizenDto createNewCitizen(CitizenDto citizenDto) {
        return apiClient.makeRequest(HttpMethod.POST, "/citizen/new", citizenDto, CitizenDto.class, CitizenDto.class);
    }

    public static CitizenDto getCitizenByUsername(CitizenDto citizenDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/citizen/getByUsername/" + citizenDto.getUsername(), citizenDto, CitizenDto.class, CitizenDto.class);
    }

    public static CitizenDto getCitizenByPesel(CitizenDto citizenDto) {
        return apiClient.makeRequest(HttpMethod.GET, "/citizen/getByPesel/" + citizenDto.getPesel(), citizenDto, CitizenDto.class, CitizenDto.class);
    }

}
