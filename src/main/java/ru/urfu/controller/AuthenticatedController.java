package ru.urfu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urfu.model.dto.TaskDto;
import ru.urfu.model.dto.UserInfoDto;
import ru.urfu.service.TaskService;
import ru.urfu.service.UserService;

import java.security.Principal;

@RestController
public class AuthenticatedController {

    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public AuthenticatedController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> getInfo(Principal principal) {
        try {
            return ResponseEntity.ok(userService.getUserInfo(principal));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/addTask")
    public ResponseEntity<?> addTask(Principal principal, @RequestBody String text) {
        try {
            taskService.addTask(principal, text);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getAllTasks")
    public ResponseEntity<?> getAllTasks(Principal principal) {
        try {
            return ResponseEntity.ok(taskService.getAllTasks(principal));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/deleteTaskById")
    public ResponseEntity<?> getAllTasks(Principal principal, @RequestBody String id) {
        try {
            taskService.deleteTaskById(Long.parseLong(id));
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
