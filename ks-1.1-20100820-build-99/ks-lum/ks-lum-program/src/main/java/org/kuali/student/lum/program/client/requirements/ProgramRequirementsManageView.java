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

package org.kuali.student.lum.program.client.requirements;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.rules.ReqCompEditWidget;
import org.kuali.student.common.ui.client.widgets.rules.RuleManagementWidget;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;
import org.kuali.student.lum.program.client.rpc.StatementRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProgramRequirementsManageView extends VerticalSectionView {

    private StatementRpcServiceAsync statementRpcServiceAsync = GWT.create(StatementRpcService.class);

    Controller parentController;

    //view's widgets
    String pageTitle;
    VerticalPanel layout = new VerticalPanel();
    ReqCompEditWidget editReqCompWidget = new ReqCompEditWidget();
    RuleManagementWidget ruleManagementWidget = new RuleManagementWidget();
    private SimplePanel twiddlerPanel = new SimplePanel();
    private KSProgressIndicator twiddler = new KSProgressIndicator();

    private KSButton btnBackToRulesSummary = new KSButton("Back to Rules Summary");    

    //view's data
    private RuleInfo rule = null;
    private boolean isInitialized = false;

    public ProgramRequirementsManageView(Controller parentController, Enum<?> viewEnum, String name, String modelId, String ruleName) {
        super(viewEnum, name, modelId);

        this.parentController = parentController;
        pageTitle = ProgramProperties.get().programRequirements_manageViewPageTitle().replace("<*>", ruleName);

        //TODO remove after testing
        rule = new RuleInfo();
        rule.setCluId("123");
        rule.setId(Integer.toString(123)); //id++));
        rule.setEditHistory(new EditHistory());
        rule.setSelectedStatementType(null);

        StatementVO statementVO = new StatementVO();
        rule.setStatementVO(statementVO);
    }
    
    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {

        if (isInitialized == false) {
            retrieveReqCompTypes();
            setupHandlers();
            isInitialized = true;
        }            

        redraw();

        onReadyCallback.exec(true);
    }

    private void setupHandlers() {                        

        //TODO setup 'edit' req. component
        // edit link -> call editReqCompWidget.setupReqComp(existin req. comp.) -> return if req. comp. type list not yet loaded        

        btnBackToRulesSummary.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

//TODO               ((SectionView)parentController.getCurrentView()).setIsDirty(true);

                parentController.showView(RequirementsViewController.ProgramRequirementsViews.VIEW);

                /*
            	getController().requestModel(LuData.class, new ModelRequestCallback<DataModel>() {
                    @Override
                    public void onModelReady(DataModel dataModel) {                 	                        
                    	if (rule.getStatementVO() == null) {
//TODO                    	    ((CourseReqManager)getController()).removeRule(managedRule); 
                    	} else {
                            rule.setNaturalLanguageForRuleEdit(naturalLanguage);                     	    
                    	}
                    	
                        //switch to first page
//TODO                        getController().showView(PrereqViews.RULES_LIST, Controller.NO_OP_CALLBACK);
                    }

                    @Override
                    public void onRequestFail(Throwable cause) {
                    	GWT.log("Failed to get LuData DataModel", cause);
                    	Window.alert("Failed to get LuData DataModel");                        
                    }
                });
                */
            }
        });        
    }
      
    private void redraw() {

        remove(layout);
        layout.clear();

        SectionTitle title = SectionTitle.generateH2Title(pageTitle);
        title.setStyleName("KS-Program-Requirements-Section-header");  //make the header orange
        layout.add(title);

        //STEP 1
        title = SectionTitle.generateH3Title(ProgramProperties.get().programRequirements_manageViewPageStep1Title());
        title.setStyleName("KS-Program-Requirements-Manage-Step-header1");  //make the header orange
        layout.add(title);

        layout.add(editReqCompWidget);

        //STEP 2
        title = SectionTitle.generateH3Title(ProgramProperties.get().programRequirements_manageViewPageStep2Title());
        title.setStyleName("KS-Program-Requirements-Manage-Step-header2");  //make the header orange
        layout.add(title);

        layout.add(ruleManagementWidget);

        //add progressive indicator when rules are being simplified
        twiddler = new KSProgressIndicator();
        twiddler.setVisible(false);
        twiddlerPanel.setWidget(twiddler);
        layout.add(twiddlerPanel);
        
        layout.add(btnBackToRulesSummary);

        addWidget(layout);
    }

    private void retrieveReqCompTypes() {

        statementRpcServiceAsync.getReqComponentTypesForStatementType(rule.getStatementTypeKey(), new AsyncCallback<List<ReqComponentTypeInfo>>() {
            public void onFailure(Throwable cause) {
            	GWT.log("Failed to get req. component types for statement of type:" + rule.getStatementTypeKey(), cause);
            	Window.alert("Failed to get req. component types for statement of type:" + rule.getStatementTypeKey());
            }

            public void onSuccess(final List<ReqComponentTypeInfo> reqComponentTypeInfoList) {
                if (reqComponentTypeInfoList == null || reqComponentTypeInfoList.size() == 0) {
                    GWT.log("Missing Requirement Component Types", null);
                    Window.alert("Missing Requirement Component Types");
                    return;
                }
                editReqCompWidget.setReqCompList(reqComponentTypeInfoList);
            }
        });
    }

    // called by requirement display widget when user wants to edit a specific piece of rule
    public void setRuleTree(StatementTreeViewInfo stmtTreeInfo) {
        rule.getStatementVO().setStatementTreeViewInfo(stmtTreeInfo);
    }
}
