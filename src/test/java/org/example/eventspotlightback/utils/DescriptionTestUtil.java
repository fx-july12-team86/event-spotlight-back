package org.example.eventspotlightback.utils;

import org.example.eventspotlightback.dto.internal.description.CreateDescriptionDto;
import org.example.eventspotlightback.dto.internal.description.DescriptionDto;
import org.example.eventspotlightback.model.Description;

public class DescriptionTestUtil {
    public static final Long TEST_DESCRIPTION_ID = 5L;
    public static final String TEST_DESCRIPTION_TITLE = "testTitle";
    public static final String TEST_DESCRIPTION_DESCRIPTION = "testDescription";

    public static final String TEST_UPDATE_DESCRIPTION_TITLE = "updateTestTitle";

    public static final Description testDescription = new Description()
            .setId(TEST_DESCRIPTION_ID)
            .setTitle(TEST_DESCRIPTION_TITLE)
            .setDescription(TEST_DESCRIPTION_DESCRIPTION);
    public static final DescriptionDto testDescriptionDto = new DescriptionDto()
            .setId(TEST_DESCRIPTION_ID)
            .setTitle(TEST_DESCRIPTION_TITLE)
            .setDescription(TEST_DESCRIPTION_DESCRIPTION);
    public static final CreateDescriptionDto addDescriptionDto = new CreateDescriptionDto()
            .setTitle(TEST_DESCRIPTION_TITLE)
            .setDescription(TEST_DESCRIPTION_DESCRIPTION);
}
