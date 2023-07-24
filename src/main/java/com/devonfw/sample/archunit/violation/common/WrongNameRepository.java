package com.devonfw.sample.archunit.violation.common;


import org.springframework.data.jpa.repository.JpaRepository;


import com.devonfw.sample.archunit.violation.dataaccess.ViolationEntity;

/**
 * Interface for the {@link JpaRepository} giving database access to {@link ViolationEntity}.
 */
 // Violation: JpaRepository interface shall be named «EntityName»Repository (ViolationRepository)
public interface WrongNameRepository extends JpaRepository<ViolationEntity, Long> {
}
