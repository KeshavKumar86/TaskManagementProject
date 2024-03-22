package com.keshav.TaskManagement.service;

import com.keshav.TaskManagement.entity.Task;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface TaskService {
    public List<Task> findAll();
    public Task findById(int id);
    public Task save(Task task);
    public  Task update(int id, Task task);
    public void delete(int id);
    public List<Task> searchTask(String title);
}
