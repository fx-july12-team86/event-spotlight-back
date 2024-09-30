package org.example.eventspotlightback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.event.CreateEventDto;
import org.example.eventspotlightback.dto.internal.event.EventDto;
import org.example.eventspotlightback.dto.internal.event.EventSearchParameters;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDto;
import org.example.eventspotlightback.service.event.EventService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Event management", description = "Endpoint for managing Events")
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    @Operation(
            summary = "Accept existing Event"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/accept/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SimpleEventDto acceptEvent(@PathVariable Long id) {
        return eventService.acceptEvent(id);
    }

    @Operation(
            summary = "Delete Event"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEventById(id);
    }

    @Operation(
            summary = "Add new Event"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto addEvent(@RequestBody @Valid CreateEventDto createEventDto) {
        return eventService.addEvent(createEventDto);
    }

    @Operation(
            summary = "Update exists Event"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public EventDto updateEvent(
            @PathVariable long id,
            @RequestBody @Valid CreateEventDto eventDto) {
        return eventService.updateEvent(id, eventDto);
    }

    @Operation(
            summary = "Find all Events"
    )
    @GetMapping
    public List<SimpleEventDto> getAllEvents(Pageable pageable) {
        return eventService.findAllEvents(pageable);
    }

    @Operation(
            summary = "Find Event by id"
    )
    @GetMapping("/{id}")
    public EventDto findEventById(@PathVariable Long id) {
        return eventService.findEventById(id);
    }

    @Operation(
            summary = "Search Event with parameters"
    )
    @GetMapping("/search")
    public List<SimpleEventDto> searchEvents(
            Pageable pageable,
            EventSearchParameters eventSearchParameters
    ) {
        return eventService.search(eventSearchParameters, pageable);
    }
}
