package org.example.eventspotlightback.service.event;

import java.util.List;
import org.example.eventspotlightback.dto.internal.event.CreateEventDto;
import org.example.eventspotlightback.dto.internal.event.EventDto;
import org.example.eventspotlightback.dto.internal.event.EventResponseDto;
import org.example.eventspotlightback.dto.internal.event.EventSearchParameters;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDto;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDtoWithFavorite;
import org.springframework.data.domain.Pageable;

public interface EventService {
    EventDto addEvent(CreateEventDto eventDto);

    EventDto updateEvent(Long id, CreateEventDto event);

    void deleteEventById(Long id);

    SimpleEventDto acceptEvent(Long eventId);

    List<SimpleEventDtoWithFavorite> findAllEvents(Pageable pageable);

    EventDto findEventById(Long id);

    List<SimpleEventDtoWithFavorite> search(
            EventSearchParameters eventSearchParameters, Pageable pageable);

    EventResponseDto searchEventsGroupedByMonth(
            EventSearchParameters eventSearchParameters,
            Pageable pageable
    );
}
