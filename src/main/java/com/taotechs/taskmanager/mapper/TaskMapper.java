package com.taotechs.taskmanager.mapper;

import com.taotechs.taskmanager.dto.TaskDTO;
import com.taotechs.taskmanager.dto.UserDTO;
import com.taotechs.taskmanager.model.Task;

public class TaskMapper {

    public static TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getUser() != null ? new UserDTO(task.getUser()) : null
        );
    }

    public static Task toEntity(TaskDTO taskDTO) {
        return new Task(
                taskDTO.id(),
                taskDTO.title(),
                taskDTO.status(),
                null
        );
    }
}
