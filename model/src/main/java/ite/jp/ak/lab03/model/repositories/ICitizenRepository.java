package ite.jp.ak.lab03.model.repositories;

import ite.jp.ak.lab03.model.entities.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ICitizenRepository extends JpaRepository<Citizen, UUID> {
    Optional<Citizen> findByPesel(String pesel);
    Optional<Citizen> findByUsername(String username);
}
