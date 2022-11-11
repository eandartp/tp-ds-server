package it.tacticalpeople.tpdsserver.service;

import it.tacticalpeople.tpdsserver.domain.TypeSkill;
import it.tacticalpeople.tpdsserver.dto.TypeSkillDto;
import it.tacticalpeople.tpdsserver.mapper.TypeSkillMapper;
import it.tacticalpeople.tpdsserver.repository.TypeSkillRepository;
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
public class TypeSkillService {

    private final Logger log = LoggerFactory.getLogger(TypeSkillService.class);

    @Autowired
    private TypeSkillRepository typeSkillRepository;

    @Autowired
    private TypeSkillMapper mapper;

    public TypeSkill save(TypeSkillDto typeSkillDto)
    {
        log.debug("Request to save TypeSkill : {}", typeSkillDto);
        TypeSkill typeSkill = this.mapper.fromDtoToTypeSkill(typeSkillDto);
        return typeSkillRepository.save(typeSkill);
    }

    public Optional<TypeSkill> partialUpdate(TypeSkillDto typeSkillDto)
    {
        log.debug("Request to partially update TypeSkill : {}", typeSkillDto);
        return typeSkillRepository
                .findById(typeSkillDto.getId())
                .map(existingTypeSkill ->
                {
                    if(typeSkillDto.getDescription() != null)
                        existingTypeSkill.setDescription((typeSkillDto.getDescription()));
                    return existingTypeSkill;
                })
                .map(typeSkillRepository::save);

    }

    @Transactional(readOnly = true)
    public List<TypeSkillDto> findAll()
    {
        log.debug("Request to get all TypeSkills");
        return typeSkillRepository.findAll().stream()
                .map(typeSkill -> mapper.fromTypeSkillToDto(typeSkill))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TypeSkillDto findOne(Long id)
    {
        log.debug("Request to get TypeSkill : {}", id);
        Optional<TypeSkill> typeSkill = typeSkillRepository.findById(id);
        TypeSkillDto typeSkillDto = null;
        if(typeSkill.isPresent())
            typeSkillDto = this.mapper.fromTypeSkillToDto(typeSkill.get());
        return typeSkillDto;
    }

    public void delete (Long id)
    {
        log.debug("Request to delete TypeSkill : {}", id);
        typeSkillRepository.deleteById(id);
    }

}
