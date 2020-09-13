package com.takeaway.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.takeaway.challenge.dto.DepartmentDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void create_withDepartmentDto_shouldReturnDepartmentDtoWithId() throws Exception {
        //given
        String sampleName = "some Department";
        DepartmentDto requestDepartmentDto =   DepartmentDto.builder().name(sampleName).build();
        //when then
        mockMvc.perform(post("http://localhost:8080/department").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestDepartmentDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",notNullValue()))
                .andExpect(jsonPath("$.name",is(sampleName)))
                .andReturn()
        ;
    }

    @Test
    public void create_withEmpty_shouldReturnError() throws Exception {
        //given
        String sampleName = null;
        DepartmentDto requestDepartmentDto = DepartmentDto.builder().name(sampleName).build();
        //when then
        mockMvc.perform(post("http://localhost:8080/department").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestDepartmentDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
        ;
    }
}