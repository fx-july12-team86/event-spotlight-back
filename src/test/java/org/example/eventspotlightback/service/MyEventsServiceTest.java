package org.example.eventspotlightback.service;

import static org.example.eventspotlightback.utils.AddressTestUtil.testAddress;
import static org.example.eventspotlightback.utils.ContactTestUtil.testContact;
import static org.example.eventspotlightback.utils.DescriptionTestUtil.testDescription;
import static org.example.eventspotlightback.utils.EventTestUtil.TEST_EVENT_ID;
import static org.example.eventspotlightback.utils.EventTestUtil.TEST_EVENT_PRICE;
import static org.example.eventspotlightback.utils.EventTestUtil.TEST_EVENT_START_TIME;
import static org.example.eventspotlightback.utils.EventTestUtil.TEST_EVENT_TITLE;
import static org.example.eventspotlightback.utils.EventTestUtil.testSimpleEventDto;
import static org.example.eventspotlightback.utils.FavoriteTestUtil.TEST_FAVORITE_ID;
import static org.example.eventspotlightback.utils.MyEventsTestUtil.TEST_MY_EVENTS_ID;
import static org.example.eventspotlightback.utils.MyEventsTestUtil.testMyEvents;
import static org.example.eventspotlightback.utils.MyEventsTestUtil.testMyEventsDto;
import static org.example.eventspotlightback.utils.UserTestUtil.TEST_USER_ID;
import static org.example.eventspotlightback.utils.UserTestUtil.TEST_USER_NAME;
import static org.example.eventspotlightback.utils.UserTestUtil.TEST_USER_PASSWORD;
import static org.example.eventspotlightback.utils.UserTestUtil.testUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.example.eventspotlightback.dto.internal.my.events.MyEventsDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.MyEventsMapper;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.model.MyEvents;
import org.example.eventspotlightback.model.User;
import org.example.eventspotlightback.repository.EventRepository;
import org.example.eventspotlightback.repository.MyEventsRepository;
import org.example.eventspotlightback.service.event.EventService;
import org.example.eventspotlightback.service.my.events.MyEventsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MyEventsServiceTest {
    @Mock
    private EventService eventService;
    @Mock
    private MyEventsRepository myEventsRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private MyEventsMapper myEventsMapper;
    @InjectMocks
    private MyEventsServiceImpl myEventsService;

    @Test
    @DisplayName("Create MyEvents with User")
    public void createMyEvents_User_Void() {
        //Given
        User testUser = new User();
        MyEvents testMyEvents = new MyEvents()
                .setId(TEST_MY_EVENTS_ID)
                .setUser(testUser);
        when(myEventsRepository.save(testMyEvents)).thenReturn(testMyEvents);

        //Then
        myEventsService.createMyEvents(testUser);
        verify(myEventsRepository, times(1)).save(testMyEvents);
    }

    @Test
    @DisplayName("Find Favorite by User id")
    public void findMyEventsByUserId_UserId_MyEventsDto() {
        //Given
        when(myEventsRepository.findMyEventsById(TEST_USER_ID))
                .thenReturn(Optional.of(testMyEvents));
        when(myEventsMapper.toDto(testMyEvents)).thenReturn(testMyEventsDto);

        //Then
        MyEventsDto actual = myEventsService.findMyEventsById(TEST_USER_ID);
        verify(myEventsRepository, times(1)).findMyEventsById(TEST_USER_ID);
        verify(myEventsMapper, times(1)).toDto(testMyEvents);
    }

    @Test
    @DisplayName("Find MyEvents by not existing User id")
    public void findFavoriteByUserId_invalidUserId_FavoriteDto() {
        //Given
        when(myEventsRepository.findMyEventsById(TEST_USER_ID)).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> myEventsService.findMyEventsById(TEST_USER_ID)
        );
        String expected = "Can't find MyEvents by userId: " + TEST_USER_ID;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
        verify(myEventsRepository, times(1)).findMyEventsById(TEST_USER_ID);
    }

    @Test
    @DisplayName("Add Event to MyEvents")
    public void addEvent_EventIdAndUserId_FavoriteDto() {
        //Given
        MyEvents testMyEvents = new MyEvents()
                .setId(TEST_FAVORITE_ID)
                .setUser(testUser);
        MyEventsDto testMyEventsDto = new MyEventsDto()
                .setId(TEST_FAVORITE_ID)
                .setEventDtos(List.of(testSimpleEventDto));

        Event testEvent = new Event()
                .setId(TEST_EVENT_ID)
                .setTitle(TEST_EVENT_TITLE)
                .setStartTime(TEST_EVENT_START_TIME)
                .setPrice(TEST_EVENT_PRICE)
                .setContact(testContact)
                .setAddress(testAddress)
                .setDescription(testDescription)
                .setUser(testUser)
                .setIsAccepted(false);

        when(myEventsRepository.findMyEventsById(TEST_USER_ID))
                .thenReturn(Optional.of(testMyEvents));

        when(eventRepository.findEventWithMyEventsById(TEST_EVENT_ID))
                .thenReturn(Optional.of(testEvent));

        when(myEventsRepository.save(any(MyEvents.class))).thenReturn(testMyEvents);
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);
        when(myEventsMapper.toDto(testMyEvents)).thenReturn(testMyEventsDto);

        //When
        MyEventsDto expected = testMyEventsDto;
        MyEventsDto actual = myEventsService.addEvent(TEST_EVENT_ID, TEST_USER_ID);

        //Then
        assertEquals(expected, actual);
        assertTrue(testMyEvents.getEvents().contains(testEvent));
        assertTrue(testEvent.getMyEvents().contains(testMyEvents));

        verify(myEventsRepository, times(1)).save(testMyEvents);
        verify(eventRepository, times(1)).save(testEvent);
        verify(myEventsMapper, times(1)).toDto(testMyEvents);
    }

    @Test
    @DisplayName("Add Event to MyEvents with invalid User id")
    public void addEvent_EventIdAndInvalidUserId_EntityNotFoundException() {
        //Given
        when(myEventsRepository.findMyEventsById(TEST_USER_ID)).thenReturn(Optional.empty());
        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> myEventsService.addEvent(TEST_EVENT_ID, TEST_USER_ID)
        );
        String expected = "Can't find MyEvents by userId: " + TEST_USER_ID;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Add Event to MyEvents with invalid Event id")
    public void addEvent_InvalidEventIdAndUserId_EntityNotFoundException() {
        //Given
        when(myEventsRepository.findMyEventsById(TEST_USER_ID))
                .thenReturn(Optional.of(testMyEvents));

        when(eventRepository.findEventWithMyEventsById(TEST_EVENT_ID))
                .thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> myEventsService.addEvent(TEST_EVENT_ID, TEST_USER_ID)
        );
        String expected = "Can't find event with id: " + TEST_EVENT_ID;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Remove Event from MyEvents by User owner")
    public void removeEvent_EventIdAndUserOwner_FavoriteDto() {
        //Given
        MyEvents testMyEvents = new MyEvents()
                .setId(TEST_MY_EVENTS_ID)
                .setUser(testUser);

        Event testEvent = new Event()
                .setId(TEST_EVENT_ID)
                .setTitle(TEST_EVENT_TITLE)
                .setStartTime(TEST_EVENT_START_TIME)
                .setPrice(TEST_EVENT_PRICE)
                .setContact(testContact)
                .setAddress(testAddress)
                .setDescription(testDescription)
                .setUser(testUser)
                .setIsAccepted(false);

        testMyEvents.getEvents().add(testEvent);
        testEvent.getMyEvents().add(testMyEvents);

        MyEventsDto testMyEventsDto = new MyEventsDto()
                .setId(TEST_MY_EVENTS_ID)
                .setEventDtos(Collections.emptyList());

        when(myEventsRepository.findMyEventsById(TEST_USER_ID))
                .thenReturn(Optional.of(testMyEvents));

        when(eventRepository.findEventWithMyEventsById(TEST_EVENT_ID))
                .thenReturn(Optional.of(testEvent));

        when(myEventsRepository.save(any(MyEvents.class))).thenReturn(testMyEvents);
        when(myEventsMapper.toDto(testMyEvents)).thenReturn(testMyEventsDto);
        testEvent.getMyEvents().remove(testMyEvents);

        //When
        MyEventsDto expected = testMyEventsDto;
        MyEventsDto actual = myEventsService.removeEventFromMyEvents(TEST_EVENT_ID, testUser);

        //Then
        assertEquals(expected, actual);
        assertTrue(testMyEvents.getEvents().isEmpty());
        assertTrue(testEvent.getMyEvents().isEmpty());

        verify(myEventsRepository, times(1)).save(testMyEvents);
        verify(eventService, times(1)).deleteEventById(TEST_EVENT_ID);
        verify(myEventsMapper, times(1)).toDto(testMyEvents);
    }

    @Test
    @DisplayName("Remove Event from MyEvents by User co-owner")
    public void removeEvent_EventIdAndUser_FavoriteDto() {
        //Given
        MyEvents testMyEvents = new MyEvents()
                .setId(TEST_MY_EVENTS_ID)
                .setUser(testUser);

        User testUser2 = new User()
                .setId(99L)
                .setUserName(TEST_USER_NAME)
                .setEmail("testEmail@gmail,com")
                .setPassword(TEST_USER_PASSWORD);

        Event testEvent = new Event()
                .setId(TEST_EVENT_ID)
                .setTitle(TEST_EVENT_TITLE)
                .setStartTime(TEST_EVENT_START_TIME)
                .setPrice(TEST_EVENT_PRICE)
                .setContact(testContact)
                .setAddress(testAddress)
                .setDescription(testDescription)
                .setUser(testUser2)
                .setIsAccepted(false);

        testMyEvents.getEvents().add(testEvent);
        testEvent.getMyEvents().add(testMyEvents);

        MyEventsDto testMyEventsDto = new MyEventsDto()
                .setId(TEST_MY_EVENTS_ID)
                .setEventDtos(Collections.emptyList());

        when(myEventsRepository.findMyEventsById(TEST_USER_ID))
                .thenReturn(Optional.of(testMyEvents));

        when(eventRepository.findEventWithMyEventsById(TEST_EVENT_ID))
                .thenReturn(Optional.of(testEvent));

        when(myEventsRepository.save(any(MyEvents.class))).thenReturn(testMyEvents);
        when(myEventsMapper.toDto(testMyEvents)).thenReturn(testMyEventsDto);

        //When
        MyEventsDto expected = testMyEventsDto;
        MyEventsDto actual = myEventsService.removeEventFromMyEvents(TEST_EVENT_ID, testUser);

        //Then
        assertEquals(expected, actual);
        assertTrue(testMyEvents.getEvents().isEmpty());
        assertTrue(testEvent.getMyEvents().isEmpty());

        verify(myEventsRepository, times(1)).save(testMyEvents);
        verify(eventRepository, times(1)).save(testEvent);
        verify(myEventsMapper, times(1)).toDto(testMyEvents);
    }

    @Test
    @DisplayName("Remove Event from MyEvents with invalid User id")
    public void removeEvent_EventIdAndInvalidUserId_EntityNotFoundException() {
        //Given
        when(myEventsRepository.findMyEventsById(TEST_USER_ID)).thenReturn(Optional.empty());
        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> myEventsService.removeEventFromMyEvents(TEST_EVENT_ID, testUser)
        );
        String expected = "Can't find MyEvents by userId: " + TEST_USER_ID;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Remove Event from MyEvents with invalid Event id")
    public void removeEvent_InvalidEventIdAndUserId_EntityNotFoundException() {
        //Given
        when(myEventsRepository.findMyEventsById(TEST_USER_ID))
                .thenReturn(Optional.of(testMyEvents));

        when(eventRepository.findEventWithMyEventsById(TEST_EVENT_ID))
                .thenReturn(Optional.empty());
        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> myEventsService.removeEventFromMyEvents(TEST_EVENT_ID, testUser)
        );
        String expected = "Can't find event with id: " + TEST_EVENT_ID;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }
}
