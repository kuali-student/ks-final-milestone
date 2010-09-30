package org.kuali.student.lum.lu.ui.course.client.requirements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.rules.SubrulePreviewWidget;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsDataModel;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.client.rpc.ProgramRpcServiceAsync;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;

public class CourseRequirementsSummaryView extends VerticalSectionView {

    private final ProgramRpcServiceAsync programRemoteService = GWT.create(ProgramRpcService.class);

    //view's widgets
    private FlowPanel layout = new FlowPanel();
    private Map<String, KSLabel> noRuleTextMap = new HashMap<String, KSLabel>();

    //view's data
    private CourseRequirementsViewController parentController;
    private CourseRequirementsDataModel rules;
    private boolean isReadOnly;
    private boolean isRulesDisplayed = false;
    private static int tempProgReqInfoID = 9999;
    private static final String NEW_PROG_REQ_ID = "NEWPROGREQ";
    private static final String NEW_STMT_TREE_ID = "NEWSTMTTREE";

    private Map<String, SpanPanel> perProgramRequirementTypePanel = new HashMap<String, SpanPanel>();

    public CourseRequirementsSummaryView(final CourseRequirementsViewController parentController, Enum<?> viewEnum, String name,
                                                            String modelId, CourseRequirementsDataModel rulesData, boolean isReadOnly) {
        super(viewEnum, name, modelId);
        this.parentController = parentController;
        rules = rulesData;
        rules.setInitialized(false);
        this.isReadOnly = isReadOnly;
    }

    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {

        //only when user wants to see rules then load requirements from database if they haven't been loaded yet
        if (!rules.isInitialized()) {
            rules.retrieveCourseRequirements(new Callback<Boolean>() {
                @Override
                public void exec(Boolean result) {
                    if (result) {
                        displayRules();
                    }
                    onReadyCallback.exec(result);
                }
            });
            return;
        }

    //    if (!isRulesDisplayed) {
    //        displayRules();
    //    }

        //for read-only view, we don't need to worry about rules being added or modified
        if (isReadOnly) {
            displayRules();            
            onReadyCallback.exec(true);
            return;
        }

        //return if user did not added or updated a rule
        parentController.getView(CourseRequirementsViewController.CourseRequirementsViews.MANAGE, new Callback<View>(){
			@Override
			public void exec(View result) {
				CourseRequirementsManageView manageView = (CourseRequirementsManageView) result;        

                if (!manageView.isDirty() || !manageView.isUserClickedSaveButton()) {
                    onReadyCallback.exec(true);
                    return;
                }

                //update the rule because user added or edited the rule
                ((SectionView)parentController.getCurrentView()).setIsDirty(false);
                manageView.setUserClickedSaveButton(false);

                final StatementTreeViewInfo newTree = manageView.getRuleTree();

                //find the affected Course Requisites
            /* TODO
                LinkedHashMap<ProgramRequirementInfo, ProgramRequirementsDataModel.requirementState> reqInfo = null;
                CourseRequirementInfo affectedProgramRequirement = null;
                StatementTypeInfo affectedStatementTypeInfo = null;
                boolean progReqFound = false;
                for (StatementTypeInfo statementTypeInfo : rules.getStoredStatementTypes()) {
                    for (ProgramRequirementInfo progReqInfo : rules.getStoredProgRequirements(statementTypeInfo)) {
                        String originalProgramReqId = manageView.getRelatedCourseReqInfoId();
                        if (manageView.isNewRule()) {
                            if (!progReqInfo.getId().equals(originalProgramReqId)) {
                                continue;
                            }
                        } else {
                            if (!findStatementBasedOnID(newTree.getId(), progReqInfo.getStatement())) {
                                continue;
                            }
                        }

                        reqInfo = rules.getStoredProgReqsAndStates(statementTypeInfo);
                        affectedProgramRequirement = progReqInfo;
                        affectedStatementTypeInfo = statementTypeInfo;
                        progReqFound = true;
                        break;
                    }

                    if (progReqFound) {
                        break;
                    }
                }

                if (reqInfo == null) {
                    Window.alert("Cannot find program requirement with a statement that has id: '" + newTree.getId() + "'");
                    GWT.log("Cannot find program requirement with a statement that has id: '" + newTree.getId() + "'", null);
                    onReadyCallback.exec(true);
                    return;
                }

                if (reqInfo.get(affectedProgramRequirement) == CourseRequirementsDataModel.requirementState.STORED) {
                    reqInfo.put(affectedProgramRequirement, CourseRequirementsDataModel.requirementState.EDITED);
                }

                //if we don't have top level req. components wrapped in statement, do so before we add another statement
                StatementTreeViewInfo affectedRule = affectedProgramRequirement.getStatement();
                if ((affectedRule.getReqComponents() != null) && !affectedRule.getReqComponents().isEmpty()) {
                    StatementTreeViewInfo stmtTree = new StatementTreeViewInfo();
                    stmtTree.setId(generateStatementTreeId());
                    stmtTree.setType(affectedStatementTypeInfo.getId());
                    stmtTree.setReqComponents(affectedRule.getReqComponents());
                    List<StatementTreeViewInfo> stmtList = new ArrayList<StatementTreeViewInfo>();
                    stmtList.add(stmtTree);
                    affectedRule.setStatements(stmtList);
                }

                List<StatementTreeViewInfo> affectedStatements = affectedRule.getStatements();
                if (manageView.isNewRule()) {
                    affectedStatements.add(newTree);
                } else {
                    //update rule
                    if (affectedStatements == null || affectedStatements.isEmpty()) {
                        affectedProgramRequirement.setStatement(newTree);
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
                    if (compareStatementTrees(affectedProgramRequirement.getStatement(), rulePreviewWidget.getStatementTreeViewInfo())) {
                            RulePreviewWidget newRulePreviewWidget = getUpdatedProgramRequirement(reqPanel, affectedStatementTypeInfo, affectedProgramRequirement);
                            reqPanel.insert(newRulePreviewWidget, i);
                            reqPanel.remove(rulePreviewWidget);
                    }
                }
                */
                onReadyCallback.exec(true);
            }
		});
    }

