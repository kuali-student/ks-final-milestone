package org.kuali.student.common.ui.client.widgets.rules;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class SubrulePreviewWidget extends FlowPanel {

    KSButton editButton = new KSButton("Edit", ButtonStyle.DEFAULT_ANCHOR);
    SpanPanel separator = new SpanPanel(" | ");
    KSButton deleteButton = new KSButton("Delete", ButtonStyle.DEFAULT_ANCHOR);

    public SubrulePreviewWidget(StatementTreeViewInfo stmtTreeInfo, boolean isReadOnly) {
        super();

        addStyleName("KS-Program-Rule-Preview-Subrule-Box");
        buildRequirementHeader(stmtTreeInfo, isReadOnly);
        buildRequirement(stmtTreeInfo);
    }

    private void buildRequirementHeader(StatementTreeViewInfo stmtTreeInfo, Boolean isReadOnly) {

        SectionTitle header = SectionTitle.generateH6Title("");
        if (stmtTreeInfo.getOperator() == StatementOperatorTypeKey.AND) {
            header.setHTML("Must meet <b>all of the following:</b>");
        } else {
            header.setHTML("Must meet <b>1 of the following:</b>");
        }      
        header.setStyleName("KS-Program-Rule-Preview-Subrule-header");
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
        actions.addStyleName("KS-Program-Rule-Preview-Subrule-header-action");
        header.add(actions);
        this.add(header);
    }

    private void buildRequirement(StatementTreeViewInfo stmtTreeInfo) {
        this.add(new HTML(buildOneRequirement(stmtTreeInfo).toString()));
    }

    private StringBuffer buildOneRequirement(StatementTreeViewInfo stmtTreeInfo) {

        List<StatementTreeViewInfo> stmtTree = stmtTreeInfo.getStatements();
        if (stmtTree != null) {
            for (StatementTreeViewInfo subTree : stmtTreeInfo.getStatements()) {
                return buildOneRequirement(subTree);
            }
        } else if (stmtTreeInfo.getReqComponents() != null) {
            List<ReqComponentInfo> reqComponents = stmtTreeInfo.getReqComponents();
            boolean firstComp = true;
            StringBuffer htmlText = new StringBuffer();
            htmlText.append("<ul class=\"KS-Program-Rule-Preview-Subrule-ul\">");
            for (ReqComponentInfo reqComp : reqComponents) {
                htmlText.append("<li style=\"padding-top: 5px;\">");
                if (!firstComp) {
                    htmlText.append("<span class=\"KS-Program-Rule-Preview-Subrule-ORAND\">");
                    htmlText.append((stmtTreeInfo.getOperator() == StatementOperatorTypeKey.AND ? "AND " : "OR "));
                    htmlText.append("</span>");        
                }
                firstComp = false;
                htmlText.append(reqComp.getNaturalLanguageTranslation());
                htmlText.append("</li>");
            }
            htmlText.append("</ul>");
            return htmlText;
        }

        return new StringBuffer("");
    }

    public void addEditButtonClickHandler(ClickHandler handler) {
        editButton.addClickHandler(handler);
    }

    public void addDeleteButtonClickHandler(ClickHandler handler) {
        deleteButton.addClickHandler(handler);    
    }    
}