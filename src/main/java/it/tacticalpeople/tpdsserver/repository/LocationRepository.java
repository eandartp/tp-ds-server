package it.tacticalpeople.tpdsserver.repository;

import it.tacticalpeople.tpdsserver.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{

}
