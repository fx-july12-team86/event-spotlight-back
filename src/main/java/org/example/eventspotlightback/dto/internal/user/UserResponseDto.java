package org.example.eventspotlightback.dto.internal.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserResponseDto {
    private Long id;
    private String userName;
    private String email;
}
