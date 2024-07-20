package org.example.eventspotlightback.dto.internal.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.example.eventspotlightback.dto.internal.address.AddressDto;
import org.example.eventspotlightback.dto.internal.category.CategoryDto;
import org.example.eventspotlightback.dto.internal.description.DescriptionDto;
import org.example.eventspotlightback.dto.internal.photo.PhotoDto;

@Data
public class EventDto {
    private long id;
    private String title;
    private DescriptionDto description;
    private long userId;
    private AddressDto address;
    private Set<PhotoDto> photos = new HashSet<>();
    private Set<CategoryDto> categories = new HashSet<>();
    private LocalDateTime startTime;
    private BigDecimal price;
    private boolean isOnline;
}
