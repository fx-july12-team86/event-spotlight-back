package org.example.eventspotlightback.dto.internal.contact;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ContactDto {
    private Long id;
    private String phoneNumber;
    private String email;
    private String instagram;
    private String telegram;
    private String facebook;
}
