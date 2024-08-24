package org.example.eventspotlightback.service.photo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.photo.PhotoDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.PhotoMapper;
import org.example.eventspotlightback.model.Photo;
import org.example.eventspotlightback.repository.PhotoRepository;
import org.example.eventspotlightback.service.dropbox.DropboxService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PhotoDropboxServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    private final DropboxService dropboxService;
    private final PhotoMapper photoMapper;

    @Transactional
    @Override
    public PhotoDto uploadPhoto(MultipartFile file) {
        String filePath = dropboxService.uploadFile(file);
        String sharedUrl = dropboxService.getSharedLink(filePath);
        Photo photo = new Photo();
        photo.setPath(filePath);
        photo.setSharedUrl(sharedUrl);
        photo.setCreatedAt(LocalDateTime.now());
        return photoMapper.toDto(photoRepository.save(photo));
    }

    @Transactional
    @Override
    public Set<PhotoDto> uploadPhotos(List<MultipartFile> files) {
        return files.stream()
                .distinct()
                .map(this::uploadPhoto)
                .collect(Collectors.toSet());
    }

    @Override
    public PhotoDto getPhoto(Long photoId) {
        Photo photo = photoRepository.findById(photoId).orElseThrow(
                () -> new EntityNotFoundException("Can't find photo by id: " + photoId)
        );
        return photoMapper.toDto(photo);
    }

    @Override
    public void deletePhoto(Long photoId) {
        Optional<Photo> photo = photoRepository.findById(photoId);
        photo.ifPresent(p -> {
            dropboxService.deleteFile(p.getPath());
            photoRepository.delete(p);
        });
    }
}
