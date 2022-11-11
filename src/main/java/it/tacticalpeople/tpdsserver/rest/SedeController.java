package it.tacticalpeople.tpdsserver.rest;

import it.tacticalpeople.tpdsserver.coreexception.ResourceException;
import it.tacticalpeople.tpdsserver.domain.Sede;
import it.tacticalpeople.tpdsserver.dto.SedeDto;
import it.tacticalpeople.tpdsserver.repository.SedeRepository;
import it.tacticalpeople.tpdsserver.service.SedeService;
import it.tacticalpeople.tpdsserver.webutils.HeaderUtil;
import it.tacticalpeople.tpdsserver.webutils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/sedi")
public class SedeController {

    private final Logger log = LoggerFactory.getLogger(SedeController.class);

    private static final String ENTITY_NAME = "Sede";

    //	@Value("${clientApp.name}")
    private String applicationName;

    @Autowired
    private SedeService sedeService;


    @Autowired
    private SedeRepository sedeRepository;

    @PostMapping("/")
    public ResponseEntity<Sede> createSede(@RequestBody SedeDto protDto) throws URISyntaxException, ResourceException {
        log.debug("REST request to save Sede: {}", protDto);
        Sede result = sedeService.save(protDto);
        return ResponseEntity.created(new URI("/sede/sedeCreate/" + result.getId())).headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSede(@PathVariable(value = "id", required = false) final Long id, @RequestBody SedeDto protDto) throws URISyntaxException, ResourceException {
        log.debug("REST request to update Sede: {}, {}", id, protDto);
        if (protDto.getId() == null) {
            throw new ResourceException("Invalid ID");
        }
        if (!Objects.equals(id, protDto.getId())) {
            throw new ResourceException("Invalid ID Object");
        }
        if (!sedeRepository.existsById(id)) {
            throw new ResourceException("Entity not found");
        }

        Sede result = sedeService.save(protDto);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, protDto.getId().toString())).body(result);
    }

    @PatchMapping(value = "/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<Object> partialUpdateSede(@PathVariable(value = "id", required = false) final Long id, @RequestBody SedeDto protDto) throws URISyntaxException, ResourceException {
        log.debug("REST request to partial update Sede partially : {}, {}", id, protDto);
        if (protDto.getId() == null) throw new ResourceException("Invalid ID");
        if (!Objects.equals(id, protDto.getId())) {
            throw new ResourceException("Invalid ID Object");
        }
        if (!sedeRepository.existsById(id)) {
            throw new ResourceException("Entity not found");
        }

        Optional<Object> result = Optional.ofNullable(sedeService.partialUpdate(protDto));

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, protDto.getId().toString()));
    }

    @GetMapping("/")
    public List<SedeDto> getAllSede() {
        log.debug("REST request to get all Sede:{}");
        return sedeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SedeDto> getSedeById(@PathVariable Long id) {
        log.debug("REST request to get Sede: {}", id);
        Optional<SedeDto> protDto = Optional.ofNullable(sedeService.findOne(id));
        return ResponseUtil.wrapOrNotFound(protDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSede(@PathVariable Long id) {
        log.debug("REST request to delete Sede: {]", id);
        sedeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
