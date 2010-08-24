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
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.rules.RulePreviewWidget;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
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
import com.google.gwt.user.client.ui.FlowPanel;

public class ProgramRequirementsSummaryView extends VerticalSectionView {

    private final ProgramRpcServiceAsync programRemoteService = GWT.create(ProgramRpcService.class);
    private StatementRpcServiceAsync statementRpcServiceAsync = GWT.create(StatementRpcService.class);

    ProgramRequirementsViewController parentController;
    Boolean isReadOnly;
    enum requirementState {STORED, ADDED, EDITED, DELETED;}
    FlowPanel layout = new FlowPanel();
    ProgramRequirementInfo newRuleAddition;                         //references a rule that will have new subrule


    List<SpanPanel> perProgramRequirementTypePanel = new ArrayList<SpanPanel>();
    LinkedHashMap<String, LinkedHashMap<ProgramRequirementInfo, requirementState>> rules =
                                                     new LinkedHashMap<String, LinkedHashMap<ProgramRequirementInfo, requirementState>>();

    public ProgramRequirementsSummaryView(final ProgramRequirementsViewController parentController, Enum<?> viewEnum, String name, String modelId, List<String> programRequirements) {
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
            public void onSuccess(List<StatementTypeInfo> stmtInfoTypes) {
                for (StatementTypeInfo stmtInfoType : stmtInfoTypes) {
                    rules.put(stmtInfoType.getName(), new LinkedHashMap<ProgramRequirementInfo, requirementState>());

                    //TODO remove after testing
                    if (stmtInfoType.getId().equals("kuali.luStatementType.programEntrance")) {
                        LinkedHashMap<ProgramRequirementInfo, requirementState> tempRulesList = new LinkedHashMap<ProgramRequirementInfo, requirementState>();
                        ProgramRequirementInfo tempProgramInfo = new ProgramRequirementInfo();
                        RichTextInfo text = new RichTextInfo();
                        text.setPlain("These are classes or sequences that a student must have completed in order to register" +
                                                    " in the course. For example, students must have completed 3 classes with a specific GPA.");
                        tempProgramInfo.setDescr(text);
                        tempProgramInfo.setShortTitle("Expected Total Credits: 50 - 60");
                        tempProgramInfo.setStatement(parentController.getTestStatement());
                        tempRulesList.put(tempProgramInfo, requirementState.STORED);
                        rules.put(stmtInfoType.getName(), tempRulesList);
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

        //display 'Program Requirements' page title
        SectionTitle pageTitle = SectionTitle.generateH2Title(ProgramProperties.get().programRequirements_summaryViewPageTitle());
        pageTitle.setStyleName("KS-Program-Requirements-Page-Title");  //make the header orange
        layout.add(pageTitle);
        
        //iterate and display rules for each Program Requirement type e.g. Entrance Requirements, Completion Requirements
        Boolean firstRequirement = true;
        for (String stmtTypeName : rules.keySet()) {
            SpanPanel requirementsPanel = new SpanPanel();
            perProgramRequirementTypePanel.add(requirementsPanel);
            displayRequirementsForGivenType(requirementsPanel, stmtTypeName, rules.get(stmtTypeName), firstRequirement);
            firstRequirement = false;
        }

        addWidget(layout);

        //display 'Save and Continue' button
        displaySaveButton();
    }

    private void displayRequirementsForGivenType(final SpanPanel requirementsPanel, final String ruleTypeName, LinkedHashMap<ProgramRequirementInfo, requirementState> rules, boolean firstRequirement) {

        //display header for this Program Requirement type e.g. Entrance Requirements; make the header plural
        SectionTitle title = SectionTitle.generateH3Title(getWordPlural(ruleTypeName));
        title.setStyleName((firstRequirement ? "KS-Program-Requirements-Summary-Rule-Type-First-Header" : "KS-Program-Requirements-Summary-Rule-Type-Header"));  //make the header orange
        layout.add(title);

        //display "Add Rule" button if user is in 'edit' mode
        if (!isReadOnly) {
            String addRuleLabel = ProgramProperties.get().programRequirements_summaryViewPageAddRule().replace("<*>", ruleTypeName);
            KSButton addProgramReqBtn = new KSButton(addRuleLabel, KSButtonAbstract.ButtonStyle.FORM_SMALL);
            addProgramReqBtn.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                        showAddProgramRequirementDialog(requirementsPanel, ruleTypeName);
                    }
                });
            layout.add(addProgramReqBtn);
        }

        layout.add(requirementsPanel);
        
        //display "No entrance requirement currently exist for this program"
        if (rules == null || rules.size() == 0) {
            String noRuleText = ProgramProperties.get().programRequirements_summaryViewPageNoRule().replace("<*>", ruleTypeName.toLowerCase());
            KSLabel ruleDesc = new KSLabel(noRuleText);
            ruleDesc.addStyleName("KS-Program-Requirements-Summary-No-Rule-Text");
            layout.add(ruleDesc);
        } else {
            //now display each requirement for this Program Requirement type
            for (ProgramRequirementInfo ruleInfo : rules.keySet()) {
                addProgramRequirement(requirementsPanel, ruleTypeName, ruleInfo, requirementState.STORED);
            }
        }
    }

