package org.example.eventspotlightback.mapper;

import java.util.List;
import org.example.eventspotlightback.config.MapperConfig;
import org.example.eventspotlightback.dto.internal.event.CreateEventDto;
import org.example.eventspotlightback.dto.internal.event.EventDto;
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
    Event toModel(CreateEventDto eventDto);

    @Mapping(source = "user.id", target = "userId")
    EventDto toDto(Event event);

    List<EventDto> toDto(List<Event> events);
}
