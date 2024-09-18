package org.example.eventspotlightback.dto.internal.city;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CityDto {
    private Long id;
    private String name;
}
