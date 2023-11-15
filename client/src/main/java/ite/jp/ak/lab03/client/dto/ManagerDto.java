package ite.jp.ak.lab03.client.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ManagerDto {

    private UUID id;

    private String username;

    private String pesel;

}
