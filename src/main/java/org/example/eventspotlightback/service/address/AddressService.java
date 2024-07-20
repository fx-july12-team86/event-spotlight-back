package org.example.eventspotlightback.service.address;

import java.util.List;
import org.example.eventspotlightback.dto.internal.address.AddAddressDto;
import org.example.eventspotlightback.dto.internal.address.AddressDto;

public interface AddressService {
    AddressDto addAddress(AddAddressDto address);

    List<AddressDto> findAll();

    AddressDto findAddressById(long addressId);

    AddressDto updateAddress(long id, AddAddressDto address);

    void deleteAddressById(long addressId);
}
