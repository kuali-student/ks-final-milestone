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
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ActionCancelGroup;
import org.kuali.student.common.ui.client.widgets.rules.ObjectClonerUtil;
import org.kuali.student.common.ui.client.widgets.rules.ReqCompEditWidget;
import org.kuali.student.common.ui.client.widgets.rules.RuleManageWidget;
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

    ProgramRequirementsViewController parentController;

    //view's widgets
    VerticalPanel layout = new VerticalPanel();
    ReqCompEditWidget editReqCompWidget = new ReqCompEditWidget();
    RuleManageWidget ruleManageWidget = new RuleManageWidget();
    private SimplePanel twiddlerPanel = new SimplePanel();
    private KSProgressIndicator twiddler = new KSProgressIndicator();   

    //view's data
    private StatementTreeViewInfo rule = null;
    private boolean isInitialized = false;
    private boolean newRule = false;

    public ProgramRequirementsManageView(ProgramRequirementsViewController parentController, Enum<?> viewEnum, String name, String modelId) {
        super(viewEnum, name, modelId);
        this.parentController = parentController;
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

        editReqCompWidget.addConfirmBtnClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {

                statementRpcServiceAsync.translateStatementTreeViewToNL(rule, "KUALI.RULEEDIT", "en", new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                        GWT.log("translateStatementTreeViewToNL failed", caught);
                   }

                    public void onSuccess(final String statementNaturalLanguage) {
                        rule.setNaturalLanguageTranslation(statementNaturalLanguage);
                        ruleManageWidget.updateRuleViews(rule);
                    }
                });

            }
        });

        //TODO setup 'edit' req. component
        // edit link -> call editReqCompWidget.setupReqComp(existin req. comp.) -> return if req. comp. type list not yet loaded        
       /*
        btnBackToRulesSummary.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

//TODO               ((SectionView)parentController.getCurrentView()).setIsDirty(true);

                parentController.showView(ProgramRequirementsViewController.ProgramRequirementsViews.PREVIEW);


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
                
            }
        }); */        
    }
      
    private void redraw() {

        remove(layout);
        layout.clear();

        //STEP 1
        SectionTitle title = SectionTitle.generateH3Title(ProgramProperties.get().programRequirements_manageViewPageStep1Title());
        title.setStyleName("KS-Program-Requirements-Manage-Step-header1");  //make the header orange
        layout.add(title);

        layout.add(editReqCompWidget);

        //STEP 2
        title = SectionTitle.generateH3Title(ProgramProperties.get().programRequirements_manageViewPageStep2Title());
        title.setStyleName("KS-Program-Requirements-Manage-Step-header2");  //make the header orange
        layout.add(title);

        ruleManageWidget.updateRuleViews(rule);
        layout.add(ruleManageWidget);

        //add progressive indicator when rules are being simplified
        twiddler = new KSProgressIndicator();
        twiddler.setVisible(false);
        twiddlerPanel.setWidget(twiddler);
        layout.add(twiddlerPanel);

        addWidget(layout);

        displaySaveButton();
    }

    private void retrieveReqCompTypes() {

        statementRpcServiceAsync.getReqComponentTypesForStatementType(rule.getType(), new AsyncCallback<List<ReqComponentTypeInfo>>() {
            public void onFailure(Throwable cause) {
            	GWT.log("Failed to get req. component types for statement of type:" + rule.getType(), cause);
            	Window.alert("Failed to get req. component types for statement of type:" + rule.getType());
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

    private void displaySaveButton() {
        ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.SaveContinueCancelEnum.SAVE_CONTINUE, ButtonEnumerations.SaveContinueCancelEnum.CANCEL);
        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
                if (result == ButtonEnumerations.SaveContinueCancelEnum.CANCEL) {
                    parentController.showView(ProgramRequirementsViewController.ProgramRequirementsViews.PREVIEW);
                } else {
                    //TODO 
                    parentController.showView(ProgramRequirementsViewController.ProgramRequirementsViews.PREVIEW);
                }
            }
        });
        addWidget(actionCancelButtons);
    }

    // called by requirement display widget when user wants to edit a specific piece of rule
    public void setRuleTree(StatementTreeViewInfo stmtTreeInfo, String ruleType) {
        //true if user is adding a new rule
        if (stmtTreeInfo == null) {
            rule = new StatementTreeViewInfo();
            rule.setType(ruleType);
            newRule = true;
        } else {
            rule = ObjectClonerUtil.clone(stmtTreeInfo);
            newRule = false;
        }
    }
}
