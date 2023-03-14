package com.devonfw.sample.archunit;

import com.tngtech.archunit.core.importer.ImportOption;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ThirdPartyRulesTest {

        @ArchTest
        private static ArchRule check_object_dependency = noClasses()
                        .should().dependOnClassesThat()
                        .haveFullyQualifiedName("com.google.common.base.Objects")
                        .because("Use Java standards instead (java.util.Objects).");

        @ArchTest
        private static ArchRule check_converter_dependency = noClasses()
                        .should().dependOnClassesThat()
                        .haveFullyQualifiedName("javax.persistence.Convert")
                        .because("Use the javax.persistence.Converter annotation on a custom converter"
                                        + " which implements the javax.persistence.AttributeConverter instead of the 'javax.persistance.Convert' annotation");

        @ArchTest
        private static ArchRule check_mysema_dependency = noClasses()
                        .should().dependOnClassesThat().resideInAPackage("com.mysema.query..")
                        .because("Use official QueryDSL (com.querydsl.* e.g. from com.querydsl:querydsl-jpa).");

}