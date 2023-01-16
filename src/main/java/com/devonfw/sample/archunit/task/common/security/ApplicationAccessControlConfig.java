package com.devonfw.sample.archunit.task.common.security;

/**
 * Constants for permissions of this application.
 */
public class ApplicationAccessControlConfig {

  /** Unique identifier of this application used as namespace prefix. */
  public static final String APP_ID = "demo";

  /**
   * The namespace prefix build from {@link #APP_ID} and prepended to every permission to avoid name-clashing of
   * permissions with other applications within the same application landscape in identity & access management (IAM).
   */
  private static final String PREFIX = APP_ID + ".";

  /** Permission to find a {@link org.example.app.task.common.TaskList}. */
  public static final String PERMISSION_FIND_TASK_LIST = PREFIX + "FindTaskList";

  /** Permission to save (insert or update) a {@link org.example.app.task.common.TaskList}. */
  public static final String PERMISSION_SAVE_TASK_LIST = PREFIX + "SaveTaskList";

  /** Permission to delete a {@link org.example.app.task.common.TaskList}. */
  public static final String PERMISSION_DELETE_TASK_LIST = PREFIX + "DeleteTaskList";

  /** Permission to find a {@link org.example.app.task.common.TaskItem}. */
  public static final String PERMISSION_FIND_TASK_ITEM = PREFIX + "FindTaskItem";

  /** Permission to save (insert or update) a {@link org.example.app.task.common.TaskItem}. */
  public static final String PERMISSION_SAVE_TASK_ITEM = PREFIX + "SaveTaskItem";

  /** Permission to delete a {@link org.example.app.task.common.TaskItem}. */
  public static final String PERMISSION_DELETE_TASK_ITEM = PREFIX + "DeleteTaskItem";

}
