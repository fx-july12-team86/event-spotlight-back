package org.example.eventspotlightback.service;

import java.util.List;
import java.util.Optional;
import org.example.eventspotlightback.dto.internal.description.CreateDescriptionDto;
import org.example.eventspotlightback.dto.internal.description.DescriptionDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.DescriptionMapper;
import org.example.eventspotlightback.model.Description;
import org.example.eventspotlightback.repository.DescriptionRepository;
import org.example.eventspotlightback.service.description.DescriptionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.example.eventspotlightback.utils.TestUtil.*;

@ExtendWith(MockitoExtension.class)
public class DescriptionServiceTest {
    private static final String TEST_UPDATE_DESCRIPTION_TITLE = "updateTestTitle";

    @Mock
    private DescriptionMapper descriptionMapper;
    @Mock
    private DescriptionRepository descriptionRepository;
    @InjectMocks
    private DescriptionServiceImpl descriptionService;

    private Description updatedTestDescription;
    private DescriptionDto updatedTestDescriptionDto;
    private CreateDescriptionDto updateDescriptionDto;

    @BeforeEach
    void setUp() {
        updatedTestDescription = new Description()
                .setId(TEST_DESCRIPTION_ID)
                .setTitle(TEST_UPDATE_DESCRIPTION_TITLE)
                .setDescription(TEST_DESCRIPTION_DESCRIPTION);

        updatedTestDescriptionDto = new DescriptionDto()
                .setId(TEST_DESCRIPTION_ID)
                .setTitle(TEST_UPDATE_DESCRIPTION_TITLE)
                .setDescription(TEST_DESCRIPTION_DESCRIPTION);

        updateDescriptionDto = new CreateDescriptionDto()
                .setTitle(TEST_UPDATE_DESCRIPTION_TITLE)
                .setDescription(TEST_DESCRIPTION_DESCRIPTION);
    }

    @Test
    @DisplayName("Add new Description to DB")
    public void addDescription_CreateDescriptionDto_DescriptionDto() {
        //Given
        when(descriptionMapper.toModel(addDescriptionDto)).thenReturn(testDescription);
        when(descriptionRepository.save(any(Description.class))).thenReturn(testDescription);
        when(descriptionMapper.toDto(any(Description.class))).thenReturn(testDescriptionDto);

        //When
        DescriptionDto expected = testDescriptionDto;
        DescriptionDto actual = descriptionService.addDescription(addDescriptionDto);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update Description by id - valid")
    public void updateById_WithValidRequest_returnUpdatedDescriptionDto() {
        //Given
        when(descriptionMapper.toModel(updateDescriptionDto)).thenReturn(updatedTestDescription);
        when(descriptionRepository.findById(anyLong())).thenReturn(Optional.of(testDescription));
        when(descriptionRepository.save(any(Description.class))).thenReturn(updatedTestDescription);
        when(descriptionMapper.toDto(any(Description.class))).thenReturn(updatedTestDescriptionDto);

        //When
        DescriptionDto expected = updatedTestDescriptionDto;
        DescriptionDto actual = descriptionService
                .updateById(TEST_DESCRIPTION_ID, updateDescriptionDto);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update Description with not existing Description id")
    public void updateById_InvalidId_EntityNotFoundException() {
        //Given
        when(descriptionMapper.toModel(updateDescriptionDto)).thenReturn(updatedTestDescription);
        when(descriptionRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> descriptionService.updateById(TEST_DESCRIPTION_ID, updateDescriptionDto)
        );
        String expectedMessage = "Description with id " + TEST_DESCRIPTION_ID + " not found";
        String actualMessage = exception.getMessage();

        //Then
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Delete Description by Id - valid")
    void deleteById_DescriptionId_empty() {
        descriptionService.deleteById(TEST_DESCRIPTION_ID);
        verify(descriptionRepository, times(1)).deleteById(TEST_DESCRIPTION_ID);
    }

    @Test
    @DisplayName("Find all descriptions")
    public void findAll_Empty_ListOfDescriptionDto() {
        //Given
        when(descriptionRepository.findAll()).thenReturn(List.of(testDescription));
        when(descriptionMapper.toDto(anyList())).thenReturn(List.of(testDescriptionDto));

        //When
        List<DescriptionDto> expected = List.of(testDescriptionDto);
        List<DescriptionDto> actual = descriptionService.findAll();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find Description by id - valid")
    public void findDescriptionById_CityId_CityDto() {
        //Given
        when(descriptionRepository.findById(TEST_DESCRIPTION_ID))
                .thenReturn(Optional.of(testDescription));
        when(descriptionMapper.toDto(any(Description.class)))
                .thenReturn(testDescriptionDto);

        //When
        DescriptionDto expected = testDescriptionDto;
        DescriptionDto actual = descriptionService.findById(TEST_DESCRIPTION_ID);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find Description by id with not existing id")
    public void findDescriptionById_InvalidId_EntityNotFoundException() {
        //Given
        when(descriptionRepository.findById(TEST_DESCRIPTION_ID)).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> descriptionService.findById(TEST_DESCRIPTION_ID)
        );
        String expected = "Description with id " + TEST_DESCRIPTION_ID + " not found";
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }
}
