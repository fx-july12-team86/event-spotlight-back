package org.example.eventspotlightback.security;

import org.example.eventspotlightback.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")
        ) {
            return null;
        }
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}
