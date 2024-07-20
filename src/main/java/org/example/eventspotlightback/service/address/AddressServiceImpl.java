package org.example.eventspotlightback.service.address;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.address.AddAddressDto;
import org.example.eventspotlightback.dto.internal.address.AddressDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.AddressMapper;
import org.example.eventspotlightback.model.Address;
import org.example.eventspotlightback.model.City;
import org.example.eventspotlightback.repository.AddressRepository;
import org.example.eventspotlightback.repository.CityRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final CityRepository cityRepository;

    @Override
    public AddressDto addAddress(AddAddressDto addressRequestDto) {
        Address newAddress = addressMapper.toModel(addressRequestDto);
        City city = cityRepository.findById(addressRequestDto.getCityId()).orElseThrow(
                () -> new EntityNotFoundException("City with id " + addressRequestDto.getCityId()
                        + "not found for address " + addressRequestDto.getStreet() + " : "
                        + addressRequestDto.getNumber())
        );
        newAddress.setCity(city);
        return addressMapper.toDto(addressRepository.save(newAddress));
    }

    @Override
    public List<AddressDto> findAll() {
        return addressMapper.toDto(addressRepository.findAll());
    }

    @Override
    public AddressDto findAddressById(long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(
                () -> new EntityNotFoundException("Address with id" + addressId + " not found"));
        return addressMapper.toDto(address);
    }

    @Override
    public AddressDto updateAddress(long id, AddAddressDto updateAddressDto) {
        Address updatedAddress = addressMapper.toModel(updateAddressDto);
        updatedAddress.setId(id);
        addressRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Address with id" + id + " not found")
        );
        City city = cityRepository.findById(updateAddressDto.getCityId()).orElseThrow(
                () -> new EntityNotFoundException("City with id " + updateAddressDto.getCityId())
        );
        updatedAddress.setCity(city);
        return addressMapper.toDto(addressRepository.save(updatedAddress));
    }

    @Override
    public void deleteAddressById(long addressId) {
        addressRepository.deleteById(addressId);
    }
}
