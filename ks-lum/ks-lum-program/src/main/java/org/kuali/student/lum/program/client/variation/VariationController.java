package org.kuali.student.lum.program.client.variation;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.lum.program.client.ProgramController;

/**
 * @author Igor
 */
public class VariationController extends ProgramController {

    private String name;

    /**
     * Constructor.
     *
     * @param programModel
     * @param eventBus
     */
    public VariationController(String name, DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super("", programModel, viewContext, eventBus);
        this.name = name;
    }

    @Override
    protected void configureView() {
        setStatus();
        super.configureView();
        setContentTitle("Specialization of " + getProgramName());
        addContentWidget(createParentAnchor());
        addContentWidget(createCommentPanel());
    }

    private Widget createParentAnchor() {
        Anchor anchor = new Anchor("Parent Program: " + name);
        anchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                HistoryManager.navigate("/HOME/CURRICULUM_HOME/PROGRAM_VIEW", getViewContext());
            }
        });
        return anchor;
    }
}
