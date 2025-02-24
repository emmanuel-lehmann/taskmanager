package com.taotechs.taskmanager.dto;

import com.taotechs.taskmanager.model.Task;
import com.taotechs.taskmanager.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskDTO(
        Long id,

        @NotBlank(message = "Title cannot be empty")
        String title,

        @NotNull(message = "Status cannot be null")
        TaskStatus status,

        UserDTO assignedTo
) {
        public TaskDTO(Task task) {
                this(task.getId(), task.getTitle(), task.getStatus(),
                        task.getUser() != null ? new UserDTO(task.getUser()) : null);
        }
}

