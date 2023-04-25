package com.devonfw.sample.archunit.task.dataaccess;

import javax.persistence.Entity;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;


import com.devonfw.sample.archunit.general.dataaccess.ApplicationPersistenceEntity;
import com.devonfw.sample.archunit.task.common.TaskList;

import io.vertx.core.impl.btc.BlockedThreadChecker.Task;

/**
 * {@link TaskList} implementation as {@link ApplicationPersistenceEntity}.
 */
@Entity
@Table(name = "TASK_LIST")
public abstract class ViolationSecruityRuleY2 implements EntityManager {

  TypedQuery<Task> violationCreateQueryStringClass = createQuery("test", Task.class);

  Query violationCreateQueryString = createQuery("test");

  Query violationCreateNativeQueryString = createNativeQuery("null");

  Query violationCreateNativeQueryStringString = createNativeQuery("null", "null");
  
  Query violationCreateNativeQueryStringClass =  createNativeQuery("null", Task.class);

}