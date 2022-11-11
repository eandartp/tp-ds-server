package it.tacticalpeople.tpdsserver.repository;

import it.tacticalpeople.tpdsserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
