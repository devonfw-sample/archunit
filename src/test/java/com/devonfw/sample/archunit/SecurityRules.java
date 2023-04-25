package com.devonfw.sample.archunit;

import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;

import com.devonfw.sample.archunit.general.logic.AbstractUc;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.domain.JavaType;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.Priority;

/**
 * Contains the {@link ArchRule}s to verify the specific security aspects.
 */
class SecurityRules extends AbstractRules {

  /**
   * @return {@link ArchRule} verifying that non-abstract classes extending {@link AbstractUc} only have public methods
   *         annotated with either {@link PermitAll}, {@link RolesAllowed} or {@link DenyAll}.
   */
  static ArchRule useCaseMethodsShouldBeAnnotatedWithSecurityAnnotation() {

    return priority(Priority.HIGH).methods().that().arePublic().and().areDeclaredInClassesThat().areAssignableFrom(AbstractUc.class).and()
        .areDeclaredInClassesThat().doNotHaveModifier(JavaModifier.ABSTRACT).should().beAnnotatedWith(PermitAll.class).orShould()
        .beAnnotatedWith(RolesAllowed.class).orShould().beAnnotatedWith(DenyAll.class).allowEmptyShould(true)
        .because("use-case methods must have proper authorization via annotation from javax.annotation.security");
  }

  /**
   * @return {@link ArchRule} verifying that none of the following methods are used to prevent SQL injection.
   *         {@link EntityManager#createQuery(String)}, {@link EntityManager#createQuery(String, Class)},
   *         {@link EntityManager#createNativeQuery(String)}, {@link EntityManager#createNativeQuery(String, Class)},
   *         {@link EntityManager#createNativeQuery(String, String)}.
   */
  static ArchRule shouldNotCreateQueryFromString() {

    return priority(Priority.HIGH).noClasses().should().callMethodWhere(createQueryFromString()).because(
        "queries shall not be created from String to prevent SQL injection - use spring-data @Query annotation, QueryDSL or named queries instead");
  }

  private static DescribedPredicate<JavaMethodCall> createQueryFromString() {

    return new DescribedPredicate<JavaMethodCall>("createQuery or createNativQuery is used with query given as String") {
      @Override
      public boolean test(JavaMethodCall javaMethod) {

        if (javaMethod.getTargetOwner().isAssignableFrom(EntityManager.class)) {
          if (javaMethod.getName().equals("createQuery")) {
            return parameterCheck(javaMethod.getTarget().getParameterTypes());
          } else if (javaMethod.getName().equals("createNativeQuery")) {
            return parameterCheck(javaMethod.getTarget().getParameterTypes());
          }
        }
        return false;
      }

      private boolean parameterCheck(List<JavaType> parameters) {

        return (!parameters.isEmpty() && parameters.get(0).getName().equals(String.class.getName()));
      }
    };
  }

}