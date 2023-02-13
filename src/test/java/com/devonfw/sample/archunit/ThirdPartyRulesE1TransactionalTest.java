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
public class ThirdPartyRulesE1TransactionalTest {

    private static boolean apiScopedClassIsUsingTransactional(JavaClass item, String targetPackageFullName) {
        if(item.getFullName().contains("api") && targetPackageFullName.equals("javax.transaction.Transactional")) {
                return true;
        }
        return false;
    }

    private static boolean isUsingSpringframeworkTransactionalAnnotation(String targetPackageFullName) {
        if(targetPackageFullName.equals("org.springframework.transaction.annotation.Transactional")) {
                return true;
        }
        return false;
    }

    static ArchCondition<JavaClass> miuse_springframework_transactional_annotation = new ArchCondition<JavaClass> ("misuse @Transactional") {
        @Override
        public void check(JavaClass item, ConditionEvents events) {
                for(Dependency access: item.getDirectDependenciesFromSelf()) {
                        String targetPackageFullName = access.getTargetClass().getFullName();
                        String targetClassDescription = access.getDescription();
                        if(isUsingSpringframeworkTransactionalAnnotation(targetPackageFullName) == true) {
                                String message = String.format("Use JEE standard (javax.transaction.Transactional from javax.transaction:javax.transaction-api:1.2+). The use (%s) is discouraged.  Violated in (%s)", targetPackageFullName, targetClassDescription);
                                events.add(new SimpleConditionEvent(item, true, message));
                        }
                        if(apiScopedClassIsUsingTransactional(item, targetPackageFullName) == true) {
                                String message = String.format("The use of @Transactional in API is discouraged. Instead use it to annotate implementations. Violated in (%s)", targetClassDescription);
                                events.add(new SimpleConditionEvent(item, true, message));
                        }
                        }
                }
        };
        
    @ArchTest
    static final ArchRule verifying_proper_transactional_use_from_jee =
        noClasses()
        .should(miuse_springframework_transactional_annotation)
        .allowEmptyShould(true);
        

}
