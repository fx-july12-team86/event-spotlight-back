package org.example.eventspotlightback.mapper;

import java.util.List;
import org.example.eventspotlightback.config.MapperConfig;
import org.example.eventspotlightback.dto.internal.event.CreateEventDto;
import org.example.eventspotlightback.dto.internal.event.EventDto;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDto;
import org.example.eventspotlightback.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {
        DescriptionMapper.class,
        AddressMapper.class,
        PhotoMapper.class,
        CategoryMapper.class
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
    @Mapping(target = "photos", ignore = true)
    Event toModel(CreateEventDto eventDto);

    SimpleEventDto toSimpleDto(Event eventDto);

    List<SimpleEventDto> toSimpleDto(List<Event> eventDto);

    @Mapping(source = "user.id", target = "userId")
    EventDto toDto(Event event);

    List<EventDto> toDto(List<Event> events);
}
