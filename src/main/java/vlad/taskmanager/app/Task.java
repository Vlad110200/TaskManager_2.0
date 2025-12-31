package vlad.taskmanager.app;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.TODO;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Task() {}

    public Task(String title, String description){
        this.title = title;
        this.description = description;
    }

    public Long getId(){
        return id;
    }

    public TaskStatus getStatus(){
        return status;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setStatus(TaskStatus status){
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
