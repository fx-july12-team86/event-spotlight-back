package org.example.eventspotlightback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.my.events.AddMyEventDto;
import org.example.eventspotlightback.dto.internal.my.events.MyEventsDto;
import org.example.eventspotlightback.model.User;
import org.example.eventspotlightback.service.my.events.MyEventsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User's events management", description = "Endpoint for managing User's events")
@RestController
@RequiredArgsConstructor
@RequestMapping("/my_events")
public class MyEventsController {
    private final MyEventsService myEventsService;

    @Operation(
            summary = "Find all User's events"
    )
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public MyEventsDto getMyEvents(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return myEventsService.findMyEventsById(user.getId());
    }

    @Operation(
            summary = "Add Events to User's events"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public MyEventsDto addMyEvent(@RequestBody @Valid AddMyEventDto myEventDto) {
        return myEventsService.addEvent(myEventDto.getEventId(), myEventDto.getUserId());
    }

    @Operation(
            summary = "Remove Event from User's events"
    )
    @PreAuthorize("hasAnyAuthority('USER')")
    @DeleteMapping("/{eventId}")
    public MyEventsDto deleteEventFromMyEvent(
            Authentication authentication,
            @PathVariable Long eventId
    ) {
        User user = (User) authentication.getPrincipal();
        return myEventsService.removeEventFromMyEvents(eventId, user);
    }
}
