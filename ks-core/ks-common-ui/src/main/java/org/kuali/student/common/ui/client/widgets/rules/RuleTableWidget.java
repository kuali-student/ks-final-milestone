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

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.SimplePanel;

public class RuleTableWidget extends FlowPanel {

     //rule table manipulation buttons
    private KSButton btnAddOR = new KSButton("OR");
    private KSButton btnAddAND = new KSButton("AND");
    private KSButton btnAddToGroup = new KSButton("Add to Group");
    private KSButton btnMoveRuleDown = new KSButton();
    private KSButton btnMoveRuleUp = new KSButton();
    private KSButton btnUndo = new KSButton("Undo");
    private KSButton btnRedo = new KSButton("Redo");
    private KSButton btnDelete = new KSButton("Delete");
    private FlowPanel topButtonsPanel = new FlowPanel();

    private KSProgressIndicator twiddler = new KSProgressIndicator();

    //rule table
    private RuleTable  ruleTable = new RuleTable();
    private ClickHandler ruleTableSelectionHandler = null;
    private ClickHandler ruleTableToggleClickHandler = null;
    private ClickHandler ruleTableEditClauseHandler = null;
    private HandlerRegistration textClickHandler = null;

    //view's data
    private RuleInfo rule = new RuleInfo();
    private Callback reqCompEditCallback;

    public RuleTableWidget() {
        createButtonsPanel();

        twiddler.setVisible(false);
        SimplePanel twiddlerPanel = new SimplePanel();
        twiddlerPanel.setHeight("30px");
        twiddlerPanel.setWidget(twiddler);

        add(topButtonsPanel);

        FlowPanel ruleTablePanel = new FlowPanel();
        ruleTablePanel.setStyleName("KS-Program-Rule-ObjectView-RulePanel");
        ruleTablePanel.add(ruleTable);
        add(ruleTablePanel);

        setupHandlers();
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
                        showRuleBeforeSimplify(unsimplified);
                    }
                    redraw(rule.getStatementVO().getStatementTreeViewInfo());
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
                    reqCompEditCallback.exec(rule.getReqComponentInfo());
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
                        redraw(rule.getStatementVO().getStatementTreeViewInfo());
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
                        redraw(rule.getStatementVO().getStatementTreeViewInfo());
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
                    showRuleBeforeSimplify(unsimplified);
                } else {
                    redraw(rule.getStatementVO().getStatementTreeViewInfo());
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
                    showRuleBeforeSimplify(unsimplified);
                } else {
                    redraw(rule.getStatementVO().getStatementTreeViewInfo());
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
                    showRuleBeforeSimplify(unsimplified);
                } else {
                    redraw((rule.getStatementVO() == null ? null : rule.getStatementVO().getStatementTreeViewInfo()));
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
                    showRuleBeforeSimplify(unsimplified);
                } else {
                    redraw(rule.getStatementVO().getStatementTreeViewInfo());
                }
            }
        });
                
        btnUndo.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO previousState = rule.getEditHistory().undo();
                if (previousState != null) {
                    rule.setStatementVO(previousState);
                }
                redraw(rule.getStatementVO().getStatementTreeViewInfo());
            }
        });
        
        btnRedo.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO nextState = rule.getEditHistory().redo();
                if (nextState != null) {
                    rule.setStatementVO(nextState);
                }
                redraw(rule.getStatementVO().getStatementTreeViewInfo());
            }
        });        
    }

    // called by requirement display widget when user wants to edit a specific piece of rule
    public void redraw(StatementTreeViewInfo stmtTreeInfo) {
        rule.getStatementVO().setStatementTreeViewInfo(stmtTreeInfo);
        updateTable();
    }
    
    private void updateTable() {        
        btnAddAND.setEnabled(rule.statementVOIsGroupAble());
        btnAddOR.setEnabled(rule.statementVOIsGroupAble());
        btnAddToGroup.setEnabled(rule.isAddToGroupOK());
        btnUndo.setEnabled(rule.getEditHistory().isUndoable());
        btnRedo.setEnabled(rule.getEditHistory().isRedoable());
        btnDelete.setEnabled(rule.statementVOIsDegroupAble());
        //btnSaveRule.setEnabled(false);
        
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
    
    private void showRuleBeforeSimplify(StatementVO unsimplified) {
        rule.setStatementVO(unsimplified);
        rule.setSimplifying(true);
        redraw(null);
        // sleep for a while to show the user how the rule looks like before simplification
        Timer simplifyingTimer = new Timer() {
            public void run() {
                rule.setSimplifying(false);
                rule.setStatementVO(rule.getEditHistory().getCurrentState());
                redraw(null);
            }
        };
        simplifyingTimer.schedule(1000);
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

    public RuleInfo getRule() {
        return rule;
    }

    public void addReqCompEditButtonClickCallback(Callback<ReqComponentInfo> callback) {
        reqCompEditCallback = callback;
    }
}
