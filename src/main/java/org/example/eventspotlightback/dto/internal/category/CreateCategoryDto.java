package org.example.eventspotlightback.dto.internal.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateCategoryDto {
    @NotBlank
    private String name;
}
