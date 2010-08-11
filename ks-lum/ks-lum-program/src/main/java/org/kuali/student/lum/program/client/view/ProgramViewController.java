package org.kuali.student.lum.program.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.edit.ProgramEditConfigurer;


public class ProgramViewController extends ProgramController {

    private KSButton switchToEditButton = new KSButton("Switch to edit");

    public ProgramViewController() {
        configurer = GWT.create(ProgramViewConfigurer.class);
        addContentWidget(switchToEditButton);
        initHandlers();
    }

    private void initHandlers() {
        switchToEditButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Application.navigate("/HOME/CURRICULUM_HOME/PROGRAM_EDIT", getViewContext());
            }
        });
    }
}
