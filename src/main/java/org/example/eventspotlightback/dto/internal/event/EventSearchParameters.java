package org.example.eventspotlightback.dto.internal.event;

public record EventSearchParameters(
        String title,
        String[] categories,
        String[] dateRange,
        String[] onlineStatus,
        String[] cities
) {
}
