package org.kuali.student.lum.program.client.bacc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.rpc.CredentialProgramRpcService;
import org.kuali.student.lum.program.client.rpc.MajorDisciplineRpcServiceAsync;
import org.kuali.student.lum.program.client.widgets.ProgramSideBar;

/**
 * @author Igor
 */
public class CredentialController extends ProgramController {
    /**
     * Constructor.
     *
     * @param programModel
     */
    public CredentialController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super("Bacc", programModel, viewContext, eventBus);
        sideBar = new ProgramSideBar(eventBus, ProgramSideBar.Type.CREDENTIAL);
    }

    /**
     * Create a ProgramRpcServiceAsync (or subclass) appropriate for this Controller
     */
    @Override
    protected MajorDisciplineRpcServiceAsync createProgramRemoteService() {
        return GWT.create(CredentialProgramRpcService.class);
    }

    @Override
    protected void configureView() {
        super.configureView();
        addContentWidget(createCommentPanel());
    }
}
