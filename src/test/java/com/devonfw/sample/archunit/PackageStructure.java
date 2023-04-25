package com.devonfw.sample.archunit;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tngtech.archunit.core.domain.JavaClass;

/**
 * A structured representation of a {@link Package#getName() package name} according to the architecture definition of
 * this project.
 */
public class PackageStructure {

  /** The {@link #getLayer() layer} {@value} */
  public static final String LAYER_COMMON = "common";

  /** The {@link #getLayer() layer} {@value} */
  public static final String LAYER_DATA_ACCESS = "dataaccess";

  /** The {@link #getLayer() layer} {@value} */
  public static final String LAYER_LOGIC = "logic";

  /** The {@link #getLayer() layer} {@value} */
  public static final String LAYER_SERVICE = "service";

  /** The {@link #getLayer() layer} {@value} */
  public static final String LAYER_CLIENT = "client";

  /** The {@link #getLayer() layer} {@value} */
  public static final String LAYER_BATCH = "batch";

  /** The {@link #getScope() scope} {@value} */
  public static final String SCOPE_API = "api";

  /** The {@link #getScope() scope} {@value} */
  public static final String SCOPE_BASE = "base";

  /** The {@link #getScope() scope} {@value} */
  public static final String SCOPE_IMPLEMENTATION = "impl";

  /** The {@link #isComponentGeneral() general component}. */
  public static final String COMPONENT_GENERAL = "general";

  /** {@link Pattern#pattern() Pattern} for valid layers. */
  public static final String PATTERN_LAYERS = LAYER_COMMON + "|" + LAYER_DATA_ACCESS + "|" + LAYER_SERVICE + "|" + LAYER_BATCH + "|"
      + LAYER_LOGIC + "|" + LAYER_CLIENT;

  private final String packageName;

  private final boolean valid;

  private final String root;

  private final String component;

  private final String layer;

  private final String scope;

  private final String detail;

  private static final String REGEX_SEGMENT = "[a-z][a-zA-Z0-9_]+";

  private static final String PATTERN_SCOPES = SCOPE_API + "|" + SCOPE_BASE + "|" + SCOPE_IMPLEMENTATION;

  private static final String REGEX_PACKAGE =
      // 1..root.....................................2..
      "(" + REGEX_SEGMENT + "\\." + REGEX_SEGMENT + "(" + "\\." + REGEX_SEGMENT + ")*" + ")" //
      // .......3....component............4....layer..............5...6....scope
          + "\\.(" + REGEX_SEGMENT + ")\\.(" + PATTERN_LAYERS + ")(\\.(" + PATTERN_SCOPES + "))?"//
          // ....7....detail...........8..
          + "\\.?(" + REGEX_SEGMENT + "(\\." + REGEX_SEGMENT + ")*)?";

  private static final Pattern PATTERN = Pattern.compile(REGEX_PACKAGE);

  /**
   * The constructor.
   *
   * @param packageName the {@link #getPackage() package name}.
   * @param valid the {@link #isValid() valid flag}.
   * @param root the {@link #getRoot() root package}.
   * @param component the {@link #getComponent() component}.
   * @param layer the {@link #getLayer() layer}.
   * @param scope the {@link #getScope() scope}.
   * @param detail the {@link #getDetail() detail}.
   */
  public PackageStructure(String packageName, boolean valid, String root, String component, String layer, String scope, String detail) {

    super();
    this.valid = valid;
    this.root = root;
    this.component = component;
    this.layer = layer;
    this.scope = scope;
    this.detail = detail;
    if (packageName == null) {
      StringBuilder sb = new StringBuilder(48);
      sb.append(root);
      sb.append('.');
      sb.append(component);
      sb.append('.');
      sb.append(layer);
      if (!scope.isEmpty()) {
        sb.append('.');
        sb.append(scope);
      }
      if (!detail.isEmpty()) {
        sb.append('.');
        sb.append(detail);
      }
      this.packageName = sb.toString();
    } else {
      this.packageName = packageName;
    }
  }

  /**
   * @return the {@link Class#getPackage() package} as plain {@link String}.
   */
  public String getPackage() {

    return this.packageName;
  }

  /**
   * Valid architecture package pattern: (root).(component).(layer)[.(scope).(detail)] (scope and detail are optional),
   * Valid package example: 'com.devonfw.sample.archunit.task.service.api'. Where root = com.devonfw.sample; component =
   * task; layer = service; scope = "api"; detail = "";
   *
   * @return {@code true} if this {@link #getPackage() package} is valid according to architecture pattern,
   *         {@code false} otherwise.
   *
   */
  public boolean isValid() {

    return this.valid;
  }

