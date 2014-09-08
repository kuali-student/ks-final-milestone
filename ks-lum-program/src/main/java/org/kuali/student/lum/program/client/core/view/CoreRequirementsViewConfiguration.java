package org.kuali.student.lum.program.client.core.view;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.core.CoreEditableHeader;
import org.kuali.student.lum.program.client.core.CoreManager;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsViewController;

public class CoreRequirementsViewConfiguration extends AbstractControllerConfiguration {

    private ProgramRequirementsViewController progReqcontroller;

    public CoreRequirementsViewConfiguration(Configurer configurer, boolean special) {
        this.setConfigurer(configurer);
        progReqcontroller = new ProgramRequirementsViewController(controller, CoreManager.getEventBus(),
                                    getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_REQUIREMENTS), ProgramSections.PROGRAM_REQUIREMENTS_VIEW,
                                    true, (special ? new CoreEditableHeader(getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_REQUIREMENTS), 
                                            ProgramSections.PROGRAM_REQUIREMENTS_EDIT) : null));
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