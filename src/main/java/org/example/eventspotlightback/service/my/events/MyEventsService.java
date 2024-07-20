package org.example.eventspotlightback.service.my.events;

import org.example.eventspotlightback.dto.internal.my.events.MyEventsDto;
import org.example.eventspotlightback.model.MyEvents;
import org.example.eventspotlightback.model.User;

public interface MyEventsService {

    MyEvents createMyEvents(User user);

    MyEventsDto findMyEventsById(long userId);

    MyEventsDto addEvent(long eventId, long userId);

    MyEventsDto removeEventFromMyEvents(long eventId, long userId);
}
