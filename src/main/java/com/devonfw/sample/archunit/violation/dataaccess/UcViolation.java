package com.devonfw.sample.archunit.violation.dataaccess;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.devonfw.sample.archunit.general.logic.AbstractUc;

/**
 * {@link AbstractUc Use-case} to save {@link com.devonfw.sample.archunit.task.common.TaskList}s.
 */
@ApplicationScoped
@Named
@Transactional
// Violation: Use-case has to be in logic layer.
public class UcViolation extends AbstractUc {
}
