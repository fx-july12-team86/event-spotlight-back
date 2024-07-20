package org.example.eventspotlightback.dto.internal.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryDto {
    @NotBlank
    private String name;
}
