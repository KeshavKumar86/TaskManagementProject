package com.keshav.TaskManagement.service;

import com.keshav.TaskManagement.entity.Task;
import com.keshav.TaskManagement.exceptionhandling.TaskNotFoundException;
import com.keshav.TaskManagement.repository.TaskRepository;
import com.keshav.TaskManagement.validation.DateValidatorUsingDateFormat;
import com.keshav.TaskManagement.validation.TaskValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
@Service
public class TaskServiceImpl implements TaskService{

    //inject TaskRepository
    private final TaskRepository taskRepository;
    private final TaskValidator taskValidator;
    //constructor injection
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,TaskValidator taskValidator)
    {
        this.taskRepository=taskRepository;
        this.taskValidator=taskValidator;
    }
    @Override
    public List<Task> findAll() {
       return taskRepository.findAll();

    }

    @Override
    public Task findById(int id) {
        Task task= taskRepository.findById(id);
        if(task==null)
        {
            throw new TaskNotFoundException("Task not present with id:"+id);
        }
        return task;
    }
    @Transactional
    public Task save(Task task)
    {
         //perform validation
        taskValidator.isValid(task);
         return taskRepository.save(task);

    }
    @Transactional
    @Override
    public Task update(int id, Task task) {
        //do some validations
        //check if this id already exist

        Task theTask = taskRepository.findById(id);
        if (theTask==null ) {
            throw new RuntimeException("cannot update task because this task with id: "+id+" does not exist");

        }
        task.setId(id);
        taskValidator.isValid(task);
        return taskRepository.update(task);

    }
    @Transactional
    @Override
    public void delete(int id) {
        //check if the task to be deleted is present
        Task task = taskRepository.findById(id);
        if(task == null)
            throw new RuntimeException("Task to be deleted not present with id: "+id);
        taskRepository.delete(task);
    }

}
