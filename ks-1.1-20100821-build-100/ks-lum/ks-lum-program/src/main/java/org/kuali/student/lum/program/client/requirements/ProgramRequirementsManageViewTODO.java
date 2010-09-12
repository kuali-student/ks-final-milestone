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

import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.KSTabPanel;
import org.kuali.student.common.ui.client.widgets.rules.RuleNodeWidget;
import org.kuali.student.common.ui.client.widgets.rules.RuleTable;
import org.kuali.student.common.ui.client.widgets.rules.Token;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;
import org.kuali.student.lum.program.client.rpc.StatementRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProgramRequirementsManageViewTODO extends VerticalSectionView {
    private StatementRpcServiceAsync statementRpcServiceAsync = GWT.create(StatementRpcService.class);
  //  private CluSetManagementRpcServiceAsync cluSetManagementRpcServiceAsync = GWT.create(CluSetManagementRpcService.class);

    Controller parentController;

    //view's widgets
    private Panel mainPanel = new SimplePanel();
    VerticalPanel complexView = new VerticalPanel();
    private KSButton btnAddRule = new KSButton("+ Add new rule");
    private KSButton btnAddAND = new KSButton("AND");
    private KSButton btnAddOR = new KSButton("OR");
    private KSButton btnAddToGroup = new KSButton("Add to group");
    private KSButton btnUndo = new KSButton("Undo");
    private KSButton btnRedo = new KSButton("Redo");
    private KSButton btnDelete = new KSButton("Delete");
    private KSButton btnMoveRuleDown = new KSButton();
    private KSButton btnMoveRuleUp = new KSButton();
//    private KSButton btnSaveRule = new KSButton("Save");
    private KSButton btnBackToRulesSummary = new KSButton("Back to Rules Summary");
    private KSTabPanel ruleViewsPanel = new KSTabPanel();
    private KSLabel logicalExpression = new KSLabel();
    private KSLabel simpleRuleNL = new KSLabel();
    private KSLabel complexRuleNL = new KSLabel();
    private KSLabel editManually = new KSLabel("Edit manually");
    private String naturalLanguage;
    private RuleTable  ruleTable = new RuleTable();
    private SimplePanel twiddlerPanel = new SimplePanel();
    private KSProgressIndicator twiddler = new KSProgressIndicator();

    private ClickHandler ruleTableSelectionHandler = null;
    private ClickHandler ruleTableToggleClickHandler = null;
    private ClickHandler ruleTableEditClauseHandler = null;
    private HandlerRegistration textClickHandler = null;

    //view's data
    private RuleInfo rule = null;
    private boolean isInitialized = false;
    public enum Views{MANAGE_RULES}

    //cluset data
    private DataModel clusetModel = new DataModel();

    public ProgramRequirementsManageViewTODO(Controller parentController, Enum<?> viewEnum, String name, String modelId, StatementTreeViewInfo ruleTree) {
        super(viewEnum, name, modelId);
       // super(controller, "Complex View", Views.MANAGE_RULES);
        super.addWidget(mainPanel);

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

        /*
        getController().requestModel(RuleInfo.class, new ModelRequestCallback<CollectionModel<RuleInfo>>() {
            public void onModelReady(CollectionModel<RuleInfo> theModel) {
                model = theModel;    
                
                if (isInitialized == false) {                    
                    model.addModelChangeHandler(new ModelChangeHandler() {
                        public void onModelChange(ModelChangeEvent event) {
                            //redraw();
                        }
                    });                          
                }
                
                redraw();                
                isInitialized = true;
                onReadyCallback.exec(true);                
            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }
        });    
        
        getController().requestModel(CourseConfigurer.CLU_PROPOSAL_MODEL, new ModelRequestCallback<DataModel>(){
	           @Override
	            public void onModelReady(DataModel model) {
	        	    clusetModel = model;
	           }       			
	           
	            @Override
	            public void onRequestFail(Throwable cause) {
	                throw new RuntimeException("Unable to connect to model", cause);
	            }       	
        });   */

        redraw(); //based on set tree..

        onReadyCallback.exec(true);

    }


    private void setupHandlers() {            
               
        ruleTableToggleClickHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Cell cell = ruleTable.getCellForEvent(event);
                if (cell == null) {
                    return;
                }
                
                RuleNodeWidget widget = (RuleNodeWidget) ruleTable.getWidget(cell.getRowIndex(), cell.getCellIndex());
                Token userObject = (Token) widget.getNode().getUserObject();
                
                if (userObject.isTokenOperator()) {
                    userObject.toggleAndOr();
                    StatementVO unsimplified = ObjectClonerUtil.clone(rule.getStatementVO());
                    boolean structureChanged = rule.getStatementVO().simplify();
                    rule.getEditHistory().save(rule.getStatementVO());
                    if (structureChanged) {
                        showUnSimpTemporarily(unsimplified);
                    }
                    redraw();
                }
            }
        };
        
        ruleTableSelectionHandler = new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                              
                Cell cell = ruleTable.getCellForEvent(event);
                if (cell == null) {
                    return;
                }
                
                RuleNodeWidget widget = (RuleNodeWidget) ruleTable.getWidget(cell.getRowIndex(), cell.getCellIndex());
                Object userObject = widget.getNode().getUserObject();
                if (userObject instanceof StatementVO) { 
                    StatementVO statementVO = (StatementVO) userObject;
                    statementVO.setCheckBoxOn(!statementVO.isCheckBoxOn());
                } else if (userObject instanceof ReqComponentVO) {                    
                    ReqComponentVO reqComponentVO = (ReqComponentVO) userObject;
                    reqComponentVO.setCheckBoxOn(!reqComponentVO.isCheckBoxOn());
                }
                updateTable();
            }
        };
        textClickHandler = ruleTable.addTextClickHandler(ruleTableSelectionHandler);
        
        ruleTableEditClauseHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Cell cell = ruleTable.getCellForEvent(event); 
            
                if (cell == null) {
                    return;
                }
                
                RuleNodeWidget widget = (RuleNodeWidget) ruleTable.getWidget(cell.getRowIndex(), cell.getCellIndex());                 
                Object userObject = widget.getNode().getUserObject();   
                if (userObject instanceof ReqComponentVO) {
                    final ReqComponentVO rule = (ReqComponentVO) userObject;
//TODO                    ((CourseReqManager)getController()).setComponentToEdit(rule);  //selected rule to be given to rule component editor      
                    
                    //get cluset data
                    if(rule.getClusetId() != null){
                    	String clusetId = rule.getClusetId();

/* TODO
                    	cluSetManagementRpcServiceAsync.getData(clusetId, new AsyncCallback<Data>() {
                            @Override
                            public void onFailure(Throwable caught) {
            	                Window.alert(caught.getMessage());
            	                GWT.log("Failed to retrieve cluset with id " + rule.getClusetId(), caught);
                            }

                            @Override
                            public void onSuccess(Data result) {
                            	if(result != null){
                            		clusetModel.getRoot().set("cluset", (Data)result.get("cluset"));
                            		getController().showView(PrereqViews.RULE_COMPONENT_EDITOR, Controller.NO_OP_CALLBACK);
                            	}
                            	else{
                   	                Window.alert("Cannot find Cluset with id " + rule.getClusetId());
                	                GWT.log("Cannot find Cluset with id " + rule.getClusetId());
                            	}
                            		
                            }
                    	});
*/                    	
                    }
 //                   else
//TODO                    	getController().showView(PrereqViews.RULE_COMPONENT_EDITOR, Controller.NO_OP_CALLBACK);
                }
            }
        };
        
        btnAddRule.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {      
//TODO            	((CourseReqManager)getController()).setComponentToEdit(null);  //selected rule to be given to rule component editor
//TODO                getController().showView(PrereqViews.RULE_COMPONENT_EDITOR, Controller.NO_OP_CALLBACK);
            }
        });                
        
        btnMoveRuleDown.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO statementVO = rule.getStatementVO();
                if (statementVO != null) {
                    List<ReqComponentVO> selectedRCs = statementVO.getSelectedReqComponentVOs();
                    List<StatementVO> selectedSs = statementVO.getSelectedStatementVOs();
                    int numSelectedRCs = (selectedRCs == null)? 0 : selectedRCs.size();
                    int numSelectedSs = (selectedSs == null)? 0 : selectedSs.size();
                    ReqComponentVO selectedReqCompVO = null;
                    
                    if (numSelectedRCs == 1 && numSelectedSs == 0) {
                        selectedReqCompVO = selectedRCs.get(0);
                        StatementVO enclosingStatementVO = statementVO.getEnclosingStatementVO(statementVO, selectedReqCompVO);
                        enclosingStatementVO.shiftReqComponent("RIGHT", selectedReqCompVO);
                        rule.getEditHistory().save(statementVO);
                        redraw();
                    }
                }
            }
        });

        btnMoveRuleUp.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO statementVO = rule.getStatementVO();
                if (statementVO != null) {
                    List<ReqComponentVO> selectedRCs = statementVO.getSelectedReqComponentVOs();
                    List<StatementVO> selectedSs = statementVO.getSelectedStatementVOs();
                    int numSelectedRCs = (selectedRCs == null)? 0 : selectedRCs.size();
                    int numSelectedSs = (selectedSs == null)? 0 : selectedSs.size();
                    ReqComponentVO selectedReqCompVO = null;
                    
                    if (numSelectedRCs == 1 && numSelectedSs == 0) {
                        selectedReqCompVO = selectedRCs.get(0);
                        StatementVO enclosingStatementVO =  statementVO.getEnclosingStatementVO(statementVO, selectedReqCompVO);
                        enclosingStatementVO.shiftReqComponent("LEFT", selectedReqCompVO);
                        rule.getEditHistory().save(statementVO);
                        redraw();
                    }
                }
            }
        });
        
        btnAddOR.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO unsimplified = null;
                boolean structureChanged = false;
                rule.insertOR();
                // clone a copy of the unsimplified form for showing intermediate step on the UI
                unsimplified = ObjectClonerUtil.clone(rule.getStatementVO());
                structureChanged = rule.getStatementVO().simplify();
                rule.getEditHistory().save(rule.getStatementVO());
                // sets the statementVO to be the version that hasn't been simplified yet
                // temporarily
                if (structureChanged) {
                    showUnSimpTemporarily(unsimplified);
                } else {
                    redraw();
                }
            }
        });
        
        btnAddAND.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO unsimplified = null;
                boolean structureChanged = false;
                rule.insertAND();
                // clone a copy of the unsimplified form for showing intermediate step on the UI
                unsimplified = ObjectClonerUtil.clone(rule.getStatementVO());
                structureChanged = rule.getStatementVO().simplify();
                rule.getEditHistory().save(rule.getStatementVO());
                // sets the statementVO to be the version that hasn't been simplified yet
                // temporarily
                if (structureChanged) {
                    showUnSimpTemporarily(unsimplified);
                } else {
                    redraw();
                }
            }
        });
        
        btnDelete.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO unsimplified = null;
                boolean structureChanged = false;
                rule.deleteItem();
                // clone a copy of the unsimplified form for showing intermediate step on the UI
                unsimplified = ObjectClonerUtil.clone(rule.getStatementVO());
                if (rule.getStatementVO() != null) {
                    structureChanged = rule.getStatementVO().simplify();
                }
                rule.getEditHistory().save(rule.getStatementVO());
                // sets the statementVO to be the version that hasn't been simplified yet
                // temporarily
                if (structureChanged) {
                    showUnSimpTemporarily(unsimplified);
                } else {
                    redraw();
                }
            }
        });
        
        btnAddToGroup.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO unsimplified = null;
                boolean structureChanged = false;
                rule.addToGroup();
                // clone a copy of the unsimplified form for showing intermediate step on the UI
                unsimplified = ObjectClonerUtil.clone(rule.getStatementVO());
                if (rule.getStatementVO() != null) {
                    structureChanged = rule.getStatementVO().simplify();
                }
                rule.getEditHistory().save(rule.getStatementVO());
                // sets the statementVO to be the version that hasn't been simplified yet
                // temporarily
                if (structureChanged) {
                    showUnSimpTemporarily(unsimplified);
                } else {
                    redraw();
                }
            }
        });
        
        editManually.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                rule.populateExpression();
                rule.setPreviewedExpression(rule.getExpression());
