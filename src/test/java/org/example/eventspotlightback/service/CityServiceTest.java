package org.example.eventspotlightback.service;

import java.util.List;
import java.util.Optional;
import org.example.eventspotlightback.dto.internal.city.AddCityDto;
import org.example.eventspotlightback.dto.internal.city.CityDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.CityMapper;
import org.example.eventspotlightback.model.City;
import org.example.eventspotlightback.repository.CityRepository;
import org.example.eventspotlightback.service.city.CityServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.example.eventspotlightback.utils.CityTestUtil.TEST_CITY_ID;
import static org.example.eventspotlightback.utils.CityTestUtil.TEST_UPDATE_CITY_NAME;
import static org.example.eventspotlightback.utils.CityTestUtil.addCityDto;
import static org.example.eventspotlightback.utils.CityTestUtil.testCity;
import static org.example.eventspotlightback.utils.CityTestUtil.testCityDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock
    private CityMapper cityMapper;
    @Mock
    private CityRepository cityRepository;
    @InjectMocks
    private CityServiceImpl cityService;

    private City testUpdatedCity;
    private CityDto testUpdatedCityDto;
    private AddCityDto updateCityDto;

    @BeforeEach
    void setUp() {

        testUpdatedCity = new City()
                .setId(TEST_CITY_ID)
                .setName(TEST_UPDATE_CITY_NAME);

        updateCityDto = new AddCityDto()
                .setName(TEST_UPDATE_CITY_NAME);

        testUpdatedCityDto = new CityDto()
                .setId(TEST_CITY_ID)
                .setName(TEST_UPDATE_CITY_NAME);
    }

    @Test
    @DisplayName("Add new City to DB")
    public void addCity_WithValidRequest_returnNewCityDto() {
        //Given
        when(cityMapper.toModel(any(AddCityDto.class))).thenReturn(testCity);
        when(cityRepository.save(any(City.class))).thenReturn(testCity);
        when(cityMapper.toDto(any(City.class))).thenReturn(testCityDto);

        //When
        CityDto expected = testCityDto;
        CityDto actual = cityService.addCity(addCityDto);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update City by id - valid")
    public void updateCityById_WithValidRequest_returnUpdatedCityDto() {
        //Given
        when(cityMapper.toModel(any(AddCityDto.class))).thenReturn(testUpdatedCity);
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(testCity));
        when(cityRepository.save(any(City.class))).thenReturn(testUpdatedCity);
        when(cityMapper.toDto(any(City.class))).thenReturn(testUpdatedCityDto);

        //When
        CityDto expected = testUpdatedCityDto;
        CityDto actual = cityService.updateCityById(TEST_CITY_ID, updateCityDto);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update City with not existing City id")
    public void updateCityById_InvalidId_EntityNotFoundException() {
        //Given
        when(cityMapper.toModel(any(AddCityDto.class))).thenReturn(testUpdatedCity);
        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> cityService.updateCityById(TEST_CITY_ID, updateCityDto)
        );
        String expectedMessage = "Cant update city with id: " + TEST_CITY_ID;
        String actualMessage = exception.getMessage();

        //Then
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Delete city by Id - valid")
    void deleteCityById_CityId_Empty() {
        cityService.deleteCityById(TEST_CITY_ID);
        verify(cityRepository, times(1)).deleteById(TEST_CITY_ID);
    }

    @Test
    @DisplayName("Find all cities")
    public void findAll_Empty_ListOfCityDto() {
        //Given
        when(cityRepository.findAll()).thenReturn(List.of(testCity));
        when(cityMapper.toDto(anyList())).thenReturn(List.of(testCityDto));

        //When
        List<CityDto> expected = List.of(testCityDto);
        List<CityDto> actual = cityService.findAllCities();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find City by id - valid")
    public void findCityById_CityId_CityDto() {
        //Given
        when(cityRepository.findById(TEST_CITY_ID)).thenReturn(Optional.of(testCity));
        when(cityMapper.toDto(any(City.class))).thenReturn(testCityDto);

        //When
        CityDto expected = testCityDto;
        CityDto actual = cityService.findCityById(TEST_CITY_ID);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find City by id with not existing id")
    public void findCityById_InvalidId_EntityNotFoundException() {
        //Given
        when(cityRepository.findById(TEST_CITY_ID)).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> cityService.findCityById(TEST_CITY_ID)
        );
        String expected = "City with id " + TEST_CITY_ID + " not found";
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }
}
