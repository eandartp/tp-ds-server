package it.tacticalpeople.tpdsserver.rest;

import it.tacticalpeople.tpdsserver.domain.Skill;
import it.tacticalpeople.tpdsserver.dto.SkillDto;
import it.tacticalpeople.tpdsserver.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/skills")
public class SkillController {

    private final Logger log = LoggerFactory.getLogger(SkillController.class);

    private static final String ENTITY_NAME = "Skill";

    //	@Value("${clientApp.name}")
    private String applicationName;


    @Autowired
    private SkillService skillService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<SkillDto>> findAll()
    {
        log.debug("SkillController - /skillAll - findAll");
        return new ResponseEntity<List<SkillDto>>(skillService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<SkillDto> findById(@PathVariable Long id)
    {
        log.debug("SkillController - /skillById - findById");
        SkillDto skillDto = null;
        try
        {
            skillDto = skillService.findOne(id);
            return new ResponseEntity<SkillDto>(skillDto, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Skill> saveSkill(@RequestBody SkillDto skillDto) throws Exception
    {
        log.debug("SkillController - /skillSave - saveSkill");
        //add resource
        Skill newSkill = skillService.save(skillDto);

        //create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newSkill.getId())
                .toUri();

        //send location in response
        return ResponseEntity.created(location).body(newSkill);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity updateSkill(@PathVariable Long id, @RequestBody SkillDto skillDto){
        log.debug("SkillController - /skillPatch - updateSkill");
        return ResponseEntity.ok(skillService.partialUpdate(id,skillDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id)
    {
        log.debug("SkillController - /skillDelete - deleteSkill");
        skillService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

}
