package org.example.eventspotlightback.utils;

import org.example.eventspotlightback.dto.internal.address.AddAddressDto;
import org.example.eventspotlightback.dto.internal.address.AddressDto;
import org.example.eventspotlightback.model.Address;

import static org.example.eventspotlightback.utils.CityTestUtil.TEST_CITY_ID;
import static org.example.eventspotlightback.utils.CityTestUtil.testCity;

public class AddressTestUtil {
    public static final Long TEST_ADDRESS_ID = 1L;
    public static final String TEST_ADDRESS_STREET = "TestStreet";
    public static final String TEST_ADDRESS_NUMBER = "TestNumber";
    public static final String TEST_UPDATE_ADDRESS_STREET = "UpdateTestStreet";

    public static final Address testAddress = new Address()
            .setId(TEST_ADDRESS_ID)
            .setCity(testCity)
            .setStreet(TEST_ADDRESS_STREET)
            .setNumber(TEST_ADDRESS_NUMBER);
    public static final AddressDto testAddressDto = new AddressDto()
            .setId(TEST_ADDRESS_ID)
            .setCityId(TEST_CITY_ID)
            .setStreet(TEST_ADDRESS_STREET)
            .setNumber(TEST_ADDRESS_NUMBER);
    public static final AddAddressDto addAddressDto = new AddAddressDto()
            .setCityId(TEST_CITY_ID)
            .setStreet(TEST_ADDRESS_STREET)
            .setNumber(TEST_ADDRESS_NUMBER);
}
