package org.example.eventspotlightback.dto.internal.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.example.eventspotlightback.validation.FieldMatch;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
public class UserRegistrationRequestDto {
    @NotBlank
    private String userName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Length(min = 3, max = 35)
    private String password;
    @NotBlank
    @Length(min = 3, max = 35)
    private String repeatPassword;
}
