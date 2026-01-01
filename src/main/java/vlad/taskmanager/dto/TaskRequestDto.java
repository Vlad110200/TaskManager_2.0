package vlad.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskRequestDto {

    @NotBlank
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotBlank
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    public TaskRequestDto() {}

    public TaskRequestDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
