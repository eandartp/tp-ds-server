package it.tacticalpeople.tpdsserver.rest;

import it.tacticalpeople.tpdsserver.coreexception.ResourceException;
import it.tacticalpeople.tpdsserver.domain.JobSystem;
import it.tacticalpeople.tpdsserver.dto.EventDto;
import it.tacticalpeople.tpdsserver.dto.JobSystemDto;
import it.tacticalpeople.tpdsserver.repository.JobSystemRepository;
import it.tacticalpeople.tpdsserver.service.JobSystemService;
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
@RequestMapping("/jobSystems")
public class JobSystemController {

    private final Logger log = LoggerFactory.getLogger(JobSystemController.class);

    private static final String ENTITY_NAME = "JobSystem";

    //@Value("${clientApp.name}")
    private String applicationName;

    @Autowired
    private JobSystemService jobSystemService;

    @Autowired
    private JobSystemRepository jobSystemRepository;

    @PostMapping("/")
    public ResponseEntity<JobSystem> createJobSystem(@RequestBody JobSystemDto protDTO) throws URISyntaxException, ResourceException {
        log.debug("REST request to save JobSystem : {}", protDTO);
        if (protDTO.getId() != null) {
            throw new ResourceException("A new JobSystem cannot already have an ID");
        }
        JobSystem result = jobSystemService.save(protDTO);
        return ResponseEntity
                .created(new URI("/jobSystems/createJobSystem/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateJobSystem(@PathVariable(value = "id", required = false) final Long id, @RequestBody JobSystemDto protDTO)
            throws URISyntaxException, ResourceException {
        log.debug("REST request to update JobSystem : {}, {}", id, protDTO);
        if (protDTO.getId() == null) {
            throw new ResourceException("Invalid id");
        }
        if (!Objects.equals(id, protDTO.getId())) {
            throw new ResourceException("Invalid ID Object");
        }

        if (!jobSystemRepository.existsById(id)) {
            throw new ResourceException("Entity not found");
        }

        JobSystem result = jobSystemService.save(protDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, protDTO.getId().toString()))
                .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Object> partialUpdateJobSystem(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody JobSystemDto protDTO
    ) throws URISyntaxException, ResourceException {
        log.debug("REST request to partial update JobSystem partially : {}, {}", id, protDTO);
        if (protDTO.getId() == null) {
            throw new ResourceException("Invalid id");
        }
        if (!Objects.equals(id, protDTO.getId())) {
            throw new ResourceException("Invalid ID Object");
        }

        if (!jobSystemRepository.existsById(id)) {
            throw new ResourceException("Entity not found");
        }

        Optional<Object> result = Optional.ofNullable(jobSystemService.partialUpdate(protDTO));

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, protDTO.getId().toString())
        );
    }

    @GetMapping("/")
    public List<JobSystemDto> getAllJobSystems() {
        log.debug("REST request to get all JobSystem");
        return jobSystemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobSystemDto> getJobSystemById(@PathVariable Long id) {
        log.debug("REST request to get JobSystem : {}", id);
        Optional<JobSystemDto> protDTO = Optional.ofNullable(jobSystemService.findOne(id));
        return ResponseUtil.wrapOrNotFound(protDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobSystem(@PathVariable Long id) {
        log.debug("REST request to delete JobSystem : {}", id);
        jobSystemService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

}
