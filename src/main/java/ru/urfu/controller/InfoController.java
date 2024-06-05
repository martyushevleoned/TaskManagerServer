package ru.urfu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class InfoController {

    @GetMapping("/info")
    public ResponseEntity<?> getInfo(Principal principal){
        return ResponseEntity.ok(principal.getName());
    }
}
