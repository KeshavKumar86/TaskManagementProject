package com.keshav.TaskManagement.service;

import com.keshav.TaskManagement.entity.Task;
import com.keshav.TaskManagement.exceptionhandling.TaskNotFoundException;
import com.keshav.TaskManagement.repository.TaskRepository;
import com.keshav.TaskManagement.validation.DateValidatorUsingDateFormat;
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
    //constructor injection
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository)
    {
        this.taskRepository=taskRepository;
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
         validateTask(task);

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
        validateTask(task);
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


    public void validateTask(Task task)
    {
        //validate task
        if(task==null)
            throw new RuntimeException("please provide a valid task");

        //validate that title cannot be null
        if(task.getTitle()==null || task.getTitle().isEmpty())
            throw new RuntimeException("Title cannot be empty for Task");

        //validate that date should be valid and future date
        DateValidatorUsingDateFormat formatter = new DateValidatorUsingDateFormat("yyyy-MM-dd");
        //get the current date to compare
        LocalDate currentDate = LocalDate.now();
        // Format current date as localdate
        DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate nowDate = LocalDate.parse(currentDate.format(dtformatter), dtformatter);


        if(!formatter.isValid(task.getDueDate().toString()) || !task.getDueDate().isAfter(nowDate))
            throw new RuntimeException("Date should be a valid date");

        //validate that Priority should be one of: LOW, MEDIUM, HIGH.
        if(task.getPriority()==null || task.getPriority().isEmpty() || !(task.getPriority().equals("LOW") || task.getPriority().equals("MEDIUM") || task.getPriority().equals("HIGH")) )
            throw  new RuntimeException("Please provide a valid Priority: LOW, MEDIUM, HIGH");

        // validate that Status should be one of: TODO, IN_PROGRESS, DONE.
        if(task.getStatus()==null || task.getStatus().isEmpty() || !(task.getStatus().equals("TODO") || task.getStatus().equals("IN_PROGRESS") || task.getStatus().equals("DONE")) )
            throw  new RuntimeException("Please provide a valid Status: TODO, IN_PROGRESS, DONE");

    }
}
