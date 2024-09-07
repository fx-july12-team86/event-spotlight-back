package org.example.eventspotlightback.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.example.eventspotlightback.config.MapperConfig;
import org.example.eventspotlightback.dto.internal.event.CreateEventDto;
import org.example.eventspotlightback.dto.internal.event.EventDto;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDto;
import org.example.eventspotlightback.model.Category;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.model.Photo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = {
        DescriptionMapper.class,
        AddressMapper.class,
        PhotoMapper.class,
        CategoryMapper.class,
        ContactMapper.class,
})
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "top", ignore = true)
    @Mapping(target = "accepted", ignore = true)
    @Mapping(target = "favorites", ignore = true)
    @Mapping(target = "myEvents", ignore = true)
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "contact", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Event toModel(CreateEventDto eventDto);

    SimpleEventDto toSimpleDto(Event event);

    List<SimpleEventDto> toSimpleDto(List<Event> events);

    @Mapping(source = "user.id", target = "userId")
    EventDto toDto(Event event);

    List<EventDto> toDto(List<Event> events);

    @AfterMapping
    default void setPhotoIds(@MappingTarget SimpleEventDto simpleEventDto, Event event) {
        if (event.getPhotos() != null) {
            simpleEventDto.setPhotosIds(event.getPhotos().stream()
                    .map(Photo::getId)
                    .collect(Collectors.toList()));
        }
    }

    @AfterMapping
    default void setCategoryIds(@MappingTarget SimpleEventDto simpleEventDto, Event event) {
        if (event.getCategories() != null) {
            simpleEventDto.setCategoryIds(event.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet()));
        }
    }
}
