package org.kuali.student.lum.program.client.requirements;

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
    private Map<String, SpanPanel> perProgramRequirementTypePanel = new HashMap<String, SpanPanel>();
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
                                    programReqInfo.setType(stmtInfo.getId());
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

            final StatementTreeViewInfo newTree = manageView.getRuleTree();

            //find the affected program requirement
            LinkedHashMap<ProgramRequirementInfo, requirementState> reqInfo = null;
            ProgramRequirementInfo affectedRequirement = null;
            StatementTypeInfo affectedStatementTypeInfo = null;
            for (StatementTypeInfo statementTypeInfo : rules.keySet()) {
                for (ProgramRequirementInfo Info : rules.get(statementTypeInfo).keySet()) {
                    if (!compareStatementTrees(Info.getStatement(), newTree)) {
                        continue;
                    }
                    reqInfo = rules.get(statementTypeInfo);
                    affectedRequirement = Info;
                    affectedStatementTypeInfo = statementTypeInfo;
                    break;
                }
            }

            if (reqInfo == null) {
	            Window.alert("Cannot find program requirement with a statement that has id: '" + newTree.getId() + "'");
	            GWT.log("Cannot find program requirement with a statement that has id: '" + newTree.getId() + "'", null);
                onReadyCallback.exec(true);
                return;
            }

            if (reqInfo.get(affectedRequirement) == requirementState.STORED) {
                reqInfo.put(affectedRequirement, requirementState.EDITED);
            }

            List<StatementTreeViewInfo> affectedStatements = affectedRequirement.getStatement().getStatements();
            if (manageView.isNewRule()) {
                affectedStatements.add(newTree);
            } else {
                //update rule
                if (affectedStatements == null || affectedStatements.isEmpty()) {
                    affectedRequirement.setStatement(newTree);
                } else { //replace rule with new version                    
                    for (StatementTreeViewInfo tree : affectedStatements) {
                        if (tree.getId().equals(newTree.getId())) {
                            int treeIx = affectedStatements.indexOf(tree);
                            //only update if the rule is not empty
                            if (!isEmptyRule(newTree)) {
                                affectedStatements.add(treeIx, newTree);
                            }
                            affectedStatements.remove(tree);
                            break;
                        }
                    }
                }
            }

            //update display of the rule
            SpanPanel reqPanel = perProgramRequirementTypePanel.get(affectedStatementTypeInfo.getId());
            for (int i = 0; i < perProgramRequirementTypePanel.get(affectedStatementTypeInfo.getId()).getWidgetCount(); i++) {
                RulePreviewWidget rulePreviewWidget = (RulePreviewWidget)reqPanel.getWidget(i);
                if (compareStatementTrees(affectedRequirement.getStatement(), rulePreviewWidget.getStatementTreeViewInfo())) {
                        RulePreviewWidget newRulePreviewWidget = getUpdatedProgramRequirement(reqPanel, affectedStatementTypeInfo, affectedRequirement);
                        reqPanel.insert(newRulePreviewWidget, i);
                        reqPanel.remove(rulePreviewWidget);
                }
            }

            //update NL for the rule
            /* not needed because the service does not care about NL in statement tree view info object
            statementRpcServiceAsync.translateStatementTreeViewToNL(newTree, ProgramRequirementsViewController.RULEEDIT_TEMLATE, ProgramRequirementsViewController.TEMLATE_LANGUAGE,
                                                                            new KSAsyncCallback<String>() {
                public void handleFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                    GWT.log("translateStatementTreeViewToNL failed", caught);
               }

                public void onSuccess(final String statementNaturalLanguage) {
                    newTree.setNaturalLanguageTranslation(statementNaturalLanguage);
                }
            }); */
        }

        onReadyCallback.exec(true);
    }

    private boolean compareStatementTrees(StatementTreeViewInfo tree1, StatementTreeViewInfo tree2) {
        boolean found = false;
        boolean noStatementsInTree1 = (tree1.getStatements() == null) || tree1.getStatements().isEmpty();
        boolean noStatementsInTree2 = (tree2.getStatements() == null) || (tree2.getStatements().isEmpty());

        if (noStatementsInTree1 && noStatementsInTree2) {
            found = tree1.getId().equals(tree2.getId());
        } else if (noStatementsInTree1) {
            found = findStatementBasedOnID(tree1.getId(), tree2);
        } else if (noStatementsInTree2) {
            found = findStatementBasedOnID(tree2.getId(), tree1);
        } else {
            for (StatementTreeViewInfo oneTree : tree1.getStatements()) {
                found = findStatementBasedOnID(oneTree.getId(), tree2);
                if (found) {
                    break;
                }
            }
        }
        return found;
    }

    private boolean findStatementBasedOnID(String id, StatementTreeViewInfo tree) {
        boolean found = false;
        for (StatementTreeViewInfo oneTree : tree.getStatements()) {
            if (oneTree.getId().equals(id)) {
                found = true;
                break;
            }
        }
        return found;
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
            perProgramRequirementTypePanel.put(stmtTypeInfo.getId(), requirementsPanel);
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

    private void addProgramRequirement(final SpanPanel requirementsPanel, final StatementTypeInfo stmtTypeInfo, final ProgramRequirementInfo progReqInfo,
                                       requirementState ruleInitialState) {
        //first add new program requirement into the map of rules
        rules.get(stmtTypeInfo).put(progReqInfo, ruleInitialState);
        final RulePreviewWidget rulePreviewWidget = new RulePreviewWidget(stmtTypeInfo.getName(), progReqInfo.getShortTitle(), progReqInfo.getDescr().getPlain(),
                                                    progReqInfo.getStatement(), isReadOnly);

        addRulePreviewWidgetHandlers(requirementsPanel, rulePreviewWidget, stmtTypeInfo, progReqInfo);

        requirementsPanel.add(rulePreviewWidget);
    }

    private RulePreviewWidget getUpdatedProgramRequirement(final SpanPanel requirementsPanel, final StatementTypeInfo stmtTypeInfo, final ProgramRequirementInfo progReqInfo) {

        final RulePreviewWidget rulePreviewWidget = new RulePreviewWidget(stmtTypeInfo.getName(), progReqInfo.getShortTitle(), progReqInfo.getDescr().getPlain(),
                                                    progReqInfo.getStatement(), isReadOnly);
        addRulePreviewWidgetHandlers(requirementsPanel, rulePreviewWidget, stmtTypeInfo, progReqInfo);
        return rulePreviewWidget;
    }

    private void addRulePreviewWidgetHandlers(final SpanPanel requirementsPanel, final RulePreviewWidget rulePreviewWidget, final StatementTypeInfo stmtTypeInfo,
                                              final ProgramRequirementInfo progReqInfo) {

        final LinkedHashMap<ProgramRequirementInfo, requirementState> rulesPerType = rules.get(stmtTypeInfo);

        rulePreviewWidget.addRequirementEditButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
            showEditProgramRequirementDialog(requirementsPanel, stmtTypeInfo.getName());
            }
        });

        rulePreviewWidget.addRequirementDeleteButtonClickHandler(new ClickHandler(){
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
                        requirementsPanel.remove(rulePreviewWidget);
                        setupNoRuleText(stmtTypeInfo);

                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });

        /* SUB-RULE edit/delete/add link&buttons handlers */

        rulePreviewWidget.addSubRuleAddButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {

                    //if user is adding a new rule to existing rules then pass in only a new statement
                    StatementTreeViewInfo manageStatement = progReqInfo.getStatement();
                    StatementTreeViewInfo rule = progReqInfo.getStatement();
                    if ((rule.getStatements() != null) && !rule.getStatements().isEmpty()) {
                        //do not add the new statement to the rule until user clicks 'save' on rule manage screen
                        manageStatement = new StatementTreeViewInfo(); 
                        manageStatement.setId("XYZ" + Integer.toString(tempProgReqInfoID++));  //set unique id
                        manageStatement.setType(stmtTypeInfo.getId());                        
                    }

                    ((ProgramRequirementsManageView)parentController.getView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE))
                                                                        .setRuleTree(manageStatement, stmtTypeInfo.getId(), true);
                    parentController.showView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE);
            }
        });

        rulePreviewWidget.addSubRuleEditCallback(editRuleCallback);
    }

    private void displaySaveButton() {
        ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.SaveContinueCancelEnum.SAVE_CONTINUE, ButtonEnumerations.SaveContinueCancelEnum.CANCEL);
        actionCancelButtons.addStyleName("KS-Program-Requirements-Preview-SaveContinue");
        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
                if (result == ButtonEnumerations.SaveContinueCancelEnum.CANCEL) {
                    //TODO remove NL from tree because it is not current
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

                    //add new statement to the rule because even if user cancel on rule manage screen, we want to have at least one statement present
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
                                                                            .setRuleTree(stmtTreeInfo, stmtTreeInfo.getType(), false);
            parentController.showView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE);
        }
    };

    private boolean isEmptyRule(StatementTreeViewInfo tree) {
        return (tree.getStatements() == null || tree.getStatements().isEmpty() && (tree.getReqComponents() == null || tree.getReqComponents().isEmpty()));
    }

    private String getWordPlural(String word) {
        return (word.endsWith("s") ? word : word + "s");
    }
}