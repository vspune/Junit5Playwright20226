package com.example.utlis;
import java.io.InputStream;
import java.util.Properties;

public class configreader {

private static Properties props = new Properties();
    private static String currentEnv;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        try {
            // ✅ Get environment from system property (default: qa)
            currentEnv = System.getProperty("env", "qa").toLowerCase();
            
            // ✅ Build filename: config-qa.properties, config-staging.properties, etc.
            String configFile = "config_" + currentEnv + ".properties";
            
            InputStream is = configreader.class
                    .getClassLoader()
                    .getResourceAsStream(configFile);

            // ✅ Fallback to default config.properties if env-specific not found
            if (is == null) {
                System.err.println("⚠️ Config file not found: " + configFile);
                System.err.println("Falling back to config.properties");
                is = configreader.class
                        .getClassLoader()
                        .getResourceAsStream("config.properties");
            }

            if (is == null) {
                throw new RuntimeException("No config file found! Ensure config-" + currentEnv + ".properties exists in src/test/resources/");
            }

            props.load(is);
            System.out.println("✅ Loaded: " + configFile + " | Environment: " + currentEnv.toUpperCase());
            
            is.close();
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config file for environment: " + currentEnv, e);
        }
    }

    public static String get(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config-" + currentEnv + ".properties");
        }
        return value;
    }

    // ✅ Optional: Get with default value
    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    // ✅ Optional: Get current environment
    public static String getEnvironment() {
        return currentEnv.toUpperCase();
    }
    
}