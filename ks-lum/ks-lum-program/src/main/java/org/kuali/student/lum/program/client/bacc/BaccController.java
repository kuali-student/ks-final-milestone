package org.kuali.student.lum.program.client.bacc;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.rpc.CredentialProgramRpcService;
import org.kuali.student.lum.program.client.rpc.ProgramRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;

/**
 * @author Igor
 */
public class BaccController extends ProgramController{
    /**
     * Constructor.
     *
     * @param programModel
     */
    public BaccController(String name, DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(name, programModel, viewContext, eventBus);
    }

    /**
     * Create a ProgramRpcServiceAsync (or subclass) appropriate for this Controller
     */
    @Override
    protected ProgramRpcServiceAsync createProgramRemoteService() {
        return GWT.create(CredentialProgramRpcService.class);
    }
}
