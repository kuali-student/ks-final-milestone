package org.kuali.student.core.statement.ui.client.widgets.rules;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.ULPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class SubrulePreviewWidget extends FlowPanel {

    private static final String OPERATOR_HEADER_OR = "Must meet 1 of the following:";
    private static final String OPERATOR_HEADER_AND = "Must meet all of the following:";
    private static final String SUBRULE_LI_CSS_CLASS = "KS-Rule-Preview-Subrule-li";
    private static final String SUBRULE_UL_CSS_CLASS = "KS-Program-Rule-Preview-Subrule-ul";
    private KSButton editButton = new KSButton("Edit", ButtonStyle.DEFAULT_ANCHOR);
    private SpanPanel separator = new SpanPanel(" | ");
    private KSButton deleteButton = new KSButton("Delete", ButtonStyle.DEFAULT_ANCHOR);
    private boolean isReadOnly = true;
    private Map<String, Widget> clusetWidgets;

    public SubrulePreviewWidget(StatementTreeViewInfo stmtTreeInfo, boolean isReadOnly, Map<String, Widget> clusetWidgets) {
        super();
        this.isReadOnly = isReadOnly;
        this.clusetWidgets = clusetWidgets;

        addStyleName("KS-Rule-Preview-Subrule-Box");
        showSubrule(stmtTreeInfo);
    }

    public void showSubrule(StatementTreeViewInfo stmtTreeInfo) {
        this.clear();

        if (stmtTreeInfo == null) {
            return;
        }

        buildRequirementHeader(stmtTreeInfo);
        buildRequirement(stmtTreeInfo);
    }

    private void buildRequirementHeader(StatementTreeViewInfo stmtTreeInfo) {

        SectionTitle header = SectionTitle.generateH6Title("");
        if (stmtTreeInfo.getOperator() == StatementOperatorTypeKey.AND) {
            header.setHTML("Must meet <b>all of the following:</b>");
        } else {
            header.setHTML("Must meet <b>1 of the following:</b>");
        }
        header.setStyleName("KS-Rule-Preview-Subrule-header");
        header.getElement().setAttribute("style", "font-weight: normal");

        //do not show edit,delete etc. if user is only viewing the rule in non-edit mode
        if (!isReadOnly) {
            buildEditActionsWidget(header);
        }
        this.add(header);
    }

    private void buildEditActionsWidget(SectionTitle header) {
        SpanPanel actions = new SpanPanel();
        actions.add(editButton);
        actions.add(separator);
        actions.add(deleteButton);
        actions.addStyleName("KS-Rule-Preview-Subrule-header-action");
        header.add(actions);
    }

    private void buildRequirement(StatementTreeViewInfo stmtTreeInfo) {
        Widget requirementsWidget = buildRequirementsWidget(stmtTreeInfo);
        
        if(requirementsWidget != null) {
            this.add(requirementsWidget);
        }
    }
    
    /**
     * 
     * This method handles creating the requirements preview widget at the parent level.  Further sub-statements are 
     * handled with recursive calls of another method 
     * 
     * @param rootStatement
     * @return
     */
    private ComplexPanel buildRequirementsWidget(StatementTreeViewInfo rootStatement) {
        ULPanel statementBulletList = new ULPanel();
        statementBulletList.setULClassName(SUBRULE_UL_CSS_CLASS);
        
        // handle simplest leaf case, where the statement has only requirement components
        if(rootStatement.getReqComponents() != null && !rootStatement.getReqComponents().isEmpty()) {
            
            populateRequirementComponentWidgets(rootStatement, statementBulletList);
            
        }
        else {
            // if no sub statements or requirement components, then return null, nothing to do
            if(rootStatement.getStatements() == null || rootStatement.getStatements().isEmpty()) {
                return null;
            }
            
            // statement has sub-statements, so prepare for recursion
            boolean firstInList = true;
            for(StatementTreeViewInfo subStatement : rootStatement.getStatements()) {
                
                statementBulletList.add(buildSubStatementWidget(subStatement, rootStatement, firstInList));
                firstInList = false;
            }
            
        }
        
        
        
        return statementBulletList;
    }

    /**
     * 
     * This method adds LI elements to the provided ULPanel.  Each element consists of the panel 
     * returned by buildRequirementPanel using each RequirementComponentInfo found in the provided 
     * StatementTreeViewInfo
     * 
     * @param statement
     * @param list
     */
    private void populateRequirementComponentWidgets(StatementTreeViewInfo statement, ULPanel list) {
        StatementOperatorTypeKey includedOperator = null;
        for (ReqComponentInfo reqComp : statement.getReqComponents()) {
            String nl = getPreviewNaturalLanguageForReqComponent(reqComp);
            
            list.add(buildRequirementPanel(reqComp, includedOperator, nl), SUBRULE_LI_CSS_CLASS);
            
            // only pass the operator after the first requirement panel is built
            if(includedOperator == null) {
                includedOperator = statement.getOperator();
            }
        }
    }

    /**
     * 
     * This method is called recursively to return a preview widget for the data in subStatement
     * 
     * @param subStatement
     * @param parentStatement 
     * @param firstInList flag to indicate that this statement is the first of its siblings, and does not need to start with the operator keyword
     * 
     * @return
     */
    private Widget buildSubStatementWidget(StatementTreeViewInfo subStatement, StatementTreeViewInfo parentStatement, boolean firstInList) {
        StatementOperatorTypeKey prefixOperator = null;
        
        boolean hasReqComponents = (subStatement.getReqComponents() != null && !subStatement.getReqComponents().isEmpty());
        
        // simplest case:  statement has one requisite component and its operator is the same as its parent
        if(hasReqComponents && subStatement.getReqComponents().size() == 1) {
            // return requirement component text, including operator text if applicable
            ReqComponentInfo reqComp = subStatement.getReqComponents().iterator().next();
            String nl = getPreviewNaturalLanguageForReqComponent(reqComp);
            if (!firstInList) {
                prefixOperator = parentStatement.getOperator();
            }
            return buildRequirementPanel(reqComp, prefixOperator, nl);
          }
        
        // The statement has one or more requisite components or sub-statements, so build the header text
        StringBuilder headerText = new StringBuilder();
        
        if (!firstInList) {
            prefixOperator = parentStatement.getOperator();
        }

        appendOperatorTag(headerText, prefixOperator);
        
        headerText.append(subStatement.getOperator() == StatementOperatorTypeKey.AND ? OPERATOR_HEADER_AND : OPERATOR_HEADER_OR);
        
        FlowPanel panel = new FlowPanel();
        panel.add(new HTML(headerText.toString()));
        
        if(hasReqComponents) {
            // requisite component case: a sub-statement with only requisite components
            ULPanel subrulePanel = new ULPanel();
            subrulePanel.setULClassName(SUBRULE_UL_CSS_CLASS);           
            populateRequirementComponentWidgets(subStatement, subrulePanel);            
            panel.add(subrulePanel);            
        }
        else {
            // sub-statement case: for each sub statement, call this method recursively
            boolean firstInSubList = true;
            for(StatementTreeViewInfo childStatement : subStatement.getStatements()) {
                ULPanel subStatementPanel = new ULPanel();
                subStatementPanel.setULClassName(SUBRULE_UL_CSS_CLASS);
                subStatementPanel.add(buildSubStatementWidget(childStatement, subStatement, firstInSubList), SUBRULE_LI_CSS_CLASS);
                panel.add(subStatementPanel);
                firstInSubList = false;
            }
            
        }
        
        return panel;
    }
    
    private void appendOperatorTag(StringBuilder builder, StatementOperatorTypeKey operator) {
        if(operator != null && builder != null) {
            builder.append("<span class=\"KS-Program-Rule-Preview-Subrule-ORAND\">").append(operator.toString()).append(" ").append("</span>");
        }
    }

    private String getPreviewNaturalLanguageForReqComponent(ReqComponentInfo reqComp) {
        String nl = null;
        if (reqComp instanceof ReqComponentInfoUi) {
            nl = ((ReqComponentInfoUi)reqComp).getPreviewNaturalLanguageTranslation();
        }
        if ( nl == null) {
            nl = reqComp.getNaturalLanguageTranslation();
        }
        return nl;
    }
    
    private FlowPanel buildRequirementPanel(ReqComponentInfo reqComp, StatementOperatorTypeKey operator, String requirement) {
        
        FlowPanel requirementPanel = new FlowPanel();
        
        StringBuilder htmlBuilder = new StringBuilder();
        appendOperatorTag(htmlBuilder, operator);
        htmlBuilder.append(requirement);
        
        requirementPanel.add(new HTML(htmlBuilder.toString()));

        //we need to display clu set show/hide list widget if this req. component has a cluset
        if ((clusetWidgets != null) && (reqComp != null)) {
            List<ReqCompFieldInfo> fieldInfos = reqComp.getReqCompFields();
            for (ReqCompFieldInfo fieldInfo : fieldInfos) {
                if (RulesUtil.isCluSetWidget(fieldInfo.getType())) {
                    Widget csWidget = clusetWidgets.get(fieldInfo.getValue());
                    if (csWidget != null) {
                        requirementPanel.add(csWidget);
                        break;
                    }
                }
            }
        }

        return requirementPanel;
    }

    public void addEditButtonClickHandler(ClickHandler handler) {
        editButton.addClickHandler(handler);
    }

    public void addDeleteButtonClickHandler(ClickHandler handler) {
        deleteButton.addClickHandler(handler);
    }
}