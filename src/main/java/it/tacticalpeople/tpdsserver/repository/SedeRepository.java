package it.tacticalpeople.tpdsserver.repository;

import it.tacticalpeople.tpdsserver.domain.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Long> {
}
