package com.devonfw.sample.archunit.violation.dataaccess;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.devonfw.sample.archunit.general.dataaccess.ApplicationPersistenceEntity;
import com.devonfw.sample.archunit.violation.common.ViolationItem;

/**
 * {@link TaskItem} implementation as {@link ApplicationPersistenceEntity}.
 */
@Entity
@Table(name = "VIOLATION")
// Violation: Entity has to be named «EntityName»Entity (ViolationItemEntity).
public class ViolationEntity extends ApplicationPersistenceEntity implements ViolationItem {
}
