package org.kuali.student.lum.program.client.core.view;

import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsViewController;

/**
 * @author Igor
 */
public class CoreRequirementsViewConfiguration extends AbstractSectionConfiguration {

    private ProgramController parentController;
    private ProgramRequirementsViewController progReqcontroller;

    public CoreRequirementsViewConfiguration() {
        progReqcontroller = new ProgramRequirementsViewController(parentController, ProgramProperties.get().program_menu_sections_requirements(),
                ProgramSections.PROGRAM_REQUIREMENTS_VIEW, true);
        rootSection = progReqcontroller.getProgramRequirementsView();
    }

    @Override
    protected void buildLayout() {
    }

    public void setViewController(ProgramController controller) {
        this.parentController = controller;
        if (progReqcontroller != null) {
            progReqcontroller.setParentController(controller);
        }
    }
}