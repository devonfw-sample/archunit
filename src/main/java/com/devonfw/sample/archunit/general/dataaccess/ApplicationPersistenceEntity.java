package com.devonfw.sample.archunit.general.dataaccess;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.devonfw.sample.archunit.general.common.ApplicationEntity;

/**
 * Abstract base class for all persistent entities of this app.
 */
@MappedSuperclass
public abstract class ApplicationPersistenceEntity implements ApplicationEntity {

  @Id
  @GeneratedValue
  private Long id;

  @Version
  private Integer version;

  @Override
  public Long getId() {

    return this.id;
  }

  @Override
  public void setId(Long id) {

    this.id = id;
  }

  @Override
  public Integer getVersion() {

    return this.version;
  }

  @Override
  public void setVersion(Integer version) {

    this.version = version;
  }

}
