package com.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

/** Immutable configuration snapshot: JVM properties > environment > optional classpath file > defaults. */
public final class FrameworkConfig {
    private static final String RESOURCE = "config/framework.properties";
    private static final FrameworkConfig INSTANCE = new FrameworkConfig();
    private final Properties values;

    private FrameworkConfig() {
        Properties loaded = new Properties();
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(RESOURCE)) {
            if (stream != null) loaded.load(stream);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load " + RESOURCE, exception);
        }
        values = loaded;
    }

    public static FrameworkConfig getInstance() { return INSTANCE; }

    public String get(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (hasText(value)) return value.trim();
        String envKey = key.toUpperCase(Locale.ROOT).replace('.', '_').replace('-', '_');
        value = System.getenv(envKey);
        if (hasText(value)) return value.trim();
        value = values.getProperty(key);
        return hasText(value) ? value.trim() : defaultValue;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key, Boolean.toString(defaultValue));
        if (!"true".equalsIgnoreCase(value) && !"false".equalsIgnoreCase(value))
            throw new IllegalArgumentException("Configuration '" + key + "' must be true or false.");
        return Boolean.parseBoolean(value);
    }

    public int getInt(String key, int defaultValue) {
        try { return Integer.parseInt(get(key, Integer.toString(defaultValue))); }
        catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Configuration '" + key + "' must be an integer.", exception);
        }
    }

    private boolean hasText(String value) { return value != null && !value.isBlank(); }
}