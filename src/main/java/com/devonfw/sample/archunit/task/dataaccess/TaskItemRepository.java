package com.devonfw.sample.archunit.task.dataaccess;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Interface for the {@link JpaRepository} giving database access to {@link TaskItemEntity}.
 */
public interface TaskItemRepository extends JpaRepository<TaskItemEntity, Long> {

  /**
   * @param completed - the {@link TaskItemEntity#isCompleted() completed} flag.
   * @param starred - the {@link TaskItemEntity#isStarred() starred} flag.
   * @return the {@link List} with all matching {@link TaskItemEntity} hits.
   */
  @Query("SELECT item FROM TaskItemEntity item WHERE item.completed = :completed AND item.starred = :starred")
  List<TaskItemEntity> findByFlags(@Param("completed") boolean completed, @Param("starred") boolean starred);

  /**
   * @param deadline the maximum {@link TaskItemEntity#getDeadline() deadline}. Use {@link LocalDateTime#now()} to get
   *        all overdue items, or add a given {@link java.time.Duration} from {@link LocalDateTime#now()} to also get
   *        the items that will get overdue after this {@link java.time.Duration}.
   * @return the {@link List} with all matching {@link TaskItemEntity} hits.
   */
  @Query("SELECT item FROM TaskItemEntity item WHERE item.deadline < :deadline")
  List<TaskItemEntity> findByDeadline(@Param("deadline") LocalDateTime deadline);

}
