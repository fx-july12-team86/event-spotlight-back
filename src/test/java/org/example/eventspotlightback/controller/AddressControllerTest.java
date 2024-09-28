package org.example.eventspotlightback.controller;

import static org.example.eventspotlightback.utils.AddressTestUtil.TEST_ADDRESS_ID;
import static org.example.eventspotlightback.utils.AddressTestUtil.addAddressDto;
import static org.example.eventspotlightback.utils.AddressTestUtil.getTestListWithAddresses;
import static org.example.eventspotlightback.utils.AddressTestUtil.testAddressDto;
import static org.example.eventspotlightback.utils.AddressTestUtil.updateAddressDto;
import static org.example.eventspotlightback.utils.AddressTestUtil.updatedAddressDto;
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
import org.example.eventspotlightback.dto.internal.address.AddressDto;
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
public class AddressControllerTest {
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
                    new ClassPathResource("database/city/add_test_city.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/address/add_three_addresses.sql")
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
                    new ClassPathResource("database/address/delete_all_addresses.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/city/delete_test_city.sql")
            );
        }
    }

    @Sql(scripts = {
            "classpath:database/address/delete_test_address.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "adminUser", authorities = {"ADMIN"})
    @Test
    @DisplayName("Create new Address")
    public void save_AddAddressDto_Success() throws Exception {
        //Given
        String jsonRequest = objectMapper.writeValueAsString(addAddressDto);
        MvcResult result = mockMvc.perform(post("/addresses")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        //When
        AddressDto expected = testAddressDto;
        AddressDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), AddressDto.class);

        //Then
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Sql(scripts = {
            "classpath:database/address/add_test_address.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/address/delete_updated_test_address.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "adminUser", authorities = {"ADMIN"})
    @Test
    @DisplayName("Update test Address")
    public void update_AddAddressDto_Success() throws Exception {
        //Given
        String jsonRequest = objectMapper.writeValueAsString(updateAddressDto);
        MvcResult result = mockMvc.perform(put("/addresses/" + TEST_ADDRESS_ID)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        //When
        AddressDto expected = updatedAddressDto;
        AddressDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), AddressDto.class);

        //Then
        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Sql(scripts = {
            "classpath:database/address/add_test_address.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @WithMockUser(username = "adminUser", authorities = {"ADMIN"})
    @Test
    @DisplayName("Delete existing Address")
    public void delete_anyRequest_Success() throws Exception {
        mockMvc.perform(delete("/addresses/" + TEST_ADDRESS_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        mockMvc.perform(get("/addresses/" + TEST_ADDRESS_ID))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @WithMockUser(username = "testUser", authorities = {"USER"})
    @Test
    @DisplayName("Test find all Addresses")
    public void findAll_Empty_ListOfAddressDto() throws Exception {
        MvcResult result = mockMvc.perform(get("/addresses"))
                .andExpect(status().isOk())
                .andReturn();

        List<AddressDto> expected = getTestListWithAddresses();
        AddressDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                AddressDto[].class
        );

        Assertions.assertEquals(3, actual.length);
        Assertions.assertEquals(expected, Arrays.stream(actual).toList());
    }

    @Sql(scripts = {
            "classpath:database/address/add_test_address.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/address/delete_test_address.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "testUser", authorities = {"USER"})
    @Test
    @DisplayName("Test find Address by id")
    public void findById_AddressId_AddressDto() throws Exception {
        MvcResult result = mockMvc.perform(get("/addresses/" + TEST_ADDRESS_ID))
                .andExpect(status().isOk())
                .andReturn();

        AddressDto expected = testAddressDto;
        AddressDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                AddressDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }
}
