package org.kuali.student.lum.program.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Hyperlink;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.ProgramController;


public class ProgramViewController extends ProgramController {

    private Hyperlink switchToEditButton = new Hyperlink("Edit", "/HOME/CURRICULUM_HOME/PROGRAM_EDIT");

    public ProgramViewController(DataModel programModel) {
        super(programModel);
        configurer = GWT.create(ProgramViewConfigurer.class);
        addContentWidget(switchToEditButton);
    }
}
