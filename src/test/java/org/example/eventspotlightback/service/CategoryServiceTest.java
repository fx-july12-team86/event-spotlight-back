package org.example.eventspotlightback.service;

import java.util.List;
import java.util.Optional;
import org.example.eventspotlightback.dto.internal.category.CategoryDto;
import org.example.eventspotlightback.dto.internal.category.CreateCategoryDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.CategoryMapper;
import org.example.eventspotlightback.model.Category;
import org.example.eventspotlightback.repository.CategoryRepository;
import org.example.eventspotlightback.service.category.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.example.eventspotlightback.utils.TestUtil.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    private static final String TEST_UPDATE_CATEGORY_NAME = "Test Update Category Name";

    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category testUpdatedCategory;
    private CategoryDto testUpdatedCategoryDto;
    private CreateCategoryDto updateCategoryDto;

    @BeforeEach
    void setUp() {
        testUpdatedCategory = new Category()
                .setId(TEST_CATEGORY_ID)
                .setName(TEST_UPDATE_CATEGORY_NAME);

        updateCategoryDto = new CreateCategoryDto()
                .setName(TEST_UPDATE_CATEGORY_NAME);

        testUpdatedCategoryDto = new CategoryDto()
                .setId(TEST_CATEGORY_ID)
                .setName(TEST_UPDATE_CATEGORY_NAME);
    }

    @Test
    @DisplayName("Add new Category to DB")
    public void addCategory_WithValidRequest_returnNewCategoryDto() {
        //Given
        when(categoryMapper.toModel(addCategoryDto)).thenReturn(testCategory);
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);
        when(categoryMapper.toDto(any(Category.class))).thenReturn(testCategoryDto);

        //When
        CategoryDto expected = testCategoryDto;
        CategoryDto actual = categoryService.addCategory(addCategoryDto);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update Category by id - valid")
    public void updateCategoryById_WithValidRequest_returnUpdatedCategoryDto() {
        //Given
        when(categoryMapper.toModel(updateCategoryDto)).thenReturn(testUpdatedCategory);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(testCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(testUpdatedCategory);
        when(categoryMapper.toDto(any(Category.class))).thenReturn(testUpdatedCategoryDto);

        //When
        CategoryDto expected = testUpdatedCategoryDto;
        CategoryDto actual = categoryService.updateCategory(TEST_CATEGORY_ID, updateCategoryDto);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update Category with not existing Category id")
    public void updateCategoryById_InvalidId_EntityNotFoundException() {
        //Given
        when(categoryMapper.toModel(any(CreateCategoryDto.class))).thenReturn(testUpdatedCategory);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.updateCategory(TEST_CATEGORY_ID, updateCategoryDto)
        );
        String expectedMessage = "Category with id" + TEST_CATEGORY_ID + " not found";
        String actualMessage = exception.getMessage();

        //Then
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Delete Category by Id - valid")
    void deleteById_Id_Empty() {
        categoryService.deleteById(TEST_CATEGORY_ID);
        verify(categoryRepository, times(1)).deleteById(TEST_CATEGORY_ID);
    }

    @Test
    @DisplayName("Find all categories")
    public void findAll_Empty_ListOfCategoryDto() {
        //Given
        when(categoryRepository.findAll()).thenReturn(List.of(testCategory));
        when(categoryMapper.toDto(anyList())).thenReturn(List.of(testCategoryDto));

        //When
        List<CategoryDto> expected = List.of(testCategoryDto);
        List<CategoryDto> actual = categoryService.findAll();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find Category by id - valid")
    public void findCategoryById_CityId_CityDto() {
        //Given
        when(categoryRepository.findById(TEST_CATEGORY_ID)).thenReturn(Optional.of(testCategory));
        when(categoryMapper.toDto(any(Category.class))).thenReturn(testCategoryDto);

        //When
        CategoryDto expected = testCategoryDto;
        CategoryDto actual = categoryService.findById(TEST_CATEGORY_ID);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find Category by id with not existing id")
    public void findCategoryById_InvalidId_EntityNotFoundException() {
        //Given
        when(categoryRepository.findById(TEST_CATEGORY_ID)).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.findById(TEST_CATEGORY_ID)
        );
        String expected = "Category with id" + TEST_CATEGORY_ID + " not found";
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }
}
