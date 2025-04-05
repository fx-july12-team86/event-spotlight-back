package org.example.eventspotlightback.dto.internal.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.eventspotlightback.dto.internal.address.AddressDto;
import org.example.eventspotlightback.dto.internal.category.CategoryDto;
import org.example.eventspotlightback.dto.internal.photo.PhotoDto;

@Data
@Accessors(chain = true)
public class SimpleEventDto {
    private Long id;
    private String title;
    private Long userId;
    private AddressDto address;
    private PhotoDto photo;
    private CategoryDto category;
    private LocalDateTime startTime;
    private BigDecimal price;
    private Boolean isOnline;
}
