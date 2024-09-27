package org.example.eventspotlightback.utils;

import org.example.eventspotlightback.dto.internal.photo.PhotoDto;
import org.example.eventspotlightback.model.Photo;

import java.time.LocalDateTime;

public class PhotoTestUtil {
    public static final Long TEST_PHOTO_ID = 8L;
    public static final String TEST_PHOTO_PATH = "/test/path";
    public static final LocalDateTime TEST_PHOTO_CREATED_AT = LocalDateTime
            .of(2024, 9, 22, 8, 45, 54);
    public static final String TEST_PHOTO_SHARED_URL = "/url/test/sharing";

    public static final Photo testPhoto = new Photo()
            .setId(TEST_PHOTO_ID)
            .setPath(TEST_PHOTO_PATH)
            .setCreatedAt(TEST_PHOTO_CREATED_AT)
            .setSharedUrl(TEST_PHOTO_SHARED_URL);
    public static final PhotoDto testPhotoDto = new PhotoDto()
            .setId(TEST_PHOTO_ID)
            .setCreatedAt(TEST_PHOTO_CREATED_AT.toString())
            .setSharedUrl(TEST_PHOTO_SHARED_URL);
}
