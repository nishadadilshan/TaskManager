package com.example.taskmanager.model;

import org.testng.annotations.Test;
import org.testng.Assert;

public class TaskTest {

    @Test
    public void testTaskCreation() {
        Task task = new Task();
        Assert.assertNotNull(task);
    }

    @Test
    public void testSetAndGetId() {
        Task task = new Task();
        task.setId(1);
        Assert.assertEquals(task.getId(), 1);
    }

    @Test
    public void testSetAndGetTitle() {
        Task task = new Task();
        String title = "Test Task";
        task.setTitle(title);
        Assert.assertEquals(task.getTitle(), title);
    }

    @Test
    public void testTaskWithMultipleFields() {
        Task task = new Task();
        task.setId(5);
        task.setTitle("Complete Project");
        
        Assert.assertEquals(task.getId(), 5);
        Assert.assertEquals(task.getTitle(), "Complete Project");
    }

    @Test
    public void testTaskWithEmptyTitle() {
        Task task = new Task();
        task.setTitle("");
        Assert.assertEquals(task.getTitle(), "");
    }

    @Test
    public void testTaskWithNullTitle() {
        Task task = new Task();
        task.setTitle(null);
        Assert.assertNull(task.getTitle());
    }
} 