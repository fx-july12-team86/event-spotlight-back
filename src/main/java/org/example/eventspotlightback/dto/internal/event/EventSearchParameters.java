package org.example.eventspotlightback.dto.internal.event;

public record EventSearchParameters(
        String[] categories,
        String[] dateRange,
        String[] onlineStatus
) {
}
