package ru.skillbox.todolist_sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skillbox.todolist_sql.model.Task;
import ru.skillbox.todolist_sql.model.TaskRepository;

import java.util.List;

@Controller
public class DefaultController {

    @Autowired
    TaskRepository taskRepository;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("task", new Task());
        return "index";
    }

    @PostMapping("/")
    public String taskSubmit(@ModelAttribute Task task, Model model) {
        model.addAttribute("task", task);
        taskRepository.save(task);
        List<Task> tasksList = taskRepository.findAll();
        model.addAttribute("tasksList", tasksList);
        long tasksCount = taskRepository.count();
        model.addAttribute("tasksCount", tasksCount);
        return "result";
    }
}
