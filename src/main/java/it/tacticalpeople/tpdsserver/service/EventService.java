package it.tacticalpeople.tpdsserver.service;

import it.tacticalpeople.tpdsserver.coreexception.ResourceException;
import it.tacticalpeople.tpdsserver.domain.Event;
import it.tacticalpeople.tpdsserver.dto.EventDto;
import it.tacticalpeople.tpdsserver.mapper.EventMapper;
import it.tacticalpeople.tpdsserver.repository.EventRepository;
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
public class EventService {

    private final Logger log = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper mapper;

    public Event save(EventDto eventDto) {
        log.debug("Request to save Event: {}", eventDto);
        Event event = EventMapper.fromDtoToEvent(eventDto);
        return eventRepository.save(event);
    }

    public EventDto partialUpdate(Long id, EventDto eventDto) {
        log.debug("EventService - partialUpdate");
        Event event = null;
        try {
            event = eventRepository.findById(id).orElseThrow(() -> new ResourceException("Invalid Id"));
        } catch (ResourceException e) {
            throw new RuntimeException(e);
        }
        event = EventMapper.fromDtoToEvent(eventDto);
        event = eventRepository.save(event);
        return  EventMapper.fromEventToDto(event);
    }
    @Transactional(readOnly = true)
    public List<EventDto> findAll()
    {
        log.debug("Request to get all Events");
        return eventRepository.findAll().stream()
                .map(event -> mapper.fromEventToDto(event))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EventDto findOne(Long id)
    {
        log.debug("Request to get Event : {}", id);
        Optional<Event> event = eventRepository.findById(id);
        EventDto eventDto = null;
        if(event.isPresent())
            eventDto = EventMapper.fromEventToDto(event.get());
        return eventDto;
    }


    public void delete(Long id)
    {
        log.debug("Request to delete Event : {}", id);
        eventRepository.deleteById(id);
    }

}
