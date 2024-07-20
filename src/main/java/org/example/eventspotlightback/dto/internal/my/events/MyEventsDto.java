package org.example.eventspotlightback.dto.internal.my.events;

import java.util.List;
import lombok.Data;
import org.example.eventspotlightback.dto.internal.event.EventDto;

@Data
public class MyEventsDto {
    private long id;
    private long userId;
    private List<EventDto> eventDtos;
}
