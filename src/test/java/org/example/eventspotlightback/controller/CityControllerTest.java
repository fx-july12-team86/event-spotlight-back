package org.example.eventspotlightback.controller;

import static org.example.eventspotlightback.utils.CityTestUtil.TEST_CITY_ID;
import static org.example.eventspotlightback.utils.CityTestUtil.addCityDto;
import static org.example.eventspotlightback.utils.CityTestUtil.getTestListWithCities;
import static org.example.eventspotlightback.utils.CityTestUtil.testCityDto;
import static org.example.eventspotlightback.utils.CityTestUtil.updateCityDto;
import static org.example.eventspotlightback.utils.CityTestUtil.updatedCityDto;
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
import org.example.eventspotlightback.dto.internal.city.AddCityDto;
import org.example.eventspotlightback.dto.internal.city.CityDto;
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
public class CityControllerTest {
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
                    new ClassPathResource("database/city/add_three_cities.sql")
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
                    new ClassPathResource("database/city/delete_all_cities.sql")
            );
        }
    }

    @Sql(scripts = {
            "classpath:database/city/delete_test_city.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "adminUser", authorities = {"ADMIN"})
    @Test
    @DisplayName("create new City")
    public void save_AddCityDto_Success() throws Exception {
        //Given
        String jsonRequest = objectMapper.writeValueAsString(addCityDto);
        MvcResult result = mockMvc.perform(post("/cities")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        //When
        CityDto expected = testCityDto;
        CityDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CityDto.class);

        //Then
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Sql(scripts = {
            "classpath:database/city/add_test_city.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/city/delete_updated_test_city.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "adminUser", authorities = {"ADMIN"})
    @Test
    @DisplayName("Update test City")
    public void update_AddCityDto_Success() throws Exception {
        //Given
        AddCityDto updateDto = updateCityDto;
        String jsonRequest = objectMapper.writeValueAsString(updateDto);
        MvcResult result = mockMvc.perform(put("/cities/" + TEST_CITY_ID)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        //When
        CityDto expected = updatedCityDto;
        CityDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CityDto.class);

        //Then
        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Sql(scripts = {
            "classpath:database/city/add_test_city.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/city/delete_test_city.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "adminUser", authorities = {"ADMIN"})
    @Test
    @DisplayName("Delete existing city")
    public void delete_anyRequest_Success() throws Exception {
        mockMvc.perform(delete("/cities/" + TEST_CITY_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        mockMvc.perform(get("/cities/" + TEST_CITY_ID))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @DisplayName("Test find all cities")
    public void findAll_Empty_ListOfCityDto() throws Exception {
        MvcResult result = mockMvc.perform(get("/cities"))
                .andExpect(status().isOk())
                .andReturn();

        List<CityDto> expected = getTestListWithCities();
        CityDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CityDto[].class
        );

        Assertions.assertEquals(3, actual.length);
        Assertions.assertEquals(expected, Arrays.stream(actual).toList());
    }

    @Sql(scripts = {
            "classpath:database/city/add_test_city.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/city/delete_test_city.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("Test find City by id")
    public void findById_CityId_CityDto() throws Exception {
        MvcResult result = mockMvc.perform(get("/cities/" + TEST_CITY_ID))
                .andExpect(status().isOk())
                .andReturn();

        CityDto expected = testCityDto;
        CityDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CityDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }
}
