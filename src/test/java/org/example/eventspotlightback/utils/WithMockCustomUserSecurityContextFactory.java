package org.example.eventspotlightback.utils;

import java.util.Set;
import org.example.eventspotlightback.model.Role;
import org.example.eventspotlightback.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory
        implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Role role = new Role()
                .setId(2L)
                .setRoleName(Role.RoleName.valueOf(customUser.authorities()[0]));

        User principal = new User()
                .setId(customUser.id())
                .setUserName(customUser.username())
                .setPassword("password")
                .setRoles(Set.of(role));

        Authentication auth = new UsernamePasswordAuthenticationToken(
                principal,
                "password",
                principal.getAuthorities()
        );
        context.setAuthentication(auth);
        return context;
    }
}
