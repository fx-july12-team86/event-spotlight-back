package org.example.eventspotlightback.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.example.eventspotlightback.dto.internal.favorite.FavoriteDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.FavoriteMapper;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.model.Favorite;
import org.example.eventspotlightback.model.User;
import org.example.eventspotlightback.repository.EventRepository;
import org.example.eventspotlightback.repository.FavoriteRepository;
import org.example.eventspotlightback.service.favorite.FavoriteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.example.eventspotlightback.utils.AddressTestUtil.testAddress;
import static org.example.eventspotlightback.utils.ContactTestUtil.testContact;
import static org.example.eventspotlightback.utils.DescriptionTestUtil.testDescription;
import static org.example.eventspotlightback.utils.EventTestUtil.TEST_EVENT_ID;
import static org.example.eventspotlightback.utils.EventTestUtil.TEST_EVENT_PRICE;
import static org.example.eventspotlightback.utils.EventTestUtil.TEST_EVENT_START_TIME;
import static org.example.eventspotlightback.utils.EventTestUtil.TEST_EVENT_TITLE;
import static org.example.eventspotlightback.utils.EventTestUtil.testSimpleEventDto;
import static org.example.eventspotlightback.utils.FavoriteTestUtil.TEST_FAVORITE_ID;
import static org.example.eventspotlightback.utils.FavoriteTestUtil.testFavorite;
import static org.example.eventspotlightback.utils.FavoriteTestUtil.testFavoriteDto;
import static org.example.eventspotlightback.utils.UserTestUtil.TEST_USER_ID;
import static org.example.eventspotlightback.utils.UserTestUtil.testUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private FavoriteRepository favoriteRepository;
    @Mock
    private FavoriteMapper favoriteMapper;
    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    @Test
    @DisplayName("Create Favorite with User")
    public void createFavorite_User_Void() {
        //Given
        User testUser = new User();
        Favorite testFavorite = new Favorite()
                .setId(TEST_FAVORITE_ID)
                .setUser(testUser);
        when(favoriteRepository.save(testFavorite)).thenReturn(testFavorite);

        //Then
        favoriteService.createFavorite(testUser);
        verify(favoriteRepository, times(1)).save(testFavorite);
    }

    @Test
    @DisplayName("Add Event to Favorite")
    public void addEvent_EventIdAndUserId_FavoriteDto() {
        //Given
        Favorite testFavorite = new Favorite()
                .setId(TEST_FAVORITE_ID)
                .setUser(testUser);
        FavoriteDto testFavoriteDto = new FavoriteDto()
                .setId(TEST_FAVORITE_ID)
                .setEvents(List.of(testSimpleEventDto));

        Event testEvent = new Event()
                .setId(TEST_EVENT_ID)
                .setTitle(TEST_EVENT_TITLE)
                .setStartTime(TEST_EVENT_START_TIME)
                .setPrice(TEST_EVENT_PRICE)
                .setContact(testContact)
                .setAddress(testAddress)
                .setDescription(testDescription)
                .setUser(testUser)
                .setAccepted(false);

        when(favoriteRepository.findByUserId(TEST_USER_ID)).thenReturn(Optional.of(testFavorite));
        when(eventRepository.findEventWithFavoriteById(TEST_EVENT_ID)).thenReturn(Optional.of(testEvent));
        when(favoriteRepository.save(any(Favorite.class))).thenReturn(testFavorite);
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);
        when(favoriteMapper.toDto(testFavorite)).thenReturn(testFavoriteDto);

        //When
        FavoriteDto expected = testFavoriteDto;
        FavoriteDto actual = favoriteService.addEvent(TEST_EVENT_ID, TEST_USER_ID);

        //Then
        assertEquals(expected, actual);
        assertTrue(testFavorite.getEvents().contains(testEvent));
        assertTrue(testEvent.getFavorites().contains(testFavorite));

        verify(favoriteRepository, times(1)).save(testFavorite);
        verify(eventRepository, times(1)).save(testEvent);
        verify(favoriteMapper, times(1)).toDto(testFavorite);
    }

    @Test
    @DisplayName("Add Event to Favorite with invalid User id")
    public void addEvent_EventIdAndInvalidUserId_EntityNotFoundException() {
        //Given
        when(favoriteRepository.findByUserId(TEST_USER_ID)).thenReturn(Optional.empty());
        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> favoriteService.addEvent(TEST_EVENT_ID, TEST_USER_ID)
        );
        String expected = "Can't find favorite by user id: " + TEST_USER_ID;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Add Event to Favorite with invalid Event id")
    public void addEvent_InvalidEventIdAndUserId_EntityNotFoundException() {
        //Given
        when(favoriteRepository.findByUserId(TEST_USER_ID)).thenReturn(Optional.of(testFavorite));
        when(eventRepository.findEventWithFavoriteById(TEST_EVENT_ID)).thenReturn(Optional.empty());
        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> favoriteService.addEvent(TEST_EVENT_ID, TEST_USER_ID)
        );
        String expected = "Can't find event with id: " + TEST_EVENT_ID;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Remove Event from Favorite")
    public void removeEvent_EventIdAndUserId_FavoriteDto() {
        //Given

        Favorite testFavorite = new Favorite()
                .setId(TEST_FAVORITE_ID)
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
                .setAccepted(false);

        testFavorite.getEvents().add(testEvent);
        testEvent.getFavorites().add(testFavorite);


        FavoriteDto testFavoriteDto = new FavoriteDto()
                .setId(TEST_FAVORITE_ID)
                .setEvents(Collections.emptyList());

        when(favoriteRepository.findByUserId(TEST_USER_ID)).thenReturn(Optional.of(testFavorite));
        when(eventRepository.findEventWithFavoriteById(TEST_EVENT_ID)).thenReturn(Optional.of(testEvent));
        when(favoriteRepository.save(any(Favorite.class))).thenReturn(testFavorite);
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);
        when(favoriteMapper.toDto(testFavorite)).thenReturn(testFavoriteDto);

        //When
        FavoriteDto expected = testFavoriteDto;
        FavoriteDto actual = favoriteService.removeEventFromFavorite(TEST_EVENT_ID, TEST_USER_ID);

        //Then
        assertEquals(expected, actual);
        assertTrue(testFavorite.getEvents().isEmpty());
        assertTrue(testEvent.getFavorites().isEmpty());

        verify(favoriteRepository, times(1)).save(testFavorite);
        verify(eventRepository, times(1)).save(testEvent);
        verify(favoriteMapper, times(1)).toDto(testFavorite);
    }

    @Test
    @DisplayName("Remove Event from Favorite with invalid User id")
    public void removeEvent_EventIdAndInvalidUserId_EntityNotFoundException() {
        //Given
        when(favoriteRepository.findByUserId(TEST_USER_ID)).thenReturn(Optional.empty());
        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> favoriteService.addEvent(TEST_EVENT_ID, TEST_USER_ID)
        );
        String expected = "Can't find favorite by user id: " + TEST_USER_ID;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Remove Event from Favorite with invalid Event id")
    public void removeEvent_InvalidEventIdAndUserId_EntityNotFoundException() {
        //Given
        when(favoriteRepository.findByUserId(TEST_USER_ID)).thenReturn(Optional.of(testFavorite));
        when(eventRepository.findEventWithFavoriteById(TEST_EVENT_ID)).thenReturn(Optional.empty());
        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> favoriteService.addEvent(TEST_EVENT_ID, TEST_USER_ID)
        );
        String expected = "Can't find event with id: " + TEST_EVENT_ID;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find Favorite by User id")
    public void findFavoriteByUserId_UserId_FavoriteDto() {
        //Given
        when(favoriteRepository.findByUserId(TEST_USER_ID)).thenReturn(Optional.of(testFavorite));
        when(favoriteMapper.toDto(testFavorite)).thenReturn(testFavoriteDto);

        //Then
        FavoriteDto actual = favoriteService.findFavoriteByUserId(TEST_USER_ID);
        verify(favoriteRepository, times(1)).findByUserId(TEST_USER_ID);
        verify(favoriteMapper, times(1)).toDto(testFavorite);
    }


    @Test
    @DisplayName("Find Favorite by not existing User id")
    public void findFavoriteByUserId_invalidUserId_FavoriteDto() {
        //Given
        when(favoriteRepository.findByUserId(TEST_USER_ID)).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> favoriteService.findFavoriteByUserId(TEST_USER_ID)
        );
        String expected = "Can't find favorite by user id: " + TEST_USER_ID;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
        verify(favoriteRepository, times(1)).findByUserId(TEST_USER_ID);
    }
}
