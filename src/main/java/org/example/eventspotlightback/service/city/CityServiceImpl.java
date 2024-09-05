package org.example.eventspotlightback.service.city;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.city.AddCityDto;
import org.example.eventspotlightback.dto.internal.city.CityDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.CityMapper;
import org.example.eventspotlightback.model.City;
import org.example.eventspotlightback.repository.CityRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    public CityDto addCity(AddCityDto addCityDto) {
        City city = cityMapper.toModel(addCityDto);
        City savedCity = cityRepository.save(city);
        return cityMapper.toDto(savedCity);
    }

    @Override
    public List<CityDto> findAllCities() {
        return cityMapper.toDto(cityRepository.findAll());
    }

    @Override
    public CityDto findCityById(Long cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(
                () -> new EntityNotFoundException("City with id " + cityId + " not found")
        );
        return cityMapper.toDto(city);
    }

    @Override
    public CityDto updateCityById(Long id, AddCityDto updateCityDto) {
        City newCity = cityMapper.toModel(updateCityDto);
        newCity.setId(id);
        cityRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cant update city with id: " + id)
        );
        City savedCity = cityRepository.save(newCity);
        return cityMapper.toDto(savedCity);
    }

    @Override
    public void deleteCityById(Long cityId) {
        cityRepository.deleteById(cityId);
    }

}
