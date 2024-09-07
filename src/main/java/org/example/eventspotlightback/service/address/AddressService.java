package org.example.eventspotlightback.service.address;

import java.util.List;
import org.example.eventspotlightback.dto.internal.address.AddAddressDto;
import org.example.eventspotlightback.dto.internal.address.AddressDto;

public interface AddressService {
    AddressDto addAddress(AddAddressDto address);

    AddressDto updateAddress(Long id, AddAddressDto address);

    void deleteAddressById(Long addressId);

    List<AddressDto> findAll();

    AddressDto findAddressById(Long addressId);
}
