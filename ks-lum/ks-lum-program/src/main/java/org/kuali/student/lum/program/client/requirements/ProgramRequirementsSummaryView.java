package org.kuali.student.lum.program.client.requirements;

import java.util.*;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ActionCancelGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.rules.RulePreviewWidget;
import org.kuali.student.common.ui.client.widgets.rules.RulesUtil;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.statement.dto.*;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProgramRequirementsSummaryView extends VerticalSectionView {

  //  private CluSetManagementRpcServiceAsync cluSetManagementRpcServiceAsync = GWT.create(CluSetManagementRpcService.class);

    //view's widgets
    private FlowPanel layout = new FlowPanel();
    private Map<String, KSLabel> noRuleTextMap = new HashMap<String, KSLabel>();
    private ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.SaveCancelEnum.SAVE, ButtonEnumerations.SaveCancelEnum.CANCEL);

    //view's data
    private ProgramRequirementsViewController parentController;
    private ProgramRequirementsDataModel rules;
    private boolean isReadOnly;
    public static int tempStmtTreeID = 9999;
    public static final String NEW_PROG_REQ_ID = "NEWPROGREQ";
    public static final String NEW_STMT_TREE_ID = "NEWSTMTTREE";
    public static final String NEW_REQ_COMP_ID = "NEWREQCOMP";    

    private Map<String, SpanPanel> perProgramRequirementTypePanel = new LinkedHashMap<String, SpanPanel>();

    public ProgramRequirementsSummaryView(final ProgramRequirementsViewController parentController, Enum<?> viewEnum, String name,
                                                            String modelId, ProgramRequirementsDataModel rulesData, boolean isReadOnly) {
        super(viewEnum, name, modelId);
        this.parentController = parentController;
        rules = rulesData;
        rules.setInitialized(false);
        this.isReadOnly = isReadOnly;
        if (!isReadOnly) {
            setupSaveCancelButtons();
        }
    }

    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {

        //only when user wants to see rules then load requirements from database if they haven't been loaded yet
        if (!rules.isInitialized()) {
            rules.retrieveProgramRequirements(new Callback<Boolean>() {
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
        parentController.getView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE, new Callback<View>(){
			@Override
			public void exec(View result) {
				ProgramRequirementsManageView manageView = (ProgramRequirementsManageView) result;
                
				//return if user did not added or updated a rule
                if (!manageView.isDirty() || !manageView.isUserClickedSaveButton()) {
                    onReadyCallback.exec(true);
                    return;
                }

                //update the rule because user added or edited the rule
                ((SectionView)parentController.getCurrentView()).setIsDirty(false);
                manageView.setUserClickedSaveButton(false);

                //if rule storage updated successfully, update the display as well
                ProgramRequirementInfo affectedRule = rules.updateRules(manageView.getRuleTree(), manageView.getInternalProgReqID(), manageView.isNewRule());
                updateRequirementWidgets(affectedRule);
                
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
                GWT.log("Unable to retrieve model for program requirements view", cause);
            }
            @Override
            public void onModelReady(Model model) {
                rules.updateProgramEntities(((DataModel)model).getRoot(), new Callback<ProgramRequirementInfo>() {
                    @Override
                    public void exec(ProgramRequirementInfo programReqInfo) {
                        updateRequirementWidgets(programReqInfo);
                    }
                });
            }
        });
    }

    private void updateRequirementWidgets(ProgramRequirementInfo programReqInfo) {
        if (programReqInfo != null) {
            StatementTypeInfo affectedStatementTypeInfo = rules.getStmtTypeInfo(programReqInfo.getStatement().getType());
            SpanPanel reqPanel = perProgramRequirementTypePanel.get(affectedStatementTypeInfo.getId());
            for (int i = 0; i < reqPanel.getWidgetCount(); i++) {
                RulePreviewWidget rulePreviewWidget = (RulePreviewWidget)reqPanel.getWidget(i);
                if (rulePreviewWidget.getInternalProgReqID().equals(rules.getInternalProgReqID(programReqInfo))) {
                        RulePreviewWidget newRulePreviewWidget = addProgramRequirement(reqPanel, programReqInfo);
                        reqPanel.insert(newRulePreviewWidget, i);
                        reqPanel.remove(rulePreviewWidget);
                }
            }
        }
    }    

    public void displayRules() {       
        remove(layout);
        layout.clear();

        //display 'Program Requirements' page title (don't add if read only because the section itself will display the title)
        if (!isReadOnly) {
            SectionTitle pageTitle = SectionTitle.generateH2Title(ProgramProperties.get().programRequirements_summaryViewPageTitle());
            pageTitle.setStyleName("KS-Program-Requirements-Section-header");  //make the header orange
            layout.add(pageTitle);
        }
        
        //iterate and display rules for each Program Requirement type e.g. Entrance Requirements, Completion Requirements
        Boolean firstRequirement = true;
        for (StatementTypeInfo stmtTypeInfo : rules.getStmtTypes()) {

            //create and display one type of program requirement section
            SpanPanel requirementsPanel = new SpanPanel();
            perProgramRequirementTypePanel.put(stmtTypeInfo.getId(), requirementsPanel);
            displayRequirementSectionForGivenType(requirementsPanel, stmtTypeInfo, firstRequirement);
            firstRequirement = false;

            //now display each requirement for this Program Requirement type
            for (ProgramRequirementInfo ruleInfo : rules.getProgReqInfo(stmtTypeInfo.getId())) {
                RulePreviewWidget rulePreviewWidget = addProgramRequirement(requirementsPanel, ruleInfo);
                requirementsPanel.add(rulePreviewWidget);
            }
        }

        //save and cancel buttons
        if (!isReadOnly) {
            layout.add(actionCancelButtons);
        }

        addWidget(layout);
    }

    private void displayRequirementSectionForGivenType(final SpanPanel requirementsPanel, final StatementTypeInfo stmtTypeInfo, boolean firstRequirement) {

        //display header for this Program Requirement type e.g. Entrance Requirements; make the header plural
        SectionTitle title = SectionTitle.generateH3Title(stmtTypeInfo.getName());
        title.setStyleName((firstRequirement ? "KS-Program-Requirements-Preview-Rule-Type-First-Header" : "KS-Program-Requirements-Preview-Rule-Type-Header"));  //make the header orange
        layout.add(title);

        //add rule description
        KSLabel ruleTypeDesc = new KSLabel(stmtTypeInfo.getDescr());
        ruleTypeDesc.addStyleName("KS-Program-Requirements-Preview-Rule-Type-Desc");        
        layout.add(ruleTypeDesc);

        //display "Add Rule" button if user is in 'edit' mode
        if (!isReadOnly) {
            String addRuleLabel = ProgramProperties.get().programRequirements_summaryViewPageAddRule(stmtTypeInfo.getName());
            KSButton addProgramReqBtn = new KSButton(addRuleLabel, KSButtonAbstract.ButtonStyle.FORM_SMALL);
            addProgramReqBtn.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                        showAddProgramRequirementDialog(requirementsPanel, stmtTypeInfo.getId());
                    }
                });
            layout.add(addProgramReqBtn);
        }

        layout.add(requirementsPanel);
        
        //add widget for displaying "No entrance requirement currently exist for this program"
        String noRuleText = ProgramProperties.get().programRequirements_summaryViewPageNoRule(stmtTypeInfo.getName().toLowerCase());
        KSLabel ruleDesc = new KSLabel(noRuleText);
        ruleDesc.addStyleName("KS-Program-Requirements-Preview-No-Rule-Text");
        noRuleTextMap.put(stmtTypeInfo.getId(), ruleDesc);
        setupNoRuleText(stmtTypeInfo.getId());
        layout.add(ruleDesc);
    }

    private RulePreviewWidget addProgramRequirement(final SpanPanel requirementsPanel, final ProgramRequirementInfo progReqInfo) {

        Integer internalProgReqID =  rules.getInternalProgReqID(progReqInfo);
        String stmtTypeId = progReqInfo.getStatement().getType();


        final RulePreviewWidget rulePreviewWidget = new RulePreviewWidget(internalProgReqID, rules.getStmtTypeName(stmtTypeId), progReqInfo.getShortTitle(),
                                                            progReqInfo.getDescr().getPlain(), progReqInfo.getStatement(),
                                                            isReadOnly, getCluSetWidgetList(progReqInfo.getStatement()));
        addRulePreviewWidgetHandlers(requirementsPanel, rulePreviewWidget, stmtTypeId, internalProgReqID);
        return rulePreviewWidget;
    }

    private void addRulePreviewWidgetHandlers(final SpanPanel requirementsPanel, final RulePreviewWidget rulePreviewWidget, final String stmtTypeId, final Integer internalProgReqID) {

        //PROGRAM REQUIREMENT handlers
        rulePreviewWidget.addProgReqEditButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
            showEditProgramRequirementDialog(requirementsPanel, stmtTypeId);
            }
        });

        rulePreviewWidget.addProgReqDeleteButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                final ConfirmationDialog dialog = new ConfirmationDialog(
                        ProgramProperties.get().programRequirements_summaryViewPageDeleteRuleDialogTitle(),
                        ProgramProperties.get().programRequirements_summaryViewPageDeleteRuleDialogMsg());

                dialog.getConfirmButton().addClickHandler(new ClickHandler(){
                    @Override
                    public void onClick(ClickEvent event) {
                        rules.deleteRule(internalProgReqID);

                        //remove rule from display
                        requirementsPanel.remove(rulePreviewWidget);
                        setupNoRuleText(stmtTypeId);

                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });

        //SUBRULE handlers
        rulePreviewWidget.addSubRuleAddButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                    final StatementTreeViewInfo newSubRule = new StatementTreeViewInfo();
                    newSubRule.setId(generateStatementTreeId());
                    newSubRule.setType(stmtTypeId);
                    RichTextInfo text = new RichTextInfo();
                    text.setPlain("");
                    newSubRule.setDesc(text);
                	parentController.getView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE, new Callback<View>(){

        				@Override
        				public void exec(View result) {
        					((ProgramRequirementsManageView) result).setRuleTree(newSubRule, true, internalProgReqID);
        					parentController.showView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE);
        				}
        			});
            }
        });

        rulePreviewWidget.addSubRuleEditButtonClickHandler(new Callback<RulePreviewWidget.SubRuleInfo>(){
            public void exec(final RulePreviewWidget.SubRuleInfo subRuleInfo) {
        	    parentController.getView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE, new Callback<View>(){
                    @Override
                    public void exec(View result) {
                        ((ProgramRequirementsManageView) result).setRuleTree(subRuleInfo.getSubrule(), false, subRuleInfo.getInternalProgReqID());
                        parentController.showView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE);
                    }
                });
			}
        });

        rulePreviewWidget.addSubRuleDeleteCallback(new Callback<Integer>(){
            public void exec(final Integer internalProgReqID) {
                //deleting subrule does not delete the requirement (rule) itself
                rules.markRuleAsEdited(internalProgReqID);
            }
        });
    }


    private Map<String, Widget> getCluSetWidgetList(StatementTreeViewInfo rule) {
        Map<String, Widget> widgetList = new HashMap<String, Widget>();
        Set<String> cluSetIds = new HashSet<String>();
        findCluSetIds(rule, cluSetIds);
        for (String clusetId : cluSetIds) {
           //TODO after Sherman changes
            //widgetList.put(clusetId, new CluSetDetailsWidget(clusetId, cluSetManagementRpcServiceAsync));
        }

        return widgetList;
    }

    private void findCluSetIds(StatementTreeViewInfo rule, Set<String> list) {

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

    private void showAddProgramRequirementDialog(final SpanPanel requirementsPanel, final String stmtTypeId) {

        String addRuleText = ProgramProperties.get().programRequirements_summaryViewPageAddRule(rules.getStmtTypeName(stmtTypeId));
        final KSLightBox dialog = new KSLightBox(addRuleText);

	    ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.AddCancelEnum.ADD, ButtonEnumerations.AddCancelEnum.CANCEL);

        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
                if (result == ButtonEnumerations.AddCancelEnum.ADD) {
 
                    ProgramRequirementInfo newProgramInfo = new ProgramRequirementInfo();
                    newProgramInfo.setId(NEW_PROG_REQ_ID + Integer.toString(tempStmtTreeID++));   //set unique id
                    newProgramInfo.setType("kuali.lu.type.Requirement");
                    //TODO remove after dialog fields implemented:
                    RichTextInfo text = new RichTextInfo();
                    text.setPlain("These are classes or sequences that a student must have completed in order to register" +
                                                " in the course. For example, students must have completed 3 classes with a specific GPA.");
                    newProgramInfo.setDescr(text);
                    newProgramInfo.setShortTitle("Expected Total Credits: 50 - 60");

                    //create a top level statement tree
                    StatementTreeViewInfo stmtTree = new StatementTreeViewInfo();
                    stmtTree.setId(generateStatementTreeId());
                    stmtTree.setType(stmtTypeId);
                    RichTextInfo text2 = new RichTextInfo();
                    text2.setPlain("");
                    stmtTree.setDesc(text2);                    
                    stmtTree.setOperator(StatementOperatorTypeKey.AND); //AND is top level operator for rules within a Program Requirement

                    //add new statement to the rule because even if user cancel on rule manage screen, we want to have at least one statement present
                    newProgramInfo.setStatement(stmtTree);

                    rules.addRule(newProgramInfo);
                    RulePreviewWidget rulePreviewWidget = addProgramRequirement(requirementsPanel, newProgramInfo);
                    requirementsPanel.add(rulePreviewWidget);
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

    private void showEditProgramRequirementDialog(final SpanPanel requirementsPanel, final String stmtTypeId) {

        String addRuleText = ProgramProperties.get().programRequirements_summaryViewPageAddRule(rules.getStmtTypeName(stmtTypeId));
        final KSLightBox dialog = new KSLightBox(addRuleText);

	    ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.UpdateCancelEnum.UPDATE, ButtonEnumerations.UpdateCancelEnum.CANCEL);

        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
                if (result == ButtonEnumerations.UpdateCancelEnum.CANCEL) {
                 dialog.hide();
                } else {
                    //TODO let user see edit dialog and validate
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

    private void setupNoRuleText(String stmtTypeId) {
        noRuleTextMap.get(stmtTypeId).setVisible(rules.isRuleExists(stmtTypeId));
    }

    private void setupSaveCancelButtons() {
        actionCancelButtons.addStyleName("KS-Program-Requirements-Save-Button");
        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
               updateModel();
            }
        });
    }
    static public String generateStatementTreeId() {
        return (NEW_STMT_TREE_ID + Integer.toString(tempStmtTreeID++));
    }

    public void setRules(ProgramRequirementsDataModel rules) {
        this.rules = rules;
        this.rules.setInitialized(false);
    }  
}