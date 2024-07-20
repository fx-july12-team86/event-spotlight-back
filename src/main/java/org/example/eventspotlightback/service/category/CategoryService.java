package org.example.eventspotlightback.service.category;

import java.util.List;
import org.example.eventspotlightback.dto.internal.category.CategoryDto;
import org.example.eventspotlightback.dto.internal.category.CreateCategoryDto;

public interface CategoryService {
    CategoryDto add(CreateCategoryDto requestDto);

    List<CategoryDto> findAll();

    CategoryDto findById(Long id);

    CategoryDto update(Long id, CreateCategoryDto dto);

    void deleteById(Long id);
}
