package org.example.eventspotlightback.repository.specification.event;

import java.time.LocalDateTime;
import org.example.eventspotlightback.exception.IllegalArgumentException;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DataRangeSpecificationProvider implements SpecificationProvider<Event> {
    public static final String KEY = "dataRange";
    public static final String EVENT_KEY = "startTime";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public Specification<Event> getSpecification(String[] params) {
        if (params.length != 2) {
            throw new IllegalArgumentException("Data range requires start and end dates");
        }

        LocalDateTime startDate = LocalDateTime.parse(params[0]);
        LocalDateTime endDate = LocalDateTime.parse(params[1]);

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(EVENT_KEY), startDate, endDate);
    }
}