  /**
   * @return the name of the root-package before the pattern matched. Will be the empty {@link String} if not
   *         {@link #isValid() valid}.
   */
  public String getRoot() {

    return this.root;
  }

  /**
   * @param otherPkg the other {@link PackageStructure} to compare.
   * @return {@code true} if both this and the given {@link PackageStructure}s have the same {@link #getRoot() root},
   *         {@code false} otherwise.
   */
  public boolean hasSameRoot(PackageStructure otherPkg) {

    return getRoot().equals(otherPkg.getRoot());
  }

  /**
   * @return the name of the component. Will be the empty {@link String} if not {@link #isValid() valid}.
   */
  public String getComponent() {

    return this.component;
  }

  /**
   * @param otherPkg the other {@link PackageStructure} to compare.
   * @return {@code true} if both this and the given {@link PackageStructure}s have the same {@link #getComponent()
   *         component}, {@code false} otherwise.
   */
  public boolean hasSameComponent(PackageStructure otherPkg) {

    return getComponent().equals(otherPkg.getComponent());
  }

  /**
   * @return {@code true} if this is the "general" component for cross-cutting aspects. It shall never user other
   *         components but may be used by any other component.
   * @see #COMPONENT_GENERAL
   */
  public boolean isComponentGeneral() {

    return getComponent().equals(COMPONENT_GENERAL);
  }

  /**
   * @return the name of the layer. Will be the empty {@link String} if not {@link #isValid() valid}.
   */
  public String getLayer() {

    return this.layer;
  }

  /**
   * @param otherPkg the other {@link PackageStructure} to compare.
   * @return {@code true} if both this and the given {@link PackageStructure}s have the same {@link #getLayer() layer},
   *         {@code false} otherwise.
   */
  public boolean hasSameLayer(PackageStructure otherPkg) {

    return getLayer().equals(otherPkg.getLayer());
  }

  /**
   * @return {@code true} if the {@link #getLayer() layer} is {@link #LAYER_BATCH batch}, {@code false} otherwise.
   */
  public boolean isLayerBatch() {

    return LAYER_BATCH.equals(getLayer());
  }

  /**
   * @return {@code true} if the {@link #getLayer() layer} is {@link #LAYER_CLIENT client}, {@code false} otherwise.
   */
  public boolean isLayerClient() {

    return LAYER_CLIENT.equals(getLayer());
  }

  /**
   * @return {@code true} if the {@link #getLayer() layer} is {@link #LAYER_COMMON common}, {@code false} otherwise.
   */
  public boolean isLayerCommon() {

    return LAYER_COMMON.equals(getLayer());
  }

  /**
   * @return {@code true} if the {@link #getLayer() layer} is {@link #LAYER_DATA_ACCESS dataaccess}, {@code false}
   *         otherwise.
   */
  public boolean isLayerDataAccess() {

    return LAYER_DATA_ACCESS.equals(getLayer());
  }

  /**
   * @return {@code true} if the {@link #getLayer() layer} is {@link #LAYER_LOGIC logic}, {@code false} otherwise.
   */
  public boolean isLayerLogic() {

    return LAYER_LOGIC.equals(getLayer());
  }

  /**
   * @return {@code true} if the {@link #getLayer() layer} is {@link #LAYER_SERVICE service}, {@code false} otherwise.
   */
  public boolean isLayerService() {

    return LAYER_SERVICE.equals(getLayer());
  }

  /**
   * @return the name of the scope. Will be the empty {@link String} if not {@link #isValid() valid}.
   */
  public String getScope() {

    return this.scope;
  }

  /**
   * @return {@code true} if {@link #getScope() scope} is present (not empty).
   */
  public boolean hasScope() {

    return !this.scope.isEmpty();
  }

  /**
   * @return {@code true} if the {@link #getScope() scope} is {@link #SCOPE_API api}, {@code false} otherwise.
   */
  public boolean isScopeApi() {

    return SCOPE_API.equals(this.scope);
  }

  /**
   * @return {@code true} if the {@link #getScope() scope} is {@link #SCOPE_BASE base}, {@code false} otherwise.
   */
  public boolean isScopeBase() {

    return SCOPE_BASE.equals(this.scope);
  }

