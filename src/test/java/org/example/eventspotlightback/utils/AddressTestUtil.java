package org.example.eventspotlightback.utils;

import static org.example.eventspotlightback.utils.CityTestUtil.testCity;

import java.util.ArrayList;
import java.util.List;
import org.example.eventspotlightback.dto.internal.address.AddAddressDto;
import org.example.eventspotlightback.dto.internal.address.AddressDto;
import org.example.eventspotlightback.model.Address;

public class AddressTestUtil {
    public static final Long TEST_ADDRESS_ID = 95L;
    public static final Long TEST_ADDRESS_CITY_ID = 1L;
    public static final String TEST_ADDRESS_STREET = "Test Street";
    public static final String TEST_ADDRESS_NUMBER = "Test Number";
    public static final String TEST_UPDATE_ADDRESS_STREET = "Update Test Street";

    public static final Address testAddress = new Address()
            .setId(TEST_ADDRESS_ID)
            .setCity(testCity)
            .setStreet(TEST_ADDRESS_STREET)
            .setNumber(TEST_ADDRESS_NUMBER);
    public static final AddressDto testAddressDto = new AddressDto()
            .setId(TEST_ADDRESS_ID)
            .setCityId(TEST_ADDRESS_CITY_ID)
            .setStreet(TEST_ADDRESS_STREET)
            .setNumber(TEST_ADDRESS_NUMBER);
    public static final AddAddressDto addAddressDto = new AddAddressDto()
            .setCityId(TEST_ADDRESS_CITY_ID)
            .setStreet(TEST_ADDRESS_STREET)
            .setNumber(TEST_ADDRESS_NUMBER);

    public static final AddAddressDto updateAddressDto = new AddAddressDto()
            .setStreet(TEST_UPDATE_ADDRESS_STREET)
            .setNumber(TEST_ADDRESS_NUMBER)
            .setCityId(TEST_ADDRESS_CITY_ID);
    public static final AddressDto updatedAddressDto = new AddressDto()
            .setId(TEST_ADDRESS_ID)
            .setId(TEST_ADDRESS_CITY_ID)
            .setStreet(TEST_UPDATE_ADDRESS_STREET)
            .setNumber(TEST_ADDRESS_NUMBER);

    public static List<AddressDto> getTestListWithAddresses() {
        AddressDto first = new AddressDto()
                .setId(1L)
                .setCityId(1L)
                .setStreet("first Test Street")
                .setNumber("first Test Number");
        AddressDto second = new AddressDto()
                .setId(2L)
                .setCityId(1L)
                .setStreet("second Test Street")
                .setNumber("second Test Number");
        AddressDto third = new AddressDto()
                .setId(3L)
                .setCityId(1L)
                .setStreet("third Test Street")
                .setNumber("third Test Number");
        List<AddressDto> testList = new ArrayList<>();
        testList.add(first);
        testList.add(second);
        testList.add(third);
        return testList;
    }
}
