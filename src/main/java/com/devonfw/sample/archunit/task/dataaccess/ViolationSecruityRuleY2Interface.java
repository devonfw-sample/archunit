package com.devonfw.sample.archunit.task.dataaccess;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityGraph;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

import com.devonfw.sample.archunit.general.dataaccess.ApplicationPersistenceEntity;
import com.devonfw.sample.archunit.task.common.TaskList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * {@link TaskList} implementation as {@link ApplicationPersistenceEntity}.
 */
@Entity
@Table(name = "TASK_LIST")
public class ViolationSecruityRuleY2Interface implements EntityManager{

  @Override
  public void persist(Object entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'persist'");
  }

  @Override
  public <T> T merge(T entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'merge'");
  }

  @Override
  public void remove(Object entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'remove'");
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'find'");
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'find'");
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'find'");
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'find'");
  }

  @Override
  public <T> T getReference(Class<T> entityClass, Object primaryKey) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getReference'");
  }

  @Override
  public void flush() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'flush'");
  }

  @Override
  public void setFlushMode(FlushModeType flushMode) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setFlushMode'");
  }

  @Override
  public FlushModeType getFlushMode() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getFlushMode'");
  }

  @Override
  public void lock(Object entity, LockModeType lockMode) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'lock'");
  }

  @Override
  public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'lock'");
  }

  @Override
  public void refresh(Object entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'refresh'");
  }

  @Override
  public void refresh(Object entity, Map<String, Object> properties) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'refresh'");
  }

  @Override
  public void refresh(Object entity, LockModeType lockMode) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'refresh'");
  }

  @Override
  public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'refresh'");
  }

  @Override
  public void clear() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'clear'");
  }

  @Override
  public void detach(Object entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'detach'");
  }

  @Override
  public boolean contains(Object entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'contains'");
  }

  @Override
  public LockModeType getLockMode(Object entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getLockMode'");
  }

  @Override
  public void setProperty(String propertyName, Object value) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setProperty'");
  }

  @Override
  public Map<String, Object> getProperties() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getProperties'");
  }

  @Override
  public Query createQuery(String qlString) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createQuery'");
  }

  @Override
  public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createQuery'");
  }

  @Override
  public Query createQuery(CriteriaUpdate updateQuery) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createQuery'");
  }

  @Override
  public Query createQuery(CriteriaDelete deleteQuery) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createQuery'");
  }

  @Override
  public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createQuery'");
  }

  @Override
  public Query createNamedQuery(String name) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createNamedQuery'");
  }

  @Override
  public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createNamedQuery'");
  }

  @Override
  public Query createNativeQuery(String sqlString) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createNativeQuery'");
  }

  @Override
  public Query createNativeQuery(String sqlString, Class resultClass) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createNativeQuery'");
  }

  @Override
  public Query createNativeQuery(String sqlString, String resultSetMapping) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createNativeQuery'");
  }

  @Override
  public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createNamedStoredProcedureQuery'");
  }

  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createStoredProcedureQuery'");
  }

  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createStoredProcedureQuery'");
  }

  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createStoredProcedureQuery'");
  }

  @Override
  public void joinTransaction() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'joinTransaction'");
  }

  @Override
  public boolean isJoinedToTransaction() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isJoinedToTransaction'");
  }

  @Override
  public <T> T unwrap(Class<T> cls) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'unwrap'");
  }

  @Override
  public Object getDelegate() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getDelegate'");
  }

  @Override
  public void close() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'close'");
  }

  @Override
  public boolean isOpen() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isOpen'");
  }

  @Override
  public EntityTransaction getTransaction() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTransaction'");
  }

  @Override
  public EntityManagerFactory getEntityManagerFactory() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getEntityManagerFactory'");
  }

  @Override
  public CriteriaBuilder getCriteriaBuilder() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getCriteriaBuilder'");
  }

  @Override
  public Metamodel getMetamodel() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getMetamodel'");
  }

  @Override
  public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createEntityGraph'");
  }

  @Override
  public EntityGraph<?> createEntityGraph(String graphName) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createEntityGraph'");
  }

  @Override
  public EntityGraph<?> getEntityGraph(String graphName) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getEntityGraph'");
  }

  @Override
  public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getEntityGraphs'");
  }

}
