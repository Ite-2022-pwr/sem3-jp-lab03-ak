package ite.jp.ak.lab03.server.model.repositories;


import ite.jp.ak.lab03.server.model.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface IReportRepository extends JpaRepository<Report, UUID> {

    @Query("SELECT r FROM Report r JOIN r.controller c WHERE c.id = ?1")
    Collection<Report> findAllByControllerId(UUID controllerId);

    @Query("SELECT r FROM Report r JOIN r.submission s WHERE s.id = ?1")
    Optional<Report> findBySubmissionId(UUID submissionId);
}
