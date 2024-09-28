package org.example.eventspotlightback.controller;

import static org.example.eventspotlightback.utils.ContactTestUtil.TEST_CONTACT_ID;
import static org.example.eventspotlightback.utils.ContactTestUtil.addContactDto;
import static org.example.eventspotlightback.utils.ContactTestUtil.getTestListWithContacts;
import static org.example.eventspotlightback.utils.ContactTestUtil.testContactDto;
import static org.example.eventspotlightback.utils.ContactTestUtil.updateContactDto;
import static org.example.eventspotlightback.utils.ContactTestUtil.updatedContactDto;
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
import org.example.eventspotlightback.dto.internal.contact.ContactDto;
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
public class ContactControllerTest {
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
                    new ClassPathResource("database/contact/add_three_contacts.sql")
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
                    new ClassPathResource("database/contact/delete_all_contacts.sql")
            );
        }
    }

    @Sql(scripts = {
            "classpath:database/contact/delete_test_contact.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "adminUser", authorities = {"ADMIN"})
    @Test
    @DisplayName("Create new Contact")
    public void save_CreateContactDto_Success() throws Exception {
        //Given
        String jsonRequest = objectMapper.writeValueAsString(addContactDto);
        MvcResult result = mockMvc.perform(post("/contacts")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        //When
        ContactDto expected = testContactDto;
        ContactDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), ContactDto.class);

        //Then
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Sql(scripts = {
            "classpath:database/contact/add_test_contact.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @WithMockUser(username = "adminUser", authorities = {"ADMIN"})
    @Test
    @DisplayName("Update test Contact")
    public void update_CreateContactDto_Success() throws Exception {
        //Given
        String jsonRequest = objectMapper.writeValueAsString(updateContactDto);
        MvcResult result = mockMvc.perform(put("/contacts/" + TEST_CONTACT_ID)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        //When
        ContactDto expected = updatedContactDto;
        ContactDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), ContactDto.class);

        //Then
        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Sql(scripts = {
            "classpath:database/contact/add_test_contact.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/contact/delete_test_contact.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "adminUser", authorities = {"ADMIN"})
    @Test
    @DisplayName("Delete existing Contact")
    public void delete_anyRequest_Success() throws Exception {
        mockMvc.perform(delete("/contacts/" + TEST_CONTACT_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        mockMvc.perform(get("/contacts/" + TEST_CONTACT_ID))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @WithMockUser(username = "testUser", authorities = {"USER"})
    @Test
    @DisplayName("Test find all Contacts")
    public void findAll_Empty_ListOfContactDto() throws Exception {
        MvcResult result = mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andReturn();

        List<ContactDto> expected = getTestListWithContacts();
        ContactDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                ContactDto[].class
        );

        Assertions.assertEquals(3, actual.length);
        Assertions.assertEquals(expected, Arrays.stream(actual).toList());
    }

    @Sql(scripts = {
            "classpath:database/contact/add_test_contact.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/contact/delete_test_contact.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "testUser", authorities = {"USER"})
    @Test
    @DisplayName("Test find Contact by id")
    public void findById_CityId_ContactDto() throws Exception {
        MvcResult result = mockMvc.perform(get("/contacts/" + TEST_CONTACT_ID))
                .andExpect(status().isOk())
                .andReturn();

        ContactDto expected = testContactDto;
        ContactDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                ContactDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }
}
