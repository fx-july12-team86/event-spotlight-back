package org.example.eventspotlightback.dto.internal.description;

import java.util.List;
import lombok.Data;

@Data
public class DescriptionDto {
    private long id;
    private String title;
    private String description;
    private List<String> contacts;
}
