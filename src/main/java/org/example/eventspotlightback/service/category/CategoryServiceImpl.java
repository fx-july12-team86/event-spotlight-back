package org.example.eventspotlightback.service.category;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.category.CategoryDto;
import org.example.eventspotlightback.dto.internal.category.CreateCategoryDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.CategoryMapper;
import org.example.eventspotlightback.model.Category;
import org.example.eventspotlightback.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto add(CreateCategoryDto requestDto) {
        Category category = categoryMapper.toModel(requestDto);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryDto dto) {
        Category newCategory = categoryMapper.toModel(dto);
        newCategory.setId(id);
        categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category with id" + id + " not found")
        );
        return categoryMapper.toDto(categoryRepository.save(newCategory));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryMapper.toDto(categoryRepository.findAll());
    }

    @Override
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category with id" + id + " not found")
        );
        return categoryMapper.toDto(category);
    }
}
