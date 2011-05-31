package org.kuali.student.lum.lu.ui.course.client.requirements;

import java.util.*;

import org.kuali.student.common.dto.RichTextInfo;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ActionCancelGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton;
import org.kuali.student.common.ui.client.widgets.field.layout.element.LabelPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton.AbbrButtonType;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.ui.client.widgets.rules.RulesUtil;
import org.kuali.student.core.statement.ui.client.widgets.rules.SubrulePreviewWidget;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.common.client.widgets.CluSetDetailsWidget;
import org.kuali.student.lum.common.client.widgets.CluSetRetriever;
import org.kuali.student.lum.common.client.widgets.CluSetRetrieverImpl;
import org.kuali.student.lum.lu.ui.course.client.configuration.AbstractCourseConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CourseRequirementsSummaryView extends VerticalSectionView {

    private static CluSetRetriever cluSetRetriever = new CluSetRetrieverImpl();

    //view's widgets
    private FlowPanel layout = new FlowPanel();
    private ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.SaveContinueCancelEnum.SAVE_CONTINUE, ButtonEnumerations.SaveContinueCancelEnum.CANCEL);
    private Map<String, Widget> addButtonsList = new HashMap<String, Widget>();

    //view's data
    private CourseRequirementsViewController parentController;
    private CourseRequirementsDataModel rules;
    private boolean isReadOnly;
    private boolean showSaveButtons;
    private static int tempProgReqInfoID = 9999;
    public static final String NEW_STMT_TREE_ID = "NEWSTMTTREE";
    public static final String NEW_REQ_COMP_ID = "NEWREQCOMP";    

    private Map<String, SpanPanel> perCourseRequisiteTypePanel = new LinkedHashMap<String, SpanPanel>();

    public CourseRequirementsSummaryView(final CourseRequirementsViewController parentController, Enum<?> viewEnum, String name,
                                                            String modelId, CourseRequirementsDataModel rulesData, boolean isReadOnly, boolean showSaveButtons) {
        super(viewEnum, name, modelId);
        this.parentController = parentController;
        rules = rulesData;
        rules.setInitialized(false);
        this.isReadOnly = isReadOnly;
        this.showSaveButtons = showSaveButtons;
        if (!isReadOnly && showSaveButtons) {
            setupSaveCancelButtons();
        }
    }

    @Override
    public boolean isDirty() {
        return rules.isDirty();
    }

    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {

        //only when user wants to see rules then load requirements from database if they haven't been loaded yet
        if (!rules.isInitialized()) {
            rules.retrieveCourseRequirements(AbstractCourseConfigurer.COURSE_PROPOSAL_MODEL, new Callback<Boolean>() {
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

        //see if we need to update a rule if user is returning from rule manage screen
        parentController.getView(CourseRequirementsViewController.CourseRequirementsViews.MANAGE, new Callback<View>(){
			@Override
			public void exec(View result) {
				CourseRequirementsManageView manageView = (CourseRequirementsManageView) result;
                
				//return if user did not added or updated a rule
                if (!manageView.isDirty() || !manageView.isUserClickedSaveButton()) {
                    rules.removeEmptyRules();
                    onReadyCallback.exec(true);
                    return;
                }

                //update the rule because user added or edited the rule
                ((SectionView)parentController.getCurrentView()).setIsDirty(false);
                manageView.setUserClickedSaveButton(false);

                //if rule storage updated successfully, update the display as well
                StatementTreeViewInfo affectedRule = rules.updateRules(manageView.getRuleTree(), manageView.getInternalCourseReqID(), manageView.isNewRule());
                updateRequirementWidgets(affectedRule);
                
		        onReadyCallback.exec(true);
			}
		});        
    }

    public void revertRuleChanges() {
        rules.revertRuleChanges();
        displayRules();
    }

    private void updateRequirementWidgets(StatementTreeViewInfo rule) {
        if (rule != null) {
            StatementTypeInfo affectedStatementTypeInfo = rules.getStmtTypeInfo(rule.getType());
            SpanPanel reqPanel = perCourseRequisiteTypePanel.get(affectedStatementTypeInfo.getId());

            //don't show 'add rule' button if we have one rule already
            addButtonsList.get(rule.getType()).setVisible(false);

            //if this is a new rule then add it to the panel
            if (reqPanel.getWidgetCount() == 0) {
                SubrulePreviewWidget newRulePreviewWidget = addCourseRequisite(reqPanel, rule);
                reqPanel.add(newRulePreviewWidget);
                return;
            }

            for (int i = 0; i < reqPanel.getWidgetCount(); i++) {
                SubrulePreviewWidget subrulePreviewWidget = (SubrulePreviewWidget)reqPanel.getWidget(i);
                SubrulePreviewWidget newRulePreviewWidget = addCourseRequisite(reqPanel, rule);
                reqPanel.insert(newRulePreviewWidget, i);
                reqPanel.remove(subrulePreviewWidget);
                break; //there should be only one rule per requisite type
            }
        }
    }    

    public void displayRules() {       
        remove(layout);
        layout.clear();

        //display 'Course Requirements' page title (don't add if read only because the section itself will display the title)
        if (!isReadOnly) {
            SectionTitle pageTitle = SectionTitle.generateH2Title("Course Requisites");
            pageTitle.addStyleName("ks-layout-header");  //make the header orange
            layout.add(pageTitle);
        }

        //iterate and display rules for each Course Requirement type e.g. Pre-Requisites, Co-Requisites, Anti-Requisites
        boolean firstSubHeader = true;
        for (StatementTypeInfo stmtTypeInfo : rules.getStmtTypes()) {

            //Show only headers for top statement types
            if (isTopStatement(stmtTypeInfo)) {
                SectionTitle title = SectionTitle.generateH3Title(stmtTypeInfo.getName());
                title.addStyleName("KS-Course-Requisites-Top-Stmt-Header");
                layout.add(title);
                firstSubHeader = true;
                continue;
            }

            //create and display one type of Course Requisites section
            SpanPanel requirementsPanel = new SpanPanel();
            perCourseRequisiteTypePanel.put(stmtTypeInfo.getId(), requirementsPanel);
            displayRequirementSectionForGivenType(requirementsPanel, stmtTypeInfo, firstSubHeader);
            firstSubHeader = false;

            //now display each requirement for this Course Requisites type; should be only one for courses
            for (StatementTreeViewInfo ruleInfo : rules.getCourseReqInfo(stmtTypeInfo.getId())) {
                SubrulePreviewWidget subrulePreviewWidget = addCourseRequisite(requirementsPanel, ruleInfo);
                requirementsPanel.add(subrulePreviewWidget);
            }
        }

        //save and cancel buttons
        if (!isReadOnly && showSaveButtons) {
            layout.add(actionCancelButtons);
        }

        addWidget(layout);
    }

    private void displayRequirementSectionForGivenType(final SpanPanel requirementsPanel, final StatementTypeInfo stmtTypeInfo, boolean firstSubHeader) {

        //display header for this Course Requisites type e.g. Enrollment Eligibility
        SectionTitle title = SectionTitle.generateH3Title(stmtTypeInfo.getName());
        title.setStyleName((firstSubHeader ? "KS-Course-Requisites-Preview-Rule-Type-First-Header" : "KS-Course-Requisites-Preview-Rule-Type-Header"));
        layout.add(title);

        LabelPanel labelExamples = new LabelPanel(stmtTypeInfo.getDescr());
        labelExamples.getElement().setAttribute("style", "font-weight: normal; width: 80%;");
        
        String examplesHtml = Application.getApplicationContext().getMessage(stmtTypeInfo.getId());
        
        if (examplesHtml != null && !examplesHtml.equals("")) {
        	AbbrButton examples = new AbbrButton(AbbrButtonType.EXAMPLES);
        	examples.setVisible(true);
        	examples.setHoverHTML(examplesHtml);
        	examples.getHoverPopup().addStyleName("ks-example-popup");
        	labelExamples.add(examples);
        }
        
        HorizontalPanel spacer0 = new HorizontalPanel();
        spacer0.addStyleName("KS-Course-Requisites-Button-Spacer");
        labelExamples.add(spacer0);
 
        layout.add(labelExamples);
        
        //display "Add Rule" button if user is in 'edit' mode OR a rule is already defined
        final String stmtId = stmtTypeInfo.getId();
        if (!isReadOnly) { // && rules.getCourseReqInfo(stmtId).isEmpty()) {
            KSButton addCourseReqButton = new KSButton("Add " + stmtTypeInfo.getName(), KSButtonAbstract.ButtonStyle.FORM_SMALL);
            addCourseReqButton.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {

                    storeRules(false, new Callback<Boolean>() {
                        @Override
                        public void exec(Boolean courseExists) {
                            if (courseExists) {
                                final StatementTreeViewInfo newRule = new StatementTreeViewInfo();
                                newRule.setId(generateStatementTreeId());
                                newRule.setType(stmtId);
                                RichTextInfo text = new RichTextInfo();
                                text.setPlain("");
                                newRule.setDesc(text);
                                parentController.getView(CourseRequirementsViewController.CourseRequirementsViews.MANAGE, new Callback<View>(){
                                    @Override
                                    public void exec(View result) {
                                        rules.addRule(newRule);
                                        ((CourseRequirementsManageView) result).setRuleTree(newRule, true, rules.getInternalCourseReqID(newRule));
                                        parentController.showView(CourseRequirementsViewController.CourseRequirementsViews.MANAGE);
                                    }
                                });
                            }
                        }
                    });
                };
            });
            addCourseReqButton.setVisible(rules.getCourseReqInfo(stmtId).isEmpty());
            addButtonsList.put(stmtId, addCourseReqButton);
            layout.add(addCourseReqButton);
            VerticalPanel spacer = new VerticalPanel();
            spacer.addStyleName("KS-Course-Requisites-Button-Spacer");            
            layout.add(spacer);
        }

        layout.add(requirementsPanel);
    }

    private SubrulePreviewWidget addCourseRequisite(final SpanPanel requirementsPanel,final StatementTreeViewInfo rule) {

        Integer internalProgReqID =  rules.getInternalCourseReqID(rule);
        String stmtTypeId = rule.getType();
        
        final SubrulePreviewWidget rulePreviewWidget = new SubrulePreviewWidget(rule, isReadOnly, getCluSetWidgetList(rule));

        addRulePreviewWidgetHandlers(requirementsPanel, rulePreviewWidget, stmtTypeId, internalProgReqID);
        return rulePreviewWidget;
    }

    private void addRulePreviewWidgetHandlers(final SpanPanel requirementsPanel, final SubrulePreviewWidget subRuleWidget, final String stmtTypeId, final Integer internalProgReqID) {

        subRuleWidget.addEditButtonClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                parentController.getView(CourseRequirementsViewController.CourseRequirementsViews.MANAGE, new Callback<View>(){
                    @Override
                    public void exec(View result) {
                        ((CourseRequirementsManageView) result).setRuleTree(rules.getRule(internalProgReqID), false, internalProgReqID);
                        parentController.showView(CourseRequirementsViewController.CourseRequirementsViews.MANAGE);
                    }
                });                
            }
        });
		
        subRuleWidget.addDeleteButtonClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //deleting subrule does not delete the requirement (rule) itself
                rules.markRuleAsEdited(internalProgReqID);
                //remove rule from display and storage
                rules.deleteRule(internalProgReqID);
                requirementsPanel.remove(subRuleWidget);
                addButtonsList.get(stmtTypeId).setVisible(true);
            }
        });		   
    }

    static public boolean isTopStatement(StatementTypeInfo stmtInfo) {
        return ((stmtInfo.getAllowedStatementTypes() != null) && !stmtInfo.getAllowedStatementTypes().isEmpty());
    }

    static public Map<String, Widget> getCluSetWidgetList(StatementTreeViewInfo rule) {
        Map<String, Widget> widgetList = new HashMap<String, Widget>();
        Set<String> cluSetIds = new HashSet<String>();
        findCluSetIds(rule, cluSetIds);
        for (String clusetId : cluSetIds) {
            widgetList.put(clusetId, new CluSetDetailsWidget(clusetId, cluSetRetriever));
        }

        return widgetList;
    }

    static private void findCluSetIds(StatementTreeViewInfo rule, Set<String> list) {

        List<StatementTreeViewInfo> statements = rule.getStatements();
        List<ReqComponentInfo> reqComponentInfos = rule.getReqComponents();

        if ((statements != null) && (statements.size() > 0)) {
            // retrieve all statements
            for (StatementTreeViewInfo statement : statements) {
                findCluSetIds(statement, list); // inside set the children of this statementTreeViewInfo
            }
        } else if ((reqComponentInfos != null) && (reqComponentInfos.size() > 0)) {
            // retrieve all req. component LEAFS
            for (ReqComponentInfo reqComponent : reqComponentInfos) {
                List<ReqCompFieldInfo> fieldInfos = reqComponent.getReqCompFields();
                for (ReqCompFieldInfo fieldInfo : fieldInfos) {
                    if (RulesUtil.isCluSetWidget(fieldInfo.getType())) {
                        list.add(fieldInfo.getValue());
                    }
                }
            }
        }
    }

    private void setupSaveCancelButtons() {
        actionCancelButtons.addStyleName("KS-Course-Requisites-Save-Button");
        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
                if (result == ButtonEnumerations.SaveContinueCancelEnum.SAVE_CONTINUE) {
                    storeRules(true, new Callback<Boolean>() {
                        @Override
                        public void exec(Boolean result) {
                            if (result) {
                                CourseProposalController courseController = ((CourseProposalController) parentController.getParentController());
                                courseController.showNextViewOnMenu();
                                courseController.getReqDataModel().retrieveStatementTypes(courseController.getCourseId(), Controller.NO_OP_CALLBACK);
                            }
                        }
                    });                    
                } else {
                    if(! ((CourseProposalController)parentController.getController()).isNew()){
                        (parentController.getController()).showView(CourseProposalConfigurer.CourseSections.SUMMARY);
                    }
                    else{
                        Application.navigate(AppLocations.Locations.CURRICULUM_MANAGEMENT.getLocation());
                    }
                }
            }
        });
    }

    public void storeRules(final boolean storeRules, final Callback<Boolean> callback) {
        parentController.requestModel(CourseRequirementsViewController.COURSE_PROPOSAL_MODEL, new ModelRequestCallback() {
            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert(cause.getMessage());
                GWT.log("Unable to retrieve model for course requirements view", cause);
                callback.exec(false);
            }
            @Override
            public void onModelReady(Model model) {
                String courseId = ((DataModel)model).getRoot().get("id");
                String courseState = ((DataModel)model).getRoot().get("state");
                if (courseId == null) {
                    final ConfirmationDialog dialog = new ConfirmationDialog("Submit Course Title", "Before saving rules please submit course proposal title");
                    dialog.getConfirmButton().addClickHandler(new ClickHandler(){
                        @Override
                        public void onClick(ClickEvent event) {
                            dialog.hide();
                        }
                    });
                    dialog.show();
                    callback.exec(false);
                } else {
                    if (storeRules) {
                        rules.updateCourseRequisites(courseId, courseState, new Callback<List<StatementTreeViewInfo>>() {
                            @Override
                            public void exec(List<StatementTreeViewInfo> rules) {
                                for (StatementTreeViewInfo rule : rules) {
                                    updateRequirementWidgets(rule);
                                }
                                callback.exec(true);                                
                            }
                        });
                    } else {
                        callback.exec(true);                         
                    }
                }
            }
        });
    }
    
    static public String generateStatementTreeId() {
        return (NEW_STMT_TREE_ID + Integer.toString(tempProgReqInfoID++));
    }
}