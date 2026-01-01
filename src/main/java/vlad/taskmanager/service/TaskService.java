package vlad.taskmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vlad.taskmanager.app.Task;
import vlad.taskmanager.app.TaskStatus;
import vlad.taskmanager.dto.TaskMapper;
import vlad.taskmanager.dto.TaskRequestDto;
import vlad.taskmanager.dto.TaskResponseDto;
import vlad.taskmanager.exception.TaskNotFoundException;
import vlad.taskmanager.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public Page<TaskResponseDto> getAll(Pageable pageable){
        return taskRepository.findAll(pageable)
                .map(TaskMapper::toDto);
    }

    public TaskResponseDto getById(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return TaskMapper.toDto(task);
    }

    public TaskResponseDto create(TaskRequestDto dto){
        Task task = TaskMapper.toEntity(dto);
        Task saved = taskRepository.save(task);
        return TaskMapper.toDto(saved);
    }

    public TaskResponseDto update(Long id, TaskRequestDto dto){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());

        return TaskMapper.toDto(taskRepository.save(task));
    }

    public void delete(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }

    public void complete(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setStatus(TaskStatus.DONE);
        taskRepository.save(task);
    }
}
