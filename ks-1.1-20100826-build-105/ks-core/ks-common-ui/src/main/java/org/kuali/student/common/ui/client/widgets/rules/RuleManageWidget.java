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
    private KSLabel naturalLanguage = new KSLabel();
    private SubrulePreviewWidget preview = new SubrulePreviewWidget(null, true);

    //widget's data
    private StatementTreeViewInfo rule = null;

    //TODO use application context for all labels
   
    public RuleManageWidget() {
        super();

        Map<String, Widget> tabLayoutMap = new HashMap<String, Widget>();
        KSTabPanel panel = new KSTabPanel(KSTabPanel.TabPanelStyle.SMALL);

        tabLayoutMap.put("Object View", manageRule);
        panel.addTab("Object View", "Object View", manageRule);

        tabLayoutMap.put("Logic View", logicalExpression);
        panel.addTab("Logic View", "Logic View", logicalExpression);

        tabLayoutMap.put("Natural Language View", naturalLanguage);
        panel.addTab("Natural Language View", "Natural Language View", naturalLanguage);
        
        tabLayoutMap.put("Preview", preview);
        panel.addTab("Preview", "Preview", preview);

        panel.selectTab("Object View");
        add(panel);
    }

    public void redraw(StatementTreeViewInfo rule) {
        this.rule = rule;
        updateObjectView();
        updateLogicView();
        updateNaturalLanguageView();
        updatePreview();
    }

    //show rule in a table
    private void updateObjectView() {
        manageRule.redraw(ObjectClonerUtil.clone(rule));
    }

    private void updateLogicView() {
       logicalExpression.setText((manageRule.getRule().getStatementVO() == null ? "" : manageRule.getRule().getStatementVO().convertToExpression()));
       //rule.populateExpression();
       //rule.setPreviewedExpression(rule.getExpression());
       //TODO add a link to RuleExpressionEditor
    }

    private void updateNaturalLanguageView() {
        naturalLanguage.setText(rule.getNaturalLanguageTranslation());
    }

    private void updatePreview() {
        preview.showSubrule(rule);
    }

    public void setReqCompEditButtonClickCallback(Callback<ReqComponentInfo> callback) {
        manageRule.addReqCompEditButtonClickCallback(callback);
    }
}
