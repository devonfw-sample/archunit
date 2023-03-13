package com.devonfw.sample.archunit.architecture;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tngtech.archunit.core.domain.JavaClass;

/**
 * Wrapper for a {@link Class#getPackage() package}
 */
public class PackageStructure {
    /* Layer */
    private static final String LAYER_COMMON = "common";

    private static final String LAYER_DATA_ACCESS = "dataaccess";

    private static final String LAYER_LOGIC = "logic";

    private static final String LAYER_SERVICE = "service";

    private static final String LAYER_CLIENT = "client";

    private static final String LAYER_BATCH = "batch";

    private static final String SCOPE_API = "api";

    private static final String SCOPE_BASE = "base";

    private static final String SCOPE_IMPLEMENTATION = "impl";
    /**
     * {@link Packages#getGroups() Group name} for the {@link #getComponent()
     * component}.
     */
    public static final String GROUP_COMPONENT = "component";

    /**
     * {@link Packages#getGroups() Group name} for the {@link #getLayer() layer}.
     */
    public static final String GROUP_LAYER = "layer";

    /**
     * {@link Packages#getGroups() Group name} for the {@link #getScope() scope}.
     */
    public static final String GROUP_SCOPE = "scope";

    /**
     * {@link Packages#getGroups() Group name} for the {@link #getDetail() detail}.
     */
    public static final String GROUP_DETAIL = "detail";

    /** {@link Packages#getGroups() Group name} of group to be ignored. */
    public static final String GROUP_IGNORE = "-";

    private static final Logger LOGGER = Logger.getLogger(PackageStructure.class.getName());

    private final String packageName;

    private final boolean valid;

    private final String root;

    private final String component;

    private final String layer;

    private final String scope;

    private final String detail;

    private String application;

    private static final List<String> LAYERS = Arrays.asList(LAYER_BATCH, LAYER_CLIENT, LAYER_COMMON, LAYER_DATA_ACCESS,
            LAYER_LOGIC, LAYER_SERVICE);

    private static final List<String> SCOPES = Arrays.asList(SCOPE_API, SCOPE_BASE, SCOPE_IMPLEMENTATION);

    private static final String PATTERN_LAYERS = LAYER_COMMON + "|"
            + LAYER_DATA_ACCESS + "|" + LAYER_SERVICE + "|"
            + LAYER_BATCH + "|" + LAYER_LOGIC + "|"
            + LAYER_CLIENT + "|gui";

    private static final String PATTERN_SCOPES = SCOPE_API + "|"
            + SCOPE_BASE + "|" + SCOPE_IMPLEMENTATION;

    private static final String REGEX_COMPONENT = "([a-zA-Z0-9_]+)";
    private static final String REGEX_LAYER = "(" + PATTERN_LAYERS + ")";
    private static final String REGEX_SCOPE = "(" + PATTERN_SCOPES + ")?[.]?";
    private static final String REGEX_DETAIL = "([a-zA-Z0-9_]+[.])?";
    private static final String REGEX_CLASS = "[.]?([a-zA-Z0-9_]+)*";
    private static final String DEFAULT_PATTERN =
            // ....1................2....................(3).................(4)....................5
            REGEX_COMPONENT + "[.]" + REGEX_LAYER + "[.]" + REGEX_SCOPE + REGEX_DETAIL + REGEX_CLASS;
    private static final List<String> DEFAULT_GROUPS = Arrays.asList(GROUP_COMPONENT,
            GROUP_LAYER, GROUP_SCOPE,
            GROUP_DETAIL);

    private static final int MINIMUM_ROOT_SEGMENTS = 3;

    private static final Pattern PATTERN = Pattern.compile(DEFAULT_PATTERN);

    /**
     * The constructor.
     *
     * @param packageName the {@link #getPackage() package name}.
     * @param valid       the {@link #isValid() valid flag}.
     * @param root        the {@link #getRoot() root package}.
     * @param component   the {@link #getComponent() component}.
     * @param layer       the {@link #getLayer() layer}.
     * @param scope       the {@link #getScope() scope}.
     * @param detail      the {@link #getDetail() detail}.
     */
    public PackageStructure(String packageName, boolean valid, String root, String component, String layer,
            String scope,
            String detail) {

        super();
        this.packageName = packageName;
        this.valid = valid;
        this.root = root;
        this.component = component;
        this.layer = layer;
        this.scope = scope;
        this.detail = detail;
    }

