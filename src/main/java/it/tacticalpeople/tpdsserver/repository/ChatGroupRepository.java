package it.tacticalpeople.tpdsserver.repository;

import it.tacticalpeople.tpdsserver.domain.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatGroupRepository extends JpaRepository<ChatGroup, Long> {
}
