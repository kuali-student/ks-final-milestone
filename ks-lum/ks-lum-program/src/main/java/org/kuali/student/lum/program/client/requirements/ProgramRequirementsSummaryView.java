package org.kuali.student.lum.program.client.requirements;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import org.kuali.student.common.assembly.data.ConstraintMetadata;
import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.dto.RichTextInfo;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.*;
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
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.core.statement.dto.*;
import org.kuali.student.lum.common.client.widgets.CluSetDetailsWidget;
import org.kuali.student.lum.common.client.widgets.CluSetRetriever;
import org.kuali.student.lum.common.client.widgets.CluSetRetrieverImpl;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.widgets.EditableHeader;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import java.util.*;

public class ProgramRequirementsSummaryView extends VerticalSectionView {

    private MetadataRpcServiceAsync metadataServiceAsync = GWT.create(MetadataRpcService.class);
    private static CluSetRetriever cluSetRetriever = new CluSetRetrieverImpl();

    //view's widgets
    private FlowPanel layout = new FlowPanel();
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


    private enum ProgramReqDialogView {
        VIEW
    }

    private static final String PROG_REQ_MODEL_ID = "progReqModelId";
    private DataModel progReqData;
    private BlockingTask gettingMetadataTask = new BlockingTask("Loading");
    private static Metadata dialogMetadata = null;  //simple caching

    private Map<String, SpanPanel> perProgramRequirementTypePanel = new LinkedHashMap<String, SpanPanel>();
    private Map<String, KSLabel> perProgramRequirementTypeTotalCredits = new LinkedHashMap<String, KSLabel>();

    public ProgramRequirementsSummaryView(final ProgramRequirementsViewController parentController, HandlerManager eventBus, Enum<?> viewEnum, String name,
                                          String modelId, boolean isReadOnly) {
        super(viewEnum, name, modelId);
        init(parentController, eventBus, isReadOnly);
    }

    public ProgramRequirementsSummaryView(final ProgramRequirementsViewController parentController, HandlerManager eventBus, Enum<?> viewEnum, String name,
                                          String modelId, boolean isReadOnly, EditableHeader header) {
        super(viewEnum, name, modelId, header);
        init(parentController, eventBus, isReadOnly);
    }

    private void init(ProgramRequirementsViewController parentController, HandlerManager eventBus, boolean isReadOnly) {
        this.parentController = parentController;
        this.isReadOnly = isReadOnly;
        rules = new ProgramRequirementsDataModel(eventBus);

        if (!isReadOnly) {
            setupSaveCancelButtons();
        }
    }

    @Override
    public boolean isDirty() {
        return rules.isDirty();
    }

