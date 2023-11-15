package ite.jp.ak.lab03.model.repositories;

import ite.jp.ak.lab03.model.entities.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.UUID;

public interface ITreeRepository extends JpaRepository<Tree, UUID> {

    @Query("SELECT t FROM Tree t JOIN t.submission s WHERE s.id = ?1")
    Collection<Tree> findAllBySubmissionId(UUID submissionId);
}
