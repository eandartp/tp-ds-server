package it.tacticalpeople.tpdsserver.repository;

import it.tacticalpeople.tpdsserver.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
}
