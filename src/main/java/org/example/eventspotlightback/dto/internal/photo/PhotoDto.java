package org.example.eventspotlightback.dto.internal.photo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PhotoDto {
    private long id;
    private String sharedUrl;
    private String createdAt;
}
