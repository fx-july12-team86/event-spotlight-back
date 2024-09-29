package org.example.eventspotlightback.dto.internal.city;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddCityDto {
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;
}
