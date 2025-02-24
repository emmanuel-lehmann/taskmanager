package com.taotechs.taskmanager.controller;

import com.taotechs.taskmanager.dto.UserDTO;
import com.taotechs.taskmanager.model.User;
import com.taotechs.taskmanager.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream().map(UserDTO::fromEntity).collect(Collectors.toList());
    }
}
