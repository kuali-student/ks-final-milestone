package org.kuali.student.common.ui.client.widgets.rules;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class RuleManageWidget extends FlowPanel {

    private RuleTableWidget manageRule = new RuleTableWidget();
    private KSLabel logicalExpression = new KSLabel();
    //private KSLabel naturalLanguage = new KSLabel();
    private SubrulePreviewWidget preview = new SubrulePreviewWidget(null, true);

    //widget's data
    private StatementTreeViewInfo rule = null;

    //TODO use application context for all labels
   
    public RuleManageWidget() {
        super();

        Map<String, Widget> tabLayoutMap = new HashMap<String, Widget>();
        KSTabPanel panel = new KSTabPanel(KSTabPanel.TabPanelStyle.SMALL);

        String objectView = "Edit with Object";
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

        /*
        tabLayoutMap.put("Natural Language View", naturalLanguage);
        panel.addTab("Natural Language View", "Natural Language View", naturalLanguage);
        panel.addTabCustomCallback("Natural Language View", new Callback<String>() {
            @Override
            public void exec(String result) {
                updateNaturalLanguageView();
            }
        });  */
        
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
        updateObjectView();
      //  updateLogicView();
      //  updateNaturalLanguageView();
      //  updatePreview();
    }

    //show rule in a table
    private void updateObjectView() {
        manageRule.redraw(rule, true);
    }

    private void updateLogicView() {
       logicalExpression.setText(getLogicExpression());
       //rule.populateExpression();
       //rule.setPreviewedExpression(rule.getExpression());
       //TODO add a link to RuleExpressionEditor
    }

    /*
    private void updateNaturalLanguageView() {
        naturalLanguage.setText(rule.getNaturalLanguageTranslation());
    } */

    public String getLogicExpression() {
        return (manageRule.getRule().getStatementVO() == null ? "" : manageRule.getRule().getStatementVO().convertToExpression());
    }

    private void updatePreview() {
        preview.showSubrule(manageRule.getRule().getStatementVO().getStatementTreeViewInfo());
    }

    public void setReqCompEditButtonClickCallback(Callback<ReqComponentInfo> callback) {
        manageRule.addReqCompEditButtonClickCallback(callback);
    }

    public StatementTreeViewInfo getStatementTreeViewInfo() {
        return manageRule.getRule().getStatementVO().getStatementTreeViewInfo();
    }
}
