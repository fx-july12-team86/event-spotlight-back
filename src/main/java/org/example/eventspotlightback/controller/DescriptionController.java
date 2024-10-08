package org.example.eventspotlightback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.description.CreateDescriptionDto;
import org.example.eventspotlightback.dto.internal.description.DescriptionDto;
import org.example.eventspotlightback.service.description.DescriptionService;
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

@Tag(name = "Description management", description = "Endpoint for managing Descriptions")
@RequiredArgsConstructor
@RestController
@RequestMapping("/descriptions")
public class DescriptionController {
    private final DescriptionService descriptionService;

    @Operation(
            summary = "Add new Description"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DescriptionDto addDescription(@RequestBody @Valid CreateDescriptionDto descriptionDto) {
        return descriptionService.addDescription(descriptionDto);
    }

    @Operation(
            summary = "Update exists Description"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}")
    public DescriptionDto update(@PathVariable Long id,
                                 @RequestBody @Valid CreateDescriptionDto updateDto) {
        return descriptionService.updateById(id, updateDto);
    }

    @Operation(
            summary = "Delete Description"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        descriptionService.deleteById(id);
    }

    @Operation(
            summary = "Find all Descriptions"
    )
    @GetMapping
    public List<DescriptionDto> findAll() {
        return descriptionService.findAll();
    }

    @Operation(
            summary = "Find Description by id"
    )
    @GetMapping("/{id}")
    public DescriptionDto findById(@PathVariable Long id) {
        return descriptionService.findById(id);
    }
}