//TODO                getController().showView(PrereqViews.RULE_EXPRESSION_EDITOR, Controller.NO_OP_CALLBACK);
            }
        });
        
        btnUndo.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO previousState = rule.getEditHistory().undo();
                if (previousState != null) {
                    rule.setStatementVO(previousState);
                }
                redraw();
            }
        });
        
        btnRedo.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO nextState = rule.getEditHistory().redo();
                if (nextState != null) {
                    rule.setStatementVO(nextState);
                }
                redraw();
            }
        });
        
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
    
    public void showUnSimpTemporarily(StatementVO unsimplified) {
        rule.setStatementVO(unsimplified);
        rule.setSimplifying(true);
        redraw();
        // sleep for a while to show the user how the rule looks like before 
        // simplification
        Timer simplifyingTimer = new Timer() {
            public void run() {
                rule.setSimplifying(false);
                rule.setStatementVO(rule.getEditHistory().getCurrentState());
                redraw();
            }
        };
        simplifyingTimer.schedule(1000);
    }
    
    private void redraw() {

        complexView.clear();
        complexView.setStyleName("Content-Margin");
        
        SimplePanel tempPanel = new SimplePanel();
        tempPanel.setStyleName("KS-Rules-FullWidth");
        KSLabel preReqHeading = new KSLabel(getHeading());
        preReqHeading.setStyleName("KS-ReqMgr-Heading");
        tempPanel.add(preReqHeading);
        complexView.add(tempPanel);
        
        //setup top rules table buttons
        HorizontalPanel topButtonsPanel = new HorizontalPanel();
        btnAddRule.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnAddRule);
        SimplePanel spacer1 = new SimplePanel();
        spacer1.setWidth("30px");
        topButtonsPanel.add(spacer1);       
        btnAddAND.addStyleName("KS-Rules-Tight-Grey-Button");
        topButtonsPanel.add(btnAddAND);   
        btnAddOR.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnAddOR);        
        btnAddToGroup.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnAddToGroup);
        SimplePanel spacer2 = new SimplePanel();
        spacer2.setWidth("30px");
        topButtonsPanel.add(spacer2);
        btnUndo.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnUndo);    
        btnRedo.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnRedo);
        SimplePanel spacer3 = new SimplePanel();
        spacer3.setWidth("30px");
        topButtonsPanel.add(spacer3);         
        btnDelete.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnDelete);        
        complexView.add(topButtonsPanel);        
        
        twiddler = new KSProgressIndicator();
        twiddler.setVisible(false);
        
        twiddlerPanel.setHeight("30px");
        twiddlerPanel.setWidget(twiddler);
        complexView.add(twiddlerPanel);        
        
        //setup Rules Table
        HorizontalPanel tempPanel2 = new HorizontalPanel();
        HorizontalPanel tableButtonsPanel = new HorizontalPanel();
        VerticalPanel arrowButtonsPanel = new VerticalPanel();
        tempPanel2.add(ruleTable);
        arrowButtonsPanel.add(btnMoveRuleUp);
        btnMoveRuleUp.addStyleName("KS-RuleTable-UpArrow");
        arrowButtonsPanel.add(btnMoveRuleDown);
        btnMoveRuleDown.addStyleName("KS-RuleTable-DownArrow");
        tableButtonsPanel.add(arrowButtonsPanel);
        tempPanel2.add(tableButtonsPanel);
        complexView.add(tempPanel2);
        SimplePanel verticalSpacer2 = new SimplePanel();
        verticalSpacer2.setHeight("30px");
        complexView.add(verticalSpacer2);                 
               
        updateRulesTable();    
        
        SimplePanel verticalSpacer3 = new SimplePanel();
        verticalSpacer3.setHeight("30px");
        complexView.add(verticalSpacer3);
        
        HorizontalPanel buttonsPanel = new HorizontalPanel();
        buttonsPanel.setSpacing(0);
