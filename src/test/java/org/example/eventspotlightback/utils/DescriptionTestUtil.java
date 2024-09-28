package org.example.eventspotlightback.utils;

import java.util.ArrayList;
import java.util.List;
import org.example.eventspotlightback.dto.internal.description.CreateDescriptionDto;
import org.example.eventspotlightback.dto.internal.description.DescriptionDto;
import org.example.eventspotlightback.model.Description;

public class DescriptionTestUtil {
    public static final Long TEST_DESCRIPTION_ID = 96L;
    public static final String TEST_DESCRIPTION_TITLE = "test Title";
    public static final String TEST_DESCRIPTION_DESCRIPTION = "test Description";

    public static final String TEST_UPDATE_DESCRIPTION_TITLE = "update Test Title";

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

    public static final CreateDescriptionDto updateDescriptionDto = new CreateDescriptionDto()
            .setTitle(TEST_UPDATE_DESCRIPTION_TITLE)
            .setDescription(TEST_DESCRIPTION_DESCRIPTION);

    public static final DescriptionDto updatedDescriptionDto = new DescriptionDto()
            .setId(TEST_DESCRIPTION_ID)
            .setTitle(TEST_UPDATE_DESCRIPTION_TITLE)
            .setDescription(TEST_DESCRIPTION_DESCRIPTION);

    public static List<DescriptionDto> getTestListWithDescriptionDto() {
        DescriptionDto first = new DescriptionDto()
                .setId(1L)
                .setTitle("first test Title")
                .setDescription("first test Description");
        DescriptionDto second = new DescriptionDto()
                .setId(2L)
                .setTitle("second test Title")
                .setDescription("second test Description");
        DescriptionDto third = new DescriptionDto()
                .setId(3L)
                .setTitle("third test Title")
                .setDescription("third test Description");
        List<DescriptionDto> testList = new ArrayList<>();
        testList.add(first);
        testList.add(second);
        testList.add(third);
        return testList;
    }
}
