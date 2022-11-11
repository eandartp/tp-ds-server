package it.tacticalpeople.tpdsserver.mapper;

import it.tacticalpeople.tpdsserver.domain.Event;
import it.tacticalpeople.tpdsserver.dto.EventDto;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class EventMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    public static Event fromDtoToEvent(EventDto eventDto) {
        Event event = new Event();
        event.setNameEvent(eventDto.getNameEvent());
        event.setDateEvent(eventDto.getDateEvent());
        event.setLink(eventDto.getLink());

        return event;
    }

    public static EventDto fromEventToDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setNameEvent(event.getNameEvent());
        eventDto.setDateEvent(event.getDateEvent());
        eventDto.setLink(event.getLink());

        return eventDto;
    }

}
