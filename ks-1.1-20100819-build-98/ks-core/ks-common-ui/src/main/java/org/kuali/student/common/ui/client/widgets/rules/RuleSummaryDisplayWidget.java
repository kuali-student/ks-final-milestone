package org.kuali.student.common.ui.client.widgets.rules;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
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
import com.google.gwt.user.client.ui.VerticalPanel;

public class RuleSummaryDisplayWidget extends FlowPanel {

    String ruleName;
    String ruleCredits;
    String ruleDesc;
    StatementTreeViewInfo stmtTreeInfo;  //program rule e.g. program completion rule
    Boolean isReadOnly;

    VerticalPanel rulePanel = new VerticalPanel();
    KSButton editButton = new KSButton("Edit", KSButtonAbstract.ButtonStyle.DEFAULT_ANCHOR);
    SpanPanel separator = new SpanPanel(" | ");
    KSButton deleteButton = new KSButton("Delete", KSButtonAbstract.ButtonStyle.DEFAULT_ANCHOR);
    KSButton addCompletionReqBtn = new KSButton("Add a Rule", KSButtonAbstract.ButtonStyle.FORM_SMALL);
    ReqSummaryDisplayWidget ruleSummaryViewWidget;

    public RuleSummaryDisplayWidget(String ruleName, String ruleCredits, String ruleDesc, StatementTreeViewInfo stmtTreeInfo, Boolean isReadOnly) {
        super();
        this.ruleName = ruleName;
        this.ruleCredits = ruleCredits;
        this.ruleDesc = ruleDesc;
        this.stmtTreeInfo = stmtTreeInfo;
        this.isReadOnly = isReadOnly;

        this.addStyleName("KS-Program-Rule-Summary-Box");
        redrawRule();
    }

    //user selected to delete this particular requirement
    private void deleteRequirement(StatementTreeViewInfo subTreeToDelete) {
        List<StatementTreeViewInfo> statements = stmtTreeInfo.getStatements();
        for (StatementTreeViewInfo subTree : statements) {
            if (subTree == subTreeToDelete) {
                statements.remove(subTree);
                redrawRule();
                break;
            }
        }
    }

    private void redrawRule() {
        //start with a header for the entire rule
        buildRuleHeader();

        //generate section for each sub-rule
        boolean addOperator = false;
        for (final StatementTreeViewInfo subTree : stmtTreeInfo.getStatements()) {
            if (addOperator) {
                buildANDOperator(stmtTreeInfo.getOperator());
            }

            ruleSummaryViewWidget = new ReqSummaryDisplayWidget(subTree, isReadOnly);
            ruleSummaryViewWidget.addDeleteButtonClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                    final ConfirmationDialog dialog = new ConfirmationDialog("Delete Requirement", "Are you sure you want to delete this requirement?"); //TODO app context for labels
                    dialog.getConfirmButton().addClickHandler(new ClickHandler(){
                            @Override
                            public void onClick(ClickEvent event) {
                        deleteRequirement(subTree);
                        dialog.hide();
                         }
                        });
                    dialog.show();
                }
            });

            rulePanel.add(ruleSummaryViewWidget);
            addOperator = true;
        }

        //add button for user to add additional subrule
        if (!isReadOnly) {
            addCompletionReqBtn.addStyleName("KS-Program-Rule-Summary-Add-Subrule");
            rulePanel.add(addCompletionReqBtn);
        }

        this.add(rulePanel);
    }

    private void buildRuleHeader() {

        //build heading
        SectionTitle header = SectionTitle.generateH4Title("");
        header.setHTML("<b>" + ruleName + "</b>" + "  " + ruleCredits);
        header.setStyleName("KS-Program-Rule-Summary-header");

        //do not show edit,delete etc. if user is only viewing the rule in non-edit mode
        if (!isReadOnly) {
            buildEditActionsWidget(header);
        }
        this.add(header);

        rulePanel.clear();
        rulePanel.addStyleName("KS-Program-Rule-Summary-Box1");

        KSLabel ruleDescLabel = new KSLabel(ruleDesc);
        ruleDescLabel.addStyleName("KS-Program-Rule-Summary-Desc");
        rulePanel.add(ruleDescLabel);

        //build subheading
        header = SectionTitle.generateH6Title("");
        header.setHTML("Must meet <b>all of the following</b> rules");
        header.setStyleName("KS-Program-Rule-Summary-Subrule-header");
        rulePanel.add(header);
    }

    private void buildEditActionsWidget(SectionTitle header) {
        SpanPanel actions = new SpanPanel();
        actions.add(editButton);
        actions.add(separator);
        actions.add(deleteButton);
        actions.addStyleName("KS-Program-Requirements-header-action");
        header.add(actions);
        rulePanel.add(header);
    }

    private void buildANDOperator(StatementOperatorTypeKey operator) {
        KSLabel andLabel = new KSLabel((operator == StatementOperatorTypeKey.AND ? "AND" : "OR"));
        andLabel.addStyleName("KS-Program-Rule-Summary-Operator");
        rulePanel.add(andLabel);        
    }

    public void addEditButtonClickHandler(ClickHandler handler) {
        editButton.addClickHandler(handler);
        ruleSummaryViewWidget.addEditButtonClickHandler(handler);        
    }

    public void addDeleteButtonClickHandler(ClickHandler handler) {
        deleteButton.addClickHandler(handler);
    }

    public void addAddRuleButtonClickHandler(ClickHandler handler) {
        addCompletionReqBtn.addClickHandler(handler);
    }
}