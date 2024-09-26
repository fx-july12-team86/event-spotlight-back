package org.example.eventspotlightback.service.my.events;

import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.my.events.MyEventsDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.MyEventsMapper;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.model.MyEvents;
import org.example.eventspotlightback.model.User;
import org.example.eventspotlightback.repository.EventRepository;
import org.example.eventspotlightback.repository.MyEventsRepository;
import org.example.eventspotlightback.service.event.EventService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyEventsServiceImpl implements MyEventsService {
    private final EventService eventService;
    private final MyEventsRepository myEventsRepository;
    private final EventRepository eventRepository;
    private final MyEventsMapper myEventsMapper;

    @Override
    public MyEvents createMyEvents(User user) {
        MyEvents myEvents = new MyEvents();
        myEvents.setUser(user);
        return myEventsRepository.save(myEvents);
    }

    @Override
    public MyEventsDto findMyEventsById(Long userId) {
        return myEventsMapper.toDto(myEventsRepository.findMyEventsById(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find MyEvents by userId: " + userId)
        ));
    }

    @Override
    public MyEventsDto addEvent(Long eventId, Long userId) {
        MyEvents myEvents = myEventsRepository.findMyEventsById(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find MyEvents by userId: " + userId)
        );
        Event newEvent = eventRepository.findEventWithMyEventsById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Can't find event with id: " + eventId)
        );

        myEvents.getEvents().add(newEvent);
        newEvent.getMyEvents().add(myEvents);

        myEventsRepository.save(myEvents);
        eventRepository.save(newEvent);

        return myEventsMapper.toDto(myEvents);
    }

    @Override
    public MyEventsDto removeEventFromMyEvents(Long eventId, User user) {
        MyEvents myEvents = myEventsRepository.findMyEventsById(user.getId()).orElseThrow(
                () -> new EntityNotFoundException("Can't find MyEvents by userId: " + user.getId())
        );
        Event event = eventRepository.findEventWithMyEventsById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Can't find event with id: " + eventId)
        );

        myEvents.getEvents().remove(event);
        myEventsRepository.save(myEvents);

        if (event.getUser().getEmail().equals(user.getEmail())) {
            eventService.deleteEventById(event.getId());
        } else {
            event.getMyEvents().remove(myEvents);
            eventRepository.save(event);
        }

        return myEventsMapper.toDto(myEvents);
    }
}
