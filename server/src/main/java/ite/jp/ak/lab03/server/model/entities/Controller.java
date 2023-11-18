package ite.jp.ak.lab03.server.model.entities;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Controller extends EntityBase {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true, length = 11)
    private String pesel;

    @OneToMany(mappedBy = "controller")
    private Set<Report> reports = new HashSet<>();

    @OneToMany(mappedBy = "controller")
    private Set<Assignment> assignments = new HashSet<>();

}
