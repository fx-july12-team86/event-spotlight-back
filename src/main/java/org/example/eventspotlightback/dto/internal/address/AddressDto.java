package org.example.eventspotlightback.dto.internal.address;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddressDto {
    private Long id;
    private Long cityId;
    private String street;
    private String number;
}
