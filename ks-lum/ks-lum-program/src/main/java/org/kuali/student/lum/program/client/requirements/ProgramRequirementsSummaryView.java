package org.kuali.student.lum.program.client.requirements;

import java.util.*;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.service.MetadataRpcService;
import org.kuali.student.common.ui.client.service.MetadataRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ActionCancelGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.client.widgets.rules.RulePreviewWidget;
import org.kuali.student.common.ui.client.widgets.rules.RulesUtil;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.statement.dto.*;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.common.client.widgets.CluSetDetailsWidget;
import org.kuali.student.lum.common.client.widgets.CluSetRetriever;
import org.kuali.student.lum.common.client.widgets.CluSetRetrieverImpl;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProgramRequirementsSummaryView extends VerticalSectionView {

    private MetadataRpcServiceAsync metadataServiceAsync = GWT.create(MetadataRpcService.class);
    private static CluSetRetriever cluSetRetriever = new CluSetRetrieverImpl();    

    //view's widgets
    private FlowPanel layout = new FlowPanel();
    private Map<String, KSLabel> noRuleTextMap = new HashMap<String, KSLabel>();
    private ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.SaveCancelEnum.SAVE, ButtonEnumerations.SaveCancelEnum.CANCEL);
    private BasicLayout reqCompController;
    private FlowPanel holdFieldsPanel = new FlowPanel();    

    //view's data
    private ProgramRequirementsViewController parentController;
    private ProgramRequirementsDataModel rules;
    private boolean isReadOnly;
    public static int tempStmtTreeID = 9999;
    public static final String NEW_PROG_REQ_ID = "NEWPROGREQ";
    public static final String NEW_STMT_TREE_ID = "NEWSTMTTREE";
    public static final String NEW_REQ_COMP_ID = "NEWREQCOMP";
    private enum ProgramReqDialogView {VIEW}
    private static final String PROG_REQ_MODEL_ID = "progReqModelId";
    private DataModel progReqData;
    private BlockingTask gettingMetadataTask = new BlockingTask("Loading");
    private static Metadata dialogMetadata = null;  //simple caching

    private Map<String, SpanPanel> perProgramRequirementTypePanel = new LinkedHashMap<String, SpanPanel>();
    private Map<String, KSLabel> perProgramRequirementTypeTotalCredits = new LinkedHashMap<String, KSLabel>();

    public ProgramRequirementsSummaryView(final ProgramRequirementsViewController parentController, Enum<?> viewEnum, String name,
                                                            String modelId, boolean isReadOnly) {
        super(viewEnum, name, modelId);
        this.parentController = parentController;
        resetRules();
        this.isReadOnly = isReadOnly;

        if (!isReadOnly) {        
            setupSaveCancelButtons();
        }
    }

    @Override
    public boolean isDirty() {
        return rules.isDirty();
    }

    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {

        parentController.requestModel(ProgramConstants.PROGRAM_MODEL_ID, new ModelRequestCallback() {

            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert(cause.getMessage());
                GWT.log("Unable to retrieve program model for program summary view", cause);
                onReadyCallback.exec(false);
            }

            @Override
            public void onModelReady(Model model) {

                //if program does not have id it means user is trying to create a new one
                String programId = ((DataModel)model).getRoot().get(ProgramConstants.ID);                
                if (programId == null) {
                    resetRules();
                }
                showRules(onReadyCallback);
            }
        });
    }

    private void showRules(final Callback<Boolean> onReadyCallback) {

        //only when user wants to see rules then load requirements from database if they haven't been loaded yet
        if (!rules.isInitialized() || isReadOnly) {
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

    public void storeRules(final Callback<Boolean> callback) {
        rules.updateProgramEntities(new Callback<List<ProgramRequirementInfo>>() {
            @Override
            public void exec(List<ProgramRequirementInfo> programReqInfos) {
                
                for (ProgramRequirementInfo programReqInfo : programReqInfos) {
                    updateRequirementWidgets(programReqInfo);
                }

                if (callback != null) {
                    callback.exec(true);
                }
            }
        });
    }

    public void resetRules() {
        rules = new ProgramRequirementsDataModel(parentController);
        rules.setInitialized(false);
    }

    public void revertRuleChanges() {
        rules.revertRuleChanges();
        displayRules();
    }

    private void updateRequirementWidgets(ProgramRequirementInfo programReqInfo) {
        if (programReqInfo != null) {
            StatementTypeInfo affectedStatementTypeInfo = rules.getStmtTypeInfo(programReqInfo.getStatement().getType());
            SpanPanel reqPanel = perProgramRequirementTypePanel.get(affectedStatementTypeInfo.getId());

            //this happens if user previously deleted requirement but didn't save when moving to another section
            //add widget
            if (reqPanel.getWidgetCount() == 0) {
                reqPanel.add(addProgramRequirement(reqPanel, programReqInfo));
                return;
            }

            //replace widget with a new version
            for (int i = 0; i < reqPanel.getWidgetCount(); i++) {
                RulePreviewWidget rulePreviewWidget = (RulePreviewWidget)reqPanel.getWidget(i);
                if (rulePreviewWidget.getInternalProgReqID().equals(rules.getInternalProgReqID(programReqInfo))) {
                        RulePreviewWidget newRulePreviewWidget = addProgramRequirement(reqPanel, programReqInfo);
                        reqPanel.insert(newRulePreviewWidget, i);
                        reqPanel.remove(rulePreviewWidget);
                        break;
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
        perProgramRequirementTypePanel.clear();
        for (StatementTypeInfo stmtTypeInfo : rules.getStmtTypes()) {

            //create and display one type of program requirement section
            SpanPanel requirementsPanel = new SpanPanel();
            perProgramRequirementTypePanel.put(stmtTypeInfo.getId(), requirementsPanel);
            displayRequirementSectionForGivenType(requirementsPanel, stmtTypeInfo, firstRequirement);
            updateTotalCreditPerType(stmtTypeInfo.getId());            
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

        //add Total Credits
        KSLabel totalCredits = new KSLabel();
        totalCredits.addStyleName("KS-Program-Requirements-Preview-Rule-Type-Credits");
        perProgramRequirementTypeTotalCredits.put(stmtTypeInfo.getId(), totalCredits);
        layout.add(totalCredits);

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
                        showProgramRequirementDialog(requirementsPanel, stmtTypeInfo.getId(), null);
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

        int minCredits = (progReqInfo.getMinCredits() == null ? 0 : progReqInfo.getMinCredits());
        int maxCredits = (progReqInfo.getMaxCredits() == null ? 0 : progReqInfo.getMaxCredits());
        final RulePreviewWidget rulePreviewWidget = new RulePreviewWidget(internalProgReqID, progReqInfo.getShortTitle(),
                                                            getTotalCreditsString(minCredits, maxCredits), 
                                                            progReqInfo.getDescr().getPlain(), progReqInfo.getStatement(),
                                                            isReadOnly, getCluSetWidgetList(progReqInfo.getStatement()));
        addRulePreviewWidgetHandlers(requirementsPanel, rulePreviewWidget, stmtTypeId, internalProgReqID);
        return rulePreviewWidget;
    }

    private void addRulePreviewWidgetHandlers(final SpanPanel requirementsPanel, final RulePreviewWidget rulePreviewWidget, final String stmtTypeId, final Integer internalProgReqID) {

        //PROGRAM REQUIREMENT handlers
        rulePreviewWidget.addProgReqEditButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
            showProgramRequirementDialog(requirementsPanel, stmtTypeId, internalProgReqID);
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
                        updateTotalCreditPerType(stmtTypeId);
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
            widgetList.put(clusetId, new CluSetDetailsWidget(clusetId, cluSetRetriever));
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

    private void showProgramRequirementDialog(final SpanPanel requirementsPanel, final String stmtTypeId, final Integer internalProgReqID) {

        boolean isAddProgReq = (internalProgReqID == null);

        String addRuleText = (isAddProgReq ? ProgramProperties.get().programRequirements_summaryViewPageAddRule(rules.getStmtTypeName(stmtTypeId)) : "Edit " + rules.getStmtTypeName(stmtTypeId));
        final KSLightBox dialog = new KSLightBox(addRuleText);

        final ButtonEnumerations.ButtonEnum actionButton = (isAddProgReq ? ButtonEnumerations.AddCancelEnum.ADD : ButtonEnumerations.UpdateCancelEnum.UPDATE);
	    ActionCancelGroup actionCancelButtons = new ActionCancelGroup(actionButton, ButtonEnumerations.UpdateCancelEnum.CANCEL);

        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
                if (result == actionButton) {

                    reqCompController.updateModel();

                    //validate and retrieve fields
                    if (progReqData.getRoot().size() > 0) {
                        progReqData.validate(new Callback<List<ValidationResultInfo>>() {
                            @Override
                            public void exec(List<ValidationResultInfo> validationResults) {

                                //do not proceed if the user input is not valid
                                if (!reqCompController.isValid(validationResults, true, true)) {
                                    return;
                                }

                                //retrieve entered values and set the rule info
                                updateProgramInfo(requirementsPanel, stmtTypeId, internalProgReqID);
                                dialog.hide();
                            }
                        });
                    }
                } else {
                    dialog.hide();
                }
            }
        });

        createAddProgramReqDialog(dialog, actionCancelButtons, internalProgReqID);
    }    

    private void createAddProgramReqDialog(final KSLightBox dialog, final ActionCancelGroup actionCancelButtons, final Integer internalProgReqID) {

        parentController.requestModel(ProgramConstants.PROGRAM_MODEL_ID, new ModelRequestCallback() {

            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert(cause.getMessage());
                GWT.log("Unable to retrieve program model for program summary view", cause);
            }

            @Override
            public void onModelReady(Model model) {

                //program has to be in the database before we can save program requirements
                String programId = ((DataModel)model).getRoot().get(ProgramConstants.ID);                
                if (programId == null) {
                    final ConfirmationDialog dialog = new ConfirmationDialog("Save Program Key Info", "Before saving rules please save program key info");
                    dialog.getConfirmButton().addClickHandler(new ClickHandler(){
                        @Override
                        public void onClick(ClickEvent event) {
                            dialog.hide();
                        }
                    });
                    dialog.show();
                    return;
                }

                if (dialogMetadata == null) {
                    KSBlockingProgressIndicator.addTask(gettingMetadataTask);
                    metadataServiceAsync.getMetadataList("org.kuali.student.lum.program.dto.ProgramRequirementInfo", "Active", new KSAsyncCallback<Metadata>() {
                        public void handleFailure(Throwable caught) {
                            KSBlockingProgressIndicator.removeTask(gettingMetadataTask);
                            Window.alert(caught.getMessage());
                            GWT.log("getMetadataList failed for ProgramRequirementInfo", caught);
                        }

                        public void onSuccess(final Metadata metadata) {
                            KSBlockingProgressIndicator.removeTask(gettingMetadataTask);
                            dialogMetadata = metadata;
                            showDialog(dialog, actionCancelButtons, metadata, internalProgReqID);
                        }
                    });
                } else {
                    showDialog(dialog, actionCancelButtons, dialogMetadata, internalProgReqID);
                }

            }
        });
    }

    //TODO rework to use Configurer if possible
    private void showDialog(final KSLightBox dialog, final ActionCancelGroup actionCancelButtons, Metadata metadata, Integer internalProgReqID) {

        String[] fieldIds = {"shortTitle", "minCredits", "maxCredits", "descr"};
        String[] fieldLabels = {"Name", "Minimum expected credits", "Maximum expected credits", "Description"};        
        Map<String, Metadata> fieldDefinitionMetadata = new HashMap<String,Metadata>();
        Map<String, FieldDescriptor> fields = new HashMap<String, FieldDescriptor>();        
        VerticalSectionView dialogPanel = new VerticalSectionView(ProgramReqDialogView.VIEW, "", PROG_REQ_MODEL_ID, false);
        holdFieldsPanel.clear();

        for (int i = 0; i < 4; i++) {

            Metadata fieldMetadata = metadata.getProperties().get(fieldIds[i]);
            if (fieldIds[i].equals("descr")) {
                fieldMetadata = fieldMetadata.getProperties().get("plain");                
            }
            String fieldPath = fieldIds[i];
            FieldDescriptor fd = new FieldDescriptor(fieldPath, new MessageKeyInfo(fieldLabels[i]), fieldMetadata);
            dialogPanel.addField(fd);
            fields.put(fieldPath, fd);

            //add field to the data model metadata
            fieldDefinitionMetadata.put(fieldPath, fieldMetadata);
        }

        //setup data model
        Metadata modelDefinitionMetadata = new Metadata();
        modelDefinitionMetadata.setCanView(true);
        modelDefinitionMetadata.setDataType(Data.DataType.DATA);
        modelDefinitionMetadata.setProperties(fieldDefinitionMetadata);
        progReqData = new DataModel();
        progReqData.setRoot(new Data());
        progReqData.setDefinition(new DataModelDefinition(modelDefinitionMetadata));

        //initialize fields with values if user is editing an existing rule
        if (internalProgReqID != null) {
            ProgramRequirementInfo progReq = rules.getProgReqByInternalId(internalProgReqID);
            progReqData.set(QueryPath.parse("shortTitle"), progReq.getShortTitle());         
            progReqData.set(QueryPath.parse("minCredits"), progReq.getMinCredits());
            progReqData.set(QueryPath.parse("maxCredits"), progReq.getMaxCredits()); 
            progReqData.set(QueryPath.parse("descr"), progReq.getDescr().getPlain());
        }

        //setup controller
        reqCompController = new BasicLayout(null);
        reqCompController.addView(dialogPanel);
        reqCompController.setDefaultModelId(PROG_REQ_MODEL_ID);
        reqCompController.registerModel(PROG_REQ_MODEL_ID, new ModelProvider<DataModel>() {
            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                callback.onModelReady(progReqData);
            }
        });

        //show fields
        holdFieldsPanel.add(reqCompController);
        reqCompController.showView(ProgramReqDialogView.VIEW);

        //layout.addStyleName("KS-Advanced-Search-Window");
        holdFieldsPanel.add(actionCancelButtons);
        dialog.setSize(550,530);
        dialog.setWidget(holdFieldsPanel);
        dialog.show();
    }

    private void updateProgramInfo(SpanPanel requirementsPanel, String stmtTypeId, Integer internalProgReqID) {

        ProgramRequirementInfo progReqInfo;
        if (internalProgReqID == null) {
            progReqInfo = new ProgramRequirementInfo();
            progReqInfo.setId(NEW_PROG_REQ_ID + Integer.toString(tempStmtTreeID++));   //set unique id
            progReqInfo.setType("kuali.lu.type.Requirement");

            //create a top level statement tree
            StatementTreeViewInfo stmtTree = new StatementTreeViewInfo();
            stmtTree.setId(generateStatementTreeId());
            stmtTree.setType(stmtTypeId);
            RichTextInfo text2 = new RichTextInfo();
            text2.setPlain("");
            stmtTree.setDesc(text2);
            stmtTree.setOperator(StatementOperatorTypeKey.AND); //AND is top level operator for rules within a Program Requirement

            //add new statement to the rule because even if user cancel on rule manage screen, we want to have at least one statement present
            progReqInfo.setStatement(stmtTree);
        } else {
            progReqInfo = rules.getProgReqByInternalId(internalProgReqID);
        }

        RichTextInfo text = new RichTextInfo();
        text.setPlain((String)(progReqData.getRoot().get("descr")));
        progReqInfo.setDescr(text);
        progReqInfo.setShortTitle((String)progReqData.getRoot().get("shortTitle"));
        progReqInfo.setLongTitle((String)progReqData.getRoot().get("shortTitle"));        
        progReqInfo.setMinCredits((Integer)progReqData.getRoot().get("minCredits"));
        progReqInfo.setMaxCredits((Integer)progReqData.getRoot().get("maxCredits"));

        if (internalProgReqID == null) {
            rules.addRule(progReqInfo);
            RulePreviewWidget rulePreviewWidget = addProgramRequirement(requirementsPanel, progReqInfo);
            requirementsPanel.add(rulePreviewWidget);
        } else {
            rules.updateRule(internalProgReqID, progReqInfo);
            for (Object aRequirementsPanel : requirementsPanel) {
                RulePreviewWidget rulePreviewWidget = (RulePreviewWidget) aRequirementsPanel;
                if (rulePreviewWidget.getInternalProgReqID().equals(internalProgReqID)) {
                    rulePreviewWidget.updateProgInfoFields(progReqInfo.getShortTitle(),
                                                            getTotalCreditsString(progReqInfo.getMinCredits(), progReqInfo.getMaxCredits()),
                                                            progReqInfo.getDescr().getPlain());
                }
            }
        }

        updateTotalCreditPerType(stmtTypeId);
    }

    private void setupNoRuleText(String stmtTypeId) {
        noRuleTextMap.get(stmtTypeId).setVisible(rules.isRuleExists(stmtTypeId));
    }

    private String getTotalCreditsString(int min, int max) {
        return "Expected Total Credits:" + min + "-" + max;
    }

    private void updateTotalCreditPerType(String stmtTypeId) {
        int min = 0;
        int max = 0;
        for (ProgramRequirementInfo ruleInfo : rules.getProgReqInfo(stmtTypeId)) {
            min += ruleInfo.getMinCredits();
            max += ruleInfo.getMaxCredits();
        }

        //update total
        perProgramRequirementTypeTotalCredits.get(stmtTypeId).setText(getTotalCreditsString(min, max));
    }

    private void setupSaveCancelButtons() {
        actionCancelButtons.addStyleName("KS-Program-Requirements-Save-Button");
        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
                if (result == ButtonEnumerations.SaveCancelEnum.SAVE) {
                    storeRules(null);
                } else {
                    HistoryManager.navigate(AppLocations.Locations.VIEW_PROGRAM.getLocation(), parentController.getViewContext());
                }
            }
        });
    }

    static public String generateStatementTreeId() {
        return (NEW_STMT_TREE_ID + Integer.toString(tempStmtTreeID++));
    }
}