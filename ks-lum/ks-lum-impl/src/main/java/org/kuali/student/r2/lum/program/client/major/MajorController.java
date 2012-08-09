package org.kuali.student.lum.program.client.major;

import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.widgets.ProgramSideBar;

/**
 * @author Igor
 */
public class MajorController extends ProgramController {

    /**
     * Constructor.
     *
     * @param programModel
     */
    public MajorController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super("Major", programModel, viewContext, eventBus);
        sideBar = new ProgramSideBar(eventBus, ProgramSideBar.Type.MAJOR);
    }

    @Override
    protected void configureView() {
        super.configureView();
        addContentWidget(createCommentPanel());
    }
}
