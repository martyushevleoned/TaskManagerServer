package ru.urfu.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.urfu.model.entity.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("""
            SELECT t
            FROM Task t, User u 
            WHERE t.user.username = ?1
            """)
    List<Task> findTasksByUsername(String username);
}
