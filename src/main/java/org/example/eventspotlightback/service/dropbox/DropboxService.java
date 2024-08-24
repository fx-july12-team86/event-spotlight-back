package org.example.eventspotlightback.service.dropbox;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface DropboxService {
    String uploadFile(MultipartFile image);

    List<String> uploadFiles(List<MultipartFile> images);

    String getSharedLink(String path);

    List<String> getSharedLinks(List<String> filePaths);

    byte[] downloadFile(String path);

    void deleteFile(String path);

    void deleteFiles(List<String> path);
}
