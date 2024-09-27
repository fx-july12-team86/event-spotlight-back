package org.example.eventspotlightback.service;

import java.util.List;
import java.util.Optional;
import org.example.eventspotlightback.dto.internal.contact.ContactDto;
import org.example.eventspotlightback.dto.internal.contact.CreateContactDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.ContactMapper;
import org.example.eventspotlightback.model.Contact;
import org.example.eventspotlightback.repository.ContactRepository;
import org.example.eventspotlightback.service.contact.ContactServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.example.eventspotlightback.utils.ContactTestUtil.TEST_CONTACT_EMAIL;
import static org.example.eventspotlightback.utils.ContactTestUtil.TEST_CONTACT_FACEBOOK;
import static org.example.eventspotlightback.utils.ContactTestUtil.TEST_CONTACT_ID;
import static org.example.eventspotlightback.utils.ContactTestUtil.TEST_CONTACT_PHONE_NUMBER;
import static org.example.eventspotlightback.utils.ContactTestUtil.TEST_CONTACT_TELEGRAM;
import static org.example.eventspotlightback.utils.ContactTestUtil.TEST_UPDATE_CONTACT_INSTAGRAM;
import static org.example.eventspotlightback.utils.ContactTestUtil.addContactDto;
import static org.example.eventspotlightback.utils.ContactTestUtil.testContact;
import static org.example.eventspotlightback.utils.ContactTestUtil.testContactDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private ContactMapper contactMapper;
    @Mock
    private ContactRepository contactRepository;
    @InjectMocks
    private ContactServiceImpl contactService;

    private Contact updatedTestContact;
    private ContactDto updatedTestContactDto;
    private CreateContactDto updateContactDto;

    @BeforeEach
    void setUp() {
        updatedTestContact = new Contact()
                .setId(TEST_CONTACT_ID)
                .setPhoneNumber(TEST_CONTACT_PHONE_NUMBER)
                .setEmail(TEST_CONTACT_EMAIL)
                .setInstagram(TEST_UPDATE_CONTACT_INSTAGRAM)
                .setTelegram(TEST_CONTACT_TELEGRAM)
                .setFacebook(TEST_CONTACT_FACEBOOK);

        updatedTestContactDto = new ContactDto()
                .setId(TEST_CONTACT_ID)
                .setPhoneNumber(TEST_CONTACT_PHONE_NUMBER)
                .setEmail(TEST_CONTACT_EMAIL)
                .setInstagram(TEST_UPDATE_CONTACT_INSTAGRAM)
                .setTelegram(TEST_CONTACT_TELEGRAM)
                .setFacebook(TEST_CONTACT_FACEBOOK);

        updateContactDto = new CreateContactDto()
                .setPhoneNumber(TEST_CONTACT_PHONE_NUMBER)
                .setEmail(TEST_CONTACT_EMAIL)
                .setInstagram(TEST_UPDATE_CONTACT_INSTAGRAM)
                .setTelegram(TEST_CONTACT_TELEGRAM)
                .setFacebook(TEST_CONTACT_FACEBOOK);
    }

    @Test
    @DisplayName("Add new Contact to DB")
    public void createContact_ValidCreateContactDto_newContactDto() {
        //Given
        when(contactMapper.toModel(any(CreateContactDto.class))).thenReturn(testContact);
        when(contactRepository.save(any(Contact.class))).thenReturn(testContact);
        when(contactMapper.toDto(any(Contact.class))).thenReturn(testContactDto);

        //When
        ContactDto expected = testContactDto;
        ContactDto actual = contactService.createContact(addContactDto);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update Contact by id - valid")
    public void updateContactById_ValidUpdateContactDto_updatedContactDto() {
        //Given
        when(contactMapper.toModel(any(CreateContactDto.class))).thenReturn(updatedTestContact);
        when(contactRepository.findById(anyLong())).thenReturn(Optional.of(testContact));
        when(contactRepository.save(any(Contact.class))).thenReturn(updatedTestContact);
        when(contactMapper.toDto(any(Contact.class))).thenReturn(updatedTestContactDto);

        //When
        ContactDto expected = updatedTestContactDto;
        ContactDto actual = contactService.updateContact(TEST_CONTACT_ID, updateContactDto);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update Contact with not existing Contact id")
    public void updateContactById_InvalidId_EntityNotFoundException() {
        //Given
        when(contactMapper.toModel(any(CreateContactDto.class))).thenReturn(updatedTestContact);
        when(contactRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> contactService.updateContact(TEST_CONTACT_ID, updateContactDto)
        );
        String expected = "Can't find contact with id: " + TEST_CONTACT_ID;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete Contact by id - valid")
    public void deleteContactById_ContactId_Empty() {
        contactService.deleteContactById(TEST_CONTACT_ID);
        verify(contactRepository, times(1)).deleteById(TEST_CONTACT_ID);
    }

    @Test
    @DisplayName("Find all Contacts")
    public void findAllContacts_Empty_ListOfContactDto() {
        //Given
        when(contactRepository.findAll()).thenReturn(List.of(testContact));
        when(contactMapper.toDto(anyList())).thenReturn(List.of(testContactDto));

        //When
        List<ContactDto> expected = List.of(testContactDto);
        List<ContactDto> actual = contactService.findAllContacts();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find Contact by id - valid")
    public void findContactById_ContactId_ContactDto() {
        //Given
        when(contactRepository.findById(TEST_CONTACT_ID)).thenReturn(Optional.of(testContact));
        when(contactMapper.toDto(any(Contact.class))).thenReturn(testContactDto);

        //When
        ContactDto expected = testContactDto;
        ContactDto actual = contactService.findContactById(TEST_CONTACT_ID);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find Contact by id with not existing id")
    public void findContactById_InvalidId_EntityNotFoundException() {
        //Given
        when(contactRepository.findById(TEST_CONTACT_ID)).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> contactService.findContactById(TEST_CONTACT_ID)
        );
        String expected = "Can't find contact with id: " + TEST_CONTACT_ID;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }
}
