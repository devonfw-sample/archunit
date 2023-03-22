package com.devonfw.sample.archunit.general.dataaccess.violations;

import javax.persistence.Convert; // Noncompliant

@Convert
public class ThirdPartyDataMappingPersistanceNotOk {
    
}
