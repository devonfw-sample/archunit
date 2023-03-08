package com.devonfw.sample.archunit.general.violations;

// compliant: standard java object method equals()
public class ThirdPartyObjectOK {

    Integer one = 1;
    boolean result = one.equals((Integer) 1);

}