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

    private Configurer configurer;

    public ConfigurationManager(Configurer configurer) {
        this.configurer = configurer;
    }

    public void registerConfiguration(Configuration configuration) {
        configurations.add(configuration);
        setConfigurerOn(configuration);
    }

    public ArrayList<Configuration> getConfigurations() {
        return configurations;
    }

    private void setConfigurerOn(Configuration configuration) {
        configuration.setConfigurer(configurer);
    }
}