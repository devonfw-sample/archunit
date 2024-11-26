package com.devonfw.sample.archunit.thirdparty;

import javax.persistence.EntityManager; // Noncompliant

public class E3ViolationThirdpartyLayerDependsOnJpaOutsideOfDataaccessLayer {
    // Noncompliant
    private EntityManager entityManager;
}
