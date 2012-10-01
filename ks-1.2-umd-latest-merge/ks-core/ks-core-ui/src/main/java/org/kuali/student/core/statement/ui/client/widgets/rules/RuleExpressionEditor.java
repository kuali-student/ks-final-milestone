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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.core.statement.dto.ReqComponentInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.*;

public class RuleExpressionEditor extends FlowPanel {

    //view's widgets
    private KSTextArea expressionTextBox = new KSTextArea();
    private HTML htmlErrorMessage = new HTML();    
    private KSButton btnUpdate = new KSButton("Update");
    private KSButton btnUndo = new KSButton("Undo");
    private FlowPanel ruleTablePanel = new FlowPanel();
    private KSLabel noRuleText = new KSLabel("");
    private Panel pnlMissingExpressions = new VerticalPanel();

    //rule table
    private RuleTable  ruleTable = new RuleTable(false);
    private ClickHandler ruleTableEditClauseHandler = null;

    //view's data
    private RuleInfo rule;
    private String previewExpression;
    private Callback reqCompEditCallback;
    private Callback ruleChangedCallback;
    private RuleManageWidget parent;
    private boolean ruleChanged = false;

    // helper object
    private RuleExpressionParser ruleExpressionParser = new RuleExpressionParser();
       
    public RuleExpressionEditor(RuleManageWidget parent) {
        this.parent = parent;

        FlowPanel expressionBox = new FlowPanel();
        HorizontalPanel expressionAndMsg = new HorizontalPanel();
        expressionBox.setStyleName("KS-Program-Rule-LogicView-ExpressionPanel");
        expressionTextBox.ensureDebugId("Expression Text Area");
        expressionTextBox.addStyleName("KS-Program-Rule-LogicView-ExpressionText");
        expressionAndMsg.add(expressionTextBox);
        expressionAndMsg.add(htmlErrorMessage);
        expressionBox.add(expressionAndMsg);
        btnUpdate.addStyleName("KS-Program-Rule-LogicView-Update-Button");
        FlowPanel buttonsPanel = new FlowPanel();
        buttonsPanel.add(btnUpdate);
        btnUndo.addStyleName("KS-Program-Rule-LogicView-Undo-Button");
        buttonsPanel.add(btnUndo);
        buttonsPanel.addStyleName("KS-Program-Rule-LogicView-ButtonPanel");
        expressionBox.add(buttonsPanel);
        add(expressionBox);

        ruleTablePanel.setStyleName("KS-Program-Rule-LogicView-RulePanel");
        ruleTablePanel.add(ruleTable);
        ruleTablePanel.add(noRuleText);
        ruleTablePanel.add(pnlMissingExpressions);
        add(ruleTablePanel);

        previewExpression = "";

        setupHandlers();
    }

    public void setRule(RuleInfo rule) {
        this.rule = rule;
        redraw();
    }

