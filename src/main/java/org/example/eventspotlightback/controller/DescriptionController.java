package org.example.eventspotlightback.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.description.CreateDescriptionDto;
import org.example.eventspotlightback.dto.internal.description.DescriptionDto;
import org.example.eventspotlightback.service.description.DescriptionService;
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
@RequestMapping("/descriptions")
public class DescriptionController {
    private final DescriptionService descriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DescriptionDto addDescription(@RequestBody @Valid CreateDescriptionDto descriptionDto) {
        return descriptionService.add(descriptionDto);
    }

    @GetMapping
    public List<DescriptionDto> findAll() {
        return descriptionService.findAll();
    }

    @GetMapping("/{id}")
    public DescriptionDto findById(@PathVariable long id) {
        return descriptionService.findById(id);
    }

    @PutMapping("{id}")
    public DescriptionDto update(@PathVariable Long id,
                          @RequestBody @Valid CreateDescriptionDto updateDto) {
        return descriptionService.update(id, updateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        descriptionService.deleteById(id);
    }
}
