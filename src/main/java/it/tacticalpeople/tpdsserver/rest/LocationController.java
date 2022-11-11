package it.tacticalpeople.tpdsserver.rest;

import it.tacticalpeople.tpdsserver.coreexception.ResourceException;
import it.tacticalpeople.tpdsserver.domain.Location;
import it.tacticalpeople.tpdsserver.dto.LocationDto;
import it.tacticalpeople.tpdsserver.repository.LocationRepository;
import it.tacticalpeople.tpdsserver.service.LocationService;
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
@RequestMapping("/locations")
public class LocationController {

    private final Logger log = LoggerFactory.getLogger(LocationController.class);

    private static final String ENTITY_NAME = "Location";

    //@Value("${clientApp.name}")
    private String applicationName;


    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @PostMapping("/")
    public ResponseEntity<Location> createLocation(@RequestBody LocationDto protDTO) throws URISyntaxException, ResourceException {
        log.debug("REST request to save Location : {}", protDTO);
        if (protDTO.getId() != null) {
            throw new ResourceException("A new JobSystem cannot already have an ID");
        }
        Location result = locationService.save(protDTO);
        return ResponseEntity
                .created(new URI("/locations/createLocation/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateLocation(@PathVariable(value = "id", required = false) final Long id, @RequestBody LocationDto protDTO)
            throws URISyntaxException, ResourceException {
        log.debug("REST request to update Location : {}, {}", id, protDTO);
        if (protDTO.getId() == null) {
            throw new ResourceException("Invalid id");
        }
        if (!Objects.equals(id, protDTO.getId())) {
            throw new ResourceException("Invalid ID Object");
        }

        if (!locationRepository.existsById(id)) {
            throw new ResourceException("Entity not found");
        }

        Location result = locationService.save(protDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, protDTO.getId().toString()))
                .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Object> partialUpdateLocation(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody LocationDto protDTO
    ) throws URISyntaxException, ResourceException {
        log.debug("REST request to partial update Location partially : {}, {}", id, protDTO);
        if (protDTO.getId() == null) {
            throw new ResourceException("Invalid id");
        }
        if (!Objects.equals(id, protDTO.getId())) {
            throw new ResourceException("Invalid ID Object");
        }

        if (!locationRepository.existsById(id)) {
            throw new ResourceException("Entity not found");
        }

        Optional<Object> result = Optional.ofNullable(locationService.partialUpdate(protDTO));

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, protDTO.getId().toString())
        );
    }

    @GetMapping("/")
    public List<LocationDto> getAllLocation() {
        log.debug("REST request to get all Location");
        return locationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable Long id) {
        log.debug("REST request to get Location : {}", id);
        Optional<LocationDto> protDTO = Optional.ofNullable(locationService.findOne(id));
        return ResponseUtil.wrapOrNotFound(protDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJLocation(@PathVariable Long id) {
        log.debug("REST request to delete Location : {}", id);
        locationService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

}
