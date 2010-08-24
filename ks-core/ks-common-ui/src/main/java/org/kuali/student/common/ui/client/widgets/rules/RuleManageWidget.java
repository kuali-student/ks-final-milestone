package org.kuali.student.common.ui.client.widgets.rules;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class RuleManageWidget extends FlowPanel {

    private KSTabPanel panel = new KSTabPanel(KSTabPanel.TabPanelStyle.SMALL);
    RuleTableWidget manageRule = new RuleTableWidget();
    KSLabel logicalExpression = new KSLabel();
    KSLabel naturalLanguage = new KSLabel();
    SubrulePreviewWidget preview;

    Boolean isInitialized = false;

	private Map<String, Widget> tabLayoutMap = new HashMap<String, Widget>();

    //widget's data
    StatementTreeViewInfo rule = null;

    //TODO use application context for all labels
   
    public RuleManageWidget() {
        super();
        setupHandlers();

        tabLayoutMap.put("Object View", manageRule);
        panel.addTab("Object View", "Object View", manageRule);        

        tabLayoutMap.put("Logic View", logicalExpression);
        panel.addTab("Logic View", "Logic View", logicalExpression);

        tabLayoutMap.put("Natural Language View", naturalLanguage);
        panel.addTab("Natural Language View", "Natural Language View", naturalLanguage);
        
      //  tabLayoutMap.put("Preview", preview);
//        panel.addTab("Preview", "Preview", preview);

        panel.selectTab("Object View");
        add(panel);
    }

    public void updateRuleViews(StatementTreeViewInfo updatedRule) {
        rule = updatedRule;

        if (!isInitialized) {
            preview = new SubrulePreviewWidget(rule, true);
            tabLayoutMap.put("Preview", preview);
            panel.addTab("Preview", "Preview", preview);
            isInitialized = true;
        }

        updateObjectView();
        updateLogicView();
        updateNaturalLanguageView();
        updatePreview();
    }

    //show rule in a table
    private void updateObjectView() {

        manageRule.setupHandlers();
        manageRule.setRuleTree(rule);
        manageRule.redraw();

        //Handler for when tab is clicked
        panel.addTabCustomCallback("My Test", new Callback<String>(){
            @Override
            public void exec(String result) {
              //  manageRule.setupHandlers();
//                manageRule.setRuleTree(rule);
//                manageRule.redraw();
            }
        });
    }

    private void updateLogicView() {
//TODO fix
       logicalExpression.setText((manageRule.getRule().getStatementVO() == null ? "" : manageRule.getRule().getStatementVO().convertToExpression()));
    }

    private void updateNaturalLanguageView() {
        naturalLanguage.setText(rule.getNaturalLanguageTranslation());
    }

    private void updatePreview() {

    }

   /*
       private KSLabel editManually = new KSLabel("Edit manually");
       
           editManually.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                rule.populateExpression();
                rule.setPreviewedExpression(rule.getExpression());
//TODO                getController().showView(PrereqViews.RULE_EXPRESSION_EDITOR, Controller.NO_OP_CALLBACK);
            }
        });
    */

    private void setupHandlers() {

    }    
}
