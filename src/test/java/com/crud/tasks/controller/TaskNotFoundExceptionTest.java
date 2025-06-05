package com.crud.tasks.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskNotFoundExceptionTest  {

    @Test
    void shouldCreateExceptionWithoutMessage() {
        // When
        TaskNotFoundException exception = new TaskNotFoundException();

        // Then
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithMessage() {
        // Given
        String message = "Task not found";

        // When
        TaskNotFoundException exception = new TaskNotFoundException(message);

        // Then
        assertEquals(message, exception.getMessage());
    }
}
