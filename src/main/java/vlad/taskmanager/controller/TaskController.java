package vlad.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlad.taskmanager.dto.TaskRequestDto;
import vlad.taskmanager.dto.TaskResponseDto;
import vlad.taskmanager.service.TaskService;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "Operation with tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    public Page<TaskResponseDto> getTasks(
            @PageableDefault(size = 10, sort = "id")Pageable pageable
            ) {
        return taskService.getAll(pageable);
    }

    @Operation(summary = "Get task by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task found"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{id}")
    public TaskResponseDto getById(@PathVariable Long id){
        return taskService.getById(id);
    }

    @Operation(
            summary = "Create task",
            description = "Creates a new task with TODO status"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Task created"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody @Valid TaskRequestDto dto){

        TaskResponseDto response = taskService.create(dto);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("{id}")
    public TaskResponseDto updateTask(@PathVariable Long id,
                                      @RequestBody @Valid TaskRequestDto dto){
        return taskService.update(id, dto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id){
        taskService.delete(id);
    }

    @PatchMapping("/{id}/complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void completeTask(@PathVariable Long id){
        taskService.complete(id);
    }
}
