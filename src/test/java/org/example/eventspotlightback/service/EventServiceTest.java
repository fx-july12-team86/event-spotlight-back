package org.example.eventspotlightback.service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.example.eventspotlightback.dto.internal.address.AddressDto;
import org.example.eventspotlightback.dto.internal.contact.ContactDto;
import org.example.eventspotlightback.dto.internal.description.DescriptionDto;
import org.example.eventspotlightback.dto.internal.event.CreateEventDto;
import org.example.eventspotlightback.dto.internal.event.EventDto;
import org.example.eventspotlightback.mapper.EventMapper;
import org.example.eventspotlightback.model.Address;
import org.example.eventspotlightback.model.City;
import org.example.eventspotlightback.model.Contact;
import org.example.eventspotlightback.model.Description;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.model.MyEvents;
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
import org.example.eventspotlightback.service.event.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    private static final Long TEST_EVENT_ID = 1L;
    private static final String TEST_EVENT_TITLE = "Test Event Title";
    private static final String TEST_UPDATE_EVENT_TITLE = "Test Updated Event Title";
    private static final LocalDateTime TEST_EVENT_START_TIME = LocalDateTime
            .of(2024, 11, 19, 6, 0);
    private static final BigDecimal TEST_EVENT_PRICE = BigDecimal.valueOf(100.0);

    private static final Long TEST_CITY_ID = 1L;
    private static final String TEST_CITY_NAME = "Test City Name";

    private static final Long TEST_ADDRESS_ID = 1L;
    private static final String TEST_ADDRESS_STREET = "TestStreet";
    private static final String TEST_ADDRESS_NUMBER = "TestNumber";

    private static final Long TEST_CONTACT_ID = 1L;
    private static final String TEST_CONTACT_PHONE_NUMBER = "380961111111";

    private static final Long TEST_DESCRIPTION_ID = 1L;
    private static final String TEST_DESCRIPTION_TITLE = "Test Description Title";
    private static final String TEST_DESCRIPTION_TEXT = "Test Description Text";

    private static final Long TEST_USER_ID = 1L;
    private static final String TEST_USER_NAME = "Test User Name";
    private static final String TEST_USER_EMAIL = "Test User Email";
    private static final String TEST_USER_PASSWORD = "Test User Password";

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

    private Event testEvent;
    private Event updatedEvent;
    private EventDto testEventDto;
    private EventDto updatedEventDto;
    private CreateEventDto addEventDto;
    private CreateEventDto updateEventDto;

    private Contact testContact;
    private ContactDto testContactDto;
    private Address testAddress;
    private AddressDto testAddressDto;
    private City testCity;
    private Description testDescription;
    private DescriptionDto testDescriptionDto;
    private User testUser;
    private MyEvents testMyEvents = new MyEvents();

    @BeforeEach
    void setUp() {
        testCity = new City()
                .setId(TEST_CITY_ID)
                .setName(TEST_CITY_NAME);

        testAddress = new Address()
                .setId(TEST_ADDRESS_ID)
                .setStreet(TEST_ADDRESS_STREET)
                .setNumber(TEST_ADDRESS_NUMBER)
                .setCity(testCity);

        testContact = new Contact()
                .setId(TEST_CONTACT_ID)
                .setPhoneNumber(TEST_CONTACT_PHONE_NUMBER);

        testDescription = new Description()
                .setId(TEST_DESCRIPTION_ID)
                .setTitle(TEST_DESCRIPTION_TITLE)
                .setDescription(TEST_DESCRIPTION_TEXT);

        testUser = new User()
                .setId(TEST_USER_ID)
                .setUserName(TEST_USER_NAME)
                .setEmail(TEST_USER_EMAIL)
                .setPassword(TEST_USER_PASSWORD);

        testEvent = new Event()
                .setId(TEST_EVENT_ID)
                .setTitle(TEST_EVENT_TITLE)
                .setStartTime(TEST_EVENT_START_TIME)
                .setPrice(TEST_EVENT_PRICE)
                .setContact(testContact)
                .setAddress(testAddress)
                .setDescription(testDescription)
                .setUser(testUser);

        updatedEvent = new Event()
                .setId(TEST_EVENT_ID)
                .setTitle(TEST_UPDATE_EVENT_TITLE)
                .setStartTime(TEST_EVENT_START_TIME)
                .setPrice(TEST_EVENT_PRICE)
                .setContact(testContact)
                .setAddress(testAddress);

        testAddressDto = new AddressDto()
                .setId(TEST_ADDRESS_ID)
                .setStreet(TEST_ADDRESS_STREET)
                .setNumber(TEST_ADDRESS_NUMBER)
                .setCityId(TEST_CITY_ID);

        testContactDto = new ContactDto()
                .setId(TEST_CONTACT_ID)
                .setPhoneNumber(TEST_CONTACT_PHONE_NUMBER);

        testDescriptionDto = new DescriptionDto()
                .setId(TEST_DESCRIPTION_ID)
                .setTitle(TEST_DESCRIPTION_TITLE)
                .setDescription(TEST_DESCRIPTION_TEXT);

        testEventDto = new EventDto()
                .setId(TEST_EVENT_ID)
                .setTitle(TEST_EVENT_TITLE)
                .setStartTime(TEST_EVENT_START_TIME)
                .setPrice(TEST_EVENT_PRICE)
                .setContact(testContactDto)
                .setAddress(testAddressDto)
                .setUserId(TEST_USER_ID)
                .setDescription(testDescriptionDto);

        updatedEventDto = new EventDto()
                .setId(TEST_EVENT_ID)
                .setTitle(TEST_UPDATE_EVENT_TITLE)
                .setStartTime(TEST_EVENT_START_TIME)
                .setPrice(TEST_EVENT_PRICE)
                .setContact(testContactDto)
                .setAddress(testAddressDto);

        addEventDto = new CreateEventDto()
                .setTitle(TEST_EVENT_TITLE)
                .setContactId(TEST_CONTACT_ID)
                .setAddressId(TEST_ADDRESS_ID)
                .setPrice(TEST_EVENT_PRICE);

        updateEventDto = new CreateEventDto()
                .setTitle(TEST_UPDATE_EVENT_TITLE)
                .setContactId(TEST_CONTACT_ID)
                .setAddressId(TEST_ADDRESS_ID)
                .setPrice(TEST_EVENT_PRICE);
    }

    @Test
    void addEvent_ValidCreateEventDto_EventDto() {
        //Given
        when(photoRepository.findAllByIdIn(anySet())).thenReturn(Collections.emptySet());
        when(eventMapper.toModel(any(CreateEventDto.class))).thenReturn(testEvent);
        when(contactRepository.findById(TEST_CONTACT_ID)).thenReturn(Optional.of(testContact));
        when(descriptionRepository.findById(TEST_DESCRIPTION_ID)).thenReturn(Optional.of(testDescription));
        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(testUser));
        when(addressRepository.findById(TEST_ADDRESS_ID)).thenReturn(Optional.of(testAddress));
        when(categoryRepository.findAllByIdIn(anySet())).thenReturn(Collections.emptySet());
        when(myEventsRepository.findMyEventsById(TEST_USER_ID)).thenReturn(Optional.of(testMyEvents));

        //When


        //Then
    }



}
