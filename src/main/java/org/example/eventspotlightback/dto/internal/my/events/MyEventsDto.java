package org.example.eventspotlightback.dto.internal.my.events;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDto;

@Data
@Accessors(chain = true)
public class MyEventsDto {
    private long id;
    private List<SimpleEventDto> eventDtos;
}
