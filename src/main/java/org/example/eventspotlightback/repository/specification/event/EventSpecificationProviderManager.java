package org.example.eventspotlightback.repository.specification.event;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.repository.specification.SpecificationProvider;
import org.example.eventspotlightback.repository.specification.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventSpecificationProviderManager implements SpecificationProviderManager<Event> {
    private final List<SpecificationProvider<Event>> specificationProviders;

    @Override
    public SpecificationProvider<Event> getSpecificationProvider(String key) {
        return specificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find specification provider for key:" + key)
                );
    }
}
