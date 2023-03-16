package com.devonfw.sample.archunit;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.core.domain.JavaType;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import java.util.List;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * JUnit test that validates the security rules of this application.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class SecurityTest {

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
     * Checks if these methods are being used
     * Query createQuery(String qlString)
     * <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass)
     * Query createNativeQuery(String sqlString)
     * Query createNativeQuery(String sqlString, Class resultClass)
     * Query createNativeQuery(String sqlString, String resultSetMapping)
     */
    @ArchTest
    private static final ArchRule shouldnTUseCreateQuery = noClasses().should().callMethodWhere(new DescribedPredicate<JavaMethodCall>("test whether CreateQuery or CreateNativQuery is used") {
            @Override
            public boolean test(JavaMethodCall javaMethod) {
                        if(javaMethod.getName().equals("createQuery")){
                                return createQueryParameterCheck(javaMethod.getTarget().getParameterTypes());
                        }else if(javaMethod.getName().equals("createNativeQuery")){
                                return true;
                        }                        
                return false;                        
            }
            public boolean createQueryParameterCheck(List<JavaType> parameters){
                return(!parameters.isEmpty() &&
                        parameters.get(0).getName().equals(String.class.getName()));}
        });            
}