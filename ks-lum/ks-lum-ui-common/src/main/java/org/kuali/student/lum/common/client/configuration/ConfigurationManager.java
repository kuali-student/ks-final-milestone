package org.kuali.student.lum.common.client.configuration;

import java.util.ArrayList;

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

    public void registerConfiguration(Configuration configuration) {
        configurations.add(configuration);
    }

    public ArrayList<Configuration> getConfigurations() {
        return configurations;
    }
}