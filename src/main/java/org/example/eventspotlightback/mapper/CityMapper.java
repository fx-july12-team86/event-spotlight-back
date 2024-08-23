package org.example.eventspotlightback.mapper;

import java.util.List;
import org.example.eventspotlightback.config.MapperConfig;
import org.example.eventspotlightback.dto.internal.city.AddCityDto;
import org.example.eventspotlightback.dto.internal.city.CityDto;
import org.example.eventspotlightback.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CityMapper {
    @Mapping(target = "id", ignore = true)
    City toModel(AddCityDto addCityDto);

    CityDto toDto(City city);

    List<CityDto> toDto(List<City> cities);
}
