package org.example.eventspotlightback.controller;

import static org.example.eventspotlightback.utils.CityTestUtil.TEST_CITY_ID;
import static org.example.eventspotlightback.utils.EventTestUtil.TEST_EVENT_ID;
import static org.example.eventspotlightback.utils.EventTestUtil.addEventDto;
import static org.example.eventspotlightback.utils.EventTestUtil.getTestListWithSimpleEventDto;
import static org.example.eventspotlightback.utils.EventTestUtil.testEventDto;
import static org.example.eventspotlightback.utils.EventTestUtil.testSimpleEventDto;
import static org.example.eventspotlightback.utils.EventTestUtil.updateEventDto;
import static org.example.eventspotlightback.utils.EventTestUtil.updatedEventDto;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.example.eventspotlightback.dto.internal.event.EventDto;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDto;
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
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventControllerTest {
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
                    new ClassPathResource("database/description/add_test_description.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/user/add_test_user.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/contact/add_test_contact.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/city/add_test_city.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/address/add_test_address.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/event/add_three_events.sql")
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
                    new ClassPathResource("database/event/delete_all_events.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/address/delete_all_addresses.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/city/delete_all_cities.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/contact/delete_all_contacts.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/user/delete_all_users.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/description/delete_all_descriptions.sql")
            );
        }
    }

    @Sql(scripts = {
            "classpath:database/event/add_test_event.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/event/delete_test_event.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "adminUser", authorities = {"ADMIN"})
    @Test
    @DisplayName("Accept existing Event")
    public void acceptEvent_EventId_SimpleEventDto() throws Exception {
        //Given
        MvcResult result = mockMvc.perform(post("/events/accept/" + TEST_EVENT_ID))
                .andExpect(status().isAccepted())
                .andReturn();

        //When
        SimpleEventDto expected = testSimpleEventDto;
        SimpleEventDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), SimpleEventDto.class);

        //Then
        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Sql(scripts = {
            "classpath:database/event/delete_test_event.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "testUser", authorities = {"USER"})
    @Test
    @DisplayName("Create new Event")
    public void addEvent_CreateEventDto_Success() throws Exception {
        //Given
        String jsonRequest = objectMapper.writeValueAsString(addEventDto);
        MvcResult result = mockMvc.perform(post("/events")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        //When
        EventDto expected = testEventDto;
        EventDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), EventDto.class);

        //Then
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Sql(scripts = {
            "classpath:database/event/add_test_event.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/event/delete_updated_test_event.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "testUser", authorities = {"USER"})
    @Test
    @DisplayName("Update test Event")
    public void update_CreateEventDto_Success() throws Exception {
        //Given
        String jsonRequest = objectMapper.writeValueAsString(updateEventDto);
        MvcResult result = mockMvc.perform(put("/events/" + TEST_EVENT_ID)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        //When
        EventDto expected = updatedEventDto;
        EventDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), EventDto.class);

        //Then
        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Sql(scripts = {
            "classpath:database/event/add_test_event.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @WithMockUser(username = "adminUser", authorities = {"ADMIN"})
    @Test
    @DisplayName("Delete existing Event")
    public void delete_anyRequest_Success() throws Exception {
        mockMvc.perform(delete("/events/" + TEST_EVENT_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        mockMvc.perform(get("/events/" + TEST_EVENT_ID))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @WithMockUser(username = "testUser", authorities = {"USER"})
    @Test
    @DisplayName("Test find all Events")
    public void getAllEvents_Empty_ListOfSimpleEventDto() throws Exception {
        MvcResult result = mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andReturn();

        List<SimpleEventDto> expected = getTestListWithSimpleEventDto();
        SimpleEventDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                SimpleEventDto[].class
        );

        Assertions.assertEquals(3, actual.length);
        Assertions.assertEquals(expected, Arrays.stream(actual).toList());
    }

    @Sql(scripts = {
            "classpath:database/event/add_test_event.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/event/delete_test_event.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "testUser", authorities = {"USER"})
    @Test
    @DisplayName("Test find Event by id")
    public void findById_EventId_EventDto() throws Exception {
        MvcResult result = mockMvc.perform(get("/events/" + TEST_EVENT_ID))
                .andExpect(status().isOk())
                .andReturn();

        EventDto expected = testEventDto;
        EventDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                EventDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "testUser", authorities = {"USER"})
    @Test
    @DisplayName("Test search Event by Event search parameters")
    public void search_EventSearchParametersAndPageable_ListOfSimpleEventDto() throws Exception {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("/events/search")
                .queryParam("page", 0)
                .queryParam("size", 10)
                .queryParam("cities", TEST_CITY_ID)
                .queryParam("onlineStatus", true);

        String searchQuery = uriBuilder.toUriString();

        MvcResult result = mockMvc.perform(get(searchQuery))
                .andExpect(status().isOk())
                .andReturn();

        List<SimpleEventDto> expected = new ArrayList<>();
        expected.add(getTestListWithSimpleEventDto().get(1));
        SimpleEventDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                SimpleEventDto[].class
        );

        Assertions.assertEquals(1, actual.length);
        Assertions.assertEquals(expected, Arrays.stream(actual).toList());
    }
}
