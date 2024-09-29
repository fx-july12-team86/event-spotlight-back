package org.example.eventspotlightback.service.contact;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.contact.ContactDto;
import org.example.eventspotlightback.dto.internal.contact.CreateContactDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.ContactMapper;
import org.example.eventspotlightback.model.Contact;
import org.example.eventspotlightback.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    @Qualifier
    private final ContactMapper contactMapper;

    @Override
    public ContactDto createContact(CreateContactDto contact) {
        Contact newContact = contactMapper.toModel(contact);
        return contactMapper.toDto(contactRepository.save(newContact));
    }

    @Override
    public ContactDto updateContact(Long id, CreateContactDto contact) {
        Contact newContact = contactMapper.toModel(contact);
        newContact.setId(id);
        contactRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find contact with id: " + id)
        );
        return contactMapper.toDto(contactRepository.save(newContact));
    }

    @Override
    public void deleteContactById(Long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public List<ContactDto> findAllContacts() {
        return contactMapper.toDto(contactRepository.findAll());
    }

    @Override
    public ContactDto findContactById(Long id) {
        Contact contact = contactRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find contact with id: " + id)
        );
        return contactMapper.toDto(contact);
    }
}
