package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final DbService service;
    private final TaskMapper taskMapper;

//    @RequestMapping(method = RequestMethod.GET, value = "")
    @GetMapping
    public List<TaskDto> getTasks() {
        List<Task> tasks = service.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/{taskId}")
    @GetMapping("/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) {
//        return new TaskDto(1L, "test title", "test_content");
        Task task = service.getTaskById(taskId);
        return taskMapper.mapToTaskDto(task);
    }

//    @RequestMapping(method = RequestMethod.DELETE, value = "/{taskId}")
    @DeleteMapping("/{taskId}")
    public void deleteTask(Long taskId) {}

//    @RequestMapping(method = RequestMethod.PUT, value = "")
    @PutMapping
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Test content");
    }

//    @RequestMapping(method = RequestMethod.POST, value = "")
    @PostMapping
    public void createTask(TaskDto taskDto) {}
}
