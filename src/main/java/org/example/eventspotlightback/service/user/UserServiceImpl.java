package org.example.eventspotlightback.service.user;

import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.user.UserRegistrationRequestDto;
import org.example.eventspotlightback.dto.internal.user.UserResponseDto;
import org.example.eventspotlightback.exception.RegistrationException;
import org.example.eventspotlightback.mapper.UserMapper;
import org.example.eventspotlightback.model.Role;
import org.example.eventspotlightback.model.User;
import org.example.eventspotlightback.repository.RoleRepository;
import org.example.eventspotlightback.repository.UserRepository;
import org.example.eventspotlightback.service.favorite.FavoriteService;
import org.example.eventspotlightback.service.my.events.MyEventsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FavoriteService favoriteService;
    private final MyEventsService myEventsService;

    @Transactional
    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can't register user");
        }
        User newUser = userMapper.toModel(requestDto);
        newUser.setRoles(Set.of(roleRepository.getByRoleName(Role.RoleName.USER)));
        User savedUser = userRepository.save(newUser);
        favoriteService.createFavorite(savedUser);
        myEventsService.createMyEvents(savedUser);
        return userMapper.toDto(savedUser);
    }
}
