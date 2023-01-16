package com.devonfw.sample.archunit.task.common;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.devonfw.sample.archunit.general.common.AbstractEto;

/**
 * {@link TaskItem} implementation as {@link AbstractEto}.
 */
public class TaskItemEto extends AbstractEto implements TaskItem {

  @NotBlank(message = "A task item must always have a title")
  private String title;

  private boolean completed;

  private boolean starred;

  @Min(value = 1, message = "A task item must always be associated with a task list")
  @NotNull(message = "A task item must always be associated with a task list")
  private Long taskListId;

  private LocalDateTime deadline;

  @Override
  public String getTitle() {

    return this.title;
  }

  @Override
  public void setTitle(String title) {

    this.title = title;
  }

  @Override
  public boolean isCompleted() {

    return this.completed;
  }

  @Override
  public void setCompleted(boolean completed) {

    this.completed = completed;
  }

  @Override
  public boolean isStarred() {

    return this.starred;
  }

  @Override
  public void setStarred(boolean starred) {

    this.starred = starred;
  }

  @Override
  public Long getTaskListId() {

    return this.taskListId;
  }

  @Override
  public void setTaskListId(Long taskListId) {

    this.taskListId = taskListId;
  }

  @Override
  public LocalDateTime getDeadline() {

    return this.deadline;
  }

  @Override
  public void setDeadline(LocalDateTime deadline) {

    this.deadline = deadline;
  }
}
