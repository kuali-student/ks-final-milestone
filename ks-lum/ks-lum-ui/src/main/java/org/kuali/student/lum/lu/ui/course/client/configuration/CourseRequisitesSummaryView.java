/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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
import org.kuali.student.lum.ui.requirements.client.view.RuleConstants;

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
            if (ruleInfo != null && ruleInfo.getStatementVO().getAllReqComponentVOs().size() > 0) {
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
        if (luStatementTypeKey.contains("enroll")) return RuleConstants.KS_STATEMENT_TYPE_ENROLLREQ_TEXT;
        if (luStatementTypeKey.contains("prereq")) return RuleConstants.KS_STATEMENT_TYPE_PREREQ_TEXT;
        if (luStatementTypeKey.contains("coreq")) return RuleConstants.KS_STATEMENT_TYPE_COREQ_TEXT;
        if (luStatementTypeKey.contains("antireq")) return RuleConstants.KS_STATEMENT_TYPE_ANTIREQ_TEXT;
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
    
	@Override
	protected void removeSectionFromLayout(BaseSection section) {
	
	}
    
}
