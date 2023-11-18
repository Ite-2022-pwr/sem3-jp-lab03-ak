package ite.jp.ak.lab03.server.model.repositories;

import ite.jp.ak.lab03.server.model.entities.Submission;
import ite.jp.ak.lab03.server.model.enums.SubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.UUID;

public interface ISubmissionRepository extends JpaRepository<Submission, UUID> {

    @Query("SELECT s FROM Submission s JOIN s.citizen c WHERE c.id = ?1")
    Collection<Submission> findAllByCitizenId(UUID citizenId);

    @Query("SELECT s FROM Submission s WHERE s.status = ?1")
    Collection<Submission> findAllByStatus(SubmissionStatus status);
}
