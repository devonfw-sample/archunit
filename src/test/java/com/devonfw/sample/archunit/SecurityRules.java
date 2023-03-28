package com.devonfw.sample.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.core.domain.JavaType;
import com.tngtech.archunit.lang.ArchRule;

/**
 * JUnit test that validates the security rules of this application.
 */
public class SecurityRules {

  /**
   * Checks 'Uc*Impl' classes for public methods. Fails if a method is neither
   * annotated with @PermitAll, @RolesAllowed
   * nor @DenyAll.
   */
  static ArchRule shouldBeProperlyAnnotated() {

    return methods().that().areDeclaredInClassesThat().haveSimpleNameStartingWith("Uc").and().arePublic().should()
        .beAnnotatedWith(PermitAll.class).orShould().beAnnotatedWith(RolesAllowed.class).orShould()
        .beAnnotatedWith(DenyAll.class)
        .because("All Use-Case implementation methods must be annotated with a security "
            + "constraint from javax.annotation.security");
  }

  /**
   * Checks if these methods are being used Query createQuery(String qlString) <T>
   * TypedQuery<T> createQuery(String
   * qlString, Class<T> resultClass) Query createNativeQuery(String sqlString)
   * Query createNativeQuery(String sqlString,
   * Class resultClass) Query createNativeQuery(String sqlString, String
   * resultSetMapping)
   */
  static ArchRule shouldntUseCreateQuery() {

    return noClasses().should()
        .callMethodWhere(
            new DescribedPredicate<JavaMethodCall>("test whether CreateQuery or CreateNativQuery is used") {
              @Override
              public boolean test(JavaMethodCall javaMethod) {

                if (javaMethod.getName().equals("createQuery")) {
                  return parameterCheck(javaMethod.getTarget().getParameterTypes());
                } else if (javaMethod.getName().equals("createNativeQuery")) {
                  return parameterCheck(javaMethod.getTarget().getParameterTypes());
                }
                return false;
              }

              public boolean parameterCheck(List<JavaType> parameters) {

                return (!parameters.isEmpty() && parameters.get(0).getName().equals(String.class.getName()));
              }
            });
  }

}