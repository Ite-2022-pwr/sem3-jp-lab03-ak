package ite.jp.ak.lab03.server.model.repositories;

import ite.jp.ak.lab03.server.model.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IFeedbackRepository extends JpaRepository<Feedback, UUID> {

    @Query("SELECT f FROM Feedback f JOIN f.manager m WHERE m.id = ?1")
    List<Feedback> findAllByManagerId(UUID managerId);

    @Query("SELECT f FROM Feedback f JOIN f.submission s WHERE s.id = ?1")
    Optional<Feedback> findBySubmissionId(UUID submissionId);

}
