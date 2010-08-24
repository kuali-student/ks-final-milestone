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

package org.kuali.student.common.ui.client.widgets.rules;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.SimplePanel;

public class RuleTableWidget extends FlowPanel {

  //TODO remove?  private CluSetManagementRpcServiceAsync cluSetManagementRpcServiceAsync = GWT.create(CluSetManagementRpcService.class);

    //rule table manipulation buttons
    private KSButton btnAddOR = new KSButton("OR");
    private KSButton btnAddAND = new KSButton("AND");
    private KSButton btnAddToGroup = new KSButton("Add to Group");
    private KSButton btnMoveRuleDown = new KSButton();
    private KSButton btnMoveRuleUp = new KSButton();
    private KSButton btnUndo = new KSButton("Undo");
    private KSButton btnRedo = new KSButton("Redo");
    private KSButton btnDelete = new KSButton("Delete");
    private SpanPanel topButtonsPanel = new SpanPanel();

    private SimplePanel twiddlerPanel = new SimplePanel();
    private KSProgressIndicator twiddler = new KSProgressIndicator();

    //rule table
    private RuleTable  ruleTable = new RuleTable();
    private ClickHandler ruleTableSelectionHandler = null;
    private ClickHandler ruleTableToggleClickHandler = null;
    private ClickHandler ruleTableEditClauseHandler = null;
    private HandlerRegistration textClickHandler = null;

    //view's data
    private RuleInfo rule = null;

    public RuleTableWidget() {
        rule = new RuleInfo();
        rule.setCluId("123");
        rule.setId(Integer.toString(123)); //id++));
        rule.setEditHistory(new EditHistory());
        rule.setSelectedStatementType(null);
        StatementVO statementVO = new StatementVO();
        rule.setStatementVO(statementVO);
        createButtonsPanel();
    }
  
    public void setupHandlers() {            
               
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
    }

    public void redraw() {

        //setup rule table buttons

        add(topButtonsPanel);

        twiddler = new KSProgressIndicator();
        twiddler.setVisible(false);
        twiddlerPanel.setHeight("30px");
        twiddlerPanel.setWidget(twiddler);

        SpanPanel ruleTablePanel = new SpanPanel();
        ruleTablePanel.setStyleName("KS-Program-Rule-ObjectView-RulePanel");
        ruleTablePanel.add(ruleTable);
        add(ruleTablePanel);

        updateTable();
    }

    private void createButtonsPanel() {
        topButtonsPanel.setStyleName("KS-Program-Rule-ObjectView-ButtonPanel");
        btnAddOR.addStyleName("KS-Program-Rule-ObjectView-OR-Button");
        topButtonsPanel.add(btnAddOR);
        btnAddAND.addStyleName("KS-Program-Rule-ObjectView-AND-Button");
        topButtonsPanel.add(btnAddAND);
        btnAddToGroup.addStyleName("KS-Program-Rule-ObjectView-Group-Button");
        topButtonsPanel.add(btnAddToGroup);
        btnMoveRuleDown.addStyleName("KS-Program-Rule-ObjectView-Down-Button");
        topButtonsPanel.add(btnMoveRuleDown);
        btnMoveRuleUp.addStyleName("KS-Program-Rule-ObjectView-Up-Button");
        topButtonsPanel.add(btnMoveRuleUp);
        btnUndo.addStyleName("KS-Program-Rule-ObjectView-Undo-Button");
        topButtonsPanel.add(btnUndo);
        btnRedo.addStyleName("KS-Program-Rule-ObjectView-Redo-Button");
        topButtonsPanel.add(btnRedo);
        btnDelete.addStyleName("KS-Program-Rule-ObjectView-Delete-Button");
        topButtonsPanel.add(btnDelete);
    }
    
    private void updateTable() {        
    //TODO        btnAddAND.setEnabled(rule.statementVOIsGroupAble());
    //TODO        btnAddOR.setEnabled(rule.statementVOIsGroupAble());
    //TODO        btnAddToGroup.setEnabled(rule.isAddToGroupOK());
    //TODO        btnUndo.setEnabled(rule.getEditHistory().isUndoable());
    //TODO        btnRedo.setEnabled(rule.getEditHistory().isRedoable());
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
//            textClickHandler.removeHandler();
            ruleTable.addTextClickHandler(ruleTableSelectionHandler);
            ruleTable.addToggleHandler(ruleTableToggleClickHandler);
            ruleTable.addEditClauseHandler(ruleTableEditClauseHandler);                
        }        
    }
    
    public void showUnSimpTemporarily(StatementVO unsimplified) {
        rule.setStatementVO(unsimplified);
        rule.setSimplifying(true);
        redraw();
        // sleep for a while to show the user how the rule looks like before simplification
        Timer simplifyingTimer = new Timer() {
            public void run() {
                rule.setSimplifying(false);
                rule.setStatementVO(rule.getEditHistory().getCurrentState());
                redraw();
            }
        };
        simplifyingTimer.schedule(1000);
    }

    // called by requirement display widget when user wants to edit a specific piece of rule
    public void setRuleTree(StatementTreeViewInfo stmtTreeInfo) {
        rule.getStatementVO().setStatementTreeViewInfo(stmtTreeInfo);
    }

    public RuleInfo getRule() {
        return rule;
    }
}
