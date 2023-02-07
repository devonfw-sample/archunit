package com.devonfw.sample.archunit.task.logic;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.devonfw.sample.archunit.general.logic.AbstractUc;
import com.devonfw.sample.archunit.task.common.TaskListEto;
import com.devonfw.sample.archunit.task.dataaccess.TaskListEntity;
import com.devonfw.sample.archunit.task.dataaccess.TaskListRepository;

/**
 * {@link AbstractUc Use-case} to save {@link com.devonfw.sample.archunit.task.common.TaskList}s.
 */
@ApplicationScoped
@Named
@Transactional
public class UcSaveTaskList extends AbstractUc {

  @Inject
  TaskListRepository taskListRepository;

  @Inject
  TaskListMapper taskListMapper;

  /**
   * @param list the {@link TaskListEto} to save.
   * @return the {@link TaskListEntity#getId() primary key} of the saved {@link TaskListEntity}.
   */
  // @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_SAVE_TASK_LIST)
  public Long save(TaskListEto list) {

    TaskListEntity entity = this.taskListMapper.toEntity(list);
    entity = this.taskListRepository.save(entity);
    return entity.getId();
  }

}
