package org.example.eventspotlightback.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.category.CategoryDto;
import org.example.eventspotlightback.dto.internal.category.CreateCategoryDto;
import org.example.eventspotlightback.service.category.CategoryService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping()
    public CategoryDto addCategory(@RequestBody @Valid CreateCategoryDto category) {
        return categoryService.add(category);
    }

    @GetMapping()
    public List<CategoryDto> findAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryDto findCategoryById(@PathVariable long id) {
        return categoryService.findById(id);
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable long id,
                                      @RequestBody @Valid CreateCategoryDto categoryDto) {
        return categoryService.update(id, categoryDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        categoryService.deleteById(id);
    }
}
