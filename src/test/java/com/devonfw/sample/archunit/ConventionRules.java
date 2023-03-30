package com.devonfw.sample.archunit;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.Embeddable;
import javax.ws.rs.Path;

import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devonfw.sample.archunit.general.common.AbstractCto;
import com.devonfw.sample.archunit.general.common.AbstractEto;
import com.devonfw.sample.archunit.general.dataaccess.ApplicationPersistenceEntity;
import com.devonfw.sample.archunit.general.logic.AbstractUc;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.Priority;
import com.tngtech.archunit.lang.SimpleConditionEvent;

/**
 * Contains the {@link ArchRule}s to verify conventions of naming and packaging for specific types.
 */
class ConventionRules extends AbstractRules {

  // N1
  static ArchRule compositeTransferObjectsShouldHaveCtoSuffixAndCommonLayer() {

    return priority(Priority.MEDIUM).classes().that().areAssignableTo(AbstractCto.class).should().haveSimpleNameEndingWith("Cto")
        .andShould().resideInAnyPackage(".." + PackageStructure.LAYER_COMMON + "..")
        .because("CompositeTransferObjects must have 'Cto' suffix and " + PackageStructure.LAYER_COMMON + " layer.");
  }

  // N3
  static ArchRule entitiesShouldHaveEntitySuffixAndDataaccessLayer() {

    return priority(Priority.MEDIUM).classes().that().areAssignableTo(ApplicationPersistenceEntity.class).and()
        .doNotHaveFullyQualifiedName(ApplicationPersistenceEntity.class.getName()).should().haveSimpleNameEndingWith("Entity").andShould()
        .resideInAnyPackage(".." + PackageStructure.LAYER_DATA_ACCESS + "..")
        .andShould(implementEntityInterface("Entity", PackageStructure.LAYER_COMMON))
        .because("classes extending ApplicationPersistenceEntity must have 'Entity' suffix and " + PackageStructure.LAYER_DATA_ACCESS
            + " layer");
  }

  static ArchRule embeddablesShouldHaveEmbeddableSuffixAndCommonLayer() {

    return priority(Priority.HIGH).classes().that().areAnnotatedWith(Embeddable.class).should().haveSimpleNameEndingWith(EMBEDDABLE)
        .andShould().resideInAnyPackage(".." + PackageStructure.LAYER_COMMON + "..")
        .because("embeddables shall have 'Embeddable' suffix and common layer to prevent overhead of transfer objects");
  }

  // N4
  static ArchRule entityTransferObjectsShouldHaveEtoSuffixAndCommonLayerAndImplementEntityInterface() {

    return priority(Priority.MEDIUM).classes().that().areAssignableTo(AbstractEto.class).and().doNotHaveSimpleName("AbstractEto").should()
        .haveSimpleNameEndingWith("Eto").andShould().resideInAnyPackage(".." + PackageStructure.LAYER_COMMON + "..")
        .andShould(implementEntityInterface("Eto", null)).because(
            "classes extending AbstractEto must follow the naming convention by ending with 'Eto', reside in package common and implent an interface with the same simple name.");
  }

  static ArchRule useCasesShouldHaveLogicLayer() {

    return priority(Priority.MEDIUM).classes().that().areAssignableTo(AbstractUc.class).should().resideInAnyPackage("..logic..")
        .because("classes extending AbstractUc must be located in the logic layer");
  }

  // N5
  static ArchRule useCasesShouldHaveUcPrefix() {

    return priority(Priority.MEDIUM).classes().that().areAssignableTo(AbstractUc.class).and().doNotHaveModifier(JavaModifier.ABSTRACT)
        .should().haveSimpleNameStartingWith("Uc").because("classes extending AbstractUc must have 'Uc' prefix.");
  }

  static ArchRule mappersShouldHaveMapperSuffixAndLogicLayer() {

    return priority(Priority.MEDIUM).classes().that().areAnnotatedWith(Mapper.class).should().resideInAnyPackage("..logic..").andShould()
        .haveSimpleNameEndingWith("Mapper").because("Types annotated with @Mapper must be located in the logic layer and end with Mapper");
  }

  static ArchRule restServicesShouldHaveServiceSuffixAndServiceLayer() {

    return priority(Priority.MEDIUM).classes().that().areAnnotatedWith(Path.class).should().resideInAnyPackage("..service..").andShould()
        .haveSimpleNameEndingWith("Service")
        .because("Types annotated with @Path must be located in the service layer and end with Service");
  }

  static ArchRule jpaRepositoriesShouldHaveEntityNamePrefixRepositorySuffixAndDataaccessLayer() {

    return priority(Priority.MEDIUM).classes().that().areAssignableTo(JpaRepository.class).should().resideInAnyPackage("..dataaccess..")
        .andShould().haveSimpleNameEndingWith("Repository").andShould(new ArchCondition<>("have entity name as prefix") {
          @Override
          public void check(JavaClass javaClass, ConditionEvents events) {

            Type entityType = null;
            for (Type genericInterface : javaClass.reflect().getGenericInterfaces()) {
              if (genericInterface instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                if (parameterizedType.getRawType() == JpaRepository.class) {
                  Type[] typeArguments = parameterizedType.getActualTypeArguments();
                  entityType = typeArguments[0];
                  break;
                }
              }
            }
            if (entityType instanceof Class) {
              String enitiyName = ((Class<?>) entityType).getSimpleName().replace("Entity", "");
              String expectedName = enitiyName + "Repository";
              if (!javaClass.getSimpleName().equals(expectedName)) {
                events.add(new SimpleConditionEvent(javaClass, false, javaClass.getFullName() + " should have name " + expectedName));
              }
            } else {
              events.add(new SimpleConditionEvent(javaClass, false,
                  javaClass.getFullName() + " should directly implement JpaRepository without generic magic"));
            }
          }
        });
  }

}
