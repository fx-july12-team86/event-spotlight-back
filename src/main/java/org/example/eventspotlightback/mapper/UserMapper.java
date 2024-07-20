package org.example.eventspotlightback.mapper;

import org.example.eventspotlightback.config.MapperConfig;
import org.example.eventspotlightback.dto.internal.user.UserRegistrationRequestDto;
import org.example.eventspotlightback.dto.internal.user.UserResponseDto;
import org.example.eventspotlightback.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegistrationRequestDto user);

    UserResponseDto toDto(User user);
}
