package com.devonfw.sample.archunit.violation.common;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devonfw.sample.archunit.violation.dataaccess.ViolationEntity;

/**
 * Interface for the {@link JpaRepository} giving database access to {@link ViolationEntity}.
 */
 // Violation: JpaRepository has to be in layer dataaccess and should be in the same package as the entity.
public interface ViolationRepository extends JpaRepository<ViolationEntity, Long> {
}