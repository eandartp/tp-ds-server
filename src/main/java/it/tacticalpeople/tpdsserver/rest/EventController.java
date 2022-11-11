package it.tacticalpeople.tpdsserver.rest;

import it.tacticalpeople.tpdsserver.coreexception.ResourceException;
import it.tacticalpeople.tpdsserver.domain.Event;
import it.tacticalpeople.tpdsserver.dto.EventDto;
import it.tacticalpeople.tpdsserver.repository.EventRepository;
import it.tacticalpeople.tpdsserver.service.EventService;
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
import java.util.Optional;


@RestController
@RequestMapping("/events")
public class EventController {

    private final Logger log = LoggerFactory.getLogger(EventController.class);

    private static final String ENTITY_NAME = "Event";

    //	@Value("${clientApp.name}")
    private String applicationName;


    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @PostMapping("/")
    public ResponseEntity<Object> createEvent(@RequestBody EventDto protDTO) throws URISyntaxException, ResourceException {
        log.debug("REST request to save Event : {}", protDTO);

        Event result = eventService.save(protDTO);
        return ResponseEntity
                .created(new URI("/events/eventCreate" + result.getIdEvent()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getNameEvent()))
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEvent(@PathVariable(value = "id", required = false) final Long id,
                                             @RequestBody EventDto protDTO)
            throws URISyntaxException, ResourceException {
        log.debug("REST request to update Event: {}, {}", id, protDTO);

        if (!eventRepository.existsById(id))
            throw new ResourceException("Entity not found");

        Event result = eventService.save(protDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
                        protDTO.getNameEvent()))
                .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Object> partialUpdateEvent(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody EventDto protDTO) throws URISyntaxException, ResourceException {
        log.debug("REST request to partially update Event: {}, {}", id, protDTO);
        if (!eventRepository.existsById(id))
            throw new ResourceException("Entity not found");

        Optional<Object> result = Optional.ofNullable(eventService.partialUpdate(id, protDTO));

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, protDTO.getNameEvent()));
    }

    @GetMapping("/")
    public List<EventDto> getAllEvents() {
        log.debug("REST request to get all Events");
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        log.debug("REST request to get Event: {}", id);
        Optional<EventDto> protDTO = Optional.ofNullable((eventService.findOne(id)));
        return ResponseUtil.wrapOrNotFound(protDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProt(@PathVariable Long id) {
        log.debug("REST request to delete Prot: {}", id);
        eventService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

}
