package org.example.eventspotlightback.utils;

import static org.example.eventspotlightback.utils.UserTestUtil.TEST_USER_ID;
import static org.example.eventspotlightback.utils.UserTestUtil.testUser;

import java.util.Collections;
import org.example.eventspotlightback.dto.internal.favorite.FavoriteDto;
import org.example.eventspotlightback.model.Favorite;

public class FavoriteTestUtil {
    public static final Long TEST_FAVORITE_ID = TEST_USER_ID;

    public static final Favorite testFavorite = new Favorite()
            .setId(TEST_FAVORITE_ID)
            .setUser(testUser);
    public static final FavoriteDto testFavoriteDto = new FavoriteDto()
            .setId(TEST_FAVORITE_ID)
            .setEvents(Collections.emptyList());
}
