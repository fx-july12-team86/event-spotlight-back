package org.example.eventspotlightback.utils;

import static org.example.eventspotlightback.utils.UserTestUtil.TEST_USER_ID;
import static org.example.eventspotlightback.utils.UserTestUtil.testUser;

import java.util.Collections;
import org.example.eventspotlightback.dto.internal.my.events.MyEventsDto;
import org.example.eventspotlightback.model.MyEvents;

public class MyEventsTestUtil {
    public static final Long TEST_MY_EVENTS_ID = TEST_USER_ID;

    public static final MyEvents testMyEvents = new MyEvents()
            .setId(TEST_MY_EVENTS_ID)
            .setUser(testUser);
    public static final MyEventsDto testMyEventsDto = new MyEventsDto()
            .setId(TEST_MY_EVENTS_ID)
            .setEventDtos(Collections.emptyList());//EventDtos fix
}
