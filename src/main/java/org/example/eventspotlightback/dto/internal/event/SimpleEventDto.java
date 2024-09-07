package org.example.eventspotlightback.dto.internal.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.Data;
import org.example.eventspotlightback.dto.internal.address.AddressDto;

@Data
public class SimpleEventDto {
    private Long id;
    private String title;
    private AddressDto address;
    private List<Long> photosIds;
    private Set<Long> categoryIds;
    private LocalDateTime startTime;
    private BigDecimal price;
}
