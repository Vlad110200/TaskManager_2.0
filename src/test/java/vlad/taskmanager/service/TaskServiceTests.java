package vlad.taskmanager.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vlad.taskmanager.app.TaskStatus;
import vlad.taskmanager.dto.TaskRequestDto;
import vlad.taskmanager.dto.TaskResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class TaskServiceTests {

    @Autowired
    TaskService taskService;

    @Test
    void create_shouldSetDefaultStatus() {
        TaskRequestDto dto =
                new TaskRequestDto("Test_task", "Description");
        TaskResponseDto result = taskService.create(dto);

        assertThat(result.getStatus()).isEqualTo(TaskStatus.TODO);
        assertThat(result.getId()).isNotNull();
    }
}
