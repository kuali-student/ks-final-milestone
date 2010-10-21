package org.kuali.student.common.ui.client.widgets.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    //Widgets
    private SpanPanel rulePanel = new SpanPanel();
    private KSButton editButton = new KSButton("Edit", KSButtonAbstract.ButtonStyle.DEFAULT_ANCHOR);
    private SpanPanel separator = new SpanPanel(" | ");
    private KSButton deleteButton = new KSButton("Delete", KSButtonAbstract.ButtonStyle.DEFAULT_ANCHOR);
    private KSButton addSubRuleBtn = new KSButton("Add a Rule", KSButtonAbstract.ButtonStyle.FORM_SMALL);
    private List<SubrulePreviewWidget> subRulePreviewWidgets = new ArrayList<SubrulePreviewWidget>();
    private Map<String, Widget> clusetWidgets;
    private SectionTitle progReqHeader = SectionTitle.generateH4Title("");
    private KSLabel ruleDescLabel = new KSLabel();
    private SpanPanel actions = new SpanPanel();

    //data
    private StatementTreeViewInfo stmtTreeInfo;     //program rule e.g. program completion rule
    private boolean isReadOnly;
    private boolean addRuleOperator = false;        //first subrule does not have operator following it
    private Integer internalProgReqID;

    private Callback<SubRuleInfo> editSubRuleCallback;
    private Callback<Integer> deleteSubRuleCallback;

    public RulePreviewWidget(Integer internalProgReqID, String ruleName, String ruleCreditsText, String ruleDesc, StatementTreeViewInfo stmtTreeInfo,
                             Boolean isReadOnly,  Map<String, Widget> clusetWidgets) {
        super();
        this.internalProgReqID = internalProgReqID;
        this.stmtTreeInfo = stmtTreeInfo;
        this.isReadOnly = isReadOnly;
        this.clusetWidgets = clusetWidgets;

        //prepare widgets
        this.addStyleName("KS-Rule-Preview-Box");
        progReqHeader.setStyleName("KS-Rule-Preview-header");
        ruleDescLabel.addStyleName("KS-Rule-Preview-Desc");
        if (!isReadOnly) {
            buildEditActionsWidget();
        }
        updateProgInfoFields(ruleName, ruleCreditsText, ruleDesc);

        displayRule();
    }

    public void updateProgInfoFields(String ruleName, String ruleCreditsText, String ruleDesc) {
        progReqHeader.clear();
        progReqHeader.setHTML("<b>" + ruleName + "</b>" + "  " + ruleCreditsText);
        //do not show edit,delete etc. if user is only viewing the rule in non-edit mode
        if (!isReadOnly) {
            progReqHeader.add(actions);
        }
        ruleDescLabel.setText(ruleDesc);
    }

    private void displayRule() {
        //start with a header for the entire rule
        buildRuleHeader();

        //show sub-rules if we have any
        if (stmtTreeInfo == null) {
            displayNoRule();
        }

        if ((stmtTreeInfo.getStatements() == null) || (stmtTreeInfo.getStatements().isEmpty())) {
            if ((stmtTreeInfo.getReqComponents() == null) || (stmtTreeInfo.getReqComponents().isEmpty())) {
                displayNoRule();
            } else {
                addSubRule(stmtTreeInfo);
            }
        } else {
            for (final StatementTreeViewInfo subTree : stmtTreeInfo.getStatements()) {
                addSubRule(subTree);
            }
        }

        //button below has to be spaced
        SpanPanel spacer = new SpanPanel();
        spacer.addStyleName("KS-Rule-Last-Preview-Spacer");
        rulePanel.add(spacer);

        //add a button for user to add additional subrule
        if (!isReadOnly) {
            addSubRuleBtn.addStyleName("KS-Rule-Preview-Add-Subrule");
            rulePanel.add(addSubRuleBtn);
        }

        this.add(rulePanel);
    }

    private void displayNoRule() {
        KSLabel noRulesAddedYet = new KSLabel("No rules have been added yet.");
        noRulesAddedYet.addStyleName("KS-Rule-Preview-NoRule");
        rulePanel.add(noRulesAddedYet);
    }

    private void addSubRule(final StatementTreeViewInfo subTree) {
        //display AND/OR operator between subrules
        if (addRuleOperator) {
            buildANDOperator(stmtTreeInfo.getOperator());
        }

        final SubrulePreviewWidget newSubRuleWidget = new SubrulePreviewWidget(subTree, isReadOnly, clusetWidgets);        
        subRulePreviewWidgets.add(newSubRuleWidget);

        newSubRuleWidget.addEditButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                SubRuleInfo subRuleInfo = new SubRuleInfo();
                subRuleInfo.setSubrule(subTree);
                subRuleInfo.setInternalProgReqID(internalProgReqID);
                editSubRuleCallback.exec(subRuleInfo);
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
                        deleteSubRuleCallback.exec(internalProgReqID);
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
        boolean lastWidgetIsSubRule = true;
        boolean foundSubRule = false;
        int counter = 0;
        Widget widget = null;
        Widget widgetToRemove = null;
        Widget lastOperatorWidget = null;
        for (Object aRulePanel : rulePanel) {
            counter++;
            widget = (Widget) aRulePanel;
            if (widget instanceof KSLabel) {
                if (((KSLabel) widget).getText().equals("AND") || ((KSLabel) widget).getText().equals("OR")) {
                    lastWidgetIsSubRule = false;
                    lastOperatorWidget = widget;
                    numberOfOperators++;
                    //remove 'AND' above the first sub-rule or two repeated instances
                    if ((numberOfOperators > 1) || (counter == 1) || !foundSubRule) {
                        widgetToRemove = widget;
                        numberOfOperators--;
                    }
                }
            } else if (widget instanceof SubrulePreviewWidget) {
                foundSubRule = true;
                lastWidgetIsSubRule = true;
                numberOfOperators = 0;
            }
        }
        
        if (!lastWidgetIsSubRule) {
            rulePanel.remove(lastOperatorWidget);
        }
        if (widgetToRemove != null) {
            rulePanel.remove(widgetToRemove);
        }
    }

    private void buildRuleHeader() {
        rulePanel.clear();
        rulePanel.addStyleName("KS-Rule-Preview-Box1");
        rulePanel.add(progReqHeader);
        rulePanel.add(ruleDescLabel);

        //build subheading
        SectionTitle header = SectionTitle.generateH6Title("");
        header.setHTML("Must meet <b>all of the following</b> rules");
        header.setStyleName("KS-Rule-Preview-header-Subrule");
        header.getElement().setAttribute("style", "font-weight: normal");        
        rulePanel.add(header);
    }

    private void buildEditActionsWidget() {       
        actions.add(editButton);
        actions.add(separator);
        actions.add(deleteButton);
        actions.addStyleName("KS-Rule-Preview-header-action");
    }

    private void buildANDOperator(StatementOperatorTypeKey operator) {
        KSLabel andLabel = new KSLabel((operator == StatementOperatorTypeKey.AND ? "AND" : "OR"));
        andLabel.addStyleName("KS-Rule-Preview-Operator");
        rulePanel.add(andLabel);        
    }

    public void addProgReqEditButtonClickHandler(ClickHandler handler) {
        editButton.addClickHandler(handler);
    }

    public void addProgReqDeleteButtonClickHandler(ClickHandler handler) {
        deleteButton.addClickHandler(handler);
    }

    public void addSubRuleAddButtonClickHandler(ClickHandler handler) {
        addSubRuleBtn.addClickHandler(handler);
    }

    public void addSubRuleEditButtonClickHandler(Callback<SubRuleInfo> callback) {
        this.editSubRuleCallback = callback;
    }

    public void addSubRuleDeleteCallback(Callback<Integer> callback) {
        this.deleteSubRuleCallback = callback;
    }

    public StatementTreeViewInfo getStatementTreeViewInfo() {
        return  stmtTreeInfo;
    }

    public Integer getInternalProgReqID() {
        return internalProgReqID;
    }

    public class SubRuleInfo {
        private StatementTreeViewInfo subrule;

        private Integer internalProgReqID;

        public StatementTreeViewInfo getSubrule() {
            return subrule;
        }

        public void setSubrule(StatementTreeViewInfo subrule) {
            this.subrule = subrule;
        }

        public Integer getInternalProgReqID() {
            return internalProgReqID;
        }

        public void setInternalProgReqID(Integer internalProgReqID) {
            this.internalProgReqID = internalProgReqID;
        }
    }
}