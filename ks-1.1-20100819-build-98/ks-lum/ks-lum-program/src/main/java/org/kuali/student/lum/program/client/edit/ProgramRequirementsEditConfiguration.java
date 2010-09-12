package org.kuali.student.lum.program.client.edit;

import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.framework.AbstractConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.requirements.RequirementsViewController;

/**
 *
 */
public class ProgramRequirementsEditConfiguration extends AbstractConfiguration<ProgramEditConfigurer> {

    private ProgramController viewController;

    @Override
    public View getView() {
        return new RequirementsViewController(viewController, ProgramProperties.get().program_menu_sections_requirements(), ProgramSections.PROGRAM_REQUIREMENTS_EDIT);  
    }

    public void setViewController(ProgramController viewController) {
        this.viewController = viewController;
    }
}
