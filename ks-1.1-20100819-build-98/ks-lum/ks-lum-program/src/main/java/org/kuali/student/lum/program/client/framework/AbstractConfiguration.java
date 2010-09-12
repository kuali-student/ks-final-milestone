package org.kuali.student.lum.program.client.framework;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;

/**
 * Skeletal implementation of {@link Configuration}
 *
 * @author Igor
 */
public abstract class AbstractConfiguration<T extends Configurer> implements Configuration<T> {

    protected T configurer;

    public void setConfigurer(T configurer) {
        this.configurer = configurer;
    }
}
