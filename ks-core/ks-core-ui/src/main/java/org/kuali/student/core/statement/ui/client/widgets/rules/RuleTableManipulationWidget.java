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

package org.kuali.student.core.statement.ui.client.widgets.rules;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.core.statement.ui.client.widgets.table.Node;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class RuleTableManipulationWidget extends FlowPanel {

     //rule table manipulation buttons
    private KSButton btnMakeOR = new KSButton("OR");
    private KSButton btnMakeAND = new KSButton("AND");
    private KSButton btnAddToGroup = new KSButton("Group");
    private KSButton btnMoveRuleDown = new KSButton("Move Down");
    private KSButton btnMoveRuleUp = new KSButton("Move Up");
    private KSButton btnUndo = new KSButton("Undo");
    private KSButton btnRedo = new KSButton("Redo");
    private KSButton btnDelete = new KSButton("Delete");
    private SpanPanel separator1 = new SpanPanel(" | ");
    private SpanPanel separator2 = new SpanPanel(" | ");
    private FlowPanel topButtonsPanel = new FlowPanel();
    private FlowPanel ruleTablePanel = new FlowPanel();

    private KSProgressIndicator twiddler = new KSProgressIndicator();

    //rule table
    private RuleTable  ruleTable = new RuleTable(true);
    private ClickHandler ruleTableSelectionHandler = null;
    private ClickHandler ruleTableEditClauseHandler = null;
    private HandlerRegistration textClickHandler = null;

    //view's data
    private RuleInfo rule = new RuleInfo();
    private Callback reqCompEditCallback;
    private Callback ruleChangedCallback;
    private boolean isEnabled = true;
    private boolean isOperatorChecked = false;

    public RuleTableManipulationWidget() {
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
                        redraw(rule.getStatementVO().getStatementTreeViewInfo(), false, true);
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
                        redraw(rule.getStatementVO().getStatementTreeViewInfo(), false, true);
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
                    redraw(rule.getStatementVO().getStatementTreeViewInfo(), false, true);
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
                    redraw(rule.getStatementVO().getStatementTreeViewInfo(), false, true);
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
                    redraw((rule.getStatementVO() == null ? null : rule.getStatementVO().getStatementTreeViewInfo()), false, true);
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
                    redraw(rule.getStatementVO().getStatementTreeViewInfo(), false, true);
                }
            }
        });

        btnUndo.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO previousState = rule.getEditHistory().undo();
                if (previousState != null) {
                    rule.setStatementVO(previousState);
                }
                redraw(rule.getStatementVO().getStatementTreeViewInfo(), false, true);
            }
        });

        btnRedo.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO nextState = rule.getEditHistory().redo();
                if (nextState != null) {
                    rule.setStatementVO(nextState);
                }
                redraw(rule.getStatementVO().getStatementTreeViewInfo(), false, true);
            }
        });
    }

    //called when user switches between Logic/Preview/Object view
    public void redraw() {
        updateTable();
    }

    // called by requirement display widget when user wants to edit a specific piece of rule
    public void redraw(StatementTreeViewInfo stmtTreeInfo, boolean newRule, Boolean ruleChanged) {
        rule.getStatementVO().clearStatementAndReqComponents();
        rule.getStatementVO().setStatementTreeViewInfo(stmtTreeInfo);    //TODO remove req. compon.t
        if (newRule) {
            rule.setEditHistory(new EditHistory(rule.getStatementVO()));
        }

        rule.getStatementVO().clearSelections();
        updateTable();

        if (ruleChanged != null) {
            ruleChangedCallback.exec(ruleChanged);
        }
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
        } else { //no rule exist so don't show rule table and show a message instead
            ruleTablePanel.clear();
            ruleTablePanel.add(new KSLabel("No rules have been added"));
        }
    }

    private void showRuleBeforeSimplify(StatementVO unsimplified) {
        rule.setStatementVO(unsimplified);
        rule.setSimplifying(true);
        redraw(rule.getStatementVO().getStatementTreeViewInfo(), false, false);
        // sleep for a while to show the user how the rule looks like before simplification
        Timer simplifyingTimer = new Timer() {
            public void run() {
                rule.setSimplifying(false);
                rule.setStatementVO(rule.getEditHistory().getLastHistoricStmtVO());
                redraw(rule.getStatementVO().getStatementTreeViewInfo(), false, true);
            }
        };
        simplifyingTimer.schedule(1000);
      }

    public void setEnabledView(boolean enabled) {
        setEnableButtons(enabled);
        ruleTable.setEnabled(enabled);
        isEnabled = enabled;
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
        int firstStmt = 0;
        if (((rule.getSelectedReqComponentVOs() == null) || (rule.getSelectedReqComponentVOs().isEmpty())) && (selectedStmts.size() == 1)
                && (((StatementVO)selectedStmts.get(firstStmt)).getType() != type)) {
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
        final String ruleEditButton = "KS-Program-Rule-ObjectView-Button";

        HorizontalPanel layout = new HorizontalPanel();
        
        topButtonsPanel.setStyleName("KS-Program-Rule-ObjectView-ButtonPanel");
        btnMakeAND.addStyleName(ruleEditButton);
        btnMakeAND.addStyleName("KS-Program-Rule-ObjectView-AND-Button");
        layout.add(btnMakeAND);

        btnMakeOR.addStyleName("KS-Program-Rule-ObjectView-OR-Button");
        btnMakeOR.addStyleName(ruleEditButton);
        layout.add(btnMakeOR);

        btnAddToGroup.addStyleName("KS-Program-Rule-ObjectView-Group-Button");
        btnAddToGroup.addStyleName(ruleEditButton);
        layout.add(btnAddToGroup);

        btnMoveRuleUp.addStyleName("KS-Program-Rule-ObjectView-Up-Button");
        btnMoveRuleUp.addStyleName(ruleEditButton);
        layout.add(btnMoveRuleUp);

        btnMoveRuleDown.addStyleName("KS-Program-Rule-ObjectView-Down-Button");
        btnMoveRuleDown.addStyleName(ruleEditButton);
        layout.add(btnMoveRuleDown);

        btnUndo.addStyleName("KS-Program-Rule-ObjectView-Undo-Button");
        btnUndo.addStyleName(ruleEditButton);
        layout.add(btnUndo);

        btnRedo.addStyleName("KS-Program-Rule-ObjectView-Redo-Button");
        btnRedo.addStyleName(ruleEditButton);
        layout.add(btnRedo);

        btnDelete.addStyleName("KS-Program-Rule-ObjectView-Delete-Button");
        btnDelete.addStyleName(ruleEditButton);
        layout.add(btnDelete);
        
        topButtonsPanel.add(layout);
    }

    public RuleInfo getRule() {
        return rule;
    }

    public void setRule(RuleInfo rule) {
        this.rule = rule;
    }

    public void addReqCompEditButtonClickCallback(Callback<ReqComponentInfo> callback) {
        reqCompEditCallback = callback;
    }

    public void addRuleChangedButtonClickCallback(Callback<Boolean> callback) {
        ruleChangedCallback = callback;
    }
}
