package org.kuali.student.lum.program.client.view;

import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsViewController;

public class ProgramRequirementsViewConfiguration extends AbstractSectionConfiguration {

    private ProgramController viewController;

    public ProgramRequirementsViewConfiguration() {
        ProgramRequirementsViewController controller = new ProgramRequirementsViewController(viewController, ProgramProperties.get().program_menu_sections_requirements(),
                                                                                                    ProgramSections.PROGRAM_REQUIREMENTS_VIEW, true);
        rootSection = controller.getProgramRequirementsView();
    }

    @Override
    protected void buildLayout() {
    }

    public void setViewController(ProgramController viewController) {
        this.viewController = viewController;
    }
}
