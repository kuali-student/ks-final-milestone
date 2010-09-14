package org.kuali.student.lum.common.client.configuration;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;

/**
 * Skeletal implementation of {@link Configuration}.
 *
 * @author Igor
 */
public abstract class AbstractConfiguration implements Configuration {

    protected Configurer configurer;

    public void setConfigurer(Configurer configurer) {
        this.configurer = configurer;
    }
}
