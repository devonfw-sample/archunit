package com.devonfw.sample.archunit.task.logic;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.devonfw.sample.archunit.general.logic.AbstractUc;
import com.devonfw.sample.archunit.task.common.TaskItemEto;
import com.devonfw.sample.archunit.task.common.security.ApplicationAccessControlConfig;
import com.devonfw.sample.archunit.task.dataaccess.TaskItemEntity;
import com.devonfw.sample.archunit.task.dataaccess.TaskItemRepository;

/**
 * {@link AbstractUc Use-case} to save {@link com.devonfw.sample.archunit.task.common.TaskItem}s.
 */
@ApplicationScoped
@Named
@Transactional
public class UcSaveTaskItem extends AbstractUc {

  @Inject
  TaskItemRepository taskItemRepository;

  @Inject
  TaskItemMapper taskItemMapper;

  /**
   * @param item the {@link TaskItemEto} to save.
   * @return the {@link TaskItemEntity#getId() primary key} of the saved {@link TaskItemEntity}.
   */
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_SAVE_TASK_ITEM)
  public Long save(TaskItemEto item) {

    TaskItemEntity entity = this.taskItemMapper.toEntity(item);
    entity = this.taskItemRepository.save(entity);
    return entity.getId();
  }

}
