package org.kuali.student.lum.program.client.major.edit;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.MajorManager;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsViewController;

import com.google.gwt.user.client.ui.Widget;

public class ProgramRequirementsEditConfiguration extends AbstractControllerConfiguration {

    private ProgramRequirementsViewController progReqcontroller;
    
    

    public ProgramRequirementsEditConfiguration(Configurer configurer) {
        super();
        this.setConfigurer(configurer);
    }

    @Override
    public View getView() {
        progReqcontroller = new ProgramRequirementsViewController(controller, MajorManager.getEventBus(), 
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
