package org.example.eventspotlightback.dto.internal.description;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateDescriptionDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
