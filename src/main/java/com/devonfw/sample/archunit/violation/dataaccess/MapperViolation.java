package com.devonfw.sample.archunit.violation.dataaccess;

import org.mapstruct.Mapper;

/**
 * {@link Mapper} for {@link com.devonfw.sample.archunit.task.common.TaskItem}.
 */
@Mapper(componentModel = "cdi")
public abstract interface MapperViolation {

  /*
   *  @Mapper (org.mapstruct.Mapper) need to be in layer logic and have to end with Mapper.
   */
}