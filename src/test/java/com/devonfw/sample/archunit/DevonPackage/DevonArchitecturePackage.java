package com.devonfw.sample.archunit.DevonPackage;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;

@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class DevonArchitecturePackage extends DevonPackageImpl {

    private final DevonPackage unresolved;

    /**
     * The constructor.
     *
     * @param ori      the {@link #getUnresolved() original package}.
     * @param packages the {@link Packages}.
     */
    public DevonArchitecturePackage(DevonPackage ori, Packages packages) {

        super(ori.getPackage(), ori.isValid(), ori.getRoot(), resolve(ori.getComponent(), packages),
                resolve(ori.getLayer(), packages), resolve(ori.getScope(), packages), ori.getDetail());
        this.unresolved = ori;
    }

    /**
     * @return original {@link DevonPackage} with unresolved segments.
     */
    public DevonPackage getUnresolved() {

        return this.unresolved;
    }

    /**
     * @param packageName the {@link #getPackage() package name}.
     * @param packages    the {@link Packages}.
     * @return the parsed {@link DevonArchitecturePackage}.
     */
    public static DevonArchitecturePackage of(String packageName, Packages packages) {

        DevonPackage originalPackage = ofOriginal(packageName, packages);
        return new DevonArchitecturePackage(originalPackage, packages);
    }

    private static String resolve(String segment, Packages packages) {

        String replacement = packages.getMappings().get(segment);
        if (replacement != null) {
            return replacement;
        }
        return segment;
    }

    public static void main(String args[]) {
        DevonArchitecturePackage sourcePackage = of(
                "com.devonfw.sample.archunit.task.common.base.thatIsADetail",
                Packages.getDefault());
        System.out.println("SourcePackage: " + sourcePackage);
        System.out.println("Component: " + sourcePackage.getComponent());
        System.out.println("Layer: " + sourcePackage.getLayer());
        System.out.println("Scope: " + sourcePackage.getScope());
        System.out.println("Detail: " + sourcePackage.getDetail());
        System.out.println("Unresolved: " + sourcePackage.getUnresolved());
    }
}
