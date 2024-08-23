package org.example.eventspotlightback.mapper;

import java.util.List;
import org.example.eventspotlightback.config.MapperConfig;
import org.example.eventspotlightback.dto.internal.address.AddAddressDto;
import org.example.eventspotlightback.dto.internal.address.AddressDto;
import org.example.eventspotlightback.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface AddressMapper {
    @Mapping(source = "city.id", target = "cityId")
    AddressDto toDto(Address address);

    List<AddressDto> toDto(List<Address> addresses);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    Address toModel(AddAddressDto addressDto);
}
