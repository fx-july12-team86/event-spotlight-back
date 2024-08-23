package org.example.eventspotlightback.mapper;

import org.example.eventspotlightback.config.MapperConfig;
import org.example.eventspotlightback.dto.internal.user.UserRegistrationRequestDto;
import org.example.eventspotlightback.dto.internal.user.UserResponseDto;
import org.example.eventspotlightback.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toModel(UserRegistrationRequestDto user);

    @Mapping(source = "username", target = "userName")
    UserResponseDto toDto(User user);
}
