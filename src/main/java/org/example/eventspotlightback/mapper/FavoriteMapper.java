package org.example.eventspotlightback.mapper;

import java.util.List;
import org.example.eventspotlightback.config.MapperConfig;
import org.example.eventspotlightback.dto.internal.favorite.FavoriteDto;
import org.example.eventspotlightback.model.Favorite;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, uses = {EventMapper.class})
public interface FavoriteMapper {

    FavoriteDto toDto(Favorite favorite);

    List<FavoriteDto> toDto(List<Favorite> favorites);
}
