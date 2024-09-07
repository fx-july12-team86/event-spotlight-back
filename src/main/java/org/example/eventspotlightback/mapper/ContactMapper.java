package org.example.eventspotlightback.mapper;

import java.util.List;
import org.example.eventspotlightback.config.MapperConfig;
import org.example.eventspotlightback.dto.internal.contact.ContactDto;
import org.example.eventspotlightback.dto.internal.contact.CreateContactDto;
import org.example.eventspotlightback.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ContactMapper {
    @Mapping(target = "id", ignore = true)
    Contact toModel(CreateContactDto contactDto);

    ContactDto toDto(Contact contact);

    List<ContactDto> toDto(List<Contact> contacts);
}
