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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.CollectionModel;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.lum.ui.requirements.client.controller.CourseReqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.RuleExpressionParser;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RuleExpressionEditor extends ViewComposite {

    //view's widgets
    private Panel mainPanel = new SimplePanel();
    private KSTextArea taExpression = new KSTextArea();
    private KSButton btnPreview = new KSButton("Preview");
    private Panel pnlMissingExpressions = new VerticalPanel();
    private RuleTable ruleTable = new RuleTable();
    private KSButton btnDone = new KSButton("Done");
    private KSButton btnCancel = new KSButton("Cancel");
    private HTML htmlErrorMessage = new HTML();
    private HTML htmlMissingExpressionNotice = new HTML();


    // views's data
    private CollectionModel<RuleInfo> model;
    
    // helper object
    private RuleExpressionParser ruleExpressionParser = new RuleExpressionParser();

    public RuleExpressionEditor(Controller controller) {
        super(controller, "Rule Expression Editor");
        super.initWidget(mainPanel);
        setupHandlers();
    }
    
    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
        getController().requestModel(RuleInfo.class, new ModelRequestCallback<CollectionModel<RuleInfo>>() {
            public void onModelReady(CollectionModel<RuleInfo> theModel) {
                model = theModel;    
            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }
        }); 
        redraw();
        // TODO should probably pass the callback into the method above and invoke it when the work is actually done
        onReadyCallback.exec(true);
    }
    
    private void setupHandlers() {
        taExpression.addKeyUpHandler(new KeyUpHandler() {
            public void onKeyUp(KeyUpEvent event) {
                // escape error keys
                if(event.getNativeKeyCode() == 37 
                        ||event.getNativeKeyCode() == 38
                        ||event.getNativeKeyCode() == 39
                        ||event.getNativeKeyCode() == 40){
                        return;
                }
                String expression = taExpression.getText();
                model.getValue().setExpression(expression);
            }
        });
        btnPreview.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String expression = null;
                RuleInfo prereqInfo = model.getValue();
                expression = prereqInfo.getExpression();
                prereqInfo.setPreviewedExpression(expression);
                redraw();
            }
        });
        
        btnDone.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
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
                }
            }
        });
        
        btnCancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                model.getValue().setPreviewedExpression(null);
                getController().showView(PrereqViews.MANAGE_RULES, Controller.NO_OP_CALLBACK);
            }
        });
    }
    
    private void redraw() {
        FlexTable flexTable = new FlexTable();
        int rowNum = 0;
        // row 0
        KSLabel preReqHeading = new KSLabel("Edit Prerequisite Logic");
        preReqHeading.setStyleName("KS-ReqMgr-Heading");
        flexTable.setWidget(rowNum,0,preReqHeading);
        rowNum++;
        // row 1
        KSLabel logicEditTitle = new KSLabel("Logic Expression");
        logicEditTitle.setStyleName("KS-RuleEditor-SubHeading");
        flexTable.setWidget(rowNum,0,logicEditTitle);
        // row 2
        Label horizontalSpacer = new Label();
        horizontalSpacer.setWidth("5px");
        rowNum++;
        taExpression.getElement().getStyle().setProperty("width", "350");
        flexTable.setWidget(rowNum,0,taExpression);
        taExpression.setHeight("100px");
        flexTable.setWidget(rowNum,1,horizontalSpacer);
        flexTable.setWidget(rowNum,2,htmlErrorMessage);
        // row 3
        rowNum++;
        flexTable.setWidget(rowNum, 0, btnPreview);
        btnPreview.addStyleName("KS-Rules-Tight-Grey-Button");
        // row 4
        rowNum++;
        SimplePanel verticalSpacer = null;
        verticalSpacer = new SimplePanel();
        verticalSpacer.setHeight("30px");
        flexTable.setWidget(rowNum, 0, verticalSpacer);
        // row 5
        rowNum++;
        flexTable.setWidget(rowNum, 0, pnlMissingExpressions);
        pnlMissingExpressions.getElement().getStyle().setProperty("borderWidth", "1px");
        flexTable.setWidget(rowNum, 1, horizontalSpacer);
        flexTable.setWidget(rowNum, 2, htmlMissingExpressionNotice);
        // row 6
        rowNum++;
        verticalSpacer = new SimplePanel();
        verticalSpacer.setHeight("30px");
        flexTable.setWidget(rowNum, 0, verticalSpacer);
        // row 7
        rowNum++;
        KSLabel previewTitle = new KSLabel("Preview");
        previewTitle.setStyleName("KS-RuleEditor-SubHeading");        
        flexTable.setWidget(rowNum, 0, previewTitle);
        // row 8
        rowNum++;
        ruleTable.setShowControls(false);
        flexTable.setWidget(rowNum, 0, ruleTable);
        flexTable.getFlexCellFormatter().setColSpan(rowNum, 0, 100);
        // row 9
        rowNum++;
        verticalSpacer = new SimplePanel();
        verticalSpacer.setHeight("15px");
        flexTable.setWidget(rowNum, 0, verticalSpacer);
        // row 10
        rowNum++;
       
        HorizontalPanel buttonsPanel = new HorizontalPanel();
        buttonsPanel.setSpacing(0);
        btnDone.addStyleName("KS-Rules-Tight-Button");  
        buttonsPanel.add(btnDone);
        SimplePanel horizSpacer = new SimplePanel();
        horizSpacer.setWidth("30px");        
        buttonsPanel.add(horizSpacer);
        btnCancel.addStyleName("KS-Rules-Tight-Grey-Button"); 
        buttonsPanel.add(btnCancel);                
        flexTable.setWidget(rowNum, 0, buttonsPanel);
        
        RuleInfo prereqInfo = model.getValue();
        if (prereqInfo != null) {
            taExpression.setText("");
            if (prereqInfo.getExpression() != null) {
                taExpression.setText(prereqInfo.getExpression());
            }
            if (prereqInfo.getPreviewedExpression() != null) {
                String previewExpression = prereqInfo.getPreviewedExpression();
                previewExpression = previewExpression.replaceAll("\n", " ");
                previewExpression = previewExpression.replaceAll("\r", " ");
                List<ReqComponentVO> rcs = 
                    (prereqInfo.getStatementVO() == null ||
                            prereqInfo.getStatementVO().getAllReqComponentVOs() == null)?
                                    new ArrayList<ReqComponentVO>() :
                                        prereqInfo.getStatementVO().getAllReqComponentVOs();
                List<String> errorMessages = new ArrayList<String>();
                List<ReqComponentVO> missingRCs = new ArrayList<ReqComponentVO>();
                ruleExpressionParser.setExpression(previewExpression);
                boolean validExpression = ruleExpressionParser.validateExpression(errorMessages, rcs);
                htmlErrorMessage.setHTML("");
                ruleTable.clear();
                
                if (!validExpression) {
                    showErrors(errorMessages);
                } else {
                    Node tree = ruleExpressionParser.parseExpressionIntoTree(
                        previewExpression, prereqInfo.getStatementVO(), prereqInfo.getSelectedStatementType());
                    if (tree != null) {
                        ruleTable.buildTable(tree);
                    }
                }
                ruleExpressionParser.checkMissingRCs(missingRCs, rcs);
                showMissingRCs(missingRCs);
            }
        }
        
        flexTable.addStyleName("Content-Margin");
        flexTable.setCellSpacing(5);
        mainPanel.clear();
        mainPanel.add(flexTable);
    }
    
    private void showMissingRCs(List<ReqComponentVO> missingRCs) {
        VerticalPanel pnlRCList = new VerticalPanel();
        boolean missingExprFound = false;
        
        pnlMissingExpressions.clear();
        htmlMissingExpressionNotice.setHTML("");
        
        if (missingRCs != null) {
            for (ReqComponentVO rc : missingRCs) {
                HorizontalPanel pnlRcListRow = new HorizontalPanel();
                KSLabel rcLabel = null;
                HTML rcText = new HTML();
                if (rc.getGuiReferenceLabelId() != null) {
                    rcLabel = new KSLabel(rc.getGuiReferenceLabelId());
                    rcLabel.getElement().getStyle().setProperty("fontWeight", "bold");
                    rcLabel.getElement().getStyle().setProperty("background", "#E0E0E0");
                    pnlRcListRow.add(rcLabel);
                    missingExprFound = true;
                }
                pnlRcListRow.getElement().getStyle().setProperty("padding", "3px");
                rcText.setHTML(rc.toString());
                rcText.getElement().getStyle().setProperty("color", "red");
                pnlRcListRow.add(rcText);
                pnlRCList.add(pnlRcListRow);
            }
        }
        
        if (missingExprFound) {
            KSLabel missingExprTitle = new KSLabel("Rules missing from logic expression");
            missingExprTitle.setStyleName("KS-RuleEditor-SubHeading");
            pnlMissingExpressions.add(missingExprTitle);                        
            pnlMissingExpressions.add(pnlRCList);
        }
        
        if (missingRCs != null && !missingRCs.isEmpty()) {
            StringBuilder sb = new StringBuilder(
            		"All rules must be included <BR/>");
            sb.append("in the Logic Expression.");
            htmlMissingExpressionNotice.getElement().getStyle().setProperty("color", "red");
            htmlMissingExpressionNotice.setHTML(sb.toString());
        }
    }
    
    private void showErrors(List<String> errorMessages) {
        if (errorMessages != null) {
            StringBuilder sb = new StringBuilder("Error Message: <BR>");
            for (String errorMessage : errorMessages) {
                sb.append(errorMessage + ",<BR>");
            }
            htmlErrorMessage.getElement().getStyle().setProperty("color", "red");
            htmlErrorMessage.setHTML(sb.toString());
        }
    }
    
    class RCTableItems implements ListItems {
        private List<ReqComponentVO> rcs = new ArrayList<ReqComponentVO>();
        private final String ATTR_KEY_LABEL = "Label";
        private final String ATTR_KEY_DESCR = "Description";
        
        public List<ReqComponentVO> getRcs() {
            return rcs;
        }

        public void setRcs(List<ReqComponentVO> rcs) {
            this.rcs = rcs;
        }
        
        public ReqComponentVO lookup(String id) {
            ReqComponentVO result = null;
            if (rcs != null && !rcs.isEmpty()) {
                for (ReqComponentVO rc : rcs) {
                    if (rc.getGuiReferenceLabelId() != null &&
                            rc.getGuiReferenceLabelId().equals(id)) {
                        result = rc;
                    }
                }
            }
            return result;
        }

        @Override
        public List<String> getAttrKeys() {
            List<String> attrKeys = new ArrayList<String>();
            attrKeys.add(ATTR_KEY_LABEL);
            attrKeys.add(ATTR_KEY_DESCR);
            return attrKeys;
        }

        @Override
        public String getItemAttribute(String id, String attrkey) {
            String value = "";
            ReqComponentVO rc = lookup(id);
            if (attrkey != null) {
                if (attrkey.equals(ATTR_KEY_LABEL)) {
                    value = rc.getGuiReferenceLabelId();
                } else if (attrkey.equals(ATTR_KEY_DESCR)) {
                    value = rc.toString();
                }
            }
            return value;
        }

        @Override
        public int getItemCount() {
            int count = (rcs == null)? 0 : rcs.size();
            return count;
        }

        @Override
        public List<String> getItemIds() {
            List<String> itemIds = new ArrayList<String>();
            if (rcs != null && !rcs.isEmpty()) {
                for (ReqComponentVO rc : rcs) {
                    itemIds.add(rc.getGuiReferenceLabelId());
                }
            }
            return itemIds;
        }

        @Override
        public String getItemText(String id) {
            String itemText = null;
            ReqComponentVO rc = lookup(id);
            itemText = rc.getGuiReferenceLabelId() + ", " + rc.toString();
            return itemText;
        }
    }
    
}
