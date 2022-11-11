package it.tacticalpeople.tpdsserver.rest;

import it.tacticalpeople.tpdsserver.coreexception.ResourceException;
import it.tacticalpeople.tpdsserver.domain.TypeSkill;
import it.tacticalpeople.tpdsserver.dto.TypeSkillDto;
import it.tacticalpeople.tpdsserver.repository.TypeSkillRepository;
import it.tacticalpeople.tpdsserver.service.TypeSkillService;
import it.tacticalpeople.tpdsserver.webutils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/typeSkills")
public class TypeSkillController {

    private final Logger log = LoggerFactory.getLogger(TypeSkillController.class);

    private static final String ENTITY_NAME = "TypeSkill";

    //	@Value("${clientApp.name}")
    private String applicationName;


    @Autowired
    private TypeSkillService typeSkillService;

    @Autowired
    private TypeSkillRepository typeSkillRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<TypeSkillDto>> findAll()
    {
        log.debug("TypeSkillController - /typeSkillAll - findAll");
        return new ResponseEntity<List<TypeSkillDto>>(typeSkillService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<TypeSkillDto> findById(@PathVariable Long id)
    {
        log.debug("TypeSkillController - /typeSkillById - findById");
        TypeSkillDto typeSkillDto = null;
        try
        {
            typeSkillDto = typeSkillService.findOne(id);
            return new ResponseEntity<TypeSkillDto>(typeSkillDto, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TypeSkill> saveTypeSkill(@RequestBody TypeSkillDto typeSkillDto) throws Exception
    {
        log.debug("TypeSkillController - /typeSkillSave - saveTypeSkill");
        //add resource
        TypeSkill newTypeSkill = typeSkillService.save(typeSkillDto);

        //create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newTypeSkill.getId())
                .toUri();
        //Send location in response
        return ResponseEntity.created(location).body(newTypeSkill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTypeSkill(@PathVariable(value="id", required=false) final Long id, @RequestBody TypeSkillDto typeSkillDto)
            throws URISyntaxException, ResourceException
    {
        log.debug("TypeSkillController: url: /typeSkillUpdate/{id} method: updateTypeSkill");
        if (typeSkillDto.getId() == null) {
            throw new ResourceException("Invalid id");
        }
        if (!Objects.equals(id, typeSkillDto.getId())) {
            throw new ResourceException("Invalid ID Object");
        }

        if (!typeSkillRepository.existsById(id)) {
            throw new ResourceException("Entity not found");
        }

        TypeSkill result = typeSkillService.save(typeSkillDto);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeSkillDto.getId().toString()))
                .body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeSkill(@PathVariable Long id)
    {
        log.debug("TypeSkillController: url: /typeSkillDelete/{id} method: deleteTypeSkill");
        typeSkillService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

}
