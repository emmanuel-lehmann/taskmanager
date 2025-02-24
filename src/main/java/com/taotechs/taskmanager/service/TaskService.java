package com.taotechs.taskmanager.service;

import com.taotechs.taskmanager.dto.TaskDTO;
import com.taotechs.taskmanager.dto.UserDTO;
import com.taotechs.taskmanager.mapper.TaskMapper;
import com.taotechs.taskmanager.model.Task;
import com.taotechs.taskmanager.model.User;
import com.taotechs.taskmanager.repository.TaskRepository;
import com.taotechs.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = TaskMapper.toEntity(taskDTO);

        if (taskDTO.assignedTo() != null) {
            userRepository.findById(taskDTO.assignedTo().id()).ifPresent(task::setUser);
        }

        return TaskMapper.toDTO(taskRepository.save(task));
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(taskDTO.title());
        task.setStatus(taskDTO.status());

        if (taskDTO.assignedTo() != null) {
            userRepository.findById(taskDTO.assignedTo().id()).ifPresent(task::setUser);
        } else {
            task.setUser(null);
        }

        return TaskMapper.toDTO(taskRepository.save(task));
    }

    public TaskDTO assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(user);
        return TaskMapper.toDTO(taskRepository.save(task));
    }

    public List<TaskDTO> getTasksByUser(Long userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TaskDTO> getTaskById(Long id) {
        return taskRepository.findById(id).map(TaskMapper::toDTO);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
