package org.kuali.student.lum.program.client.configuration.base;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.mvc.View;

/**
 * Should be implemented by classes that want to configure the main view using configurer.
 *
 * @author Igor
 */
public interface Configuration<T extends Configurer> {
    void setConfigurer(T configurer);

    View getView();
}