    private void addProgramRequirement(final SpanPanel requirementsPanel, final String ruleType, final ProgramRequirementInfo ruleInfo, requirementState ruleInitialState) {
        //first add new program requirement into the map of rules
        LinkedHashMap<ProgramRequirementInfo, requirementState> rulesPerType = rules.get(ruleType);
        rulesPerType.put(ruleInfo, ruleInitialState);

        final RulePreviewWidget ruleSummaryViewWidget =
                new RulePreviewWidget(ruleType, ruleInfo.getShortTitle(), ruleInfo.getDescr().getPlain(), ruleInfo.getStatement(), isReadOnly);

        ruleSummaryViewWidget.addRequirementEditButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                showEditProgramRequirementDialog(requirementsPanel, ruleType);
            }
        });

        ruleSummaryViewWidget.addRequirementDeleteButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                final ConfirmationDialog dialog = new ConfirmationDialog(
                        ProgramProperties.get().programRequirements_summaryViewPageDeleteRuleDialogTitle(),
                        ProgramProperties.get().programRequirements_summaryViewPageDeleteRuleDialogMsg());

                dialog.getConfirmButton().addClickHandler(new ClickHandler(){
                    @Override
                    public void onClick(ClickEvent event) {
                        //update rule status in the stored rules list
                        LinkedHashMap<ProgramRequirementInfo, requirementState> rulesPerType = rules.get(ruleType);

                        //we completely remove a rule that was added and then deleted without any save between
                        if (rulesPerType.get(ruleInfo) == requirementState.ADDED) {
                            rulesPerType.remove(ruleInfo);
                        } else {
                            rulesPerType.put(ruleInfo, requirementState.DELETED);  //overwrite previous state
                        }

                        //remove rule from display
                        requirementsPanel.remove(ruleSummaryViewWidget);

                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });

        /* SUB-RULE edit/delete/add link&buttons handlers */

       ruleSummaryViewWidget.addSubRuleAddButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                //allow only one rule being edited at at ime
                if (newRuleAddition == null) {
                    newRuleAddition = ruleInfo;
                    ((ProgramRequirementsManageView)parentController.getView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE)).setRuleTree(null, ruleInfo.getType());
                    parentController.showView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE);
                }
            }
        });        

       ruleSummaryViewWidget.addSubRuleEditCallback(editRuleCallback);

       requirementsPanel.add(ruleSummaryViewWidget);
    }

    private void displaySaveButton() {
        ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.SaveContinueCancelEnum.SAVE_CONTINUE, ButtonEnumerations.SaveContinueCancelEnum.CANCEL);
        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
                if (result == ButtonEnumerations.SaveContinueCancelEnum.CANCEL) {
                    //TODO
                } else {
                    //save all changes to program requirements
                    //for ()

                }
            }
        });
        addWidget(actionCancelButtons);        
    }

    protected Callback<StatementTreeViewInfo> editRuleCallback = new Callback<StatementTreeViewInfo>(){
        public void exec(StatementTreeViewInfo stmtTreeInfo) {
            ((ProgramRequirementsManageView)parentController.getView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE)).setRuleTree(stmtTreeInfo, stmtTreeInfo.getType());
            parentController.showView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE);
        }
    };

    private void showAddProgramRequirementDialog(final SpanPanel requirementsPanel, final String ruleType) {

        String addRuleText = ProgramProperties.get().programRequirements_summaryViewPageAddRule().replace("<*>", ruleType);
        final KSLightBox dialog = new KSLightBox(addRuleText);

	    ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.AddCancelEnum.ADD, ButtonEnumerations.AddCancelEnum.CANCEL);

        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
                if (result == ButtonEnumerations.AddCancelEnum.ADD) {
                    //create a new program requirement
                    //- call validate
                    //- call update method on vertical section view

                    //true if user is adding a new program requirement
                    ProgramRequirementInfo newProgramInfo = new ProgramRequirementInfo();
                    RichTextInfo text = new RichTextInfo();
                    text.setPlain("These are classes or sequences that a student must have completed in order to register" +
                                                " in the course. For example, students must have completed 3 classes with a specific GPA.");
                    newProgramInfo.setDescr(text);
                    newProgramInfo.setShortTitle("Expected Total Credits: 50 - 60");
                    newProgramInfo.setStatement(null); //parentController.getTestStatement());

                    addProgramRequirement(requirementsPanel, ruleType, newProgramInfo, requirementState.ADDED);
                }
                dialog.hide();
            }
        });

        //TODO need proper dialog here
        //VerticalSectionView layout = new VerticalSectionView();
        //layout.addStyleName("KS-Advanced-Search-Window");
        SpanPanel layout = new SpanPanel();
		layout.add(new KSLabel("TEST"));
		layout.add(actionCancelButtons);

        dialog.setSize(600,400);
        dialog.setWidget(layout);
        dialog.show();
    }

    private void showEditProgramRequirementDialog(final SpanPanel requirementsPanel, final String ruleType) {

        String addRuleText = ProgramProperties.get().programRequirements_summaryViewPageAddRule().replace("<*>", ruleType);
        final KSLightBox dialog = new KSLightBox(addRuleText);

	    ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.UpdateCancelEnum.UPDATE, ButtonEnumerations.UpdateCancelEnum.CANCEL);

        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
                if (result == ButtonEnumerations.UpdateCancelEnum.CANCEL) {
                 dialog.hide();
                } else {   //create a new program requirement
                    //- call validate
                    //- call update method on vertical section view
                    //true if user is editing existing program requirement
                }
            }
        });

        //TODO need proper dialog here
        //VerticalSectionView layout = new VerticalSectionView();
        //layout.addStyleName("KS-Advanced-Search-Window");
        SpanPanel layout = new SpanPanel();
		layout.add(new KSLabel("TEST"));
		layout.add(actionCancelButtons);

        dialog.setSize(600,400);
        dialog.setWidget(layout);
        dialog.show();
    }

    private String getWordPlural(String word) {
        return (word.endsWith("s") ? word : word + "s");
    }
}