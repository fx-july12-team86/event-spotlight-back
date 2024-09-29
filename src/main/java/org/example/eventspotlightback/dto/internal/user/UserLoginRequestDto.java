package org.example.eventspotlightback.dto.internal.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Accessors(chain = true)
public record UserLoginRequestDto(
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Length(min = 3, max = 35)
        String password
) {
}
