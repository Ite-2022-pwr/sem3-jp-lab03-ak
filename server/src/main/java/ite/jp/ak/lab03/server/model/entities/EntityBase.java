package ite.jp.ak.lab03.server.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@MappedSuperclass
@Getter
@Setter
public class EntityBase {

    @Id
    @UuidGenerator
    protected UUID id;

}
