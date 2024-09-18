package org.example.eventspotlightback.dto.internal.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.eventspotlightback.dto.internal.address.AddressDto;
import org.example.eventspotlightback.dto.internal.category.CategoryDto;
import org.example.eventspotlightback.dto.internal.contact.ContactDto;
import org.example.eventspotlightback.dto.internal.description.DescriptionDto;
import org.example.eventspotlightback.dto.internal.photo.PhotoDto;

@Data
@Accessors(chain = true)
public class EventDto {
    private Long id;
    private String title;
    private DescriptionDto description;
    private Long userId;
    private ContactDto contact;
    private AddressDto address;
    private Set<PhotoDto> photos = new HashSet<>();
    private Set<CategoryDto> categories = new HashSet<>();
    private LocalDateTime startTime;
    private BigDecimal price;
    private boolean isOnline;
}