  /**
   * @return {@code true} if the {@link #getScope() scope} is {@link #SCOPE_IMPLEMENTATION impl}, {@code false}
   *         otherwise.
   */
  public boolean isScopeImpl() {

    return SCOPE_IMPLEMENTATION.equals(this.scope);
  }

  /**
   * @return the detail (suffix) of the package after the official segments specified by the architecture. Will be the
   *         empty {@link String} if not present.
   */
  public String getDetail() {

    return this.detail;
  }

  /**
   * @return {@code true} if {@link #getDetail() detail} is present (not empty).
   */
  public boolean hasDetail() {

    return !this.detail.isEmpty();
  }

  /**
   * @param newRoot the new value of {@link #getRoot()}.
   * @return a copy of this {@link PackageStructure} with the given {@link #getRoot() root}.
   */
  public PackageStructure withRoot(String newRoot) {

    Objects.requireNonNull(newRoot);
    if (newRoot.equals(this.root)) {
      return this;
    }
    return new PackageStructure(null, this.valid, newRoot, this.component, this.layer, this.scope, this.detail);
  }

  /**
   * @param newComponent the new value of {@link #getComponent()}.
   * @return a copy of this {@link PackageStructure} with the given {@link #getComponent() component}.
   */
  public PackageStructure withComponent(String newComponent) {

    Objects.requireNonNull(newComponent);
    if (newComponent.equals(this.component)) {
      return this;
    }
    return new PackageStructure(null, this.valid, this.root, newComponent, this.layer, this.scope, this.detail);
  }

  /**
   * @param newLayer the new value of {@link #getLayer()}.
   * @return a copy of this {@link PackageStructure} with the given {@link #getLayer() layer}.
   */
  public PackageStructure withLayer(String newLayer) {

    Objects.requireNonNull(newLayer);
    if (newLayer.equals(this.layer)) {
      return this;
    }
    return new PackageStructure(null, this.valid, this.root, this.component, newLayer, this.scope, this.detail);
  }

  /**
   * @param newScope the new value of {@link #getScope()}.
   * @return a copy of this {@link PackageStructure} with the given {@link #getScope() scope}.
   */
  public PackageStructure withScope(String newScope) {

    Objects.requireNonNull(newScope);
    if (newScope.equals(this.scope)) {
      return this;
    }
    return new PackageStructure(null, this.valid, this.root, this.component, this.layer, newScope, this.detail);
  }

  /**
   * @param newDetail the new value of {@link #getDetail()}.
   * @return a copy of this {@link PackageStructure} with the given {@link #getDetail() detail}.
   */
  public PackageStructure withDetail(String newDetail) {

    Objects.requireNonNull(newDetail);
    if (newDetail.equals(this.detail)) {
      return this;
    }
    return new PackageStructure(null, this.valid, this.root, this.component, this.layer, this.scope, newDetail);
  }

  @Override
  public String toString() {

    return this.packageName;
  }

  /**
   * @param pkgName the {@link Package#getName() package name} (excluding the type/class).
   * @return a new {@link PackageStructure} instance parsed from the given {@code pkgName}.
   */
  public static PackageStructure of(String pkgName) {

    String root = "";
    String component = "";
    String layer = "";
    String scope = "";
    String detail = "";
    Matcher matcher = PATTERN.matcher(pkgName);
    boolean valid = matcher.matches();
    if (valid) {
      root = matcher.group(1);
      component = matcher.group(3);
      layer = matcher.group(4);
      scope = nonNull(matcher.group(6));
      detail = nonNull(matcher.group(7));
    }
    return new PackageStructure(pkgName, valid, root, component, layer, scope, detail);
  }

  /**
   * @param type the arch-unit {@link JavaClass}.
   * @return a new {@link PackageStructure} instance parsed from the {@code JavaClass#getPackageName() package name} of
   *         the given {@link JavaClass}.
   */
  public static PackageStructure of(JavaClass type) {

    return of(type.getPackageName());
  }

  /**
   * @param type the java {@link Class}.
   * @return a new {@link PackageStructure} instance parsed from the {@code Class#getPackageName() package name} of the
   *         given {@link Class}.
   */
  public static PackageStructure of(Class<?> type) {

    return of(type.getPackageName());
  }

  /**
   * @param pkg the java {@link Package}.
   * @return a new {@link PackageStructure} instance parsed from the {@code Package#getName() package name} of the given
   *         {@link Package}.
   */
  public static PackageStructure of(Package pkg) {

    return of(pkg.getName());
  }

  private static String nonNull(String s) {

    if (s == null) {
      return "";
    }
    return s;
  }
}
