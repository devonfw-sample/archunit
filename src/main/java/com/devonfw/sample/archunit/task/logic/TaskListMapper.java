package com.devonfw.sample.archunit.task.logic;

import org.mapstruct.Mapper;

import com.devonfw.sample.archunit.task.common.TaskListEto;
import com.devonfw.sample.archunit.task.dataaccess.TaskListEntity;

/**
 * {@link Mapper} for {@link com.devonfw.sample.archunit.task.common.TaskList}.
 */
@Mapper(componentModel = "cdi")
public interface TaskListMapper
 {

  /**
   * @param task the {@link TaskListEntity} to map.
   * @return the mapped {@link TaskListEto}.
   */
  TaskListEto toEto(TaskListEntity task);

  /**
   * @param task the {@link TaskListEto} to map.
   * @return the mapped {@link TaskListEntity}.
   */
  TaskListEntity toEntity(TaskListEto task);
}