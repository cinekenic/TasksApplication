package com.crud.tasks.controller;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException() {
        super();
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}