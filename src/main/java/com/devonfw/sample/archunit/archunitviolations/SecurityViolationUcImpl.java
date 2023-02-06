package com.devonfw.sample.archunit.archunitviolations;

import javax.annotation.security.PermitAll;

public class SecurityViolationUcImpl {

    /**
     * Violation:
     * Public method without proper annotations
     */
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
