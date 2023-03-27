package com.devonfw.sample.archunit.violation.common;

import javax.persistence.MappedSuperclass;

import com.devonfw.sample.archunit.general.common.ApplicationEntity;

/**
 * Abstract base class for all persistent entities of this app.
 */
@MappedSuperclass
public abstract class ApplicationPersistenceEntity implements ApplicationEntity {
  /*
    ApplicationPersistenceEntity have to have be in layer dataaccess
  */

}
