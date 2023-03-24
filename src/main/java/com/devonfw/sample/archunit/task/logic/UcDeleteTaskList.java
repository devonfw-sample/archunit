package com.devonfw.sample.archunit.task.logic;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.devonfw.sample.archunit.task.common.security.ApplicationAccessControlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devonfw.sample.archunit.general.logic.AbstractUc;
import com.devonfw.sample.archunit.task.common.TaskListEto;
import com.devonfw.sample.archunit.task.dataaccess.TaskListRepository;

/**
 * {@link AbstractUc Use-case} to delete {@link com.devonfw.sample.archunit.task.common.TaskList}s.
 */
@ApplicationScoped
@Named
@Transactional
public class UcDeleteTaskList extends AbstractUc {

  private static final Logger LOG = LoggerFactory.getLogger(UcDeleteTaskList.class);

  @Inject
  TaskListRepository taskListRepository;

  /**
   * @param id the {@link com.devonfw.sample.archunit.task.dataaccess.TaskListEntity#getId() primary key} of the
   *        {@link com.devonfw.sample.archunit.task.dataaccess.TaskListEntity} to delete.
   */
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_DELETE_TASK_ITEM)
  public void delete(Long id) {

    this.taskListRepository.deleteById(id);
  }

  /**
   * @param list the {@link TaskListEto} to delete.
   */
   @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_DELETE_TASK_ITEM)
  public void delete(TaskListEto list) {

    Long id = list.getId();
    if (id == null) {
      LOG.info("TaskItem {} ist transient und kann nicht gelöscht werden", list.getTitle());
    }
    this.taskListRepository.deleteById(id);
  }

}
