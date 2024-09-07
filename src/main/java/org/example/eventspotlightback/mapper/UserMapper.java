package org.example.eventspotlightback.mapper;

import org.example.eventspotlightback.config.MapperConfig;
import org.example.eventspotlightback.dto.internal.user.UserRegistrationRequestDto;
import org.example.eventspotlightback.dto.internal.user.UserResponseDto;
import org.example.eventspotlightback.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "favorite", ignore = true)
    @Mapping(target = "myEvents", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    User toModel(UserRegistrationRequestDto user);

    UserResponseDto toDto(User user);

    @AfterMapping
    default void setUserName(@MappingTarget UserResponseDto responseDto, User user) {
        if (user.getCustomUserName() != null) {
            responseDto.setUserName(user.getCustomUserName());
        }
    }
}
