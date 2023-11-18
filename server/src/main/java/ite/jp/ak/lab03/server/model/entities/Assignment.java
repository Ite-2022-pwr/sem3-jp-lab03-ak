package ite.jp.ak.lab03.server.model.entities;

import ite.jp.ak.lab03.server.model.enums.AssignmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Assignment extends EntityBase {

    @Column
    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Controller controller;

    @OneToOne
    @JoinColumn(nullable = false)
    private Submission submission;

}
