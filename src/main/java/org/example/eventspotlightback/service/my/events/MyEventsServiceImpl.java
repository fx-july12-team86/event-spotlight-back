package org.example.eventspotlightback.service.my.events;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.my.events.MyEventsDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.MyEventsMapper;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.model.MyEvents;
import org.example.eventspotlightback.model.User;
import org.example.eventspotlightback.repository.EventRepository;
import org.example.eventspotlightback.repository.MyEventsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyEventsServiceImpl implements MyEventsService {
    private final MyEventsRepository myEventsRepository;
    private final EventRepository eventRepository;
    private final MyEventsMapper myEventsMapper;

    @Override
    public MyEvents createMyEvents(User user) {
        MyEvents myEvents = new MyEvents();
        myEvents.setUser(user);
        return myEventsRepository.save(myEvents);
    }

    @Transactional
    @Override
    public MyEventsDto findMyEventsById(long userId) {
        return myEventsMapper.toDto(myEventsRepository.findMyEventsByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find MyEvents by userId: " + userId)
        ));
    }

    @Transactional
    @Override
    public MyEventsDto addEvent(long eventId, long userId) {
        MyEvents myEvents = myEventsRepository.findMyEventsByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find MyEvents by userId: " + userId)
        );
        Event newEvent = eventRepository.findById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Can't find event with id: " + eventId)
        );
        myEvents.getEvents().add(newEvent);
        return myEventsMapper.toDto(myEventsRepository.save(myEvents));
    }

    @Transactional
    @Override
    public MyEventsDto removeEventFromMyEvents(long eventId, long userId) {
        MyEvents myEvents = myEventsRepository.findMyEventsByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find MyEvents by userId: " + userId)
        );
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Can't find event with id: " + eventId)
        );
        myEvents.getEvents().remove(event);
        return myEventsMapper.toDto(myEventsRepository.save(myEvents));
    }
}
