package org.example.eventspotlightback.utils;

import java.util.Set;
import org.example.eventspotlightback.dto.internal.user.UserLoginRequestDto;
import org.example.eventspotlightback.dto.internal.user.UserLoginResponseDto;
import org.example.eventspotlightback.dto.internal.user.UserRegistrationRequestDto;
import org.example.eventspotlightback.dto.internal.user.UserResponseDto;
import org.example.eventspotlightback.model.Role;
import org.example.eventspotlightback.model.User;

public class UserTestUtil {
    public static final Long TEST_USER_ID = 94L;
    public static final String TEST_USER_NAME = "Test User Name";
    public static final String TEST_USER_EMAIL = "testUserEmail@i.com";
    public static final String TEST_USER_PASSWORD = "Test*User*Password";
    public static final String TEST_USER_ENCODED_PASSWORD =
            "$2a$10$2le1ol1z1uuSfRNE09RW4OykpanyzgW3wJiqTgMPx6BVfiQZ0016G";
    public static final Long TEST_ROLE_ID = 8L;
    public static final String TEST_USER_TOKEN = "testUserTokenAdijasdlkaiuehqwnejf231";

    public static final Role testUserRole = new Role()
            .setId(TEST_ROLE_ID)
            .setRoleName(Role.RoleName.USER);
    public static final User testUser = new User()
            .setId(TEST_USER_ID)
            .setUserName(TEST_USER_NAME)
            .setEmail(TEST_USER_EMAIL)
            .setPassword(TEST_USER_PASSWORD)
            .setRoles(Set.of(testUserRole));
    public static final UserResponseDto testUserDto = new UserResponseDto()
            .setId(TEST_USER_ID)
            .setUserName(TEST_USER_NAME)
            .setEmail(TEST_USER_EMAIL);
    public static final UserRegistrationRequestDto testUserRegistrationDto =
            new UserRegistrationRequestDto()
                    .setUserName(TEST_USER_NAME)
                    .setEmail(TEST_USER_EMAIL)
                    .setPassword(TEST_USER_PASSWORD)
                    .setRepeatPassword(TEST_USER_PASSWORD);

    public static final UserLoginRequestDto testUserLoginRequestDto =
            new UserLoginRequestDto(TEST_USER_EMAIL, TEST_USER_PASSWORD);

    public static final UserLoginResponseDto testUserLoginResponseDto =
            new UserLoginResponseDto(TEST_USER_EMAIL, TEST_USER_TOKEN);
}
