package org.example.eventspotlightback.utils;

import java.util.ArrayList;
import java.util.List;
import org.example.eventspotlightback.dto.internal.city.AddCityDto;
import org.example.eventspotlightback.dto.internal.city.CityDto;
import org.example.eventspotlightback.model.City;

public class CityTestUtil {

    public static final Long TEST_CITY_ID = 99L;
    public static final Long UPDATE_CITY_ID = 99L;
    public static final String TEST_CITY_NAME = "Test City Name";
    public static final String TEST_UPDATE_CITY_NAME = "Test Update City Name";

    public static final City testCity = new City()
            .setId(TEST_CITY_ID)
            .setName(TEST_CITY_NAME);
    public static final CityDto testCityDto = new CityDto()
            .setId(TEST_CITY_ID)
            .setName(TEST_CITY_NAME);
    public static final CityDto updatedCityDto = new CityDto()
            .setId(UPDATE_CITY_ID)
            .setName(TEST_UPDATE_CITY_NAME);
    public static final AddCityDto addCityDto = new AddCityDto()
            .setName(TEST_CITY_NAME);
    public static final AddCityDto updateCityDto = new AddCityDto()
            .setName(TEST_UPDATE_CITY_NAME);

    public static List<CityDto> getTestListWithCities() {
        CityDto kyiv = new CityDto()
                .setId(1L)
                .setName("Kyiv");
        CityDto kharkiv = new CityDto()
                .setId(2L)
                .setName("Kharkiv");
        CityDto dnipro = new CityDto()
                .setId(3L)
                .setName("Dnipro");
        List<CityDto> testList = new ArrayList<>();
        testList.add(dnipro);
        testList.add(kharkiv);
        testList.add(kyiv);
        return testList;
    }
}
