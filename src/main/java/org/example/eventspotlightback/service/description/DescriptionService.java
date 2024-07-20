package org.example.eventspotlightback.service.description;

import java.util.List;
import org.example.eventspotlightback.dto.internal.description.CreateDescriptionDto;
import org.example.eventspotlightback.dto.internal.description.DescriptionDto;

public interface DescriptionService {
    DescriptionDto add(CreateDescriptionDto descriptionDto);

    List<DescriptionDto> findAll();

    DescriptionDto findById(long id);

    DescriptionDto update(Long id, CreateDescriptionDto descriptionDto);

    void deleteById(long id);
}
