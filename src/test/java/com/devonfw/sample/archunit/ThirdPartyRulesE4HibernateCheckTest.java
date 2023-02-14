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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link DevonArchitecture3rdPartyCheck} verifying that the {@code JPA} is properly used.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ThirdPartyRulesE4HibernateCheckTest {

    private static final Set<String> DISCOURAGED_HIBERNATE_ANNOTATIONS = new HashSet<>(
        Arrays.asList("OrderBy", "Entity", "AccessType", "ForeignKey", "Cascade", "Index", "IndexColumn"));
  
    private static final String ORG_HIBERNATE_ENVERS = "org.hibernate.envers";
  
    private static final String ORG_HIBERNATE_VALIDATOR = "org.hibernate.validator";
  
    private static final String ORG_HIBERNATE_ANNOTATIONS = "org.hibernate.annotations";

    private static boolean isUsingHibernateOutsideOfDataaccessLayer(JavaClass item, ConditionEvents events, String targetPackageName, String targetPackageFullName, String targetClassDescription) {
        if(!item.getPackageName().contains("dataaccess") && targetPackageName.startsWith("org.hibernate") && !targetPackageName.startsWith(ORG_HIBERNATE_VALIDATOR)) {
            return true;
        }
        return false;
    }

    private static boolean isUsingProprietaryHibernateAnnotation(JavaClass item, ConditionEvents events, String targetPackageName, String targetPackageFullName, String targetClassDescription, String targetSimpleName) {
        if(targetPackageName.equals(ORG_HIBERNATE_ANNOTATIONS) && DISCOURAGED_HIBERNATE_ANNOTATIONS.contains(targetSimpleName)) {
            return true;
        }
        return false;
    }

    private static boolean isNotImplementingHibernateEnversInImplScope(JavaClass item, ConditionEvents events, String targetPackageName, String targetPackageFullName, String targetClassDescription, String targetSimpleName) {
        if(targetPackageName.startsWith(ORG_HIBERNATE_ENVERS) && !item.getPackageName().contains("impl")
        && (!targetPackageFullName.equals(ORG_HIBERNATE_ENVERS) || targetSimpleName.startsWith("Default")
        || targetSimpleName.contains("Listener") || targetSimpleName.contains("Reader"))) 
            {
                return true;
            }
            return false;
    }

    private static boolean isImplementingHibernateEnversInternalsDirectly(JavaClass item, ConditionEvents events, String targetPackageName, String targetPackageFullName, String targetClassDescription) {
        if(targetPackageName.startsWith(ORG_HIBERNATE_ENVERS) && targetPackageName.contains("internal")) {
            return true;
        }
        return false;
    }

    private static boolean isUsingHibernateOutsideOfImplScope(JavaClass item, ConditionEvents events, String targetPackageName, String targetPackageFullName, String targetClassDescription) {
        if(!item.getPackageName().contains("impl") && targetPackageName.startsWith("org.hibernate") && !targetPackageName.startsWith(ORG_HIBERNATE_VALIDATOR)) {
            return true;
        }
        return false;
    }

    static ArchCondition<JavaClass> misUseHibernate = new ArchCondition<JavaClass> ("misuse hibernate (Rule-E4).") {
        @Override
        public void check(JavaClass item, ConditionEvents events) {
            for(Dependency access: item.getDirectDependenciesFromSelf()) {
                String targetPackageName = access.getTargetClass().getPackageName();
                String targetPackageFullName = access.getTargetClass().getFullName();
                String targetClassDescription = access.getDescription();
                String targetSimpleName = access.getTargetClass().getSimpleName();
                if(isUsingHibernateOutsideOfDataaccessLayer(item, events, targetPackageName, targetPackageFullName, targetClassDescription) == true)
                {
                    String message = String.format("Hibernate (%s) should only be used in dataaccess layer. Violated in (%s)", targetPackageFullName, targetClassDescription);
                    events.add(new SimpleConditionEvent(item, true, message));
                }
                if(isUsingProprietaryHibernateAnnotation(item, events, targetPackageName, targetPackageFullName, targetClassDescription, targetSimpleName) == true) {
                    String message = String.format("Standard JPA annotations should be used instead of this proprietary hibernate annotation (%s). Violated in (%s)", targetPackageFullName, targetClassDescription);
                    events.add(new SimpleConditionEvent(item, true, message));
                }
                if(isNotImplementingHibernateEnversInImplScope(item, events, targetPackageName, targetPackageFullName, targetClassDescription, targetSimpleName) == true) {
                    String message = String.format("Hibernate envers implementation (%s) should only be used in impl scope of dataaccess layer. Violated in (%s)", targetPackageFullName, targetClassDescription);
                    events.add(new SimpleConditionEvent(item, true, message));
                }
                if(isImplementingHibernateEnversInternalsDirectly(item, events, targetPackageName, targetPackageFullName, targetClassDescription) == true) {
                    String message = String.format("Hibernate envers internals (%s) should never be used directly. Violated in (%s)", targetPackageFullName, targetClassDescription);
                    events.add(new SimpleConditionEvent(item, true, message));
                }
                if(isUsingHibernateOutsideOfImplScope(item, events, targetPackageName, targetPackageFullName, targetClassDescription) == true) {
                    String message = String.format("Hibernate internals (%s) should only be used in impl scope of dataaccess layer. Violated in (%s)", targetPackageFullName, targetClassDescription);
                    events.add(new SimpleConditionEvent(item, true, message));
                }
            }
        }
    };
    
    @ArchTest
    static final ArchRule jpa_is_used_as_encouraged = 
        noClasses()
        .should(misUseHibernate)
        .allowEmptyShould(true);
}
