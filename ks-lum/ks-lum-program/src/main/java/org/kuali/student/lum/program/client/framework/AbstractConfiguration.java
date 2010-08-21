package org.kuali.student.lum.program.client.framework;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.View;

/**
 * Skeletal implementation of {@link Configuration}.
 *
 * @author Igor
 */
public abstract class AbstractConfiguration<T extends Configurer> implements Configuration<T> {

    protected T configurer;

    public void setConfigurer(T configurer) {
        this.configurer = configurer;
    }
}
