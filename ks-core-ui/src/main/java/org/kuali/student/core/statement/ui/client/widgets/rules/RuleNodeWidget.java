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

import org.kuali.student.common.ui.client.util.DebugIdUtils;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.core.statement.ui.client.widgets.table.Node;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RuleNodeWidget extends FocusPanel {

    //widgets
    private Node node;
    private HTML html = new HTML();
    private HorizontalPanel checkBoxAndEdit;
    private CheckBox checkBox = new CheckBox();
    private KSLabel edit = new KSLabel("Edit");
    private KSLabel toggle = new KSLabel("");

    //data
    private HandlerRegistration editClauseHandlerRegistration;
    private ClickHandler editClickHandler;
    private boolean editMode = false;
    private boolean showCheckbox;

    public RuleNodeWidget(Node n) {
        init(n, true);
    }
    
    public RuleNodeWidget(Node n, boolean showControls) {
        init(n, showControls);
    }
    
    private void init(Node n, boolean showControls) {
        node = n;
        this.showCheckbox = showControls;
        drawNode(n, null, -1, -1); 
    }
    
    public void drawNode(Node n, RuleTable ruleTable, int rowIndex, int columnIndex) {
        node = n;

        if (!(node.getUserObject() instanceof Token)) {
            super.setWidget(html);
            html.setHTML(node.getUserObject().toString());
            checkBox.setHTML(node.getUserObject().toString());
            checkBox.ensureDebugId(DebugIdUtils.createWebDriverSafeDebugId(checkBox.getHTML()));
            return;
        }

        Token userObject = (Token) node.getUserObject();
        String selectionStyle = (userObject.isCheckBoxOn() ? "KS-ReqComp-Selected" : "KS-ReqComp-DeSelected");

        //true if the node is AND/OR operator
        if (userObject.isTokenOperator()) {
            VerticalPanel checkBoxAndToggle = new VerticalPanel();
            super.setWidget(checkBoxAndToggle);
            this.addStyleName(userObject.isCheckBoxOn() ? "KS-Rules-Table-Cell-Selected" : "KS-Rules-Table-Cell-DeSelected");            
            if (showCheckbox) {
                checkBoxAndToggle.add(checkBox); 
            }
            toggle.setText(userObject.getType() == Token.Or ? Token.createOrToken().value.toUpperCase() : Token.createAndToken().value.toUpperCase());
            toggle.addStyleName("KS-Rules-Table-Cell-ANDOR");
            if (userObject instanceof StatementVO) {
                StatementVO statementVO = (StatementVO)userObject;
                checkBox.ensureDebugId(DebugIdUtils.createWebDriverSafeDebugId(toggle.getText() + "-" + statementVO.getReqComponentVOCount()));
            }
            checkBoxAndToggle.add(toggle);
            if (ruleTable != null) {
                ruleTable.getCellFormatter().setStyleName(rowIndex, columnIndex, selectionStyle);
                ruleTable.getCellFormatter().addStyleName(rowIndex, columnIndex, "KS-Toggle");
            }
        } else { //we have a rule to display in this node 
            checkBoxAndEdit = new HorizontalPanel();
            HorizontalPanel tableCell = new HorizontalPanel();
            super.setWidget(tableCell);
            this.setStyleName(userObject.isCheckBoxOn() ? "KS-Rules-Table-Cell-Selected" : "KS-Rules-Table-Cell-DeSelected");
            if (userObject.getTokenID() != null) {
                KSLabel rcLabel = new KSLabel(userObject.getTokenID());
                rcLabel.setStyleName("KS-Rules-Table-Cell-ID");
                tableCell.add(rcLabel);
            }            
            tableCell.add(checkBoxAndEdit);
            
            if (showCheckbox) {
                checkBoxAndEdit.add(checkBox);
                checkBox.setHTML(node.getUserObject().toString());
                checkBox.ensureDebugId(DebugIdUtils.createWebDriverSafeDebugId(checkBox.getHTML()));
            } else {
                checkBoxAndEdit.add(new KSLabel(node.getUserObject().toString()));
            }
            edit.ensureDebugId(DebugIdUtils.createWebDriverSafeDebugId(node.getUserObject().toString() + "-Edit"));
            edit.addStyleName("KS-Rules-URL-Link");
            checkBoxAndEdit.add(edit);
            if (ruleTable != null) {
                checkBoxAndEdit.setStyleName(selectionStyle);
            }
            
            html.addStyleName("KS-ReqComp-Panel");
        }

        checkBox.setValue(userObject.isCheckBoxOn());
        html.setHTML(node.getUserObject().toString());
    }

    public boolean isSelected(){
        return checkBox.getValue();
    }

    public boolean isShowCheckbox() {
        return showCheckbox;
    }

    public void setShowCheckbox(boolean showCheckbox) {
        this.showCheckbox = showCheckbox;
    }

    public Node getNode() {
        return node;
    }

    public void addEditClauseHandler(ClickHandler ch) {
        editClickHandler = ch;
        editClauseHandlerRegistration = edit.addClickHandler(ch);
    }

    public void clearEditClauseHandler() {
        if (editClauseHandlerRegistration != null) {
            editClauseHandlerRegistration.removeHandler();
            editClauseHandlerRegistration = null;
        }
    }  

    public void setEnabled(boolean enabled) {
        checkBox.setEnabled(enabled);
        if (enabled) {
            edit.removeStyleName("KS-Rules-URL-Link-Readonly");
            edit.addStyleName("KS-Rules-URL-Link");
            addEditClauseHandler(editClickHandler);
        } else {
            edit.removeStyleName("KS-Rules-URL-Link");
            edit.addStyleName("KS-Rules-URL-Link-Readonly");            
            clearEditClauseHandler();
        }
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
}
