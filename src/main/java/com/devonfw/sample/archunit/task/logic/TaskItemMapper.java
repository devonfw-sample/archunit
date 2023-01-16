package com.devonfw.sample.archunit.task.logic;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

import com.devonfw.sample.archunit.task.common.TaskItemEto;
import com.devonfw.sample.archunit.task.dataaccess.TaskItemEntity;

/**
 * {@link Mapper} for {@link com.devonfw.sample.archunit.task.common.TaskItem}.
 */
@Mapper(componentModel = "cdi")
public interface TaskItemMapper {

  /**
   * @param items the {@link List} of {@link TaskItemEntity items} to convert.
   * @return the {@link List} of converted {@link TaskItemEto}s.
   */
  default List<TaskItemEto> toEtos(List<TaskItemEntity> items) {

    List<TaskItemEto> etos = new ArrayList<>(items.size());
    for (TaskItemEntity item : items) {
      etos.add(toEto(item));
    }
    return etos;
  }

  /**
   * @param item the {@link TaskItemEntity} to map.
   * @return the mapped {@link TaskItemEto}.
   */
  TaskItemEto toEto(TaskItemEntity item);

  /**
   * @param item the {@link TaskItemEto} to map.
   * @return the mapped {@link TaskItemEntity}.
   */
  TaskItemEntity toEntity(TaskItemEto item);
}