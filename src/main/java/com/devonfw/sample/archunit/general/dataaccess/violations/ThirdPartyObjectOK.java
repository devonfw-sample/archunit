package com.devonfw.sample.archunit.general.dataaccess.violations;

// compliant: standard java object method equals()
public class ThirdPartyObjectOK {

    boolean result1 = java.util.Objects.equals(1, 1);

}