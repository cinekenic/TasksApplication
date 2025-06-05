package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.exception.MissingTaskException;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DbServiceTest {
    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository repository;

    @Test
    void shouldReturnAllTasks() {
        // Given
        List<Task> tasks = List.of(new Task(1L, "Test task", "desc"));
        when(repository.findAll()).thenReturn(tasks);

        // When
        List<Task> result = dbService.getAllTasks();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test task");
    }

    @Test
    void shouldReturnTaskById() {
        // Given
        Task task = new Task(1L, "Test task", "desc");
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        // When
        Task result = dbService.getTaskById(1L);


        // Then
        assertThat(result).isEqualTo(task);
    }

    @Test
    void shouldThrowMissingTaskExceptionWhenTaskNotFoundById() {
        // Given
        when(repository.findById(42L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> dbService.getTaskById(42L))
                .isInstanceOf(MissingTaskException.class)
                .hasMessage("Task with id 42 not found");
    }

    @Test
    void shouldSaveTask() {
        // Given
        Task task = new Task(null, "New task", "desc");
        Task savedTask = new Task(1L, "New task", "desc");

        when(repository.save(task)).thenReturn(savedTask);

        // When
        Task result = dbService.saveTask(task);

        // Then
        assertThat(result).isEqualTo(savedTask);
    }


    @Test
    void shouldDeleteTaskById() {
        // When
        dbService.deleteTask(5L);

        // Then
        verify(repository, times(1)).deleteById(5L);
    }

    @Test
    void shouldGetTask() throws TaskNotFoundException {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.of(new Task(1L, "title", "desc")));

        // When
        Task result = dbService.getTask(1L);

        // Then
        assertThat(result).isNotNull();
    }

    @Test
    void shouldThrowTaskNotFoundException() {
        // Given
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> dbService.getTask(999L))
                .isInstanceOf(TaskNotFoundException.class);

    }
}
