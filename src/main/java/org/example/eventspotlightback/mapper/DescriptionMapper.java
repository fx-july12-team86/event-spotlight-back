package org.example.eventspotlightback.mapper;

import java.util.List;
import org.example.eventspotlightback.config.MapperConfig;
import org.example.eventspotlightback.dto.internal.description.CreateDescriptionDto;
import org.example.eventspotlightback.dto.internal.description.DescriptionDto;
import org.example.eventspotlightback.model.Description;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface DescriptionMapper {
    Description toModel(CreateDescriptionDto createDescriptionDto);

    Description toModel(DescriptionDto descriptionDto);

    DescriptionDto toDto(Description description);

    List<DescriptionDto> toDto(List<Description> descriptions);
}
