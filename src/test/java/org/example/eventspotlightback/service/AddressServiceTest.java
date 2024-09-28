package org.example.eventspotlightback.service;

import static org.example.eventspotlightback.utils.AddressTestUtil.TEST_ADDRESS_ID;
import static org.example.eventspotlightback.utils.AddressTestUtil.TEST_ADDRESS_NUMBER;
import static org.example.eventspotlightback.utils.AddressTestUtil.TEST_ADDRESS_STREET;
import static org.example.eventspotlightback.utils.AddressTestUtil.TEST_UPDATE_ADDRESS_STREET;
import static org.example.eventspotlightback.utils.AddressTestUtil.addAddressDto;
import static org.example.eventspotlightback.utils.AddressTestUtil.testAddress;
import static org.example.eventspotlightback.utils.AddressTestUtil.testAddressDto;
import static org.example.eventspotlightback.utils.CityTestUtil.TEST_CITY_ID;
import static org.example.eventspotlightback.utils.CityTestUtil.testCity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.example.eventspotlightback.dto.internal.address.AddAddressDto;
import org.example.eventspotlightback.dto.internal.address.AddressDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.AddressMapper;
import org.example.eventspotlightback.model.Address;
import org.example.eventspotlightback.repository.AddressRepository;
import org.example.eventspotlightback.repository.CityRepository;
import org.example.eventspotlightback.service.address.AddressServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;
    @Mock
    private AddressMapper addressMapper;
    @Mock
    private CityRepository cityRepository;
    @InjectMocks
    private AddressServiceImpl addressService;

    private Address updatedTestAddress;
    private AddAddressDto updateAddressDto;
    private AddressDto updatedTestAddressDto;

    @BeforeEach
    void setUp() {
        updatedTestAddress = new Address()
                .setId(TEST_ADDRESS_ID)
                .setCity(testCity)
                .setStreet(TEST_UPDATE_ADDRESS_STREET)
                .setNumber(TEST_ADDRESS_NUMBER);

        updateAddressDto = new AddAddressDto()
                .setCityId(TEST_CITY_ID)
                .setStreet(TEST_UPDATE_ADDRESS_STREET)
                .setNumber(TEST_ADDRESS_NUMBER);

        updatedTestAddressDto = new AddressDto()
                .setCityId(TEST_ADDRESS_ID)
                .setCityId(TEST_CITY_ID)
                .setStreet(TEST_UPDATE_ADDRESS_STREET)
                .setNumber(TEST_ADDRESS_NUMBER);
    }

    @Test
    @DisplayName("Add new Address to DB")
    public void addAddress_WithValidRequest_returnNewAddressDto() {
        //Given
        when(addressMapper.toModel(any(AddAddressDto.class))).thenReturn(testAddress);
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(testCity));
        when(addressRepository.save(any(Address.class))).thenReturn(testAddress);
        when(addressMapper.toDto(any(Address.class))).thenReturn(testAddressDto);

        //When
        AddressDto actual = addressService.addAddress(addAddressDto);
        AddressDto expected = testAddressDto;

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("add Address with not existing city")
    public void addAddress_WithInvalidCityId_EntityNotFoundException() {
        //Given
        when(addressMapper.toModel(any(AddAddressDto.class))).thenReturn(testAddress);
        when(cityRepository.findById(TEST_CITY_ID)).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> addressService.addAddress(addAddressDto)
        );
        String expected = "City with id " + TEST_CITY_ID
                + " not found for address " + TEST_ADDRESS_STREET + " : "
                + TEST_ADDRESS_NUMBER;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update Address by id - valid")
    public void updateAddress_ExistingAddress_AddressDto() {
        //Given
        when(addressMapper.toModel(any(AddAddressDto.class)))
                .thenReturn(updatedTestAddress);
        when(addressRepository.findById(TEST_ADDRESS_ID))
                .thenReturn(Optional.of(testAddress));
        when(cityRepository.findById(anyLong()))
                .thenReturn(Optional.of(testCity));
        when(addressRepository.save(any(Address.class))).thenReturn(updatedTestAddress);
        when(addressMapper.toDto(updatedTestAddress)).thenReturn(updatedTestAddressDto);

        //When

        AddressDto expected = updatedTestAddressDto;
        AddressDto actual = addressService.updateAddress(TEST_ADDRESS_ID, updateAddressDto);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update Address by id with not Existing Address")
    public void updateAddress_NotExistingAddress_EntityNotFoundException() {
        //Given
        when(addressMapper.toModel(any(AddAddressDto.class)))
                .thenReturn(updatedTestAddress);
        when(addressRepository.findById(TEST_ADDRESS_ID))
                .thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> addressService.updateAddress(TEST_ADDRESS_ID, updateAddressDto)
        );

        String expected = "Address with id " + TEST_ADDRESS_ID + " not found";
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update Address by id with not Existing City")
    public void updateAddress_NotExistingCity_EntityNotFoundException() {
        //Given
        when(addressMapper.toModel(any(AddAddressDto.class)))
                .thenReturn(updatedTestAddress);
        when(addressRepository.findById(TEST_ADDRESS_ID))
                .thenReturn(Optional.of(testAddress));
        when(cityRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> addressService.updateAddress(TEST_ADDRESS_ID, updateAddressDto)
        );

        String expected = "City with id " + updateAddressDto.getCityId();
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find all addresses")
    public void findAll_Empty_ListOfAddressDto() {
        //Given
        when(addressRepository.findAll()).thenReturn(List.of(testAddress));
        when(addressMapper.toDto(anyList())).thenReturn(List.of(testAddressDto));

        //When
        List<AddressDto> expected = List.of(testAddressDto);
        List<AddressDto> actual = addressService.findAll();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find Address by id - valid")
    public void findAddressById_AddressId_AddressDto() {
        //Given
        when(addressRepository.findById(TEST_ADDRESS_ID))
                .thenReturn(Optional.of(testAddress));
        when(addressMapper.toDto(any(Address.class))).thenReturn(testAddressDto);

        //When
        AddressDto expected = testAddressDto;
        AddressDto actual = addressService.findAddressById(TEST_ADDRESS_ID);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find Address by id with not existsng id")
    public void findAddressById_InvalidAddressId_EntityNotFoundException() {
        //Given
        when(addressRepository.findById(TEST_ADDRESS_ID))
                .thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> addressService.findAddressById(TEST_ADDRESS_ID)
        );
        String expected = "Address with id" + TEST_ADDRESS_ID + " not found";
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete address by Id - valid")
    void testDeleteAddressById() {
        addressService.deleteAddressById(TEST_ADDRESS_ID);
        verify(addressRepository, times(1)).deleteById(TEST_ADDRESS_ID);
    }
}
