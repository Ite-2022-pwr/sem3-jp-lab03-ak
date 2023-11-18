package ite.jp.ak.lab03.server.model.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table
@Getter
@Setter
public class Feedback extends EntityBase {

    @JoinColumn(nullable = false)
    @OneToOne
    private Submission submission;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Manager manager;

    @Column(nullable = false)
    private String description;

}
