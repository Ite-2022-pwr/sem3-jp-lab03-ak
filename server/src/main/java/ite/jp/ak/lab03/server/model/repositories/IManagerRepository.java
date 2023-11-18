package ite.jp.ak.lab03.server.model.repositories;

import ite.jp.ak.lab03.server.model.entities.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IManagerRepository extends JpaRepository<Manager, UUID> {
    Optional<Manager> findByPesel(String pesel);
    Optional<Manager> findByUsername(String username);
}
