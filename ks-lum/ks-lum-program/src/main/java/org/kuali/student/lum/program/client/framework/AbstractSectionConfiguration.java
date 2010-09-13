package org.kuali.student.lum.program.client.framework;

import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.View;

/**
 * @author Igor
 */
public abstract class AbstractSectionConfiguration extends AbstractConfiguration {

    protected SectionView rootSection;

    @Override
    public View getView() {
        buildLayout();
        return rootSection;
    }

    protected abstract void buildLayout();

    @Override
    public Widget asWidget() {
        return rootSection;
    }

    @Override
    public Enum<?> getName() {
        return rootSection.getViewEnum();
    }
}
