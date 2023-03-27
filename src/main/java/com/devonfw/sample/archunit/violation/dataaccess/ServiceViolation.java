package com.devonfw.sample.archunit.violation.dataaccess;

import javax.ws.rs.Path;

/**
 * Rest service for task component with {@link com.devonfw.sample.archunit.task.common.TaskList} and
 * {@link com.devonfw.sample.archunit.task.common.TaskItem}.
 */
@Path("/task")
public class ServiceViolation {

  /*
   * @Path (javax.ws.rs.Path) need to be in layer service and have to end with Service.
   */
}
