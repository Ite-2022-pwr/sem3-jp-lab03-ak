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
public class Manager extends EntityBase {

        @Column(nullable = false, unique = true)
        private String username;

        @Column(nullable = false, unique = true, length = 11)
        private String pesel;

        @OneToMany(mappedBy = "manager")
        private Set<Feedback> feedbacks = new HashSet<>();
}
