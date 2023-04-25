package com.devonfw.sample.archunit.general.dataaccess.violations;

import com.querydsl.jpa.impl.JPAQuery; // compliant

public class ThirdPartyMysemaOk {

    JPAQuery<Integer> jpaQuery;

}
