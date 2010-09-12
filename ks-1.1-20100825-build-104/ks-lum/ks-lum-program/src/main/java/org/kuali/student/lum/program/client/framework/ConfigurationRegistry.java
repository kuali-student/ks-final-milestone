package org.kuali.student.lum.program.client.framework;

import java.util.HashMap;

/**
 * @author Igor
 */
public class ConfigurationRegistry {

    private static HashMap<String, ConfigurationManager> configurationManagerMap = new HashMap<String, ConfigurationManager>();

    public static void register(String key, ConfigurationManager configurationManager) {
        configurationManagerMap.put(key, configurationManager);
    }

    public static void remove(String key) {
        configurationManagerMap.remove(key);
    }

    public static ConfigurationManager get(String key) {
        return configurationManagerMap.get(key);
    }
}
