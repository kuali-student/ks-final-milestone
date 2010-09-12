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

import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

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
      //  this.setStyleName("KS-Rules-Table-Cell");
    }
    
    public RuleNodeWidget(Node n, boolean showControls) {
        init(n, showControls);
    }
    
    private void init(Node n, boolean showControls) {
        node = n;
        this.showControls = showControls;
        drawNode(n, null, -1, -1); 
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
    public boolean isSelected(){
        return checkBox.getValue() == true;
    }
    public void addToggleHandler(ClickHandler ch) {
        toggle.addClickHandler(ch);
    }
    
    public void clearToggleHandler() {
        toggle.removeClickHandler();
    }
    
    public void addEditClauseHandler(ClickHandler ch) {
        editClauseHandlerRegistration = edit.addClickHandler(ch);
    }

    public void clearEditClauseHandler() {
        if (editClauseHandlerRegistration != null) {
            editClauseHandlerRegistration.removeHandler();
        }
    }
    
    public void drawNode(Node n, RuleTable ruleTable, int rowIndex, int columnIndex) {
        Object userObject = null;
        node = n;
        userObject = node.getUserObject();

        if (userObject instanceof StatementVO) {
            checkBox.setStyleName("KS-Rules-Table-Cell-ANDOR-Checkbox");
            StatementVO statementVO = (StatementVO) userObject;
            VerticalPanel checkBoxAndToggle = new VerticalPanel();
            super.setWidget(checkBoxAndToggle);
            if (showControls) {
                checkBoxAndToggle.add(checkBox);
                if (statementVO.getStatementInfo() != null && statementVO.getStatementInfo().getOperator() == StatementOperatorTypeKey.OR) {
                    toggle.setValue(AndOrButton.Or);
                } else {
                    toggle.setValue(AndOrButton.And);
                }
                checkBoxAndToggle.add(toggle);
                if (ruleTable != null) {
                    String selectionStyle = (statementVO.isCheckBoxOn() ? "KS-ReqComp-Selected" : "KS-ReqComp-DeSelected");
                    ruleTable.getCellFormatter().setStyleName(rowIndex, columnIndex, selectionStyle);
                    ruleTable.getCellFormatter().addStyleName(rowIndex, columnIndex, "KS-Toggle");
                }
            } else {
                checkBoxAndToggle.add(html);
              //  checkBoxAndToggle.setStyleName("KS-Rules-Table-Cell");
                checkBoxAndToggle.setStyleName("KS-ReqComp-DeSelected");
               // checkBoxAndToggle.addStyleName("KS-Toggle");
            }
            html.setHTML(node.getUserObject().toString());
            checkBox.setValue(statementVO.isCheckBoxOn(), false);
            
        } else if (userObject instanceof ReqComponentVO) {
            
            ReqComponentVO reqComponentVO = (ReqComponentVO) userObject;
            KSLabel rcLabel = null;
            HorizontalPanel tableCell = new HorizontalPanel();
            checkBoxAndEdit= new HorizontalPanel();
            super.setWidget(tableCell);
            if (reqComponentVO.getGuiReferenceLabelId() != null) {
                Node parent = node.getParent();
                if (parent != null) {
                    rcLabel = new KSLabel(reqComponentVO.getGuiReferenceLabelId());
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
                    String selectionStyle = (reqComponentVO.isCheckBoxOn() ? "KS-ReqComp-Selected" : "KS-ReqComp-DeSelected");
                    //ruleTable.getCellFormatter().setStyleName(rowIndex, columnIndex, selectionStyle);  this does not work all the time
                    checkBoxAndEdit.setStyleName(selectionStyle);
                }
            } else {
                checkBoxAndEdit.add(html);
             //   checkBoxAndEdit.setStyleName("KS-Rules-Table-Cell");
                checkBoxAndEdit.setStyleName("KS-ReqComp-DeSelected");                
            }
            html.setHTML(node.getUserObject().toString());
            checkBox.setHTML(node.getUserObject().toString());
            checkBox.setValue(reqComponentVO.isCheckBoxOn());
            
        } else {
            
            super.setWidget(html);
            html.setHTML(node.getUserObject().toString());
            checkBox.setHTML(node.getUserObject().toString());
        }
    }
}
