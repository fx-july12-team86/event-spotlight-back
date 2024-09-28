package org.example.eventspotlightback.service;

import static org.example.eventspotlightback.utils.FavoriteTestUtil.testFavorite;
import static org.example.eventspotlightback.utils.MyEventsTestUtil.testMyEvents;
import static org.example.eventspotlightback.utils.UserTestUtil.TEST_USER_ID;
import static org.example.eventspotlightback.utils.UserTestUtil.TEST_USER_PASSWORD;
import static org.example.eventspotlightback.utils.UserTestUtil.testUser;
import static org.example.eventspotlightback.utils.UserTestUtil.testUserDto;
import static org.example.eventspotlightback.utils.UserTestUtil.testUserRegistrationDto;
import static org.example.eventspotlightback.utils.UserTestUtil.testUserRole;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.example.eventspotlightback.dto.internal.user.UserResponseDto;
import org.example.eventspotlightback.exception.RegistrationException;
import org.example.eventspotlightback.mapper.UserMapper;
import org.example.eventspotlightback.model.Role;
import org.example.eventspotlightback.model.User;
import org.example.eventspotlightback.repository.RoleRepository;
import org.example.eventspotlightback.repository.UserRepository;
import org.example.eventspotlightback.service.favorite.FavoriteService;
import org.example.eventspotlightback.service.my.events.MyEventsService;
import org.example.eventspotlightback.service.user.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private FavoriteService favoriteService;
    @Mock
    private MyEventsService myEventsService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("Register user - valid")
    public void register_UserRegistrationRequestDto_UserResponseDto() throws RegistrationException {
        //Given
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(userMapper.toModel(testUserRegistrationDto)).thenReturn(testUser);
        when(passwordEncoder.encode(any())).thenReturn(TEST_USER_PASSWORD);
        when(roleRepository.getByRoleName(any(Role.RoleName.class))).thenReturn(testUserRole);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(favoriteService.createFavorite(any(User.class))).thenReturn(testFavorite);
        when(myEventsService.createMyEvents(any(User.class))).thenReturn(testMyEvents);
        when(userMapper.toDto(testUser)).thenReturn(testUserDto);

        //When
        UserResponseDto expected = testUserDto;
        UserResponseDto actual = userService.register(testUserRegistrationDto);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Register with existing user")
    public void register_UserRegistrationRequestDto_RegistrationException() {
        //Given
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(testUser));

        //When
        Exception exception = Assertions.assertThrows(
                RegistrationException.class,
                () -> userService.register(testUserRegistrationDto)
        );
        String expected = "Can't register user";
        String actual = exception.getMessage();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete User by Id - valid")
    public void deleteById_DescriptionId_empty() {
        userService.deleteUserById(TEST_USER_ID);
        verify(userRepository, times(1)).deleteById(TEST_USER_ID);
    }

}
