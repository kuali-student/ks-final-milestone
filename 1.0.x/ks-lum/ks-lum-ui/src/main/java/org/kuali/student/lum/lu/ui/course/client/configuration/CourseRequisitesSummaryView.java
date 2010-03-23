package org.kuali.student.lum.lu.ui.course.client.configuration;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcService;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcServiceAsync;
import org.kuali.student.lum.ui.requirements.client.view.CourseRequisiteView;
import org.kuali.student.lum.ui.requirements.client.view.RuleComponentEditorView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CourseRequisitesSummaryView extends SectionView {
    private RequirementsRpcServiceAsync requirementsRpcServiceAsync = GWT.create(RequirementsRpcService.class);
    
    protected final VerticalPanel mainPanel = new VerticalPanel();
    private boolean loaded = false;
    private SimplePanel prereqRuleTextPanel = new SimplePanel();
    private SimplePanel coreqRuleTextPanel = new SimplePanel();
    private SimplePanel antireqRuleTextPanel = new SimplePanel();
    private SimplePanel enrollRuleTextPanel = new SimplePanel(); 
    private List<RuleInfo> courseRules;                             //contains all rules belonging to this course
    private Controller theController; 

    public CourseRequisitesSummaryView(Enum<?> viewEnum, String name) {
        super(viewEnum, name);
        super.initWidget(mainPanel);
    }
    
    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
        Window.alert("CourseRequisitesSummary.beforeShow");
    }
    
    private void initUI() {
        mainPanel.clear();
        
        for (String statementType : CourseRequisiteView.applicableLuStatementTypes) {
            SimplePanel rulesTextPanel = getRulesTextPanel(statementType);
            String ruleTitle = getRuleTypeName(statementType);
            Label ruleLabel = new Label(ruleTitle + ": ");
            rulesTextPanel.setWidget(ruleLabel);
            mainPanel.add(rulesTextPanel);
        }
    }
    
    private void displayRules() {
        for (final String statementType : CourseRequisiteView.applicableLuStatementTypes) {
            final RuleInfo ruleInfo = getRuleInfo(statementType);
            if (ruleInfo != null) {
                requirementsRpcServiceAsync.getNaturalLanguageForStatementVO(
                        ruleInfo.getCluId(), ruleInfo.getStatementVO(), "KUALI.CATALOG",
                        RuleComponentEditorView.TEMLATE_LANGUAGE, new AsyncCallback<String>() {
                    public void onFailure(Throwable cause) {
                        GWT.log("Failed to get NL for " + ruleInfo.getCluId(), cause);
                        Window.alert("Failed to get NL for " + ruleInfo.getCluId());
                    }
                    
                    public void onSuccess(final String statementNaturalLanguage) { 
                        String ruleTitle = getRuleTypeName(statementType);
                        SimplePanel rulesTextPanel = getRulesTextPanel(statementType);
                        rulesTextPanel.clear();
                        rulesTextPanel.setWidget(new Label(ruleTitle + ": " + statementNaturalLanguage));
                    } 
                }); 
            }
        }
    }
    
    private SimplePanel getRulesTextPanel(String statementType) {
        if (statementType.contains("enroll")) return enrollRuleTextPanel;
        if (statementType.contains("prereq")) return prereqRuleTextPanel;
        if (statementType.contains("coreq")) return coreqRuleTextPanel;
        if (statementType.contains("antireq")) return antireqRuleTextPanel;   
        return new SimplePanel();
    }    
    
    private RuleInfo getRuleInfo(String statementType) {   
        if ((courseRules != null) && !courseRules.isEmpty()) {
            for (RuleInfo ruleInfo : courseRules) {
                if (ruleInfo.getStatementTypeKey() != null &&
                        ruleInfo.getStatementTypeKey().equals(statementType)) {                
                    return ruleInfo;
                }                
            }
        }     
        return null;
    }

    private String getRuleTypeName(String luStatementTypeKey) {
        if (luStatementTypeKey.contains("enroll")) return "Enrollment Restrictions";
        if (luStatementTypeKey.contains("prereq")) return "Prerequisites";
        if (luStatementTypeKey.contains("coreq")) return "Corequisites";
        if (luStatementTypeKey.contains("antireq")) return "Antirequisites";
        return "";
    }

    @Override
    public void clear() {
        mainPanel.clear();
    }

    @Override
    public void redraw() {
        if (getTheController() != null) {
            getTheController().requestModel(LuData.class, new ModelRequestCallback<DataModel>() {
                @Override
                public void onModelReady(DataModel model) {
                    LuData luData = (LuData)model.getRoot();
                    courseRules = luData.getRuleInfos();
                    initUI();
                    displayRules();
                }

                @Override
                public void onRequestFail(Throwable cause) {
                    GWT.log("Failed to get LuData DataModel", cause);
                    Window.alert("Failed to get LuData DataModel");
                }
            });
        }
    }
    
    
    public Controller getTheController() {
        return theController;
    }

    public void setTheController(Controller theController) {
        this.theController = theController;
    }

    @Override
    public void updateModel() {
        Window.alert("CourseRequisitesSummary.updateModel");
    }
    
    @Override
    protected void addFieldToLayout(FieldDescriptor f) {
        // TODO Auto-generated method stub  
    }

    @Override
    protected void addSectionToLayout(BaseSection s) {
        // TODO Auto-generated method stub      
    }

    @Override
    protected void addWidgetToLayout(Widget w) {
        // TODO Auto-generated method stub      
    }
    
}
