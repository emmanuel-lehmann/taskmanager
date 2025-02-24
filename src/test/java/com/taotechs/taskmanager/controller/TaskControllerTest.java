package com.taotechs.taskmanager.controller;

import com.taotechs.taskmanager.dto.TaskDTO;
import com.taotechs.taskmanager.model.TaskStatus;
import com.taotechs.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private TaskDTO taskDTO;

    @BeforeEach
    void setUp() {
        taskDTO = new TaskDTO(1L, "Test Task", TaskStatus.INCOMPLETE, null);
    }

    @Test
    void testGetAllTasks() {
        List<TaskDTO> tasks = Arrays.asList(taskDTO);
        when(taskService.getAllTasks()).thenReturn(tasks);
        ResponseEntity<List<TaskDTO>> response = taskController.getAllTasks();

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetTaskById() {
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(taskDTO));
        ResponseEntity<TaskDTO> response = taskController.getTaskById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Task", response.getBody().title());
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskService.getTaskById(1L)).thenReturn(Optional.empty());
        ResponseEntity<TaskDTO> response = taskController.getTaskById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateTask() {
        when(taskService.createTask(taskDTO)).thenReturn(taskDTO);
        ResponseEntity<TaskDTO> response = taskController.createTask(taskDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Test Task", response.getBody().title());
    }

    @Test
    void testUpdateTask() {
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(new TaskDTO(1L, "Original Task", TaskStatus.INCOMPLETE, null)));
        when(taskService.updateTask(1L, taskDTO)).thenReturn(taskDTO);

        ResponseEntity<TaskDTO> response = taskController.updateTask(1L, taskDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Task", response.getBody().title());
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskService).deleteTask(1L);
        ResponseEntity<Void> response = taskController.deleteTask(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTask(1L);
    }
}
