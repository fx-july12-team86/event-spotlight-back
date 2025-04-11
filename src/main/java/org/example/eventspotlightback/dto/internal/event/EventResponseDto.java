package org.example.eventspotlightback.dto.internal.event;

import java.util.List;

public record EventResponseDto(Integer pageCount, List<GroupedSimpleEventDto> events) {
}
