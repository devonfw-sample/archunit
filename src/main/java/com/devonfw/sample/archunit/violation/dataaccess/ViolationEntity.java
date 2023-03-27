package com.devonfw.sample.archunit.violation.dataaccess;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.devonfw.sample.archunit.general.dataaccess.ApplicationPersistenceEntity;
import com.devonfw.sample.archunit.task.common.TaskItem;

/**
 * {@link TaskItem} implementation as {@link ApplicationPersistenceEntity}.
 */
@Entity
@Table(name = "TASK_ITEM")
public class ViolationEntity extends ApplicationPersistenceEntity implements TaskItem {

  @Override
  public String getTitle() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTitle'");
  }

  @Override
  public void setTitle(String title) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setTitle'");
  }

  @Override
  public boolean isCompleted() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isCompleted'");
  }

  @Override
  public void setCompleted(boolean completed) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setCompleted'");
  }

  @Override
  public boolean isStarred() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isStarred'");
  }

  @Override
  public void setStarred(boolean starred) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setStarred'");
  }

  @Override
  public LocalDateTime getDeadline() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getDeadline'");
  }

  @Override
  public void setDeadline(LocalDateTime deadline) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setDeadline'");
  }

  @Override
  public Long getTaskListId() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTaskListId'");
  }

  @Override
  public void setTaskListId(Long taskListId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setTaskListId'");
  }

 
}
