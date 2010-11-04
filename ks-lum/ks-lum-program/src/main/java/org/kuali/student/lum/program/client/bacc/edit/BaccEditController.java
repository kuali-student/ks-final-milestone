package org.kuali.student.lum.program.client.bacc.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.bacc.CredentialController;

/**
 * @author Igor
 */
public class BaccEditController extends CredentialController {

    /**
     * Constructor.
     *
     * @param programModel
     */
    public BaccEditController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(programModel, viewContext, eventBus);
        configurer = GWT.create(EditBaccConfigurer.class);
    }
}
