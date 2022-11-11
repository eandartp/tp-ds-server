package it.tacticalpeople.tpdsserver.repository;

import it.tacticalpeople.tpdsserver.domain.TypeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeSkillRepository extends JpaRepository<TypeSkill, Long> {
}
