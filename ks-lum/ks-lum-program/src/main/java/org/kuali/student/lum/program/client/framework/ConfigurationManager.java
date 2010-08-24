package org.kuali.student.lum.program.client.framework;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class manages configurations. We should create a repository of all ConfigurationManagers and let universities
 * register new configuration, remove already defined configuration or change the order in which they appear on the
 * ui.
 *
 * @author Igor
 * @see Configuration
 * @see AbstractConfiguration
 */
public class ConfigurationManager<T extends Configurer> {

    private ArrayList<Configuration<T>> configurations = new ArrayList<Configuration<T>>();

    private HashMap<String, Configuration<T>> configurationMap = new HashMap<String, Configuration<T>>();

    private T configurer;

    public ConfigurationManager(T configurer) {
        this.configurer = configurer;
    }

    public void registerConfiguration(Configuration<T> configuration) {
        configurations.add(configuration);
        configurationMap.put(configuration.getClass().getName(), configuration);
        setConfigurerOn(configuration);
    }

    public void removeConfiguration(Configuration<T> configuration) {
        configurations.remove(configuration);
        configurationMap.remove(configuration.getClass().getName());
    }

    public Configuration<T> getConfiguration(String name) {
        return configurationMap.get(name);
    }

    public ArrayList<Configuration<T>> getConfigurations() {
        return configurations;
    }

    private void setConfigurerOn(Configuration<T> configuration) {
        configuration.setConfigurer(configurer);
    }
}