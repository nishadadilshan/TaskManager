package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.Test;
import org.testng.Assert;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void testGetAllTasks() throws Exception {
        // Prepare test data
        Task task1 = new Task();
        task1.setId(1);
        task1.setTitle("Task 1");
        
        Task task2 = new Task();
        task2.setId(2);
        task2.setTitle("Task 2");
        
        List<Task> tasks = Arrays.asList(task1, task2);
        
        // Mock service behavior
        when(taskService.getAllTasks()).thenReturn(tasks);
        
        // Perform request and verify
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Task 2"));
        
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    public void testGetAllTasksEmpty() throws Exception {
        // Mock empty list
        when(taskService.getAllTasks()).thenReturn(Arrays.asList());
        
        // Perform request and verify
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
        
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    public void testCreateTask() throws Exception {
        // Prepare test data
        Task createdTask = new Task();
        createdTask.setId(1);
        createdTask.setTitle("New Task");
        
        // Mock service behavior
        when(taskService.createTask("New Task")).thenReturn(createdTask);
        
        // Perform request and verify
        mockMvc.perform(post("/tasks")
                .param("title", "New Task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Task"));
        
        verify(taskService, times(1)).createTask("New Task");
    }

    @Test
    public void testCreateTaskWithEmptyTitle() throws Exception {
        // Prepare test data
        Task createdTask = new Task();
        createdTask.setId(1);
        createdTask.setTitle("");
        
        // Mock service behavior
        when(taskService.createTask("")).thenReturn(createdTask);
        
        // Perform request and verify
        mockMvc.perform(post("/tasks")
                .param("title", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(""));
        
        verify(taskService, times(1)).createTask("");
    }

    @Test
    public void testDeleteTaskSuccess() throws Exception {
        // Mock service behavior
        when(taskService.deleteTask(1)).thenReturn(true);
        
        // Perform request and verify
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
        
        verify(taskService, times(1)).deleteTask(1);
    }

    @Test
    public void testDeleteTaskNotFound() throws Exception {
        // Mock service behavior
        when(taskService.deleteTask(999)).thenReturn(false);
        
        // Perform request and verify
        mockMvc.perform(delete("/tasks/999"))
                .andExpect(status().isOk())
                .andExpect(content().string("Not Found"));
        
        verify(taskService, times(1)).deleteTask(999);
    }

    @Test
    public void testDeleteTaskWithInvalidId() throws Exception {
        // Mock service behavior
        when(taskService.deleteTask(-1)).thenReturn(false);
        
        // Perform request and verify
        mockMvc.perform(delete("/tasks/-1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Not Found"));
        
        verify(taskService, times(1)).deleteTask(-1);
    }
} 