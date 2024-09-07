package org.example.eventspotlightback.mapper;

import java.util.List;
import org.example.eventspotlightback.config.MapperConfig;
import org.example.eventspotlightback.dto.internal.my.events.MyEventsDto;
import org.example.eventspotlightback.model.MyEvents;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {EventMapper.class})
public interface MyEventsMapper {

    @Mapping(source = "events", target = "eventDtos")
    MyEventsDto toDto(MyEvents myEvents);

    List<MyEventsDto> toDto(List<MyEvents> myEvents);
}
