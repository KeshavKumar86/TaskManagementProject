package com.keshav.TaskManagement.validation;


import com.keshav.TaskManagement.entity.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Component
public class TaskValidatorImpl implements TaskValidator {
    @Override
    public void isValid(Task task) {
        //validate task
        if(task==null)
            throw new RuntimeException("please provide a valid task");

        //validate that title cannot be null

        if(task.getTitle()==null || task.getTitle().trim().isEmpty())
            throw new RuntimeException("Title cannot be empty for Task");

        //validate that date should be valid and future date
        DateValidatorUsingDateFormat formatter = new DateValidatorUsingDateFormat("yyyy-MM-dd");
        //get the current date to compare
        LocalDate currentDate = LocalDate.now();
        // Format current date as localdate
        DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate nowDate = LocalDate.parse(currentDate.format(dtformatter), dtformatter);


        if(!formatter.isValid(task.getDueDate().toString()) || !task.getDueDate().isAfter(nowDate))
            throw new RuntimeException("Date should be a future date");

        //validate that Priority should be one of: LOW, MEDIUM, HIGH.
        if(task.getPriority()==null || task.getPriority().isEmpty() || !(task.getPriority().equals("LOW") || task.getPriority().equals("MEDIUM") || task.getPriority().equals("HIGH")) )
            throw  new RuntimeException("Please provide a valid Priority: LOW, MEDIUM, HIGH");

        // validate that Status should be one of: TODO, IN_PROGRESS, DONE.
        if(task.getStatus()==null || task.getStatus().isEmpty() || !(task.getStatus().equals("TODO") || task.getStatus().equals("IN_PROGRESS") || task.getStatus().equals("DONE")) )
            throw  new RuntimeException("Please provide a valid Status: TODO, IN_PROGRESS, DONE");

    }
}
