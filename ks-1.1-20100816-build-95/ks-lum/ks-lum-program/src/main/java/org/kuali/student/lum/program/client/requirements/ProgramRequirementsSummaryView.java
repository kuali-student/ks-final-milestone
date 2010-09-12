package org.kuali.student.lum.program.client.requirements;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ActionCancelGroup;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.rules.RuleSummaryDisplayWidget;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.client.rpc.ProgramRpcServiceAsync;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;
import org.kuali.student.lum.program.client.rpc.StatementRpcServiceAsync;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProgramRequirementsSummaryView extends VerticalSectionView {

    private final ProgramRpcServiceAsync programRemoteService = GWT.create(ProgramRpcService.class);
    private StatementRpcServiceAsync statementRpcServiceAsync = GWT.create(StatementRpcService.class);

    RequirementsViewController parentController;
    Boolean isReadOnly;
    VerticalPanel layout = new VerticalPanel();

    LinkedHashMap<StatementTypeInfo, List<ProgramRequirementInfo>> rules = new LinkedHashMap<StatementTypeInfo, List<ProgramRequirementInfo>>();

    public ProgramRequirementsSummaryView(final RequirementsViewController parentController, Enum<?> viewEnum, String name, String modelId, List<String> programRequirements) {
        super(viewEnum, name, modelId);

        this.parentController = parentController;
        isReadOnly = (parentController == null ? true : false);

        //retrieve available program requirement types and display them
        statementRpcServiceAsync.getStatementTypesForStatementType("kuali.luStatementType.program", new AsyncCallback<List<StatementTypeInfo>>() {
            @Override
            public void onFailure(Throwable caught) {
	            Window.alert(caught.getMessage());
	            GWT.log("getStatementTypes failed", caught);
            }

            @Override
            public void onSuccess(List<StatementTypeInfo> stmtTypes) {
                for (StatementTypeInfo stmtType : stmtTypes) {
                    rules.put(stmtType, new ArrayList<ProgramRequirementInfo>());

                    //TODO remove after testing
                    if (stmtType.getId().equals("kuali.luStatementType.programEntrance")) {
                        List<ProgramRequirementInfo> tempRulesList = new ArrayList<ProgramRequirementInfo>();
                        ProgramRequirementInfo tempProgramInfo = new ProgramRequirementInfo();
                        RichTextInfo text = new RichTextInfo();
                        text.setPlain("These are classes or sequences that a student must have completed in order to register" +
                                                    " in the course. For example, students must have completed 3 classes with a specific GPA.");
                        tempProgramInfo.setDescr(text);
                        tempProgramInfo.setShortTitle("Expected Total Credits: 50 - 60");
                        tempProgramInfo.setStatement(parentController.getTestStatement());
                        tempRulesList.add(tempProgramInfo);
                        rules.put(stmtType, tempRulesList);
                    }
                }

                //retrieve program requirements
                /* TODO test when program service is ready
                for (String programReq : programRequirements) {
                    programRemoteService.getProgramRequirement(programReq, new AsyncCallback<ProgramRequirementInfo>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                            GWT.log("getProgramRequirement failed", caught);
                        }

                        @Override
                        public void onSuccess(ProgramRequirementInfo programReqInfo) {
                            //update list with new program requirements
                            for (StatementTypeInfo stmtInfo : rules.keySet()) {
                                if (stmtInfo.getId() == programReqInfo.getType()) {
                                    if (rules.get(stmtInfo) != null) {
                                        Window.alert("Found 2 same program requirements? ("+programReqInfo.getType());
                                        GWT.log("Found 2 same program requirements? ("+programReqInfo.getType());
                                    }
                                    rules.put(stmtInfo, programReqInfo);
                                    break;
                                }
                            }
                        }
                    });
                }*/
            }
        });
    }

    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
        displayRules();
        onReadyCallback.exec(true);
    }

    private void displayRules() {
        remove(layout);
        layout.clear();

        SectionTitle title = SectionTitle.generateH2Title(ProgramProperties.get().programRequirements_summaryViewPageTitle());
        title.setStyleName("KS-Program-Requirements-Section-header");  //make the header orange
        layout.add(title);
        
        //show rules for each program rule type
        Boolean firstHeader = true;
        for (StatementTypeInfo stmtInfo : rules.keySet()) {
            displayRulesForRuleType(stmtInfo.getName(), rules.get(stmtInfo), firstHeader);
            firstHeader = false;
        }

        addWidget(layout);
    }

    private void displayRulesForRuleType(final String ruleType, List<ProgramRequirementInfo> rules, boolean firstHeader) {

        //display rule type header e.g. Entrance Requirements
        SectionTitle title = SectionTitle.generateH3Title(ruleType);
        title.setStyleName((firstHeader ? "KS-Program-Requirements-Summary-Rule-Type-First-Header" : "KS-Program-Requirements-Summary-Rule-Type-Header"));  //make the header orange
        layout.add(title);

        //display text/add rule button
        if (!isReadOnly) {
            String addRuleText = ProgramProperties.get().programRequirements_summaryViewPageAddRule().replace("<*>", ruleType);
            KSButton addCompletionReqBtn = new KSButton(addRuleText, KSButtonAbstract.ButtonStyle.FORM_SMALL);
            addCompletionReqBtn.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                    showAddRequirementDialog(ruleType);
                }
            });
            layout.add(addCompletionReqBtn);
        }

        if (rules.size() == 0) {
            String noRuleText = ProgramProperties.get().programRequirements_summaryViewPageNoRule().replace("<*>", ruleType.toLowerCase());
            KSLabel ruleDesc = new KSLabel(noRuleText);
            ruleDesc.addStyleName("KS-Program-Requirements-Summary-No-Rule-Text");
            layout.add(ruleDesc);
        }

        //now display each rule for this rule type
        for (ProgramRequirementInfo ruleInfo : rules) {
            displayRule(ruleType, ruleInfo);
        }
    }

    private void showAddRequirementDialog(String ruleType) {

        String addRuleText = ProgramProperties.get().programRequirements_summaryViewPageAddRule().replace("<*>", ruleType.toLowerCase());
        final KSLightBox dialog = new KSLightBox(addRuleText);
	    ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.AddCancelEnum.ADD, ButtonEnumerations.AddCancelEnum.CANCEL);        

        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
                 if (result == ButtonEnumerations.SearchCancelEnum.CANCEL) {
                     dialog.hide();
                 } else {
                     displayRules();
                 }
            }
        });

        //TODO need proper dialog here
        VerticalFlowPanel layout = new VerticalFlowPanel();
        //layout.addStyleName("KS-Advanced-Search-Window");
		layout.add(new KSLabel("TEST"));
		layout.add(actionCancelButtons);

      //  dialog.setSize(600,400);
        dialog.setWidget(layout);
        dialog.show();
    }

    private void displayRule(String ruleType, final ProgramRequirementInfo ruleInfo) {

        RuleSummaryDisplayWidget ruleSummaryViewWidget =
                new RuleSummaryDisplayWidget(ruleType, ruleInfo.getShortTitle(), ruleInfo.getDescr().getPlain(), ruleInfo.getStatement(), isReadOnly);

        ruleSummaryViewWidget.addAddRuleButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                ((ProgramRequirementsManageView)parentController.getView(RequirementsViewController.ProgramRequirementsViews.MANAGE)).setRuleTree(null);
                parentController.showView(RequirementsViewController.ProgramRequirementsViews.MANAGE);
            }
        });

        ruleSummaryViewWidget.addEditButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                ((ProgramRequirementsManageView)parentController.getView(RequirementsViewController.ProgramRequirementsViews.MANAGE)).setRuleTree(ruleInfo.getStatement());
                parentController.showView(RequirementsViewController.ProgramRequirementsViews.MANAGE);
            }
        });

        ruleSummaryViewWidget.addDeleteButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                final ConfirmationDialog dialog = new ConfirmationDialog(
                        ProgramProperties.get().programRequirements_summaryViewPageDeleteRuleDialogTitle(),
                        ProgramProperties.get().programRequirements_summaryViewPageDeleteRuleDialogMsg());
                dialog.getConfirmButton().addClickHandler(new ClickHandler(){
                    @Override
                    public void onClick(ClickEvent event) {
                        //TODO how to delete rule...
                        displayRules();
                        dialog.hide();  //TODO when to hide dialog - after displayRules finishes?
                    }
                });
                dialog.show();
            }
        });

        layout.add(ruleSummaryViewWidget);
    }
}