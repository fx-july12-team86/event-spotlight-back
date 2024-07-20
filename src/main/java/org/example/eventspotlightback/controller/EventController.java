package org.example.eventspotlightback.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.event.CreateEventDto;
import org.example.eventspotlightback.dto.internal.event.EventDto;
import org.example.eventspotlightback.service.event.EventService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    @PostMapping
    public EventDto postEvent(@RequestBody CreateEventDto createEventDto) {
        return eventService.addEvent(createEventDto);
    }

    @GetMapping
    public List<EventDto> getAllEvents() {
        return eventService.findAllEvents();
    }

    @GetMapping("/{id}")
    public EventDto findEventById(@PathVariable long id) {
        return eventService.findEventById(id);
    }

    @PutMapping("/{id}")
    public EventDto updateEvent(
            @PathVariable long id,
            @RequestBody CreateEventDto eventDto) {
        return eventService.updateEvent(id, eventDto);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable long id) {
        eventService.deleteEventById(id);
    }
}
