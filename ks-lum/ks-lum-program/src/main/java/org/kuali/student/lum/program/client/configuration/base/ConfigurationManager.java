package org.kuali.student.lum.program.client.configuration.base;

import com.google.gwt.core.client.GWT;
import org.kuali.student.common.ui.client.configurable.mvc.Configurer;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author Igor
 */
public class ConfigurationManager<T extends Configurer> {

    private LinkedHashMap<String, Configuration<T>> configurationMap = new LinkedHashMap<String, Configuration<T>>();

    private T configurer;

    public ConfigurationManager(T configurer) {
        this.configurer = configurer;
    }

    public void registerConfiguration(Class clazz) {
        Configuration<T> configuration = GWT.create(clazz);
        configurationMap.put(clazz.getName(), configuration);
        setConfigurerOn(configuration);
    }

    public void registerEditableConfiguration(Class clazz) {
        EditableConfiguration<T> editableConfiguration = GWT.create(clazz);
        configurationMap.put(clazz.getName(), editableConfiguration);
        setConfigurerOn(editableConfiguration);
    }

    public void removeConfiguration(Class clazz) {
        configurationMap.remove(clazz.getName());
    }

    private void setConfigurerOn(Configuration<T> configuration) {
        configuration.setConfigurer(configurer);
    }

    public ArrayList<Configuration<T>> getConfigurations() {
        return new ArrayList<Configuration<T>>(configurationMap.values());
    }
}
