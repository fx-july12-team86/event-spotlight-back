package org.example.eventspotlightback.service.event;

import java.util.List;
import org.example.eventspotlightback.dto.internal.event.CreateEventDto;
import org.example.eventspotlightback.dto.internal.event.EventDto;
import org.example.eventspotlightback.dto.internal.event.EventSearchParameters;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDto;
import org.springframework.data.domain.Pageable;

public interface EventService {
    EventDto addEvent(CreateEventDto eventDto);

    EventDto updateEvent(Long id, CreateEventDto event);

    void deleteEventById(Long id);

    SimpleEventDto acceptEvent(Long eventId);

    List<SimpleEventDto> findAllEvents(Pageable pageable);

    EventDto findEventById(Long id);

    List<SimpleEventDto> search(EventSearchParameters eventSearchParameters, Pageable pageable);
}
