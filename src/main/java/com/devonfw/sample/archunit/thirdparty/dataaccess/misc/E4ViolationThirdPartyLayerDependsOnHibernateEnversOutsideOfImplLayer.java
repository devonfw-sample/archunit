package com.devonfw.sample.archunit.thirdparty.dataaccess.misc;

import org.hibernate.envers.Audited; // Noncompliant

public class E4ViolationThirdPartyLayerDependsOnHibernateEnversOutsideOfImplLayer {
    @Audited
    String name;
}
