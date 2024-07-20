package org.example.eventspotlightback.service.event;

import java.util.List;
import org.example.eventspotlightback.dto.internal.event.CreateEventDto;
import org.example.eventspotlightback.dto.internal.event.EventDto;

public interface EventService {
    EventDto addEvent(CreateEventDto eventDto);

    List<EventDto> findAllEvents();

    EventDto findEventById(Long id);

    EventDto updateEvent(Long id, CreateEventDto event);

    void deleteEventById(Long id);
}
