package org.example.eventspotlightback.service.contact;

import java.util.List;
import org.example.eventspotlightback.dto.internal.contact.ContactDto;
import org.example.eventspotlightback.dto.internal.contact.CreateContactDto;

public interface ContactService {
    ContactDto createContact(CreateContactDto contact);

    ContactDto updateContact(Long id, CreateContactDto contact);

    void deleteContactById(Long id);

    ContactDto findContactById(Long id);

    List<ContactDto> findAllContacts();
}
