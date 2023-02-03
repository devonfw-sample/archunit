package com.devonfw.sample.archunit.task.logic;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devonfw.sample.archunit.general.logic.AbstractUc;
import com.devonfw.sample.archunit.task.common.TaskItemEto;
import com.devonfw.sample.archunit.task.dataaccess.TaskItemRepository;

/**
 * {@link AbstractUc Use-case} to delete {@link com.devonfw.sample.archunit.task.common.TaskItem}s.
 */
@ApplicationScoped
@Named
@Transactional
public class UcDeleteTaskItem extends AbstractUc {

  private static final Logger LOG = LoggerFactory.getLogger(UcDeleteTaskItem.class);

  @Inject
  TaskItemRepository taskItemRepository;

  /**
   * @param id the {@link com.devonfw.sample.archunit.task.dataaccess.TaskListEntity#getId() primary key} of the
   *        {@link com.devonfw.sample.archunit.task.dataaccess.TaskListEntity} to delete.
   */
  // @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_DELETE_TASK_ITEM)
  public void delete(Long id) {

    this.taskItemRepository.deleteById(id);
  }

  /**
   * @param item the {@link TaskItemEto} to delete.
   */
  // @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_DELETE_TASK_ITEM)
  public void delete(TaskItemEto item) {

    Long id = item.getId();
    if (id == null) {
      LOG.info("TaskItem {} ist transient und kann nicht gelöscht werden", item.getTitle());
    }
    this.taskItemRepository.deleteById(id);
  }

}
