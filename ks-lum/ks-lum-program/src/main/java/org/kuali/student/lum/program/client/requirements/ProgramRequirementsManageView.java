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

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.rules.RuleTable;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;
import org.kuali.student.lum.program.client.rpc.StatementRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.SimplePanel;

public class ProgramRequirementsManageView extends VerticalSectionView {

    private StatementRpcServiceAsync statementRpcServiceAsync = GWT.create(StatementRpcService.class);

    Controller parentController;

    //view's widgets
    String pageTitle;
    private RuleTable ruleTable = new RuleTable();
    private SimplePanel twiddlerPanel = new SimplePanel();
    private KSProgressIndicator twiddler = new KSProgressIndicator();

    private KSButton btnBackToRulesSummary = new KSButton("Back to Rules Summary");    

    //view's data
    private RuleInfo rule = null;
    private boolean isInitialized = false;

    public ProgramRequirementsManageView(Controller parentController, Enum<?> viewEnum, String name, String modelId,
                                         StatementTreeViewInfo ruleTree, String ruleName) {
        super(viewEnum, name, modelId);
        pageTitle = ProgramProperties.get().programRequirements_manageViewPageTitle().replace("<*>", ruleName);

        this.parentController = parentController;        

        rule = new RuleInfo();
        rule.setCluId("123");
        rule.setId(Integer.toString(123)); //id++));
        rule.setEditHistory(new EditHistory());
        rule.setSelectedStatementType(null);

        StatementVO statementVO = new StatementVO();
        statementVO.setStatementTreeViewInfo(ruleTree);
        rule.setStatementVO(statementVO);
    }
    
    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {

        if (isInitialized == false) {
            setupHandlers();            
        }            

        redraw();

        onReadyCallback.exec(true);
    }

    private void setupHandlers() {                        


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

        addWidget(SectionTitle.generateH2Title(pageTitle));

        addWidget(SectionTitle.generateH3Title(ProgramProperties.get().programRequirements_manageViewPageStep1Title()));

        addWidget(SectionTitle.generateH3Title(ProgramProperties.get().programRequirements_manageViewPageStep2Title()));        

        //add progressive indicator when rules are being simplified
        twiddler = new KSProgressIndicator();
        twiddler.setVisible(false);
        twiddlerPanel.setWidget(twiddler);
        addWidget(twiddlerPanel);
        
        addWidget(btnBackToRulesSummary);
    }

    // called by requirement display widget when user wants to edit a specific piece of rule
    public void setRuleTree(StatementTreeViewInfo stmtTreeInfo) {
        rule.getStatementVO().setStatementTreeViewInfo(stmtTreeInfo);
    }
}
