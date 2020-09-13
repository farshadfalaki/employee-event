package com.takeaway.eventservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.takeaway.eventservice.constants.EventType;
import com.takeaway.eventservice.model.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest()
public class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;
    static ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void getEmployeeEvents() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc.perform(delete("http://localhost:9090/event/all").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        String sampleEmployeeId = "123";
        String sampleEventId = "1000";
        String sampleEventId2 = "1001";
        Event event1 = new Event(sampleEventId,sampleEmployeeId, EventType.CREATE_EVENT, Instant.now());
        Event event2 = new Event(sampleEventId2,sampleEmployeeId, EventType.UPDATE_EVENT, Instant.now());
        mockMvc.perform(post("http://localhost:9090/event").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(event1)))
                .andDo(print())
                .andReturn();

        mockMvc.perform(post("http://localhost:9090/event").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(event2)))
                .andDo(print())
                .andReturn();

        mockMvc.perform(get("http://localhost:9090/event/list/" + sampleEmployeeId ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                ;
    }
}