package com.taotechs.taskmanager.service;

import com.taotechs.taskmanager.dto.TaskDTO;
import com.taotechs.taskmanager.model.Task;
import com.taotechs.taskmanager.model.TaskStatus;
import com.taotechs.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask() {
        TaskDTO taskDTO = new TaskDTO(null, "New Task", TaskStatus.INCOMPLETE, null);
        Task savedTask = new Task(1L, "New Task", TaskStatus.INCOMPLETE, null);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskDTO result = taskService.createTask(taskDTO);

        assertNotNull(result);
        assertEquals("New Task", result.title());
        assertEquals(TaskStatus.INCOMPLETE, result.status());
    }

    @Test
    void testUpdateTask() {
        Task existingTask = new Task(1L, "Old Task", TaskStatus.INCOMPLETE, null);
        TaskDTO updatedTaskDTO = new TaskDTO(1L, "Updated Task", TaskStatus.COMPLETED, null);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskDTO result = taskService.updateTask(1L, updatedTaskDTO);

        assertNotNull(result);
        assertEquals("Updated Task", result.title());
        assertEquals(TaskStatus.COMPLETED, result.status());
    }

    @Test
    void testFindTaskById() {
        Task task = new Task(1L, "Sample Task", TaskStatus.INCOMPLETE, null);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<TaskDTO> result = taskService.getTaskById(1L);

        assertTrue(result.isPresent());
        assertEquals("Sample Task", result.get().title());
    }

    @Test
    void testFindTaskByIdNotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<TaskDTO> result = taskService.getTaskById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetAllTasks() {
        List<Task> tasks = List.of(
                new Task(1L, "Task 1", TaskStatus.INCOMPLETE, null),
                new Task(2L, "Task 2", TaskStatus.COMPLETED, null)
        );
        when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskDTO> result = taskService.getAllTasks();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).title());
        assertEquals("Task 2", result.get(1).title());
    }

    @Test
    void testDeleteTask() {
        Task task = new Task(1L, "Task to Delete", TaskStatus.INCOMPLETE, null);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).deleteById(1L);

        assertDoesNotThrow(() -> taskService.deleteTask(1L));
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
