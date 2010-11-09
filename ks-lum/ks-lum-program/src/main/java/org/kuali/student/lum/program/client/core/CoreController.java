package org.kuali.student.lum.program.client.core;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.rpc.CoreProgramRpcService;
import org.kuali.student.lum.program.client.rpc.MajorDisciplineRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.lum.program.client.widgets.ProgramSideBar;

/**
 * @author Igor
 */
public class CoreController extends ProgramController{
    /**
     * Constructor.
     *
     * @param programModel
     */
    public CoreController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super("CoreProgram", programModel, viewContext, eventBus);
        sideBar = new ProgramSideBar(eventBus, ProgramSideBar.Type.CORE);
    }

    /**
     * Create a ProgramRpcServiceAsync (or subclass) appropriate for this Controller
     */
    @Override
    protected MajorDisciplineRpcServiceAsync createProgramRemoteService() {
        return GWT.create(CoreProgramRpcService.class);
    }

}
