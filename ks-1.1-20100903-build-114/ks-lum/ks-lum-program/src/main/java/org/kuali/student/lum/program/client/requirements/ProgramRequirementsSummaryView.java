package org.kuali.student.lum.program.client.requirements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
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
import com.google.gwt.user.client.ui.FlowPanel;

public class ProgramRequirementsSummaryView extends VerticalSectionView {

    private final ProgramRpcServiceAsync programRemoteService = GWT.create(ProgramRpcService.class);
    private StatementRpcServiceAsync statementRpcServiceAsync = GWT.create(StatementRpcService.class);

    //view's widgets
    private FlowPanel layout = new FlowPanel();
    private Map<String, KSLabel> noRuleTextMap = new HashMap<String, KSLabel>();

    //view's data
    private ProgramRequirementsViewController parentController;
    private boolean isReadOnly;
    private boolean isInitialized = false;
    static int tempProgReqInfoID = 9999;
    private enum requirementState {STORED, ADDED, EDITED, DELETED;}
    private List<SpanPanel> perProgramRequirementTypePanel = new ArrayList<SpanPanel>();
    private LinkedHashMap<StatementTypeInfo, LinkedHashMap<ProgramRequirementInfo, requirementState>> rules = new LinkedHashMap<StatementTypeInfo, LinkedHashMap<ProgramRequirementInfo, requirementState>>();