//        btnSaveRule.addStyleName("KS-Rules-Tight-Button");  
//        buttonsPanel.add(btnSaveRule);
        SimplePanel horizSpacer = new SimplePanel();
        horizSpacer.setWidth("30px");        
        buttonsPanel.add(horizSpacer);        
        btnBackToRulesSummary.addStyleName("KS-Rules-Tight-Grey-Button"); 
        buttonsPanel.add(btnBackToRulesSummary);
        complexView.add(buttonsPanel);                 
        
        mainPanel.clear();
        mainPanel.add(complexView); 
    }
    
    private void updateRulesTable() {
        
        simpleRuleNL.setText("");                
        complexRuleNL.setText("");
        updateTable(); 
        
        if (rule != null) {
            
            boolean simpleRule = (rule.getStatementVO() == null)? true : rule.getStatementVO().isSimple();
            boolean emptyRule = (rule.getStatementVO() == null)? true : rule.getStatementVO().isEmpty();
            
            btnAddAND.setVisible(!simpleRule);
            btnAddOR.setVisible(!simpleRule);
            btnAddToGroup.setVisible(!simpleRule);
            btnMoveRuleUp.setVisible(!simpleRule);
            btnMoveRuleDown.setVisible(!simpleRule);           
            
            if (isInitialized == false) {
                HorizontalPanel logicExpressionTab = new HorizontalPanel();
                logicExpressionTab.add(logicalExpression);
                editManually.addStyleName("KS-Rules-URL-Link");
                logicExpressionTab.add(editManually);
                ruleViewsPanel.addTab(logicExpressionTab, "Logic Expression");
                SimplePanel NLPanel = new SimplePanel();
                NLPanel.add(complexRuleNL);
                ruleViewsPanel.addTab(NLPanel, "Natural Language");
                ruleViewsPanel.setStyleName("KS-Rules-Rule-Views");
                ruleViewsPanel.selectTab(0);   
                ruleViewsPanel.setHeight("80px");
            }                
            
            //update rule views tabs
            if (emptyRule) {    //don't show NL or tabs
                ruleViewsPanel.setVisible(false);
                KSLabel noRulesText = new KSLabel("No prerequisite rules.");
                noRulesText.setStyleName("KS-ReqMgr-NoRuleText");
                noRulesText.addStyleName("KS-Rules-BoldText");
                complexView.add(noRulesText);
            } else if (simpleRule) {                
                KSLabel nlHeading = new KSLabel("Natural Language");
                nlHeading.setStyleName("KS-ReqMgr-SubHeading"); 
                complexView.add(nlHeading);
                complexView.add(simpleRuleNL);
                updateNaturalLanguage();
                ruleViewsPanel.setVisible(false);
            } else {  //rule with 1 or more logical operators                    
                KSLabel viewsHeading = new KSLabel("Alternate Views");
                viewsHeading.setStyleName("KS-ReqMgr-BlockHeading");
                complexView.add(viewsHeading);                
                complexView.add(ruleViewsPanel);
                logicalExpression.setText((rule.getStatementVO() == null ? "" : rule.getStatementVO().convertToExpression()));                
                updateNaturalLanguage();                
                ruleViewsPanel.setVisible(true);
            }                                                     
        }        
    }        
    
    private void updateTable() {        
//TODO        btnAddAND.setEnabled(rule.statementVOIsGroupAble());
//TODO        btnAddOR.setEnabled(rule.statementVOIsGroupAble());
//TODO        btnAddToGroup.setEnabled(rule.isAddToGroupOK());
//TODO        btnUndo.setEnabled(rule.getEditHistory().isUndoable());
//TODO        btnRedo.setEnabled(rule.getEditHistory().isRedoable());
        btnAddRule.setEnabled(true);
//TODO        btnDelete.setEnabled(rule.statementVOIsDegroupAble());
//        btnSaveRule.setEnabled(false);  
        
        ruleTable.clear();
        Node tree = rule.getStatementTree();
        if (tree != null) {
            if (rule.isSimplifying()) {
                twiddler.setText("Simplifying...");
                twiddler.setVisible(true);
            } else {
                twiddler.setText("");
                twiddler.setVisible(false);
            }
            ruleTable.buildTable(tree);
            textClickHandler.removeHandler();
            ruleTable.addTextClickHandler(ruleTableSelectionHandler);
            ruleTable.addToggleHandler(ruleTableToggleClickHandler);
            ruleTable.addEditClauseHandler(ruleTableEditClauseHandler);                
        }        
    }
    
    private void updateNaturalLanguage() {                 

        statementRpcServiceAsync.translateStatementTreeViewToNL(rule.getStatementVO().getStatementTreeViewInfo(), "KUALI.RULEEDIT", "en", new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                GWT.log("NL failed", caught);
           }
            
            public void onSuccess(final String statementNaturalLanguage) { 
                naturalLanguage = statementNaturalLanguage;
                simpleRuleNL.setText(naturalLanguage);                
                complexRuleNL.setText(naturalLanguage);
            } 
        }); 
    }

    private String getHeading() {
    	return "Manage " + getRuleTypeName() + " Rules";
    }
    
    private String getRuleTypeName() {
    	//String luStatementTypeKey = rule.getSelectedStatementType();
        /*
        if (luStatementTypeKey.contains("enroll")) return RuleConstants.KS_STATEMENT_TYPE_ENROLLREQ_TEXT;
        if (luStatementTypeKey.contains("prereq")) return RuleConstants.KS_STATEMENT_TYPE_PREREQ_TEXT;
        if (luStatementTypeKey.contains("coreq")) return RuleConstants.KS_STATEMENT_TYPE_COREQ_TEXT;
        if (luStatementTypeKey.contains("antireq")) return RuleConstants.KS_STATEMENT_TYPE_ANTIREQ_TEXT;
        */
        return "";
    }

    // called by requirement display widget when user wants to edit a specific piece of rule
    public void setRuleTree(StatementTreeViewInfo stmtTreeInfo) {
        rule.getStatementVO().setStatementTreeViewInfo(stmtTreeInfo);
    }
}
