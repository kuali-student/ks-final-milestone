package org.kuali.student.lum.program.client.credential.view;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.credential.CredentialEditableHeader;
import org.kuali.student.lum.program.client.credential.CredentialManager;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsViewController;

public class CredentialRequirementsViewConfiguration extends AbstractControllerConfiguration {

    private ProgramRequirementsViewController progReqcontroller;

    public CredentialRequirementsViewConfiguration(Configurer configurer, boolean special) {
        this.setConfigurer(configurer);
        progReqcontroller = new ProgramRequirementsViewController(controller, CredentialManager.getEventBus(),
                                    getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_REQUIREMENTS), ProgramSections.PROGRAM_REQUIREMENTS_VIEW, true,
                                    (special ? new CredentialEditableHeader(getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_REQUIREMENTS), ProgramSections.PROGRAM_REQUIREMENTS_EDIT) : null)); 
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

