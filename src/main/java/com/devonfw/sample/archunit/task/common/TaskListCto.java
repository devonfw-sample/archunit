package com.devonfw.sample.archunit.task.common;

import java.util.ArrayList;
import java.util.List;

import com.devonfw.sample.archunit.general.common.MasterCto;

/**
 * {@link MasterCto CTO} for a {@link TaskListEto task-list} together with its {@link TaskItemEto task-items}.
 */
public class TaskListCto extends MasterCto<TaskListEto> {

  private List<TaskItemEto> items;

  /**
   * The constructor.
   */
  public TaskListCto() {

    super();
  }

  /**
   * The constructor.
   *
   * @param eto the {@link #getEto() owning task-list}.
   */
  public TaskListCto(TaskListEto eto) {

    super();
    setEto(eto);
  }

  /**
   * @return items the {@link List} of {@link TaskItemEto items} belonging to the owning {@link #getEto() task-list}.
   */
  public List<TaskItemEto> getItems() {

    if (this.items == null) {
      this.items = new ArrayList<>();
    }
    return this.items;
  }

  /**
   * @param items new value of {@link #getItems()}.
   */
  public void setItems(List<TaskItemEto> items) {

    this.items = items;
  }

}
