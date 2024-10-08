package org.example.eventspotlightback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.contact.ContactDto;
import org.example.eventspotlightback.dto.internal.contact.CreateContactDto;
import org.example.eventspotlightback.service.contact.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Contact management", description = "Endpoint for managing Contacts")
@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @Operation(
            summary = "Add new Contact"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ContactDto save(@RequestBody @Valid CreateContactDto contactDto) {
        return contactService.createContact(contactDto);
    }

    @Operation(
            summary = "Update exists Contact"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    ContactDto update(@PathVariable Long id, @RequestBody @Valid CreateContactDto contactDto) {
        return contactService.updateContact(id, contactDto);
    }

    @Operation(
            summary = "Delete Contact"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        contactService.deleteContactById(id);
    }

    @Operation(
            summary = "Find Contact by id"
    )
    @GetMapping("/{id}")
    ContactDto findById(@PathVariable Long id) {
        return contactService.findContactById(id);
    }

    @Operation(
            summary = "Find all Contacts"
    )
    @GetMapping
    List<ContactDto> findAll() {
        return contactService.findAllContacts();
    }
}
