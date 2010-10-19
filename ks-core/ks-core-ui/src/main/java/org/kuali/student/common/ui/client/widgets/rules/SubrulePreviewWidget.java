package org.kuali.student.common.ui.client.widgets.rules;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class SubrulePreviewWidget extends FlowPanel {

    private KSButton editButton = new KSButton("Edit", ButtonStyle.DEFAULT_ANCHOR);
    private SpanPanel separator = new SpanPanel(" | ");
    private KSButton deleteButton = new KSButton("Delete", ButtonStyle.DEFAULT_ANCHOR);
    private boolean isReadOnly = true;
    private Callback getClusetWidgetCallback;
    private Map<String, Widget> clusetWidgets;
    private final String CLU_SET_WIDGET_TAG = "[CLU_SET_WIDGET_TAG]";

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
    }

    private void buildEditActionsWidget(SectionTitle header) {
        SpanPanel actions = new SpanPanel();
        actions.add(editButton);
        actions.add(separator);
        actions.add(deleteButton);
        actions.addStyleName("KS-Rule-Preview-Subrule-header-action");
        header.add(actions);
        this.add(header);
    }

    private void buildRequirement(StatementTreeViewInfo stmtTreeInfo) {
        String html = buildOneRequirement(stmtTreeInfo, null, true, true).toString();

        boolean foundOpennigBracket = false;
        while (html.length() > 0) {

            int ix = html.indexOf(CLU_SET_WIDGET_TAG);
            if (ix == -1) {
                this.add(new HTML(html));
                break;
            }

            foundOpennigBracket = !foundOpennigBracket;
            if (ix == 0) {
                html = html.substring(CLU_SET_WIDGET_TAG.length());
                continue;
            }

            if (ix > 0) {
                String beforeTagString = html.substring(0, ix);
                if (foundOpennigBracket) {
                    this.add(new HTML(beforeTagString));
                } else {
                    this.add(clusetWidgets.get(beforeTagString));                    
                }
                html = html.substring(ix + CLU_SET_WIDGET_TAG.length());
                continue;                
            }
        }       
    }

    private StringBuffer buildOneRequirement(StatementTreeViewInfo stmtTreeInfo, StatementOperatorTypeKey upperLevelOperator, boolean firstLevel, boolean firstRequirement) {

        StringBuffer htmlText = new StringBuffer();
        List<StatementTreeViewInfo> stmtTreeList = stmtTreeInfo.getStatements();

        if ((stmtTreeList != null) && (stmtTreeList.size() > 0)) {
            StringBuffer htmlOneLevelText = new StringBuffer();
            firstRequirement = true;
            for (StatementTreeViewInfo subTree : stmtTreeList) {
                boolean trueFirstLevel = (firstLevel && (stmtTreeList.size() <= 1));
                htmlOneLevelText.append(buildOneRequirement(subTree, stmtTreeInfo.getOperator(), trueFirstLevel, firstRequirement));
                firstRequirement = htmlOneLevelText.toString().trim().isEmpty();
            }

            //only if we have other statements within this statement we indent
            if (firstLevel || stmtTreeInfo.getStatements().size() > 1) {

                if (!firstLevel) {
                    String operatorText = (stmtTreeInfo.getOperator() == StatementOperatorTypeKey.AND ?
                                            "Must meet all of the following:" : "Must meet 1 of the following:");
                    htmlText.append(addRequirementToList(null, upperLevelOperator, operatorText, firstRequirement));
                }

                htmlText.append("<ul class=\"KS-Program-Rule-Preview-Subrule-ul\">");
                htmlText.append(htmlOneLevelText);
                htmlText.append("</ul>");
            } else {
                htmlText.append(htmlOneLevelText);
            }

        } else if ((stmtTreeInfo.getReqComponents() != null) && !stmtTreeInfo.getReqComponents().isEmpty()) {
            List<ReqComponentInfo> reqComponents = stmtTreeInfo.getReqComponents();
            StringBuffer htmlListText = new StringBuffer();

            //build a bullet list of requirements
            boolean firstListRequirement = firstRequirement;
            if (!firstLevel && reqComponents.size() > 1) {
                firstListRequirement = true;   // we indent if we have more than one requirement on second or lower levels which means first requirement
            }
            for (ReqComponentInfo reqComp : reqComponents) {
                StatementOperatorTypeKey operator = (reqComponents.size() > 1 ? stmtTreeInfo.getOperator() : upperLevelOperator);
                htmlListText.append(addRequirementToList(reqComp, operator, reqComp.getNaturalLanguageTranslation(), firstListRequirement));
                firstListRequirement = false;
            }

            //indent if we have more than one requirement on second or lower levels
            if (firstLevel || reqComponents.size() == 1) {               
                htmlText.append(htmlListText);
            } else {
                String operatorText = (stmtTreeInfo.getOperator() == StatementOperatorTypeKey.AND ?
                                        "Must meet all of the following:" : "Must meet 1 of the following:");
                htmlText.append(addRequirementToList(null, upperLevelOperator, operatorText, firstRequirement));
              //  firstRequirement = false;
                htmlText.append("<ul class=\"KS-Program-Rule-Preview-Subrule-ul\">");
                htmlText.append(htmlListText);                
                htmlText.append("</ul>");
            }
        } else {
            return new StringBuffer("No rules have been added");    
        }

        return htmlText;
    }

    private StringBuffer addRequirementToList(ReqComponentInfo reqComp, StatementOperatorTypeKey operator, String requirement, boolean firstRequirement) {
        StringBuffer html = new StringBuffer();
        html.append("<li style=\"padding-top: 5px;\">");        
        if (!firstRequirement) {
            html.append("<span class=\"KS-Program-Rule-Preview-Subrule-ORAND\">");
            html.append(operator == StatementOperatorTypeKey.AND ? "AND " : "OR ");
            html.append("</span>");
        }
        html.append(requirement);

        //we need to display clu set show/hide list widget if this req. component has a cluset
        if ((clusetWidgets != null) && (reqComp != null)) {
            List<ReqCompFieldInfo> fieldInfos = reqComp.getReqCompFields();
            for (ReqCompFieldInfo fieldInfo : fieldInfos) {
                if (RulesUtil.isCluSetWidget(fieldInfo.getType())) {
                    if (clusetWidgets.get(fieldInfo.getValue()) != null) {
                        html.append(CLU_SET_WIDGET_TAG + fieldInfo.getValue() + CLU_SET_WIDGET_TAG); 
                        break;
                    }
                }
            }
        }

        html.append("</li>");        
        return html;
    }

    public void addEditButtonClickHandler(ClickHandler handler) {
        editButton.addClickHandler(handler);
    }

    public void addDeleteButtonClickHandler(ClickHandler handler) {
        deleteButton.addClickHandler(handler);    
    }

    public void setRetrieveClusetWidgetCallback(Callback<String> callback) {
        getClusetWidgetCallback = callback;
    }
}