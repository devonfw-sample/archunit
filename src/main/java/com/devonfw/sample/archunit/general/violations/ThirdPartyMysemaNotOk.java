package com.devonfw.sample.archunit.general.violations;

import com.mysema.query.jpa.impl.JPAQuery; // Noncompliant

public class ThirdPartyMysemaNotOk {

    JPAQuery jpaQuery = new JPAQuery();

}
