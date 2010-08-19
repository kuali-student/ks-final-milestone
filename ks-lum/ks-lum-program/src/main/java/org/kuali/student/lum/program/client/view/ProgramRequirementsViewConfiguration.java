package org.kuali.student.lum.program.client.view;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.framework.AbstractConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsSummaryView;

public class ProgramRequirementsViewConfiguration extends AbstractConfiguration<ProgramViewConfigurer> {

    @Override
    public View getView() {
        //no name for the view so that breadcrumbs do not extra link
        List<String> programRequirements = null; // TODO retrieve a list of program requirements
        return new ProgramRequirementsSummaryView(null, ProgramSections.PROGRAM_DETAILS_VIEW,
                                 ProgramProperties.get().program_menu_sections_requirements(), ProgramConstants.PROGRAM_MODEL_ID, programRequirements);
    }
}