    /**
     * @return the {@link Class#getPackage() package} as plain {@link String}.
     */
    public String getPackage() {

        return this.packageName;
    }

    /**
     * Valid architecture package pattern:
     * (root).(component).(layer)[.(scope).(detail)] (scope and
     * detail are optional),
     * Valid package example:
     * 'com.devonfw.sample.archunit.task.service.api'. Where root =
     * com.devonfw.sample; component = task; layer = service; scope = "api";
     * detail = "";
     * 
     * @return {@code true} if this {@link #getPackage() package} is valid according
     *         to architecture pattern,
     *         {@code false} otherwise.
     * 
     */
    public boolean isValid() {

        return this.valid;
    }

    /**
     * @return the name of the root-package before the pattern matched. Will be the
     *         empty {@link String} if not
     *         {@link #isValid() valid}.
     */
    public String getRoot() {

        return this.root;
    }

    /**
     * @param otherPkg the other {@link PackageStructure} to compare.
     * @return {@code true} if both this and the given
     *         {@link PackageStructure}s have
     *         the same {@link #getRoot() root},
     *         {@code false} otherwise.
     */
    public boolean hasSameRoot(PackageStructure otherPkg) {

        return getRoot().equals(otherPkg.getRoot());
    }

    /**
     * @param otherPkg the other {@link PackageStructure} to compare.
     * @return {@code true} if both this and the given
     *         {@link PackageStructure}s have
     *         the same {@link #getComponent()
     *         component}, {@code false} otherwise.
     */
    public boolean hasSameComponent(PackageStructure otherPkg) {

        return getComponent().equals(otherPkg.getComponent());
    }

    /**
     * @param otherPkg the other {@link PackageStructure} to compare.
     * @return {@code true} if both this and the given
     *         {@link PackageStructure}s have
     *         the same {@link #getComponent()
     *         component}, {@link #getLayer() layer}, and {@link #getRoot() root},
     *         {@code false} otherwise.
     */
    public boolean hasSameComponentPart(PackageStructure otherPkg) {

        return hasSameComponent(otherPkg) && hasSameLayer(otherPkg) && hasSameRoot(otherPkg);
    }

    /**
     * @return the name of the component. Will be the empty {@link String} if not
     *         {@link #isValid() valid}.
     */
    public String getComponent() {

        return this.component;
    }

    /**
     * @return the name of the layer. Will be the empty {@link String} if not
     *         {@link #isValid() valid}.
     */
    public String getLayer() {

        return this.layer;
    }

    /**
     * @param otherPkg the other {@link PackageStructure} to compare.
     * @return {@code true} if both this and the given
     *         {@link PackageStructure}s have
     *         the same {@link #getLayer() layer},
     *         {@code false} otherwise.
     */
    public boolean hasSameLayer(PackageStructure otherPkg) {

        return getLayer().equals(otherPkg.getLayer());
    }

    /**
     * @return {@code true} if the {@link #getLayer() layer} is a valid architecture
     *         layer, {@code false} otherwise.
     */
    public boolean isValidLayer() {

        return LAYERS.contains((this.layer));
    }

    /**
     * @return {@code true} if the {@link #getLayer() layer} is {@link #LAYER_BATCH
     *         batch}, {@code false} otherwise.
     */
    public boolean isLayerBatch() {

        return LAYER_BATCH.equals(getLayer());
    }

    /**
     * @return {@code true} if the {@link #getLayer() layer} is {@link #LAYER_CLIENT
     *         client}, {@code false} otherwise.
     */
    public boolean isLayerClient() {

        return LAYER_CLIENT.equals(getLayer());
    }

    /**
     * @return {@code true} if the {@link #getLayer() layer} is {@link #LAYER_COMMON
     *         common}, {@code false} otherwise.
     */
    public boolean isLayerCommon() {

        return LAYER_COMMON.equals(getLayer());
    }

    /**
     * @return {@code true} if the {@link #getLayer() layer} is
     *         {@link #LAYER_DATA_ACCESS dataaccess}, {@code false}
     *         otherwise.
     */
    public boolean isLayerDataAccess() {

        return LAYER_DATA_ACCESS.equals(getLayer());
    }

