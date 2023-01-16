package com.devonfw.sample.archunit.task.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for the {@link JpaRepository} giving database access to {@link TaskListEntity}.
 */
public interface TaskListRepository extends JpaRepository<TaskListEntity, Long> {

}
