package org.example.eventspotlightback.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.favorite.FavoriteDto;
import org.example.eventspotlightback.dto.internal.favorite.RequestFavoriteDto;
import org.example.eventspotlightback.service.favorite.FavoriteService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping
    public FavoriteDto addToFavorite(@RequestBody RequestFavoriteDto requestDto) {
        return favoriteService.addEvent(requestDto.getEventId(), requestDto.getUserId());
    }

    @GetMapping("/{userId}")
    public FavoriteDto findByUserId(@PathVariable Long userId) {
        return favoriteService.findFavoriteByUserId(userId);
    }

    @DeleteMapping("/{userId}/{eventId}")
    public void deleteFromFavorite(@PathVariable Long userId, @PathVariable Long eventId) {
        favoriteService.removeEventFromFavorite(eventId, userId);
    }
}
