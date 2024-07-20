package org.example.eventspotlightback.service.favorite;

import org.example.eventspotlightback.dto.internal.favorite.FavoriteDto;
import org.example.eventspotlightback.model.Favorite;
import org.example.eventspotlightback.model.User;

public interface FavoriteService {
    Favorite createFavorite(User user);

    FavoriteDto findFavoriteByUserId(Long userId);

    FavoriteDto addEvent(Long eventId, Long userId);

    FavoriteDto removeEventFromFavorite(Long eventId, Long userId);
}
