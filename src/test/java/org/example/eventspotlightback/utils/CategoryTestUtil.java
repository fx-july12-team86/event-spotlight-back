package org.example.eventspotlightback.utils;

import java.util.ArrayList;
import java.util.List;
import org.example.eventspotlightback.dto.internal.category.CategoryDto;
import org.example.eventspotlightback.dto.internal.category.CreateCategoryDto;
import org.example.eventspotlightback.model.Category;

public class CategoryTestUtil {
    public static final Long TEST_CATEGORY_ID = 98L;
    public static final String TEST_CATEGORY_NAME = "Test Category Name";
    public static final String TEST_UPDATE_CATEGORY_NAME = "Test Update Category Name";

    public static final Category testCategory = new Category()
            .setId(TEST_CATEGORY_ID)
            .setName(TEST_CATEGORY_NAME);
    public static final CategoryDto testCategoryDto = new CategoryDto()
            .setId(TEST_CATEGORY_ID)
            .setName(TEST_CATEGORY_NAME);
    public static final CategoryDto updatedCategoryDto = new CategoryDto()
            .setId(TEST_CATEGORY_ID)
            .setName(TEST_UPDATE_CATEGORY_NAME);
    public static final CreateCategoryDto addCategoryDto = new CreateCategoryDto()
            .setName(TEST_CATEGORY_NAME);
    public static final CreateCategoryDto updateCategoryDto = new CreateCategoryDto()
            .setName(TEST_UPDATE_CATEGORY_NAME);

    public static List<CategoryDto> getTestListWithCategories() {
        CategoryDto disco = new CategoryDto()
                .setId(1L)
                .setName("Disco");
        CategoryDto interview = new CategoryDto()
                .setId(2L)
                .setName("Interview");
        CategoryDto masterClass = new CategoryDto()
                .setId(3L)
                .setName("Master-class");
        List<CategoryDto> testList = new ArrayList<>();
        testList.add(disco);
        testList.add(interview);
        testList.add(masterClass);
        return testList;
    }
}
