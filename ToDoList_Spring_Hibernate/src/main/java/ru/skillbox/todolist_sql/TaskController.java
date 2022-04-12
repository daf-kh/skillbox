package ru.skillbox.todolist_sql;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.todolist_sql.model.Task;
import ru.skillbox.todolist_sql.model.TaskRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    private TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElse(null);
    }

    @PostMapping("/tasks")
    public int addTask(@RequestBody Task task) {
        return taskRepository.save(task).getId();
    }

    @PutMapping("/tasks/{id}")
    public String updateTask(@PathVariable int id, @RequestBody Task task) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            task.setId(id);
            taskRepository.save(task);
            return "Task with id " + id + " was updated: " + task;
        }
        return "There is no task with id " + id;
    }

    @DeleteMapping("/tasks/{id}")
    public String deleteTask(@PathVariable int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            taskRepository.delete(optionalTask.get());
            return "Task with id " + id + " was deleted";
        }
        return "There is no task with id " + id;
    }

    @DeleteMapping("/tasks")
    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

}
