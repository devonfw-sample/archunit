package com.devonfw.sample.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class LayerDependencyRulesTest {
    
    // 'access' catches only violations by real accesses, i.e. accessing a field, calling a method; compare 'dependOn' further down

    // L09: verifying that code from logic layer does not access service layer (of same app).
    @ArchTest
    static final ArchRule logic_should_not_access_services =
            noClasses().that().resideInAPackage("..logic..")
                    .should().accessClassesThat().resideInAPackage("..service..");

    // L10: verifying that dataaccess layer does not access service layer.
    // L12: verifying that dataaccess layer does not access logic layer.
    @ArchTest
    static final ArchRule persistence_should_not_access_services_or_logic =
            noClasses().that().resideInAPackage("..dataaccess..")
                    .should().accessClassesThat().resideInAnyPackage("..service..", "..logic..");

    @ArchTest
    static final ArchRule services_should_only_be_accessed_by_clients_or_other_services =
            classes().that().resideInAPackage("..service..")
                    .should().onlyBeAccessed().byAnyPackage("..client..", "..service..");

    @ArchTest
    static final ArchRule services_should_only_access_logic_common_or_other_services =
            classes().that().resideInAPackage("..service..")
                    .should().onlyAccessClassesThat().resideInAnyPackage("..service..", "..common..", "..logic..", "java..", "javax..");

    // 'dependOn' catches a wider variety of violations, e.g. having fields of type, having method parameters of type, extending type ...

    // L09: verifying that code from logic layer does not depend on service layer (of same app).
    @ArchTest
    static final ArchRule logic_should_not_depend_on_services =
            noClasses().that().resideInAPackage("..logic..")
                    .should().dependOnClassesThat().resideInAPackage("..service..");

    // L06: verifying that service layer does not depend on batch layer.
    // L08: verifying that service layer does not depend on dataaccess layer.
    @ArchTest
    static final ArchRule services_should_not_depend_on_batch_or_persistence =
            noClasses().that().resideInAPackage("..service..")
                    .should().dependOnClassesThat().resideInAnyPackage("..batch..", "..dataaccess..");

    // L10: verifying that dataaccess layer does not depend on service layer.
    // L12: verifying that dataaccess layer does not depend on logic layer.
    @ArchTest
    static final ArchRule persistence_should_not_depend_on_services_or_logic =
            noClasses().that().resideInAPackage("..dataaccess..")
                    .should().dependOnClassesThat().resideInAnyPackage("..service..", "..logic..");

    @ArchTest
    static final ArchRule services_should_only_be_depended_on_by_controllers_or_other_services =
            classes().that().resideInAPackage("..service..")
                    .should().onlyHaveDependentClassesThat().resideInAnyPackage("..controller..", "..service..");

    @ArchTest
    static final ArchRule services_should_only_depend_on_logic_common_or_other_services =
            classes().that().resideInAPackage("..service..")
                    .should().onlyDependOnClassesThat().resideInAnyPackage("..service..", "..logic..", "..common..", "java..", "javax..");

    // L01: Common Layer does not depend on any other layer
    @ArchTest
    static final ArchRule common_should_only_depend_on_common =
            classes().that().resideInAPackage("..common..")
                    .should().onlyDependOnClassesThat().resideInAnyPackage("..common..", "java..", "javax..");

    // L02: verifying that only client layer code may depend on client layer.
    @ArchTest
    static final ArchRule client_should_only_depend_on_client =
            classes().that().resideInAPackage("..client..")
                    .should().onlyDependOnClassesThat().resideInAnyPackage("..client..", "java..", "javax..").allowEmptyShould(true);

    // L03: verifying that client layer does not depend on logic layer.
    // L04: verifying that client layer does not depend on dataaccess layer.
    // L05: verifying that client layer does not depend on batch layer.
    @ArchTest
    static final ArchRule client_should_not_depend_on_logic_persistence_or_batch_layer =
            noClasses().that().resideInAPackage("..client..")
                    .should().dependOnClassesThat().resideInAnyPackage("..logic..", "..dataaccess..", "..batch..").allowEmptyShould(true);

    // L07: verifying that batch layer does not depend on service layer.
    // L11: verifying that batch layer does not depend on dataaccess layer.
    @ArchTest
    static final ArchRule batch_should_not_depend_on_service_or_persistence =
            noClasses().that().resideInAPackage("..batch..")
                    .should().dependOnClassesThat().resideInAnyPackage("..service..", "..dataaccess..").allowEmptyShould(true);
}
