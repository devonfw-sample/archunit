package com.devonfw.sample.archunit.violation.dataaccess;

import com.devonfw.sample.archunit.violation.common.ViolationItem;
import com.devonfw.sample.archunit.general.common.AbstractEto;

/**
 * {@link ViolationItem} implementation as {@link AbstractEto}.
 */
 // Violation: ETO has to be in common layer.
public class ViolationEto extends AbstractEto implements ViolationItem {
}
