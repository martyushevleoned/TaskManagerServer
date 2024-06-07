package ru.urfu.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.model.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
