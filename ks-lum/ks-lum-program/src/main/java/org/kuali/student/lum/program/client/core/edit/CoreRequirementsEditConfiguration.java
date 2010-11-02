package org.kuali.student.lum.program.client.core.edit;

import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.lum.common.client.configuration.AbstractConfiguration;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsViewController;

/**
 * @author Igor
 */
public class CoreRequirementsEditConfiguration extends AbstractConfiguration {

    private ProgramController parentController;
    private ProgramRequirementsViewController progReqcontroller;

    @Override
    public View getView() {
        progReqcontroller = new ProgramRequirementsViewController(parentController, ProgramProperties.get().program_menu_sections_requirements(), ProgramSections.PROGRAM_REQUIREMENTS_EDIT, false);
        return progReqcontroller;
    }

    public void setViewController(ProgramController controller) {
        this.parentController = controller;
        if (progReqcontroller != null) {
            progReqcontroller.setParentController(controller);
        }
    }

    @Override
    public Widget asWidget() {
        return parentController;
    }

    @Override
    public Enum<?> getName() {
        return ProgramSections.PROGRAM_REQUIREMENTS_EDIT;
    }
}
