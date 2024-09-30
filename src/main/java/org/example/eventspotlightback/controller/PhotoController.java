package org.example.eventspotlightback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.photo.PhotoDto;
import org.example.eventspotlightback.service.photo.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Photo management", description = "Endpoint for managing Photos")
@RestController
@RequiredArgsConstructor
@RequestMapping("/photos")
public class PhotoController {
    private final PhotoService photoService;

    @Operation(
            summary = "Upload new Photo"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PhotoDto uploadPhoto(@RequestParam("photo") MultipartFile photo) {
        return photoService.uploadPhoto(photo);
    }

    @Operation(
            summary = "Upload new several Photos"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/several")
    public Set<PhotoDto> uploadPhotos(@RequestParam("photos") List<MultipartFile> photos) {
        return photoService.uploadPhotos(photos);
    }

    @Operation(
            summary = "Find all Photos"
    )
    @GetMapping
    public List<PhotoDto> findAllPhotos() {
        return photoService.findAllPhotos();
    }

    @Operation(
            summary = "Find photo",
            description = "Find photo by id"
    )
    @PermitAll
    @GetMapping("/{id}")
    public PhotoDto getPhoto(@PathVariable Long id) {
        return photoService.findPhotoById(id);
    }

    @Operation(
            summary = "Delete photo by id",
            description = "Delete photo by id"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhoto(@PathVariable Long id) {
        photoService.deletePhoto(id);
    }
}
