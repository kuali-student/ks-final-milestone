package org.kuali.student.lum.program.client.core.edit;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.core.CoreManager;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsViewController;

import com.google.gwt.user.client.ui.Widget;

public class CoreRequirementsEditConfiguration extends AbstractControllerConfiguration {

    private ProgramRequirementsViewController progReqcontroller;

    @Override
    public View getView() {
        progReqcontroller = new ProgramRequirementsViewController(controller, CoreManager.getEventBus(), 
                                    ProgramProperties.get().program_menu_sections_requirements(), ProgramSections.PROGRAM_REQUIREMENTS_EDIT, false, null);
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
