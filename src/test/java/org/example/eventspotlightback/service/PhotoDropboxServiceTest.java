package org.example.eventspotlightback.service;

import static org.example.eventspotlightback.utils.PhotoTestUtil.TEST_PHOTO_ID;
import static org.example.eventspotlightback.utils.PhotoTestUtil.TEST_PHOTO_PATH;
import static org.example.eventspotlightback.utils.PhotoTestUtil.TEST_PHOTO_SHARED_URL;
import static org.example.eventspotlightback.utils.PhotoTestUtil.testPhoto;
import static org.example.eventspotlightback.utils.PhotoTestUtil.testPhotoDto;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.example.eventspotlightback.dto.internal.photo.PhotoDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.PhotoMapper;
import org.example.eventspotlightback.model.Photo;
import org.example.eventspotlightback.repository.PhotoRepository;
import org.example.eventspotlightback.service.dropbox.DropboxService;
import org.example.eventspotlightback.service.photo.PhotoDropboxServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
public class PhotoDropboxServiceTest {

    @Mock
    private PhotoRepository photoRepository;
    @Mock
    private DropboxService dropboxService;
    @Mock
    private PhotoMapper photoMapper;
    @InjectMocks
    private PhotoDropboxServiceImpl photoDropboxService;

    @Test
    @DisplayName("Upload photo - valid")
    public void uploadPhoto_MultipartFile_PhotoDto() throws IOException {
        //Given
        MultipartFile testPhotoFile = mock(MultipartFile.class);
        when(dropboxService.uploadFile(testPhotoFile)).thenReturn(TEST_PHOTO_PATH);
        when(dropboxService.getSharedLink(TEST_PHOTO_PATH)).thenReturn(TEST_PHOTO_SHARED_URL);
        when(photoRepository.save(any(Photo.class))).thenReturn(testPhoto);
        when(photoMapper.toDto(testPhoto)).thenReturn(testPhotoDto);

        //When
        PhotoDto expected = testPhotoDto;
        PhotoDto actual = photoDropboxService.uploadPhoto(testPhotoFile);

        //Then
        assertEquals(expected, actual);
        assertNotNull(actual);
        verify(dropboxService).uploadFile(testPhotoFile);
        verify(dropboxService).getSharedLink(TEST_PHOTO_PATH);
        verify(photoRepository).save(any(Photo.class));
        verify(photoMapper).toDto(testPhoto);
    }

    @Test
    @DisplayName("Find all photos")
    public void findAll_Empty_ListOfPhotoDto() {
        //Given
        when(photoRepository.findAll()).thenReturn(List.of(testPhoto));
        when(photoMapper.toDto(anyList())).thenReturn(List.of(testPhotoDto));

        //When
        List<PhotoDto> expected = List.of(testPhotoDto);
        List<PhotoDto> actual = photoDropboxService.findAllPhotos();

        //Then
        assertEquals(expected, actual);
        verify(photoRepository, times(1)).findAll();
        verify(photoMapper, times(1)).toDto(List.of(testPhoto));
    }

    @Test
    @DisplayName("Find Photo by id - valid")
    public void findPhotoById_PhotoId_PhotoDto() {
        //Given
        when(photoRepository.findById(TEST_PHOTO_ID))
                .thenReturn(Optional.of(testPhoto));
        when(photoMapper.toDto(any(Photo.class)))
                .thenReturn(testPhotoDto);

        //When
        PhotoDto expected = testPhotoDto;
        PhotoDto actual = photoDropboxService.findPhotoById(TEST_PHOTO_ID);

        //Then
        assertEquals(expected, actual);
        verify(photoRepository, times(1)).findById(TEST_PHOTO_ID);
        verify(photoMapper, times(1)).toDto(testPhoto);
    }

    @Test
    @DisplayName("Find Photo by id with not existing id")
    public void findPhotoById_InvalidId_PhotoDto() {
        //Given
        when(photoRepository.findById(TEST_PHOTO_ID)).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> photoDropboxService.findPhotoById(TEST_PHOTO_ID)
        );
        String expected = "Can't find photo by id: " + TEST_PHOTO_ID;
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete Photo by id")
    public void deletePhoto_PhotoId_Void() {
        //Given
        when(photoRepository.findById(TEST_PHOTO_ID)).thenReturn(Optional.of(testPhoto));
        photoDropboxService.deletePhoto(TEST_PHOTO_ID);

        //Then
        verify(dropboxService, times(1)).deleteFile(testPhoto.getPath());
        verify(photoRepository, times(1)).delete(testPhoto);
    }

    @Test
    @DisplayName("Delete Photo by id")
    public void deletePhoto_invalidPhotoId_Void() {
        //Given
        when(photoRepository.findById(TEST_PHOTO_ID)).thenReturn(Optional.empty());
        photoDropboxService.deletePhoto(TEST_PHOTO_ID);

        //Then
        verify(dropboxService, times(0)).deleteFile(testPhoto.getPath());
        verify(photoRepository, times(0)).delete(testPhoto);
    }
}
