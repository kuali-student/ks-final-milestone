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
import com.google.gwt.user.client.ui.FlexTable;
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
    private FlexTable tableMissingExpressions = new FlexTable();
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
                redrawMissingRules();
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
        SimplePanel verticalSpacer = null;
        verticalSpacer = new SimplePanel();
        verticalSpacer.setHeight("30px");
        verticalPanel.add(verticalSpacer);
        VerticalPanel pnlMissingDisplay = new VerticalPanel();
        pnlMissingDisplay.add(new KSLabel("Rule(s) missing from expression"));
        pnlMissingDisplay.add(tableMissingExpressions);
        pnlMissingExpressions.clear();
        pnlMissingExpressions.add(pnlMissingDisplay);
        verticalPanel.add(pnlMissingExpressions);
        verticalSpacer = new SimplePanel();
        verticalSpacer.setHeight("30px");
        verticalPanel.add(verticalSpacer);
        ruleTable.setShowControls(false);
        verticalPanel.add(ruleTable);
        
        HorizontalPanel buttonsPanel = new HorizontalPanel();
        buttonsPanel.add(btnCancel);
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
                List<ReqComponentVO> rcs = 
                    (prereqInfo.getStatementVO() == null ||
                            prereqInfo.getStatementVO().getAllReqComponentVOs() == null)?
                                    new ArrayList<ReqComponentVO>() :
                                        prereqInfo.getStatementVO().getAllReqComponentVOs();
                ruleTable.clear();
                List<String> errorMessages = new ArrayList<String>();
                if (!ruleExpressionParser.validateExpression(errorMessages, previewExpression)) {
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
            }
        }
        
        redrawMissingRules();
        mainPanel.clear();
        mainPanel.add(verticalPanel);
    }
    
    private void redrawMissingRules() {
        
    }

}
