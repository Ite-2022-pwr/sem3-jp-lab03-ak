package ite.jp.ak.lab03.client.ui;

import ite.jp.ak.lab03.client.dto.CitizenDto;
import ite.jp.ak.lab03.client.logic.requests.CitizenApiRequests;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        CitizenDto citizenDto = new CitizenDto();
        citizenDto.setUsername("krzysiek");
        System.out.println(citizenDto);
        CitizenDto citizenDto2 = CitizenApiRequests.getCitizenByUsername(citizenDto);
        System.out.println(citizenDto2);


    }
}
