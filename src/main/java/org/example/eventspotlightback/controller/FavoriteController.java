package org.example.eventspotlightback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.favorite.FavoriteDto;
import org.example.eventspotlightback.model.User;
import org.example.eventspotlightback.service.favorite.FavoriteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Favorite management", description = "Endpoint for managing Favorites")
@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @Operation(
            summary = "Add Event to User's favorite"
    )
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{eventId}")
    public FavoriteDto addToFavorite(Authentication authentication, @PathVariable Long eventId) {
        User user = (User) authentication.getPrincipal();
        return favoriteService.addEvent(eventId, user.getId());
    }

    @Operation(
            summary = "Remove event from User's favorite"
    )
    @PreAuthorize("hasAnyAuthority('USER')")
    @DeleteMapping("/{eventId}")
    public FavoriteDto deleteFromFavorite(
            Authentication authentication,
            @PathVariable Long eventId
    ) {
        User user = (User) authentication.getPrincipal();
        return favoriteService.removeEventFromFavorite(eventId, user.getId());
    }

    @Operation(
            summary = "Find all User's favorites"
    )
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public FavoriteDto findByUserId(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return favoriteService.findFavoriteByUserId(user.getId());
    }
}
