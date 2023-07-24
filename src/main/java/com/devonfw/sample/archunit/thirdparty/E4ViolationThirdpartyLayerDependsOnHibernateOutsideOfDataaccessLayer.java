package com.devonfw.sample.archunit.thirdparty;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session; //Noncompliant
import org.hibernate.annotations.OrderBy; //Noncompliant

public class E4ViolationThirdpartyLayerDependsOnHibernateOutsideOfDataaccessLayer {
    @OrderBy(clause = "NAME DESC") // Noncompliant
    Set<String> taskList = new HashSet<>();
    Session session; //Noncompliant
}
