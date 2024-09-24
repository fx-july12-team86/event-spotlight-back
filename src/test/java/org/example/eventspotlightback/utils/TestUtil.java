package org.example.eventspotlightback.utils;

import org.example.eventspotlightback.dto.internal.address.AddAddressDto;
import org.example.eventspotlightback.dto.internal.address.AddressDto;
import org.example.eventspotlightback.dto.internal.category.CategoryDto;
import org.example.eventspotlightback.dto.internal.category.CreateCategoryDto;
import org.example.eventspotlightback.dto.internal.city.AddCityDto;
import org.example.eventspotlightback.dto.internal.city.CityDto;
import org.example.eventspotlightback.dto.internal.contact.ContactDto;
import org.example.eventspotlightback.dto.internal.contact.CreateContactDto;
import org.example.eventspotlightback.dto.internal.description.CreateDescriptionDto;
import org.example.eventspotlightback.dto.internal.description.DescriptionDto;
import org.example.eventspotlightback.dto.internal.event.CreateEventDto;
import org.example.eventspotlightback.dto.internal.event.EventDto;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDto;
import org.example.eventspotlightback.dto.internal.favorite.FavoriteDto;
import org.example.eventspotlightback.dto.internal.photo.PhotoDto;
import org.example.eventspotlightback.dto.internal.user.UserRegistrationRequestDto;
import org.example.eventspotlightback.dto.internal.user.UserResponseDto;
import org.example.eventspotlightback.model.Address;
import org.example.eventspotlightback.model.Category;
import org.example.eventspotlightback.model.City;
import org.example.eventspotlightback.model.Contact;
import org.example.eventspotlightback.model.Description;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.model.Favorite;
import org.example.eventspotlightback.model.MyEvents;
import org.example.eventspotlightback.model.Photo;
import org.example.eventspotlightback.model.Role;
import org.example.eventspotlightback.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class TestUtil {
    public static final Long TEST_ADDRESS_ID = 1L;
    public static final String TEST_ADDRESS_STREET = "TestStreet";
    public static final String TEST_ADDRESS_NUMBER = "TestNumber";

    public static final Long TEST_CITY_ID = 2L;
    public static final String TEST_CITY_NAME = "TestCityName";

    public static final Long TEST_CATEGORY_ID = 3L;
    public static final String TEST_CATEGORY_NAME = "Test Category Name";

    public static final Long TEST_CONTACT_ID = 4L;
    public static final String TEST_CONTACT_PHONE_NUMBER = "380961111111";
    public static final String TEST_CONTACT_EMAIL = "test@example.com";
    public static final String TEST_CONTACT_INSTAGRAM = "@testInstagram";
    public static final String TEST_CONTACT_TELEGRAM = "@testTelegram";
    public static final String TEST_CONTACT_FACEBOOK = "@testFacebook";

    public static final Long TEST_DESCRIPTION_ID = 5L;
    public static final String TEST_DESCRIPTION_TITLE = "testTitle";
    public static final String TEST_DESCRIPTION_DESCRIPTION = "testDescription";

    public static final Long TEST_EVENT_ID = 6L;
    public static final String TEST_EVENT_TITLE = "Test Event Title";
    public static final LocalDateTime TEST_EVENT_START_TIME = LocalDateTime
            .of(2024, 11, 19, 6, 0);
    public static final BigDecimal TEST_EVENT_PRICE = BigDecimal.valueOf(100.0);

    public static final Long TEST_USER_ID = 7L;
    public static final String TEST_USER_NAME = "Test User Name";
    public static final String TEST_USER_EMAIL = "Test User Email";
    public static final String TEST_USER_PASSWORD = "Test User Password";
    public static final Long TEST_ROLE_ID = 8L;

    public static final Long TEST_MY_EVENTS_ID = TEST_USER_ID;
    public static final Long TEST_FAVORITE_ID = TEST_USER_ID;

    public static final Long TEST_PHOTO_ID = 8L;
    public static final String TEST_PHOTO_PATH = "/test/path";
    public static final LocalDateTime TEST_PHOTO_CREATED_AT = LocalDateTime
            .of(2024, 9, 22, 8, 45, 54);
    public static final String TEST_PHOTO_SHARED_URL = "/url/test/sharing";

    public static final City testCity = new City()
            .setId(TEST_CITY_ID)
            .setName(TEST_CITY_NAME);
    public static final CityDto testCityDto = new CityDto()
            .setId(TEST_CITY_ID)
            .setName(TEST_CITY_NAME);
    public static final AddCityDto addCityDto = new AddCityDto()
            .setName(TEST_CITY_NAME);

    public static final Category testCategory = new Category()
            .setId(TEST_CATEGORY_ID)
            .setName(TEST_CATEGORY_NAME);
    public static final CategoryDto testCategoryDto = new CategoryDto()
            .setId(TEST_CATEGORY_ID)
            .setName(TEST_CATEGORY_NAME);
    public static final CreateCategoryDto addCategoryDto = new CreateCategoryDto()
            .setName(TEST_CATEGORY_NAME);

    public static final Contact testContact = new Contact()
            .setId(TEST_CONTACT_ID)
            .setPhoneNumber(TEST_CONTACT_PHONE_NUMBER)
            .setEmail(TEST_CONTACT_EMAIL)
            .setInstagram(TEST_CONTACT_INSTAGRAM)
            .setTelegram(TEST_CONTACT_TELEGRAM)
            .setFacebook(TEST_CONTACT_FACEBOOK);
    public static final ContactDto testContactDto = new ContactDto()
            .setId(TEST_CONTACT_ID)
            .setPhoneNumber(TEST_CONTACT_PHONE_NUMBER)
            .setEmail(TEST_CONTACT_EMAIL)
            .setInstagram(TEST_CONTACT_INSTAGRAM)
            .setTelegram(TEST_CONTACT_TELEGRAM)
            .setFacebook(TEST_CONTACT_FACEBOOK);
    public static final CreateContactDto addContactDto = new CreateContactDto()
            .setPhoneNumber(TEST_CONTACT_PHONE_NUMBER)
            .setEmail(TEST_CONTACT_EMAIL)
            .setInstagram(TEST_CONTACT_INSTAGRAM)
            .setTelegram(TEST_CONTACT_TELEGRAM)
            .setFacebook(TEST_CONTACT_FACEBOOK);

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

    public static final Address testAddress = new Address()
            .setId(TEST_ADDRESS_ID)
            .setCity(testCity)
            .setStreet(TEST_ADDRESS_STREET)
            .setNumber(TEST_ADDRESS_NUMBER);
    public static final AddressDto testAddressDto = new AddressDto()
            .setId(TEST_ADDRESS_ID)
            .setCityId(TEST_CITY_ID)
            .setStreet(TEST_ADDRESS_STREET)
            .setNumber(TEST_ADDRESS_NUMBER);
    public static final AddAddressDto addAddressDto = new AddAddressDto()
            .setCityId(TEST_CITY_ID)
            .setStreet(TEST_ADDRESS_STREET)
            .setNumber(TEST_ADDRESS_NUMBER);


    public static final Role testUserRole = new Role()
            .setId(TEST_ROLE_ID)
            .setRoleName(Role.RoleName.USER);
    public static final User testUser = new User()
            .setId(TEST_USER_ID)
            .setUserName(TEST_USER_NAME)
            .setEmail(TEST_USER_EMAIL)
            .setPassword(TEST_USER_PASSWORD)
            .setRoles(Set.of(testUserRole));
    public static final UserResponseDto testUserDto = new UserResponseDto()
            .setId(TEST_USER_ID)
            .setUserName(TEST_USER_NAME)
            .setEmail(TEST_USER_EMAIL);
    public static final UserRegistrationRequestDto testUserRegistrationDto =
            new UserRegistrationRequestDto()
                    .setUserName(TEST_USER_NAME)
                    .setEmail(TEST_USER_EMAIL)
                    .setPassword(TEST_USER_PASSWORD)
                    .setRepeatPassword(TEST_USER_PASSWORD);

    public static final MyEvents testMyEvents = new MyEvents()
            .setId(TEST_MY_EVENTS_ID)
            .setUser(testUser);
    public static final Favorite testFavorite = new Favorite()
            .setId(TEST_FAVORITE_ID)
            .setUser(testUser);
    public static final FavoriteDto testFavoriteDto = new FavoriteDto()
            .setId(TEST_FAVORITE_ID)
            .setEvents(Collections.emptyList());

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

    public static final Photo testPhoto = new Photo()
            .setId(TEST_PHOTO_ID)
            .setPath(TEST_PHOTO_PATH)
            .setCreatedAt(TEST_PHOTO_CREATED_AT)
            .setSharedUrl(TEST_PHOTO_SHARED_URL);
    public static final PhotoDto testPhotoDto = new PhotoDto()
            .setId(TEST_PHOTO_ID)
            .setCreatedAt(TEST_PHOTO_CREATED_AT.toString())
            .setSharedUrl(TEST_PHOTO_SHARED_URL);
}
