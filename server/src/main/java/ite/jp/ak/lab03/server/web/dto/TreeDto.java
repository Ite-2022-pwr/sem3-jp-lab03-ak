package ite.jp.ak.lab03.server.web.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TreeDto {

    private String name;

    private Double diameter;

    private UUID id;

}
