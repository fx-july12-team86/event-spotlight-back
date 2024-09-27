package org.example.eventspotlightback.utils;

import org.example.eventspotlightback.dto.internal.category.CategoryDto;
import org.example.eventspotlightback.dto.internal.category.CreateCategoryDto;
import org.example.eventspotlightback.model.Category;

public class CategoryTestUtil {
    public static final Long TEST_CATEGORY_ID = 3L;
    public static final String TEST_CATEGORY_NAME = "Test Category Name";
    public static final String TEST_UPDATE_CATEGORY_NAME = "Test Update Category Name";

    public static final Category testCategory = new Category()
            .setId(TEST_CATEGORY_ID)
            .setName(TEST_CATEGORY_NAME);
    public static final CategoryDto testCategoryDto = new CategoryDto()
            .setId(TEST_CATEGORY_ID)
            .setName(TEST_CATEGORY_NAME);
    public static final CreateCategoryDto addCategoryDto = new CreateCategoryDto()
            .setName(TEST_CATEGORY_NAME);
}
