package com.devonfw.sample.archunit;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaEnumConstant;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.Priority;
import com.tngtech.archunit.lang.SimpleConditionEvent;

/**
 * Contains the {@link ArchRule}s to verify the proper usage of common 3rd party libraries.
 */
class ThirdPartyRules extends AbstractRules {

  private static final Set<String> OBSOLETE_HIBERNATE_ANNOTATIONS = Set.of("OrderBy", "Entity", "AccessType", "ForeignKey", "Cascade",
      "Index", "IndexColumn");

  private static final Set<Class<? extends Annotation>> RELATIONS = Set.of(ManyToOne.class, OneToMany.class, OneToOne.class,
      ManyToMany.class);

  private static final String ORG_HIBERNATE = "org.hibernate";

  private static final String ORG_HIBERNATE_ENVERS = ORG_HIBERNATE + ".envers";

  private static final String ORG_HIBERNATE_VALIDATOR = ORG_HIBERNATE + ".validator";

  private static final String ORG_HIBERNATE_ANNOTATIONS = ORG_HIBERNATE + ".annotations";

  static ArchRule shouldNotUseGuavaObjects() {

    return priority(Priority.LOW).noClasses().should().dependOnClassesThat().haveFullyQualifiedName("com.google.common.base.Objects")
        .because("Use Java standards (java.util.Objects) instead.");
  }

  static ArchRule shouldNotUseJpaConvert() {

    return priority(Priority.MEDIUM).noClasses().should().dependOnClassesThat().haveFullyQualifiedName("javax.persistence.Convert")
        .because("Use the javax.persistence.Converter annotation on a custom converter"
            + " implementing javax.persistence.AttributeConverter instead of 'javax.persistance.Convert' annotation");
  }

  static ArchRule shouldNotUseMysemaDependency() {

    return priority(Priority.HIGH).noClasses().should().dependOnClassesThat().resideInAPackage("com.mysema.query..").because(
        "com.mysema.query is obsolete since years and you shall use official QueryDSL (com.querydsl.* e.g. from com.querydsl:querydsl-jpa)");
  }

  static ArchRule shouldUseHibernateOnlyInDataaccess() {

    return priority(Priority.HIGH).noClasses().that().resideOutsideOfPackage(".." + PackageStructure.LAYER_DATA_ACCESS + "..").should()
        .dependOnClassesThat().resideInAPackage(ORG_HIBERNATE + "..")
        .because("Hibernate may only be used in " + PackageStructure.LAYER_DATA_ACCESS + " layer");
  }

  static ArchRule shouldUseJpaOnlyInDataaccessExceptEmbeddables() {

    return priority(Priority.HIGH).noClasses().that().resideOutsideOfPackage(".." + PackageStructure.LAYER_DATA_ACCESS + "..").and()
        .haveSimpleNameNotEndingWith(EMBEDDABLE).should().dependOnClassesThat().resideInAnyPackage("javax.persistence..")
        .because("JPA shall only be used in " + PackageStructure.LAYER_DATA_ACCESS + " layer");
  }

  static ArchRule shouldUseJpaInsteadOfHibernate() {

    return priority(Priority.MEDIUM).noClasses().should().dependOnClassesThat(isObsoleteHibernateAnnotation())
        .because("standard JPA annotations should be used instead of obsolete proprietary hibernate annotations");
  }

  static ArchRule shouldNotUseEnversInternal() {

    return priority(Priority.HIGH).noClasses().should().dependOnClassesThat(isEnversInternal())
        .because("envers internals are not API and shall never be used");
  }

  static ArchRule shouldOnlyUseLazyRelations() {

    return priority(Priority.HIGH).fields().that().areDeclaredInClassesThat().areAnnotatedWith(Entity.class).should(isRelationNotLazy());
  }

  static ArchRule jpaIsUsedAsEncouraged() {

    return priority(Priority.MEDIUM).noClasses().should(misuseHibernate()).allowEmptyShould(true)
        .because("Hibernate (Envers) shall not be used in scopes other than impl.");
  }

  static ArchRule shouldNotUseSpringTransactional() {

    return priority(Priority.MEDIUM).noClasses().should().dependOnClassesThat()
        .haveFullyQualifiedName("org.springframework.transaction.annotation.Transactional")
        .because("Use JEE standard (javax.transaction.Transactional).");
  }

