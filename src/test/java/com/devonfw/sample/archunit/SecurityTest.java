package com.devonfw.sample.archunit;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import javax.persistence.EntityManager;

/**
 * JUnit test that validates the security rules of this application.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class SecurityTest {

        /*Parameters */
        private static final String STRING_CLASS = "[JavaClass{name='java.lang.String'}, JavaClass{name='java.lang.Class'}]";
        private static final String STRING = "[JavaClass{name='java.lang.String'}]";
        private static final String STRING_STRING = "[JavaClass{name='java.lang.String'}, JavaClass{name='java.lang.String'}]";

        /*Patterns */

        private static final String PARAMETER_CREATEQUERY =
        //..1...............2
        STRING + "|" +STRING_CLASS ;

        private static final String PARAMETER_CREATENATIVEQUERY =      
        // .1.............2.................3
        STRING +"|"+ STRING_CLASS +"|"+ STRING_STRING;

        static final Pattern PARAMETER_PATERN_CQ = Pattern.compile(PARAMETER_CREATEQUERY);
        static final Pattern PARAM_PATTERN_CNQ = Pattern.compile(PARAMETER_CREATENATIVEQUERY);
        
    /**
     * Checks 'Uc*Impl' classes for public methods.
     * Fails if a method is neither annotated with @PermitAll, @RolesAllowed nor @DenyAll.
     */
    @ArchTest
    private static final ArchRule shouldBeProperlyAnnotated = //
            methods()
                    .that().areDeclaredInClassesThat().haveSimpleNameStartingWith("Uc")
                    .and().arePublic()
                    .should().beAnnotatedWith(PermitAll.class)
                    .orShould().beAnnotatedWith(RolesAllowed.class)
                    .orShould().beAnnotatedWith(DenyAll.class)
                    .because("All Use-Case implementation methods must be annotated with a security " +
                            "constraint from javax.annotation.security");

    /**
     * Checks if these methods from the javax.persistence.Entitymanager are being used
     * Query createQuery(String qlString)
     * <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass)
     * Query createNativeQuery(String sqlString)
     * Query createNativeQuery(String sqlString, Class resultClass)
     * Query createNativeQuery(String sqlString, String resultSetMapping)
     */
    @ArchTest
    private static final ArchRule shouldnTUseCreateQuery = noClasses().should().callMethodWhere(new DescribedPredicate<JavaMethodCall>("test if used") {
            @Override
            public boolean test(JavaMethodCall javaMethod) {
                if(javaMethod.getOriginOwner().getSimpleName().equals("EnitityManager") || 
                   javaMethod.getOriginOwner().isAssignableFrom(EntityManager.class) ){
                        if(javaMethod.getName().equals("createQuery")){
                                System.out.println(javaMethod.getTarget().getParameterTypes());
                                Matcher matcherCQ = PARAMETER_PATERN_CQ.matcher(javaMethod.getTarget().getParameterTypes().toString());
                                return matcherCQ.find();
                        }else if(javaMethod.getName() == "createNativQuery"){
                                Matcher matcherCNQ = PARAM_PATTERN_CNQ.matcher(javaMethod.getTarget().getParameterTypes().toString());
                                return matcherCNQ.find();
                        }
                }                        
                return false;                        
            }
     });            
}