    protected ProgramRequirementsDataModel getRules() {
        return rules;
    }

    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
        if (!rules.isInitialized() || parentController.reloadFlag) {
            retrieveProgramRequirements(onReadyCallback);
            return;
        }
        onReadyCallback.exec(true);
    }

    private void retrieveProgramRequirements(final Callback<Boolean> onReadyCallback) {
        rules.retrieveProgramRequirements(parentController, new Callback<Boolean>() {
            @Override
            public void exec(Boolean result) {
                if (result) {
                    displayRules();
                }
                onReadyCallback.exec(result);
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
                callback.exec(true);
            }
        });
    }

     public void justStoreRules(final Callback<Boolean> callback) {
        rules.updateProgramEntities(new Callback<List<ProgramRequirementInfo>>() {
            @Override
            public void exec(List<ProgramRequirementInfo> programReqInfos) {
                callback.exec(true);
            }
        });
    }

    public void revertRuleChanges() {
        rules.revertRuleChanges();
        displayRules();
    }

    protected void updateRequirementWidgets(ProgramRequirementInfo programReqInfo) {
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
                RulePreviewWidget rulePreviewWidget = (RulePreviewWidget) reqPanel.getWidget(i);
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
            //pageTitle.setStyleName("KS-Program-Requirements-Section-header");  //make the header orange
            pageTitle.addStyleName("ks-layout-header");// change the header to green

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
        title.setStyleName((firstRequirement ? "KS-Program-Requirements-Preview-Rule-Type-First-Header" : "KS-Program-Requirements-Preview-Rule-Type-Header"));
        layout.add(title);

        //add Total Credits
        KSLabel totalCredits = new KSLabel();
        totalCredits.addStyleName("KS-Program-Requirements-Preview-Rule-Type-Credits");
        perProgramRequirementTypeTotalCredits.put(stmtTypeInfo.getId(), totalCredits);
        totalCredits.setVisible(false);
        layout.add(totalCredits);

        //add rule description
        if (!isReadOnly) {
            KSLabel ruleTypeDesc = new KSLabel(stmtTypeInfo.getDescr());
            ruleTypeDesc.addStyleName("KS-Program-Requirements-Preview-Rule-Type-Desc");
            layout.add(ruleTypeDesc);
        }

        //display "Add Rule" button if user is in 'edit' mode
        if (!isReadOnly) {
            String addRuleLabel = ProgramProperties.get().programRequirements_summaryViewPageAddRule(stmtTypeInfo.getName());
            KSButton addProgramReqBtn = new KSButton(addRuleLabel, KSButtonAbstract.ButtonStyle.FORM_SMALL);
            addProgramReqBtn.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    showProgramRequirementDialog(requirementsPanel, stmtTypeInfo.getId(), null);
                }
            });
            layout.add(addProgramReqBtn);
        }

        layout.add(requirementsPanel);
    }

    private RulePreviewWidget addProgramRequirement(final SpanPanel requirementsPanel, final ProgramRequirementInfo progReqInfo) {

        Integer internalProgReqID = rules.getInternalProgReqID(progReqInfo);
        String stmtTypeId = progReqInfo.getStatement().getType();

        int minCredits = (progReqInfo.getMinCredits() == null ? 0 : progReqInfo.getMinCredits());
        int maxCredits = (progReqInfo.getMaxCredits() == null ? 0 : progReqInfo.getMaxCredits());
        String plainDesc = (progReqInfo.getDescr() == null ? "" : progReqInfo.getDescr().getPlain());
        final RulePreviewWidget rulePreviewWidget = new RulePreviewWidget(internalProgReqID, progReqInfo.getShortTitle(),
                getTotalCreditsString(minCredits, maxCredits),
                plainDesc, progReqInfo.getStatement(),
                isReadOnly, getCluSetWidgetList(progReqInfo.getStatement()));
        addRulePreviewWidgetHandlers(requirementsPanel, rulePreviewWidget, stmtTypeId, internalProgReqID);
        return rulePreviewWidget;
    }

    private void addRulePreviewWidgetHandlers(final SpanPanel requirementsPanel, final RulePreviewWidget rulePreviewWidget, final String stmtTypeId, final Integer internalProgReqID) {

        //PROGRAM REQUIREMENT handlers
        rulePreviewWidget.addProgReqEditButtonClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                showProgramRequirementDialog(requirementsPanel, stmtTypeId, internalProgReqID);
            }
        });

        rulePreviewWidget.addProgReqDeleteButtonClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                final ConfirmationDialog dialog = new ConfirmationDialog(
                        ProgramProperties.get().programRequirements_summaryViewPageDeleteRequirementDialogTitle(),
                        ProgramProperties.get().programRequirements_summaryViewPageDeleteRequirementDialogMsg());

                dialog.getConfirmButton().addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        rules.deleteRule(internalProgReqID);

                        //remove rule from display
                        requirementsPanel.remove(rulePreviewWidget);
                        perProgramRequirementTypeTotalCredits.get(stmtTypeId).setVisible(false);
                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });

        //SUBRULE handlers
        rulePreviewWidget.addSubRuleAddButtonClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                final StatementTreeViewInfo newSubRule = new StatementTreeViewInfo();
                newSubRule.setId(generateStatementTreeId());
                newSubRule.setType(stmtTypeId);
                RichTextInfo text = new RichTextInfo();
                text.setPlain("");
                newSubRule.setDesc(text);
                parentController.getView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE, new Callback<View>() {

                    @Override
                    public void exec(View result) {
                        ((ProgramRequirementsManageView) result).setRuleTree(newSubRule, true, internalProgReqID);
                        parentController.showView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE);
                    }
                });
            }
        });

        rulePreviewWidget.addSubRuleEditButtonClickHandler(new Callback<RulePreviewWidget.SubRuleInfo>() {
            public void exec(final RulePreviewWidget.SubRuleInfo subRuleInfo) {
                parentController.getView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE, new Callback<View>() {
                    @Override
                    public void exec(View result) {
                        ((ProgramRequirementsManageView) result).setRuleTree(subRuleInfo.getSubrule(), false, subRuleInfo.getInternalProgReqID());
                        parentController.showView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE);
                    }
                });
            }
        });

        rulePreviewWidget.addSubRuleDeleteCallback(new Callback<Integer>() {
            public void exec(final Integer internalProgReqID) {
                //deleting subrule does not delete the requirement (rule) itself
                rules.markRuleAsEdited(internalProgReqID);
            }
        });
    }

    protected Map<String, Widget> getCluSetWidgetList(StatementTreeViewInfo rule) {
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

        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>() {
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

        parentController.requestModel(ProgramConstants.PROGRAM_MODEL_ID, new ModelRequestCallback<DataModel>() {

            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert(cause.getMessage());
                GWT.log("Unable to retrieve program model for program summary view", cause);
            }

            @Override
            public void onModelReady(DataModel model) {

                //program has to be in the database before we can save program requirements
                String programId = model.getRoot().get(ProgramConstants.ID);
                if (programId == null) {
                    final ConfirmationDialog dialog = new ConfirmationDialog("Save Program Key Info", "Before saving rules please save program key info");
                    dialog.getConfirmButton().addClickHandler(new ClickHandler() {
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

    private static FieldDescriptor createInputField(final String id, final Metadata metadata, final Map<String, Metadata> fieldDefinitionMetadata, final Map<String, FieldDescriptor> fields) {
        FieldDescriptor fd = new FieldDescriptor(id, new MessageKeyInfo("program", "program", "draft", id), metadata);
        fields.put(id, fd);

        //add field to the data model metadata
        fieldDefinitionMetadata.put(id, metadata);

        return fd;
    }

    //TODO rework to use Configurer if possible
    private void showDialog(final KSLightBox dialog, final ActionCancelGroup actionCancelButtons, Metadata metadata, Integer internalProgReqID) {

        Map<String, Metadata> fieldDefinitionMetadata = new HashMap<String, Metadata>();
        Map<String, FieldDescriptor> fields = new HashMap<String, FieldDescriptor>();
        VerticalSectionView dialogPanel = new VerticalSectionView(ProgramReqDialogView.VIEW, "", PROG_REQ_MODEL_ID, false);
        holdFieldsPanel.clear();

        dialogPanel.addField(createInputField("shortTitle", metadata.getProperties().get("shortTitle"), fieldDefinitionMetadata, fields));
        FieldDescriptor minFd = createInputField("minCredits", metadata.getProperties().get("minCredits"), fieldDefinitionMetadata, fields);
        FieldDescriptor maxFd = createInputField("maxCredits", metadata.getProperties().get("maxCredits"), fieldDefinitionMetadata, fields);
        GroupSection credits = new GroupSection();
        credits.addField(minFd);
        credits.addField(maxFd);
        dialogPanel.addSection(credits);
        final Metadata descrMetadata = metadata.getProperties().get("descr").getProperties().get("plain");
        dialogPanel.addField(createInputField("descr", descrMetadata, fieldDefinitionMetadata, fields));

        List<ConstraintMetadata> constraints = descrMetadata.getConstraints();
        dialogPanel.addWidget(new KSLabel(constraints.get(0).getMaxLength() + getUILabel("descrLimit")));

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
            progReqData.set(QueryPath.parse("descr"), (progReq.getDescr() == null ? "" : progReq.getDescr().getPlain()));
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
        dialog.setSize(550, 530);
        dialog.setWidget(holdFieldsPanel);
        dialog.show();
    }

    private String getUILabel(final String id) {
        return Application.getApplicationContext().getUILabel("program", null, null, id);
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
        text.setPlain((String) (progReqData.getRoot().get("descr")));
        progReqInfo.setDescr(text);
        progReqInfo.setShortTitle((String) progReqData.getRoot().get("shortTitle"));
        progReqInfo.setLongTitle((String) progReqData.getRoot().get("shortTitle"));
        progReqInfo.setMinCredits((Integer) progReqData.getRoot().get("minCredits"));
        progReqInfo.setMaxCredits((Integer) progReqData.getRoot().get("maxCredits"));

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

        if (min != 0 || max != 0) {
            //update total
            perProgramRequirementTypeTotalCredits.get(stmtTypeId).setVisible(true);
            perProgramRequirementTypeTotalCredits.get(stmtTypeId).setText(getTotalCreditsString(min, max));
        }
    }

    private void setupSaveCancelButtons() {
        actionCancelButtons.addStyleName("KS-Program-Requirements-Save-Button");
        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>() {
            @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
                if (result == ButtonEnumerations.SaveCancelEnum.SAVE) {
                    storeRules(Controller.NO_OP_CALLBACK);
                } else {
                    //does not work any more: HistoryManager.navigate(AppLocations.Locations.VIEW_PROGRAM.getLocation(), parentController.getViewContext());
                    parentController.getParentController().showView(ProgramSections.SUMMARY);
                }
            }
        });
    }

    static public String generateStatementTreeId() {
        return (NEW_STMT_TREE_ID + Integer.toString(tempStmtTreeID++));
    }
}