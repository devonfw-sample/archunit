package com.devonfw.sample.archunit.general.violations;

import com.querydsl.jpa.impl.JPAQuery; // compliant

public class ThirdPartyMysemaOk {

    JPAQuery<Integer> jpaQuery = new JPAQuery<>();

}
