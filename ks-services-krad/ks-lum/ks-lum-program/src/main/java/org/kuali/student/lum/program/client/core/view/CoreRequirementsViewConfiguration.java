package org.kuali.student.lum.program.client.core.view;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.core.CoreEditableHeader;
import org.kuali.student.lum.program.client.core.CoreManager;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsViewController;

public class CoreRequirementsViewConfiguration extends AbstractControllerConfiguration {

    private ProgramRequirementsViewController progReqcontroller;

    public CoreRequirementsViewConfiguration(boolean special) {
        progReqcontroller = new ProgramRequirementsViewController(controller, CoreManager.getEventBus(),
                                    ProgramProperties.get().program_menu_sections_requirements(), ProgramSections.PROGRAM_REQUIREMENTS_VIEW,
                                    true, (special ? new CoreEditableHeader(ProgramProperties.get().program_menu_sections_requirements(), ProgramSections.PROGRAM_REQUIREMENTS_EDIT) : null));
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
}