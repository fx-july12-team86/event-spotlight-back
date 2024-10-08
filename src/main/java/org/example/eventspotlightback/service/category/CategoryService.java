package org.example.eventspotlightback.service.category;

import java.util.List;
import org.example.eventspotlightback.dto.internal.category.CategoryDto;
import org.example.eventspotlightback.dto.internal.category.CreateCategoryDto;

public interface CategoryService {
    CategoryDto addCategory(CreateCategoryDto requestDto);

    CategoryDto updateCategory(Long id, CreateCategoryDto dto);

    void deleteById(Long id);

    List<CategoryDto> findAll();

    CategoryDto findById(Long id);
}
