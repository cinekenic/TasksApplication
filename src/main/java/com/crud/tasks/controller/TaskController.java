package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

//    @RequestMapping(method = RequestMethod.GET, value = "")
    @GetMapping
    public List<TaskDto> getTasks() {
        return new ArrayList<>();
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/{taskId}")
    @GetMapping("/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) {
        return new TaskDto(1L, "test title", "test_content");
    }

//    @RequestMapping(method = RequestMethod.DELETE, value = "/{taskId}")
    @DeleteMapping("/{taskId}")
    public void deleteTask(Long taskId) {}

//    @RequestMapping(method = RequestMethod.PUT, value = "")
    @PutMapping
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Test content");
    }

//    @RequestMapping(method = RequestMethod.POST, value = "")
    @PostMapping
    public void createTask(TaskDto taskDto) {}
}
