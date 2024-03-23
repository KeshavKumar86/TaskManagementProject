package com.keshav.TaskManagement.service;

import com.keshav.TaskManagement.entity.Task;
import com.keshav.TaskManagement.exceptionhandling.TaskNotFoundException;
import com.keshav.TaskManagement.repository.TaskRepository;
import com.keshav.TaskManagement.validation.TaskValidator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest("TaskServiceTest.class")
class TaskServiceTest {
    @Mock
    TaskRepository taskRepository;
    @Mock
    TaskValidator taskValidator;
    @InjectMocks
    TaskServiceImpl service;

    @Test
    void testFindAllSuccess() {
        //given
        Task task1 = new Task();
        task1.setId(1);
        task1.setTitle("DS Work");
        task1.setStatus("TODO");
        task1.setDescription("have to complete the tree in DSA");
        task1.setPriority("LOW");
        task1.setDueDate(LocalDate.parse("2024-02-25"));
        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);

        Mockito.when(taskRepository.findAll()).thenReturn(taskList);
        List<Task> taskListActual = service.findAll();

        assertEquals("DS Work", taskListActual.get(0).getTitle());

    }

    @Test
    void testFindAllReturnEmptyList() {   //given
        List<Task> taskList = new ArrayList<>();

        Mockito.when(taskRepository.findAll()).thenReturn(taskList);
        List<Task> actualTaskList = service.findAll();

        assertEquals(0, actualTaskList.size());

    }

    @Test
    void testFindByIdSuccess() {
        //given
        Task task = new Task();
        task.setId(1);
        task.setTitle("CN Work");
        task.setStatus("TODO");
        task.setDescription("have to complete the tree in DSA");
        task.setPriority("LOW");
        task.setDueDate(LocalDate.parse("2024-02-25"));

        Mockito.when(taskRepository.findById(task.getId())).thenReturn(task);
        Task actualTask = service.findById(1);

        assertEquals("CN Work", actualTask.getTitle());

    }

    @Test
    void testFindByIdThrowsException() {
        String expected = "Task not present with id";
        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> {
            service.findById(1);
        });
        assertTrue(exception.getMessage().contains(expected));
    }

    @Test
    void testSaveSuccess() {
        Task task = new Task();
        task.setId(1);
        task.setTitle("CN Work");
        task.setStatus("TODO");
        task.setDescription("have to complete the tree in DSA");
        task.setPriority("LOW");
        task.setDueDate(LocalDate.parse("2024-02-25"));

        Mockito.when(taskRepository.save(task)).thenReturn(task);
        Task actualTask = service.save(task);

        assertEquals(1,actualTask.getId());
    }
    @Test
    void testUpdateSuccess(){
        int id = 1;
        Task task = new Task();
        //task.setId(1);
        task.setTitle("CN Work");
        task.setStatus("TODO");
        task.setDescription("have to complete the tree in DSA");
        task.setPriority("LOW");
        task.setDueDate(LocalDate.parse("2024-02-25"));
        Task updatedTask = new Task();
        updatedTask.setId(1);
        updatedTask.setTitle("OS Work");
        updatedTask.setStatus("TODO");
        updatedTask.setDescription("have to complete the process scheduling in OS");
        updatedTask.setPriority("LOW");
        updatedTask.setDueDate(LocalDate.parse("2024-02-25"));

        Mockito.when(taskRepository.findById(id)).thenReturn(task);
        Mockito.when(taskRepository.update(task)).thenReturn(updatedTask);
        Task actualTask = service.update(1,task);
        assertEquals("OS Work",actualTask.getTitle());
    }
    @Test
    void testUpdateThrowsException() {
        Task task = new Task();
        String expectedMessage = "cannot update task because this task with id";
        Mockito.when(taskRepository.findById(1)).thenReturn(null);
        RuntimeException exception = assertThrows(RuntimeException.class,()->{
            service.update(1,task);
        });
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
    @Test
    void testDeleteSuccess() {
        int id = 1;
        Task task = new Task();
        task.setTitle("CN Work");
        task.setStatus("TODO");
        task.setDescription("have to complete the tree in DSA");
        task.setPriority("LOW");
        task.setDueDate(LocalDate.parse("2024-02-25"));

        Mockito.when(taskRepository.findById(id)).thenReturn(task);
        service.delete(1);
        verify(taskRepository,times(1)).delete(task);
    }
    @Test
    void testDeleteThrowsException(){
        int id = 1;
        String expectedMessage = "Task to be deleted not present with id:";

        Mockito.when(taskRepository.findById(id)).thenReturn(null);
        RuntimeException exception = assertThrows(RuntimeException.class,()->{
           service.delete(id);
        });

        assertTrue(exception.getMessage().contains(expectedMessage));
    }
    @Test
    void testSearchTask(){
        String title = "CN Work";
        Task task1 = new Task();
        task1.setTitle("CN Work");
        task1.setStatus("TODO");
        task1.setDescription("have to complete the OSI layer in CN");
        task1.setPriority("LOW");
        task1.setDueDate(LocalDate.parse("2024-02-25"));
        Task task2 = new Task();
        task1.setTitle("DS Work");
        task1.setStatus("TODO");
        task1.setDescription("have to complete the Tree in DS");
        task1.setPriority("LOW");
        task1.setDueDate(LocalDate.parse("2024-02-25"));
        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);

        Mockito.when(taskRepository.searchTask(title)).thenReturn(taskList);
        List<Task> actualTaskList = service.searchTask(title);

        assertEquals(2,actualTaskList.size());
    }
}