    /**
     * @return {@code true} if the {@link #getLayer() layer} is {@link #LAYER_LOGIC
     *         logic}, {@code false} otherwise.
     */
    public boolean isLayerLogic() {

        return LAYER_LOGIC.equals(getLayer());
    }

    /**
     * @return {@code true} if the {@link #getLayer() layer} is
     *         {@link #LAYER_SERVICE service}, {@code false} otherwise.
     */
    public boolean isLayerService() {

        return LAYER_SERVICE.equals(getLayer());
    }

    /**
     * @return the name of the scope. Will be the empty {@link String} if not
     *         {@link #isValid() valid}.
     */
    public String getScope() {

        return this.scope == null ? "" : this.scope;
    }

    /**
     * @return {@code true} if the {@link #getScope() scope} is a valid architecture
     *         scope, {@code false} otherwise.
     */
    public boolean isValidScope() {

        return SCOPES.contains(this.scope);
    }

    /**
     * @return {@code true} if the {@link #getScope() scope} is {@link #SCOPE_API
     *         api}, {@code false} otherwise.
     */
    public boolean isScopeApi() {

        return SCOPE_API.equals(getScope());
    }

    /**
     * @return {@code true} if the {@link #getScope() scope} is {@link #SCOPE_BASE
     *         base}, {@code false} otherwise.
     */
    public boolean isScopeBase() {

        return SCOPE_BASE.equals(getScope());
    }

    /**
     * @return {@code true} if the {@link #getScope() scope} is
     *         {@link #SCOPE_IMPLEMENTATION impl}, {@code false}
     *         otherwise.
     */
    public boolean isScopeImpl() {

        return SCOPE_IMPLEMENTATION.equals(getScope());
    }

    /**
     * @return the name of the application. Will be the empty {@link String} if not
     *         {@link #isValid() valid}.
     */
    public String getApplication() {

        if (this.application == null) {
            int lastIndexOfRoot = this.root.lastIndexOf(".");
            if (lastIndexOfRoot > 0) {
                this.application = this.root.substring(lastIndexOfRoot + 1);
            } else {
                this.application = this.root;
            }
        }
        return this.application;
    }

    /**
     * @return the detail (suffix) of the package after the official segments
     *         specified by the architecture. Will be the
     *         empty {@link String} if not present.
     */
    public String getDetail() {
        String detail = "";
        if (this.detail != null && this.detail != "") {
            detail = this.detail.substring(0, this.detail.length() - 1);
        }
        return detail;
    }

    @Override
    public String toString() {

        return this.packageName;
    }

    public static PackageStructure of(String source) {

        String root = "";
        boolean valid = false;
        String component = "";
        String layer = "";
        String scope = "";
        String detail = "";
        Matcher matcher = PATTERN.matcher(source);
        int i = 1;
        if (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (start >= 1) {
                root = source.substring(0, start - 1);
                int segments = countChars(root, '.'); // segments = #dots + 1
                if (segments >= MINIMUM_ROOT_SEGMENTS) {
                    if (end == source.length()) {
                        valid = true;
                        int groupCount = matcher.groupCount();
                        for (String group : DEFAULT_GROUPS) {
                            if (i > groupCount) {
                                LOGGER.log(Level.WARNING,
                                        "The package '" + source
                                                + "' contains more groups than declared in your architecture.json.");
                                break;
                            }
                            String value = matcher.group(i);
                            switch (group) {
                                case GROUP_LAYER:
                                    layer = value;
                                    break;
                                case GROUP_COMPONENT:
                                    component = value;
                                    break;
                                case GROUP_SCOPE:
                                    scope = value;
                                    break;
                                case GROUP_DETAIL:
                                    detail = value;
                                    break;
                                case GROUP_IGNORE:
                                    break;
                                default:
                                    LOGGER.log(Level.WARNING, "The group '" + group + "' is unknown.");
                            }
                            i++;
                        }
                    }
                }
            }
        }
        return new PackageStructure(source, valid, root, component, layer, scope, detail);
    }

    public static PackageStructure of(JavaClass sourceClass) {
        String fullyQualifiedName = sourceClass.getFullName();
        return of(fullyQualifiedName);
    }

    private static int countChars(String string, char c) {

        int count = 0;
        int i = 0;
        while (i >= 0) {
            i = string.indexOf(c, i);
            count++;
            if (i >= 0) {
                i++;
            }
        }
        return count;
    }

}
