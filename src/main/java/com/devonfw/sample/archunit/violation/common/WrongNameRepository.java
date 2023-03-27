package com.devonfw.sample.archunit.violation.common;


import org.springframework.data.jpa.repository.JpaRepository;


import com.devonfw.sample.archunit.violation.dataaccess.ViolationEntity;

/**
 * Interface for the {@link JpaRepository} giving database access to {@link ViolationEntity}.
 */
public interface WrongNameRepository extends JpaRepository<ViolationEntity, Long> {
  /*
   *  has to be named «EntityName»Repository
   *  where «EntityName» is the name of the entity filled in 
   *  the generic argument of JpaRepository excluding the Entity suffix.
   */
}