  static ArchRule shouldNotUseTransactionalInApi() {

    return priority(Priority.MEDIUM).noClasses().that().areAnnotatedWith(Transactional.class).should().resideInAnyPackage("..api..")
        .because("@Transactional is implementation specific and should not be used in api scope");
  }

  private static ArchCondition<JavaField> isRelationNotLazy() {

    return new ArchCondition<>("have only relations with FetchType.LAZY") {
      @Override
      public void check(JavaField field, ConditionEvents events) {

        field.getAnnotations().stream().filter(relation -> RELATIONS.stream().anyMatch(relation.getRawType()::isEquivalentTo))
            .forEach(relation -> {
              JavaEnumConstant fetchType = (JavaEnumConstant) relation.get("fetch").get();
              if (!FetchType.LAZY.name().equals(fetchType.name())) {
                String message = field.getDescription() + " is not LAZY in " + field.getSourceCodeLocation();
                events.add(SimpleConditionEvent.violated(field, message));
              }
            });
      }
    };
  }

  private static DescribedPredicate<JavaClass> isObsoleteHibernateAnnotation() {

    return new DescribedPredicate<>("are obsolete hibernate annotations") {

      public boolean test(JavaClass target) {

        if (target.getPackageName().equals(ORG_HIBERNATE_ANNOTATIONS) && OBSOLETE_HIBERNATE_ANNOTATIONS.contains(target.getSimpleName())) {
          return true;
        }
        return false;
      }
    };
  }

  private static DescribedPredicate<JavaClass> isEnversInternal() {

    return new DescribedPredicate<>("are internal types of envers") {

      public boolean test(JavaClass target) {

        String pkg = target.getPackageName();
        if (pkg.startsWith(ORG_HIBERNATE_ENVERS) && pkg.contains("internal")) {
          return true;
        }
        return false;
      }
    };
  }

  private static ArchCondition<JavaClass> misuseHibernate() {

    return new ArchCondition<>("misuse hibernate") {
      @Override
      public void check(JavaClass source, ConditionEvents events) {

        for (Dependency dependency : source.getDirectDependenciesFromSelf()) {
          JavaClass targetClass = dependency.getTargetClass();
          String targetPackageName = targetClass.getPackageName();
          String targetPackageFullName = targetClass.getFullName();
          String targetClassDescription = dependency.getDescription();

          if (targetPackageName.startsWith(ORG_HIBERNATE) && !targetPackageName.startsWith(ORG_HIBERNATE_VALIDATOR)) {
            /*
             * In case the project has a classic architecture that uses scopes, check that Hibernate/Envers are only
             * utilized inside the impl scope of the dataaccess layer.
             */
            if (isUsingEnversInNonImplScope(source, targetClass) == true) {
              String message = String.format("Hibernate envers (%s) should only be used in impl scope of dataaccess layer violated in %s",
                  targetPackageFullName, targetClassDescription);
              events.add(new SimpleConditionEvent(source, false, message));
            }
            if (isUsingHibernateInNonImplScopeScope(source, targetClass) == true) {
              String message = String.format("Hibernate (%s) should only be used in impl scope of dataaccess layer violated in %s",
                  targetPackageFullName, targetClassDescription);
              events.add(new SimpleConditionEvent(source, false, message));
            }
          }
        }
      }
    };
  }

  private static boolean isUsingHibernateInNonImplScopeScope(JavaClass source, JavaClass targetClass) {

    String targetPackageName = targetClass.getPackageName();
    PackageStructure sourcePkg = PackageStructure.of(source);
    if (sourcePkg.hasScope() && !sourcePkg.isScopeImpl() && targetPackageName.startsWith(ORG_HIBERNATE)) {
      return true;
    }
    return false;
  }

  private static boolean isUsingEnversInNonImplScope(JavaClass source, JavaClass targetClass) {

    String targetPackageName = targetClass.getPackageName();
    String targetPackageFullName = targetClass.getFullName();
    String targetSimpleName = targetClass.getSimpleName();
    PackageStructure sourcePkg = PackageStructure.of(source);
    if (sourcePkg.hasScope() && !sourcePkg.isScopeImpl() && targetPackageName.startsWith(ORG_HIBERNATE_ENVERS)
        && (!targetPackageFullName.equals(ORG_HIBERNATE_ENVERS) || targetSimpleName.startsWith("Default")
            || targetSimpleName.contains("Listener") || targetSimpleName.contains("Reader"))) {
      return true;
    }
    return false;
  }

}