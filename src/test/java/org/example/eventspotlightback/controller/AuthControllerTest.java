package org.example.eventspotlightback.controller;

import static org.example.eventspotlightback.utils.UserTestUtil.TEST_USER_ID;
import static org.example.eventspotlightback.utils.UserTestUtil.testUserDto;
import static org.example.eventspotlightback.utils.UserTestUtil.testUserLoginRequestDto;
import static org.example.eventspotlightback.utils.UserTestUtil.testUserLoginResponseDto;
import static org.example.eventspotlightback.utils.UserTestUtil.testUserRegistrationDto;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.example.eventspotlightback.dto.internal.user.UserLoginResponseDto;
import org.example.eventspotlightback.dto.internal.user.UserResponseDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {
    private static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext webApplicationContext
    ) throws SQLException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        tearDown(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
        }
    }

    @AfterAll
    public static void afterAll(
            @Autowired DataSource dataSource
    ) {
        tearDown(dataSource);
    }

    @SneakyThrows
    private static void tearDown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/user/delete_all_users.sql")
            );
        }
    }

    @Sql(scripts = {
            "classpath:database/user/delete_test_user.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("Test register user")
    public void registerUser_UserRegistrationRequestDto_UserResponseDto() throws Exception {
        //Given
        String jsonRequest = objectMapper.writeValueAsString(testUserRegistrationDto);
        MvcResult mvcResult = mockMvc.perform(post("/auth/register")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        //When
        UserResponseDto expected = testUserDto;
        UserResponseDto actual = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), UserResponseDto.class);

        //Then
        EqualsBuilder.reflectionEquals(expected, actual, "id", "password");
    }

    @Sql(scripts = {
            "classpath:database/user/add_test_user.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/user/delete_test_user.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("Test first time user login")
    public void login_UserLoginRequestDto_UserLoginResponseDto() throws Exception {
        //Given
        String jsonRequest = objectMapper.writeValueAsString(testUserLoginRequestDto);
        MvcResult mvcResult = mockMvc.perform(post("/auth/login")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        //When
        UserLoginResponseDto expected = testUserLoginResponseDto;
        UserLoginResponseDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                UserLoginResponseDto.class
        );

        //Then
        EqualsBuilder.reflectionEquals(expected, actual, "token");
    }

    @Test
    @DisplayName("Relogin user")
    public void re_login_UserLoginRequestDto_UserLoginResponseDto() {

    }

    @Sql(scripts = {
            "classpath:database/user/add_test_user.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/user/delete_test_user.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "adminUser", authorities = {"ADMIN"})
    @Test
    @DisplayName("Delete User by Id")
    public void delete_UserId_NoContent() throws Exception {
        mockMvc.perform(delete("/auth/" + TEST_USER_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        String jsonRequest = objectMapper.writeValueAsString(testUserLoginRequestDto);
        mockMvc.perform(post("/auth/login")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }
}
