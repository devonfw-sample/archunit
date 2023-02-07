package com.devonfw.sample.archunit;

import com.devonfw.sample.archunit.general.common.AbstractEto;
import com.devonfw.sample.archunit.general.dataaccess.ApplicationPersistenceEntity;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

/**
 * JUnit test that validates the naming convention rules of this application.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class NamingConventionTest {

    /**
     * DevonNamingConventionCheck verifying that classes extending ApplicationPersistenceEntity are following the
     * naming convention by ending with 'Entity'.
     */
    @ArchTest
    private static final ArchRule DevonNamingConventionEntityCheck =
            classes()
                    .that().areAssignableTo(ApplicationPersistenceEntity.class)
                    .should().haveSimpleNameEndingWith("Entity")
                    .because("Classes extending ApplicationPersistenceEntity must follow the naming convention by" +
                            "ending with 'Entity'.");

    /**
     * DevonNamingConventionCheck verifying that classes extending AbstractEto are following the naming convention by
     * ending with Eto.
     */
    @ArchTest
    private static final ArchRule DevonNamingConventionEtoCheck =
            classes()
                    .that().areAssignableTo(AbstractEto.class)
                    .should().haveSimpleNameEndingWith("Eto")
                    .because("Classes extending AbstractEto must follow the naming convention by ending with 'Eto'.");

}
