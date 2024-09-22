package org.example.eventspotlightback.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import org.example.eventspotlightback.dto.internal.event.CreateEventDto;
import org.example.eventspotlightback.dto.internal.event.EventDto;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.example.eventspotlightback.utils.TestUtil.testAddress;
import static org.example.eventspotlightback.utils.TestUtil.testAddressDto;
import static org.example.eventspotlightback.utils.TestUtil.testContact;
import static org.example.eventspotlightback.utils.TestUtil.testContactDto;
import static org.example.eventspotlightback.utils.TestUtil.testDescription;
import static org.example.eventspotlightback.utils.TestUtil.testEvent;
import static org.example.eventspotlightback.utils.TestUtil.testUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;
import static org.example.eventspotlightback.utils.TestUtil.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    private static final String TEST_UPDATE_EVENT_TITLE = "Test Updated Event Title";

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
                .setAddress(testAddress);

        updatedEventDto = new EventDto()
                .setId(TEST_EVENT_ID)
                .setTitle(TEST_UPDATE_EVENT_TITLE)
                .setStartTime(TEST_EVENT_START_TIME)
                .setPrice(TEST_EVENT_PRICE)
                .setContact(testContactDto)
                .setAddress(testAddressDto);

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
