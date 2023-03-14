package com.devonfw.sample.archunit;

import com.devonfw.sample.archunit.general.common.AbstractCto;
import com.devonfw.sample.archunit.general.common.AbstractEto;
import com.devonfw.sample.archunit.general.logic.AbstractUc;

import javax.ws.rs.Path;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devonfw.sample.archunit.general.dataaccess.ApplicationPersistenceEntity;
import com.devonfw.sample.archunit.general.logic.AbstractUc;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * JUnit test that validates the naming convention rules of this application.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunitviolations", importOptions = ImportOption.DoNotIncludeTests.class)
public class NamingConventionTest {

  /**
   * DevonNamingConventionCheck N1 verifying that classes extending AbstractCto are following the naming convention by
   * ending with 'Cto'.
   */
  @ArchTest
  private static final ArchRule N1DevonNamingConventionCtoCheck = classes().that().areAssignableTo(AbstractCto.class)
      .should().haveSimpleNameEndingWith("Cto")
      .because("Classes extending AbstractCto must follow the naming convention by" + " ending with 'Cto'.");

  /**
   * DevonNamingConventionCheck N3 verifying that classes extending ApplicationPersistenceEntity are following the
   * naming convention by ending with 'Entity' and have to be in layer dataaccess.
   */
  @ArchTest
  private static final ArchRule N3DevonNamingConventionEntityCheck = classes().that()
      .areAssignableTo(ApplicationPersistenceEntity.class).should().haveSimpleNameEndingWith("Entity").andShould()
      .resideInAnyPackage("..dataaccess..")
      .because("Classes extending ApplicationPersistenceEntity must follow the naming convention by"
          + "ending with 'Entity' and must be located in the package dataaccess.");

  /**
   * DevonNamingConventionCheck N4 verifying that classes extending AbstractEto are following the naming convention by
   * ending with 'Eto'n reside in package "common" and implement an interface with the same simple name.
   */
  @ArchTest
  private static final ArchRule N4DevonNamingConventionEtoCheck = classes().that().areAssignableTo(AbstractEto.class)
      .and().doNotHaveSimpleName("AbstractEto").should().haveSimpleNameEndingWith("Eto").andShould()
      .resideInAnyPackage("..common..")
      .andShould(new ArchCondition<JavaClass>("implemted an interface with same simple name", new Object() {
      }) {
        @Override
        public void check(JavaClass javaClass, ConditionEvents events) {

          String supposedInterfaceName = javaClass.getPackageName() + "."
              + javaClass.getSimpleName().replace("Eto", "");
          boolean hasCorrectInterface = javaClass.getInterfaces().stream()
              .anyMatch(i -> i.getName().equals(supposedInterfaceName));
          String message = "The Testresult of " + javaClass.getSimpleName() + " was " + hasCorrectInterface;
          events.add(new SimpleConditionEvent(javaClass, hasCorrectInterface, message));
        }
      }).because(
          "Classes extending AbstractEto must follow the naming convention by ending with 'Eto', reside in package common and implent an interface with the same simple name.");

  /**
   * DevonAbstractUcCheck verifying that classes extending AbstractUc have to have be in layer logic
   */
  @ArchTest
  private static final ArchRule DevonAbstractUcCheck = classes().that().areAssignableTo(AbstractUc.class).should()
      .resideInAnyPackage("..logic..").because("Classes extending AbstractUc must be located in the package logic");

  /**
   * DevonMapperCheck verifying that classes extending Mapper have to have be in layer logic and end with the suffix
   * Mapper
   */
  @ArchTest
  private static final ArchRule DevonMapperCheck = classes().that().areAnnotatedWith(Mapper.class).should()
      .resideInAnyPackage("..logic..").andShould().haveSimpleNameEndingWith("Mapper")
      .because("Types annotated with @Mapper must be located in the package logic and end with Mapper");

  /**
   * DevonPathCheck verifying that classes extending Path have to have be in layer service and end with the suffix
   * Service
   */
  @ArchTest
  private static final ArchRule DevonPathCheck = classes().that().areAnnotatedWith(Path.class).should()
      .resideInAnyPackage("..service..").andShould().haveSimpleNameEndingWith("Service")
      .because("Types annotated with @Path must be located in the package service and end with Service");

  /**
   * DevonJpaRepositoryCheck verifying that classes implementing JpaRepository have to have be in layer dataaccess and
   * have to be named «EntityName»Repository where «EntityName» is the name of the entity filled in the generic argument
   * of JpaRepository excluding the Entity suffix. Further they should be in the same package as the entity.
   */
  @ArchTest
  private static final ArchRule DevonJpaRepositoryCheck = classes().that().areAssignableTo(JpaRepository.class).should()
      .resideInAnyPackage("..dataaccess..").andShould().haveSimpleNameEndingWith("Repository")
      .andShould(new ArchCondition<JavaClass>("check for the jpa naming structure to be valid", new Object() {
      }) {
        @Override
        public void check(JavaClass javaClass, ConditionEvents events) {

          Boolean hasCorrectName = false;
          Type[] genericInterfaces = javaClass.reflect().getGenericInterfaces();
          for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
              ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
              Type[] typeArguments = parameterizedType.getActualTypeArguments();
              String enitiyName = typeArguments[0].getTypeName().replace("Entity", "");
              hasCorrectName = javaClass.getFullName().equals(enitiyName + "Repository");
            }
          }
          events.add(new SimpleConditionEvent(javaClass, hasCorrectName, "message"));
        }
      });

  /**
   * DevonNamingConventionCheck N5 verifying that non-abstract classes inherited from AbstractUc are following the
   * devonfw naming convention by beginning with 'Uc' and ending with 'Impl'. They must also implement an interface with
   * the same name except for the suffix 'Impl'.
   */
  @ArchTest
  private static final ArchRule N5DevonNamingConventionUcCheck = classes().that().areAssignableTo(AbstractUc.class)
      .and().doNotHaveModifier(JavaModifier.ABSTRACT).should().haveSimpleNameStartingWith("Uc")
      .because("Classes extending AbstractUc must follow the naming convention by starting with 'Uc'.");

    /**
     * DevonNamingConventionCheck N5 verifying that non-abstract classes inherited from AbstractUc are following the
     * devonfw naming convention by beginning with 'Uc' and ending with 'Impl'. They must also implement an interface
     * with the same name except for the suffix 'Impl'.
     */
    @ArchTest
    private static final ArchRule N5DevonNamingConventionUcCheck =
            classes()
                    .that().areAssignableTo(AbstractUc.class)
                    .and().doNotHaveSimpleName("AbstractUc")
                    .should().haveSimpleNameStartingWith("Uc")
                    .because("Classes extending AbstractUc must follow the naming convention by starting with 'Uc'.");

}
