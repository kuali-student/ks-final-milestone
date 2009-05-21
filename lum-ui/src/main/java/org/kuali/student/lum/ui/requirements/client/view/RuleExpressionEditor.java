package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.list.KSSelectableTableList;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.lum.ui.requirements.client.RulesUtilities;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.RuleExpressionParser;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RuleExpressionEditor extends ViewComposite {

    //view's widgets
    private Panel mainPanel = new SimplePanel();
    private KSTextArea taExpression = new KSTextArea();
    private KSButton btnPreview = new KSButton("Preview");
    private Panel pnlMissingExpressions = new SimplePanel();
    private KSSelectableTableList tableMissingExpressions = 
        new KSSelectableTableList();
    private RuleTable ruleTable = new RuleTable();
    private KSButton btnDone = new KSButton("Done");
    private KSButton btnCancel = new KSButton("Cancel");
    private HTML htmlErrorMessage = new HTML();


    // views's data
    private Model<PrereqInfo> model;
    
    // helper object
    private RuleExpressionParser ruleExpressionParser = new RuleExpressionParser();

    public RuleExpressionEditor(Controller controller) {
        super(controller, "Rule Expression Editor");
        super.initWidget(mainPanel);
        setupHandlers();
    }
    
    @Override
    public void beforeShow() {
        getController().requestModel(PrereqInfo.class, new ModelRequestCallback<PrereqInfo>() {
            public void onModelReady(Model<PrereqInfo> theModel) {
                model = theModel;    
            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }
        }); 
        redraw();
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
                PrereqInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                prereqInfo.setExpression(expression);
            }
        });
        btnPreview.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String expression = null;
                PrereqInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                expression = prereqInfo.getExpression();
                prereqInfo.setPreviewedExpression(expression);
                redraw();
            }
        });
        
        btnDone.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                PrereqInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                StatementVO newStatementVO = ruleExpressionParser
                .parseExpressionIntoStatementVO(
                        prereqInfo.getExpression(),
                        prereqInfo.getStatementVO().getAllReqComponentVOs());
                prereqInfo.setStatementVO(newStatementVO);
                prereqInfo.setPreviewedExpression(null);
                prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
                getController().showView(PrereqViews.COMPLEX);
            }
        });
        
        btnCancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                PrereqInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                prereqInfo.setPreviewedExpression(null);
                getController().showView(PrereqViews.COMPLEX);
            }
        });
    }
    
    private void redraw() {
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(new KSLabel("Manually Edit Logic"));
        HorizontalPanel expressionAndError = new HorizontalPanel();
        expressionAndError.add(taExpression);
        taExpression.setHeight("100px");
        expressionAndError.add(htmlErrorMessage);
        verticalPanel.add(expressionAndError);
        verticalPanel.add(btnPreview);
        btnPreview.addStyleName("KS-Rules-Tight-Grey-Button");
        SimplePanel verticalSpacer = null;
        verticalSpacer = new SimplePanel();
        verticalSpacer.setHeight("30px");
        verticalPanel.add(verticalSpacer);
        VerticalPanel pnlMissingDisplay = new VerticalPanel();
        pnlMissingDisplay.add(new KSLabel("Rule(s) missing from expression"));
        tableMissingExpressions = new KSSelectableTableList();
        tableMissingExpressions.setListItems(new RCTableItems());
        pnlMissingDisplay.add(tableMissingExpressions);
        pnlMissingExpressions.clear();
        pnlMissingExpressions.add(pnlMissingDisplay);
        verticalPanel.add(pnlMissingExpressions);
        verticalSpacer = new SimplePanel();
        verticalSpacer.setHeight("30px");
        verticalPanel.add(verticalSpacer);
        verticalPanel.add(new KSLabel("Preview"));
        ruleTable.setShowControls(false);
        verticalPanel.add(ruleTable);
        verticalSpacer = new SimplePanel();
        verticalSpacer.setHeight("30px");
        verticalPanel.add(verticalSpacer);
        HorizontalPanel buttonsPanel = new HorizontalPanel();
        btnCancel.addStyleName("KS-Rules-Tight-Grey-Button");
        buttonsPanel.add(btnCancel);
        btnDone.addStyleName("KS-Rules-Tight-Grey-Button");
        buttonsPanel.add(btnDone);
        verticalPanel.add(buttonsPanel);
        
        PrereqInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
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
                ruleTable.clear();
                ruleExpressionParser.setExpression(previewExpression);
                List<String> errorMessages = new ArrayList<String>();
                List<ReqComponentVO> missingRCs = new ArrayList<ReqComponentVO>();
                boolean validExpression = ruleExpressionParser.validateExpression(errorMessages, rcs);
                htmlErrorMessage.setHTML("");
                if (!validExpression) {
                    if (errorMessages != null) {
                        StringBuilder sb = new StringBuilder("Error Message: <BR>");
                        for (String errorMessage : errorMessages) {
                            sb.append(errorMessage + ",<BR>");
                        }
                        htmlErrorMessage.setHTML(sb.toString() + "<HR>");
                    }
                } else {
                    Node tree = ruleExpressionParser.parseExpressionIntoTree(
                        previewExpression, rcs);
                    if (tree != null) {
                        ruleTable.buildTable(tree);
                    }
                }
                // redraw missing rules table.
                ruleExpressionParser.checkMissingRCs(missingRCs, rcs);
                RCTableItems rcTableItems = new RCTableItems();
                rcTableItems.setRcs(missingRCs);
                tableMissingExpressions.setListItems(rcTableItems);

            }
        }
        
        mainPanel.clear();
        mainPanel.add(verticalPanel);
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
