package it.tacticalpeople.tpdsserver.repository;

import it.tacticalpeople.tpdsserver.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
