package ite.jp.ak.lab03.server.model.entities;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table
@Getter
@Setter
public class Tree extends EntityBase {

    @ManyToOne
//    @JoinColumn(nullable = false)
    @JoinColumn
    private Submission submission;

    @Column
    private String name;

    @Column
    private Double diameter;
}
