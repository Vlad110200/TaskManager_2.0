package vlad.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import vlad.taskmanager.app.TaskStatus;

import java.time.LocalDateTime;

public class TaskResponseDto {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public TaskResponseDto(){}

    public TaskResponseDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId(){
        return id;
    }

    public TaskStatus getStatus(){
        return status;
    }

    public String getTitle(){
        return title;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public String getDescription(){
        return description;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setStatus(TaskStatus status){
        this.status = status;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

}
