package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TaskMapperTest {

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    void shouldMapToTask() {
        // Given
        TaskDto dto = new TaskDto(1L, "Test title", "Test content");

        // When
        Task task = taskMapper.mapToTask(dto);

        // Then
        assertThat(task.getId()).isEqualTo(1L);
        assertThat(task.getTitle()).isEqualTo("Test title");
        assertThat(task.getContent()).isEqualTo("Test content");
    }

    @Test
    void shouldMapToTaskDto() {
        // Given
        Task task = new Task(2L, "Title", "Content");

        // When
        TaskDto dto = taskMapper.mapToTaskDto(task);

        // Then
        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getTitle()).isEqualTo("Title");
        assertThat(dto.getContent()).isEqualTo("Content");
    }

    @Test
    void shouldMapToTaskDtoList() {
        //Given
        List<Task> tasks = List.of(
                new Task(1L, "T1", "C1"),
                new Task(2L, "T2", "C2")
        );

        // When
        List<TaskDto> dtoList = taskMapper.mapToTaskDtoList(tasks);

        // Then
        assertThat(dtoList).hasSize(2);

        assertThat(dtoList.get(0).getId()).isEqualTo(1L);
        assertThat(dtoList.get(0).getTitle()).isEqualTo("T1");
        assertThat(dtoList.get(0).getContent()).isEqualTo("C1");

        assertThat(dtoList.get(1).getId()).isEqualTo(2L);
        assertThat(dtoList.get(1).getTitle()).isEqualTo("T2");
        assertThat(dtoList.get(1).getContent()).isEqualTo("C2");
    }
}
