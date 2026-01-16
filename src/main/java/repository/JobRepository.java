package repository;

import domain.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("select distinct j from Job j left join fetch j.requiredSkills where j.id = :id")
    Optional<Job> findWithSkillsById(@Param("id") Long id);
}