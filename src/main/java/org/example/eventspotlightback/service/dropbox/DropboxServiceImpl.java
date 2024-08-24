package org.example.eventspotlightback.service.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.ListSharedLinksResult;
import com.dropbox.core.v2.sharing.RequestedVisibility;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkSettings;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.exception.DropboxException;
import org.example.eventspotlightback.security.dropbox.DropboxAuthService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DropboxServiceImpl implements DropboxService {

    private static final String clientIdentifier = "eventSpotlight/1.0";

    private final DropboxAuthService dropboxAuthService;

    private DbxClientV2 getDbxClient() {
        return new DbxClientV2(
                DbxRequestConfig.newBuilder(clientIdentifier).build(),
                dropboxAuthService.getAccessToken()
        );
    }

    @Override
    public String uploadFile(MultipartFile image) {
        DbxClientV2 dropboxClient = getDbxClient();
        try (InputStream in = new ByteArrayInputStream(image.getBytes())) {
            FileMetadata fileMetadata = dropboxClient.files()
                    .uploadBuilder("/" + image.getOriginalFilename())
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(in);
            return fileMetadata.getPathLower();

        } catch (IOException | DbxException e) {
            throw new DropboxException("Failed to upload file to the Dropbox", e);
        }
    }

    @Override
    public List<String> uploadFiles(List<MultipartFile> images) {
        List<String> filePaths = new ArrayList<>();
        for (MultipartFile image : images) {
            filePaths.add(uploadFile(image));
        }
        return filePaths;
    }

    private String createSharedLinkWithSettings(String path) {
        DbxClientV2 dropboxClient = getDbxClient();
        try {
            SharedLinkSettings settings = SharedLinkSettings.newBuilder()
                    .withRequestedVisibility(RequestedVisibility.PUBLIC)
                    .build();

            SharedLinkMetadata sharedLinkMetadata = dropboxClient.sharing()
                    .createSharedLinkWithSettings(path, settings);
            return sharedLinkMetadata.getUrl();
        } catch (DbxException e) {
            throw new DropboxException("Can't create shared link for path:" + path, e);
        }
    }

    @Override
    public String getSharedLink(String path) {
        DbxClientV2 dropboxClient = getDbxClient();
        try {
            ListSharedLinksResult result = dropboxClient.sharing()
                    .listSharedLinksBuilder()
                    .withPath(path)
                    .withDirectOnly(true)
                    .start();
            if (!result.getLinks().isEmpty()) {
                return result.getLinks().get(0).getUrl();
            }
            return createSharedLinkWithSettings(path);
        } catch (DbxException e) {
            throw new DropboxException("Can't get shared link for path: " + path, e);
        }
    }

    @Override
    public List<String> getSharedLinks(List<String> filePaths) {
        List<String> sharedLinks = new ArrayList<>();
        for (String filePath : filePaths) {
            sharedLinks.add(getSharedLink(filePath));
        }
        return sharedLinks;
    }

    @Override
    public byte[] downloadFile(String path) {
        DbxClientV2 dropboxClient = getDbxClient();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            dropboxClient.files().downloadBuilder(path).download(out);
            return out.toByteArray();
        } catch (IOException | DbxException e) {
            throw new DropboxException("Failed to download file from Dropbox", e);
        }
    }

    @Override
    public void deleteFile(String path) {
        DbxClientV2 dropboxClient = getDbxClient();
        try {
            dropboxClient.files().deleteV2(path);
        } catch (DbxException e) {
            throw new DropboxException("Failed to delete file from Dropbox", e);
        }
    }

    @Override
    public void deleteFiles(List<String> path) {
        for (String filePath : path) {
            deleteFile(filePath);
        }
    }
}
