package vlad.taskmanager.dto;

import vlad.taskmanager.app.Task;

public class TaskMapper {

    public static Task toEntity(TaskRequestDto dto){
        return new Task(
                dto.getTitle(),
                dto.getDescription()
        );
    }

    public static TaskResponseDto toDto(Task task){
        TaskResponseDto dto = new TaskResponseDto();
                dto.setId(task.getId());
                dto.setTitle(task.getTitle());
                dto.setDescription(task.getDescription());
                dto.setStatus(task.getStatus());
                dto.setCreatedAt(task.getCreatedAt());
                return dto;
    }
}
