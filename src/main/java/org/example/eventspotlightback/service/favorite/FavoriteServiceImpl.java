package org.example.eventspotlightback.service.favorite;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.favorite.FavoriteDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.FavoriteMapper;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.model.Favorite;
import org.example.eventspotlightback.model.User;
import org.example.eventspotlightback.repository.EventRepository;
import org.example.eventspotlightback.repository.FavoriteRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FavoriteServiceImpl implements FavoriteService {
    private final EventRepository eventRepository;
    private final FavoriteRepository favoriteRepository;
    private final FavoriteMapper favoriteMapper;

    @Override
    public Favorite createFavorite(User user) {
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        return favoriteRepository.save(favorite);
    }

    @Transactional
    @Override
    public FavoriteDto addEvent(Long eventId, Long userId) {
        Favorite favorite = favoriteRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find favorite by user id: " + userId)
        );
        Event newFavoriteEvent = eventRepository.findById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Can't find event with id: " + eventId)
        );
        newFavoriteEvent.getFavorites().add(favorite);
        favorite.getEvents().add(newFavoriteEvent);
        return favoriteMapper.toDto(favorite);
    }

    @Transactional
    @Override
    public FavoriteDto removeEventFromFavorite(Long eventId, Long userId) {
        Favorite favorite = favoriteRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find favorite by user id: " + userId)
        );
        Event removeFavoriteEvent = eventRepository.findById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Can't find event with id: " + eventId)
        );
        removeFavoriteEvent.getFavorites().remove(favorite);
        favorite.getEvents().remove(removeFavoriteEvent);
        return favoriteMapper.toDto(favorite);
    }

    @Transactional
    @Override
    public FavoriteDto findFavoriteByUserId(Long userId) {
        return favoriteMapper.toDto(favoriteRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find favorite by user id: " + userId)
        ));
    }
}
