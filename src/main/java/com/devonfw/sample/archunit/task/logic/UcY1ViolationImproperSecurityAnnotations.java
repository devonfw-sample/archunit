package com.devonfw.sample.archunit.task.logic;

import javax.annotation.security.PermitAll;

public class UcY1ViolationImproperSecurityAnnotations {

    // Violation: Public method without proper annotations
    public void someMethodWithViolation() {
        // empty
    }

    /**
     * Public method with proper annotations
     */
    @PermitAll
    public void someMethodWithoutViolation() {
        // empty
    }

    /**
     * Private method without annotations
     */
    private void someOtherMethodWithoutViolation() {
        // empty
    }
}