    @Override
    public void updateModel() {
        parentController.requestModel(new ModelRequestCallback() {
            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert(cause.getMessage());
                GWT.log("Unable to retrieve model for course requirements view", cause);
            }
            @Override
            public void onModelReady(Model model) {
                updateModelFromLocalData(((DataModel)model).getRoot());
            }
        });
    }

    private void updateModelFromLocalData(final Object dto) {
        for (final StatementTypeInfo stmtTypeInfo : rules.getStoredStatementTypes()) {
            for (final StatementTreeViewInfo rule : rules.getStoredProgRequirements(stmtTypeInfo)) {
                final CourseRequirementsDataModel.requirementState ruleState = rules.getStoredProgReqsAndStates(stmtTypeInfo).get(rule);
            /* TODO                
                switch (ruleState) {
                    case STORED:
                        //rule was not changed so continue
                        break;
                    case ADDED:
                        programRemoteService.createProgramRequirement(rule, new KSAsyncCallback<ProgramRequirementInfo>() {
                            @Override
                            public void handleFailure(Throwable caught) {
                                Window.alert(caught.getMessage());
                                GWT.log("createProgramRequirement failed", caught);
                            }
                            @Override
                            public void onSuccess(ProgramRequirementInfo programReqInfo) {
                     //           updateProgReqId(dto, programReqInfo.getId(), ruleState);
                     //           rules.getStoredProgReqsAndStates(stmtTypeInfo).put(programReqInfo, ProgramRequirementsDataModel.requirementState.STORED);
                            }
                        });
                        break;
                    case EDITED:
                        programRemoteService.updateProgramRequirement(rule, new KSAsyncCallback<ProgramRequirementInfo>() {
                            @Override
                            public void handleFailure(Throwable caught) {
                                Window.alert(caught.getMessage());
                                GWT.log("updateProgramRequirement failed", caught);
                            }
                            @Override
                            public void onSuccess(ProgramRequirementInfo programReqInfo) {
                     //           updateProgReqId(dto, programReqInfo.getId(), ruleState);
                     //           rules.getStoredProgReqsAndStates(stmtTypeInfo).put(programReqInfo, ProgramRequirementsDataModel.requirementState.STORED);
                            }
                        });
                        break;
                    case DELETED:
                        programRemoteService.deleteProgramRequirement(rule.getId(), new KSAsyncCallback<StatusInfo>() {
                            @Override
                            public void handleFailure(Throwable caught) {
                                Window.alert(caught.getMessage());
                                GWT.log("deleteProgramRequirement failed", caught);
                            }
                            @Override
                            public void onSuccess(StatusInfo statusInfo) {
                     //           updateProgReqId(dto, rule.getId(), ruleState);
                                rules.getStoredProgRequirements(stmtTypeInfo).remove(rule);
                            }
                        });
                        break;
                    default:
                        break;
                }          */
            }
        }        
    }

    //now update the program this requirement belongs to    
    private void updateProgReqId(Object dto, String progReqId, ProgramRequirementsDataModel.requirementState op) {
        if (dto instanceof MajorDisciplineInfo) {
            MajorDisciplineInfo mdInfo = (MajorDisciplineInfo) dto;
            //mdInfo.getProgramRequirements().add(progReqId);
            updateProgramInfo(mdInfo.getProgramRequirements(), progReqId, op);
        } else {
            Window.alert("Only persistence of MajorDiscipline is currently implemented");
            GWT.log("Unable to retrieve model for course requirements view", null);
        }
    }

    private void updateProgramInfo(List<String> requirements, String id, ProgramRequirementsDataModel.requirementState op) {
        switch (op) {
            case ADDED:
                requirements.add(id);
                break;
            case DELETED:
                requirements.remove(id);
                break;
            default:
                break;
        }
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
        if (id.equals(tree.getId())) {
            return true;
        }
        for (StatementTreeViewInfo oneTree : tree.getStatements()) {
            if (id.equals(oneTree.getId())) {
                found = true;
                break;
            }
        }
        return found;
    }

    public void displayRules() {
        isRulesDisplayed = true;        
        remove(layout);
        layout.clear();

        //display 'Course Requirements' page title (don't add if read only because the section itself will display the title)
        if (!isReadOnly) {
            SectionTitle pageTitle = SectionTitle.generateH2Title("Course Requisites");
            pageTitle.setStyleName("KS-Program-Requirements-Section-header");  //make the header orange
            layout.add(pageTitle);
        }
        
        //iterate and display rules for each Course Requirement type e.g. Entrance Requirements, Completion Requirements
        Boolean firstRequirement = true;
        for (StatementTypeInfo stmtTypeInfo : rules.getStoredStatementTypes()) {

            //create and display one type of Course Requisites section
            SpanPanel requirementsPanel = new SpanPanel();
            perProgramRequirementTypePanel.put(stmtTypeInfo.getId(), requirementsPanel);
            displayRequirementSectionForGivenType(requirementsPanel, stmtTypeInfo, firstRequirement);
            firstRequirement = false;

            //now display each requirement for this Course Requisites type
            for (StatementTreeViewInfo ruleInfo : rules.getStoredProgRequirements(stmtTypeInfo)) {
                addCourseRequisite(requirementsPanel, stmtTypeInfo, ruleInfo, ProgramRequirementsDataModel.requirementState.STORED);
            }
        }

        addWidget(layout);
    }

    private void displayRequirementSectionForGivenType(final SpanPanel requirementsPanel, final StatementTypeInfo stmtTypeInfo, boolean firstRequirement) {

        //display header for this Course Requisites type e.g. Entrance Requirements; make the header plural
        SectionTitle title = SectionTitle.generateH3Title(getWordPlural(stmtTypeInfo.getName()));
        title.setStyleName((firstRequirement ? "KS-Program-Requirements-Preview-Rule-Type-First-Header" : "KS-Program-Requirements-Preview-Rule-Type-Header"));  //make the header orange
        layout.add(title);

        //display "Add Rule" button if user is in 'edit' mode
        if (!isReadOnly) {
            KSButton addProgramReqBtn = new KSButton("Add " + stmtTypeInfo.getName(), KSButtonAbstract.ButtonStyle.FORM_SMALL);
            addProgramReqBtn.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                        final StatementTreeViewInfo newRule = new StatementTreeViewInfo();
                        newRule.setId(generateStatementTreeId());
                        newRule.setType(stmtTypeInfo.getId());
                        RichTextInfo text = new RichTextInfo();
                        text.setPlain("");
                        newRule.setDesc(text);
                        parentController.getView(CourseRequirementsViewController.CourseRequirementsViews.MANAGE, new Callback<View>(){
                            @Override
                            public void exec(View result) {
                                ((CourseRequirementsManageView) result).setRuleTree(newRule, true, newRule.getId());
                                parentController.showView(CourseRequirementsViewController.CourseRequirementsViews.MANAGE);
                            }
                        });
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

    private void addCourseRequisite(final SpanPanel requirementsPanel, final StatementTypeInfo stmtTypeInfo, final StatementTreeViewInfo rule,
                                       ProgramRequirementsDataModel.requirementState ruleInitialState) {
        //first add new Course Requisites into the map of rules
      //  rules.getStoredProgReqsAndStates(stmtTypeInfo).put(progReqInfo, ruleInitialState);
        final SubrulePreviewWidget rulePreviewWidget = new SubrulePreviewWidget(rule, isReadOnly);

        addRulePreviewWidgetHandlers(requirementsPanel, rulePreviewWidget, stmtTypeInfo, rule);

        requirementsPanel.add(rulePreviewWidget);
    }


    private void addRulePreviewWidgetHandlers(final SpanPanel requirementsPanel, final SubrulePreviewWidget subRuleWidget, final StatementTypeInfo stmtTypeInfo,
                                              final StatementTreeViewInfo rule) {

        subRuleWidget.addDeleteButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                final ConfirmationDialog dialog = new ConfirmationDialog(
                        ProgramProperties.get().programRequirements_summaryViewPageDeleteRuleDialogTitle(),
                        ProgramProperties.get().programRequirements_summaryViewPageDeleteRuleDialogMsg());

                dialog.getConfirmButton().addClickHandler(new ClickHandler(){
                    @Override
                    public void onClick(ClickEvent event) {
                        /* TODO
                        if (rulesPerType.get(progReqInfo) == ProgramRequirementsDataModel.requirementState.ADDED) {
                            //we completely remove a rule that was added and then deleted without any save between
                            rulesPerType.remove(progReqInfo);
                        } else {
                            rulesPerType.put(progReqInfo, ProgramRequirementsDataModel.requirementState.DELETED);  //overwrite previous state
                        } */

                        //remove rule from display
                        requirementsPanel.remove(subRuleWidget);
                        setupNoRuleText(stmtTypeInfo);

                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });

        subRuleWidget.addEditButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                parentController.getView(CourseRequirementsViewController.CourseRequirementsViews.MANAGE, new Callback<View>(){
                    @Override
                    public void exec(View result) {
                        ((CourseRequirementsManageView) result).setRuleTree(rule, false, rule.getId());
                        parentController.showView(CourseRequirementsViewController.CourseRequirementsViews.MANAGE);
                    }
                });                
            }
        });
    }

    private void setupNoRuleText(StatementTypeInfo stmtTypeInfo) {
        noRuleTextMap.get(stmtTypeInfo.getName()).setVisible(rules.isRuleExists(stmtTypeInfo));
    }

    private boolean isEmptyRule(StatementTreeViewInfo tree) {
        return (tree.getStatements() == null || tree.getStatements().isEmpty() && (tree.getReqComponents() == null || tree.getReqComponents().isEmpty()));
    }

    private String getWordPlural(String word) {
        return (word.endsWith("s") ? word : word + "s");
    }

    private String generateStatementTreeId() {
        return (NEW_STMT_TREE_ID + Integer.toString(tempProgReqInfoID++));
    }
}