package org.kuali.student.lum.program.client.configuration;

import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.lum.program.client.ProgramConfigurer;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.configuration.base.AbstractConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class RequirementsConfiguration extends AbstractConfiguration<ProgramConfigurer> {
    @Override
    public View getView() {
        return new VerticalSectionView(ProgramSections.PROGRAM_REQUIREMENTS_VIEW, ProgramProperties.get().program_menu_sections_requirements(), ProgramController.PROGRAM_MODEL_ID);
    }
}
