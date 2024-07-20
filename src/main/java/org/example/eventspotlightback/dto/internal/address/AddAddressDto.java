package org.example.eventspotlightback.dto.internal.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddAddressDto {
    @NotNull
    private Long cityId;
    @NotBlank
    private String street;
    @NotBlank
    private String number;
}
