package org.example.eventspotlightback.utils;

import org.example.eventspotlightback.dto.internal.city.AddCityDto;
import org.example.eventspotlightback.dto.internal.city.CityDto;
import org.example.eventspotlightback.model.City;

public class CityTestUtil {

    public static final Long TEST_CITY_ID = 2L;
    public static final String TEST_CITY_NAME = "TestCityName";
    public static final String TEST_UPDATE_CITY_NAME = "Test Update City Name";

    public static final City testCity = new City()
            .setId(TEST_CITY_ID)
            .setName(TEST_CITY_NAME);
    public static final CityDto testCityDto = new CityDto()
            .setId(TEST_CITY_ID)
            .setName(TEST_CITY_NAME);
    public static final AddCityDto addCityDto = new AddCityDto()
            .setName(TEST_CITY_NAME);
}
