package org.kuali.student.lum.program.client.requirements;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

import java.util.List;

public class RequirementDisplayWidget extends FlowPanel {

    LayoutController parentController;
    StatementTreeViewInfo stmtTreeInfo;

    //list...; controller have application handler to handle view course sets - controller get courses and fetches the info back
      
    public RequirementDisplayWidget(LayoutController parentController, StatementTreeViewInfo stmtTreeInfo) {
        super();

        this.parentController = parentController;
        this.stmtTreeInfo = stmtTreeInfo;

        //start with a header for the entire rule
        buildRuleHeader();

        //do we have subrules or only one simple rule with no ANDs between?
        /*
        if (stmtTreeInfo.getStatements() == null) {
            buildSubRule(stmtTreeInfo);
            return;
        } */

        if (stmtTreeInfo.getOperator() != StatementOperatorTypeKey.AND) {
            try {
                throw new Exception("Invalid statement operator. Expected 'AND' but found 'OR'");
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        //generate section for each ANDed sub-rule
        boolean addANDoperator = false;
        for (StatementTreeViewInfo subTree : stmtTreeInfo.getStatements()) {
            if (addANDoperator) {
                buildANDOperator();
            }
            buildSubRule(subTree);
            addANDoperator = true;
        }
    }

    private void buildRuleHeader() {
        SectionTitle header = SectionTitle.generateH5Title("");
        header.setHTML("Must have completed <b>all of the following:</b>");
        header.setStyleName("KS-Program-Requirements-Rule-header");
        this.add(header);
    }

    private void buildANDOperator() {
        KSLabel andLabel = new KSLabel("AND");
        andLabel.addStyleName("KS-Program-Requirements-Rule-AND");
        this.add(andLabel);        
    }

    private void buildSubRule(final StatementTreeViewInfo stmtTreeInfo) {

        //build header
        SectionTitle header = SectionTitle.generateH6Title("");
        header.setHTML("Must have completed <b>1 of the following:</b>");
        header.setStyleName("KS-Program-Requirements-SubRule-header");

        //do not show edit,delete etc. if user is only viewing the rule (non-edit mode)
        if (parentController != null) {
            buildEditActionsWidget(header);
        }

        //build context
        this.add(new HTML(buildContent(stmtTreeInfo, stmtTreeInfo.getOperator()).toString()));       
    }

    private void buildEditActionsWidget(SectionTitle header) {

        KSButton editButton = new KSButton("Edit", ButtonStyle.DEFAULT_ANCHOR);
        SpanPanel separator = new SpanPanel(" | ");
        KSButton deleteButton = new KSButton("Delete", ButtonStyle.DEFAULT_ANCHOR);
        
        SpanPanel actions = new SpanPanel();
        actions.add(editButton);
        actions.add(separator);
        actions.add(deleteButton);
        actions.addStyleName("KS-Program-Requirements-SubRule-header-action");
        header.add(actions);
        this.add(header);

        editButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                ((ProgramRequirementsManageView)parentController.getView(RequirementsViewController.ProgramRequirementsViews.MANAGE)).setRuleTree(stmtTreeInfo);
                parentController.showView(RequirementsViewController.ProgramRequirementsViews.MANAGE);
            }
        });
        deleteButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {

            }
        });
    }

    private StringBuffer buildContent(StatementTreeViewInfo stmtTreeInfo, StatementOperatorTypeKey operator) {

        // if (cluset) create sublist with cluset
        // if (sublist) create sublist

        List<StatementTreeViewInfo> stmtTree = stmtTreeInfo.getStatements();
        if (stmtTree != null) {
            for (StatementTreeViewInfo subTree : stmtTreeInfo.getStatements()) {
                return buildContent(subTree, subTree.getOperator());
            }
        } else {
            List<ReqComponentInfo> reqComponents = stmtTreeInfo.getReqComponents();
            boolean firstComp = true;
            StringBuffer htmlText = new StringBuffer();
            htmlText.append("<ul class=\"KS-Program-Requirements-SubRule-ul\">");
            for (ReqComponentInfo reqComp : reqComponents) {
                htmlText.append("<li style=\"padding-top: 5px;\">");
                if (!firstComp) {
                    htmlText.append("<span class=\"KS-Program-Requirements-SubRule-ORAND\">");
                    htmlText.append((operator == StatementOperatorTypeKey.AND ? "AND " : "OR "));
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
                /*
                new String("<ul class=\"KS-Program-Requirements-SubRule-ul\">" +
                "    <li style=\"padding-top: 5px;\">1 course from PROFESSIONAL WRITING</li>" +
                "    <li style=\"padding-top: 5px;\"><span class=\"KS-Program-Requirements-SubRule-ORAND\">OR</span> Earned a minimum grade of letter grade A in</li>" +
                "    <li style=\"padding-top: 5px; list-style-type: none;\">ENGL 101 ACADEMIC WRITING</li>" +
                "    <div><ul class=\"KS-Program-Requirements-SubRule-ul\"><li>test</li></ul></div>" +
                "  </ul></div></span></div>");  */
    }


}