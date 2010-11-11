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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.table.Node;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;

public class RuleExpressionEditor extends FlowPanel {

    //view's widgets
    private KSTextArea expressionTextBox = new KSTextArea();
    private HTML htmlErrorMessage = new HTML();    
    private KSButton btnUpdate = new KSButton("Update");
    private KSButton btnUndo = new KSButton("Undo");
    private FlowPanel ruleTablePanel = new FlowPanel();
    private Panel pnlMissingExpressions = new VerticalPanel();

    //rule table
    private RuleTable  ruleTable = new RuleTable(false);
    private ClickHandler ruleTableSelectionHandler = null;
    private ClickHandler ruleTableEditClauseHandler = null;
    private HandlerRegistration textClickHandler = null;

    //view's data
    private RuleInfo rule;
    private String previewExpression;
    private Callback reqCompEditCallback;
    private Callback ruleChangedCallback;
    private boolean isEnabled = true;
    private boolean isOperatorChecked = false;

    // helper object
    private RuleExpressionParser ruleExpressionParser = new RuleExpressionParser();
       
    public RuleExpressionEditor() {

        FlowPanel expressionBox = new FlowPanel();
        expressionBox.setStyleName("KS-Program-Rule-LogicView-ExpressionPanel");
        expressionTextBox.addStyleName("KS-Program-Rule-LogicView-ExpressionText");
        expressionBox.add(expressionTextBox);
        expressionBox.add(htmlErrorMessage);
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
        expressionTextBox.addKeyUpHandler(new KeyUpHandler() {
            public void onKeyUp(KeyUpEvent event) {
                // escape error keys
                if(event.getNativeKeyCode() == 37 
                        ||event.getNativeKeyCode() == 38
                        ||event.getNativeKeyCode() == 39
                        ||event.getNativeKeyCode() == 40){
                        return;
                }
                String expression = expressionTextBox.getText();
            }
        });
        btnUpdate.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                /*
                expression = prereqInfo.getExpression();
                prereqInfo.setPreviewedExpression(expression);
                redraw(); */
            }
        });
        
        btnUndo.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                /*
                RuleInfo prereqInfo = model.getValue();
                List<String> errorMessages = new ArrayList<String>();
                List<ReqComponentVO> rcs = (prereqInfo.getStatementVO() == null ||
                            				prereqInfo.getStatementVO().getAllReqComponentVOs() == null)?
                            						new ArrayList<ReqComponentVO>() :
                            								prereqInfo.getStatementVO().getAllReqComponentVOs();
                ruleExpressionParser.setExpression(prereqInfo.getExpression());
                boolean validExpression = ruleExpressionParser.validateExpression(errorMessages, rcs);
                List<ReqComponentVO> missingRCs = new ArrayList<ReqComponentVO>();
                ruleExpressionParser.checkMissingRCs(missingRCs, rcs);
                
                if (validExpression && missingRCs.isEmpty()) {
                    StatementVO newStatementVO = ruleExpressionParser.parseExpressionIntoStatementVO(
                            prereqInfo.getExpression(), prereqInfo.getStatementVO(), prereqInfo.getSelectedStatementType());
                    prereqInfo.setStatementVO(newStatementVO);
                    prereqInfo.setPreviewedExpression(null);
                    prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
                    getController().showView(PrereqViews.MANAGE_RULES, Controller.NO_OP_CALLBACK);
                } else {
                    String expression = prereqInfo.getExpression();
                    prereqInfo.setPreviewedExpression(expression);
                    redraw();
                } */
            }
        });
    }
    
    private void redraw() {

        String ruleExpression = rule.getExpression().trim();
        expressionTextBox.setText(ruleExpression.isEmpty() ? "" : ruleExpression);

        htmlErrorMessage.setHTML("");
        ruleTable.clear();

        if (ruleExpression.isEmpty()) {
            ruleTablePanel.add(new KSLabel("No rules have been added"));
            setEnabledView(false);
            return;
        }

        setEnableButtons(false);
        btnUndo.setEnabled(rule.getEditHistory().isUndoable());           
        previewExpression = expressionTextBox.getText();
        previewExpression = previewExpression.replaceAll("\n", " ");
        previewExpression = previewExpression.replaceAll("\r", " ");
        ruleExpressionParser.setExpression(previewExpression);

        List<ReqComponentVO> rcs = (rule.getStatementVO() == null || rule.getStatementVO().getAllReqComponentVOs() == null) ?
                                        new ArrayList<ReqComponentVO>() : rule.getStatementVO().getAllReqComponentVOs();
        List<String> errorMessages = new ArrayList<String>();
        boolean validExpression = ruleExpressionParser.validateExpression(errorMessages, rcs);
        if (!validExpression) {
            showErrors(errorMessages);
        } else {
            Node tree = ruleExpressionParser.parseExpressionIntoTree(previewExpression, rule.getStatementVO(), rule.getStatementTypeKey());
            if (tree != null) {
                ruleTable.buildTable(tree);
            }
        }

        List<ReqComponentVO> missingRCs = new ArrayList<ReqComponentVO>();
        ruleExpressionParser.checkMissingRCs(missingRCs, rcs);
        showMissingRCs(missingRCs);
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
        for (String errorMessage : errorMessages) {
            sb.append(errorMessage).append(",<BR>");
        }
        htmlErrorMessage.getElement().getStyle().setProperty("color", "red");
        htmlErrorMessage.setHTML(sb.toString());
    }

    private void setEnableButtons(boolean enabled) {
        btnUpdate.setEnabled(enabled);
        btnUndo.setEnabled(enabled);
    }

    public void setEnabledView(boolean enabled) {
        setEnableButtons(enabled);
        ruleTable.setEnabled(enabled);
        expressionTextBox.setEnabled(enabled);
        isEnabled = enabled;
    }
}