    private void setupHandlers() {
        ruleTableEditClauseHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                HTMLTable.Cell cell = ruleTable.getCellForEvent(event);
                if (cell == null) {
                    return;
                }

                RuleNodeWidget widget = (RuleNodeWidget) ruleTable.getWidget(cell.getRowIndex(), cell.getCellIndex());
                Object userObject = widget.getNode().getUserObject();
                if (userObject instanceof ReqComponentVO) {
                    widget.setEditMode(true);
                    final ReqComponentVO tempRule = (ReqComponentVO) userObject;
                    reqCompEditCallback.exec(tempRule.getReqComponentInfo());
                }
            }
        };                        
        expressionTextBox.addKeyUpHandler(new KeyUpHandler() {
            public void onKeyUp(KeyUpEvent event) {
                // escape error keys
                if(event.getNativeKeyCode() == 37 
                        ||event.getNativeKeyCode() == 38
                        ||event.getNativeKeyCode() == 39
                        ||event.getNativeKeyCode() == 40){
                        return;
                }
                btnUpdate.setEnabled(true);
            }
        });
        btnUpdate.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

                //get and format logic expression of the rule
                previewExpression = expressionTextBox.getText();
                previewExpression = previewExpression.replaceAll("\n", " ");
                previewExpression = previewExpression.replaceAll("\r", " ");
                ruleExpressionParser.setExpression(previewExpression);

                //validate the expression
                List<ReqComponentVO> rcs = (rule.getStatementVO() == null || rule.getStatementVO().getAllReqComponentVOs() == null) ?
                                                new ArrayList<ReqComponentVO>() : rule.getStatementVO().getAllReqComponentVOs();
                List<String> errorMessages = new ArrayList<String>();
                boolean validExpression = ruleExpressionParser.validateExpression(errorMessages, rcs);

                //show errors and don't change anything else
                if (!validExpression) {

                    //show missing 'subrules' that were removed from the expression box by the user
                    List<ReqComponentVO> missingRCs = new ArrayList<ReqComponentVO>();
                    rcs = (rule.getStatementVO() == null || rule.getStatementVO().getAllReqComponentVOs() == null) ?
                                                    new ArrayList<ReqComponentVO>() : rule.getStatementVO().getAllReqComponentVOs();
                    ruleExpressionParser.checkMissingRCs(missingRCs, rcs);
                    showMissingRCs(missingRCs);

                    showErrors(errorMessages);
                    return;
                }

                //update the rule based on the updated logic expression
                StatementVO newStatementVO = ruleExpressionParser.parseExpressionIntoStatementVO(previewExpression, rule.getStatementVO(), rule.getStatementTypeKey());
                rule.setStatementVO(newStatementVO);
                rule.getEditHistory().save(newStatementVO);

                //display rule table for updated rule
                redraw();

                parent.updateObjectRule(rule);
                ruleChanged = true;
                ruleChangedCallback.exec(true);
            }
        });
        
        btnUndo.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StatementVO previousState = rule.getEditHistory().undo();
                if (previousState != null) {
                    rule.setStatementVO(previousState);
                }
                redraw();
                parent.updateObjectRule(rule);
                ruleChanged = true;
                ruleChangedCallback.exec(true);
            }
        });
    }

    private void redraw() {
        htmlErrorMessage.setHTML("");
        ruleTable.clear();
        noRuleText.setText("");
        ruleChanged = false;
        setEnabledView(true);
        expressionTextBox.setText(rule.getExpression().trim().isEmpty() ? "" : rule.getExpression().trim());        

        if (rule.getExpression().trim().isEmpty()) {
            noRuleText.setText("No rules have been added");
            btnUpdate.setEnabled(false);
            return;
        }

        btnUpdate.setEnabled(false);    //redraw rule means no outstanding change to the expression    
        ruleTable.buildTable(rule.getStatementTree());
        ruleTable.addEditClauseHandler(ruleTableEditClauseHandler);
    }

    private void showMissingRCs(List<ReqComponentVO> missingRCs) {
        VerticalPanel pnlRCList = new VerticalPanel();
        boolean missingExprFound = false;       
        pnlMissingExpressions.clear();

        for (ReqComponentVO rc : missingRCs) {
            HorizontalPanel pnlRcListRow = new HorizontalPanel();
            if (rc.getGuiReferenceLabelId() != null) {
                KSLabel rcLabel = new KSLabel(rc.getGuiReferenceLabelId());
                rcLabel.setStyleName("KS-Program-Rule-LogicView-Rule-ID");
                pnlRcListRow.add(rcLabel);
                missingExprFound = true;
            }
            pnlRCList.add(pnlRcListRow);
        }
        
        if (missingExprFound) {
            KSLabel missingExprTitle = new KSLabel("The following rules do not exist");
            missingExprTitle.setStyleName("KS-Program-Rule-LogicView-Missing-Rules");
            pnlMissingExpressions.add(missingExprTitle);                        
            pnlMissingExpressions.add(pnlRCList);
        }        
    }
    
    private void showErrors(List<String> errorMessages) {
        StringBuilder sb = new StringBuilder("");
        boolean firstRow = true;
        for (String errorMessage : errorMessages) {
            if (!firstRow) {
                sb.append(",<BR>");
            }
            firstRow = false;
            sb.append(errorMessage);
        }
        htmlErrorMessage.getElement().getStyle().setProperty("color", "red");
        htmlErrorMessage.setHTML(sb.toString());
    }

    private void setEnableButtons(boolean enabled) {
        if (enabled) {
            btnUpdate.setEnabled(enabled);
            btnUndo.setEnabled(rule.getEditHistory().isUndoable()); 
        } else {
            btnUpdate.setEnabled(false);
            btnUndo.setEnabled(false);
        }
    }

    public void setEnabledView(boolean enabled) {
        setEnableButtons(enabled);
        ruleTable.setEnabled(enabled);
        expressionTextBox.setEnabled(enabled);
    }

    public void addReqCompEditButtonClickCallback(Callback<ReqComponentInfo> callback) {
        reqCompEditCallback = callback;
    }

    public void addRuleChangedButtonClickCallback(Callback<Boolean> callback) {
        ruleChangedCallback = callback;
    }
}
