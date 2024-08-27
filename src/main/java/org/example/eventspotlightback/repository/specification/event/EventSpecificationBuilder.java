package org.example.eventspotlightback.repository.specification.event;

import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.event.EventSearchParameters;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.repository.specification.SpecificationBuilder;
import org.example.eventspotlightback.repository.specification.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventSpecificationBuilder implements SpecificationBuilder<Event> {
    private static final String CATEGORIES_SPEC_KEY = "categories";
    private static final String DATA_RANGE_SPEC_KEY = "dataRange";
    private static final String ONLINE_STATUS_SPEC_KEY = "isOnline";
    private final SpecificationProviderManager<Event> specificationProviderManager;

    @Override
    public Specification<Event> build(EventSearchParameters eventSearchParameters) {
        Specification<Event> specification = Specification.where(null);
        if (eventSearchParameters.categories() != null
                && eventSearchParameters.categories().length > 0) {
            specification = specification.and(
                    specificationProviderManager.getSpecificationProvider(CATEGORIES_SPEC_KEY)
                            .getSpecification(eventSearchParameters.categories())
            );
        }

        if (eventSearchParameters.dateRange() != null
                && eventSearchParameters.dateRange().length > 0) {
            specification = specification.and(
                    specificationProviderManager.getSpecificationProvider(DATA_RANGE_SPEC_KEY)
                            .getSpecification(eventSearchParameters.dateRange())
            );
        }

        if (eventSearchParameters.onlineStatus() != null
                && eventSearchParameters.onlineStatus().length > 0) {
            specification = specification.and(
                    specificationProviderManager.getSpecificationProvider(ONLINE_STATUS_SPEC_KEY)
                            .getSpecification(eventSearchParameters.onlineStatus())
            );
        }
        return specification;
    }
}
