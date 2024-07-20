package org.example.eventspotlightback.service.city;

import java.util.List;
import org.example.eventspotlightback.dto.internal.city.AddCityDto;
import org.example.eventspotlightback.dto.internal.city.CityDto;

public interface CityService {
    CityDto addCity(AddCityDto city);

    List<CityDto> findAllCities();

    CityDto findCityById(Long cityId);

    CityDto updateCityById(Long id, AddCityDto city);

    void deleteCityById(Long cityId);
}
