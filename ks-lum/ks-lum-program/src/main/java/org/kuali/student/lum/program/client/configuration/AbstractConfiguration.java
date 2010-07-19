package org.kuali.student.lum.program.client.configuration;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.Configurer;

/**
 * @author Igor
 */
public abstract class AbstractConfiguration<T> implements Configuration<T>{

    protected T configurer;

    public void setConfigurer(T configurer) {
        this.configurer = configurer;
    }
}
