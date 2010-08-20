package org.kuali.student.lum.program.client.view;

import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.framework.AbstractSectionConfiguration;

/**
 * @author Igor
 */
public class ViewAllSectionConfiguration extends AbstractSectionConfiguration<ProgramViewConfigurer> {

    public ViewAllSectionConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.VIEW_ALL, "View All", ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {

    }
}
