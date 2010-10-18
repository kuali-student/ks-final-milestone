package org.kuali.student.lum.common.client.configuration;

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
public class ConfigurationManager {

    private ArrayList<Configuration> configurations = new ArrayList<Configuration>();

    private HashMap<Enum<?>, Configuration> configurationMap = new HashMap<Enum<?>, Configuration>();

    private Configurer configurer;

    public ConfigurationManager(Configurer configurer) {
        this.configurer = configurer;
        ConfigurationRegistry.register(configurer.getClass().getName(), this);
    }

    public void registerConfiguration(Configuration configuration) {
        configurations.add(configuration);
        configurationMap.put(configuration.getName(), configuration);
        setConfigurerOn(configuration);
    }

    public void removeConfiguration(Configuration configuration) {
        configurations.remove(configuration);
        configurationMap.remove(configuration.getName());
    }

    public Configuration getConfiguration(Enum<?> name) {
        return configurationMap.get(name);
    }

    public ArrayList<Configuration> getConfigurations() {
        return configurations;
    }

    private void setConfigurerOn(Configuration configuration) {
        configuration.setConfigurer(configurer);
    }
}