package com.keshav.TaskManagement.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskRestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<TaskErrorResponse> taskNotFoundException(TaskNotFoundException exception)
    {
        TaskErrorResponse taskErrorResponse=new TaskErrorResponse();

        taskErrorResponse.setMessage(exception.getMessage());
        taskErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        taskErrorResponse.setTimeStamp(System.currentTimeMillis());
        ResponseEntity<TaskErrorResponse> response=new ResponseEntity<>(taskErrorResponse,HttpStatus.NOT_FOUND);

        return response;
    }
    //method to handle general exceptions
    @ExceptionHandler
    public ResponseEntity<TaskErrorResponse> handleException(Exception exception)
    {
        TaskErrorResponse taskErrorResponse=new TaskErrorResponse();

        taskErrorResponse.setMessage(exception.getMessage());
        taskErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        taskErrorResponse.setTimeStamp(System.currentTimeMillis());
        ResponseEntity<TaskErrorResponse> response=new ResponseEntity<>(taskErrorResponse,HttpStatus.BAD_REQUEST);

        return response;
    }

}
