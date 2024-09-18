package org.example.eventspotlightback.dto.internal.category;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryDto {
    private long id;
    private String name;
}
