package org.example.eventspotlightback.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.city.AddCityDto;
import org.example.eventspotlightback.dto.internal.city.CityDto;
import org.example.eventspotlightback.service.city.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityDto save(@RequestBody @Valid AddCityDto dto) {
        return cityService.addCity(dto);
    }

    @GetMapping
    public List<CityDto> findAll() {
        return cityService.findAllCities();
    }

    @GetMapping("/{id}")
    public CityDto findById(@PathVariable Long id) {
        return cityService.findCityById(id);
    }

    @PutMapping("{id}")
    public CityDto update(@PathVariable Long id,
                          @RequestBody @Valid AddCityDto dto) {
        return cityService.updateCityById(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        cityService.deleteCityById(id);
    }
}
