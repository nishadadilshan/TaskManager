package com.example.taskmanager.integration;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.controller.TaskController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskController taskController;

    private String baseUrl;

    @BeforeMethod
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/tasks";
    }

    @Test
    public void testFullTaskLifecycle() {
        // Test creating a task
        String title = "Integration Test Task";
        ResponseEntity<Task> createResponse = restTemplate.postForEntity(
            baseUrl + "?title=" + title, null, Task.class);
        
        Assert.assertEquals(createResponse.getStatusCode(), HttpStatus.OK);
        Task createdTask = createResponse.getBody();
        Assert.assertNotNull(createdTask);
        Assert.assertEquals(createdTask.getTitle(), title);
        Assert.assertEquals(createdTask.getId(), 1);

        // Test getting all tasks
        ResponseEntity<Task[]> getAllResponse = restTemplate.getForEntity(
            baseUrl, Task[].class);
        
        Assert.assertEquals(getAllResponse.getStatusCode(), HttpStatus.OK);
        Task[] tasks = getAllResponse.getBody();
        Assert.assertNotNull(tasks);
        Assert.assertEquals(tasks.length, 1);
        Assert.assertEquals(tasks[0].getId(), 1);
        Assert.assertEquals(tasks[0].getTitle(), title);

        // Test deleting the task
        ResponseEntity<String> deleteResponse = restTemplate.exchange(
            baseUrl + "/" + createdTask.getId(), 
            org.springframework.http.HttpMethod.DELETE, 
            null, String.class);
        
        Assert.assertEquals(deleteResponse.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(deleteResponse.getBody(), "Deleted");

        // Verify task is deleted
        ResponseEntity<Task[]> getAllAfterDeleteResponse = restTemplate.getForEntity(
            baseUrl, Task[].class);
        
        Assert.assertEquals(getAllAfterDeleteResponse.getStatusCode(), HttpStatus.OK);
        Task[] tasksAfterDelete = getAllAfterDeleteResponse.getBody();
        Assert.assertNotNull(tasksAfterDelete);
        Assert.assertEquals(tasksAfterDelete.length, 0);
    }

    @Test
    public void testCreateMultipleTasks() {
        // Create multiple tasks
        restTemplate.postForEntity(baseUrl + "?title=Task 1", null, Task.class);
        restTemplate.postForEntity(baseUrl + "?title=Task 2", null, Task.class);
        restTemplate.postForEntity(baseUrl + "?title=Task 3", null, Task.class);

        // Get all tasks
        ResponseEntity<Task[]> response = restTemplate.getForEntity(baseUrl, Task[].class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Task[] tasks = response.getBody();
        Assert.assertNotNull(tasks);
        Assert.assertEquals(tasks.length, 3);
    }

    @Test
    public void testDeleteNonExistentTask() {
        ResponseEntity<String> response = restTemplate.exchange(
            baseUrl + "/999", 
            org.springframework.http.HttpMethod.DELETE, 
            null, String.class);
        
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), "Not Found");
    }

    @Test
    public void testCreateTaskWithEmptyTitle() {
        ResponseEntity<Task> response = restTemplate.postForEntity(
            baseUrl + "?title=", null, Task.class);
        
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Task task = response.getBody();
        Assert.assertNotNull(task);
        Assert.assertEquals(task.getTitle(), "");
    }

    @Test
    public void testApplicationContextLoads() {
        Assert.assertNotNull(taskService);
        Assert.assertNotNull(taskController);
    }
} 