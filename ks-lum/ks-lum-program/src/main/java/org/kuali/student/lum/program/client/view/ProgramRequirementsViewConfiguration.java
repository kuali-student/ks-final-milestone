package org.kuali.student.lum.program.client.view;

import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsSummaryView;

import java.util.List;

public class ProgramRequirementsViewConfiguration extends AbstractSectionConfiguration {

    public ProgramRequirementsViewConfiguration() {
        List<String> programRequirements = null;
        rootSection = new ProgramRequirementsSummaryView(null, ProgramSections.PROGRAM_REQUIREMENTS_VIEW,
                ProgramProperties.get().program_menu_sections_requirements(), ProgramConstants.PROGRAM_MODEL_ID, programRequirements);
    }

    @Override
    protected void buildLayout() {

    }
}
