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

package org.kuali.student.lum.ui.requirements.client.view;

import java.util.List;

import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.CollectionModel;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelChangeEvent;
import org.kuali.student.common.ui.client.mvc.ModelChangeHandler;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.KSTabPanel;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseReqSummaryHolder;
import org.kuali.student.lum.ui.requirements.client.controller.CourseReqManager;
import org.kuali.student.lum.ui.requirements.client.controller.CourseReqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.ObjectClonerUtil;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcService;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class ManageRulesView extends ViewComposite {
    private RequirementsRpcServiceAsync requirementsRpcServiceAsync = GWT.create(RequirementsRpcService.class);
    
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
    private RuleTable ruleTable = new RuleTable();
    private SimplePanel twiddlerPanel = new SimplePanel();
    private KSProgressIndicator twiddler = new KSProgressIndicator();

    private ClickHandler ruleTableSelectionHandler = null;
    private ClickHandler ruleTableToggleClickHandler = null;
    private ClickHandler ruleTableEditClauseHandler = null;
    private HandlerRegistration textClickHandler = null;
    
    //view's data
    private CollectionModel<RuleInfo> model;   //contains only RuleInfo object for selected rule
    private boolean isInitialized = false;

    public ManageRulesView(Controller controller) {
        super(controller, "Complex View");
        super.initWidget(mainPanel);                
    }
    
    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {

        if (isInitialized == false) {
            setupHandlers();            
        }            
            
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
    }

    private void setupHandlers() {            
               
        ruleTableToggleClickHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Cell cell = ruleTable.getCellForEvent(event);
                if (cell == null) {
                    GWT.log("Cell is NULL", null);
                    return;
                }
                
                RuleNodeWidget widget = (RuleNodeWidget) ruleTable.getWidget(cell.getRowIndex(), cell.getCellIndex());                 
                Object userObject = widget.getNode().getUserObject();
                
                if (userObject instanceof StatementVO) {
                    StatementVO statementVO = (StatementVO) userObject;
                    if (statementVO != null) {
                        StatementOperatorTypeKey currentOp =
                            (statementVO.getStatementInfo().getOperator());
                        StatementOperatorTypeKey newOp = null;
                        if (currentOp == StatementOperatorTypeKey.AND) {
                            newOp = StatementOperatorTypeKey.OR;
                        } else {
                            newOp = StatementOperatorTypeKey.AND;
                        }
                        statementVO.getStatementInfo().setOperator(newOp);
                        RuleInfo prereqInfo = model.getValue();
                        StatementVO unsimplified = ObjectClonerUtil.clone(prereqInfo.getStatementVO());
                        boolean structureChanged = prereqInfo.getStatementVO().simplify();
                        prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
                        if (structureChanged) {
                            showUnSimpTemporarily(unsimplified);
                        }
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
                    ((CourseReqManager)getController()).setComponentToEdit(rule);  //selected rule to be given to rule component editor                                                                 
                    getController().showView(PrereqViews.RULE_COMPONENT_EDITOR, Controller.NO_OP_CALLBACK);
                }
            }
        };
        
        btnAddRule.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {      
            	((CourseReqManager)getController()).setComponentToEdit(null);  //selected rule to be given to rule component editor
                getController().showView(PrereqViews.RULE_COMPONENT_EDITOR, Controller.NO_OP_CALLBACK);
            }
        });                
        
        btnMoveRuleDown.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RuleInfo prereqInfo = model.getValue();
                StatementVO statementVO = prereqInfo.getStatementVO();
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
                        prereqInfo.getEditHistory().save(statementVO);
                        redraw();
                    }
                }
            }
        });

        btnMoveRuleUp.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RuleInfo prereqInfo = model.getValue();
                StatementVO statementVO = prereqInfo.getStatementVO();
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
                        prereqInfo.getEditHistory().save(statementVO);
                        redraw();
                    }
                }
            }
        });
        
        btnAddOR.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RuleInfo prereqInfo = model.getValue();
                StatementVO unsimplified = null;
                boolean structureChanged = false;
                prereqInfo.insertOR();
                // clone a copy of the unsimplified form for showing intermediate step on the UI
                unsimplified = ObjectClonerUtil.clone(prereqInfo.getStatementVO());
                structureChanged = prereqInfo.getStatementVO().simplify();
                prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
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
                RuleInfo prereqInfo = model.getValue();
                StatementVO unsimplified = null;
                boolean structureChanged = false;
                prereqInfo.insertAND();
                // clone a copy of the unsimplified form for showing intermediate step on the UI
                unsimplified = ObjectClonerUtil.clone(prereqInfo.getStatementVO());
                structureChanged = prereqInfo.getStatementVO().simplify();
                prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
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
                RuleInfo prereqInfo = model.getValue();
                StatementVO unsimplified = null;
                boolean structureChanged = false;
                prereqInfo.deleteItem();
                // clone a copy of the unsimplified form for showing intermediate step on the UI
                unsimplified = ObjectClonerUtil.clone(prereqInfo.getStatementVO());
                if (prereqInfo.getStatementVO() != null) {
                    structureChanged = prereqInfo.getStatementVO().simplify();
                }
                prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
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
                RuleInfo prereqInfo = model.getValue();
                StatementVO unsimplified = null;
                boolean structureChanged = false;
                prereqInfo.addToGroup();
                // clone a copy of the unsimplified form for showing intermediate step on the UI
                unsimplified = ObjectClonerUtil.clone(prereqInfo.getStatementVO());
                if (prereqInfo.getStatementVO() != null) {
                    structureChanged = prereqInfo.getStatementVO().simplify();
                }
                prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
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
                RuleInfo prereqInfo = model.getValue();
                prereqInfo.populateExpression();
                prereqInfo.setPreviewedExpression(prereqInfo.getExpression());
                getController().showView(PrereqViews.RULE_EXPRESSION_EDITOR, Controller.NO_OP_CALLBACK);
            }
        });
        
        btnUndo.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RuleInfo prereqInfo = model.getValue();
                StatementVO previousState = prereqInfo.getEditHistory().undo();
                if (previousState != null) {
                    prereqInfo.setStatementVO(previousState);
                }
                redraw();
            }
        });
        
        btnRedo.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RuleInfo prereqInfo = model.getValue();
                StatementVO nextState = prereqInfo.getEditHistory().redo();
                if (nextState != null) {
                    prereqInfo.setStatementVO(nextState);
                }
                redraw();
            }
        });
        
        btnBackToRulesSummary.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	getController().requestModel(LuData.class, new ModelRequestCallback<DataModel>() {
                    @Override
                    public void onModelReady(DataModel dataModel) {
                    	RuleInfo managedRule = model.getValue();                    	
                        
                    	if (managedRule.getStatementVO() == null) {
                    	    ((CourseReqManager)getController()).removeRule(managedRule); 
                    	} else {
                            managedRule.setNaturalLanguage(naturalLanguage);                     	    
                    	}
                    	
                        //switch to first page
                        getController().showView(PrereqViews.RULES_LIST, Controller.NO_OP_CALLBACK);
                    }

                    @Override
                    public void onRequestFail(Throwable cause) {
                    	GWT.log("Failed to get LuData DataModel", cause);
                    	Window.alert("Failed to get LuData DataModel");                        
                    }
                });            	            
            }
        });        
    }
    
    public void showUnSimpTemporarily(StatementVO unsimplified) {
        RuleInfo prereqInfo = model.getValue();
        prereqInfo.setStatementVO(unsimplified);
        prereqInfo.setSimplifying(true);
        redraw();
        // sleep for a while to show the user how the rule looks like before 
        // simplification
        Timer simplifyingTimer = new Timer() {
            public void run() {
                RuleInfo prereqInfo = model.getValue();
                prereqInfo.setSimplifying(false);
                prereqInfo.setStatementVO(prereqInfo.getEditHistory().getCurrentState());
                redraw();
            }
        };
        simplifyingTimer.schedule(1000);
    }
    
    private void redraw() {
        if (CourseReqSummaryHolder.getView() != null) {
            CourseReqSummaryHolder.getView().setTheController(getController());
            CourseReqSummaryHolder.getView().redraw();
        }
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
        btnMoveRuleUp.setStyleName("KS-RuleTable-UpArrow");
        arrowButtonsPanel.add(btnMoveRuleDown);
        btnMoveRuleDown.setStyleName("KS-RuleTable-DownArrow");
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
        
        RuleInfo prereqInfo = model.getValue();
        simpleRuleNL.setText("");                
        complexRuleNL.setText("");
        updateTable(); 
        
        if (prereqInfo != null) {
            
            boolean simpleRule = (prereqInfo.getStatementVO() == null)? true : prereqInfo.getStatementVO().isSimple();
            boolean emptyRule = (prereqInfo.getStatementVO() == null)? true : prereqInfo.getStatementVO().isEmpty();
            
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
                logicalExpression.setText((prereqInfo.getStatementVO() == null ? "" : prereqInfo.getStatementVO().convertToExpression()));                
                updateNaturalLanguage();                
                ruleViewsPanel.setVisible(true);
            }                                                     
        }        
    }        
    
    private void updateTable() {        
        RuleInfo prereqInfo = model.getValue();
        btnAddAND.setEnabled(prereqInfo.statementVOIsGroupAble());
        btnAddOR.setEnabled(prereqInfo.statementVOIsGroupAble());  
        btnAddToGroup.setEnabled(prereqInfo.isAddToGroupOK());  
        btnUndo.setEnabled(prereqInfo.getEditHistory().isUndoable());  
        btnRedo.setEnabled(prereqInfo.getEditHistory().isRedoable());                 
        btnAddRule.setEnabled(true);
        btnDelete.setEnabled(prereqInfo.statementVOIsDegroupAble());
//        btnSaveRule.setEnabled(false);  
        
        ruleTable.clear();
        Node tree = prereqInfo.getStatementTree();            
        if (tree != null) {
            if (prereqInfo.isSimplifying()) {
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
        
        requirementsRpcServiceAsync.getNaturalLanguageForStatementVO(model.getValue().getCluId(),
        									model.getValue().getStatementVO(), "KUALI.CATALOG", RuleComponentEditorView.TEMLATE_LANGUAGE, new AsyncCallback<String>() {
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
    	String luStatementTypeKey = model.getValue().getSelectedStatementType();
        if (luStatementTypeKey.contains("enroll")) return RuleConstants.KS_STATEMENT_TYPE_ENROLLREQ_TEXT;
        if (luStatementTypeKey.contains("prereq")) return RuleConstants.KS_STATEMENT_TYPE_PREREQ_TEXT;
        if (luStatementTypeKey.contains("coreq")) return RuleConstants.KS_STATEMENT_TYPE_COREQ_TEXT;
        if (luStatementTypeKey.contains("antireq")) return RuleConstants.KS_STATEMENT_TYPE_ANTIREQ_TEXT;
        return "";
    }
}
