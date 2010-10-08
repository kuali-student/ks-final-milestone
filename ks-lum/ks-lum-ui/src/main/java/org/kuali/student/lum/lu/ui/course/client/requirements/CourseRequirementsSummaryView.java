package org.kuali.student.lum.lu.ui.course.client.requirements;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ActionCancelGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.rules.SubrulePreviewWidget;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.client.rpc.ProgramRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;

public class CourseRequirementsSummaryView extends VerticalSectionView {

    private final ProgramRpcServiceAsync programRemoteService = GWT.create(ProgramRpcService.class);

    //view's widgets
    private FlowPanel layout = new FlowPanel();
    private ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.SaveCancelEnum.SAVE, ButtonEnumerations.SaveCancelEnum.CANCEL);

    //view's data
    private CourseRequirementsViewController parentController;
    private CourseRequirementsDataModel rules;
    private boolean isReadOnly;
    private static int tempProgReqInfoID = 9999;
    private static final String NEW_STMT_TREE_ID = "NEWSTMTTREE";

    private Map<String, SpanPanel> perCourseRequisiteTypePanel = new HashMap<String, SpanPanel>();

    public CourseRequirementsSummaryView(final CourseRequirementsViewController parentController, Enum<?> viewEnum, String name,
                                                            String modelId, CourseRequirementsDataModel rulesData, boolean isReadOnly) {
        super(viewEnum, name, modelId);
        this.parentController = parentController;
        rules = rulesData;
        rules.setInitialized(false);
        this.isReadOnly = isReadOnly;
        setupButtons();
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
                
				//return if user did not added or updated a rule
                if (!manageView.isDirty() || !manageView.isUserClickedSaveButton()) {
                    onReadyCallback.exec(true);
                    return;
                }

                //update the rule because user added or edited the rule
                ((SectionView)parentController.getCurrentView()).setIsDirty(false);
                manageView.setUserClickedSaveButton(false);

                //if rule storage updated successfully, update the display as well
                Map<StatementTypeInfo, StatementTreeViewInfo> affectedRule = rules.updateRules(manageView.getRuleTree(), null, manageView.isNewRule());
                if (affectedRule != null) {
                    StatementTypeInfo affectedStatementTypeInfo = new StatementTypeInfo();
                    for (final StatementTypeInfo stmtTypeInfo : affectedRule.keySet()) {
                        affectedStatementTypeInfo = stmtTypeInfo;
                    }
                    SpanPanel reqPanel = perCourseRequisiteTypePanel.get(affectedStatementTypeInfo.getId());
                    SubrulePreviewWidget rulePreviewWidget = (SubrulePreviewWidget)reqPanel.getWidget(0);
                    SubrulePreviewWidget newRulePreviewWidget = getUpdatedProgramRequirement(reqPanel, affectedStatementTypeInfo, affectedRule.get(affectedStatementTypeInfo));
                    reqPanel.insert(newRulePreviewWidget, 0);
                    reqPanel.remove(rulePreviewWidget);
                }

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
                rules.updateModelFromLocalData(((DataModel)model).getRoot());
            }
        });
    }

    public void displayRules() {
        remove(layout);
        layout.clear();

        //display 'Course Requirements' page title (don't add if read only because the section itself will display the title)
        if (!isReadOnly) {
            SectionTitle pageTitle = SectionTitle.generateH2Title("Course Requisites");
            pageTitle.setStyleName("KS-Course-Requisites-Section-header");  //make the header orange
            layout.add(pageTitle);
        }

        //iterate and display rules for each Course Requirement type e.g. Pre-Requisites, Co-Requisites, Anti-Requisites
        for (StatementTypeInfo stmtTypeInfo : rules.getStoredStatementTypes()) {

            //Show only headers for top statement types
            if (isTopStatement(stmtTypeInfo)) {
                SectionTitle title = SectionTitle.generateH3Title(stmtTypeInfo.getName());
                title.addStyleName("KS-Course-Requisites-Top-Stmt-Header");
                layout.add(title);
                continue;
            }

            //create and display one type of Course Requisites section
            SpanPanel requirementsPanel = new SpanPanel();
            perCourseRequisiteTypePanel.put(stmtTypeInfo.getId(), requirementsPanel);
            displayRequirementSectionForGivenType(requirementsPanel, stmtTypeInfo);

            //now display each requirement for this Course Requisites type
            for (StatementTreeViewInfo ruleInfo : rules.getStoredProgRequirements(stmtTypeInfo)) {
                addCourseRequisite(requirementsPanel, stmtTypeInfo, ruleInfo, CourseRequirementsDataModel.requirementState.STORED);
            }
        }

        //save and cancel buttons
        layout.add(actionCancelButtons);

        addWidget(layout);
    }

    private void displayRequirementSectionForGivenType(final SpanPanel requirementsPanel, final StatementTypeInfo stmtTypeInfo) {

        //display header for this Course Requisites type e.g. Enrollment Eligibility
        SectionTitle title = SectionTitle.generateH3Title(stmtTypeInfo.getName());
        title.setStyleName("KS-Course-Requisites-Preview-Rule-Type-Header");
        layout.add(title);

        //add rule description
        KSLabel ruleTypeDesc = new KSLabel(stmtTypeInfo.getDescr());
        ruleTypeDesc.addStyleName("KS-Course-Requisites-Preview-Rule-Type-Desc");
        layout.add(ruleTypeDesc);

        //display "Add Rule" button if user is in 'edit' mode
        if (!isReadOnly) {
            KSButton addCourseReqButton = new KSButton("Add " + stmtTypeInfo.getName(), KSButtonAbstract.ButtonStyle.FORM_SMALL);
             addCourseReqButton.addClickHandler(new ClickHandler(){
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
            layout.add( addCourseReqButton);
        }

        layout.add(requirementsPanel);
    }

    private void addCourseRequisite(final SpanPanel requirementsPanel, final StatementTypeInfo stmtTypeInfo, final StatementTreeViewInfo rule,
                                       CourseRequirementsDataModel.requirementState ruleInitialState) {
        //first add new Course Requisites into the map of rules
        rules.getStoredProgReqsAndStates(stmtTypeInfo).put(rule, ruleInitialState);
        final SubrulePreviewWidget rulePreviewWidget = new SubrulePreviewWidget(rule, isReadOnly);

        addRulePreviewWidgetHandlers(requirementsPanel, rulePreviewWidget, stmtTypeInfo, rule);

        requirementsPanel.add(rulePreviewWidget);
    }

    private void addRulePreviewWidgetHandlers(final SpanPanel requirementsPanel, final SubrulePreviewWidget subRuleWidget, final StatementTypeInfo stmtTypeInfo,
                                              final StatementTreeViewInfo rule) {

        subRuleWidget.addDeleteButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                final ConfirmationDialog dialog = new ConfirmationDialog("Delete Rule", "Are you sure you want to delete this rule?");
                dialog.getConfirmButton().addClickHandler(new ClickHandler(){
                    @Override
                    public void onClick(ClickEvent event) {
                        final LinkedHashMap<StatementTreeViewInfo, CourseRequirementsDataModel.requirementState> rulesPerType = rules.getStoredProgReqsAndStates(stmtTypeInfo);
                        if (rulesPerType.get(rule) == CourseRequirementsDataModel.requirementState.ADDED) {
                            //we completely remove a rule that was added and then deleted without any save between
                            rulesPerType.remove(rule);
                        } else {
                            rulesPerType.put(rule, CourseRequirementsDataModel.requirementState.DELETED);  //overwrite previous state
                        }

                        //remove rule from display
                        requirementsPanel.remove(subRuleWidget);

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
    
    private SubrulePreviewWidget getUpdatedProgramRequirement(final SpanPanel requirementsPanel, final StatementTypeInfo stmtTypeInfo, final StatementTreeViewInfo rule) {

        final SubrulePreviewWidget rulePreviewWidget = new SubrulePreviewWidget(rule, isReadOnly);
        addRulePreviewWidgetHandlers(requirementsPanel, rulePreviewWidget, stmtTypeInfo, rule);
        return rulePreviewWidget;
    }

    private void setupButtons() {
        actionCancelButtons.addStyleName("KS-Course-Requisites-Save-Button");
        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
               updateModel();
            }
        });
    }

    static public boolean isTopStatement(StatementTypeInfo stmtInfo) {
        return ((stmtInfo.getAllowedStatementTypes() != null) && !stmtInfo.getAllowedStatementTypes().isEmpty());
    }

    static public String generateStatementTreeId() {
        return (NEW_STMT_TREE_ID + Integer.toString(tempProgReqInfoID++));
    }

    public void setRules(CourseRequirementsDataModel rules) {
        this.rules = rules;
        this.rules.setInitialized(false);
    }
}