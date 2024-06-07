package ru.urfu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.model.dto.TaskDto;
import ru.urfu.model.entity.Task;
import ru.urfu.model.entity.User;
import ru.urfu.model.repository.TaskRepository;
import ru.urfu.model.repository.UserRepository;

import java.security.Principal;
import java.util.List;

@Service
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

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

    public List<TaskDto> getAllTasks(Principal principal) {
        try {
            return taskRepository.findTasksByUsername(principal.getName()).stream().map(t -> new TaskDto(t.getId(), t.getText())).toList();
        } catch (Exception e) {
            throw new RuntimeException("Пользователь не найден");
        }
    }

    public void deleteTaskById(Long id) {
        try {
            taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Задача не найдена");
        }
    }
}
