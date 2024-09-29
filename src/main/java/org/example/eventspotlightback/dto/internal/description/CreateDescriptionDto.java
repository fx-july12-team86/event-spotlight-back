package org.example.eventspotlightback.dto.internal.description;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateDescriptionDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
