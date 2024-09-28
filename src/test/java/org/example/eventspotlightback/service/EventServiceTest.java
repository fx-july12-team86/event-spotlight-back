package org.example.eventspotlightback.service;

import static org.example.eventspotlightback.utils.AddressTestUtil.TEST_ADDRESS_ID;
import static org.example.eventspotlightback.utils.AddressTestUtil.testAddress;
import static org.example.eventspotlightback.utils.AddressTestUtil.testAddressDto;
import static org.example.eventspotlightback.utils.CategoryTestUtil.TEST_CATEGORY_ID;
import static org.example.eventspotlightback.utils.CategoryTestUtil.testCategory;
import static org.example.eventspotlightback.utils.ContactTestUtil.TEST_CONTACT_ID;
import static org.example.eventspotlightback.utils.ContactTestUtil.testContact;
import static org.example.eventspotlightback.utils.ContactTestUtil.testContactDto;
import static org.example.eventspotlightback.utils.DescriptionTestUtil.TEST_DESCRIPTION_ID;
import static org.example.eventspotlightback.utils.DescriptionTestUtil.testDescription;
import static org.example.eventspotlightback.utils.DescriptionTestUtil.testDescriptionDto;
import static org.example.eventspotlightback.utils.EventTestUtil.TEST_EVENT_ID;
import static org.example.eventspotlightback.utils.EventTestUtil.TEST_EVENT_PRICE;
import static org.example.eventspotlightback.utils.EventTestUtil.TEST_EVENT_START_TIME;
import static org.example.eventspotlightback.utils.EventTestUtil.TEST_UPDATE_EVENT_TITLE;
import static org.example.eventspotlightback.utils.EventTestUtil.addEventDto;
import static org.example.eventspotlightback.utils.EventTestUtil.testEvent;
import static org.example.eventspotlightback.utils.EventTestUtil.testEventDto;
import static org.example.eventspotlightback.utils.EventTestUtil.testSimpleEventDto;
import static org.example.eventspotlightback.utils.PhotoTestUtil.TEST_PHOTO_ID;
import static org.example.eventspotlightback.utils.PhotoTestUtil.testPhoto;
import static org.example.eventspotlightback.utils.UserTestUtil.TEST_USER_ID;
import static org.example.eventspotlightback.utils.UserTestUtil.testUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.example.eventspotlightback.dto.internal.event.CreateEventDto;
import org.example.eventspotlightback.dto.internal.event.EventDto;
import org.example.eventspotlightback.dto.internal.event.EventSearchParameters;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.EventMapper;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.model.MyEvents;
import org.example.eventspotlightback.repository.AddressRepository;
import org.example.eventspotlightback.repository.CategoryRepository;
import org.example.eventspotlightback.repository.ContactRepository;
import org.example.eventspotlightback.repository.DescriptionRepository;
import org.example.eventspotlightback.repository.EventRepository;
import org.example.eventspotlightback.repository.MyEventsRepository;
import org.example.eventspotlightback.repository.PhotoRepository;
import org.example.eventspotlightback.repository.UserRepository;
import org.example.eventspotlightback.repository.specification.SpecificationBuilder;
import org.example.eventspotlightback.service.event.EventServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventMapper eventMapper;
    @Mock
    private ContactRepository contactRepository;
    @Mock
    private DescriptionRepository descriptionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private PhotoRepository photoRepository;
    @Mock
    private MyEventsRepository myEventsRepository;
    @Mock
    private SpecificationBuilder<Event> specificationBuilder;
    @InjectMocks
    private EventServiceImpl eventService;

    private Event updatedEvent;
    private EventDto updatedEventDto;
    private CreateEventDto updateEventDto;
    private MyEvents testMyEvents = new MyEvents();

    @BeforeEach
    void setUp() {
        updatedEvent = new Event()
                .setId(TEST_EVENT_ID)
                .setTitle(TEST_UPDATE_EVENT_TITLE)
                .setStartTime(TEST_EVENT_START_TIME)
                .setPrice(TEST_EVENT_PRICE)
                .setContact(testContact)
                .setAddress(testAddress)
                .setDescription(testDescription);
        //Photos
        //Categories

        updatedEventDto = new EventDto()
                .setId(TEST_EVENT_ID)
                .setTitle(TEST_UPDATE_EVENT_TITLE)
                .setStartTime(TEST_EVENT_START_TIME)
                .setPrice(TEST_EVENT_PRICE)
                .setContact(testContactDto)
                .setAddress(testAddressDto)
                .setDescription(testDescriptionDto);
        //Photos
        //Categories

        updateEventDto = new CreateEventDto()
                .setTitle(TEST_UPDATE_EVENT_TITLE)
                .setContactId(TEST_CONTACT_ID)
                .setAddressId(TEST_ADDRESS_ID)
                .setDescriptionId(TEST_DESCRIPTION_ID)
                .setPrice(TEST_EVENT_PRICE)
                .setUserId(TEST_USER_ID)
                .setPhotoIds(Set.of(TEST_PHOTO_ID))
                .setCategoryIds(Set.of(TEST_CATEGORY_ID));

        testMyEvents = new MyEvents()
                .setUser(testUser);
    }

    @Test
    @DisplayName("Create new Event to DB")
    public void addEvent_ValidCreateEventDto_EventDto() {
        //Given
        when(eventMapper.toModel(any(CreateEventDto.class))).thenReturn(testEvent);
        when(photoRepository.findAllByIdIn(anySet())).thenReturn(Set.of(testPhoto));
        when(contactRepository.findById(TEST_CONTACT_ID)).thenReturn(Optional.of(testContact));

        when(descriptionRepository.findById(TEST_DESCRIPTION_ID))
                .thenReturn(Optional.of(testDescription));
        when(userRepository.findById(TEST_USER_ID))
                .thenReturn(Optional.of(testUser));
        when(addressRepository.findById(TEST_ADDRESS_ID))
                .thenReturn(Optional.of(testAddress));

        when(categoryRepository.findAllByIdIn(Set.of(TEST_CATEGORY_ID)))
                .thenReturn(Set.of(testCategory));
        when(myEventsRepository.findMyEventsById(TEST_USER_ID))
                .thenReturn(Optional.of(testMyEvents));
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);
        when(eventMapper.toDto(testEvent)).thenReturn(testEventDto);

        //When
        EventDto expected = testEventDto;
        EventDto actual = eventService.addEvent(addEventDto);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Create Event with not existing Contact id")
    public void addEvent_InvalidContactId_EntityNotFoundException() {
        //Given
        when(eventMapper.toModel(any(CreateEventDto.class))).thenReturn(testEvent);
        when(photoRepository.findAllByIdIn(anySet())).thenReturn(Set.of(testPhoto));
        when(contactRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> eventService.addEvent(addEventDto));
        String expected = "Can't find contact with id: "
                + TEST_CONTACT_ID;
        String actual = exception.getMessage();

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Create Event with not existing Description id")
    public void addEvent_InvalidDescriptionId_EntityNotFoundException() {
        //Given
        when(eventMapper.toModel(any(CreateEventDto.class))).thenReturn(testEvent);
        when(photoRepository.findAllByIdIn(anySet())).thenReturn(Set.of(testPhoto));
        when(contactRepository.findById(TEST_CONTACT_ID)).thenReturn(Optional.of(testContact));
        when(descriptionRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> eventService.addEvent(addEventDto)
        );
        String expected = "Can't find description with id: " + TEST_DESCRIPTION_ID;
        String actual = exception.getMessage();

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Create Event with not existing User id")
    public void addEvent_InvalidUserId_EntityNotFoundException() {
        //Given
        when(eventMapper.toModel(any(CreateEventDto.class))).thenReturn(testEvent);
        when(photoRepository.findAllByIdIn(anySet())).thenReturn(Set.of(testPhoto));
        when(contactRepository.findById(TEST_CONTACT_ID)).thenReturn(Optional.of(testContact));
        when(descriptionRepository.findById(TEST_DESCRIPTION_ID))
                .thenReturn(Optional.of(testDescription));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> eventService.addEvent(addEventDto)
        );
        String expected = "Can't find user with id: " + TEST_USER_ID;
        String actual = exception.getMessage();

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Create Event with not existing Address id")
    public void addEvent_InvalidAddressId_EntityNotFoundException() {
        //Given
        when(eventMapper.toModel(any(CreateEventDto.class))).thenReturn(testEvent);
        when(photoRepository.findAllByIdIn(anySet())).thenReturn(Set.of(testPhoto));
        when(contactRepository.findById(TEST_CONTACT_ID)).thenReturn(Optional.of(testContact));
        when(descriptionRepository.findById(TEST_DESCRIPTION_ID))
                .thenReturn(Optional.of(testDescription));
        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(testUser));
        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> eventService.addEvent(addEventDto)
        );
        String expected = "Can't find address with id: " + TEST_ADDRESS_ID;
        String actual = exception.getMessage();

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Create Event with not existing MyEvents id")
    public void addEvent_InvalidMyEventsId_EntityNotFoundException() {
        //Given
        when(eventMapper.toModel(any(CreateEventDto.class))).thenReturn(testEvent);
        when(photoRepository.findAllByIdIn(anySet())).thenReturn(Set.of(testPhoto));
        when(contactRepository.findById(TEST_CONTACT_ID)).thenReturn(Optional.of(testContact));

        when(descriptionRepository.findById(TEST_DESCRIPTION_ID))
                .thenReturn(Optional.of(testDescription));
        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(testUser));
        when(addressRepository.findById(TEST_ADDRESS_ID)).thenReturn(Optional.of(testAddress));
        when(categoryRepository.findAllByIdIn(Set.of(TEST_CATEGORY_ID)))
                .thenReturn(Set.of(testCategory));
        when(myEventsRepository.findMyEventsById(anyLong())).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> eventService.addEvent(addEventDto)
        );
        String expected = "Can't find user's events with id: " + TEST_USER_ID;
        String actual = exception.getMessage();

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Accept Event with valid Id")
    public void acceptEvent_validEventId_SimpleEventDto() {
        //Given
        when(eventRepository.findById(anyLong()))
                .thenReturn(Optional.of(testEvent.setAccepted(false)));
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);
        when(eventMapper.toSimpleDto(testEvent)).thenReturn(testSimpleEventDto);

        //When
        SimpleEventDto expected = testSimpleEventDto;
        SimpleEventDto actual = eventService.acceptEvent(TEST_EVENT_ID);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Accept Event - invalid Id")
    public void acceptEvent_invalidEventId_EntityNotFoundException() {
        //Given
        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> eventService.acceptEvent(TEST_EVENT_ID)
        );
        String expected = "Can't find event with id: " + TEST_EVENT_ID;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update Event")
    public void updateEvent_ValidUpdateRequest_EventDto() {
        //Given
        when(eventRepository.findById(TEST_EVENT_ID)).thenReturn(Optional.of(testEvent));
        when(photoRepository.findAllByIdIn(anySet())).thenReturn(Set.of(testPhoto));
        when(contactRepository.findById(TEST_CONTACT_ID)).thenReturn(Optional.of(testContact));

        when(descriptionRepository.findById(TEST_DESCRIPTION_ID))
                .thenReturn(Optional.of(testDescription));
        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(testUser));
        when(addressRepository.findById(TEST_ADDRESS_ID)).thenReturn(Optional.of(testAddress));

        when(categoryRepository.findAllByIdIn(Set.of(TEST_CATEGORY_ID)))
                .thenReturn(Set.of(testCategory));
        when(eventRepository.save(any(Event.class))).thenReturn(updatedEvent);
        when(eventMapper.toDto(updatedEvent)).thenReturn(updatedEventDto);

        //When
        EventDto expected = updatedEventDto;
        EventDto actual = eventService.updateEvent(TEST_EVENT_ID, updateEventDto);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update Event with not existing Event id")
    public void updateEvent_InvalidEventId_EntityNotFoundException() {
        //Given
        when(eventRepository.findById(TEST_EVENT_ID)).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> eventService.updateEvent(TEST_EVENT_ID, updateEventDto)
        );
        String expected = "Can't find event with id: " + TEST_EVENT_ID;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete Description by Id - valid")
    public void deleteById_DescriptionId_empty() {
        eventService.deleteEventById(TEST_EVENT_ID);
        verify(eventRepository, times(1)).deleteById(TEST_EVENT_ID);
    }

    @Test
    @DisplayName("Find all events")
    public void findAllEvents_Empty_ListOfSimpleEventDto() {
        //Given
        Pageable pageable = PageRequest.of(0,1);
        List<Event> testEventList = List.of(testEvent);
        Page<Event> eventsPage = new PageImpl<>(testEventList, pageable, testEventList.size());

        when(eventRepository.findAll(pageable)).thenReturn(eventsPage);
        when(eventMapper.toSimpleDto(testEventList)).thenReturn(List.of(testSimpleEventDto));

        //When
        List<SimpleEventDto> expected = List.of(testSimpleEventDto);
        List<SimpleEventDto> actual = eventService.findAllEvents(pageable);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find Event by id")
    public void findEventById_EventId_EventDto() {
        //Given
        when(eventRepository.findById(TEST_EVENT_ID)).thenReturn(Optional.of(testEvent));
        when(eventMapper.toDto(testEvent)).thenReturn(testEventDto);

        //When
        EventDto expected = testEventDto;
        EventDto actual = eventService.findEventById(TEST_EVENT_ID);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find Event by id with not existing id")
    public void findEventById_InvalidEventId_EntityNotFoundException() {
        //Given
        when(eventRepository.findById(TEST_EVENT_ID)).thenReturn(Optional.empty());

        //When
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> eventService.findEventById(TEST_EVENT_ID)
        );
        String expected = "Can't find event with id: " + TEST_EVENT_ID;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("")
    public void search_ValidParams_ListSimpleEventDto() {
        //Given
        String[] cities = {"TestCityName"};
        EventSearchParameters params = new EventSearchParameters(
                null, null, null, cities
        );
        Specification<Event> specification = Specification.where(null);
        Pageable pageable = PageRequest.of(0, 10);

        when(specificationBuilder.build(params)).thenReturn(specification);
        when(eventRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(testEvent)));
        when(eventMapper.toSimpleDto(any(Event.class))).thenReturn(testSimpleEventDto);

        //When
        List<SimpleEventDto> expected = List.of(testSimpleEventDto);
        List<SimpleEventDto> actual = eventService.search(params, pageable);

        //Then
        assertEquals(expected, actual);
    }
}
