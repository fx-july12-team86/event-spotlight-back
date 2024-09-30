package org.example.eventspotlightback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.city.AddCityDto;
import org.example.eventspotlightback.dto.internal.city.CityDto;
import org.example.eventspotlightback.service.city.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "City management", description = "Endpoint for managing Cities")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;

    @Operation(
            summary = "Add new City"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityDto save(@RequestBody @Valid AddCityDto dto) {
        return cityService.addCity(dto);
    }

    @Operation(
            summary = "Update exists City"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}")
    public CityDto update(@PathVariable Long id,
                          @RequestBody @Valid AddCityDto dto) {
        return cityService.updateCityById(id, dto);
    }

    @Operation(
            summary = "Delete City"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        cityService.deleteCityById(id);
    }

    @Operation(
            summary = "Find all Cities"
    )
    @GetMapping
    public List<CityDto> findAll() {
        return cityService.findAllCities();
    }

    @Operation(
            summary = "Find City by id"
    )
    @GetMapping("/{id}")
    public CityDto findById(@PathVariable Long id) {
        return cityService.findCityById(id);
    }
}
