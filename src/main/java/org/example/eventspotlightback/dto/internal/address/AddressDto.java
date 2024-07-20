package org.example.eventspotlightback.dto.internal.address;

import lombok.Data;

@Data
public class AddressDto {
    private long id;
    private long cityId;
    private String street;
    private String number;
}
