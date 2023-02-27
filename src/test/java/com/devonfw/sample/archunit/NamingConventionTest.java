package com.devonfw.sample.archunit;

import com.devonfw.sample.archunit.general.common.AbstractEto;
import com.devonfw.sample.archunit.general.logic.AbstractUc;

import javax.sound.midi.Soundbank;
import javax.ws.rs.Path;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devonfw.sample.archunit.general.dataaccess.ApplicationPersistenceEntity;
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
     * DevonNamingConventionCheck verifying that classes extending ApplicationPersistenceEntity are following the
     * naming convention by ending with 'Entity'.
     */
    @ArchTest
    private static final ArchRule DevonNamingConventionEntityCheck =
            classes()
                    .that().areAssignableTo(ApplicationPersistenceEntity.class)
                    .should().haveSimpleNameEndingWith("Entity")
                    .because("Classes extending ApplicationPersistenceEntity must follow the naming convention by" +
                            "ending with 'Entity'.");

    /**
     * DevonNamingConventionCheck verifying that classes extending AbstractEto are following the naming convention by
     * ending with Eto.
     */
    @ArchTest
    private static final ArchRule DevonNamingConventionEtoCheck =
            classes()
                    .that().areAssignableTo(AbstractEto.class)
                    .should().haveSimpleNameEndingWith("Eto")
                    .because("Classes extending AbstractEto must follow the naming convention by ending with 'Eto'.");

    @ArchTest
    private static final ArchRule DevonApplicationPersistenceEntityLocationCheck = 
            classes()
                   .that().areAssignableTo(ApplicationPersistenceEntity.class).should().resideInAnyPackage("..dataaccess..")
                   .because("Classes extending ApplicationPersistenceEntity must be located in the package dataaccess");

    @ArchTest
    private static final ArchRule DevonAbstractUcLocationCheck = 
            classes()
                   .that().areAssignableTo(AbstractUc.class).should().resideInAnyPackage("..logic..")
                   .because("Classes extending AbstractUc must be located in the package logic");
    
    @ArchTest
    private static final ArchRule DevonMapperLocationandNamingCheck = 
            classes()   
                   .that().areAnnotatedWith(Mapper.class).should().resideInAnyPackage("..logic..").andShould().haveSimpleNameEndingWith("Mapper")
                   .because("Classes extending Mapper must be located in the package logic and end with Mapper");

    @ArchTest
    private static final ArchRule DevonPathLocationandNamingCheck = 
            classes()   
                   .that().areAnnotatedWith(Path.class).should().resideInAnyPackage("..service..").andShould().haveSimpleNameEndingWith("Service")
                   .because("Classes extending Path must be located in the package service and end with Service");
        
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
                        String supposedInterfaceName = javaClass.getPackageName() + "." + javaClass.getSimpleName().substring(0, javaClass.getSimpleName().length()-3);
                        boolean hasCorrectInterface = javaClass.getInterfaces().stream().anyMatch(i->i.getName().equals(supposedInterfaceName));
                        String message = "The Testresult of " + javaClass.getSimpleName() + " was " + hasCorrectInterface;
                        events.add(new SimpleConditionEvent(javaClass, hasCorrectInterface, message));
                    }
                        
                   })
                   .because("Classes extending AbstractEto must be located in the package common");                 
                    
    @ArchTest
    private static final ArchRule DevonJpaRepositoryCheck = 
                classes().that().areAssignableTo(JpaRepository.class)
                         .should().resideInAnyPackage("..dataaccess..")
                         .andShould(new ArchCondition<JavaClass>("check for the jpa naming structure to be valid", new Object(){}) {
        @Override 
                public void check(JavaClass javaClass, ConditionEvents events) {

                        Type[] genericInterfaces = javaClass.reflect().getGenericInterfaces();
                        for (Type genericInterface : genericInterfaces) {
                            if (genericInterface instanceof ParameterizedType) {
                                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                                System.out.println(typeArguments[0].getTypeName());
                                // for (Type typeArgument : typeArguments) {
                                //     System.out.println("Type argument: " + typeArgument.getTypeName());
                                // }
                            }
                        }
                        events.add(new SimpleConditionEvent(javaClass, true, "message"));

                }});
}
