package com.devonfw.sample.archunit.thirdparty.dataaccess.api;

import org.hibernate.Session; // Noncompliant

public class E4ViolationThirdpartyLayerDependsOnHibernateInsideApiLayer {
    Session sessionFactory;
}
