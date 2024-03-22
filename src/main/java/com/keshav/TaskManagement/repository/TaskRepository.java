package com.keshav.TaskManagement.repository;

import com.keshav.TaskManagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository  {
    public List<Task> findAll();
    public Task findById(int id);
    public Task save(Task task);
    public  Task update(Task task);
    public void delete(Task  task);
    public List<Task> searchTask(String title);
}
