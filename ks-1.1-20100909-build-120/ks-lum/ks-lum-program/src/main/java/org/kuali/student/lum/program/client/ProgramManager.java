package org.kuali.student.lum.program.client;

import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.edit.ProgramEditController;
import org.kuali.student.lum.program.client.view.ProgramViewController;
import org.kuali.student.lum.program.client.view.variation.VariationViewController;

/**
 * @author Igor
 */
public class ProgramManager {

    private ProgramViewController programViewController;

    private ProgramEditController programEditController;
    
    private VariationViewController variationViewController;

    protected DataModel programModel;

    public ProgramManager() {
        programModel = new DataModel();
    }

    public ProgramViewController getProgramViewController() {
        if (programViewController == null) {
            programViewController = new ProgramViewController(programModel);
        }
        return programViewController;
    }

    public VariationViewController getVariationViewController() {
        if (variationViewController == null) {
        	variationViewController = new VariationViewController(programModel);
        }
        return variationViewController;
    }
    
    public ProgramEditController getProgramEditController(boolean create) {
        if (programEditController == null) {
            programEditController = new ProgramEditController(programModel);
        }
        /*
        if (create) {
            programEditController.getViewContext().setId(null);
            programEditController.programModel = new DataModel();
        }
        */
        return programEditController;
    }
}
