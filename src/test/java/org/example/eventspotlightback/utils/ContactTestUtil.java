package org.example.eventspotlightback.utils;

import org.example.eventspotlightback.dto.internal.contact.ContactDto;
import org.example.eventspotlightback.dto.internal.contact.CreateContactDto;
import org.example.eventspotlightback.model.Contact;

public class ContactTestUtil {
    public static final Long TEST_CONTACT_ID = 4L;
    public static final String TEST_CONTACT_PHONE_NUMBER = "380961111111";
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
}
