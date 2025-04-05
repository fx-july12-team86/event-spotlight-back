package org.example.eventspotlightback.service.photo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
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
    private static final List<String> ALLOWED_FORMATS = Arrays.asList("jpg", "jpeg", "png");
    private final PhotoRepository photoRepository;
    private final DropboxService dropboxService;
    private final PhotoMapper photoMapper;

    @Transactional
    @Override
    public PhotoDto uploadPhoto(MultipartFile file) {
        MultipartFile resizedFile = resizeImage(file, 510, 510);
        String filePath = dropboxService.uploadFile(resizedFile);
        String sharedUrl = dropboxService.getSharedLink(filePath);
        Photo photo = new Photo();
        photo.setPath(filePath);
        photo.setSharedUrl(sharedUrl);
        photo.setCreatedAt(LocalDateTime.now());
        return photoMapper.toDto(photoRepository.save(photo));
    }

    public MultipartFile resizeImage(MultipartFile file, int width, int height) {
        try {
            BufferedImage originalImage;
            try (InputStream inputStream = file.getInputStream()) {
                originalImage = ImageIO.read(inputStream);
            }

            if (originalImage == null) {
                throw new RuntimeException("Unsupported image format");
            }

            String formatName = getFileExtension(file.getOriginalFilename());
            if (!ALLOWED_FORMATS.contains(formatName)) {
                throw new RuntimeException("Unsupported file format: "
                        + formatName + ". Allowed formats: JPG, PNG");
            }

            BufferedImage resizedImage = Thumbnails.of(originalImage)
                    .size(width, height)
                    .crop(Positions.CENTER)
                    .outputFormat(formatName) // Гарантуємо збереження формату
                    .asBufferedImage();

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ImageIO.write(resizedImage, formatName, outputStream);
                byte[] resizedBytes = outputStream.toByteArray();

                return new ByteArrayMultipartFile(
                        resizedBytes,
                        file.getOriginalFilename(),
                        file.getContentType()
                );
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to resize image", e);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        }
        return "jpg"; // За замовчуванням JPG
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
    public List<PhotoDto> findAllPhotos() {
        return photoMapper.toDto(photoRepository.findAll());
    }

    @Override
    public PhotoDto findPhotoById(Long photoId) {
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
