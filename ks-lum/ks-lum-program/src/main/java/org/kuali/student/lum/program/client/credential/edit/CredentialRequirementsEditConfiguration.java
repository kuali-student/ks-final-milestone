package org.kuali.student.lum.program.client.credential.edit;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.credential.CredentialManager;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsViewController;

import com.google.gwt.user.client.ui.Widget;

public class CredentialRequirementsEditConfiguration extends AbstractControllerConfiguration {

    private ProgramRequirementsViewController progReqcontroller;
    
    public CredentialRequirementsEditConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
    }

    @Override
    public View getView() {
        progReqcontroller = new ProgramRequirementsViewController(controller, CredentialManager.getEventBus(), 
                                    getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_REQUIREMENTS), ProgramSections.PROGRAM_REQUIREMENTS_EDIT, false, null);
        return progReqcontroller;
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

    @Override
    public Widget asWidget() {
        return controller;
    }

    @Override
    public Enum<?> getName() {
        return ProgramSections.PROGRAM_REQUIREMENTS_EDIT;
    }
}
