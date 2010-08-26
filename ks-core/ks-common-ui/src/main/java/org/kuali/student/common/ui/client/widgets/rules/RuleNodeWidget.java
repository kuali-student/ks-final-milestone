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

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.table.Node;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;

public class RuleNodeWidget extends FocusPanel {
    
    private Node node;
    private boolean showControls;
    HTML html = new HTML();
    CheckBox checkBox = new CheckBox();
    KSLabel edit = new KSLabel("Edit");
    AndOrButton toggle = new AndOrButton();
    HorizontalPanel checkBoxAndEdit;
    HandlerRegistration editClauseHandlerRegistration;
    
    public RuleNodeWidget(Node n) {
        init(n, true);
    }
    
    public RuleNodeWidget(Node n, boolean showControls) {
        init(n, showControls);
    }
    
    private void init(Node n, boolean showControls) {
        node = n;
        this.showControls = showControls;
        drawNode(n, null, -1, -1); 
    }
    
    public void drawNode(Node n, RuleTable ruleTable, int rowIndex, int columnIndex) {
        node = n;

        if (node.getUserObject() instanceof Token == false) {
            super.setWidget(html);
            html.setHTML(node.getUserObject().toString());
            checkBox.setHTML(node.getUserObject().toString());
            return;
        }

        Token userObject = (Token) node.getUserObject();
        if (userObject.isTokenOperator()) {
            checkBox.setStyleName("KS-Rules-Table-Cell-ANDOR-Checkbox");
            VerticalPanel checkBoxAndToggle = new VerticalPanel();
            super.setWidget(checkBoxAndToggle);
            this.addStyleName(userObject.isCheckBoxOn() ? "KS-Rules-Table-Cell-Selected" : "KS-Rules-Table-Cell-DeSelected");            
            if (showControls) {
                checkBoxAndToggle.add(checkBox);
                if (userObject.getType()==  Token.Or) {
                    toggle.setValue(AndOrButton.Or);
                } else {
                    toggle.setValue(AndOrButton.And);
                }
                checkBoxAndToggle.add(toggle);
                if (ruleTable != null) {
                    String selectionStyle = (userObject.isCheckBoxOn() ? "KS-ReqComp-Selected" : "KS-ReqComp-DeSelected");
                    ruleTable.getCellFormatter().setStyleName(rowIndex, columnIndex, selectionStyle);
                    ruleTable.getCellFormatter().addStyleName(rowIndex, columnIndex, "KS-Toggle");
                }
            } else {
                checkBoxAndToggle.add(html);
                checkBoxAndToggle.setStyleName("KS-ReqComp-DeSelected");
            }
            html.setHTML(node.getUserObject().toString());
            checkBox.setValue(userObject.isCheckBoxOn(), false);
            
        } else { //we have a rule to display in this node  //if (userObject instanceof ReqComponentVO) {
            KSLabel rcLabel = null;
            HorizontalPanel tableCell = new HorizontalPanel();
            checkBoxAndEdit = new HorizontalPanel();
            super.setWidget(tableCell);
            this.setStyleName(userObject.isCheckBoxOn() ? "KS-Rules-Table-Cell-Selected" : "KS-Rules-Table-Cell-DeSelected");
            if (userObject.getTokenID() != null) {
                Node parent = node.getParent();
                if (parent != null) {
                    rcLabel = new KSLabel(userObject.getTokenID());
                    rcLabel.setStyleName("KS-Rules-Table-Cell-ID");
                    tableCell.add(rcLabel);
        //            checkBoxAndEdit.add(rcLabel);
                }
            }
            
            tableCell.add(checkBoxAndEdit);
            
            if (showControls) {
                checkBoxAndEdit.add(checkBox);
                edit.addStyleName("KS-Rules-URL-Link");
                checkBoxAndEdit.add(edit);
                if (ruleTable != null) {
                    String selectionStyle = (userObject.isCheckBoxOn() ? "KS-ReqComp-Selected" : "KS-ReqComp-DeSelected");
                    //ruleTable.getCellFormatter().setStyleName(rowIndex, columnIndex, selectionStyle);  this does not work all the time
                    checkBoxAndEdit.setStyleName(selectionStyle);
                }
            } else {
                checkBoxAndEdit.add(html);
                checkBoxAndEdit.setStyleName("KS-ReqComp-DeSelected");                
            }
            html.setHTML(node.getUserObject().toString());
            checkBox.setHTML(node.getUserObject().toString());
            checkBox.setValue(userObject.isCheckBoxOn());
            
        } /*else {
            super.setWidget(html);
            html.setHTML(node.getUserObject().toString());
            checkBox.setHTML(node.getUserObject().toString());
        } */
    }

    public boolean isSelected(){
        return checkBox.getValue() == true;
    }
    public void addToggleHandler(ClickHandler ch) {
        toggle.addClickHandler(ch);
    }

    public void clearToggleHandler() {
        toggle.removeClickHandler();
    }

    public boolean isShowControls() {
        return showControls;
    }

    public void setShowControls(boolean showControls) {
        this.showControls = showControls;
    }

    public Node getNode() {
        return node;
    }

    public void addEditClauseHandler(ClickHandler ch) {
        editClauseHandlerRegistration = edit.addClickHandler(ch);
    }

    public void clearEditClauseHandler() {
        if (editClauseHandlerRegistration != null) {
            editClauseHandlerRegistration.removeHandler();
        }
    }    
}
