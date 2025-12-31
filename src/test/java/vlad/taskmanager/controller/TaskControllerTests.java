package vlad.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import vlad.taskmanager.config.TestSecurityConfig;
import vlad.taskmanager.dto.TaskRequestDto;
import vlad.taskmanager.dto.TaskResponseDto;
import vlad.taskmanager.exception.ValidationExceptionHandler;
import vlad.taskmanager.security.JwtUtil;
import vlad.taskmanager.service.TaskService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@Import({TestSecurityConfig.class, ValidationExceptionHandler.class})
@ActiveProfiles("test")
public class TaskControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskService taskService;

    @MockBean
    JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_shouldReturn201() throws Exception {
        when(taskService.create(any(TaskRequestDto.class)))
                .thenAnswer(invocation -> {
                    TaskRequestDto req = invocation.getArgument(0);
                    return new TaskResponseDto(req.getTitle(), req.getDescription());
                });
        when(jwtUtil.validateToken(any())).thenReturn(true);
        when(jwtUtil.extractUsername(any())).thenReturn("user");

        mockMvc.perform(post("/tasks")
                        .header("Authorization", "Bearer dummy-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "title": "Task",
                        "description": "Description"
                        }
                        """)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Task"))
                .andExpect(jsonPath("$.description").value("Description"));
    }

    @Test
    void create_invalidRequest_shouldReturnValidationError() throws Exception {
        when(jwtUtil.validateToken(any())).thenReturn(true);
        when(jwtUtil.extractUsername(any())).thenReturn("user");

        mockMvc.perform(post("/tasks")
                .header("Authorization", "Bearer dummy-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "title": "",
                        "description": "Description"
                        }
                        """))
                .andExpect(jsonPath("$.title").value("must not be blank"));
    }

    @Test
    void getAllTasks_shouldreturn200() throws Exception {
        Page<TaskResponseDto> page = new PageImpl<>(List.of(
                new TaskResponseDto("Task1", "Desc1"),
                new TaskResponseDto("Task2", "Desc2")
        ));
        when(taskService.getAll(any(Pageable.class))).thenReturn(page);
        when(jwtUtil.validateToken(any())).thenReturn(true);
        when(jwtUtil.extractUsername(any())).thenReturn("user");

        mockMvc.perform(get("/tasks")
                .header("Authorization", "Bearer dummy-token")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].title").value("Task1"));
    }

    @Test
    void getById_shouldReturnTask() throws Exception {
        TaskResponseDto mockTask = new TaskResponseDto("Task1", "Desc1");
        when(taskService.getById(1L)).thenReturn(mockTask);

        mockMvc.perform(get("/tasks/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Task1"))
                .andExpect(jsonPath("$.description").value("Desc1"));
    }

    @Test
    void updateTask_shouldReturnUpdatedTask() throws Exception {
        TaskRequestDto updateDto = new TaskRequestDto();
        updateDto.setTitle("Updated Task");
        updateDto.setDescription("Updated Desc");

        TaskResponseDto updatedTask = new TaskResponseDto("Updated Task", "Updated Desc");
        when(taskService.update(eq(1L), any(TaskRequestDto.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/tasks/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated Desc"));
    }

    @Test
    void deleteTask_shouldReturn200() throws Exception {
        doNothing().when(taskService).delete(1L);

        mockMvc.perform(delete("/tasks/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
