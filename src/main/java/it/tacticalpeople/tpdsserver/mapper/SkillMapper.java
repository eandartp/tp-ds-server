package it.tacticalpeople.tpdsserver.mapper;

import it.tacticalpeople.tpdsserver.domain.Skill;
import it.tacticalpeople.tpdsserver.dto.SkillDto;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class SkillMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    public Skill fromDtoToSkill(SkillDto skillDto){
        Skill skill = new Skill();
        skill.setId(skillDto.getId());
        skill.setSkillName(skillDto.getSkillName());
        skill.setSkillDescription(skillDto.getSkillDescription());
        return skill;
    }

    public SkillDto fromSkillToDto(Skill skill){
        SkillDto skillDto = new SkillDto();
        skillDto.setId(skill.getId());
        skillDto.setSkillName(skill.getSkillName());
        skillDto.setSkillDescription(skill.getSkillDescription());
        return skillDto;
    }
}
