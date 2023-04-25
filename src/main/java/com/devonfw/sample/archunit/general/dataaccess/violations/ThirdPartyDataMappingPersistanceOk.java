package com.devonfw.sample.archunit.general.dataaccess.violations;

import javax.persistence.Converter; // compliant

@Converter
// OK - just for demonstration purpose. A reasonable example can be found here:
// https://github.com/devonfw/devon4j/blob/master/documentation/guide-jpa.asciidoc#entities-and-datatypes
public class ThirdPartyDataMappingPersistanceOk {

}
