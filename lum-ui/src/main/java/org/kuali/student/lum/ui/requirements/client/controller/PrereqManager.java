package org.kuali.student.lum.ui.requirements.client.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcService;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcServiceAsync;
import org.kuali.student.lum.ui.requirements.client.view.ClauseEditorView;
import org.kuali.student.lum.ui.requirements.client.view.ComplexView;
import org.kuali.student.lum.ui.requirements.client.view.RuleExpressionEditor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;

public class PrereqManager extends Controller {
    private RequirementsRpcServiceAsync requirementsRpcServiceAsync = GWT.create(RequirementsRpcService.class);
    
    public enum PrereqViews {
        SIMPLE, COMPLEX, CLAUSE_EDITOR, RULE_EXPRESSION_EDITOR
    }

    //controller's widgets
    private final SimplePanel mainPanel = new SimplePanel();
    private final SimplePanel viewPanel = new SimplePanel();
    private final ComplexView complexView = new ComplexView(this);
    private final ClauseEditorView clauseEditorView = new ClauseEditorView(this);
    private final RuleExpressionEditor ruleExpressionEditorView = new RuleExpressionEditor(this);
    
    //controller's data
    private final Model<PrereqInfo> prereqInfo;
    private final Model<ReqComponentVO>  selectedReqCompVO;    
    private Model<ReqComponentTypeInfo> reqComponentTypes;  
    private Map<String, String> clusData = new HashMap<String, String>(); 
    private Map<String, String> cluSetsData = new HashMap<String, String>(); 
    
    public PrereqManager(Model<PrereqInfo> prereqInfo) {
        super();
        super.initWidget(viewPanel);
        viewPanel.add(mainPanel);              
        this.prereqInfo = prereqInfo;
        this.selectedReqCompVO = new Model<ReqComponentVO>();
        loadData();
    }

    public SimplePanel getMainPanel() {
        return mainPanel;
    }
    
    @Override
    protected void onLoad() {
        showDefaultView();
    }

    // controller operations
    @Override
    public void renderView(View view) {
        // in this case we know that all of our widgets are composites
        // but we could do view specific rendering, e.g. show a lightbox, etc
        mainPanel.setWidget((ViewComposite) view);
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
        } else if (modelType.equals(ReqComponentVO.class)) {
            callback.onModelReady(selectedReqCompVO);
        }
        else {
            super.requestModel(modelType, callback);
        }
    }

    @Override
    public void showDefaultView() {
        showView(PrereqViews.COMPLEX);
    }

    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
        switch ((PrereqViews) viewType) {
            case COMPLEX:
                return complexView;
            case CLAUSE_EDITOR:    
                clauseEditorView.setClusData(clusData);
                clauseEditorView.setCluSetsData(cluSetsData);
                return clauseEditorView;
            case RULE_EXPRESSION_EDITOR:
                return ruleExpressionEditorView;
            default:
                return null;
        }
    }

    public void retrieveModelData(Class<? extends Idable> modelType, final ModelRequestCallback<ReqComponentTypeInfo> callback) {
        
        if (modelType.equals(ReqComponentTypeInfo.class)) {        
            requirementsRpcServiceAsync.getReqComponentTypesForLuStatementType("kuali.luStatementType.prereqAcademicReadiness", new AsyncCallback<List<ReqComponentTypeInfo>>() {
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                    // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                }
    
                public void onSuccess(final List<ReqComponentTypeInfo> reqComponentTypeInfoList) {  
                    reqComponentTypes = new Model<ReqComponentTypeInfo>();
                    for (ReqComponentTypeInfo reqCompInfo : reqComponentTypeInfoList) {
                        reqComponentTypes.add(reqCompInfo);
                    }      
                    callback.onModelReady(reqComponentTypes);                                   
                }
            });  
        }
    }

    private void loadData() {
        requirementsRpcServiceAsync.getAllClus(new AsyncCallback<Map<String, String>>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
            }

            public void onSuccess(final Map<String, String> cluData) {  
                clusData = cluData;                   
            }
        });
        
        cluSetsData.put("CLUSET-NL-3", "CLUSET-NL-3");
        cluSetsData.put("CLUSET-NL-2", "CLUSET-NL-2");
        cluSetsData.put("CLUSET-NL-1", "CLUSET-NL-1");

        
        /*
        RequirementsService.Util.getInstance().getAllClusets(new AsyncCallback<Map<String, String>>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
            }

            public void onSuccess(final Map<String, String> cluSetData) {  
                cluSetsData = cluSetData;                   
            }
        }); */         
    }
    
    public Class<? extends Enum<?>> getViewsEnum() {
        return PrereqViews.class;
    }
}
