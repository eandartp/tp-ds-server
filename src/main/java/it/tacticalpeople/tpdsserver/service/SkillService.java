package it.tacticalpeople.tpdsserver.service;

import it.tacticalpeople.tpdsserver.coreexception.ResourceException;
import it.tacticalpeople.tpdsserver.domain.Skill;
import it.tacticalpeople.tpdsserver.dto.SkillDto;
import it.tacticalpeople.tpdsserver.mapper.SkillMapper;
import it.tacticalpeople.tpdsserver.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class SkillService {

    private final Logger log = LoggerFactory.getLogger(SkillService.class);

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SkillMapper mapper;

    /**
     * Save a skill
     * @param skillDto to transform into entity and save
     * @return the persisted entity
     */
    public Skill save(SkillDto skillDto)
    {
        log.debug("Request to save Skill : {}", skillDto);
        Skill skill = this.mapper.fromDtoToSkill(skillDto);
        return skillRepository.save(skill);
    }

    /**
     * Partially update a Skill
     * @param skillDto to transform into entity to update partially.
     * @return the persisted entity
     */
    public SkillDto partialUpdate(Long id, SkillDto skillDto)
    {
        log.debug("Skill Service - partialUpdate");
        Skill skill = null;
        try {
            skill = skillRepository.findById(id)
                    .orElseThrow(() -> new ResourceException("Invalid Id"));
        } catch (ResourceException e) {
            throw new RuntimeException(e);
        }
        skill = mapper.fromDtoToSkill(skillDto);
        skill = skillRepository.save(skill);
        return mapper.fromSkillToDto(skill);
    }

    /**
     * Get all the Skills
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SkillDto> findAll()
    {
        log.debug("Request to get all Skills");
        return skillRepository.findAll().stream()
                .map(skill -> mapper.fromSkillToDto(skill))
                .collect(Collectors.toList());
    }

    /**
     * Get one SKill by id
     * @param id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SkillDto findOne(Long id)
    {
        log.debug("Request to get Skill : {}", id);
        Optional<Skill> skill = skillRepository.findById(id);
        SkillDto skillDto = null;
        if(skill.isPresent())
            skillDto = this.mapper.fromSkillToDto(skill.get());
        return skillDto;
    }

    /**
     * Delete the Skill by id
     * @param id of the entity
     */
    public void delete(Long id)
    {
        log.debug("Request to delete Hard Skill : {}", id);
        skillRepository.deleteById(id);
    }

}
