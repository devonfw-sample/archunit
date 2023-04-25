package com.devonfw.sample.archunit.general.dataaccess.violations;

import static com.google.common.base.Objects.equal; // Noncompliant

public class ThirdPartyObjectNotOk {

    boolean result = equal(1, 1);

}
