package org.kuali.student.lum.program.client.bacc.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.common.client.widgets.DropdownList;
import org.kuali.student.lum.program.client.bacc.CredentialController;
import org.kuali.student.lum.program.client.major.ActionType;

/**
 * @author Igor
 */
public class BaccViewController extends CredentialController {

    private DropdownList actionBox = new DropdownList(ActionType.getValues());

    /**
     * Constructor.
     *
     * @param programModel
     */
    public BaccViewController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(programModel, viewContext, eventBus);
        configurer = GWT.create(ViewBaccConfigurer.class);
    }

    @Override
    protected void configureView() {
        super.configureView();
        addContentWidget(actionBox);
        initialized = true;
    }
}
