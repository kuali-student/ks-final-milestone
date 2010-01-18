package org.kuali.student.lum.ui.requirements.client.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcService;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcServiceAsync;
import org.kuali.student.lum.ui.requirements.client.view.CourseRequisiteSummaryView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CourseReqSummaryManager extends Controller {
    private RequirementsRpcServiceAsync requirementsRpcServiceAsync = GWT.create(RequirementsRpcService.class);
    public enum CourseReqSummaryViews {
        RULES_SUMMARY
    }

    //controller's widgets
    // TODO define the views here
    private final SimplePanel mainPanel = new SimplePanel();
    private final SimplePanel viewPanel = new SimplePanel();
    private final CourseRequisiteSummaryView courseRequisiteSummaryView = new CourseRequisiteSummaryView(this);
    
    //controller's data
//    private Model<RuleInfo> ruleInfo;                      //contains all rules, each rule has its own RuleInfo object
//    private Model<ReqComponentVO>  selectedReqCompVO;    
//    private Model<ReqComponentTypeInfo> reqComponentTypes; //all requirements type info (fields and NL templates) for given statement type
    private String luStatementType = "unknown";             //type of rule that is being edited
    private Map<String, String> cluSetsData = new HashMap<String, String>(); 

    public CourseReqSummaryManager(VerticalPanel displayPanel) {
        super();       
        
        if (displayPanel == null) {        
            super.initWidget(viewPanel);
            viewPanel.add(mainPanel);
        } else {
            displayPanel.add(mainPanel);
        }
        
//        this.ruleInfo = null;
        resetReqCompVOModel();
        loadSearchBoxData();
    }

    @Override
    protected void onLoad() {
        //showDefaultView();   instead of showing default view every time user comes back, just show screen user left of last time
    }

    // controller operations
    @Override
    @SuppressWarnings("unchecked")
    public void requestModel(Class modelType, ModelRequestCallback callback) {
//        if (modelType.equals(RuleInfo.class)) {
//            
//            //pass back only rule corresponding to the user selected rule type
//            Model<RuleInfo> ruleInfoGivenType = new Model<RuleInfo>();
//            ruleInfoGivenType.add(getRuleInfo(luStatementType));                        
//            callback.onModelReady(ruleInfoGivenType);
//        } else if (modelType.equals(ReqComponentTypeInfo.class)) {
//          //  if (reqComponentTypes == null) {
//                retrieveModelData(ReqComponentTypeInfo.class, callback);
//          //  }
//          //  else {
//          //      callback.onModelReady(reqComponentTypes);
//          //  }
//        } else if (modelType.equals(ReqComponentVO.class)) {
//            callback.onModelReady(selectedReqCompVO);
//        }
//        else {
            super.requestModel(modelType, callback);
//        }
    }
    
    private RuleInfo getRuleInfo(String luStatementTypeKey) {
//        if (ruleInfo.getValues() != null && !ruleInfo.getValues().isEmpty()) {
//            for (RuleInfo oneRuleInfo : ruleInfo.getValues()) {
//                if (oneRuleInfo.getLuStatementTypeKey().equals(luStatementTypeKey)) {
//                    return oneRuleInfo;
//                }                
//            }
//        } 
        return null;
    }    

    @Override
    public void renderView(View view) {
        // in this case we know that all of our widgets are composites
        // but we could do view specific rendering, e.g. show a lightbox, etc
        mainPanel.setWidget((ViewComposite) view);
    }

    @Override
    protected void hideView(View view) {
        mainPanel.clear();
    }    

    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
        switch ((CourseReqSummaryViews) viewType) {
            case RULES_SUMMARY:
                return courseRequisiteSummaryView;
            default:
                return null;
        }
    }

    //TODO: should we retrieve requirement comp. types for all applicable lu statement types?
//    public void retrieveModelData(Class<? extends Idable> modelType, final ModelRequestCallback<ReqComponentTypeInfo> callback) {
//        
//        if (luStatementType == null) {
//            throw new IllegalArgumentException();
//        }
//        
//        System.out.println("Retrieving req. comp. types: " + luStatementType);
//        
//        if (modelType.equals(ReqComponentTypeInfo.class)) {        
//            requirementsRpcServiceAsync.getReqComponentTypesForLuStatementType(luStatementType, new AsyncCallback<List<ReqComponentTypeInfo>>() {
//                public void onFailure(Throwable caught) {
//                    Window.alert(caught.getMessage());
//                    // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
//                }
//    
//                public void onSuccess(final List<ReqComponentTypeInfo> reqComponentTypeInfoList) {  
//                    reqComponentTypes = new Model<ReqComponentTypeInfo>();
//                    for (ReqComponentTypeInfo reqCompInfo : reqComponentTypeInfoList) {
//                        reqComponentTypes.add(reqCompInfo);
//                    }      
//                    callback.onModelReady(reqComponentTypes);                                   
//                }
//            });  
//        }
//    }

    private void loadSearchBoxData() {        
        cluSetsData.put("CLUSET-NL-3", "CLUSET-NL-3");
        cluSetsData.put("CLUSET-NL-2", "CLUSET-NL-2");
        cluSetsData.put("CLUSET-NL-1", "CLUSET-NL-1");              
    }
    
    public Class<? extends Enum<?>> getViewsEnum() {
        return CourseReqSummaryViews.class;
    }
    
    private void resetReqCompVOModel () {
//        this.selectedReqCompVO = new Model<ReqComponentVO>();
    }
    
    public SimplePanel getMainPanel() {
        return mainPanel;
    }
    
    public String getLuStatementType() {
        return luStatementType;
    }

    public void setLuStatementType(String luStatementType) {
        this.luStatementType = luStatementType;
    }

    @Override
    public void showDefaultView(Callback<Boolean> onReadyCallback) {
        // TODO Auto-generated method stub
        
    }
}
