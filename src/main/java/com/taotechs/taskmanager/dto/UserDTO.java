package com.taotechs.taskmanager.dto;

import com.taotechs.taskmanager.model.User;

public record UserDTO(Long id, String username) {

    public UserDTO(User user) {
        this(user.getId(), user.getUsername());
    }

    public static UserDTO fromEntity(User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }
}
