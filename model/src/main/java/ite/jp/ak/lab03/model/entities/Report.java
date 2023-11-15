package ite.jp.ak.lab03.model.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table
@Getter
@Setter
public class Report extends EntityBase {

    @JoinColumn(nullable = false)
    @ManyToOne
    private Controller controller;

    @OneToOne
    @JoinColumn(nullable = false)
    private Submission submission;

    @Column(nullable = false)
    private String description;

}
