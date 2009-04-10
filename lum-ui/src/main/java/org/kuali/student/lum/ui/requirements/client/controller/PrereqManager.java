package org.kuali.student.lum.ui.requirements.client.controller;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.mvc.events.LogoutEvent;
import org.kuali.student.common.ui.client.mvc.events.LogoutHandler;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsService;
import org.kuali.student.lum.ui.requirements.client.view.ClauseEditorView;
import org.kuali.student.lum.ui.requirements.client.view.ComplexView;
import org.kuali.student.lum.ui.requirements.client.view.SearchView;
import org.kuali.student.lum.ui.requirements.client.view.SimpleView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;

public class PrereqManager extends Controller {
    public enum PrereqViews {
        SIMPLE, COMPLEX, SEARCH, CLAUSE_EDITOR
    }

    private final SimplePanel viewPanel = new SimplePanel();
    private final SimpleView simpleView = new SimpleView(this);
    private final ComplexView complexView = new ComplexView(this);
    private final SearchView searchView = new SearchView(this);
    private final ClauseEditorView clauseEditorView = new ClauseEditorView(this);
    private final Model<PrereqInfo> prereqInfo;
    private Model<ReqComponentTypeInfo> reqComponentTypes;    

    public PrereqManager(Model<PrereqInfo> prereqInfo) {
        super();
        super.initWidget(viewPanel);
        this.prereqInfo = prereqInfo;
    }

    @Override
    protected void onLoad() {
        showDefaultView();
        // add event handler to show example of a nested controller listening for unchecked events
        addApplicationEventHandler(LogoutEvent.TYPE, new LogoutHandler() {
            public void onLogout(LogoutEvent event) {
                Window.alert("PrereqManager caught logout event");
            }
        });
    }

    // controller operations
    @Override
    public void renderView(View view) {
        // in this case we know that all of our widgets are composites
        // but we could do view specific rendering, e.g. show a lightbox, etc
        viewPanel.setWidget((ViewComposite) view);
    }

    @Override
    protected void hideView(View view) {
        viewPanel.clear();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void requestModel(Class<? extends Idable> modelType, ModelRequestCallback callback) {
        if (modelType.equals(PrereqInfo.class)) {
            callback.onModelReady(prereqInfo);
        } else if (modelType.equals(ReqComponentTypeInfo.class)) {
            if (reqComponentTypes == null) {
                retrieveModelData(ReqComponentTypeInfo.class, callback);
            }
            else {
                callback.onModelReady(reqComponentTypes);
            }
        } else {
            super.requestModel(modelType, callback);
        }
    }

    @Override
    public void showDefaultView() {
        showView(PrereqViews.SIMPLE);
    }

    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
        switch ((PrereqViews) viewType) {
            case SIMPLE:
                return simpleView;
            case COMPLEX:
                return complexView;
            case SEARCH:
                return searchView;
            case CLAUSE_EDITOR:
                clauseEditorView.setEditedReqComp(complexView.getSelectedReqComp());
                return clauseEditorView;
            default:
                return null;
        }
    }

    public void retrieveModelData(Class<? extends Idable> modelType, final ModelRequestCallback callback) {
        
        if (modelType.equals(ReqComponentTypeInfo.class)) {        
            RequirementsService.Util.getInstance().getReqComponentTypesForLuStatementType("kuali.luStatementType.prereqAcademicReadiness", new AsyncCallback<List<ReqComponentTypeInfo>>() {
                public void onFailure(Throwable caught) {
                    // just re-throw it and let the uncaught exception handler deal with it
                    Window.alert(caught.getMessage());
                    // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                }
    
                public void onSuccess(final List<ReqComponentTypeInfo> reqComponentTypeInfoList) {  
                    System.out.println("TEST: " + reqComponentTypeInfoList.size());
                    reqComponentTypes = new Model<ReqComponentTypeInfo>();
                    for (ReqComponentTypeInfo reqCompInfo : reqComponentTypeInfoList) {
                        reqComponentTypes.add(reqCompInfo);
                    }      
                    System.out.println("Printing model...");
                    //ComplexView.printModel(reqComponentTypes);
                    callback.onModelReady(reqComponentTypes);                                   
                }
            });  
        }
    }    
    
}
