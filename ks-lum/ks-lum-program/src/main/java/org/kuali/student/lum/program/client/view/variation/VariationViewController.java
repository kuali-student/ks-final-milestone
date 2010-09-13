package org.kuali.student.lum.program.client.view.variation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.lum.program.client.ProgramController;

public class VariationViewController extends ProgramController {

    private String name;

    public VariationViewController(String name, DataModel programModel, ViewContext viewContext) {
        super("Variations", programModel, viewContext);
        this.name = name;
        configurer = GWT.create(VariationViewConfigurer.class);
    }

    @Override
    protected void configureView() {
        super.configureView();
        this.setContentTitle("Specialization of " + getProgramName());
        //HTML parentLink = new HTML("Parent Program");
        //parentLink.setHTML("<br><b>Parent Program: </b><a href=\"" + parentLinkbaseURL + "&docId=" + mdId + "\"><b>" + mdTitle + "</b></a>");
        // parentLink.getElement().getStyle().setPaddingRight(20d, Style.Unit.PX);
        // parentLink.getElement().getStyle().setFontSize(3, Style.Unit.PX);
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
