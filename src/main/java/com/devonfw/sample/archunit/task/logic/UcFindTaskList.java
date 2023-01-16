package com.devonfw.sample.archunit.task.logic;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.devonfw.sample.archunit.task.common.TaskListEto;
import com.devonfw.sample.archunit.task.dataaccess.TaskListEntity;
import com.devonfw.sample.archunit.task.dataaccess.TaskListRepository;

/**
 * Use-Case to find {@link TaskListEntity task-lists}.
 */
@ApplicationScoped
@Named
@Transactional
public class UcFindTaskList {

  @Inject
  TaskListRepository taskListRepository;

  @Inject
  TaskListMapper taskListMapper;

  /**
   * @param listId the {@link TaskListEntity#getId() primary key} of the {@link TaskListEntity} to find.
   * @return the {@link TaskListEto} for the given {@link TaskListEto#getId() primary key} or {@code null} if not found.
   */
  // @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_FIND_TASK_LIST)
  public TaskListEto findById(Long listId) {

    Optional<TaskListEntity> taskList = this.taskListRepository.findById(listId);
    return taskList.map(taskListEntity -> this.taskListMapper.toEto(taskListEntity)).orElse(null);
  }

}
