package org.example.eventspotlightback.dto.internal.description;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DescriptionDto {
    private long id;
    private String title;
    private String description;
}
