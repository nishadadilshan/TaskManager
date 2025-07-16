package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.util.List;

public class TaskServiceTest {

    private TaskService taskService;

    @BeforeMethod
    public void setUp() {
        taskService = new TaskService();
    }

    @Test
    public void testGetAllTasksWhenEmpty() {
        List<Task> tasks = taskService.getAllTasks();
        Assert.assertNotNull(tasks);
        Assert.assertEquals(tasks.size(), 0);
    }

    @Test
    public void testCreateTask() {
        String title = "New Task";
        Task createdTask = taskService.createTask(title);
        
        Assert.assertNotNull(createdTask);
        Assert.assertEquals(createdTask.getTitle(), title);
        Assert.assertEquals(createdTask.getId(), 1);
    }

    @Test
    public void testCreateMultipleTasks() {
        Task task1 = taskService.createTask("Task 1");
        Task task2 = taskService.createTask("Task 2");
        Task task3 = taskService.createTask("Task 3");
        
        Assert.assertEquals(task1.getId(), 1);
        Assert.assertEquals(task2.getId(), 2);
        Assert.assertEquals(task3.getId(), 3);
        
        List<Task> allTasks = taskService.getAllTasks();
        Assert.assertEquals(allTasks.size(), 3);
    }

    @Test
    public void testGetTaskById() {
        Task createdTask = taskService.createTask("Test Task");
        Task retrievedTask = taskService.getTaskById(createdTask.getId());
        
        Assert.assertNotNull(retrievedTask);
        Assert.assertEquals(retrievedTask.getId(), createdTask.getId());
        Assert.assertEquals(retrievedTask.getTitle(), createdTask.getTitle());
    }

    @Test
    public void testGetTaskByIdNotFound() {
        Task retrievedTask = taskService.getTaskById(999);
        Assert.assertNull(retrievedTask);
    }

    @Test
    public void testDeleteTask() {
        Task createdTask = taskService.createTask("Task to Delete");
        int taskId = createdTask.getId();
        
        boolean deleted = taskService.deleteTask(taskId);
        Assert.assertTrue(deleted);
        
        Task retrievedTask = taskService.getTaskById(taskId);
        Assert.assertNull(retrievedTask);
        
        List<Task> allTasks = taskService.getAllTasks();
        Assert.assertEquals(allTasks.size(), 0);
    }

    @Test
    public void testDeleteTaskNotFound() {
        boolean deleted = taskService.deleteTask(999);
        Assert.assertFalse(deleted);
    }

    @Test
    public void testDeleteTaskFromMultipleTasks() {
        taskService.createTask("Task 1");
        taskService.createTask("Task 2");
        taskService.createTask("Task 3");
        
        boolean deleted = taskService.deleteTask(2);
        Assert.assertTrue(deleted);
        
        List<Task> allTasks = taskService.getAllTasks();
        Assert.assertEquals(allTasks.size(), 2);
        
        // Verify remaining tasks
        Task task1 = taskService.getTaskById(1);
        Task task3 = taskService.getTaskById(3);
        Assert.assertNotNull(task1);
        Assert.assertNotNull(task3);
        Assert.assertNull(taskService.getTaskById(2));
    }

    @Test
    public void testCreateTaskWithEmptyTitle() {
        Task task = taskService.createTask("");
        Assert.assertNotNull(task);
        Assert.assertEquals(task.getTitle(), "");
    }

    @Test
    public void testCreateTaskWithNullTitle() {
        Task task = taskService.createTask(null);
        Assert.assertNotNull(task);
        Assert.assertNull(task.getTitle());
    }
} 