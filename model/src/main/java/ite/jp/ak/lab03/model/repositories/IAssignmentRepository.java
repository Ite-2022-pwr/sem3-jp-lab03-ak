package ite.jp.ak.lab03.model.repositories;

import ite.jp.ak.lab03.model.entities.Assignment;
import ite.jp.ak.lab03.model.enums.AssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAssignmentRepository extends JpaRepository<Assignment, UUID> {

    @Query("SELECT a FROM Assignment a JOIN a.controller c WHERE c.id = ?1")
    Collection<Assignment> findAllByControllerId(UUID controllerId);

    @Query("SELECT a FROM Assignment a JOIN a.submission s WHERE s.id = ?1")
    Optional<Assignment> findBySubmissionId(UUID submissionId);

    @Query("SELECT a FROM Assignment a WHERE a.status = ?1")
    Collection<Assignment> findAllByStatus(AssignmentStatus status);

}
