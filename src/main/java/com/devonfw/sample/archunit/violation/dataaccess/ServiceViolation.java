package com.devonfw.sample.archunit.violation.dataaccess;

import javax.ws.rs.Path;

/**
 * Rest service for task component with {@link com.devonfw.sample.archunit.task.common.TaskList} and
 * {@link com.devonfw.sample.archunit.task.common.TaskItem}.
 */
 // Violation: REST-Service has to be in service layer and name has to end with "Service".
@Path("/task")
public class ServiceViolation {
}
