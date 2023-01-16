package com.devonfw.sample.archunit.task.common;

import javax.validation.constraints.NotBlank;

import com.devonfw.sample.archunit.general.common.AbstractEto;

/**
 * {@link TaskList} implementation as {@link AbstractEto}.
 */
public class TaskListEto extends AbstractEto implements TaskList {

  @NotBlank(message = "A task list must always have a title")
  private String title;

  @Override
  public String getTitle() {

    return this.title;
  }

  @Override
  public void setTitle(String title) {

    this.title = title;
  }

}
