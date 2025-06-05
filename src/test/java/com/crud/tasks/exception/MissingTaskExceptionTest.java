package com.crud.tasks.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MissingTaskExceptionTest {

    @Test
    void shouldCreateExceptionWithCorrectMessage() {
        // Given
        Long missingId = 42L;

        // When
        MissingTaskException exception = new MissingTaskException(missingId);

        // Then
        assertThat(exception)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Task with id 42 not found");
    }
}
