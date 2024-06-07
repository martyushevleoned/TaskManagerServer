package ru.urfu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.urfu.model.dto.UserInfoDto;
import ru.urfu.service.UserService;

import java.security.Principal;

@Controller
public class AuthenticatedController {

    private UserService userService;

    @Autowired
    public AuthenticatedController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> getInfo(Principal principal){
        return ResponseEntity.ok(userService.getUserInfo(principal));
    }
}
