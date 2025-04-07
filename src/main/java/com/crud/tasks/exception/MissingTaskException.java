package com.crud.tasks.exception;

public class MissingTaskException extends RuntimeException {
    public MissingTaskException(Long id) {
        super("Task with id " + id + " not found");
    }
}