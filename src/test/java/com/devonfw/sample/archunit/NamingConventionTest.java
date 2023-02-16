package com.devonfw.sample.archunit;

import com.devonfw.sample.archunit.general.common.AbstractCto;
import com.devonfw.sample.archunit.general.common.AbstractEto;
import com.devonfw.sample.archunit.general.dataaccess.ApplicationPersistenceEntity;
import com.devonfw.sample.archunit.general.logic.AbstractUc;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

/**
 * JUnit test that validates the naming convention rules of this application.
 */
@AnalyzeClasses(packages = {"com.devonfw.sample.archunit"}, importOptions = ImportOption.DoNotIncludeTests.class)
public class NamingConventionTest {

    /**
     * DevonNamingConventionCheck N1 verifying that classes extending AbstractCto are following the
     * naming convention by ending with 'Cto'.
     */
    @ArchTest
    private static final ArchRule N1DevonNamingConventionCtoCheck =
            classes()
                    .that().areAssignableTo(AbstractCto.class)
                    .should().haveSimpleNameEndingWith("Cto")
                    .because("Classes extending AbstractCto must follow the naming convention by" +
                            "ending with 'Cto'.");

    /**
     * DevonNamingConventionCheck N3 verifying that classes extending ApplicationPersistenceEntity are following the
     * naming convention by ending with 'Entity'.
     */
    @ArchTest
    private static final ArchRule N3DevonNamingConventionEntityCheck =
            classes()
                    .that().areAssignableTo(ApplicationPersistenceEntity.class)
                    .should().haveSimpleNameEndingWith("Entity")
                    .because("Classes extending ApplicationPersistenceEntity must follow the naming convention by" +
                            "ending with 'Entity'.");

    /**
     * DevonNamingConventionCheck N4 verifying that classes extending AbstractEto are following the naming convention by
     * ending with 'Eto'.
     */
    @ArchTest
    private static final ArchRule N4DevonNamingConventionEtoCheck =
            classes()
                    .that().areAssignableTo(AbstractEto.class)
                    .should().haveSimpleNameEndingWith("Eto")
                    .because("Classes extending AbstractEto must follow the naming convention by ending with 'Eto'.");

    /**
     * DevonNamingConventionCheck N5 verifying that non-abstract classes inherited from AbstractUc are following the
     * devonfw naming convention by beginning with 'Uc' and ending with 'Impl'. They must also implement an interface
     * with the same name except for the suffix 'Impl'.
     */
    @ArchTest
    private static final ArchRule N5DevonNamingConventionUcCheck =
            classes()
                    .that().areAssignableTo(AbstractUc.class)
                    .and().doNotHaveSimpleName("AbstractUc")
                    .should().haveSimpleNameStartingWith("Uc")
                    .because("Classes extending AbstractUc must follow the naming convention by starting with 'Uc'.");

}
