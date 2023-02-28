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
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * JUnit test that validates the naming convention rules of this application.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class NamingConventionTest {

    /**
     * DevonNamingConventionCheck N1 verifying that classes extending AbstractCto are following the
     * naming convention by ending with 'Cto'.
     */
    @ArchTest
    private static final ArchRule N1DevonNamingConventionCtoCheck =
            classes()
                    .that().areAssignableTo(AbstractCto.class)
                    .should().haveSimpleNameEndingWith("Cto")
                    .because("Classes extending AbstractCto must follow the naming convention by" +
                            " ending with 'Cto'.");

    /**
     * DevonNamingConventionCheck N3 verifying that classes extending ApplicationPersistenceEntity are following the
     * naming convention by ending with 'Entity'.
     */
    @ArchTest
    private static final ArchRule N3DevonNamingConventionEntityCheck =
            classes()
                    .that().areAssignableTo(ApplicationPersistenceEntity.class)
                    .should().haveSimpleNameEndingWith("Entity")
                    .because("Classes extending ApplicationPersistenceEntity must follow the naming convention by" +
                            " ending with 'Entity'.");

    /**
     * DevonNamingConventionCheck N4 verifying that classes extending AbstractEto are following the naming convention by
     * ending with 'Eto'.
     */
    @ArchTest
    private static final ArchRule N4DevonNamingConventionEtoCheck =
            classes()
                    .that().areAssignableTo(AbstractEto.class)
                    .should().haveSimpleNameEndingWith("Eto")
                    .because("Classes extending AbstractEto must follow the naming convention by ending with 'Eto'.");
    /**
     * DevonApplicationPersistenceEntityCheck verifying that classes extending ApplicationPersistenceEntity have to have be in layer dataaccess
     */
    @ArchTest
    private static final ArchRule DevonApplicationPersistenceEntityCheck = 
            classes()
                   .that().areAssignableTo(ApplicationPersistenceEntity.class).should().resideInAnyPackage("..dataaccess..")
                   .because("Classes extending ApplicationPersistenceEntity must be located in the package dataaccess");

    /**
     * DevonAbstractUcCheck verifying that classes extending AbstractUc have to have be in layer logic
     */
    @ArchTest
    private static final ArchRule DevonAbstractUcCheck = 
            classes()
                   .that().areAssignableTo(AbstractUc.class).should().resideInAnyPackage("..logic..")
                   .because("Classes extending AbstractUc must be located in the package logic");
       
    /**
     * DevonMapperCheck verifying that classes extending Mapper have to have be in layer logic and end with the suffix Mapper
     */
    @ArchTest
    private static final ArchRule DevonMapperCheck = 
            classes()   
                   .that().areAnnotatedWith(Mapper.class).should().resideInAnyPackage("..logic..").andShould().haveSimpleNameEndingWith("Mapper")
                   .because("Classes extending Mapper must be located in the package logic and end with Mapper");

    /**
     * DevonPathCheck verifying that classes extending Path have to have be in layer service and end with the suffix Service
     */
    @ArchTest
    private static final ArchRule DevonPathCheck = 
            classes()   
                   .that().areAnnotatedWith(Path.class).should().resideInAnyPackage("..service..").andShould().haveSimpleNameEndingWith("Service")
                   .because("Classes extending Path must be located in the package service and end with Service");

    /**
     * DevonAbstractEtoCheck verifying that classes extending AbstractEto have to have be in layer common and implement an interface with the same SimpleName excluding Eto and is in the same Package
     */                  
    @ArchTest
    private static final ArchRule DevonAbstractEtoCheck = 
            classes()
                   .that().areAssignableTo(AbstractEto.class)
                   .and().doNotHaveSimpleName("AbstractEto")
                   .should().resideInAnyPackage("..common..")
                   .andShould(new ArchCondition<JavaClass>("implemted a interface with same simple name", new Object(){}) 
                   {
                        @Override  
                        public void check(JavaClass javaClass, ConditionEvents events) {
                                String supposedInterfaceName = javaClass.getPackageName() + "." + javaClass.getSimpleName().replace("Eto", "");
                                boolean hasCorrectInterface = javaClass.getInterfaces().stream().anyMatch(i->i.getName().equals(supposedInterfaceName));
                                String message = "The Testresult of " + javaClass.getSimpleName() + " was " + hasCorrectInterface;
                                events.add(new SimpleConditionEvent(javaClass, hasCorrectInterface, message));
                        }
                        
                   });               
             
       
    /**
     * DevonJpaRepositoryCheck verifying that classes implementing JpaRepository have to have be in layer dataaccess 
     * and have to be named «EntityName»Repository where «EntityName» is the name of the entity filled in the generic argument of JpaRepository excluding the Entity suffix. 
     * Further they should be in the same package as the entity.
     */               
    @ArchTest
    private static final ArchRule DevonJpaRepositoryCheck = 
            classes().that().areAssignableTo(JpaRepository.class)
                  .should().resideInAnyPackage("..dataaccess..")
                  .andShould(new ArchCondition<JavaClass>("check for the jpa naming structure to be valid", new Object(){}) 
                  {
                        @Override 
                        public void check(JavaClass javaClass, ConditionEvents events) {
                                Boolean hasCorrectName = false;
                                Type[] genericInterfaces = javaClass.reflect().getGenericInterfaces();
                                for (Type genericInterface : genericInterfaces) {
                                        if (genericInterface instanceof ParameterizedType) {
                                                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                                                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                                                String enitiyName = typeArguments[0].getTypeName().replace("Entity", "");
                                                hasCorrectName = javaClass.getFullName().equals(enitiyName +"Repository");
                                        }
                                }
                                events.add(new SimpleConditionEvent(javaClass, hasCorrectName , "message"));
                        }
                  });

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
