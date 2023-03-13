package com.devonfw.sample.archunit;

import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

import static com.tngtech.archunit.base.DescribedPredicate.alwaysTrue;
import static com.tngtech.archunit.core.domain.properties.HasName.Predicates.nameMatching;

@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ComponentRuleTest {

    /**
     * verifying that the service layer of one component does not depend on the
     * service layer of
     * another component.
     */
    @ArchTest
    public static final ArchRule C3_no_service_layer_depends_on_service_layer_of_another_component = slices()
            .matching("..archunit.(*).service..")
            .namingSlices("$1 service").should().notDependOnEachOther()
            .ignoreDependency(alwaysTrue(), nameMatching(".*general.*"))
            .as("Code from service layer shall not depend on service layer of a different component");

    public static final ArchCondition<JavaClass> haveComponentServiceLayerDependingOnDiffComponentsLogicLayer = new ArchCondition<JavaClass>(
            "have dependencies, towards another components logic layer (Rule-C4).") {
        @Override
        public void check(JavaClass sourceClass, ConditionEvents events) {

            String sourceClassPackageName = sourceClass.getPackageName();
            PackageStructure sourcePkg = PackageStructure.of(sourceClassPackageName);
            String sourceClassLayer = sourcePkg.getLayer();
            String sourceClassComponent = sourcePkg.getComponent();
            // Check all project components' service layers except the default component for
            // noncompliant dependencies towards
            // other components' logic layers.
            if (sourcePkg.isLayerService() && !sourceClassComponent.equals("")
                    && !sourceClassComponent.equals("general")) {
                for (Dependency dependency : sourceClass.getDirectDependenciesFromSelf()) {
                    JavaClass targetClass = dependency.getTargetClass();
                    String targetClassPackageName = targetClass.getPackageName();
                    PackageStructure targetPkg = PackageStructure.of(targetClassPackageName);
                    String targetClassComponent = targetPkg.getComponent();
                    String targetClassLayer = targetPkg.getLayer();
                    boolean isAllowedDependency = isComponentServiceLayerCompliant(sourceClass, targetClass);

                    // WARNING: Dependency of a components service layer towards another components
                    // layer other than logic wont be
                    // registered as a violation.
                    // (Other rules cover these violations.)
                    if (targetPkg.isLayerLogic() && !isAllowedDependency) {
                        String message = String.format(
                                "'%s.%s' is dependend on '%s.%s'. Violated in: (%s). Dependency towards (%s)",
                                sourceClassComponent, sourceClassLayer, targetClassComponent, targetClassLayer,
                                sourceClass.getDescription(), targetClass.getDescription());
                        events.add(new SimpleConditionEvent(sourceClass, true, message));
                    }
                }
            }
        }
    };

    /**
     * verifying that the service layer of one component does not depend on the
     * logic layer of
     * another component.
     */
    @ArchTest
    public static final ArchRule C4_no_dependencies_from_a_components_service_layer_to_anothers_component_logic_layer = noClasses()
            .should(haveComponentServiceLayerDependingOnDiffComponentsLogicLayer)
            .as("Code from service layer of a component shall not depend on logic layer of a different component.")
            .allowEmptyShould(true);

    public static ArchCondition<JavaClass> haveComponentLogicLayerDependingOnDiffComponentsDataaccessLayer = new ArchCondition<JavaClass>(
            "have dependencies, towards another components dataaccess layer (Rule-C5).") {
        @Override
        public void check(JavaClass sourceClass, ConditionEvents events) {
            String sourceClassPackageName = sourceClass.getPackageName();
            PackageStructure sourcePkg = PackageStructure.of(sourceClassPackageName);
            String sourceClassLayer = sourcePkg.getLayer();
            String sourceClassComponent = sourcePkg.getComponent();

            // All project components logic layer except the default component.
            if (sourceClassLayer.equals("logic") && !sourceClassComponent.equals("")
                    && !sourceClassComponent.equals("general")) {
                for (Dependency dependency : sourceClass.getDirectDependenciesFromSelf()) {
                    JavaClass targetClass = dependency.getTargetClass();
                    String targetClassPackageName = targetClass.getPackageName();
                    PackageStructure targetPkg = PackageStructure.of(targetClassPackageName);
                    String targetClassComponent = targetPkg.getComponent();
                    String targetClassLayer = targetPkg.getLayer();
                    boolean isAllowedDependency = isComponentLogicLayerCompliant(sourceClass, targetClass);

                    // WARNING: Dependency of a components logic layer towards another components
                    // layer other than logic wont be registered as a violation.
                    // (Other rules cover these violations.)
                    if (targetPkg.isLayerDataAccess() && !isAllowedDependency) {
                        String message = String.format(
                                "'%s.%s' is dependend on '%s.%s'. Violated in: (%s). Dependency towards (%s)",
                                sourceClassComponent, sourceClassLayer, targetClassComponent, targetClassLayer,
                                sourceClass.getDescription(), targetClass.getDescription());
                        events.add(new SimpleConditionEvent(sourceClass, true, message));
                    }
                }
            }
        }
    };

    /**
     * verifying that the logic layer of a component may not depend on the
     * dataaccess layer of another component.
     */
    @ArchTest
    public static final ArchRule C5_no_dependencies_from_a_components_logic_layer_to_anothers_component_dataaccess_layer = noClasses()
            .should(haveComponentLogicLayerDependingOnDiffComponentsDataaccessLayer)
            .as("Code from logic layer of a component shall not depend on dataaccess layer of a different component.")
            .allowEmptyShould(true);

    /**
     * verifying that the dataaccess layer of one component does not depend on the
     * dataaccess layer of
     * another component.
     */

    // medium severity
    @ArchTest
    public static final ArchRule C6_no_dataaccess_layer_depends_on_dataaccess_layer_of_another_component = slices()
            .matching("..archunit.(*).dataaccess..")
            .namingSlices("$1 dataaccess")
            .should()
            .notDependOnEachOther()
            .ignoreDependency(alwaysTrue(), nameMatching(".*general.*"))
            .as("Code from dataaccess layer shall not depend on dataaccess layer of a different component");

    public static ArchCondition<JavaClass> haveComponentDataaccessLayerDependingOnDiffComponentsDataaccessLayer = new ArchCondition<JavaClass>(
            "have dependencies, towards another components logic layer (Rule-C7).") {
        @Override
        public void check(JavaClass sourceClass, ConditionEvents events) {
            String sourceClassPackageName = sourceClass.getPackageName();
            PackageStructure sourcePkg = PackageStructure.of(sourceClassPackageName);
            String sourceClassLayer = sourcePkg.getLayer();
            String sourceClassComponent = sourcePkg.getComponent();

            // All project components batch layer except the default component.
            if (sourcePkg.isLayerBatch() && !sourceClassComponent.equals("")
                    && !sourceClassComponent.equals("general")) {
                for (Dependency dependency : sourceClass.getDirectDependenciesFromSelf()) {
                    JavaClass targetClass = dependency.getTargetClass();
                    String targetClassPackageName = targetClass.getPackageName();
                    PackageStructure targetPkg = PackageStructure.of(targetClassPackageName);
                    String targetClassComponent = targetPkg.getComponent();
                    String targetClassLayer = targetPkg.getLayer();
                    boolean isAllowedDependency = isComponentBatchLayerCompliant(sourceClass, targetClass);

                    // WARNING: Dependency of a components batch layer towards another components
                    // layer other than logic wont be registered as a violation.
                    // (Other rules cover these violations.)
                    if (targetPkg.isLayerBatch() && !isAllowedDependency) {
                        String message = String.format(
                                "'%s.%s' is dependend on '%s.%s'. Violated in: (%s). Dependency towards (%s)",
                                sourceClassComponent, sourceClassLayer, targetClassComponent, targetClassLayer,
                                sourceClass.getDescription(), targetClass.getDescription());
                        events.add(new SimpleConditionEvent(sourceClass, true, message));
                    }
                }
            }
        }
    };

    /**
     * verifying that the batch layer of a component may not depend on the
     * logic layer of another component.
     */
    @ArchTest
    public static final ArchRule C7_no_dependencies_from_a_components_batch_layer_to_anothers_component_logic_layer = noClasses()
            .should(haveComponentDataaccessLayerDependingOnDiffComponentsDataaccessLayer)
            .as("Code from batch layer of a component shall not depend on logic layer of a different component.")
            .allowEmptyShould(true);

    /**
     * Dependency of a components batch layer towards the same components logic or
     * common layer is allowed.
     */
    public static boolean isComponentBatchLayerCompliant(JavaClass sourceClass, JavaClass targetClass) {
        boolean isAllowed = false;
        PackageStructure sourcePkg = PackageStructure.of(sourceClass.getPackageName());
        PackageStructure targetPkg = PackageStructure.of(targetClass.getPackageName());
        // Components batch layer can depend on their own lower layers: logic and
        // common.
        if (sourcePkg.hasSameComponent(targetPkg)
                && (targetPkg.isLayerLogic() || targetPkg.isLayerCommon())) {
            isAllowed = true;
        }
        // Components may always depend on the default business component.
        if (targetPkg.getComponent().equals("general")
                && (targetPkg.isLayerLogic() || targetPkg.isLayerCommon())) {
            isAllowed = true;
        }
        return isAllowed;
    }

    /**
     * Dependency of a components logic layer towards the same components dataaccess
     * or common layer is allowed.
     */
    public static boolean isComponentLogicLayerCompliant(JavaClass sourceClass, JavaClass targetClass) {
        boolean isAllowed = false;
        PackageStructure sourcePkg = PackageStructure.of(sourceClass.getPackageName());
        PackageStructure targetPkg = PackageStructure.of(targetClass.getPackageName());
        // Components logic layer can depend on their own lower layers: dataaccess and
        // common.
        if (sourcePkg.hasSameComponent(targetPkg)
                && (targetPkg.isLayerDataAccess() || targetPkg.isLayerCommon())) {
            isAllowed = true;
        }
        // Components may always depend on the default business component.
        if (targetPkg.getComponent().equals("general")
                && (targetPkg.isLayerDataAccess() || targetPkg.isLayerCommon())) {
            isAllowed = true;
        }
        return isAllowed;
    }

    /**
     * Dependency of a components service layer towards the same components logic or
     * common layer is allowed. In addition
     * a dependency towards the projects default component is allowed too.
     *
     * @param sourceClass Source JavaClass to check if dependencies from itself
     *                    towards targetClass are allowed.
     * @param targetClass Target JavaClass to check if dependencies from sourceClass
     *                    towards it are allowed.
     */
    public static boolean isComponentServiceLayerCompliant(JavaClass sourceClass, JavaClass targetClass) {

        boolean isAllowed = false;
        PackageStructure sourcePkg = PackageStructure.of(sourceClass.getPackageName());
        PackageStructure targetPkg = PackageStructure.of(targetClass.getPackageName());
        // Components service layer can depend on their own lower layers: logic and
        // common.
        if (sourcePkg.hasSameComponent(targetPkg)
                && (targetPkg.isLayerLogic() || targetPkg.isLayerCommon())) {
            isAllowed = true;
        }
        // Components may always depend on the default business component.
        if (targetPkg.getComponent().equals("general")
                && (targetPkg.isLayerLogic() || targetPkg.isLayerCommon())) {
            isAllowed = true;
        }
        return isAllowed;
    }

}