    public ProgramRequirementsSummaryView(final ProgramRequirementsViewController parentController, Enum<?> viewEnum, String name, String modelId, List<String> programRequirements) {
        super(viewEnum, name, modelId);

        this.parentController = parentController;
        isReadOnly = (parentController == null ? true : false);

        //retrieve available program requirement types
        statementRpcServiceAsync.getStatementTypesForStatementType("kuali.statement.type.program", new KSAsyncCallback<List<StatementTypeInfo>>() {
            @Override
            public void handleFailure(Throwable caught) {
	            Window.alert(caught.getMessage());
	            GWT.log("getStatementTypes failed", caught);
            }

            @Override
            public void onSuccess(List<StatementTypeInfo> stmtInfoTypes) {
                for (StatementTypeInfo stmtInfoType : stmtInfoTypes) {
                    rules.put(stmtInfoType, new LinkedHashMap<ProgramRequirementInfo, requirementState>());

                    //TODO remove after testing
                    if (stmtInfoType.getId().equals("kuali.statement.type.program.entrance")) {
                        LinkedHashMap<ProgramRequirementInfo, requirementState> tempRulesList = new LinkedHashMap<ProgramRequirementInfo, requirementState>();
                        ProgramRequirementInfo tempProgramInfo = new ProgramRequirementInfo();
                        RichTextInfo text = new RichTextInfo();
                        text.setPlain("These are classes or sequences that a student must have completed in order to register" +
                                                    " in the course. For example, students must have completed 3 classes with a specific GPA.");
                        tempProgramInfo.setDescr(text);
                        tempProgramInfo.setShortTitle("Expected Total Credits: 50 - 60");
                        tempProgramInfo.setStatement(parentController.getTestStatement());
                        tempRulesList.put(tempProgramInfo, requirementState.STORED);
                        rules.put(stmtInfoType, tempRulesList);
                    }
                }

                //retrieve program requirements
                /* TODO test when program service is ready
                for (String programReq : programRequirements) {
                    programRemoteService.getProgramRequirement(programReq, new KSAsyncCallback<ProgramRequirementInfo>() {
                        @Override
                        public void handleFailure(Throwable caught) {
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
        if (!isInitialized) {
            displayRules();
            isInitialized = true;
        }

        //update rule if user added or edited this rule
        ProgramRequirementsManageView manageView = (ProgramRequirementsManageView)parentController.getView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE);
        if (manageView.isDirty()) {
            ((SectionView)parentController.getCurrentView()).setIsDirty(false);
            StatementTreeViewInfo changedTree = manageView.getRuleTree();

            ProgramRequirementInfo affectedRequirement = getProgramRequirement(changedTree.getId());
            if (affectedRequirement == null) {
	            Window.alert("Cannot find program requirement with a statement that has id: '" + changedTree.getId() + "'");
	            GWT.log("Cannot find program requirement with a statement that has id: '" + changedTree.getId() + "'", null);                
            }

            if (rules.get(changedTree.getType()).get(affectedRequirement) == requirementState.STORED) {
                rules.get(changedTree.getType()).put(affectedRequirement, requirementState.EDITED);
            }

            if (manageView.isNewRule()) {
                affectedRequirement.getStatement().getStatements().add(changedTree);
            } else {
                //update rule
                if (changedTree.getStatements() == null || changedTree.getStatements().isEmpty()) {
                    affectedRequirement.setStatement(changedTree);
                } else {
                    affectedRequirement.getStatement().getStatements().add(changedTree);
                }
            }
        }

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
        for (StatementTypeInfo stmtTypeInfo : rules.keySet()) {

            //create and display one type of program requirement section
            SpanPanel requirementsPanel = new SpanPanel();
            perProgramRequirementTypePanel.add(requirementsPanel);
            displayRequirementSectionForGivenType(requirementsPanel, stmtTypeInfo, rules.get(stmtTypeInfo), firstRequirement);
            firstRequirement = false;

            //now display each requirement for this Program Requirement type
            for (ProgramRequirementInfo ruleInfo : rules.get(stmtTypeInfo).keySet()) {
                addProgramRequirement(requirementsPanel, stmtTypeInfo, ruleInfo, requirementState.STORED);
            }

        }

        addWidget(layout);

        //display 'Save and Continue' button
        displaySaveButton();
    }

    private void displayRequirementSectionForGivenType(final SpanPanel requirementsPanel, final StatementTypeInfo stmtTypeInfo, LinkedHashMap<ProgramRequirementInfo,
                                                                    requirementState> rules, boolean firstRequirement) {

        //display header for this Program Requirement type e.g. Entrance Requirements; make the header plural
        SectionTitle title = SectionTitle.generateH3Title(getWordPlural(stmtTypeInfo.getName()));
        title.setStyleName((firstRequirement ? "KS-Program-Requirements-Preview-Rule-Type-First-Header" : "KS-Program-Requirements-Preview-Rule-Type-Header"));  //make the header orange
        layout.add(title);

        //display "Add Rule" button if user is in 'edit' mode
        if (!isReadOnly) {
            String addRuleLabel = ProgramProperties.get().programRequirements_summaryViewPageAddRule().replace("<*>", stmtTypeInfo.getName());
            KSButton addProgramReqBtn = new KSButton(addRuleLabel, KSButtonAbstract.ButtonStyle.FORM_SMALL);
            addProgramReqBtn.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                        showAddProgramRequirementDialog(requirementsPanel, stmtTypeInfo);
                    }
                });
            layout.add(addProgramReqBtn);
        }

        layout.add(requirementsPanel);
        
        //add widget for displaying "No entrance requirement currently exist for this program"
        String noRuleText = ProgramProperties.get().programRequirements_summaryViewPageNoRule().replace("<*>", stmtTypeInfo.getName().toLowerCase());
        KSLabel ruleDesc = new KSLabel(noRuleText);
        ruleDesc.addStyleName("KS-Program-Requirements-Preview-No-Rule-Text");
        noRuleTextMap.put(stmtTypeInfo.getName(), ruleDesc);
        setupNoRuleText(stmtTypeInfo);
        layout.add(ruleDesc);
    }

    private void addProgramRequirement(final SpanPanel requirementsPanel, final StatementTypeInfo stmtTypeInfo, final ProgramRequirementInfo progReqInfo, requirementState ruleInitialState) {
        //first add new program requirement into the map of rules
        final LinkedHashMap<ProgramRequirementInfo, requirementState> rulesPerType = rules.get(stmtTypeInfo);
        rulesPerType.put(progReqInfo, ruleInitialState);

        final RulePreviewWidget ruleSummaryViewWidget =
            new RulePreviewWidget(stmtTypeInfo.getName(), progReqInfo.getShortTitle(), progReqInfo.getDescr().getPlain(), progReqInfo.getStatement(), isReadOnly);

        ruleSummaryViewWidget.addRequirementEditButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
            showEditProgramRequirementDialog(requirementsPanel, stmtTypeInfo.getName());
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
                        if (rulesPerType.get(progReqInfo) == requirementState.ADDED) {
                            //we completely remove a rule that was added and then deleted without any save between
                            rulesPerType.remove(progReqInfo);
                        } else {
                            rulesPerType.put(progReqInfo, requirementState.DELETED);  //overwrite previous state
                        }

                        //remove rule from display
                        requirementsPanel.remove(ruleSummaryViewWidget);
                        setupNoRuleText(stmtTypeInfo);

                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });

        /* SUB-RULE edit/delete/add link&buttons handlers */

       ruleSummaryViewWidget.addSubRuleAddButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                    ((ProgramRequirementsManageView)parentController.getView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE))
                                                                        .setRuleTree(null, progReqInfo.getStatement().getId(), stmtTypeInfo.getId());
                    parentController.showView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE);
            }
        });        

       ruleSummaryViewWidget.addSubRuleEditCallback(editRuleCallback);

       requirementsPanel.add(ruleSummaryViewWidget);
    }

    private void displaySaveButton() {
        ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.SaveContinueCancelEnum.SAVE_CONTINUE, ButtonEnumerations.SaveContinueCancelEnum.CANCEL);
        actionCancelButtons.addStyleName("KS-Program-Requirements-Preview-SaveContinue");
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
        layout.add(actionCancelButtons);
    }

    private void showAddProgramRequirementDialog(final SpanPanel requirementsPanel, final StatementTypeInfo stmtTypeInfo) {

        String addRuleText = ProgramProperties.get().programRequirements_summaryViewPageAddRule().replace("<*>", stmtTypeInfo.getName());
        final KSLightBox dialog = new KSLightBox(addRuleText);

	    ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.AddCancelEnum.ADD, ButtonEnumerations.AddCancelEnum.CANCEL);

        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
                if (result == ButtonEnumerations.AddCancelEnum.ADD) {
                    //- call validate
                    //- call update method on vertical section view
                    //create a new program requirement

                    ProgramRequirementInfo newProgramInfo = new ProgramRequirementInfo();
                    newProgramInfo.setId("ABC" + Integer.toString(tempProgReqInfoID++));   //set unique id
                    RichTextInfo text = new RichTextInfo();
                    text.setPlain("These are classes or sequences that a student must have completed in order to register" +
                                                " in the course. For example, students must have completed 3 classes with a specific GPA.");
                    newProgramInfo.setDescr(text);
                    newProgramInfo.setShortTitle("Expected Total Credits: 50 - 60");

                    //create a new statement tree
                    StatementTreeViewInfo stmtTree = new StatementTreeViewInfo();
                    stmtTree.setId("XYZ" + Integer.toString(tempProgReqInfoID++));  //set unique id
                    stmtTree.setType(stmtTypeInfo.getId());

                    newProgramInfo.setStatement(stmtTree);

                    addProgramRequirement(requirementsPanel, stmtTypeInfo, newProgramInfo, requirementState.ADDED);
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

    private void showEditProgramRequirementDialog(final SpanPanel requirementsPanel, final String ruleTypeName) {

        String addRuleText = ProgramProperties.get().programRequirements_summaryViewPageAddRule().replace("<*>", ruleTypeName);
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

    private void setupNoRuleText(StatementTypeInfo stmtTypeInfo) {
        boolean showNoRuleText = true;
        if (rules.get(stmtTypeInfo) != null && !rules.get(stmtTypeInfo).isEmpty()) {       
            for (ProgramRequirementInfo ruleInfo : rules.get(stmtTypeInfo).keySet()) {
                if (rules.get(stmtTypeInfo).get(ruleInfo) != requirementState.DELETED) {
                    showNoRuleText = false;
                }
            }
        }
        noRuleTextMap.get(stmtTypeInfo.getName()).setVisible(showNoRuleText);
    }    

    protected Callback<StatementTreeViewInfo> editRuleCallback = new Callback<StatementTreeViewInfo>(){
        public void exec(StatementTreeViewInfo stmtTreeInfo) {
            ((ProgramRequirementsManageView)parentController.getView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE))
                                                                            .setRuleTree(stmtTreeInfo, stmtTreeInfo.getId(), stmtTreeInfo.getType());
            parentController.showView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE);
        }
    };

    private ProgramRequirementInfo getProgramRequirement(String treeId) {
        for (StatementTypeInfo rulesPerType : rules.keySet()) {
            for (ProgramRequirementInfo ruleInfo : rules.get(rulesPerType).keySet()) {
                if (ruleInfo.getStatement().getId().equals(treeId)) {
                    return ruleInfo;
                }
            }
        }
        return null;
    }    

    private String getWordPlural(String word) {
        return (word.endsWith("s") ? word : word + "s");
    }
}