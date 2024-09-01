package org.example.eventspotlightback.service.event;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.event.CreateEventDto;
import org.example.eventspotlightback.dto.internal.event.EventDto;
import org.example.eventspotlightback.dto.internal.event.EventSearchParameters;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.EventMapper;
import org.example.eventspotlightback.model.Address;
import org.example.eventspotlightback.model.Category;
import org.example.eventspotlightback.model.Description;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.model.MyEvents;
import org.example.eventspotlightback.model.Photo;
import org.example.eventspotlightback.model.User;
import org.example.eventspotlightback.repository.AddressRepository;
import org.example.eventspotlightback.repository.CategoryRepository;
import org.example.eventspotlightback.repository.DescriptionRepository;
import org.example.eventspotlightback.repository.EventRepository;
import org.example.eventspotlightback.repository.MyEventsRepository;
import org.example.eventspotlightback.repository.PhotoRepository;
import org.example.eventspotlightback.repository.UserRepository;
import org.example.eventspotlightback.repository.specification.SpecificationBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final DescriptionRepository descriptionRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CategoryRepository categoryRepository;
    private final PhotoRepository photoRepository;
    private final MyEventsRepository myEventsRepository;
    private final SpecificationBuilder<Event> specificationBuilder;

    @Transactional
    @Override
    public EventDto addEvent(CreateEventDto createEventDto) {
        Event newEvent = eventMapper.toModel(createEventDto);
        insertEntitiesFromIds(newEvent, createEventDto);
        Event savedEvent = eventRepository.save(newEvent);
        return eventMapper.toDto(savedEvent);
    }

    @Override
    public SimpleEventDto acceptEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Can't find event with id: " + eventId)
        );
        event.setAccepted(true);
        return eventMapper.toSimpleDto(eventRepository.save(event));
    }

    @Override
    public List<SimpleEventDto> findAllEvents(Pageable pageable) {
        return eventMapper.toSimpleDto(eventRepository.findAll(pageable).toList());
    }

    @Override
    public EventDto findEventById(Long id) {
        return eventMapper.toDto(eventRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find event with id: " + id)
        ));
    }

    @Transactional
    @Override
    public List<SimpleEventDto> search(
            EventSearchParameters eventSearchParameters,
            Pageable pageable
    ) {
        Specification<Event> specification = specificationBuilder.build(eventSearchParameters);
        return eventRepository.findAll(specification, pageable).stream()
                .map(eventMapper::toSimpleDto)
                .toList();
    }

    @Transactional
    @Override
    public EventDto updateEvent(Long id, CreateEventDto updateEventDto) {
        Event updatedEvent = eventMapper.toModel(updateEventDto);
        updatedEvent.setId(id);
        eventRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find event with id: " + id)
        );
        insertEntitiesFromIds(updatedEvent, updateEventDto);
        Event savedEvent = eventRepository.save(updatedEvent);
        return eventMapper.toDto(savedEvent);
    }

    @Override
    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);
    }

    private void insertEntitiesFromIds(Event event, CreateEventDto createEventDto) {
        Set<Photo> eventPhotos = photoRepository.findAllByIdIn(createEventDto.getPhotoIds());
        event.setPhotos(eventPhotos);

        Description eventDescription = descriptionRepository
                .findById(createEventDto.getDescriptionId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find description with id: "
                                + createEventDto.getDescriptionId())
                );
        event.setDescription(eventDescription);

        User eventOwner = userRepository.findById(createEventDto.getUserId()).orElseThrow(
                () -> new EntityNotFoundException("Can't find user with id: "
                        + createEventDto.getUserId())
        );
        event.setUser(eventOwner);

        MyEvents myEvents = myEventsRepository.findMyEventsByUserId(eventOwner.getId()).orElseThrow(
                () -> new EntityNotFoundException("Can't find user with id: "
                        + createEventDto.getUserId())
        );
        myEvents.getEvents().add(event);
        event.getMyEvents().add(myEvents);

        Address eventAddress = addressRepository.findById(createEventDto.getAddressId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find address with id: "
                                + createEventDto.getAddressId())
                );
        event.setAddress(eventAddress);

        Set<Category> eventCategories =
                categoryRepository.findAllByIdIn(createEventDto.getCategoryIds());
        event.setCategories(eventCategories);
    }
}
