package it.tacticalpeople.tpdsserver.mapper;

import it.tacticalpeople.tpdsserver.domain.TypeSkill;
import it.tacticalpeople.tpdsserver.dto.TypeSkillDto;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class TypeSkillMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    public TypeSkill fromDtoToTypeSkill(TypeSkillDto typeSkillDto)
    {
        TypeSkill typeSkill = new TypeSkill();
        typeSkill.setId(typeSkillDto.getId());
        typeSkill.setDescription(typeSkillDto.getDescription());
        return typeSkill;
    }

    public TypeSkillDto fromTypeSkillToDto(TypeSkill typeSkill)
    {
        TypeSkillDto typeSkillDto = new TypeSkillDto();
        typeSkillDto.setId(typeSkill.getId());
        typeSkillDto.setDescription(typeSkill.getDescription());
        return typeSkillDto;
    }

}
