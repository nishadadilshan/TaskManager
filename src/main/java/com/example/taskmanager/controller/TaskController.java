package com.example.taskmanager.controller;


import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    public Task createTask(@RequestParam String title) {
        return taskService.createTask(title);
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable int id) {
        boolean removed = taskService.deleteTask(id);
        return removed? "Deleted" : "Not Found";
    }
}
