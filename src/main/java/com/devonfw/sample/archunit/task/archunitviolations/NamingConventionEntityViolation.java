package com.devonfw.sample.archunit.task.archunitviolations;

import com.devonfw.sample.archunit.general.dataaccess.ApplicationPersistenceEntity;

import javax.persistence.MappedSuperclass;

/**
 * Abstract base class for all persistent entities of this app.
 */
@MappedSuperclass
public abstract class NamingConventionEntityViolation extends ApplicationPersistenceEntity {

}
