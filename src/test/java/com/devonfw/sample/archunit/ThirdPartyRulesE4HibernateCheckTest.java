package com.devonfw.sample.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

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

  private static final Pattern PATTERN_DATAACCESS = Pattern.compile(PackageRuleTest.DATAACCESS_PATTERN);

  private static boolean isUsingHibernateOutsideOfDataaccessLayer(JavaClass source, String targetPackageName) {

    Matcher dataaccessMatcher = PATTERN_DATAACCESS.matcher(source.getPackageName());
    if (!dataaccessMatcher.matches()) {
      return true;
    }
    return false;
  }

  private static boolean isUsingProprietaryHibernateAnnotation(String targetPackageName, String targetSimpleName) {

    if (targetPackageName.equals(ORG_HIBERNATE_ANNOTATIONS)
        && DISCOURAGED_HIBERNATE_ANNOTATIONS.contains(targetSimpleName)) {
      return true;
    }
    return false;
  }

  private static boolean isImplementingHibernateEnversInternalsDirectly(String targetPackageName) {

    if (targetPackageName.startsWith(ORG_HIBERNATE_ENVERS) && targetPackageName.contains("internal")) {
      return true;
    }
    return false;
  }

  static ArchCondition<JavaClass> misUseHibernate = new ArchCondition<JavaClass>("misuse hibernate (Rule-E4).") {
    @Override
    public void check(JavaClass source, ConditionEvents events) {

      for (Dependency dependency : source.getDirectDependenciesFromSelf()) {
        String targetPackageName = dependency.getTargetClass().getPackageName();
        String targetPackageFullName = dependency.getTargetClass().getFullName();
        String targetClassDescription = dependency.getDescription();
        String targetSimpleName = dependency.getTargetClass().getSimpleName();
        if (targetPackageName.startsWith("org.hibernate") && !targetPackageName.startsWith(ORG_HIBERNATE_VALIDATOR)) {
          if (isUsingHibernateOutsideOfDataaccessLayer(source, targetPackageName) == true) {
            String message = String.format("Hibernate (%s) should only be used in dataaccess layer. Violated in (%s)",
                targetPackageFullName, targetClassDescription);
            events.add(new SimpleConditionEvent(source, true, message));
          }
          if (isUsingProprietaryHibernateAnnotation(targetPackageName, targetSimpleName) == true) {
            String message = String.format(
                "Standard JPA annotations should be used instead of this proprietary hibernate annotation (%s). Violated in (%s)",
                targetPackageFullName, targetClassDescription);
            events.add(new SimpleConditionEvent(source, true, message));
          }
          if (isImplementingHibernateEnversInternalsDirectly(targetPackageName) == true) {
            String message = String.format(
                "Hibernate envers internals (%s) should never be used directly. Violated in (%s)",
                targetPackageFullName, targetClassDescription);
            events.add(new SimpleConditionEvent(source, true, message));
          }
        }
      }
    }
  };

  @ArchTest
  static final ArchRule jpa_is_used_as_encouraged = noClasses().should(misUseHibernate).allowEmptyShould(true);
}
