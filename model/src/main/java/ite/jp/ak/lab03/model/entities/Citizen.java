package ite.jp.ak.lab03.model.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Citizen extends EntityBase {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true, length = 11)
    private String pesel;

    @OneToMany(mappedBy = "citizen")
    private Set<Submission> submissions = new HashSet<>();

}
