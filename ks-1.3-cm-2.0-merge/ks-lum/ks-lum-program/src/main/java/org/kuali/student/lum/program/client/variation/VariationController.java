package org.kuali.student.lum.program.client.variation;

import java.util.List;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.events.ModelLoadedEvent;
import org.kuali.student.lum.program.client.major.MajorController;
import org.kuali.student.lum.program.client.major.proposal.MajorProposalController;
import org.kuali.student.lum.program.client.rpc.AbstractCallback;
import org.kuali.student.lum.program.client.widgets.ProgramSideBar;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Igor
 */
public abstract class VariationController extends ProgramController {

    private String parentName;

    protected MajorController majorController;

    /**
     * Constructor.
     *
     * @param programModel
     * @param eventBus
     */
    public VariationController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus, MajorController majorController) {
        super("", programModel, viewContext, eventBus);
        this.parentName = majorController.getName();
        this.majorController = majorController;
        setName(getProgramName());
        sideBar = new ProgramSideBar(eventBus, ProgramSideBar.Type.MAJOR);
        sideBar.initialize(majorController);
    }

    @Override
    protected void configureView() {
        setStatus();
        super.configureView();
        setContentTitle(getProgramName());
        addContentWidget(createParentAnchor());
        addContentWidget(createCommentPanel());
    }

    private Widget createParentAnchor() {
        HorizontalPanel anchorPanel = new HorizontalPanel();
        Anchor anchor = new Anchor(parentName);
        anchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                navigateToParent();
            }
        });
        Label parentProgram = new Label(getLabel(ProgramMsgConstants.VARIATION_PARENTPROGRAM));
        parentProgram.addStyleName("parentProgram");
        anchorPanel.add(parentProgram);
        anchorPanel.add(anchor);
        return anchorPanel;
    }

    protected abstract void navigateToParent();

    @Override
    public String getProgramName() {
        String name = getStringProperty(ProgramConstants.LONG_TITLE);
        if (name == null) {
            return getLabel(ProgramMsgConstants.VARIATION_NEW);
        }
        return getLabel(ProgramMsgConstants.VARIATION_TITLE, name);
    }

    @Override
    public void collectBreadcrumbNames(List<String> names) {
    	String appLoc = "";
    	
    	if(!(majorController instanceof MajorProposalController))//programModel.get("isProposal") == null )
    		appLoc = AppLocations.Locations.VIEW_PROGRAM.getLocation();
    	else
    		appLoc = AppLocations.Locations.PROGRAM_PROPOSAL.getLocation();
        names.add(parentName + "@" + HistoryManager.appendContext(appLoc, getViewContext()));
        super.collectBreadcrumbNames(names);
    }

    /**
     * Loads data model from the server.
     *
     * @param callback we have to invoke this callback when model is loaded or failed.
     */
    @Override
    protected void loadModel(final ModelRequestCallback<DataModel> callback) {
        programRemoteService.getData(getViewContext().getId(), new AbstractCallback<Data>(getLabel(ProgramMsgConstants.COMMON_RETRIEVINGDATA)) {

            @Override
            public void onFailure(Throwable caught) {
                super.onFailure(caught);
                callback.onRequestFail(caught);
            }

            @Override
            public void onSuccess(Data result) {
                super.onSuccess(result);
                programModel.setRoot(result);
                callback.onModelReady(programModel);
                eventBus.fireEvent(new ModelLoadedEvent(programModel));
            }
        });
    }
}
