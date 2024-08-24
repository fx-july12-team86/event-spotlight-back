package org.example.eventspotlightback.dto.internal.description;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Data
public class CreateDescriptionDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    @Size(min = 1, message = "there must be at least one contact")
    private List<String> contacts;
}
