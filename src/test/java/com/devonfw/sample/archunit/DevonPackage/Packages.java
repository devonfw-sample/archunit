package com.devonfw.sample.archunit.DevonPackage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * {@link Packages} of the Architecture.
 */
public class Packages {
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

    private static final String GROUP_COMPONENT = "component";

    private static final String GROUP_LAYER = "layer";

    private static final String GROUP_SCOPE = "scope";

    private static final String GROUP_DETAIL = "detail";

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

    private String pattern;

    private transient Pattern regex;

    private Map<String, String> mappings;

    private List<String> groups;

    private int minimumRootSegments;

    /**
     * The constructor.
     */
    public Packages() {

    }

    /**
     * The constructor.
     *
     * @param pattern  of this {@link Packages}.
     * @param mappings of this {@link Packages}.
     * @param groups   of this {@link Packages}.
     */
    public Packages(String pattern, Map<String, String> mappings, List<String> groups) {

        this.pattern = pattern;
        this.mappings = mappings;
        this.groups = groups;
    }

    /**
     * @return the requested default {@link Packages}
     */
    public static Packages getDefault() {

        Map<String, String> defaultMappings = new HashMap<>();
        defaultMappings.put("gui", "client");

        Packages packages = new Packages(DEFAULT_PATTERN, defaultMappings, DEFAULT_GROUPS);
        packages.minimumRootSegments = 3;
        return packages;
    }

    /**
     * @return the regular expression pattern the {@link Class#getPackage() package}
     *         has to match. This pattern must not
     *         match the entire {@link Class#getPackage() package} as it excludes
     *         the
     *         {@link com.devonfw.sample.archunit.DevonPackage.DevonArchitecturePackage.sonarqube.common.api.config.DevonArchitecturePackage#getRoot()
     *         root package}.
     */
    public String getPattern() {

        return this.pattern;
    }

    /**
     * @param pattern new value of {@link #getPattern()}.
     */
    public void setPattern(String pattern) {

        this.regex = null;
        this.pattern = pattern;
    }

    /**
     * @return the {@link #getPattern() pattern} compiled as regular expression
     *         {@link Pattern}. It will be lazily
     *         initialized. This is not a regular getter to prevent JSON
     *         serialization of this transient property.
     */
    public Pattern patternRegex() {

        if ((this.regex == null) && (this.pattern != null)) {
            this.regex = Pattern.compile(this.pattern);
        }
        return this.regex;
    }

    /**
     * @return a {@link Map} that may {@link Map#get(Object) re-map} specific
     *         package segments to the normalized default
     *         segment names used by devon4j. This allows e.g. to use
     *         {@code persistence} instead of {@code dataaccess},
     *         {@code core} instead of {@code logic}, or {@code gui} instead of
     *         {@code client}.
     */
    public Map<String, String> getMappings() {

        return this.mappings;
    }

    /**
     * @param mappings new value of {@link #getMappings()}.
     */
    public void setMappings(Map<String, String> mappings) {

        this.mappings = mappings;
    }

    /**
     * @return the {@link List} of the group names in order.
     */
    public List<String> getGroups() {

        return this.groups;
    }

    /**
     * @param groups new value of {@link #getGroups()}.
     */
    public void setGroups(List<String> groups) {

        this.groups = groups;
    }

    /**
     * @return the minimum number of package segments required for the
     *         {@link com.devonfw.sample.archunit.DevonPackage.DevonArchitecturePackage.sonarqube.common.api.config.DevonArchitecturePackage#getRoot()
     *         root package}.
     */
    public int getMinimumRootSegments() {

        return this.minimumRootSegments;
    }

    /**
     * @param minimumRootSegments new value of {@link #getMinimumRootSegments()}.
     */
    public void setMinimumRootSegments(int minimumRootSegments) {

        this.minimumRootSegments = minimumRootSegments;
    }

}
