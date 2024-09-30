package org.example.eventspotlightback.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.service.dropbox.DropboxService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "File management", description = "Endpoint for managing files from DropBox")
@RestController
@RequiredArgsConstructor
@RequestMapping("/dropbox")
public class DropBoxController {
    private final DropboxService dropboxService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String upload(@RequestParam("image") MultipartFile image) {
        return dropboxService.uploadFile(image);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/link/{filename}")
    public String getSharedLink(@PathVariable String filename) {
        return dropboxService.getSharedLink("/" + filename);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<byte[]> getFile(@RequestBody String path) {
        byte[] fileBytes = dropboxService.downloadFile(path);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"testPlanet\"")
                .body(fileBytes);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{filename}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable String filename) {
        dropboxService.deleteFile("/" + filename);
    }

}
