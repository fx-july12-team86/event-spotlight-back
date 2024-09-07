package org.example.eventspotlightback.service.my.events;

import org.example.eventspotlightback.dto.internal.my.events.MyEventsDto;
import org.example.eventspotlightback.model.MyEvents;
import org.example.eventspotlightback.model.User;

public interface MyEventsService {

    MyEvents createMyEvents(User user);

    MyEventsDto findMyEventsById(Long userId);

    MyEventsDto addEvent(Long eventId, Long userId);

    MyEventsDto removeEventFromMyEvents(Long eventId, User userId);
}
