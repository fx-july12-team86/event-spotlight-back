package org.example.eventspotlightback.dto.internal.contact;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.eventspotlightback.validation.PhoneNumber;

@Data
@Accessors(chain = true)
public class CreateContactDto {
    @PhoneNumber
    private String phoneNumber;
    @Email
    private String email;
    private String instagram;
    private String telegram;
    private String facebook;
}
