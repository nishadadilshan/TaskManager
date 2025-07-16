package com.example.taskmanager.service;


import com.example.taskmanager.model.Task;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task getTaskById(int id) {
        return tasks.stream()
            .filter(task -> task.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public Task createTask(String title) {
        Task task = new Task();
        task.setId(tasks.size() + 1);
        task.setTitle(title);
        tasks.add(task);
        return task;
    }

   public boolean deleteTask(int id) {
    return tasks.removeIf(task -> task.getId() == id);
   }
}
