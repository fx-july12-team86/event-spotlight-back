package org.example.eventspotlightback.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.my.events.AddMyEventDto;
import org.example.eventspotlightback.dto.internal.my.events.MyEventsDto;
import org.example.eventspotlightback.service.my.events.MyEventsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my_events")
public class MyEventsController {
    private final MyEventsService myEventsService;

    @GetMapping("/{userId}")
    public MyEventsDto getMyEvents(@PathVariable Long userId) {
        return myEventsService.findMyEventsById(userId);
    }

    @PostMapping
    public MyEventsDto addMyEvent(@RequestBody AddMyEventDto myEventDto) {
        return myEventsService.addEvent(myEventDto.getEventId(), myEventDto.getUserId());
    }

    @DeleteMapping("/{userId}/{eventId}")
    public MyEventsDto deleteMyEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        return myEventsService.removeEventFromMyEvents(eventId, userId);
    }
}
