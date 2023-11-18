package ite.jp.ak.lab03.server.model.entities;

import ite.jp.ak.lab03.server.model.enums.SubmissionStatus;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Submission extends EntityBase {

    @Column
    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Citizen citizen;

    @Column
    private LocalDate date;

    @OneToMany(mappedBy = "submission")
    private Set<Tree> trees = new HashSet<>();

    @OneToOne(mappedBy = "submission")
    private Report report;

    @OneToOne(mappedBy = "submission")
    private Feedback feedback;

    @OneToOne(mappedBy = "submission")
    private Assignment assignment;

}
