package com.devonfw.sample.archunit.violation.common;

import javax.persistence.MappedSuperclass;

import com.devonfw.sample.archunit.general.common.ApplicationEntity;

/**
 * Abstract base class for all persistent entities of this app.
 */
// Violation: ApplicationPersistenceEntity has to be be in layer dataaccess
@MappedSuperclass
public abstract class ApplicationPersistenceEntity implements ApplicationEntity {

}
