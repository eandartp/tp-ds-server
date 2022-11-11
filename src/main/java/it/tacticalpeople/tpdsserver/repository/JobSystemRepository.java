package it.tacticalpeople.tpdsserver.repository;

import it.tacticalpeople.tpdsserver.domain.JobSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSystemRepository extends JpaRepository<JobSystem, Long> {

}
