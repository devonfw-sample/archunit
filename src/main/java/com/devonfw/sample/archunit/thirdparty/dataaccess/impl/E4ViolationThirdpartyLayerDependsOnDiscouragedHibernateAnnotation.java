package com.devonfw.sample.archunit.thirdparty.dataaccess.impl;

import org.hibernate.annotations.Entity; // Noncompliant

public class E4ViolationThirdpartyLayerDependsOnDiscouragedHibernateAnnotation {
    // Noncompliant
    @Entity
    class NoncompliantInnerClass{
        
    }
}
