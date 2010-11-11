package org.kuali.student.common.ui.client.widgets.rules;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class RuleManageWidget extends FlowPanel {

    private RuleTableWidget manageRule = new RuleTableWidget();
    private RuleExpressionEditor logicalExpression = new RuleExpressionEditor();
    private SubrulePreviewWidget preview = new SubrulePreviewWidget(null, true, null);
    private KSTabPanel panel = new KSTabPanel(KSTabPanel.TabPanelStyle.SMALL);

    //widget's data
    private StatementTreeViewInfo rule = null;
    private static final String objectView = "Edit with Object";

    //TODO use application context for all labels
   
    public RuleManageWidget() {
        super();

        Map<String, Widget> tabLayoutMap = new HashMap<String, Widget>();

        tabLayoutMap.put(objectView, manageRule);
        panel.addTab(objectView, objectView, manageRule);

        String logicView = "Edit with Logic";
        tabLayoutMap.put(logicView, logicalExpression);
        panel.addTab(logicView, logicView, logicalExpression);
        panel.addTabCustomCallback(logicView, new Callback<String>() {
            @Override
            public void exec(String result) {
                updateLogicView();
            }
        });
        
        tabLayoutMap.put("Preview", preview);
        panel.addTab("Preview", "Preview", preview);
        panel.addTabCustomCallback("Preview", new Callback<String>() {
            @Override
            public void exec(String result) {
                updatePreview();
            }
        });

        panel.selectTab(objectView);
        add(panel);
    }

    public void redraw(StatementTreeViewInfo rule) {
        this.rule = rule;
        panel.selectTab(objectView);        
        updateObjectView();
    }

    //show rule in a table
    private void updateObjectView() {
        manageRule.redraw(rule, true);
    }

    private void updateLogicView() {
        logicalExpression.setRule(manageRule.getRule());
    }

    private void updatePreview() {
        preview.showSubrule(manageRule.getRule().getStatementVO().getStatementTreeViewInfo());
    }

    public void setReqCompEditButtonClickCallback(Callback<ReqComponentInfo> callback) {
        manageRule.addReqCompEditButtonClickCallback(callback);
    }

    public void setRuleChangedButtonClickCallback(Callback<Boolean> callback) {
        manageRule.addRuleChangedButtonClickCallback(callback);
    }

    public StatementTreeViewInfo getStatementTreeViewInfo() {
        return manageRule.getRule().getStatementVO().getStatementTreeViewInfo();
    }

    public void setEnabled(boolean enabled) {
        manageRule.setEnabledView(enabled);
        logicalExpression.setEnabledView(enabled);
    }
}
