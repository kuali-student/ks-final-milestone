package org.kuali.student.lum.program.client.variation;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
     */
    public VariationController(String name, DataModel programModel, ViewContext viewContext) {
        super("", programModel, viewContext);
        this.name = name;
    }

    @Override
    protected void configureView() {
        super.configureView();
        this.setContentTitle("Specialization of " + getProgramName());
        this.addContentWidget(createParentAnchor());
        initialized = true;
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
