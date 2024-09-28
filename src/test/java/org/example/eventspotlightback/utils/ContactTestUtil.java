package org.example.eventspotlightback.utils;

import org.example.eventspotlightback.dto.internal.city.CityDto;
import org.example.eventspotlightback.dto.internal.contact.ContactDto;
import org.example.eventspotlightback.dto.internal.contact.CreateContactDto;
import org.example.eventspotlightback.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactTestUtil {
    public static final Long TEST_CONTACT_ID = 97L;
    public static final String TEST_CONTACT_PHONE_NUMBER = "+380961111111";
    public static final String TEST_CONTACT_EMAIL = "test@example.com";
    public static final String TEST_CONTACT_INSTAGRAM = "@testInstagram";
    public static final String TEST_CONTACT_TELEGRAM = "@testTelegram";
    public static final String TEST_CONTACT_FACEBOOK = "@testFacebook";

    public static final String TEST_UPDATE_CONTACT_INSTAGRAM = "@testUpdatedInstagram";

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

    public static final CreateContactDto updateContactDto = new CreateContactDto()
            .setPhoneNumber(TEST_CONTACT_PHONE_NUMBER)
            .setEmail(TEST_CONTACT_EMAIL)
            .setInstagram(TEST_UPDATE_CONTACT_INSTAGRAM)
            .setTelegram(TEST_CONTACT_TELEGRAM)
            .setFacebook(TEST_CONTACT_FACEBOOK);

    public static final ContactDto updatedContactDto = new ContactDto()
            .setId(TEST_CONTACT_ID)
            .setPhoneNumber(TEST_CONTACT_PHONE_NUMBER)
            .setEmail(TEST_CONTACT_EMAIL)
            .setInstagram(TEST_UPDATE_CONTACT_INSTAGRAM)
            .setTelegram(TEST_CONTACT_TELEGRAM)
            .setFacebook(TEST_CONTACT_FACEBOOK);

    public static List<ContactDto> getTestListWithContacts() {
        ContactDto first = new ContactDto()
                .setId(1L)
                .setPhoneNumber("+38063111111")
                .setEmail("firstEmail@example.com")
                .setInstagram("@firstTestInstagram")
                .setTelegram("@firstTestTelegram")
                .setFacebook("@firstTestFacebook");
        ContactDto second = new ContactDto()
                .setId(2L)
                .setPhoneNumber("+38063222222")
                .setEmail("secondEmail@example.com")
                .setInstagram("@secondTestInstagram")
                .setTelegram("@secondTestTelegram")
                .setFacebook("@secondTestFacebook");
        ContactDto third = new ContactDto()
                .setId(3L)
                .setPhoneNumber("+38063333333")
                .setEmail("thirdEmail@example.com")
                .setInstagram("@thirdTestInstagram")
                .setTelegram("@thirdTestTelegram")
                .setFacebook("@thirdTestFacebook");
        List<ContactDto> testList = new ArrayList<>();
        testList.add(first);
        testList.add(second);
        testList.add(third);
        return testList;
    }

}
