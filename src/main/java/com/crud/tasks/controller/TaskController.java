package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final DbService service;
    private final TaskMapper taskMapper;

//    @RequestMapping(method = RequestMethod.GET, value = "")
    @GetMapping
    public  ResponseEntity<List<TaskDto>> getTasks() {
        List<Task> tasks = service.getAllTasks();
        return ResponseEntity.ok(taskMapper.mapToTaskDtoList(tasks));
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/{taskId}")
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto>  getTask(@PathVariable Long taskId) throws TaskNotFoundException {
//        return new TaskDto(1L, "test title", "test_content");
//        Task task = service.getTaskById(taskId);
//        return taskMapper.mapToTaskDto(task);

//        try {
//        return new ResponseEntity<>(taskMapper.mapToTaskDto(service.getTask(taskId)), HttpStatus.OK);
//        } catch (TaskNotFoundException e) {
//            return new ResponseEntity<>(new TaskDto(0L, "There is no task with id equal to " + taskId, ""), HttpStatus.BAD_REQUEST);
//        }
        return ResponseEntity.ok(taskMapper.mapToTaskDto(service.getTask(taskId)));
    }

//    @RequestMapping(method = RequestMethod.DELETE, value = "/{taskId}")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        service.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

//    @RequestMapping(method = RequestMethod.PUT, value = "")
    @PutMapping
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto)  {
//        return taskMapper.mapToTaskDto(
//                service.getTask(taskId).orElseThrow(TaskNotFoundException::new));
//        return new ResponseEntity<>(taskMapper.mapToTaskDto(service.getTask(taskId)), HttpStatus.OK);
        Task task = taskMapper.mapToTask(taskDto);
        Task savedTask = service.saveTask(task);
        return ResponseEntity.ok(taskMapper.mapToTaskDto(savedTask));
    }

//    @RequestMapping(method = RequestMethod.POST, value = "")
@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Void> createTask(@RequestBody TaskDto taskDto) {
    Task task = taskMapper.mapToTask(taskDto);
    service.saveTask(task);
    return ResponseEntity.ok().build();
}
}
