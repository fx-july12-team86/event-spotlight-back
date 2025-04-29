package org.example.eventspotlightback.dto.internal.event;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class GroupedSimpleEventDto {
    private String field;
    private List<SimpleEventDtoWithFavorite> events;
}
