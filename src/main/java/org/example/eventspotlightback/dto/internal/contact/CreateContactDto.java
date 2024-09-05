package org.example.eventspotlightback.dto.internal.contact;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.example.eventspotlightback.validation.PhoneNumber;

@Data
public class CreateContactDto {
    @PhoneNumber
    private String phoneNumber;
    @Email
    private String email;
    private String instagram;
    private String telegram;
    private String facebook;
}
