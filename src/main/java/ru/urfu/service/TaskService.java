package ru.urfu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.model.entity.Task;
import ru.urfu.model.entity.User;
import ru.urfu.model.repository.TaskRepository;
import ru.urfu.model.repository.UserRepository;

import java.security.Principal;

@Service
public class TaskService {

    private UserRepository userRepository;
    private TaskRepository taskRepository;

    @Autowired
    public TaskService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public void addTask(Principal principal, String taskText) {
        try {
            User user = userRepository.findByUsername(principal.getName());
            Task task = new Task();
            task.setText(taskText);
            task.setUser(user);
            taskRepository.save(task);
        } catch (Exception e) {
            throw new RuntimeException("Пользователь не найден");
        }
    }
}
