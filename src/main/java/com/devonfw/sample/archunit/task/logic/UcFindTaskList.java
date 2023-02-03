package com.devonfw.sample.archunit.task.logic;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.devonfw.sample.archunit.general.logic.AbstractUc;
import com.devonfw.sample.archunit.task.common.TaskListCto;
import com.devonfw.sample.archunit.task.common.TaskListEto;
import com.devonfw.sample.archunit.task.dataaccess.TaskListEntity;
import com.devonfw.sample.archunit.task.dataaccess.TaskListRepository;

/**
 * {@link AbstractUc Use-case} to find {@link TaskListEntity task-lists}.
 */
@ApplicationScoped
@Named
@Transactional
public class UcFindTaskList extends AbstractUc {

  @Inject
  TaskListRepository taskListRepository;

  @Inject
  TaskListMapper taskListMapper;

  @Inject
  TaskItemMapper taskItemMapper;

  /**
   * @param listId the {@link TaskListEntity#getId() primary key} of the {@link TaskListEntity} to find.
   * @return the {@link TaskListEto} for the given {@link TaskListEto#getId() primary key} or {@code null} if not found.
   */
  // @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_FIND_TASK_LIST)
  public TaskListEto findById(Long listId) {

    Optional<TaskListEntity> taskList = this.taskListRepository.findById(listId);
    return taskList.map(taskListEntity -> this.taskListMapper.toEto(taskListEntity)).orElse(null);
  }

  /**
   * @param listId the {@link TaskListEntity#getId() primary key} of the {@link TaskListEntity} to find.
   * @return the {@link TaskListCto} for the given {@link TaskListEto#getId() primary key} or {@code null} if not found.
   */
  // @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_FIND_TASK_LIST)
  public TaskListCto findCtoById(Long listId) {

    Optional<TaskListEntity> list = this.taskListRepository.findById(listId);
    if (list.isEmpty()) {
      return null;
    }
    TaskListEntity taskListEntity = list.get();
    TaskListEto taskListEto = this.taskListMapper.toEto(taskListEntity);
    TaskListCto taskListCto = new TaskListCto(taskListEto);
    taskListCto.setItems(this.taskItemMapper.toEtos(taskListEntity.getItems()));
    return taskListCto;
  }

}
