package com.devonfw.sample.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

/**
 * JUnit test that validates the security rules of this application.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class SecurityTest {

    /**
     * Checks 'Uc*Impl' classes for public methods.
     * Fails if a method is neither annotated with @PermitAll, @RolesAllowed nor @DenyAll.
     */
    @ArchTest
    private static final ArchRule shouldBeProperlyAnnotated = //
            methods()
                    .that().areDeclaredInClassesThat().haveSimpleNameStartingWith("Uc")
                    .and().arePublic()
                    .should().beAnnotatedWith(PermitAll.class)
                    .orShould().beAnnotatedWith(RolesAllowed.class)
                    .orShould().beAnnotatedWith(DenyAll.class)
                    .because("All Use-Case implementation methods must be annotated with a security " +
                            "constraint from javax.annotation.security");
}