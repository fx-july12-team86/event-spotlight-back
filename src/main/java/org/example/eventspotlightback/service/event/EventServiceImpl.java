package org.example.eventspotlightback.service.event;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.event.CreateEventDto;
import org.example.eventspotlightback.dto.internal.event.EventDto;
import org.example.eventspotlightback.dto.internal.event.EventResponseDto;
import org.example.eventspotlightback.dto.internal.event.EventSearchParameters;
import org.example.eventspotlightback.dto.internal.event.GroupedSimpleEventDto;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDto;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDtoWithFavorite;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.EventMapper;
import org.example.eventspotlightback.model.Address;
import org.example.eventspotlightback.model.Category;
import org.example.eventspotlightback.model.Contact;
import org.example.eventspotlightback.model.Description;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.model.MyEvents;
import org.example.eventspotlightback.model.Photo;
import org.example.eventspotlightback.model.User;
import org.example.eventspotlightback.repository.AddressRepository;
import org.example.eventspotlightback.repository.CategoryRepository;
import org.example.eventspotlightback.repository.ContactRepository;
import org.example.eventspotlightback.repository.DescriptionRepository;
import org.example.eventspotlightback.repository.EventRepository;
import org.example.eventspotlightback.repository.MyEventsRepository;
import org.example.eventspotlightback.repository.PhotoRepository;
import org.example.eventspotlightback.repository.UserRepository;
import org.example.eventspotlightback.repository.specification.SpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private static final Map<Month, String> UKRAINIAN_MONTHS = Map.ofEntries(
            Map.entry(Month.JANUARY, "Січень"),
            Map.entry(Month.FEBRUARY, "Лютий"),
            Map.entry(Month.MARCH, "Березень"),
            Map.entry(Month.APRIL, "Квітень"),
            Map.entry(Month.MAY, "Травень"),
            Map.entry(Month.JUNE, "Червень"),
            Map.entry(Month.JULY, "Липень"),
            Map.entry(Month.AUGUST, "Серпень"),
            Map.entry(Month.SEPTEMBER, "Вересень"),
            Map.entry(Month.OCTOBER, "Жовтень"),
            Map.entry(Month.NOVEMBER, "Листопад"),
            Map.entry(Month.DECEMBER, "Грудень")
    );

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final ContactRepository contactRepository;
    private final DescriptionRepository descriptionRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CategoryRepository categoryRepository;
    private final PhotoRepository photoRepository;
    private final MyEventsRepository myEventsRepository;
    private final SpecificationBuilder<Event> specificationBuilder;

    @Override
    public EventDto addEvent(CreateEventDto createEventDto) {
        Event newEvent = eventMapper.toModel(createEventDto);
        updateModelFromDto(newEvent, createEventDto);
        MyEvents myEvents = myEventsRepository.findMyEventsById(createEventDto.getUserId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find user's events with id: "
                                + createEventDto.getUserId())
                );
        newEvent.getMyEvents().add(myEvents);
        Event savedEvent = eventRepository.save(newEvent);
        return eventMapper.toDto(savedEvent);
    }

    @Transactional
    @Override
    public SimpleEventDto acceptEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Can't find event with id: " + eventId)
        );
        event.setIsAccepted(true);
        return eventMapper.toSimpleDto(eventRepository.save(event));
    }

    @Transactional
    @Override
    public EventDto updateEvent(Long id, CreateEventDto updateEventDto) {
        Event existingEvent = eventRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find event with id: " + id)
        );
        updateModelFromDto(existingEvent, updateEventDto);
        existingEvent.setIsAccepted(false);
        Event savedEvent = eventRepository.save(existingEvent);
        return eventMapper.toDto(savedEvent);
    }

    @Override
    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);
    }
    
    @Override
    public List<SimpleEventDtoWithFavorite> findAllEvents(Pageable pageable) {
        return eventMapper.toSimpleDtoWithFavorite(eventRepository.findAll(pageable).toList());
    }

    @Override
    public EventDto findEventById(Long id) {
        return eventMapper.toDto(eventRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find event with id: " + id)
        ));
    }

    @Override
    public List<SimpleEventDtoWithFavorite> search(
            EventSearchParameters eventSearchParameters,
            Pageable pageable
    ) {
        Specification<Event> specification = specificationBuilder.build(eventSearchParameters);
        return eventRepository.findAll(specification, pageable).stream()
                .map(eventMapper::toSimpleDtoWithFavorite)
                .toList();
    }

    @Override
    public EventResponseDto searchEventsGroupedByMonth(
            EventSearchParameters eventSearchParameters,
            Pageable pageable
    ) {
        Specification<Event> specification = specificationBuilder.build(eventSearchParameters);
        Page<Event> page = eventRepository.findAll(specification, pageable);

        Map<Month, List<SimpleEventDtoWithFavorite>> groupedByMonth = page.getContent()
                .stream()
                .map(eventMapper::toSimpleDtoWithFavorite)
                .collect(Collectors.groupingBy(dto -> dto.getStartTime().getMonth()));

        List<GroupedSimpleEventDto> groupedDtos = groupedByMonth.entrySet()
                .stream()
                .map(entry -> new GroupedSimpleEventDto()
                        .setField(UKRAINIAN_MONTHS.get(entry.getKey()))
                        .setEvents(entry.getValue()))
                .sorted(Comparator.comparing(dto ->
                        dto.getEvents().stream()
                                .map(SimpleEventDtoWithFavorite::getStartTime)
                                .min(Comparator.naturalOrder())
                                .orElse(LocalDateTime.MIN) // Безпечне сортування
                ))
                .toList();

        return new EventResponseDto(page.getTotalPages(), groupedDtos);
    }

    private void updateModelFromDto(Event event, CreateEventDto createEventDto) {
        event.setTitle(createEventDto.getTitle());
        event.setStartTime(createEventDto.getStartTime());
        event.setPrice(createEventDto.getPrice());
        event.setIsOnline(createEventDto.getIsOnline());

        Set<Photo> eventPhotos = photoRepository.findAllByIdIn(createEventDto.getPhotoIds());
        event.setPhotos(eventPhotos);

        Contact eventContact = contactRepository
                .findById(createEventDto.getContactId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find contact with id: "
                                + createEventDto.getContactId())
                );
        event.setContact(eventContact);

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
