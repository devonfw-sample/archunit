package com.devonfw.sample.archunit.task.common;

import com.devonfw.sample.archunit.general.common.ApplicationEntity;

/**
 * {@link ApplicationEntity} for a task list. It groups {@link TaskItem}s that belong together to a list.
 */
public interface TaskList extends ApplicationEntity {

  /**
   * @return the title of this {@link TaskList}. Gives a brief summary to describe this list of tasks (e.g. "Shopping
   *         list", "Packing list" or "Things to buy at construction market").
   */
  String getTitle();

  /**
   * @param title new value of {@link #getTitle()}.
   */
  void setTitle(String title);
}
