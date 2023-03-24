package com.devonfw.sample.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ThirdPartyRulesTest {
  private static final Set<String> DISCOURAGED_HIBERNATE_ANNOTATIONS = new HashSet<>(
      Arrays.asList("OrderBy", "Entity", "AccessType", "ForeignKey", "Cascade", "Index", "IndexColumn"));

  private static final String ORG_HIBERNATE_ENVERS = "org.hibernate.envers";

  private static final String ORG_HIBERNATE_VALIDATOR = "org.hibernate.validator";

  private static final String ORG_HIBERNATE_ANNOTATIONS = "org.hibernate.annotations";

  public static ArchRule check_object_dependency = noClasses().should().dependOnClassesThat()
      .haveFullyQualifiedName("com.google.common.base.Objects")
      .because("Use Java standards instead (java.util.Objects).");

  public static ArchRule check_converter_dependency = noClasses().should().dependOnClassesThat()
      .haveFullyQualifiedName("javax.persistence.Convert")
      .because("Use the javax.persistence.Converter annotation on a custom converter"
          + " which implements the javax.persistence.AttributeConverter instead of the 'javax.persistance.Convert' annotation");

  public static ArchRule check_mysema_dependency = noClasses().should().dependOnClassesThat()
      .resideInAPackage("com.mysema.query..")
      .because("Use official QueryDSL (com.querydsl.* e.g. from com.querydsl:querydsl-jpa).");

  private static boolean isApiScopedClassUsingTransactional(JavaClass source, String targetPackageFullName) {

    PackageStructure sourcePkg = PackageStructure.of(source);

    if (sourcePkg.isScopeApi() && targetPackageFullName.equals("javax.transaction.Transactional")) {
      return true;
    }
    return false;
  }

  public static ArchCondition<JavaClass> verifyingTransactionalAnnotationIsNotUsedInsideApi = new ArchCondition<JavaClass>(
      "use @Transactional in API") {
    @Override
    public void check(JavaClass source, ConditionEvents events) {

      for (Dependency dependency : source.getDirectDependenciesFromSelf()) {
        String targetFullName = dependency.getTargetClass().getFullName();
        String targetClassDescription = dependency.getDescription();
        /*
         * In case the project has a classic architecture using scopes, check that no API scoped class is using
         * 'javax.transaction.Transactional'
         */
        if (isApiScopedClassUsingTransactional(source, targetFullName) == true) {
          String message = String.format(
              "The use of @Transactional in API is discouraged. Instead use it to annotate implementations. Violated in (%s)",
              targetClassDescription);
          events.add(new SimpleConditionEvent(source, true, message));
        }
      }
    }
  };

  public static ArchRule verifyingSpringframeworkTransactionalIsNotUsed = noClasses().should().dependOnClassesThat()
      .haveFullyQualifiedName("org.springframework.transaction.annotation.Transactional")
      .because("Use JEE standard (javax.transaction.Transactional from javax.transaction:javax.transaction-api:1.2+).");

  public static ArchRule verifyingProperTransactionalUseFromJee = noClasses()
      .should(verifyingTransactionalAnnotationIsNotUsedInsideApi).allowEmptyShould(true);

  private static boolean isUsingJavaxPersistenceDataAccessOrEmbeddablesInCommon(JavaClass source,
      String targetPackageFullName) {

    if (targetPackageFullName.startsWith("javax.persistence")) {
      PackageStructure sourcePkg = PackageStructure.of(source);
      if (sourcePkg.isLayerDataAccess()) {
        return true;
      }
      if (sourcePkg.isLayerCommon() && source.getSimpleName().contains("Embeddable")) {
        return true;
      }
      return false;
    }
    return true;
  }

  public static final ArchCondition<JavaClass> misuse_jpa = new ArchCondition<JavaClass>(
      "use JPA outside of dataaccess layer or embeddables in common layer") {
    @Override
    public void check(JavaClass source, ConditionEvents events) {

      for (Dependency dependency : source.getDirectDependenciesFromSelf()) {
        String targetPackageFullName = dependency.getTargetClass().getFullName();
        String targetClassDescription = dependency.getDescription();
        if (isUsingJavaxPersistenceDataAccessOrEmbeddablesInCommon(source, targetPackageFullName) == false) {
          String message = String.format(
              "JPA (%s) shall only be used in dataaccess layer or for embeddables in common layer. Violated in (%s)",
              targetPackageFullName, targetClassDescription);
          events.add(new SimpleConditionEvent(source, true, message));
        }
      }
    }
  };

  public static final ArchRule verifyingProperJpaUse = noClasses().should(misuse_jpa).allowEmptyShould(true);

  private static boolean isUsingHibernateOutsideOfDataaccessLayer(JavaClass source) {

    PackageStructure sourcePkg = PackageStructure.of(source);
    if (!sourcePkg.isLayerDataAccess()) {
      return true;
    }
    return false;
  }

  private static boolean isUsingProprietaryHibernateAnnotation(JavaClass targetClass) {

    String targetPackageName = targetClass.getPackageName();
    String targetSimpleName = targetClass.getSimpleName();
    if (targetPackageName.equals(ORG_HIBERNATE_ANNOTATIONS)
        && DISCOURAGED_HIBERNATE_ANNOTATIONS.contains(targetSimpleName)) {
      return true;
    }
    return false;
  }

  private static boolean isImplementingHibernateEnversInternalsDirectly(JavaClass targetClass) {

    String targetPackageName = targetClass.getPackageName();
    if (targetPackageName.startsWith(ORG_HIBERNATE_ENVERS) && targetPackageName.contains("internal")) {
      return true;
    }
    return false;
  }

  private static boolean isUsingHibernateOutsideOfImplScope(JavaClass source, JavaClass targetClass) {

    String targetPackageName = targetClass.getPackageName();
    PackageStructure sourcePkg = PackageStructure.of(source);
    if (!sourcePkg.isScopeImpl() && targetPackageName.startsWith("org.hibernate")
        && !targetPackageName.startsWith(ORG_HIBERNATE_VALIDATOR)) {
      return true;
    }
    return false;
  }

  private static boolean isNotImplementingHibernateEnversInImplScope(JavaClass source, JavaClass targetClass) {

    String targetPackageName = targetClass.getPackageName();
    String targetPackageFullName = targetClass.getFullName();
    String targetSimpleName = targetClass.getSimpleName();
    PackageStructure sourcePkg = PackageStructure.of(source);
    if (!sourcePkg.isScopeImpl() && targetPackageName.startsWith(ORG_HIBERNATE_ENVERS)
        && (!targetPackageFullName.equals(ORG_HIBERNATE_ENVERS) || targetSimpleName.startsWith("Default")
            || targetSimpleName.contains("Listener") || targetSimpleName.contains("Reader"))) {
      return true;
    }
    return false;
  }

  public static final ArchCondition<JavaClass> misUseHibernate = new ArchCondition<JavaClass>("misuse hibernate") {
    @Override
    public void check(JavaClass source, ConditionEvents events) {

      for (Dependency dependency : source.getDirectDependenciesFromSelf()) {
        JavaClass targetClass = dependency.getTargetClass();
        String targetPackageName = targetClass.getPackageName();
        String targetPackageFullName = targetClass.getFullName();
        String targetClassDescription = dependency.getDescription();

        if (targetPackageName.startsWith("org.hibernate") && !targetPackageName.startsWith(ORG_HIBERNATE_VALIDATOR)) {
          if (isUsingHibernateOutsideOfDataaccessLayer(source) == true) {
            String message = String.format("Hibernate (%s) should only be used in dataaccess layer. Violated in (%s)",
                targetPackageFullName, targetClassDescription);
            events.add(new SimpleConditionEvent(source, true, message));
          }
          if (isUsingProprietaryHibernateAnnotation(targetClass) == true) {
            String message = String.format(
                "Standard JPA annotations should be used instead of this proprietary hibernate annotation (%s). Violated in (%s)",
                targetPackageFullName, targetClassDescription);
            events.add(new SimpleConditionEvent(source, true, message));
          }
          if (isImplementingHibernateEnversInternalsDirectly(targetClass) == true) {
            String message = String.format(
                "Hibernate envers internals (%s) should never be used directly. Violated in (%s)",
                targetPackageFullName, targetClassDescription);
            events.add(new SimpleConditionEvent(source, true, message));
          }
          /*
           * In case the project has a classic architecture that uses scopes, check that Hibernate.Envers are only
           * utilized inside the impl scope of the dataaccess layer. In addition, Hibernate internals also need to be
           * used inside the impl scope of the dataaccess layer.
           */
          if (isNotImplementingHibernateEnversInImplScope(source, targetClass) == true) {
            String message = String.format(
                "Hibernate envers implementation (%s) should only be used in impl scope of dataaccess layer. Violated in (%s)",
                targetPackageFullName, targetClassDescription);
            events.add(new SimpleConditionEvent(source, true, message));
          }
          if (isUsingHibernateOutsideOfImplScope(source, targetClass) == true) {
            String message = String.format(
                "Hibernate internals (%s) should only be used in impl scope of dataaccess layer. Violated in (%s)",
                targetPackageFullName, targetClassDescription);
            events.add(new SimpleConditionEvent(source, true, message));
          }
        }
      }
    }
  };

  public static final ArchRule jpaIsUsedAsEncouraged = noClasses().should(misUseHibernate).allowEmptyShould(true);
}