package com.devonfw.sample.archunit.violation.dataaccess;

import org.mapstruct.Mapper;

/**
 * {@link Mapper} for {@link com.devonfw.sample.archunit.task.common.TaskItem}.
 */
// Violation: Mapper has to be in logic layer and name has to end with Mapper.
@Mapper(componentModel = "cdi")
public abstract interface MapperViolation {
}
