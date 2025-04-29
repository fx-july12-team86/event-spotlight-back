package org.example.eventspotlightback.dto.internal.event;

import org.example.eventspotlightback.dto.internal.photo.PhotoDto;

public interface EventAdditionalDataHolder {

    EventAdditionalDataHolder setCategoryName(String categoryName);

    EventAdditionalDataHolder setPhoto(PhotoDto photoDto);
}

