package org.example.eventspotlightback.mapper;

import java.util.Comparator;
import java.util.List;
import org.example.eventspotlightback.config.MapperConfig;
import org.example.eventspotlightback.dto.internal.event.CreateEventDto;
import org.example.eventspotlightback.dto.internal.event.EventDto;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDto;
import org.example.eventspotlightback.dto.internal.photo.PhotoDto;
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
    @Mapping(target = "isTop", ignore = true)
    @Mapping(target = "isAccepted", ignore = true)
    @Mapping(target = "favorites", ignore = true)
    @Mapping(target = "myEvents", ignore = true)
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "contact", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "isOnline", defaultValue = "false")
    Event toModel(CreateEventDto eventDto);

    @Mapping(source = "user.id", target = "userId")
    SimpleEventDto toSimpleDto(Event event);

    List<SimpleEventDto> toSimpleDto(List<Event> events);

    @Mapping(source = "user.id", target = "userId")
    EventDto toDto(Event event);

    List<EventDto> toDto(List<Event> events);

    @AfterMapping
    default void setPhotoId(@MappingTarget SimpleEventDto simpleEventDto, Event event) {
        if (event.getPhotos() != null) {
            simpleEventDto.setPhoto(event.getPhotos().stream()
                            .min(Comparator.comparing(Photo::getId))
                            .map(photo -> {
                                PhotoDto photoDto = new PhotoDto();
                                photoDto.setId(photo.getId());
                                photoDto.setSharedUrl(photo.getSharedUrl());
                                photoDto.setCreatedAt(photo.getCreatedAt().toString());
                                return photoDto;
                            })
                            .orElse(null));

        }
    }

    @AfterMapping
    default void setCategoryId(@MappingTarget SimpleEventDto simpleEventDto, Event event) {
        if (event.getCategories() != null) {
            simpleEventDto.setCategoryName(event.getCategories().stream()
                    .min(Comparator.comparing(Category::getId))
                    .map(Category::getName)
                    .orElse(null));
        }
    }
}
