package org.kuali.student.lum.program.client;

import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.edit.ProgramEditController;
import org.kuali.student.lum.program.client.view.ProgramViewController;

/**
 * 
 * @author Igor
 */
public class ProgramManager {

    private ProgramViewController programViewController;

    private ProgramEditController programEditController;

    protected final DataModel programModel;

    public ProgramManager() {
        programModel = new DataModel();
    }

    public ProgramViewController getProgramViewController() {
        if (programViewController == null) {
            programViewController = new ProgramViewController(programModel);
        }
        return programViewController;
    }

    public ProgramEditController getProgramEditController() {
        if (programEditController == null) {
            programEditController = new ProgramEditController(programModel);
        }
        return programEditController;
    }
}
