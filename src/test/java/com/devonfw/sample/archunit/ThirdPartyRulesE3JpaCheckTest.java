package com.devonfw.sample.archunit;

import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ThirdPartyRulesE3JpaCheckTest {

    private static boolean isUsingJavaxPersistenceDataAccessOrEmbeddablesInCommon(JavaClass item, String targetPackageFullName) {
        if(targetPackageFullName.startsWith("javax.persistence")) {
            if(item.getFullName().contains("dataaccess")) {
                return true;
            }
            if(item.getFullName().contains("common") && item.getSimpleName().contains("Embeddable")) {
                return true;
            }
            return false;
        }
        return true;
    }

    static ArchCondition<JavaClass> misuse_jpa = new ArchCondition<JavaClass> ("use JPA outside of dataaccess layer or embeddables in common layer") {
        @Override
        public void check(JavaClass item, ConditionEvents events) {
                for(Dependency access: item.getDirectDependenciesFromSelf()) {
                        String targetPackageFullName = access.getTargetClass().getFullName();
                        String targetClassDescription = access.getDescription();
                        if(isUsingJavaxPersistenceDataAccessOrEmbeddablesInCommon(item, targetPackageFullName) == false) {
                                String message = String.format("JPA (%s) shall only be used in dataaccess layer or for embeddables in common layer. Violated in (%s)", targetPackageFullName, targetClassDescription);
                                events.add(new SimpleConditionEvent(item, true, message));
                        }
                    }
                }
        };

    @ArchTest
    static final ArchRule verifying_proper_jpa_use =
        noClasses()
        .should(misuse_jpa)
        .allowEmptyShould(true);
}
