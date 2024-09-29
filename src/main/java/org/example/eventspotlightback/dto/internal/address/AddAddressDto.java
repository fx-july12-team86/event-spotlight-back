package org.example.eventspotlightback.dto.internal.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddAddressDto {
    @NotNull
    private Long cityId;
    @NotBlank
    private String street;
    @NotBlank
    private String number;
}
