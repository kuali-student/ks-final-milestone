package org.kuali.student.common.ui.client.widgets.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class RulePreviewWidget extends FlowPanel {

    
    String ruleName;
    String ruleCredits;
    String ruleDesc;
    StatementTreeViewInfo stmtTreeInfo;     //program rule e.g. program completion rule
    boolean isReadOnly;
    boolean addRuleOperator = false;        //first subrule does not have operator following it  

    SpanPanel rulePanel = new SpanPanel();
    KSButton editButton = new KSButton("Edit", KSButtonAbstract.ButtonStyle.DEFAULT_ANCHOR);
    SpanPanel separator = new SpanPanel(" | ");
    KSButton deleteButton = new KSButton("Delete", KSButtonAbstract.ButtonStyle.DEFAULT_ANCHOR);
    KSButton addSubRuleBtn = new KSButton("Add a Rule", KSButtonAbstract.ButtonStyle.FORM_SMALL);
    List<SubrulePreviewWidget> subRulePreviewWidgets = new ArrayList();

    ClickHandler addSubRuleAddButtonClickHandler;
    Callback<StatementTreeViewInfo> editRuleCallback;

    public RulePreviewWidget(String ruleName, String ruleCredits, String ruleDesc, StatementTreeViewInfo stmtTreeInfo, Boolean isReadOnly) {
        super();
        this.ruleName = ruleName;
        this.ruleCredits = ruleCredits;
        this.ruleDesc = ruleDesc;
        this.stmtTreeInfo = stmtTreeInfo;
        this.isReadOnly = isReadOnly;

        this.addStyleName("KS-Program-Rule-Preview-Box");
        displayRule();
    }

    private void displayRule() {
        //start with a header for the entire rule
        buildRuleHeader();

        //true if we have an empty rule
        if (stmtTreeInfo == null) {
            KSLabel noRulesAddedYet = new KSLabel("No rules have been added yet.");
            noRulesAddedYet.addStyleName("KS-Program-Rule-Preview-NoRule");
            rulePanel.add(noRulesAddedYet);
        } else {
            /*
            List<StatementTreeViewInfo> rootStmt = stmtTreeInfo.getStatements();
            //if we have only one subrule it can be composed of 
            if (stmtTreeInfo.getStatements() == null || stmtTreeInfo.getStatements().size() == 0) {
            } */
            //generate section for each sub-rule
            for (final StatementTreeViewInfo subTree : stmtTreeInfo.getStatements()) {
                addSubRule(subTree);
            }
        }

        //button below has to be spaced
        SpanPanel spacer = new SpanPanel();
        spacer.addStyleName("KS-Program-Rule-Last-Preview-Spacer");
        rulePanel.add(spacer);

        //add a button for user to add additional subrule
        if (!isReadOnly) {
            addSubRuleBtn.addStyleName("KS-Program-Rule-Preview-Add-Subrule");
            spacer.add(addSubRuleBtn);
        }

        this.add(rulePanel);
    }

    private void addSubRule(final StatementTreeViewInfo subTree) {
        //display AND/OR operator between subrules
        if (addRuleOperator) {
            buildANDOperator(stmtTreeInfo.getOperator());
        }

        final SubrulePreviewWidget newSubRuleWidget = new SubrulePreviewWidget(subTree, isReadOnly);
        subRulePreviewWidgets.add(newSubRuleWidget);

        newSubRuleWidget.addEditButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                editRuleCallback.exec(subTree);    
            }
        });

        newSubRuleWidget.addDeleteButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                final ConfirmationDialog dialog = new ConfirmationDialog("Delete Requirement", "Are you sure you want to delete this requirement?"); //TODO app context for labels
                dialog.getConfirmButton().addClickHandler(new ClickHandler(){
                    @Override
                    public void onClick(ClickEvent event) {
                        removeSubRule(newSubRuleWidget, subTree);
                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });

        rulePanel.add(newSubRuleWidget);
        addRuleOperator = true;
    }

    //user selected to delete this particular requirement
    public void removeSubRule(SubrulePreviewWidget subRuleWidget, StatementTreeViewInfo subTree) {
        //update data
        List<StatementTreeViewInfo> statements = stmtTreeInfo.getStatements();
        statements.remove(subTree);
        subRulePreviewWidgets.remove(subRuleWidget);

        //update display
        rulePanel.remove(subRuleWidget);

        //remove unnecessary operators
        int numberOfOperators = 0;
        boolean lastWidgetSubRule = true;
        int counter = 0;
        Widget widget = null;
        Widget widgetToRemove = null;
        Iterator iterator = rulePanel.iterator();
        while(iterator.hasNext()) {
            counter++;
            widget = (Widget) iterator.next();
            if (widget instanceof KSLabel) {
                if (((KSLabel)widget).getText().equals("AND") || ((KSLabel)widget).getText().equals("OR")) {
                    lastWidgetSubRule = false;
                    numberOfOperators++;
                    //remove 'AND' above the first sub-rule or two repeated instances
                    if ((numberOfOperators > 1) || (counter == 1)) {
                        widgetToRemove = widget;
                        numberOfOperators--;
                    }
                }
            } else if (widget instanceof SubrulePreviewWidget) {
                lastWidgetSubRule = true;
                numberOfOperators = 0;
            }
        }
        
        if (!lastWidgetSubRule) {
            rulePanel.remove(widget);
        } else if (widgetToRemove != null) {
            rulePanel.remove(widgetToRemove);
        }
    }

    private void buildRuleHeader() {

        //build heading
        SectionTitle header = SectionTitle.generateH4Title("");
        header.setHTML("<b>" + ruleName + "</b>" + "  " + ruleCredits);
        header.setStyleName("KS-Program-Rule-Preview-header");

        //do not show edit,delete etc. if user is only viewing the rule in non-edit mode
        if (!isReadOnly) {
            buildEditActionsWidget(header);
        }
        this.add(header);

        rulePanel.clear();
        rulePanel.addStyleName("KS-Program-Rule-Preview-Box1");

        KSLabel ruleDescLabel = new KSLabel(ruleDesc);
        ruleDescLabel.addStyleName("KS-Program-Rule-Preview-Desc");
        rulePanel.add(ruleDescLabel);

        //build subheading
        header = SectionTitle.generateH6Title("");
        header.setHTML("Must meet <b>all of the following</b> rules");
        header.setStyleName("KS-Program-Rule-Preview-header-Subrule");
        header.getElement().setAttribute("style", "font-weight: normal");        
        rulePanel.add(header);
    }

    private void buildEditActionsWidget(SectionTitle header) {
        SpanPanel actions = new SpanPanel();
        actions.add(editButton);
        actions.add(separator);
        actions.add(deleteButton);
        actions.addStyleName("KS-Program-Rule-Preview-header-action");
        header.add(actions);
        rulePanel.add(header);
    }

    private void buildANDOperator(StatementOperatorTypeKey operator) {
        KSLabel andLabel = new KSLabel((operator == StatementOperatorTypeKey.AND ? "AND" : "OR"));
        andLabel.addStyleName("KS-Program-Rule-Preview-Operator");
        rulePanel.add(andLabel);        
    }

    public void addRequirementEditButtonClickHandler(ClickHandler handler) {
        editButton.addClickHandler(handler);
    }

    public void addRequirementDeleteButtonClickHandler(ClickHandler handler) {
        deleteButton.addClickHandler(handler);
    }

    public void addSubRuleAddButtonClickHandler(ClickHandler handler) {
        addSubRuleBtn.addClickHandler(handler);
    }

    public void addSubRuleEditCallback(Callback<StatementTreeViewInfo> editRuleCallback) {
        this.editRuleCallback = editRuleCallback; 
    }
}