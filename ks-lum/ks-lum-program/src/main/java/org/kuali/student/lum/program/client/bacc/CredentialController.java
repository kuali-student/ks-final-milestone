package org.kuali.student.lum.program.client.bacc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.rpc.CredentialProgramRpcService;
import org.kuali.student.lum.program.client.rpc.ProgramRpcServiceAsync;

/**
 * @author Igor
 */
public class CredentialController extends ProgramController{
    /**
     * Constructor.
     *
     * @param programModel
     */
    public CredentialController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super("Bacc", programModel, viewContext, eventBus);
    }

    /**
     * Create a ProgramRpcServiceAsync (or subclass) appropriate for this Controller
     */
    @Override
    protected ProgramRpcServiceAsync createProgramRemoteService() {
        return GWT.create(CredentialProgramRpcService.class);
    }
}
