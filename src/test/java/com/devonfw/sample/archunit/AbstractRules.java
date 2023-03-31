package com.devonfw.sample.archunit;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.Priority;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.Creator;

/**
 * Abstract base class for all topic based collections of rules.
 */
abstract class AbstractRules {

  static final String EMBEDDABLE = "Embeddable";

  static Creator priority(Priority priority) {

    return ArchRuleDefinition.priority(priority);
  }

  static DescribedPredicate<JavaClass> haveLayer(String layer) {

    return new DescribedPredicate<>("in " + layer + " layer") {
      @Override
      public boolean test(JavaClass type) {

        PackageStructure pkg = PackageStructure.of(type);
        return pkg.getLayer().equals(layer);
      }
    };
  }

  static DescribedPredicate<JavaClass> haveServiceLayer() {

    return haveLayer(PackageStructure.LAYER_SERVICE);
  }

  static DescribedPredicate<JavaClass> haveLogicLayer() {

    return haveLayer(PackageStructure.LAYER_LOGIC);
  }

  static DescribedPredicate<JavaClass> haveDataaccessLayer() {

    return haveLayer(PackageStructure.LAYER_DATA_ACCESS);
  }

  static DescribedPredicate<JavaClass> haveBatchLayer() {

    return haveLayer(PackageStructure.LAYER_BATCH);
  }

  static DescribedPredicate<JavaClass> haveCommonLayer() {

    return haveLayer(PackageStructure.LAYER_COMMON);
  }

  static ArchCondition<JavaClass> dependOnOtherComponentsLayer(String layer) {

    return new ArchCondition<>("depend on the " + layer + " layer of a different component") {
      @Override
      public void check(JavaClass sourceClass, ConditionEvents events) {

        PackageStructure sourcePkg = PackageStructure.of(sourceClass);

        for (Dependency dependency : sourceClass.getDirectDependenciesFromSelf()) {

          JavaClass targetClass = dependency.getTargetClass();
          PackageStructure targetPkg = PackageStructure.of(targetClass);

          if (targetPkg.isValid() && !sourcePkg.hasSameComponent(targetPkg) && targetPkg.getLayer().equals(layer)
              && (!targetPkg.isComponentGeneral() || !sourcePkg.getLayer().equals(layer))) {
            String message = String.format("'%s.%s' depends on '%s.%s' violated in %s by dependency on %s", //
                sourcePkg.getComponent(), sourcePkg.getLayer(), //
                targetPkg.getComponent(), targetPkg.getLayer(), //
                sourceClass.getDescription(), targetClass.getDescription());
            events.add(new SimpleConditionEvent(sourceClass, false, message));
          }
        }
      }
    };
  }

  static ArchCondition<JavaClass> dependOnOtherComponentsServiceLayer() {

    return dependOnOtherComponentsLayer(PackageStructure.LAYER_SERVICE);
  }

  static ArchCondition<JavaClass> dependOnOtherComponentsLogicLayer() {

    return dependOnOtherComponentsLayer(PackageStructure.LAYER_LOGIC);
  }

  static ArchCondition<JavaClass> dependOnOtherComponentsDataaccessLayer() {

    return dependOnOtherComponentsLayer(PackageStructure.LAYER_DATA_ACCESS);
  }

  static ArchCondition<JavaClass> dependOnOtherComponentsBatchLayer() {

    return dependOnOtherComponentsLayer(PackageStructure.LAYER_BATCH);
  }

  static ArchCondition<JavaClass> dependOnOtherComponentsCommonLayer() {

    return dependOnOtherComponentsLayer(PackageStructure.LAYER_COMMON);
  }

  static DescribedPredicate<JavaClass> haveComponent(String component) {

    return new DescribedPredicate<>("in " + component + " component") {
      @Override
      public boolean test(JavaClass type) {

        PackageStructure pkg = PackageStructure.of(type);
        return pkg.getComponent().equals(component);
      }
    };
  }

  static DescribedPredicate<JavaClass> notHaveComponent(String component) {

    return new DescribedPredicate<>("not in " + component + " component") {
      @Override
      public boolean test(JavaClass type) {

        PackageStructure pkg = PackageStructure.of(type);
        return pkg.isValid() && !pkg.getComponent().equals(component);
      }
    };
  }

  static DescribedPredicate<JavaClass> haveGeneralComponent() {

    return haveComponent(PackageStructure.COMPONENT_GENERAL);
  }

  static DescribedPredicate<JavaClass> notHaveGeneralComponent() {

    return notHaveComponent(PackageStructure.COMPONENT_GENERAL);
  }

  static ArchCondition<JavaClass> implementEntityInterface(String suffix, String interfaceLayer) {

    return new ArchCondition<>("implement an interface with entity name (excluding the " + suffix + " suffix)"
        + ((interfaceLayer == null) ? "" : " in " + interfaceLayer + " layer")) {
      @Override
      public void check(JavaClass javaClass, ConditionEvents events) {

        String expectedSimpleName = javaClass.getSimpleName();
        if (expectedSimpleName.endsWith(suffix)) {
          expectedSimpleName = expectedSimpleName.substring(0, expectedSimpleName.length() - suffix.length());
        } else {
          events.add(new SimpleConditionEvent(javaClass, false, javaClass.getFullName() + " does not end with suffix " + suffix));
          return;
        }
        String expectedPackageName;
        if (interfaceLayer == null) {
          expectedPackageName = javaClass.getPackageName();
        } else {
          PackageStructure pkg = PackageStructure.of(javaClass).withLayer(interfaceLayer);
          expectedPackageName = pkg.getPackage();
        }
        String expectedInterfaceName = expectedPackageName + "." + expectedSimpleName;
        if (!javaClass.getInterfaces().stream().anyMatch(i -> i.getName().equals(expectedInterfaceName))) {
          String message = javaClass.getFullName() + " does not implement " + expectedInterfaceName;
          events.add(new SimpleConditionEvent(javaClass, false, message));
        }
      }
    };
  }

}
