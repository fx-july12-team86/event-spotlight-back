package org.example.eventspotlightback.service.photo;

import java.util.List;
import java.util.Set;
import org.example.eventspotlightback.dto.internal.photo.PhotoDto;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    PhotoDto uploadPhoto(MultipartFile file);

    Set<PhotoDto> uploadPhotos(List<MultipartFile> files);

    PhotoDto getPhoto(Long photoId);

    void deletePhoto(Long photoId);
}
