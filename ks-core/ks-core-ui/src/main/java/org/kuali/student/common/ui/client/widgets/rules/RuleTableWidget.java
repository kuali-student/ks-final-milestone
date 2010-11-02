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
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class RuleTableWidget extends FlowPanel {

     //rule table manipulation buttons
    private KSButton btnMakeOR = new KSButton("OR");
    private KSButton btnMakeAND = new KSButton("AND");
    private KSButton btnAddToGroup = new KSButton("Add to Group");
    private KSButton btnMoveRuleDown = new KSButton("Down");
    private KSButton btnMoveRuleUp = new KSButton("Up");
    private KSButton btnUndo = new KSButton("Undo");
    private KSButton btnRedo = new KSButton("Redo");
    private KSButton btnDelete = new KSButton("Delete");
    private FlowPanel topButtonsPanel = new FlowPanel();
    private FlowPanel ruleTablePanel = new FlowPanel();

    private KSProgressIndicator twiddler = new KSProgressIndicator();

    //rule table
    private RuleTable  ruleTable = new RuleTable();
    private ClickHandler ruleTableSelectionHandler = null;
    private ClickHandler ruleTableEditClauseHandler = null;
    private HandlerRegistration textClickHandler = null;

    //view's data
    private RuleInfo rule = new RuleInfo();
    private Callback reqCompEditCallback;
    private Callback ruleChangedCallback;
    private boolean isEnabled = true;
    private boolean isOperatorChecked = false;

    public RuleTableWidget() {
        createButtonsPanel();

        twiddler.setVisible(false);
        SimplePanel twiddlerPanel = new SimplePanel();
        twiddlerPanel.setHeight("30px");
        twiddlerPanel.setWidget(twiddler);

        add(topButtonsPanel);

        ruleTablePanel.setStyleName("KS-Program-Rule-ObjectView-RulePanel");
        ruleTablePanel.add(ruleTable);
        add(ruleTablePanel);

        setupHandlers();
    }
  
    private void setupHandlers() {            
                       
        ruleTableSelectionHandler = new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                              
                Cell cell = ruleTable.getCellForEvent(event);
                if (cell == null) {
                    return;
                }

                RuleNodeWidget widget = (RuleNodeWidget) ruleTable.getWidget(cell.getRowIndex(), cell.getCellIndex());

                //we don't want to select a cell if user clicked on 'edit' link or the table is disabled for editing
                if (widget.isEditMode() || !isEnabled) {
                    widget.setEditMode(false);
                    return;
                }

                //select operator or rule
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
                    widget.setEditMode(true);
                    final ReqComponentVO rule = (ReqComponentVO) userObject;
                    reqCompEditCallback.exec(rule.getReqComponentInfo());
                }
            }
        };


        btnMoveRuleDown.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO statementVO = rule.getStatementVO();
                if (statementVO != null) {
                    //only allow moving up to 1 req. component but not operands
                    if (isAbletoMoveReqComp()) {
                        ReqComponentVO selectedReqCompVO = statementVO.getSelectedReqComponentVOs().get(0);
                        StatementVO enclosingStatementVO =  statementVO.getEnclosingStatementVO(statementVO, selectedReqCompVO);
                        enclosingStatementVO.shiftReqComponent("RIGHT", selectedReqCompVO);
                        rule.getEditHistory().save(statementVO);
                        redraw(rule.getStatementVO().getStatementTreeViewInfo(), false);
                    }
                }
            }
        });

        btnMoveRuleUp.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO statementVO = rule.getStatementVO();
                if (statementVO != null) {
                    //only allow moving up to 1 req. component but not operands
                    if (isAbletoMoveReqComp()) {
                        ReqComponentVO selectedReqCompVO = statementVO.getSelectedReqComponentVOs().get(0);
                        StatementVO enclosingStatementVO =  statementVO.getEnclosingStatementVO(statementVO, selectedReqCompVO);
                        enclosingStatementVO.shiftReqComponent("LEFT", selectedReqCompVO);
                        rule.getEditHistory().save(statementVO);
                        redraw(rule.getStatementVO().getStatementTreeViewInfo(), false);
                    }
                }
            }
        });
        
        btnMakeOR.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

                //did user select an operand to change from OR to AND ?
                if (isMatchingOperandSelected(Token.Or)) {                           
                    StatementVO statementVO = rule.getSelectedStatementVOs().get(0);
                    if (statementVO != null) {
                        statementVO.getStatementInfo().setOperator(StatementOperatorTypeKey.OR);
                        statementVO.toggleAndOr();
                    }
                } else {
                    rule.insertOR();
                }

                // clone a copy of the unsimplified form for showing intermediate step on the UI
                StatementVO unsimplified = RulesUtil.clone(rule.getStatementVO());
                boolean structureChanged = rule.getStatementVO().simplify();
                rule.getEditHistory().save(rule.getStatementVO());

                // sets the statementVO to be the version that hasn't been simplified yet temporarily
                if (structureChanged) {
                    showRuleBeforeSimplify(unsimplified);
                } else {
                    redraw(rule.getStatementVO().getStatementTreeViewInfo(), false);
                }
            }
        });
        
        btnMakeAND.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

                //did user select an operand to change from AND to OR ?
                if (isMatchingOperandSelected(Token.And)) {                           
                    StatementVO statementVO = rule.getSelectedStatementVOs().get(0);
                    if (statementVO != null) {
                        statementVO.getStatementInfo().setOperator(StatementOperatorTypeKey.AND);
                        statementVO.toggleAndOr();
                    }
                } else {
                    rule.insertAND();
                }

                // clone a copy of the unsimplified form for showing intermediate step on the UI
                StatementVO unsimplified = RulesUtil.clone(rule.getStatementVO());
                boolean structureChanged = rule.getStatementVO().simplify();
                rule.getEditHistory().save(rule.getStatementVO());

                // sets the statementVO to be the version that hasn't been simplified yet temporarily
                if (structureChanged) {
                    showRuleBeforeSimplify(unsimplified);
                } else {
                    redraw(rule.getStatementVO().getStatementTreeViewInfo(), false);
                }
            }
        });
        
        btnDelete.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                rule.deleteItem();

                // clone a copy of the unsimplified form for showing intermediate step on the UI
                StatementVO unsimplified = RulesUtil.clone(rule.getStatementVO());
                boolean structureChanged = false;
                if (rule.getStatementVO() != null) {
                    structureChanged = rule.getStatementVO().simplify();
                }

                rule.getEditHistory().save(rule.getStatementVO());

                // sets the statementVO to be the version that hasn't been simplified yet temporarily
                if (structureChanged) {
                    showRuleBeforeSimplify(unsimplified);
                } else {
                    redraw((rule.getStatementVO() == null ? null : rule.getStatementVO().getStatementTreeViewInfo()), false);
                }
            }
        });
        
        btnAddToGroup.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                rule.addToGroup();

                // clone a copy of the unsimplified form for showing intermediate step on the UI
                StatementVO unsimplified = RulesUtil.clone(rule.getStatementVO());
                boolean structureChanged = false;
                if (rule.getStatementVO() != null) {
                    structureChanged = rule.getStatementVO().simplify();
                }
                rule.getEditHistory().save(rule.getStatementVO());
                
                // sets the statementVO to be the version that hasn't been simplified yet temporarily
                if (structureChanged) {
                    showRuleBeforeSimplify(unsimplified);
                } else {
                    redraw(rule.getStatementVO().getStatementTreeViewInfo(), false);
                }
            }
        });
                
        btnUndo.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO previousState = rule.getEditHistory().undo();
                if (previousState != null) {
                    rule.setStatementVO(previousState);
                }
                redraw(rule.getStatementVO().getStatementTreeViewInfo(), false);
            }
        });
        
        btnRedo.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO nextState = rule.getEditHistory().redo();
                if (nextState != null) {
                    rule.setStatementVO(nextState);
                }
                redraw(rule.getStatementVO().getStatementTreeViewInfo(), false);
            }
        });        
    }

    // called by requirement display widget when user wants to edit a specific piece of rule
    public void redraw(StatementTreeViewInfo stmtTreeInfo, boolean newRule) {
        rule.getStatementVO().clearStatementAndReqComponents();
        rule.getStatementVO().setStatementTreeViewInfo(stmtTreeInfo);    //TODO remove req. compon.t
        if (newRule) {
            rule.setEditHistory(new EditHistory(rule.getStatementVO()));
        }

        rule.getStatementVO().clearSelections();
        updateTable();
    }
    
    private void updateTable() {        
        setEnableButtons(true);        
        ruleTable.clear();
        Node tree = rule.getStatementTree();
        if ((tree != null) && (rule.getStatementVO().getChildCount() > 0)) {

            //if we didn't have rule before, now we do so add back the rule table
            if (ruleTablePanel.getWidgetIndex(ruleTable) == -1) {
                ruleTablePanel.clear();                
                ruleTablePanel.add(ruleTable);   
            }

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
            ruleTable.addEditClauseHandler(ruleTableEditClauseHandler);
            ruleChangedCallback.exec(false);
        } else { //no rule exist so don't show rule table and show a message instead
            ruleTablePanel.clear();
            ruleTablePanel.add(new KSLabel("No rules have been added"));
            ruleChangedCallback.exec(true);
        }
    }
    
    private void showRuleBeforeSimplify(StatementVO unsimplified) {
        rule.setStatementVO(unsimplified);
        rule.setSimplifying(true);
        redraw(rule.getStatementVO().getStatementTreeViewInfo(), false);
        // sleep for a while to show the user how the rule looks like before simplification
        Timer simplifyingTimer = new Timer() {
            public void run() {
                rule.setSimplifying(false);
                rule.setStatementVO(rule.getEditHistory().getLastHistoricStmtVO());
                redraw(rule.getStatementVO().getStatementTreeViewInfo(), false);
            }
        };
        simplifyingTimer.schedule(1000);
    }

    public void setEnabledView(boolean enabled) {
        setEnableButtons(enabled);
        ruleTable.setEnabled(enabled);
        isEnabled = enabled;
        //TODO enable/disable buttons in Edit With Logic view
    }

    public void setEnableButtons(boolean enabled) {
        if (enabled) {
            btnMakeAND.setEnabled(isMatchingOperandSelected(Token.And) || rule.statementVOIsGroupAble());
            btnMakeOR.setEnabled(isMatchingOperandSelected(Token.Or) || rule.statementVOIsGroupAble());
            btnAddToGroup.setEnabled(rule.isAddToGroupOK());
            btnUndo.setEnabled(rule.getEditHistory().isUndoable());
            btnRedo.setEnabled(rule.getEditHistory().isRedoable());
            btnDelete.setEnabled(isAbleToDelete());
            btnMoveRuleUp.setEnabled(rule.getStatementVO().isNodeSelected() && !rule.getStatementVO().isFirstSelectedReqComp() && isAbletoMoveReqComp());
            btnMoveRuleDown.setEnabled(rule.getStatementVO().isNodeSelected() && !rule.getStatementVO().isLastSelectedReqComp() && isAbletoMoveReqComp());
        } else {
            btnMakeAND.setEnabled(false);
            btnMakeOR.setEnabled(false);
            btnAddToGroup.setEnabled(false);
            btnUndo.setEnabled(false);
            btnRedo.setEnabled(false);
            btnDelete.setEnabled(false);
            btnMoveRuleUp.setEnabled(false);
            btnMoveRuleDown.setEnabled(false);
        }
    }

    //right now only allow moving of up to 1 req. component but not operands
    private boolean isAbletoMoveReqComp() {
        List<ReqComponentVO> selectedRCs = rule.getStatementVO().getSelectedReqComponentVOs();
        List<StatementVO> selectedSs = rule.getStatementVO().getSelectedStatementVOs();
        int numSelectedRCs = (selectedRCs == null)? 0 : selectedRCs.size();
        int numSelectedSs = (selectedSs == null)? 0 : selectedSs.size();
        return (numSelectedRCs == 1 && numSelectedSs == 0);       
    }

    //check whether user selected only 1 operator of opossite type    
    private boolean isMatchingOperandSelected(int type) {
        List<StatementVO> selectedStmts = rule.getSelectedStatementVOs();
        if (((rule.getSelectedReqComponentVOs() == null) || (rule.getSelectedReqComponentVOs().isEmpty())) && (selectedStmts.size() == 1)
                && (((StatementVO)selectedStmts.get(0)).getType() != type)) {
            return true;
        }
        return false;
    }

    private boolean isAbleToDelete() {
        //return ((rule.getSelectedStatementVOs().size() > 0) || (rule.getSelectedReqComponentVOs().size() > 0));
        if ((rule.getSelectedStatementVOs() == null || rule.getSelectedStatementVOs().size() == 0) && (rule.getSelectedReqComponentVOs().size() > 1)) {
            return true;
        }

        return rule.statementVOIsDegroupAble();
    }

    private void createButtonsPanel() {
        topButtonsPanel.setStyleName("KS-Program-Rule-ObjectView-ButtonPanel");
        btnMakeOR.addStyleName("KS-Program-Rule-ObjectView-OR-Button");
        topButtonsPanel.add(btnMakeOR);
        btnMakeAND.addStyleName("KS-Program-Rule-ObjectView-AND-Button");
        topButtonsPanel.add(btnMakeAND);
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

    public void addRuleChangedButtonClickCallback(Callback<Boolean> callback) {
        ruleChangedCallback = callback;
    }
}
