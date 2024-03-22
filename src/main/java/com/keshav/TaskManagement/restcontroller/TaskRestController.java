package com.keshav.TaskManagement.restcontroller;

import com.keshav.TaskManagement.entity.Task;
import com.keshav.TaskManagement.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RequestMapping("/api")
@RestController
public class TaskRestController {
    //inject TaskService
    private final TaskService taskService;
    //constructor injection
    public TaskRestController(TaskService taskService) {
      this.taskService=taskService;
    }

    //define endpoint to get all Tasks
    @GetMapping("/tasks")
    public List<Task> getAll () throws ParseException
    {
//    {     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//          // Define a date string
//          String dateString = "2024-03-20"; // Change this to your desired date
//          // Parse the date string to obtain a Date object
//          Date date = sdf.parse(dateString);
//
//        Task task1=new Task(1,"DB Work","have to complete the normalization in database",date,"MEDIUM","TODO");
//        List<Task> tasks=new ArrayList<>();
//        tasks.add(task1);
         return taskService.findAll();
    }

    //define endpoint to get a task
    @GetMapping("/tasks/{taskId}")
    public Task getTask(@PathVariable("taskId") int id)
    {
        return taskService.findById(id);
    }

    //endpoint to create a task
    @PostMapping("/tasks")
    public ResponseEntity<Task> save(@RequestBody Task task)
    {   //to make sure insertion using merge set id to 0
        task.setId(0);
        return new ResponseEntity<>(taskService.save(task),HttpStatus.CREATED);

    }

    //endpoint to update a task
    @PutMapping("/tasks/{taskId}")
    public Task update(@PathVariable("taskId") int  id,@RequestBody Task task)
    {
        return taskService.update(id,task);
    }
    //endpoint to delete a task
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<String> delete(@PathVariable("taskId") int id)
    {   taskService.delete(id);
        return new ResponseEntity<>("Task with id: "+id+" Deleted ", HttpStatus.NO_CONTENT);
    }
    //endpoint to search a task by title
    @GetMapping("/tasks/search")
    public List<Task> searchTask(@RequestParam(name = "title") String title)
    {
        return taskService.searchTask(title);

    }
}
