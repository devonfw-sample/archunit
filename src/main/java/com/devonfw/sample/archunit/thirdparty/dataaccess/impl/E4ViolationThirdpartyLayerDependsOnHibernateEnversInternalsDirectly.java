package com.devonfw.sample.archunit.thirdparty.dataaccess.impl;

import org.hibernate.envers.query.internal.impl.EntitiesAtRevisionQuery; // Noncompliant

public class E4ViolationThirdpartyLayerDependsOnHibernateEnversInternalsDirectly {
    //Noncompliant
    EntitiesAtRevisionQuery nonCompliantDirectQuery = new EntitiesAtRevisionQuery(null, null, getClass(), null, false);
}
