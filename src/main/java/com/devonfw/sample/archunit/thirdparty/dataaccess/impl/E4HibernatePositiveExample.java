package com.devonfw.sample.archunit.thirdparty.dataaccess.impl;
//The use is compliant because it lies inside dataaccess/impl
import org.hibernate.Session; //compliant 
import org.hibernate.envers.Audited;

public class E4HibernatePositiveExample {
    @Audited
    Long id;
    Session session;
}
