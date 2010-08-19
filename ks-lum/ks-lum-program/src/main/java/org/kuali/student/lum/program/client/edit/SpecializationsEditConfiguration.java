package org.kuali.student.lum.program.client.edit;

import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.configuration.base.AbstractConfiguration;
import org.kuali.student.lum.program.client.configuration.base.Configuration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.view.ProgramViewConfigurer;

/**
 * @author Igor
 */
public class SpecializationsEditConfiguration extends AbstractConfiguration<ProgramEditConfigurer> {

    @Override
    public View getView() {
        return new VerticalSectionView(ProgramSections.SPECIALIZATIONS, ProgramProperties.get().program_menu_sections_specializations(), ProgramConstants.PROGRAM_MODEL_ID);
    }
}
