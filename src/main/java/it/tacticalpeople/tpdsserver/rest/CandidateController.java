package it.tacticalpeople.tpdsserver.rest;

import it.tacticalpeople.tpdsserver.coreexception.ResourceException;
import it.tacticalpeople.tpdsserver.domain.Candidate;
import it.tacticalpeople.tpdsserver.dto.CandidateDto;
import it.tacticalpeople.tpdsserver.repository.CandidateRepository;
import it.tacticalpeople.tpdsserver.service.CandidateService;
import it.tacticalpeople.tpdsserver.webutils.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@RestController
@RequestMapping("/candidates")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CandidateController {

    private final Logger log = LoggerFactory.getLogger(CandidateController.class);

    private static final String ENTITY_NAME = "Candidate";

    //@Value("${clientApp.name}")
    private String applicationName;


    @Autowired
    private CandidateService candidateService;

    @Autowired
    private CandidateRepository candidateRepository;

    @RequestMapping(value = "/candidateAll", method = RequestMethod.GET)
    ResponseEntity<List<CandidateDto>> findAllCandidate()
    {
        log.debug("CandidateController: url: /candidateAll method: findAllCandidate");
        return new ResponseEntity<List<CandidateDto>>(candidateService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/candidateById/{id}", method = RequestMethod.GET)
    ResponseEntity<CandidateDto> findByIdCandidate(@PathVariable Long id)
    {
        log.debug("CandidateController: url: /candidateById/{id} method: findByIdCandidate");
        CandidateDto candidateDto = null;
        try
        {
            candidateDto = candidateService.findOne(id);
            return new ResponseEntity<CandidateDto>(candidateDto, HttpStatus.OK);
        }catch(Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path= "/candidateCreate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Candidate> saveCandidate(@RequestBody CandidateDto candidateDto) throws Exception
    {
        log.debug("CandidateController: url: /candidateCreate method: saveCandidate");
        //add resource
        Candidate newCandidate = candidateService.save(candidateDto);

        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCandidate.getId())
                .toUri();

        //Send location in response
        return ResponseEntity.created(location).body(newCandidate);
    }
    @DeleteMapping("/candidateDelete/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id)
    {
        log.debug("CandidateController: url: /candidateDelete/{id} method: deleteCandidate");
        candidateService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/candidateUpdate/{id}")
    public ResponseEntity<Object> updateCandidate(@PathVariable(value="id", required=false) final Long id, @RequestBody CandidateDto candidateDto)
    throws URISyntaxException, ResourceException
    {
        log.debug("CandidateController: url: /candidateUpdate/{id} method: updateCandidate");
        if (candidateDto.getId() == null) {
            throw new ResourceException("Invalid id");
        }
        if (!Objects.equals(id, candidateDto.getId())) {
            throw new ResourceException("Invalid ID Object");
        }

        if (!candidateRepository.existsById(id)) {
            throw new ResourceException("Entity not found");
        }

        Candidate result = candidateService.save(candidateDto);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidateDto.getId().toString()))
                .body(result);
    }

}
