package org.example.eventspotlightback.mapper;

import java.util.List;
import java.util.Set;
import org.example.eventspotlightback.config.MapperConfig;
import org.example.eventspotlightback.dto.internal.photo.PhotoDto;
import org.example.eventspotlightback.model.Photo;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface PhotoMapper {
    Photo toModel(PhotoDto photoDto);

    Set<Photo> toModel(Set<PhotoDto> photoDto);

    PhotoDto toDto(Photo photo);

    List<PhotoDto> toDto(List<Photo> photos);
}
