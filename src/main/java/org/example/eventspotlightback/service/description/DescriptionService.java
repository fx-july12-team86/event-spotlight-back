package org.example.eventspotlightback.service.description;

import java.util.List;
import org.example.eventspotlightback.dto.internal.description.CreateDescriptionDto;
import org.example.eventspotlightback.dto.internal.description.DescriptionDto;

public interface DescriptionService {
    DescriptionDto addDescription(CreateDescriptionDto descriptionDto);

    DescriptionDto updateById(Long id, CreateDescriptionDto descriptionDto);

    void deleteById(Long id);

    List<DescriptionDto> findAll();

    DescriptionDto findById(Long id);
}
