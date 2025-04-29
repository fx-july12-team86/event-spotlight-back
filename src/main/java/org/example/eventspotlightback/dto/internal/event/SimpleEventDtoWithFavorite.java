package org.example.eventspotlightback.dto.internal.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.eventspotlightback.dto.internal.address.AddressDto;
import org.example.eventspotlightback.dto.internal.photo.PhotoDto;

@Data
@Accessors(chain = true)
public class SimpleEventDtoWithFavorite implements EventAdditionalDataHolder {
    private Long id;
    private String title;
    private Long userId;
    private AddressDto address;
    private PhotoDto photo;
    private String categoryName;
    private LocalDateTime startTime;
    private BigDecimal price;
    private Boolean isOnline;
    private Boolean isFavorite = false;

    @Override
    public SimpleEventDtoWithFavorite setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    @Override
    public SimpleEventDtoWithFavorite setPhoto(PhotoDto photoDto) {
        this.photo = photoDto;
        return this;
    }
}
