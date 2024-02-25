package com.keshav.TaskManagement.repository;

import com.keshav.TaskManagement.entity.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    //inject EntityManager
    private final EntityManager entityManager;
    //constructor injection
    public TaskRepositoryImpl(EntityManager entityManager)
    {
        this.entityManager=entityManager;
    }
    @Override
    public List<Task> findAll() {
        TypedQuery<Task> query= entityManager.createQuery("from Task", Task.class);
        return query.getResultList();

    }

    @Override
    public Task findById(int id) {
        return entityManager.find(Task.class,id);
    }
    public  Task save(Task task)
    {


        return entityManager.merge(task);

    }

    @Override
    public Task update(Task task) {
        return entityManager.merge(task);

    }

    @Override
    public void delete(Task   task) {

        entityManager.remove(task);

    }
}
