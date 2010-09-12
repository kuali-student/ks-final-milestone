package org.kuali.student.lum.program.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Hyperlink;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.lum.program.client.ProgramController;


public class ProgramViewController extends ProgramController {

    private Anchor switchToEditButton = new Anchor("Edit");

    /**
     * Constructor.
     *
     * @param programModel
     */
    public ProgramViewController(DataModel programModel, ViewContext viewContext) {
        super(programModel, viewContext);
        configurer = GWT.create(ProgramViewConfigurer.class);
        addContentWidget(switchToEditButton);
        switchToEditButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                 HistoryManager.navigate("/HOME/CURRICULUM_HOME/PROGRAM_EDIT", getViewContext());
            }
        });
    }

    @Override
    protected void configureView() {
        super.configureView();
        initialized = true;
    }
}
