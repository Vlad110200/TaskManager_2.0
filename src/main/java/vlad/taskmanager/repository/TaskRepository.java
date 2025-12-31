package vlad.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlad.taskmanager.app.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
