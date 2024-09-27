package org.example.eventspotlightback.utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import org.example.eventspotlightback.dto.internal.event.CreateEventDto;
import org.example.eventspotlightback.dto.internal.event.EventDto;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDto;
import org.example.eventspotlightback.model.Event;

import static org.example.eventspotlightback.utils.AddressTestUtil.TEST_ADDRESS_ID;
import static org.example.eventspotlightback.utils.AddressTestUtil.testAddress;
import static org.example.eventspotlightback.utils.AddressTestUtil.testAddressDto;
import static org.example.eventspotlightback.utils.CategoryTestUtil.TEST_CATEGORY_ID;
import static org.example.eventspotlightback.utils.ContactTestUtil.TEST_CONTACT_ID;
import static org.example.eventspotlightback.utils.ContactTestUtil.testContact;
import static org.example.eventspotlightback.utils.ContactTestUtil.testContactDto;
import static org.example.eventspotlightback.utils.DescriptionTestUtil.TEST_DESCRIPTION_ID;
import static org.example.eventspotlightback.utils.DescriptionTestUtil.testDescription;
import static org.example.eventspotlightback.utils.DescriptionTestUtil.testDescriptionDto;
import static org.example.eventspotlightback.utils.PhotoTestUtil.TEST_PHOTO_ID;
import static org.example.eventspotlightback.utils.UserTestUtil.TEST_USER_ID;
import static org.example.eventspotlightback.utils.UserTestUtil.testUser;

public class EventTestUtil {
    public static final Long TEST_EVENT_ID = 6L;
    public static final String TEST_EVENT_TITLE = "Test Event Title";
    public static final LocalDateTime TEST_EVENT_START_TIME = LocalDateTime
            .of(2024, 11, 19, 6, 0);
    public static final BigDecimal TEST_EVENT_PRICE = BigDecimal.valueOf(100.0);

    public static final String TEST_UPDATE_EVENT_TITLE = "Test Updated Event Title";

    public static final Event testEvent = new Event()
            .setId(TEST_EVENT_ID)
            .setTitle(TEST_EVENT_TITLE)
            .setStartTime(TEST_EVENT_START_TIME)
            .setPrice(TEST_EVENT_PRICE)
            .setContact(testContact)
            .setAddress(testAddress)
            .setDescription(testDescription)
            .setUser(testUser)
            .setAccepted(false);
    public static final EventDto testEventDto = new EventDto()
            .setId(TEST_EVENT_ID)
            .setTitle(TEST_EVENT_TITLE)
            .setStartTime(TEST_EVENT_START_TIME)
            .setPrice(TEST_EVENT_PRICE)
            .setContact(testContactDto)
            .setAddress(testAddressDto)
            .setUserId(TEST_USER_ID)
            .setDescription(testDescriptionDto);
    public static final SimpleEventDto testSimpleEventDto = new SimpleEventDto()
            .setId(TEST_EVENT_ID)
            .setTitle(TEST_EVENT_TITLE)
            .setStartTime(TEST_EVENT_START_TIME)
            .setPrice(TEST_EVENT_PRICE)
            .setAddress(testAddressDto);
    public static final CreateEventDto addEventDto = new CreateEventDto()
            .setTitle(TEST_EVENT_TITLE)
            .setStartTime(TEST_EVENT_START_TIME)
            .setPrice(TEST_EVENT_PRICE)
            .setContactId(TEST_CONTACT_ID)
            .setAddressId(TEST_ADDRESS_ID)
            .setDescriptionId(TEST_DESCRIPTION_ID)
            .setUserId(TEST_USER_ID)
            .setPhotoIds(Set.of(TEST_PHOTO_ID))
            .setCategoryIds(Set.of(TEST_CATEGORY_ID));
}
