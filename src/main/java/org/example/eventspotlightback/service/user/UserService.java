package org.example.eventspotlightback.service.user;

import org.example.eventspotlightback.dto.internal.user.UserRegistrationRequestDto;
import org.example.eventspotlightback.dto.internal.user.UserResponseDto;
import org.example.eventspotlightback.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    void deleteUserById(Long id);
}
