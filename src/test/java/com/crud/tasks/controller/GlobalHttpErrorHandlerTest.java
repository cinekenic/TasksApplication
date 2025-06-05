package com.crud.tasks.controller;

import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
public class GlobalHttpErrorHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DbService service;

    @MockitoBean
    private TaskMapper taskMapper;

    @Test
    void shouldHandleTaskNotFoundException() throws Exception {
        // Given
        when(service.getTask(100L)).thenThrow(new TaskNotFoundException());

        // When & Then
        mockMvc.perform(get("/v1/tasks/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Task whit given id doesn't exist"));
    }
}
