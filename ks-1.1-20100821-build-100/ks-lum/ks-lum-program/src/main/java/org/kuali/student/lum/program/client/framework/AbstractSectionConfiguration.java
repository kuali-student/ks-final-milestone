package org.kuali.student.lum.program.client.framework;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.View;

/**
 * @author Igor
 */
public abstract class AbstractSectionConfiguration<T extends Configurer> extends AbstractConfiguration<T> {

    protected SectionView rootSection;

    @Override
    public View getView() {
        buildLayout();
        return rootSection;
    }

    protected abstract void buildLayout();
}
