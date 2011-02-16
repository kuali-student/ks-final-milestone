package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.MajorEditableHeader;
import org.kuali.student.lum.program.client.major.MajorManager;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsViewController;

public class ProgramRequirementsViewConfiguration extends AbstractControllerConfiguration {

    private ProgramRequirementsViewController progReqcontroller;

    public ProgramRequirementsViewConfiguration(boolean special) {
        progReqcontroller = new ProgramRequirementsViewController(controller, MajorManager.getEventBus(), 
                                    ProgramProperties.get().program_menu_sections_requirements(), ProgramSections.PROGRAM_REQUIREMENTS_VIEW, true,
                                    (special ? new MajorEditableHeader(ProgramProperties.get().program_menu_sections_requirements(), ProgramSections.PROGRAM_REQUIREMENTS_EDIT) : null));
        rootSection = progReqcontroller.getProgramRequirementsView();
    }

      public ProgramRequirementsViewConfiguration(boolean special, boolean reloadRequirements) {
        progReqcontroller = new ProgramRequirementsViewController(controller, MajorManager.getEventBus(),
                                    ProgramProperties.get().program_menu_sections_requirements(), ProgramSections.PROGRAM_REQUIREMENTS_VIEW, true,
                                    (special ? new MajorEditableHeader(ProgramProperties.get().program_menu_sections_requirements(), ProgramSections.PROGRAM_REQUIREMENTS_EDIT) : null), reloadRequirements);
        rootSection = progReqcontroller.getProgramRequirementsView();
    }

    @Override
    protected void buildLayout() {
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
        if (progReqcontroller != null) {
            progReqcontroller.setParentController(controller);
        }
    }

    public ProgramRequirementsViewController getProgReqcontroller() {
        return progReqcontroller;
    }
}
