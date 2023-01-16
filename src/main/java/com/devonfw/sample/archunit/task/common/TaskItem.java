package com.devonfw.sample.archunit.task.common;

import java.time.LocalDateTime;

import com.devonfw.sample.archunit.general.common.ApplicationEntity;

/**
 * {@link ApplicationEntity} for a single task item.
 */
public interface TaskItem extends ApplicationEntity {

  /**
   * @return the title of this task item. Gives a brief summary to describe what to do or buy.
   */
  String getTitle();

  /**
   * @param title new value of {@link #getTitle()}.
   */
  void setTitle(String title);

  /**
   * @return {@code true} if this task is completed (done), {@code false} otherwise.
   */
  boolean isCompleted();

  /**
   * @param completed new value of {@link #isCompleted()}.
   */
  void setCompleted(boolean completed);

  /**
   * @return {@code true} if this task is starred (marked as favorite), {@code false} otherwise.
   */
  boolean isStarred();

  /**
   * @param starred new value of {@link #isStarred()}.
   */
  void setStarred(boolean starred);

  /**
   * @return the deadline as until this task shall be {@link #isCompleted() completed}.
   */
  LocalDateTime getDeadline();

  /**
   * @param deadline the new value of {@link #getDeadline()}.
   */
  void setDeadline(LocalDateTime deadline);

  /**
   * @return the {@link TaskList#getId() primary key} of the owning {@link TaskList}.
   */
  Long getTaskListId();

  /**
   * @param taskListId the new value of {@link #getTaskListId()}.
   */
  void setTaskListId(Long taskListId);
}
