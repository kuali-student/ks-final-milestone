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

    private RuleTableManipulationWidget editObject = new RuleTableManipulationWidget();
    private RuleExpressionEditor editLogic = new RuleExpressionEditor(this);
    private SubrulePreviewWidget preview = new SubrulePreviewWidget(null, true, null);
    private KSTabPanel panel = new KSTabPanel(KSTabPanel.TabPanelStyle.SMALL);

    //widget's data
    private StatementTreeViewInfo rule = null;
    private static final String objectView = "Edit with Object";
    private static final String logicView = "Edit with Logic";
    private static final String previewView = "Preview";

    //TODO use application context for all labels
   
    public RuleManageWidget() {
        super();

        Map<String, Widget> tabLayoutMap = new HashMap<String, Widget>();

        tabLayoutMap.put(objectView, editObject);
        panel.addTab(objectView, objectView, editObject);
        panel.addTabCustomCallback(objectView, new Callback<String>() {
            @Override
            public void exec(String result) {
                //updateObjectView(false, null);
                editObject.redraw();
            }
        });

        tabLayoutMap.put(logicView, editLogic);
        panel.addTab(logicView, logicView, editLogic);
        panel.addTabCustomCallback(logicView, new Callback<String>() {
            @Override
            public void exec(String result) {
                updateLogicView();
            }
        });
        
        tabLayoutMap.put(previewView, preview);
        panel.addTab(previewView, previewView, preview);
        panel.addTabCustomCallback(previewView, new Callback<String>() {
            @Override
            public void exec(String result) {
                updatePreview();
            }
        });

        panel.selectTab(objectView);
        add(panel);
    }

    public void redraw(StatementTreeViewInfo rule, Boolean ruleChanged) {
        this.rule = rule;
        updateObjectView(ruleChanged);    //update object view first as other views depend on it
        updateLogicView();
        //if we are coming from summary view then make sure object view is the default view
        if (!ruleChanged) {
            panel.selectTab(objectView);        
        }
    }

    private void updateObjectView(Boolean ruleChanged) {
        if (rule != null) {
            editObject.redraw(rule, true, ruleChanged);
        }
    }

    private void updateLogicView() {
        if (rule != null) {
            editLogic.setRule(editObject.getRule());
        }
    }

    private void updatePreview() {
        preview.showSubrule(getStatementTreeViewInfo());
    }

    //called when user clicks 'update' or 'undo' on rule in rule expression editor
    protected void updateObjectRule(RuleInfo rule) {
        //this.rule = rule.getStatementVO().getStatementTreeViewInfo();
        editObject.setRule(rule);
    }

    public void setReqCompEditButtonClickCallback(Callback<ReqComponentInfo> callback) {
        editObject.addReqCompEditButtonClickCallback(callback);
        editLogic.addReqCompEditButtonClickCallback(callback);
    }

    public void setRuleChangedButtonClickCallback(Callback<Boolean> callback) {
        editObject.addRuleChangedButtonClickCallback(callback);
        editLogic.addRuleChangedButtonClickCallback(callback);
    }

    public StatementTreeViewInfo getStatementTreeViewInfo() {
        return editObject.getRule().getStatementVO().getStatementTreeViewInfo();
    }

    public void setEnabled(boolean enabled) {
        editObject.setEnabledView(enabled);
        editLogic.setEnabledView(enabled);
    }
}
