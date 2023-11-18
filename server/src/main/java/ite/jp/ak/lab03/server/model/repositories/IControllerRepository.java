package ite.jp.ak.lab03.server.model.repositories;

import ite.jp.ak.lab03.server.model.entities.Controller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IControllerRepository extends JpaRepository<Controller, UUID> {

    Optional<Controller> findByPesel(String pesel);
    Optional<Controller> findByUsername(String username);
}
