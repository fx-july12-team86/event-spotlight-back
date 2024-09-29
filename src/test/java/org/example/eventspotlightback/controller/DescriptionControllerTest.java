package org.example.eventspotlightback.controller;

import static org.example.eventspotlightback.utils.DescriptionTestUtil.TEST_DESCRIPTION_ID;
import static org.example.eventspotlightback.utils.DescriptionTestUtil.addDescriptionDto;
import static org.example.eventspotlightback.utils.DescriptionTestUtil.getTestListWithDescriptionDto;
import static org.example.eventspotlightback.utils.DescriptionTestUtil.testDescriptionDto;
import static org.example.eventspotlightback.utils.DescriptionTestUtil.updateDescriptionDto;
import static org.example.eventspotlightback.utils.DescriptionTestUtil.updatedDescriptionDto;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.example.eventspotlightback.dto.internal.description.DescriptionDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
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
public class DescriptionControllerTest {
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
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/description/add_three_descriptions.sql")
            );
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
                    new ClassPathResource("database/description/delete_all_descriptions.sql")
            );
        }
    }

    @Sql(scripts = {
            "classpath:database/description/delete_test_description.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "adminUser", authorities = {"ADMIN"})
    @Test
    @DisplayName("Create new Description")
    public void save_CreateDescriptionDto_Success() throws Exception {
        //Given
        String jsonRequest = objectMapper.writeValueAsString(addDescriptionDto);
        MvcResult result = mockMvc.perform(post("/descriptions")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        //When
        DescriptionDto expected = testDescriptionDto;
        DescriptionDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), DescriptionDto.class);

        //Then
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Sql(scripts = {
            "classpath:database/description/add_test_description.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/description/delete_updated_test_description.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "adminUser", authorities = {"ADMIN"})
    @Test
    @DisplayName("Update test Description")
    public void update_CreateDescriptionDto_Success() throws Exception {
        //Given
        String jsonRequest = objectMapper.writeValueAsString(updateDescriptionDto);
        MvcResult result = mockMvc.perform(put("/descriptions/" + TEST_DESCRIPTION_ID)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        //When
        DescriptionDto expected = updatedDescriptionDto;
        DescriptionDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), DescriptionDto.class);

        //Then
        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Sql(scripts = {
            "classpath:database/description/add_test_description.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @WithMockUser(username = "adminUser", authorities = {"ADMIN"})
    @Test
    @DisplayName("Delete existing Description")
    public void delete_anyRequest_Success() throws Exception {
        mockMvc.perform(delete("/descriptions/" + TEST_DESCRIPTION_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        mockMvc.perform(get("/descriptions/" + TEST_DESCRIPTION_ID))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @WithMockUser(username = "testUser", authorities = {"USER"})
    @Test
    @DisplayName("Test find all Descriptions")
    public void findAll_Empty_ListOfDescriptionDto() throws Exception {
        MvcResult result = mockMvc.perform(get("/descriptions"))
                .andExpect(status().isOk())
                .andReturn();

        List<DescriptionDto> expected = getTestListWithDescriptionDto();
        DescriptionDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                DescriptionDto[].class
        );

        Assertions.assertEquals(3, actual.length);
        Assertions.assertEquals(expected, Arrays.stream(actual).toList());
    }

    @Sql(scripts = {
            "classpath:database/description/add_test_description.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/description/delete_test_description.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "testUser", authorities = {"USER"})
    @Test
    @DisplayName("Test find Description by id")
    public void findById_DescriptionId_DescriptionDto() throws Exception {
        MvcResult result = mockMvc.perform(get("/descriptions/" + TEST_DESCRIPTION_ID))
                .andExpect(status().isOk())
                .andReturn();

        DescriptionDto expected = testDescriptionDto;
        DescriptionDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                DescriptionDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }
}
