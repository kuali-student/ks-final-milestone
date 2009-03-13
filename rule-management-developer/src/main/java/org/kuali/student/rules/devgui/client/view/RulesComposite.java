/**
 * 
 */
package org.kuali.student.rules.devgui.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.kuali.student.commons.ui.logging.client.Logger;
import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelBinding;
import org.kuali.student.commons.ui.validators.client.ValidationResult;
import org.kuali.student.commons.ui.validators.client.Validator;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.widgets.tables.ModelTableSelectionListener;
import org.kuali.student.commons.ui.widgets.trees.TreeMouseOverListener;
import org.kuali.student.rules.devgui.client.DateRange;
import org.kuali.student.rules.devgui.client.GuiUtil;
import org.kuali.student.rules.devgui.client.IllegalRuleFormatException;
import org.kuali.student.rules.devgui.client.GuiUtil.YieldValueFunctionType;
import org.kuali.student.rules.devgui.client.controller.DevelopersGuiController;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.rules.devgui.client.model.RulesVersionInfo;
import org.kuali.student.rules.devgui.client.service.DevelopersGuiService;
import org.kuali.student.rules.factfinder.dto.FactParamDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.rules.ruleexecution.dto.PropositionReportDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleTypeInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.MetaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Zdenek
 */
public class RulesComposite extends Composite {

    // final static Logger logger = LoggerFactory.getLogger(RulesComposite.class);

    public static final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("HH:mm MMM d, yyyy");
    final String FORM_ROW_HEIGHT = "16px";

    // events to be fired to parent controller
    public static class RulesEvent extends MVCEvent {}

    public static class RulesAddEvent extends RulesEvent {}

    public static class RulesUpdateEvent extends RulesEvent {}

    public static class RulesTestEvent extends RulesEvent {}

    // singleton instances of the events
    public static final RulesEvent RULES_EVENT = GWT.create(RulesEvent.class);
    public static final RulesAddEvent RULES_ADD_EVENT = GWT.create(RulesAddEvent.class);
    public static final RulesUpdateEvent RULES_UPDATE_EVENT = GWT.create(RulesUpdateEvent.class);
    public static final RulesTestEvent RULES_REMOVE_EVENT = GWT.create(RulesTestEvent.class);

    // controller and meta data to be looked up externally
    Controller controller;
    ViewMetaData metadata;
    Messages messages;

    // class that binds a widget to a model, instantiation is deferred
    // until application state is guaranteed to be ready
    ModelBinding<RulesHierarchyInfo> binding;
    ModelBinding<RulesVersionInfo> versionInfoBinding;

    // widgets used for Rules forms.....
    final BusinessRulesTree rulesTree = new BusinessRulesTree(); // used to browse Rules
    final ScrollPanel rulesBrowserScrollPanel = new ScrollPanel();
    final HorizontalSplitPanel rulesHorizontalSplitPanel = new HorizontalSplitPanel();
    final ScrollPanel rulesScrollPanel = new ScrollPanel();
    final VerticalSplitPanel rulesVerticalSplitPanel = new VerticalSplitPanel();
    final SimplePanel simplePanel = new SimplePanel();
    final Label copiedVersionIndicator = new Label("");
    final TabPanel rulesFormTabs = new TabPanel();

    // Rule Version tab
    // final Grid rulesVersionsGrid = new Grid();
    final BusinessRulesVersionTable rulesVersionsTable = new BusinessRulesVersionTable();
    final TextArea activeTimeGapsTextArea = new TextArea();

    // Main rules tab
    final Label businessRuleID = new Label();
    final Label ruleStatus = new Label();
    final TextBox nameTextBox = new TextBox();
    final TextArea descriptionTextArea = new TextArea();
    final TextArea successMessageTextArea = new TextArea();
    final TextArea failureMessageTextArea = new TextArea();
    final ListBox agendaTypesListBox = new ListBox();
    final ListBox businessRuleTypesListBox = new ListBox();
    final Label ruleAnchorType = new Label();
    final TextBox ruleAnchorTextBox = new TextBox();

    // Propositions rules tab
    // YVF - for now, hard code the number of parameters (1 or 2) and number of facts (up to 2)
    final ListBox yvfListBox = new ListBox();

    // YVF Facts
    final VerticalPanel factDetailsPanel = new VerticalPanel();
    final Map<String, Widget> factTypes = new HashMap<String, Widget>();
    final Map<String, String> factTypeSelectedValues = new HashMap<String, String>();
    final Map<String, List<TextArea>> factTypeParams = new HashMap<String, List<TextArea>>();
    final Map<String, VerticalPanel> factParamsPanel = new HashMap<String, VerticalPanel>();
    private List<FactTypeInfoDTO> definedFactTypesList = null; // keep copy of current list of factTypeKeys based on business
                                                               // rule type
    private boolean loadingFactTypeKeyList = false; // TODO 'loading' icon; also for other drop downs

    // Propositions
    final VerticalPanel propositionDetailsPanel = new VerticalPanel();
    final ListBox propositionsListBox = new ListBox();
    int selectedPropListBoxIndex = -1;
    final TextBox propNameTextBox = new TextBox();
    final TextBox propDescTextBox = new TextBox();
    final ListBox operatorsListBox = new ListBox();
    final TextArea completeRuleTextArea = new TextArea();
    final TextArea propCompositionTextArea = new TextArea();
    final Button validateRuleButton = new Button("Validate Rule");
    final TextBox expectedValueTextBox = new TextBox();
    final Label compositionStatusLabel = new Label();
    // Proposition Update, Reset, Add, Delete buttons
    final Button addPropButton = new Button("Add");
    final Button updatePropButton = new Button("Update");
    final Button removePropButton = new Button("Remove");
    final Button makeCopyPropButton = new Button("Make Copy");
    // final Button cancelPropButton = new Button("Cancel Changes");

    // Authoring tab
    final TextBox effectiveDateTextBox = new TextBox();
    final TextBox expiryDateTextBox = new TextBox();
    final Label createTimeLabel = new Label();
    final Label createUserIdLabel = new Label();
    final TextBox createCommentTextBox = new TextBox();
    final Label updateTimeLabel = new Label();
    final Label updateUserIdLabel = new Label();
    final TextBox updateCommentTextBox = new TextBox();

    // Test Rule tab
    final VerticalPanel propositionsTestPanel = new VerticalPanel();
    final Map<String, String> definitionFactValuesForTest = new HashMap<String, String>();
    final Map<String, String> executionFactValuesForTest = new HashMap<String, String>();
    final List<TextArea> testDefinitionTimeFactsWidgets = new ArrayList<TextArea>();
    final List<TextArea> testExecutionTimeFactsWidgets = new ArrayList<TextArea>();
    final Button testRuleButton = new Button("Test Rule");
    final TextArea testReport = new TextArea();
    final TextArea completeRuleTestTextArea = new TextArea();
    final TextArea propCompositionTestTextArea = new TextArea();
    final Label testResult = new Label();

    final PopupPanel treePopupMessage = new PopupPanel(true);
    final VerticalPanel treePopupMessagePanel = new VerticalPanel();
    final HTML treePopupMessageHTML = new HTML();

    boolean loaded = false;

    public RulesComposite() {
        super.initWidget(simplePanel);
    }

    // main buttons controlling rule life cycle
    final Button submitRuleButton = new Button("Submit Rule");
    final Button updateRuleButton = new Button("Update Rule");
    final Button activateRuleButton = new Button("Activate Rule");
    final Button createNewVersionButton = new Button("Create New Version");
    final Button retireRuleButton = new Button("Retire Rule");
    final Button copyRuleButton = new Button("Make Rule Copy");
    final Button cancelButton = new Button("Cancel Changes");

    // variables representing client internal state
    private Model<RulesHierarchyInfo> displayedRulesGroup = null; // keep copy of rule group info
    private BusinessRuleInfoDTO displayedRule = null; // keep copy of business rule so we can update all fields user
    private BusinessRuleInfoDTO displayedRuleInitialCopy = null; // keep the rule initial copy, give user warning if they
                                                                 // made changes
    private RulesVersionInfo displayedRuleInfo = null; // keep copy of rule meta info
    private Map<Integer, RuleElementDTO> definedPropositions = new TreeMap<Integer, RuleElementDTO>();
    private StringBuffer ruleComposition;
    private final String STATUS_NOT_STORED_IN_DATABASE = "TO_BE_ENTERED";
    private final String EMPTY_LIST_BOX_ITEM = "        ";
    private final String USER_DEFINED_FACT_TYPE_KEY = "STATIC  FACT";
    private static Integer factStructureTemporaryID = 1;

    @Override
    protected void onLoad() {
        super.onLoad();
        if (!loaded) {
            loaded = true;
            // get a reference to our parent controller
            controller = MVC.findParentController(this);

            // get a reference to our view meta data and internationalization messages
            metadata = ApplicationContext.getViews().get(DevelopersGuiController.VIEW_NAME);
            messages = metadata.getMessages();

            // bind the list to the parent controller's Model of BusinessRuleInfo objects
            Model<RulesHierarchyInfo> model = (Model<RulesHierarchyInfo>) controller.getModel(RulesHierarchyInfo.class);
            displayedRulesGroup = model;
            binding = new ModelBinding<RulesHierarchyInfo>(model, rulesTree);
            Model<RulesVersionInfo> rulesVersionModel = (Model<RulesVersionInfo>) controller.getModel(RulesVersionInfo.class);
            versionInfoBinding = new ModelBinding<RulesVersionInfo>(rulesVersionModel, rulesVersionsTable);

            // initialize client internal state
            loadEmptyRule();

            // create tree-like rules browser
            rulesTree.setSize("100%", "100%");
            rulesBrowserScrollPanel.add(rulesTree);

            // create panel with a tree on left and a form on the right
            rulesHorizontalSplitPanel.setLeftWidget(rulesTree);
            rulesHorizontalSplitPanel.setRightWidget(addRulesForm());
            rulesHorizontalSplitPanel.setSize("100%", "100%");
            rulesHorizontalSplitPanel.setSplitPosition("35%");

            // scroll panel on the bottom for log/error messages
            rulesScrollPanel.setSize("100%", "0%"); // TODO setSize("100%", "100%");

            // add tree/form and scroll panel together
            rulesVerticalSplitPanel.setSize("100%", "800px");
            rulesVerticalSplitPanel.setTopWidget(rulesHorizontalSplitPanel);
            rulesVerticalSplitPanel.setBottomWidget(rulesScrollPanel);
            rulesVerticalSplitPanel.setSplitPosition("90%");
            simplePanel.add(rulesVerticalSplitPanel);

            // set style of popup message label
            treePopupMessagePanel.setStyleName("businessRulesTree_popup");
            treePopupMessage.setWidget(treePopupMessagePanel);
            treePopupMessagePanel.add(treePopupMessageHTML);

            // add selection event listener to rulesTree widget
            rulesVersionsTable.addSelectionListener(new ModelTableSelectionListener<RulesVersionInfo>() {

                // user click on rules tree to select or deselect rules
                public void onSelect(RulesVersionInfo modelObject) {
                    System.out.println("rulesVersionsTable listener.onSelect()");
                    // populate rule based on the selected rule version
                    // clears out the previous rule display
                    if (modelObject == null) {
                        loadEmptyRule();
                        System.out.println("DEBUG: no selection....");
                        return;
                    }

                    if (updateCopyOfDisplayedRule(false) == false) {                    
                        return;
                    }                    
                                   
                    System.out.println("rule comparison: " + displayedRule + ", " + displayedRuleInitialCopy);
                    if ((displayedRule != null) && (displayedRuleInitialCopy != null))
                    {
                        String comparisonResult = areRulesTheSame(displayedRuleInitialCopy, displayedRule);
                        System.out.println("COMPARISON RESULT:  " + comparisonResult);
                        //remove comment once we resolve the GUI synchronization issues
                        //if (comparisonResult.isEmpty() == false) GuiUtil.showUserDialog("Rule's " + comparisonResult + " has changed.");                                                                    
                    }
                    
                    // populate fields based on a rule selected by user
                    displayedRuleInfo = modelObject;

                    DevelopersGuiService.Util.getInstance().fetchDetailedBusinessRuleInfo(modelObject.getBusinessRuleId(), new AsyncCallback<BusinessRuleInfoDTO>() {

                        public void onFailure(Throwable caught) {
                            // just re-throw it and let the uncaught exception handler deal with it
                            Window.alert(caught.getMessage());
                            // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                        }

                        public void onSuccess(BusinessRuleInfoDTO ruleInfo) {
                            loadExistingRule(ruleInfo);
                        }
                    });
                    copiedVersionIndicator.setText("");
                }
            });

            // TODO add selection event listener to rulesTree widget
            rulesTree.addSelectionListener(new ModelTableSelectionListener<RulesHierarchyInfo>() {

                // user click on rules tree to select or deselect rules
                public void onSelect(RulesHierarchyInfo modelObject) {
                    System.out.println("rulesTree listener.onSelect()");
                    // populate ruleVersions based on the rule version group selected
                    // clears out the previous rule display
                    // TODO are you sure? esp. if user changed something - your changes will be lost
                    if (modelObject == null) {
                        return;
                    }
                    
                    if (displayedRule != null) {
                        if (updateCopyOfDisplayedRule(false) == false) {                    
                            return;
                        }
                    }                    
                    
                    if ((displayedRule != null) && (displayedRuleInitialCopy != null))
                    {
                        String comparisonResult = areRulesTheSame(displayedRuleInitialCopy, displayedRule);
                        System.out.println("COMPARISON RESULT:  " + comparisonResult);
                        //remove comment once we resolve the GUI synchronization issues
                        //if (comparisonResult.isEmpty() == false) GuiUtil.showUserDialog("Rule's " + comparisonResult + " has changed.");                                                                    
                    }
                    
                    loadEmptyRule();
                    loadVersionGroup(modelObject);
                    if (displayedRuleInfo == null &&
                            modelObject != null &&
                            modelObject.getVersions() != null &&
                            modelObject.getVersions().size() == 1) {
                        rulesVersionsTable.select(modelObject.getVersions().get(0));
                    }
                    
                    copiedVersionIndicator.setText("");
                    }
            });

            rulesTree.addMouseOverListener(new TreeMouseOverListener<RulesHierarchyInfo>() {
                public void onTreeItemMouseOver(RulesHierarchyInfo item, int x, int y) {
                    System.out.println("mouse overed item");
                    int numVersions = 0;
                    List<RulesVersionInfo> activeVersions = item.getActiveVersions();
                    StringBuilder message = new StringBuilder();
                    if (item != null && item.getVersions() != null) {
                        numVersions = item.getVersions().size();
                    }
                    
                    message.append("# of versions: ").append(Integer.toString(numVersions)).append("<br/>");
                    
                    if ((activeVersions != null) && !activeVersions.isEmpty()) {
                        message.append("Active version exists").append("<br/>");
                        message.append("Expiry Dates are:").append("<br/>");
                        message.append("<table>");
                        
                        for (RulesVersionInfo activeVersion : activeVersions) {
                            java.util.Date expiryDate = (activeVersion.getExpirationDate() == null)? 
                                    GuiUtil.OMEGA_DATE : activeVersion.getExpirationDate();
                            String expiryDateString = dateFormatter.format(expiryDate);
                            message.append("<tr>");
                            message.append("<td>&nbsp;&nbsp;&nbsp;<td/>");
                            message.append("<td><font class=\"businessRulesTree_popupmessage\">").append(expiryDateString).append("<font/><td/>");
                            message.append("</tr>");
                        }
                        message.append("</table>");
                    } else {
                        message.append("No Active Version").append("<br/>");
                    }
                    
                    treePopupMessageHTML.setHTML(message.toString());
                    treePopupMessage.setPopupPosition(x, y + 30);
                    treePopupMessage.show();
                }

                public void onMouseOut() {
                    treePopupMessage.hide();
                }
            });

            /****************************************************************************************************************
             * listeners for rule RULE VERSIONING buttons
             ***************************************************************************************************************/

            submitRuleButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    System.out.println("submitRuleButton listener.onClick()");

                    // 1) update the displayed rule copy with data entered
                    if (updateCopyOfDisplayedRule(true) == false) {
                        return;
                    }

                    // 2) validate draft before submitting - we need at least agenda type and business rule type
                    if (isDisplayedRuleValid("Cannot submit rule") == false) {
                        return;
                    }

                    // make sure rule has draft status
                    displayedRule.setState(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
                    displayedRuleInfo.setStatus(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());

                    // 3) create new draft rule
                    DevelopersGuiService.Util.getInstance().createBusinessRule(displayedRule, new AsyncCallback<BusinessRuleInfoDTO>() {
                        public void onFailure(Throwable caught) {
                            // just re-throw it and let the uncaught exception handler deal with it
                            Window.alert(caught.getMessage());
                            // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);

                            // now revert the rule status back
                            displayedRule.setState(STATUS_NOT_STORED_IN_DATABASE);
                            displayedRuleInfo.setStatus(STATUS_NOT_STORED_IN_DATABASE);
                        }

                        public void onSuccess(BusinessRuleInfoDTO newBusinessRule) {
                            String newRuleID = newBusinessRule.getId();
                            System.out.println("Created new rule with ID: " + newRuleID);
                            System.out.println("Created new rule with compiled ID: " + newBusinessRule.getCompiledId());

                            displayedRule = newBusinessRule;
                            //displayedRuleInitialCopy = createRuleCopy(newBusinessRule);

                            // update the model
                            displayedRuleInfo = new RulesVersionInfo();
                            displayedRuleInfo.setValuesFromDTO(newBusinessRule);
                            String agendaType = rulesTree.getSelection().getGroupAgendaType();
                            displayedRuleInfo.setAgendaType(agendaType);

                            // fire the event and the updated model object to the parent controller
                            RulesHierarchyInfo targetGroup = null;
                            targetGroup = new RulesHierarchyInfo();
                            targetGroup.add(displayedRuleInfo);
                            displayedRulesGroup.add(targetGroup);
                            loadVersionGroup(targetGroup);
                            // might not be necessary to loop through ALL rule groups
                            // since the submitted rule is always in a new group (i.e. has a
                            // new originalId
                            // List<RulesHierarchyInfo> treeItems = rulesTree.getItems();
                            // for (RulesHierarchyInfo treeItem : treeItems) {
                            // if (treeItem.getUniqueId() != null &&
                            // treeItem.getUniqueId()
                            // .equals(displayedRuleInfo.getBusinessRuleOriginalId())) {
                            // targetGroup = treeItem;
                            // }
                            // }
                            // if (targetGroup == null) {
                            // // create new group and add to tree if the group does not already exist
                            // // select the new group and new version
                            // targetGroup = new RulesHierarchyInfo();
                            // targetGroup.add(displayedRuleInfo);
                            // displayedRulesGroup.add(targetGroup);
                            // loadVersionGroup(targetGroup);
                            // } else {
                            // targetGroup.add(displayedRuleInfo);
                            // loadVersionGroup(targetGroup);
                            // }
                            rulesVersionsTable.select(displayedRuleInfo);
                          //ID has changed so update the initial copy so we don't trigger false alarm of user 'changing' this rule
                            //displayedRuleInitialCopy.setId(displayedRule.getId());  
                            loadExistingRule(displayedRule);
                            GuiUtil.showUserDialog("New rule submitted.");
                        }
                    });
                }
            });

            updateRuleButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    System.out.println("updateRuleButton listener.onClick()");

                    // 1) update the draft with data entered
                    if (updateCopyOfDisplayedRule(true) == false) {
                        return;
                    }

                    // 2) validate draft before update
                    if (isDisplayedRuleValid("Cannot update rule.") == false) {
                        return;
                    }

                    DevelopersGuiService.Util.getInstance().updateBusinessRule(displayedRule.getId(), displayedRule, new AsyncCallback<BusinessRuleInfoDTO>() {
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        public void onSuccess(BusinessRuleInfoDTO updatedBusRule) {
                            // update the model
                            displayedRule = updatedBusRule;
                            //displayedRuleInitialCopy = createRuleCopy(updatedBusRule);
                            displayedRuleInfo.setValuesFromDTO(updatedBusRule);
                            String agendaType = rulesTree.getSelection().getGroupAgendaType();
                            displayedRuleInfo.setAgendaType(agendaType);

                            displayedRulesGroup.update(rulesTree.getSelection());
                            loadVersionGroup(rulesTree.getSelection());
                            loadExistingRule(displayedRule);
                            GuiUtil.showUserDialog("Rule updated.");
                        }
                    });
                }
            });

            activateRuleButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    System.out.println("activateRuleButton listener.onClick()");

                    // 1) update the active rule with data entered
                    if (updateCopyOfDisplayedRule(true) == false) {
                        return;
                    }

                    // 2) validate that the rule entered/changed is correct
                    if (isDisplayedRuleValid("Cannot activate rule.") == false) {
                        return;
                    }

                    DevelopersGuiService.Util.getInstance().updateBusinessRuleState(displayedRule.getId(), BusinessRuleStatus.ACTIVE.toString(), new AsyncCallback<BusinessRuleInfoDTO>() {
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        public void onSuccess(BusinessRuleInfoDTO updatedBusRule) {
                            displayedRule = updatedBusRule;
                            //displayedRuleInitialCopy = createRuleCopy(updatedBusRule);
                            displayedRuleInfo.setValuesFromDTO(updatedBusRule);
                            String agendaType = rulesTree.getSelection().getGroupAgendaType();
                            displayedRuleInfo.setAgendaType(agendaType);

                            displayedRulesGroup.update(rulesTree.getSelection());
                            loadVersionGroup(rulesTree.getSelection());
                            loadExistingRule(displayedRule);

                            GuiUtil.showUserDialog("Rule Activated");
                        }
                    });
                }
            });

            createNewVersionButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    System.out.println("createNewVersionButton listener.onClick()");

                    // 1) validate that the rule entered/changed data is correct
                    if (isDisplayedRuleValid("Cannot make a new rule version.") == false) {
                        return;
                    }

                    // 2) update the active rule with data entered
                    if (updateCopyOfDisplayedRule(true) == false) {
                        return;
                    }

                    // 3) create a new rule version as a copy of existing rule
                    BusinessRuleInfoDTO newRuleVersion = createRuleCopy(displayedRule);
                    initializeRuleCopy(newRuleVersion, newRuleVersion.getName());
                    newRuleVersion.setState(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
                    newRuleVersion.setOriginalRuleId(displayedRule.getOriginalRuleId());

                    // 3) create new version of this rule
                    DevelopersGuiService.Util.getInstance().createBusinessRule(newRuleVersion, new AsyncCallback<BusinessRuleInfoDTO>() {
                        public void onFailure(Throwable caught) {
                            // just re-throw it and let the uncaught exception handler deal with it
                            Window.alert(caught.getMessage());
                            // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                        }

                        public void onSuccess(BusinessRuleInfoDTO newRuleVersion) {
                            displayedRule = newRuleVersion;
                            //displayedRuleInitialCopy = createRuleCopy(newRuleVersion);
                            displayedRuleInfo = new RulesVersionInfo();
                            displayedRuleInfo.setValuesFromDTO(newRuleVersion);
                            String agendaType = rulesTree.getSelection().getGroupAgendaType();
                            displayedRuleInfo.setAgendaType(agendaType);
                            RulesHierarchyInfo selectedGroup = rulesTree.getSelection();
                            selectedGroup.add(displayedRuleInfo);

                            displayedRulesGroup.update(rulesTree.getSelection());
                            loadVersionGroup(rulesTree.getSelection());
                            rulesVersionsTable.select(displayedRuleInfo);
                            loadExistingRule(displayedRule);
                            GuiUtil.showUserDialog("New Rule Version Created.");
                        }
                    });
                }
            });

            retireRuleButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    System.out.println("retireRuleButton listener.onClick()");

                    DevelopersGuiService.Util.getInstance().updateBusinessRuleState(displayedRule.getId(), BusinessRuleStatus.RETIRED.toString(), new AsyncCallback<BusinessRuleInfoDTO>() {
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        public void onSuccess(BusinessRuleInfoDTO updatedBusRule) {
                            displayedRule = updatedBusRule;
                            //displayedRuleInitialCopy = createRuleCopy(updatedBusRule);
                            displayedRuleInfo.setValuesFromDTO(updatedBusRule);
                            String agendaType = rulesTree.getSelection().getGroupAgendaType();
                            displayedRuleInfo.setAgendaType(agendaType);

                            displayedRulesGroup.update(rulesTree.getSelection());
                            loadVersionGroup(rulesTree.getSelection());
                            loadExistingRule(displayedRule);

                            GuiUtil.showUserDialog("Rule retired.");
                        }
                    });
                }
            });

            copyRuleButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    System.out.println("copyRuleButton listener.onClick()");
                    // keep original rule values except rule id, original rule id, compiled id and name
                    initializeRuleCopy(displayedRule, "COPY OF " + displayedRule.getName());
                    //displayedRuleInitialCopy = createRuleCopy(displayedRule);
                    ruleStatus.setText(STATUS_NOT_STORED_IN_DATABASE);
                    loadExistingRule(displayedRule);
                    GuiUtil.showUserDialog("Rule copied.");
                    copiedVersionIndicator.setText("Copied Version.");
                }
            });

            // rule in order to create a new rule or void changes to the existing rule
            cancelButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    System.out.println("cancelButton listener.onClick()");
                    // TODO "Are you sure?' dialog -> see ui common package for widget
                    loadEmptyRule();
                }
            });

            /****************************************************************************************************************
             * listeners for proposition ADD, UPDATE, REMOVE buttons
             ***************************************************************************************************************/
            propositionsListBox.addChangeListener(new ChangeListener() {
                public void onChange(Widget sender) {
                    System.out.println("propositionsListBox listener.onChange()");
                    ListBox box = ((ListBox) sender);
                    int selectedIndex = box.getSelectedIndex();
                    if (selectedIndex == -1) {
                        return;
                    }

                    if (definedPropositions.size() > 1) {
                        removePropButton.setEnabled(true);
                    }

                    // extra check
                    RulePropositionDTO selectedRuleElement = definedPropositions.get(new Integer(box.getValue(selectedIndex))).getBusinessRuleProposition();
                    if (selectedRuleElement == null) {
                        System.out.println("Selected elemetn NULL?");
                        return;
                    }
                    // first we need to update the currently edited proposition before we switch to new one (update by
                    // default)
                    updateSelectedPropositionDTO(selectedPropListBoxIndex);

                    // second we load the selected proposition details
                    selectedPropListBoxIndex = selectedIndex;
                    populatePropositionFormFields(selectedRuleElement);
                }
            });

            addPropButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    System.out.println("addPropButton listener.onClick()");

                    // first we need to update the currently edited proposition (update by default)
                    updateSelectedPropositionDTO(propositionsListBox.getSelectedIndex());

                    int newPropIx = addEmptyPropositionToDTOList();

                    // reorganize propositions
                    updatePropositionListBoxAndFormFields(newPropIx);
                }
            });

            removePropButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    System.out.println("removePropButton listener.onClick()");

                    if (definedPropositions.size() < 2) {
                        removePropButton.setEnabled(false);
                        GuiUtil.showUserDialog("Cannot remove last proposition.");
                    }

                    // TODO are you sure? dialog
                    int selectedProp = propositionsListBox.getSelectedIndex();
                    if ((selectedProp != -1)) {
                        // remove the proposition
                        definedPropositions.remove(Integer.parseInt(propositionsListBox.getValue(selectedProp)));

                        // refresh the form
                        updatePropositionListBoxAndFormFields(-1);
                        // updatePropButton.setEnabled(false);
                        removePropButton.setEnabled(false);
                    }
                }
            });

            /*
             * cancelPropButton.addClickListener(new ClickListener() { public void onClick(final Widget sender) {
             * //updatePropButton.setEnabled(false); //removePropButton.setEnabled(false);
             * updatePropositionListBoxAndDetails(propositionsListBox.getSelectedIndex()); } });
             */

            propNameTextBox.addFocusListener(new FocusListener() {
                public void onFocus(final Widget sender) {}

                public void onLostFocus(final Widget sender) {
                    String propAbreviation = "P" + propositionsListBox.getValue(selectedPropListBoxIndex) + ":  ";
                    propositionsListBox.setItemText(selectedPropListBoxIndex, propAbreviation + propNameTextBox.getText());
                }
            });

            /****************************************************************************************************************
             * listeners for YVF drop downs, COMPOSITION and COMPLETE TEXT elements of a rule
             ***************************************************************************************************************/

            // Yield Value Function list box
            yvfListBox.addChangeListener(new ChangeListener() {
                public void onChange(final Widget sender) {
                    // TODO: are you sure? dialog
                    clearYVFFactFields();
                    initializeYVFFactFormFields(yvfListBox.getValue(yvfListBox.getSelectedIndex()));
                }
            });

            validateRuleButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    // first update the DTO of the proposition being edited
                    updateSelectedPropositionDTO(selectedPropListBoxIndex);
                    // check whether the current composition is valid
                    if (validateRuleComposition() == false) {
                        return; // invalid proposition being edited
                    }
                }
            });

            propCompositionTextArea.addFocusListener(new FocusListener() {
                public void onFocus(final Widget sender) {}

                public void onLostFocus(final Widget sender) {

                    // first update the DTO of the proposition being edited
                    updateSelectedPropositionDTO(selectedPropListBoxIndex);

                    // check whether the current composition is valid
                    if (validateRuleComposition() == false) {
                        return; // invalid proposition being edited
                    }

                    // update Rule Overview text as well
                    completeRuleTextArea.setText(GuiUtil.assembleRuleFromComposition(propCompositionTextArea.getText(), definedPropositions));
                }
            });

            /****************************************************************************************************************
             * other listeners
             ***************************************************************************************************************/

            rulesFormTabs.addTabListener(new TabListener() {

                public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
                    return true;
                }

                public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
                    // show rule facts for test tab
                    if (tabIndex == 4) { // TODO hard coded number

                        // 1) update the displayed rule copy with data entered
                        if (updateCopyOfDisplayedRule(true) == false) {
                            return;
                        }

                        // now validate each proposition
                        if (validateRuleComposition() == false) {
                            GuiUtil.showUserDialog("ERROR: Invalid rule composition.");
                            rulesFormTabs.selectTab(1);
                        } else {
                            displayTestPageRuleFacts();
                        }
                    }
                }
            });

            testRuleButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {

                    definitionFactValuesForTest.clear();
                    executionFactValuesForTest.clear();

                    // first, retrieve all definition time fact values from test tab, assuming empty fields means use current
                    // rule values
                    for (TextArea widget : testDefinitionTimeFactsWidgets) {
                        if (widget.getText().trim().isEmpty() == false) {
                            definitionFactValuesForTest.put(widget.getName(), widget.getText());
                            System.out.println("Adding definition time fact:" + widget.getName() + " with value " + widget.getText());
                        }
                    }

                    // next, retrieve dynamic facts i.e. go through all propositions and update dynamic facts values
                    for (TextArea widget : testExecutionTimeFactsWidgets) {
                        if ((widget.getText().trim().isEmpty() == false) && (executionFactValuesForTest.containsKey(widget.getName()) == false)) {
                            executionFactValuesForTest.put(widget.getName(), widget.getText());
                            System.out.println("Adding execution time fact:" + widget.getName() + " with value " + widget.getText());
                        }
                    }

                    DevelopersGuiService.Util.getInstance().executeBusinessRuleTest(displayedRule, definitionFactValuesForTest, executionFactValuesForTest, new AsyncCallback<ExecutionResultDTO>() {
                        public void onFailure(Throwable caught) {
                            // just re-throw it and let the uncaught exception handler deal with it
                            Window.alert(caught.getMessage());
                            // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                        }

                        public void onSuccess(ExecutionResultDTO executionResult) {
                            StringBuffer executionLog = new StringBuffer();

                            StringBuffer reportText = new StringBuffer();

                            List<PropositionReportDTO> propositionReports = executionResult.getReport().getPropositionReports();
                            if (propositionReports != null) {
                                for (PropositionReportDTO report : propositionReports) {
                                    reportText.append("\nProposition Name: " + report.getPropositionName() + "\n");
                                    System.out.println("PROPOSITION TYPE: " + report.getPropositionType());
                                    reportText.append("Proposition Type: " + report.getPropositionType() + "\n");
                                    if ((report.isSuccessful())) {
                                        reportText.append("Success Message: " + report.getMessage() + "\n");
                                    } else {
                                        reportText.append("Failure Message: " + report.getMessage() + "\n");
                                    }

                                    if (report.getCriteriaResult() != null) {
                                        reportText.append("\nCriteria: " + report.getCriteriaResult() + "\n");
                                    }
                                    if (report.getFactResult() != null) {
                                        reportText.append("\nFact: " + report.getFactResult() + "\n");
                                    }

                                    reportText.append("\nProposition result: " + report.getPropositionResult().getResultList());

                                    reportText.append("\n------------------------------------------------------------------------" + "\n");
                                }
                            }

                            if (executionResult.isExecutionSuccessful()) {
                                GuiUtil.showUserDialog("Rule executed.");
                                if (executionResult.getReport().isSuccessful()) {
                                    executionLog.append("\nSUCCESS: " + executionResult.getReport().getSuccessMessage());
                                } else {
                                    executionLog.append("\nFAILURE: " + executionResult.getReport().getFailureMessage());
                                }
                                reportText.append("\n\n\n   DETAIL EXECUTION REPORT\n\n" + executionResult.getExecutionLog());
                            } else {
                                GuiUtil.showUserDialog("ERROR: Failed to execute rule.");
                                executionLog.append("\nFailed to execute rule.");
                                executionLog.append("\nERROR: " + executionResult.getErrorMessage());
                            }

                            testReport.setText(reportText.toString());
                            testResult.setText(executionLog.toString());
                        }
                    });
                }
            });

            // agenda type can be changed ONLY if rule is in STATE_NOT_IN_DATABASE
            agendaTypesListBox.addChangeListener(new ChangeListener() {
                public void onChange(final Widget sender) {
                    // changing agenda type will empty the business rule type and related Anchor Key
                    if (displayedRuleInfo == null) {
                        displayedRuleInfo = new RulesVersionInfo();
                    }

                    displayedRuleInfo.setAgendaType(GuiUtil.getListBoxSelectedValue(agendaTypesListBox).trim());
                    displayedRule.setType("");
                    displayedRule.setAnchorTypeKey("");
                    ruleAnchorType.setText("");
                    populateAgendaAndBusinessRuleTypesListBox();
                }
            });

            // business rule type can be changed ONLY if rule is in STATE_NOT_IN_DATABASE
            businessRuleTypesListBox.addChangeListener(new ChangeListener() {
                public void onChange(final Widget sender) {
                    if (businessRuleTypesListBox.getSelectedIndex() == 0) {
                        // we need to clear related Anchor Type
                        displayedRule.setAnchorTypeKey("");
                        ruleAnchorType.setText("");
                        definedFactTypesList = null;
                    } else {
                        displayedRule.setType(GuiUtil.getListBoxSelectedValue(businessRuleTypesListBox).trim());
                        ruleAnchorType.setText(AnchorTypeKey.KUALI_COURSE.name()); // TODO lookup based on business rule type
                        displayedRule.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.name());
                        retrieveFactTypes();
                    }
                }
            });
        }
    }

    private void loadEmptyRule() {
        displayedRule = createEmptyBusinessRule();
        displayedRuleInitialCopy = createRuleCopy(displayedRule); 
        clearRuleForms();

        // set an empty proposition
        ruleComposition = new StringBuffer();
        definedPropositions.clear();
        int newPropIx = addEmptyPropositionToDTOList();
        updatePropositionListBoxAndFormFields(newPropIx);

        displayedRuleInfo = null;
        definedFactTypesList = null;
        setRuleStatus(STATUS_NOT_STORED_IN_DATABASE);
        populateAgendaAndBusinessRuleTypesListBox();
        updateRulesFormButtons(displayedRule.getState());
        rulesTree.unSelect(); // clear current rule tree selection
    }

    private void loadExistingRule(BusinessRuleInfoDTO ruleInfo) {

        //seconds in rule effective start and end time set to 00 as they are insignificant
        ruleInfo.setEffectiveDate(dateFormatter.parse(dateFormatter.format(ruleInfo.getEffectiveDate())));
        ruleInfo.setExpirationDate(dateFormatter.parse(dateFormatter.format(ruleInfo.getExpirationDate())));        
        
        displayedRule = ruleInfo;

        // store individual propositions in a temporary list & set Rule Composition text
        int propCount = 1;
        ruleComposition = new StringBuffer();
        definedPropositions = new HashMap<Integer, RuleElementDTO>();

        int ix = 1;
        for (RuleElementDTO elem : ruleInfo.getBusinessRuleElementList()) {
            elem.setOrdinalPosition(ix++);  //enforce ordinal position
            if (elem.getBusinessRuleElemnetTypeKey().equals(RuleElementType.PROPOSITION.getName())) {
                definedPropositions.put(propCount, elem);
                ruleComposition.append("P" + (propCount++) + " ");
            } else {
                ruleComposition.append(elem.getBusinessRuleElemnetTypeKey() + " ");
            }
        }
        
        displayedRuleInitialCopy = createRuleCopy(ruleInfo);

        updateRulesFormButtons(ruleInfo.getState());
        
        displayActiveRule();
    }

    private void initializeRuleCopy(BusinessRuleInfoDTO rule, String newRuleName) {
        rule.setId("");
        rule.setOriginalRuleId("");
        rule.setCompiledId("");
        rule.setName(newRuleName);
        rule.setState(STATUS_NOT_STORED_IN_DATABASE);
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateTime(null);
        metaInfo.setCreateID("");
        metaInfo.setCreateComment("");
        metaInfo.setUpdateTime(null);
        metaInfo.setUpdateID("");
        metaInfo.setUpdateComment("");
        rule.setMetaInfo(metaInfo);
        updateRulesFormButtons(displayedRule.getState());
    }

    private void setRuleStatus(String status) {
        displayedRule.setState(status);
        ruleStatus.setText(status);

        if (ruleStatus.getText().equals(STATUS_NOT_STORED_IN_DATABASE)) {
            ruleStatus.setStylePrimaryName("status-empty");
        } else if (ruleStatus.getText().equalsIgnoreCase(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString())) {
            ruleStatus.setStylePrimaryName("status-draft");
        } else if (ruleStatus.getText().equalsIgnoreCase(BusinessRuleStatus.ACTIVE.toString())) {
            ruleStatus.setStylePrimaryName("status-active");
        } else if (ruleStatus.getText().equalsIgnoreCase(BusinessRuleStatus.RETIRED.toString())) {
            ruleStatus.setStylePrimaryName("status-retired");
        } else {
            ruleStatus.setStylePrimaryName("status-empty"); // TODO warning
        }
    }

    private void updateRulesFormButtons(String ruleStatus) {

        if (ruleStatus.equalsIgnoreCase(STATUS_NOT_STORED_IN_DATABASE)) {
            submitRuleButton.setEnabled(true);
            updateRuleButton.setEnabled(false);
            activateRuleButton.setEnabled(false);
            createNewVersionButton.setEnabled(false);
            retireRuleButton.setEnabled(false);
            copyRuleButton.setEnabled(false);
            cancelButton.setEnabled(true);
            return;
        } else if (ruleStatus.equalsIgnoreCase(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString())) {
            submitRuleButton.setEnabled(false);
            updateRuleButton.setEnabled(true);
            activateRuleButton.setEnabled(true);
            createNewVersionButton.setEnabled(false);
            retireRuleButton.setEnabled(false);
            copyRuleButton.setEnabled(true);
            cancelButton.setEnabled(true);
        } else if (ruleStatus.equalsIgnoreCase(BusinessRuleStatus.ACTIVE.toString())) {
            submitRuleButton.setEnabled(false);
            updateRuleButton.setEnabled(false);
            activateRuleButton.setEnabled(false);
            createNewVersionButton.setEnabled(true);
            retireRuleButton.setEnabled(true);
            copyRuleButton.setEnabled(true);
            cancelButton.setEnabled(true);
        } else if (ruleStatus.equalsIgnoreCase(BusinessRuleStatus.RETIRED.toString())) {
            submitRuleButton.setEnabled(false);
            updateRuleButton.setEnabled(false);
            activateRuleButton.setEnabled(false);
            createNewVersionButton.setEnabled(false);
            retireRuleButton.setEnabled(false);
            copyRuleButton.setEnabled(true);
            cancelButton.setEnabled(true);
        } else {
            // TODO error message
        }
    }

    // populate displayedRule object with data from rule forms
    private boolean updateCopyOfDisplayedRule(boolean validateRule) {

        // set rule basic info
        displayedRule.setName(nameTextBox.getText());
        displayedRule.setDesc(descriptionTextArea.getText());
        displayedRule.setSuccessMessage(successMessageTextArea.getText());
        displayedRule.setFailureMessage(failureMessageTextArea.getText());
        displayedRule.setType(GuiUtil.getListBoxSelectedValue(businessRuleTypesListBox).trim());
        displayedRule.setAnchorTypeKey(ruleAnchorType.getText());
        displayedRule.setAnchorValue(ruleAnchorTextBox.getText());

        // update DTO of the currently edited proposition
        updateSelectedPropositionDTO(propositionsListBox.getSelectedIndex());

        // now validate each proposition
        if (validateRule && (validateRuleComposition() == false)) {
            GuiUtil.showUserDialog("ERROR: Invalid rule composition.");
            return false;
        }

        // retrieve propositions and proposition composition
        ruleComposition = new StringBuffer(propCompositionTextArea.getText());
        if (ruleComposition != null) {
            List<RuleElementDTO> elemList;
            try {
                elemList = GuiUtil.createRuleElementsFromComposition(ruleComposition.toString(), definedPropositions);
            } catch (IllegalRuleFormatException e) {
                GuiUtil.showUserDialog("ERROR: Failed to process defined propositions." + e.getMessage());
                return false;
            }
            displayedRule.setBusinessRuleElementList(elemList);            
        }

        // set authoring info
        displayedRule.setEffectiveDate((effectiveDateTextBox.getText().isEmpty() ? GuiUtil.ALPHA_DATE : dateFormatter.parse(effectiveDateTextBox.getText())));
        displayedRule.setExpirationDate((expiryDateTextBox.getText().isEmpty() ? GuiUtil.OMEGA_DATE : dateFormatter.parse(expiryDateTextBox.getText())));

        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateTime(displayedRule.getMetaInfo().getCreateTime());
        metaInfo.setCreateID(displayedRule.getMetaInfo().getCreateID());
        metaInfo.setCreateComment(createCommentTextBox.getText());  
        metaInfo.setUpdateTime(displayedRule.getMetaInfo().getUpdateTime());
        metaInfo.setUpdateID(displayedRule.getMetaInfo().getUpdateID());
        metaInfo.setUpdateComment(updateCommentTextBox.getText());
        displayedRule.setMetaInfo(metaInfo);

        return true;
    }

    private void clearVersionsDisplay() {
        rulesVersionsTable.clear();
        activeTimeGapsTextArea.setText("");
    }

    private void loadVersionGroup(RulesHierarchyInfo rulesGroup) {
        // only attempt to load the selected group if user has actually selected a group
        clearVersionsDisplay();
        if (rulesGroup == null) {
            return;
        }
        displayedRulesGroup.update(rulesGroup);
        List<RulesVersionInfo> versions = rulesGroup.getVersions();
        for (RulesVersionInfo version : versions) {
            rulesVersionsTable.add(version);
        }
        List<DateRange> activeTimeGaps = getActiveTimeGaps(rulesGroup);
        if (activeTimeGaps != null) {
            StringBuilder message = new StringBuilder("");
            for (DateRange activeTimeGap : activeTimeGaps) {
                message.append("From ");
                if (activeTimeGap.getStartDate().compareTo(GuiUtil.ALPHA_DATE) == 0) {
                    message.append("beginning");
                } else {
                    message.append(dateFormatter.format(activeTimeGap.getStartDate()));
                }
                message.append(" to ");
                if (activeTimeGap.getEndDate().compareTo(GuiUtil.OMEGA_DATE) == 0) {
                    message.append("end");
                } else {
                    message.append(dateFormatter.format(activeTimeGap.getEndDate()));
                }
                message.append("\n");
            }
            activeTimeGapsTextArea.setText(message.toString());
        }
    }

    /******************************************************************************************************************
     * VALIDATIONS
     *******************************************************************************************************************/

    // TODO validate all fields of the rule or should the validate happen when given field is changed?
    private boolean validateRuleFields() {
        StringBuilder messages = new StringBuilder();
        boolean valid = true;
        boolean fieldValid = true;

        // the if statement after each field validation
        // updates the valid status if valid is still true.
        // In effect valid status will be false if one or more
        // of the fields is invalid
      fieldValid = validateField("ruleName", this.nameTextBox.getText(), messages);
        if (valid) {
            valid = fieldValid;
        }
      fieldValid = validateField("ruleDescription", this.descriptionTextArea.getText(), messages);
        if (valid) {
            valid = fieldValid;
        }
      fieldValid = validateField("ruleSuccessMessage", this.successMessageTextArea.getText(), messages);
        if (valid) {
            valid = fieldValid;
        }
      fieldValid = validateField("ruleFailureMessage", this.failureMessageTextArea.getText(), messages);
        if (valid) {
            valid = fieldValid;
        }
      fieldValid = validateField("ruleAnchor", this.ruleAnchorTextBox.getText(), messages);
        if (valid) {
            valid = fieldValid;
        }
        // result = ...
        if (!valid) {
            GuiUtil.showUserDialog(messages.toString());
        }
        return valid;
    }

    private boolean validateField(String fieldName, String value, StringBuilder output) {
        boolean valid = true;
      /*  Validator v = metadata.getFields().get(fieldName).getValidatorInstance();
        ValidationResult vr = v.validate(value, fieldName);
        if (vr.isError()) {
            valid = false;
        }
        if (!vr.isOk()) {
            for (String s : vr.getMessages()) {
                output.append(s);
                output.append("\n");
            }
        } */
        return valid;
    }

    private boolean isDisplayedRuleValid(String message) {

        if (!validateRuleFields()) {
            return false;
        }

        if (displayedRuleInfo == null) {
            GuiUtil.showUserDialog(message + "\n" + "Agenda Type cannot be empty.");
            return false;
        }

        // all rules need Agenda Type, Business Rule Type
        if ((displayedRuleInfo.getAgendaType() == null) || displayedRuleInfo.getAgendaType().equals(EMPTY_LIST_BOX_ITEM) || displayedRuleInfo.getAgendaType().isEmpty()) {
            GuiUtil.showUserDialog(message + "\n" + "Agenda Type cannot be empty.");
            return false;
        }

        if ((displayedRule.getType() == null) || displayedRule.getType().equals(EMPTY_LIST_BOX_ITEM) || displayedRule.getType().isEmpty()) {
            GuiUtil.showUserDialog(message + "\n" + "Business Rule Type cannot be empty.");
            return false;
        }

        ruleComposition = new StringBuffer(propCompositionTextArea.getText());

        // rule should have at least one proposition
        if ((definedPropositions == null) || (definedPropositions.size() == 0)) {
            GuiUtil.showUserDialog(message + "\n" + "Rule has to have at least one Proposition.");
            return false;
        }

        // at least one proposition needs to be used
        if (ruleComposition.toString().isEmpty()) {
            GuiUtil.showUserDialog(message + "\n" + "Rule Proposition has to be part of rule composition.");
            return false;
        }

        return true;
    }

    private boolean isPropositionValid(RulePropositionDTO prop) {
        StringBuilder messages = new StringBuilder();

        if (!validateField("propositionName", propNameTextBox.getText(), messages)) {
            GuiUtil.showUserDialog(messages.toString());
        }
        if (!validateField("propositionDescription", propDescTextBox.getText(), messages)) {
            GuiUtil.showUserDialog(messages.toString());
        }

        if (prop.getName().trim().isEmpty()) {
            GuiUtil.showUserDialog("ERROR: Proposition needs name.");
            return false;
        }

        // we don't want duplicate names
        // if (propNameTextBox.getText() TODO

        YieldValueFunctionDTO yvf = prop.getLeftHandSide().getYieldValueFunction();
        if (yvf == null) {
            GuiUtil.showUserDialog("ERROR: YVF cannot be empty.");
            return false;
        }

        String yvfType = yvf.getYieldValueFunctionType();
        if ((yvfType.equals(EMPTY_LIST_BOX_ITEM)) || (yvfType.trim().isEmpty())) {
            GuiUtil.showUserDialog("ERROR: YVF cannot be empty.");
            return false;
        }

        if ((prop.getComparisonOperatorTypeKey() == null) || (prop.getComparisonOperatorTypeKey().trim().isEmpty()) || prop.getComparisonOperatorTypeKey().equals(equals(EMPTY_LIST_BOX_ITEM))) {
            GuiUtil.showUserDialog("ERROR: Missing Operator.");
            return false;
        }

        if ((prop.getRightHandSide() == null) || prop.getRightHandSide().getExpectedValue().isEmpty()) {
            GuiUtil.showUserDialog("ERROR: Missing Expected Value.");
            return false;
        }

        // verify we are not missing first Fact parameters
        List<FactStructureDTO> factStructureList = yvf.getFactStructureList();

        // 1. Fact Type cannot be empty
        if ((factStructureList == null) || (factStructureList.size() == 0)) {
            GuiUtil.showUserDialog("ERROR: No Fact Type found.");
            return false;
        }

        FactStructureDTO firstFact = factStructureList.get(0);
        if ((firstFact == null) || (firstFact.getFactTypeKey().trim().isEmpty()) || (firstFact.getFactTypeKey().trim().equals(EMPTY_LIST_BOX_ITEM))) {
            GuiUtil.showUserDialog("ERROR: Missing 1st Fact Type.");
            return false;
        }

        // determine whether we have static or dynamic fact
        if (firstFact.isStaticFact()) {
            if (firstFact.getStaticValue().trim().isEmpty()) {
                GuiUtil.showUserDialog("ERROR: Missing 1st Fact static value.");
                return false;
            }
        } else { // dynamic fact - at least the first one has to be available
            /*
             * not required at this moment... if (yvfFirstFactParamOneTextBox.getText().isEmpty() &&
             * yvfFirstFactParamTwoTextBox.getText().isEmpty()) {
             * GuiUtil.showUserDialog("ERROR: Missing 1st Fact dynamic key."); return false; }
             */
        }

        // if YVF is INTERSECTION, check on the second Fact parameters
        if (yvfType.equals(YieldValueFunctionType.INTERSECTION.name())) {
            // 1. Fact Type cannot be empty
            if (factStructureList.size() == 0) {
                GuiUtil.showUserDialog("ERROR: Missing 2nd Fact Type.");
                return false;
            }

            FactStructureDTO secondFact = factStructureList.get(1);
            if ((secondFact == null) || (secondFact.getFactTypeKey().trim().isEmpty()) || (secondFact.getFactTypeKey().trim().equals(EMPTY_LIST_BOX_ITEM))) {
                GuiUtil.showUserDialog("ERROR: Missing 2nd Fact Type.");
                return false;
            }

            // determine whether we have static or dynamic fact
            if (secondFact.isStaticFact()) {
                if (secondFact.getStaticValue().trim().isEmpty()) {
                    GuiUtil.showUserDialog("ERROR: Missing 2nd Fact static value.");
                    return false;
                }
            } else { // dynamic fact - at least the first one has to be available
                /*
                 * not required at this moment... if (secondFact.getText().isEmpty() &&
                 * yvfFirstFactParamTwoTextBox.getText().isEmpty()) {
                 * GuiUtil.showUserDialog("ERROR: Missing 2nd Fact dynamic key."); return false; }
                 */
            }
        }

        return true;
    }

    private boolean validateRuleComposition() {
        // check that every defined proposition is valid
        int propIx = -1;
        for (Map.Entry<Integer, RuleElementDTO> entry : definedPropositions.entrySet()) {
            propIx++;
            if (isPropositionValid(entry.getValue().getBusinessRuleProposition()) == false) {
                selectedPropListBoxIndex = propIx;
                propositionsListBox.setSelectedIndex(propIx);
                populatePropositionFormFields(entry.getValue().getBusinessRuleProposition());
                return false;
            }
        }

        // now check the proposition composition
        compositionStatusLabel.setText(GuiUtil.validateRuleComposition(propCompositionTextArea.getText(), definedPropositions.keySet()));
        if (compositionStatusLabel.getText().equals(GuiUtil.COMPOSITION_IS_VALID_MESSAGE)) {
            compositionStatusLabel.setStyleName("prop_composition_valid");
            return true;
        }

        compositionStatusLabel.setStyleName("prop_composition_invalid");
        return false;
    }

    /******************************************************************************************************************
     * PROPOSITION
     *******************************************************************************************************************/

    private void updatePropositionListBoxAndFormFields(int selectedPropIx) {
        String propAbreviation;

        // re-sequence the propositions again and update both proposition list and details of selected one
        propositionsListBox.clear();
        int ix = -1;
        for (Map.Entry<Integer, RuleElementDTO> entry : definedPropositions.entrySet()) {
            propAbreviation = "P" + entry.getKey() + ":  ";
            propositionsListBox.addItem(propAbreviation + entry.getValue().getName(), entry.getKey().toString());

            ix++;
            if (ix == selectedPropIx) {
                populatePropositionFormFields(entry.getValue().getBusinessRuleProposition());
            }
        }

        // if we are removing a proposition then clear proposition fields but make sure we have
        // at least one empty proposition in the list of propositions
        if (selectedPropIx == -1) {
            clearPropositionFormFields();
            if (ix == -1) {
                selectedPropIx = addEmptyPropositionToDTOList();
            }
        }

        propositionsListBox.setSelectedIndex(selectedPropIx);
        selectedPropListBoxIndex = selectedPropIx;
    }

    private void populatePropositionFormFields(RulePropositionDTO prop) {
        clearPropositionFormFields();
        propNameTextBox.setText(prop.getName());
        propDescTextBox.setText(prop.getDescription());
        operatorsListBox.setSelectedIndex(GuiUtil.getListBoxIndexByName(operatorsListBox, prop.getComparisonOperatorTypeKey()));
        expectedValueTextBox.setText(prop.getRightHandSide().getExpectedValue());
        populateYVFFormFields(prop.getLeftHandSide().getYieldValueFunction());
    }

    // user selected YVF so show the specific number of facts and make them all static as default
    private void initializeYVFFactFormFields(String yvfType) {
        if ((yvfType == null) || (yvfType.length() == 0) || yvfType.equals(EMPTY_LIST_BOX_ITEM)) {
            clearYVFFactFields();
        } else {
            YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
            yvf.setYieldValueFunctionType(yvfType);
            List<FactStructureDTO> factStructureList = new ArrayList<FactStructureDTO>();
            yvf.setFactStructureList(factStructureList);
            int numberOfYVFFacts = GuiUtil.YieldValueFunctionType.getNumberOfFactsFromName(yvfType);
            for (int i = 0; i < numberOfYVFFacts; i++) {
                FactStructureDTO fact = createEmptyFactSturectureDTO();
                factStructureList.add(fact);
                fact.setFactTypeKey("fact.static_key");
                fact.setStaticFact(true);
                fact.setStaticValue("");
                fact.setStaticValueDataType(GuiUtil.YieldValueFunctionType.getValueDataTypeFromSymbol(GuiUtil.getYVFSymbol(yvfType)));
            }
            populateYVFFormFields(yvf);
        }
    }

    // ASSUMPTION: all propositions are stored in VALID state in both GUI and in the Rule Management service
    private void populateYVFFormFields(YieldValueFunctionDTO yvf) {

        String yvfType = (yvf == null ? "" : yvf.getYieldValueFunctionType());

        // do not enable YVF fact parameters if no YVF is selected or we don't know the rule type
        if ((yvf == null) || yvfType.equals(EMPTY_LIST_BOX_ITEM) || (displayedRule == null) || (displayedRule.getType().trim().isEmpty())) {
            return; // no selection of YVF yet
        }

        // 1. set YVF list box according to set value
        GuiUtil.setListBoxSelectionByItemName(yvfListBox, yvfType);

        // given YVF, confirm that proposition has correct number of facts
        int nofFactsEpected = GuiUtil.YieldValueFunctionType.getNumberOfFactsFromName(yvf.getYieldValueFunctionType());
        List<FactStructureDTO> factStructureList = yvf.getFactStructureList();
        if (nofFactsEpected != factStructureList.size()) {
            System.out.println("INVALID STATE: number of facts (" + factStructureList.size() + ") found in YVF entity " + yvf.getYieldValueFunctionType() + " is not equal to number of expected facts (" + nofFactsEpected);
            GuiUtil.showUserDialog("Incorrect number of facts.");
            return;
        }

        // 2. show static or dynamic fact fields based on selection in Fact Type list box if any
        String factID;
        factDetailsPanel.clear();
        factTypes.clear();
        factTypeParams.clear();
        factTypeSelectedValues.clear();

        // for each FACT TYPE, show fact types drop down and list of individual fact parameters
        for (int i = 1; i <= nofFactsEpected; i++) {
            FactStructureDTO fact = factStructureList.get(i - 1);
            factID = Integer.toString(i);

            System.out.println("======================================================");
            System.out.println("Structure id: " + fact.getFactStructureId());
            System.out.println("Fact type key: " + fact.getFactTypeKey());

            // for given FACT of YVF, create a new panel that will show FACT TYPE listbox and fact parameters
            VerticalPanel yvfFactPanel = new VerticalPanel();
            yvfFactPanel.setSpacing(5);
            factDetailsPanel.add(yvfFactPanel);

            ListBox factType = new ListBox();
            factType.setName(factID);
            factType.setEnabled(false); // disable before list loaded
            factType.addChangeListener(new ChangeListener() {
                public void onChange(final Widget sender) {
                    ListBox factTypeListBox = (ListBox) sender;
                    String selectedFactType = GuiUtil.getListBoxSelectedValue(factTypeListBox);
                    String factID = factTypeListBox.getName();
                    VerticalPanel vp = factParamsPanel.get(factID);
                    vp.clear();
                    factTypeParams.remove(factID); // remove defined fact params for this fact

                    List<TextArea> factTypeParamList = new ArrayList<TextArea>();
                    if (selectedFactType.equals(USER_DEFINED_FACT_TYPE_KEY)) {
                        TextArea staticFactValueTextBox = new TextArea();
                        factTypeParamList.add(staticFactValueTextBox);
                        vp.add(GuiUtil.addLabelAndFieldHorizontally(new Label("- static fact value"), staticFactValueTextBox, "250px"));
                    } else {
                        for (FactTypeInfoDTO factTypeInfo : definedFactTypesList) {
                            if (!factTypeInfo.getFactTypeKey().equals(GuiUtil.addFactTypeKeyPrefix(selectedFactType))) {
                                continue;
                            }

                            // go through each key and set it up for given fact type
                            Map<String, FactParamDTO> definedParamValueMap = factTypeInfo.getFactCriteriaTypeInfo().getFactParamMap();
                            for (String key : definedParamValueMap.keySet()) {
                                FactParamDTO factParam = definedParamValueMap.get(key);

                                // execution time keys don't have values
                                if (factParam.getDefTime().equals("KUALI_FACT_EXECUTION_TIME_KEY")) {
                                    vp.add(new Label("- " + GuiUtil.removeFactParamPrefix(factParam.getName())));
                                    continue;
                                }

                                TextArea factParamValue = new TextArea();
                                factParamValue.setName(factParam.getName());
                                factTypeParamList.add(factParamValue);
                                vp.add(GuiUtil.addLabelAndFieldHorizontally(new Label("- " + GuiUtil.removeFactParamPrefix(factParam.getName())), factParamValue, "250px"));
                            }
                        }
                    }
                    factTypeParams.put(factID, factTypeParamList);
                    System.out.println("Setting selected fact type: " + selectedFactType);
                    factTypeSelectedValues.put(factID, selectedFactType);
                }
            });

            // FACTx label
            Label factLabel = new Label("Fact" + factID);
            factLabel.setStyleName("yvf_fields");

            // FACT TYPEs drop down list
            // factType.addItem((fact.isStaticFact() ? USER_DEFINED_FACT_TYPE_KEY :
            // GuiUtil.removeFactTypeKeyPrefix(fact.getFactTypeKey())));
            factTypeSelectedValues.put(factID, fact.isStaticFact() ? USER_DEFINED_FACT_TYPE_KEY : GuiUtil.removeFactTypeKeyPrefix(fact.getFactTypeKey()));
            factTypes.put(factID, factType);
            yvfFactPanel.add(GuiUtil.addLabelAndFieldHorizontally(factLabel, factType, "150px"));

            // create indentation
            VerticalPanel vp = new VerticalPanel();
            factParamsPanel.put(factID, vp);
            HorizontalPanel hp = new HorizontalPanel();
            factDetailsPanel.add(hp);
            SimplePanel space = new SimplePanel();
            space.setWidth("30px");
            hp.add(space);
            hp.add(vp);

            List<TextArea> factTypeParamList = new ArrayList<TextArea>();

            // STATIC FACT goes beside the FACT TYPE drop down
            if (fact.isStaticFact()) {
                System.out.println("Static fact value data type: " + fact.getStaticValueDataType()); // TODO use somehow
                System.out.println("Static fact VALUE: " + fact.getStaticValue());

                TextArea staticFactValueTextBox = new TextArea();
                staticFactValueTextBox.setText(fact.getStaticValue());
                factTypeParamList.add(staticFactValueTextBox);
                vp.add(GuiUtil.addLabelAndFieldHorizontally(new Label("- static fact value"), staticFactValueTextBox, "250px"));
            } else {
                // it is a dynamic fact
                Map<String, String> map = fact.getParamValueMap();
                for (Entry<String, String> entry : map.entrySet()) {
                    if (entry.getValue().equals("KUALI_FACT_EXECUTION_TIME_KEY")) { // execution key
                        // factTypeParamList.add(executionKeyWidget);
                        vp.add(new Label("- " + GuiUtil.removeFactParamPrefix(entry.getKey())));
                        System.out.println("Dynamic Execution Key: " + entry.getKey());
                        continue;
                    }

                    TextArea factParamValue = new TextArea();
                    factParamValue.setName(entry.getKey());
                    factParamValue.setText(entry.getValue());
                    factTypeParamList.add(factParamValue);
                    vp.add(GuiUtil.addLabelAndFieldHorizontally(new Label("- " + GuiUtil.removeFactParamPrefix(entry.getKey())), factParamValue, "250px"));
                }
            }
            factTypeParams.put(factID, factTypeParamList);
        }

        // set fact type list boxes and their values
        retrieveFactTypes();
    }

    private boolean isExecutionTimeKey(String factType, String unknownKey) {
        for (FactTypeInfoDTO factTypeInfo : definedFactTypesList) {
            if (!factTypeInfo.getFactTypeKey().equals(factType)) {
                continue;
            }
            Map<String, FactParamDTO> definedParamValueMap = factTypeInfo.getFactCriteriaTypeInfo().getFactParamMap();
            FactParamDTO factParam = definedParamValueMap.get(unknownKey);
            return factParam.getDefTime().equals("KUALI_FACT_EXECUTION_TIME_KEY");
        }
        return false;
    }

    private void updateSelectedPropositionDTO(int selectedPropListIx) {

        if (selectedPropListIx == -1) {
            return; // no proposition selected
        }

        int origPropKey = new Integer(propositionsListBox.getValue(selectedPropListIx));

        if (definedPropositions.get(origPropKey).getBusinessRuleProposition() == null) {
            return; // nothing to update
        }

        RulePropositionDTO newProp = retrievePropositionDTOFromFormFields();
        
        //preserve the rule element info
        RuleElementDTO origElem = definedPropositions.get(origPropKey);
        RuleElementDTO newElem = new RuleElementDTO();
        newElem.setId(origElem.getId());
        newElem.setName(origElem.getName());
        newElem.setDescription(origElem.getDescription());
        newElem.setOrdinalPosition(origElem.getOrdinalPosition());
        newElem.setBusinessRuleElemnetTypeKey(origElem.getBusinessRuleElemnetTypeKey()); 
        newElem.setBusinessRuleProposition(newProp);
        
        definedPropositions.remove(origPropKey);
        definedPropositions.put(origPropKey, newElem);

        // update Rule Overview text as well
        completeRuleTextArea.setText(GuiUtil.assembleRuleFromComposition(propCompositionTextArea.getText(), definedPropositions));
    }

    private RulePropositionDTO retrievePropositionDTOFromFormFields() {
        // now create a new rule proposition
        RulePropositionDTO prop = new RulePropositionDTO();
        prop.setName(propNameTextBox.getText());
        prop.setDescription(propDescTextBox.getText());
        LeftHandSideDTO leftSide = new LeftHandSideDTO();
        YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();

        String yvfType = GuiUtil.getListBoxSelectedValue(yvfListBox).trim();
        boolean yvfSelected = true;
        if (yvfType.equals(EMPTY_LIST_BOX_ITEM) || (yvfType.length() == 0)) {
            yvfSelected = false;
            yvf = null;
        }

        // for given YVF, retrieve facts
        String processingColumn;
        if (yvfSelected) {
            yvf.setYieldValueFunctionType(yvfListBox.getValue(yvfListBox.getSelectedIndex()));
            if (yvf.getYieldValueFunctionType().equals(YieldValueFunctionType.INTERSECTION.name())) {
                prop.setComparisonDataTypeKey("java.math.BigDecimal");
                processingColumn = "resultColumn.cluId";
            } else { // if ((yvf.equals(YieldValueFunctionType.SUM.name()) || (yvf.equals(YieldValueFunctionType..name()) {
                prop.setComparisonDataTypeKey("java.math.BigDecimal");
                processingColumn = "resultColumn.credit";
            }

            // store set facts in YVF
            List<FactStructureDTO> factStructureList = new ArrayList<FactStructureDTO>();
            yvf.setFactStructureList(factStructureList);

            String factID;
            ListBox factType;
            List<TextArea> factParameters;
            int numberOfYVFFacts = GuiUtil.YieldValueFunctionType.getNumberOfFactsFromName(yvf.getYieldValueFunctionType());
            System.out.println("************** RETRIEVING FACTS FROM GUI ************************");
            for (int i = 1; i <= numberOfYVFFacts; i++) {
                factID = Integer.toString(i);

                FactStructureDTO fact = createEmptyFactSturectureDTO();
                fact.setResultColumnKeyTranslations(new HashMap<String, String>());
                factStructureList.add(fact);
                factType = (ListBox) factTypes.get(factID);
                factParameters = factTypeParams.get(factID);

                String selectedFactType = GuiUtil.getListBoxSelectedValue(factType);
                System.out.println("-> Fact Type (" + factID + "): " + selectedFactType);

                if (selectedFactType.trim().equals(USER_DEFINED_FACT_TYPE_KEY)) {
                    System.out.println("-> STATIC FACT: " + factParameters.get(0).getText());
                    fact.setFactTypeKey("fact.static_key");
                    fact.setStaticFact(true);
                    fact.setStaticValue(factParameters.get(0).getText());
                    fact.setStaticValueDataType(GuiUtil.YieldValueFunctionType.getValueDataTypeFromSymbol(yvfType));             
                } else {
                    for (FactTypeInfoDTO factTypeInfo : definedFactTypesList) {
                        if (!factTypeInfo.getFactTypeKey().equals(GuiUtil.addFactTypeKeyPrefix(selectedFactType))) {
                            continue;
                        }

                        System.out.println("-> DYNAMIC FACT: " + factTypeInfo.getFactCriteriaTypeInfo().getFactParamMap());
                        fact.setFactTypeKey(factTypeInfo.getFactTypeKey());
                        fact.setCriteriaTypeInfo(factTypeInfo.getFactCriteriaTypeInfo());
                        Map<String, FactParamDTO> definedParamValueMap = factTypeInfo.getFactCriteriaTypeInfo().getFactParamMap();
                        Map<String, String> newParamValueMap = new HashMap<String, String>();

                        Map<String, String> resultColumnKeyMap = new HashMap<String, String>();
                        resultColumnKeyMap.put(GuiUtil.YieldValueFunctionType.getProcessingColumnFromSymbol(yvfType), processingColumn);
                        fact.setResultColumnKeyTranslations(resultColumnKeyMap);
                        System.out.println("FACT STRUCTURE ID: " + fact.getFactStructureId());

                        // go through each key and set it up for given fact type
                        for (String key : definedParamValueMap.keySet()) {
                            FactParamDTO factParam = definedParamValueMap.get(key);

                            // execution time keys don't have values
                            if (factParam.getDefTime().equals("KUALI_FACT_EXECUTION_TIME_KEY")) {
                                newParamValueMap.put(key, "KUALI_FACT_EXECUTION_TIME_KEY");
                                System.out.println("-> EXEC FACT ENTERED: " + key);
                                continue;
                            }

                            // for definition time, retrieve value from GUI (TODO: error if empty?)
                            for (TextArea factParamField : factParameters) {
                                System.out.println("Fact param: " + factParamField.getName() + " - " + factParamField.getText());
                                // String factParamName = GuiUtil.addFactParamPrefix(factParamField.getName());
                                if (factParamField.getName().equals(factParam.getName())) {
                                    newParamValueMap.put(key, factParamField.getText());
                                    System.out.println("-> FACT ENTERED: " + key + " - " + factParamField.getText());
                                }
                            }
                        }

                        fact.setParamValueMap(newParamValueMap);
                    }
                }
            }
        }

        // populate rest of the proposition
        leftSide.setYieldValueFunction(yvf);
        prop.setLeftHandSide(leftSide);
        RightHandSideDTO rightSide = new RightHandSideDTO();
        rightSide.setExpectedValue(expectedValueTextBox.getText());
        prop.setRightHandSide(rightSide);
        prop.setComparisonOperatorTypeKey(operatorsListBox.getValue(operatorsListBox.getSelectedIndex()));

        return prop;
    }

    // updates stored proposition DTO because we either committing changes or selected a different proposition or a different
    // rule

    private int addEmptyPropositionToDTOList() {
        // find the new rule proposition place in the list (at the end)
        int max = 0;
        int lastPropIx = -1;
        for (Integer key : definedPropositions.keySet()) {
            lastPropIx++;
            if (key.intValue() > max) {
                max = key.intValue();
            }
        }
        max++;
        lastPropIx++;
        definedPropositions.put(max, createEmptyRuleElementTO());
        removePropButton.setEnabled(true);
        if (lastPropIx == 0) {
            removePropButton.setEnabled(false);
        }

        return lastPropIx;
    }

    /******************************************************************************************************************
     * VARIOUS
     *******************************************************************************************************************/

    private BusinessRuleInfoDTO createEmptyBusinessRule() {

        BusinessRuleInfoDTO newRule = new BusinessRuleInfoDTO();
        newRule.setId("");
        newRule.setState(STATUS_NOT_STORED_IN_DATABASE);
        newRule.setName("");
        newRule.setDesc("");
        newRule.setSuccessMessage("");
        newRule.setFailureMessage("");
        newRule.setType("");
        newRule.setAnchorTypeKey("");
        newRule.setAnchorValue("");
        newRule.setEffectiveDate(GuiUtil.ALPHA_DATE);
        newRule.setExpirationDate(GuiUtil.OMEGA_DATE);

        // set rule elements and empty rule proposition
        List<RuleElementDTO> elemList = new ArrayList<RuleElementDTO>();
        /* 
        RuleElementDTO elem = new RuleElementDTO();
        elem.setName("");
        elem.setDescription("");
        elem.setBusinessRuleElemnetTypeKey("");
        elem.setOrdinalPosition(1);
        elem.setBusinessRuleProposition(createEmptyRulePropositionDTO());
        elemList.add(elem); */
        newRule.setBusinessRuleElementList(elemList);

        // set meta info
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateTime(null);
        metaInfo.setCreateID("");
        metaInfo.setCreateComment("");
        metaInfo.setUpdateTime(null);
        metaInfo.setUpdateID("");
        metaInfo.setUpdateComment("");
        newRule.setMetaInfo(metaInfo);

        return newRule;
    }

    private RuleElementDTO createEmptyRuleElementTO() {
        LeftHandSideDTO leftSide = new LeftHandSideDTO();
        RightHandSideDTO rightSide = new RightHandSideDTO();
        RulePropositionDTO prop = new RulePropositionDTO();
        prop.setName("");
        prop.setDescription("");
        prop.setLeftHandSide(leftSide);
        prop.setComparisonOperatorTypeKey("");
        prop.setRightHandSide(rightSide);
        prop.setComparisonDataTypeKey("");
        prop.setFailureMessage("");
        
        RuleElementDTO elem = new RuleElementDTO();
        elem.setId("");
        elem.setName("");
        elem.setDescription("");
        elem.setOrdinalPosition(1);
        elem.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        elem.setBusinessRuleProposition(prop);

        return elem;
    }

    private BusinessRuleInfoDTO createRuleCopy(BusinessRuleInfoDTO originalRule) {

        BusinessRuleInfoDTO newRule = new BusinessRuleInfoDTO();
        newRule.setId(originalRule.getId());
        newRule.setOriginalRuleId(originalRule.getOriginalRuleId());
        newRule.setState(originalRule.getState());
        newRule.setName(originalRule.getName());
        newRule.setDesc(originalRule.getDesc());
        newRule.setSuccessMessage(originalRule.getSuccessMessage());
        newRule.setFailureMessage(originalRule.getFailureMessage());
        newRule.setType(originalRule.getType());
        newRule.setAnchorTypeKey(originalRule.getAnchorTypeKey());
        newRule.setAnchorValue(originalRule.getAnchorValue());
        newRule.setEffectiveDate(originalRule.getEffectiveDate());
        newRule.setExpirationDate(originalRule.getExpirationDate());

        // set meta info
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        MetaInfoDTO originalMetaInfo = originalRule.getMetaInfo();
        metaInfo.setCreateTime(originalMetaInfo.getCreateTime());
        metaInfo.setCreateID(originalMetaInfo.getCreateID());
        metaInfo.setCreateComment(originalMetaInfo.getCreateComment());
        metaInfo.setUpdateTime(originalMetaInfo.getUpdateTime());
        metaInfo.setUpdateID(originalMetaInfo.getUpdateID());
        metaInfo.setUpdateComment(originalMetaInfo.getUpdateComment());
        newRule.setMetaInfo(metaInfo);

        // set rule proposition
        List<RuleElementDTO> newElementList = new ArrayList<RuleElementDTO>();
        newRule.setBusinessRuleElementList(newElementList);
        for (RuleElementDTO originalRuleElement : originalRule.getBusinessRuleElementList()) {
            RuleElementDTO newRuleElement = new RuleElementDTO();
            newElementList.add(newRuleElement);
            newRuleElement.setId(originalRuleElement.getId());
            newRuleElement.setName(originalRuleElement.getName());
            newRuleElement.setDescription(originalRuleElement.getDescription());
            newRuleElement.setOrdinalPosition(originalRuleElement.getOrdinalPosition());
            newRuleElement.setBusinessRuleElemnetTypeKey(originalRuleElement.getBusinessRuleElemnetTypeKey());

            // if the element is a proposition, then copy the proposition as well
            RulePropositionDTO originalProposition = originalRuleElement.getBusinessRuleProposition();
            if (originalProposition == null) {
                continue;
            }

            RulePropositionDTO newProposition = new RulePropositionDTO();
            newRuleElement.setBusinessRuleProposition(newProposition);
            newProposition.setName(originalProposition.getName());
            newProposition.setDescription(originalProposition.getDescription());
            newProposition.setFailureMessage(originalProposition.getFailureMessage());
            newProposition.setComparisonDataTypeKey(originalProposition.getComparisonDataTypeKey());
            newProposition.setComparisonOperatorTypeKey(originalProposition.getComparisonOperatorTypeKey());

            LeftHandSideDTO leftSide = new LeftHandSideDTO();
            leftSide.setYieldValueFunction(originalProposition.getLeftHandSide().getYieldValueFunction());
            newProposition.setLeftHandSide(leftSide);

            RightHandSideDTO rightSide = new RightHandSideDTO();
            rightSide.setExpectedValue(originalProposition.getRightHandSide().getExpectedValue());
            newProposition.setRightHandSide(rightSide);
        }

        return newRule;
    }

    private FactStructureDTO createEmptyFactSturectureDTO() {
        FactStructureDTO factStructure = new FactStructureDTO();
        factStructure.setAnchorFlag(false);
        factStructure.setCriteriaTypeInfo(null);
        factStructure.setFactStructureId(Integer.toString(factStructureTemporaryID++)); // java.util.UUID.randomUUID().toString())
                                                                                        // or UUIDHelper.genStringUUID());
        factStructure.setFactTypeKey("");
        Map<String, String> map = new HashMap<String, String>();
        factStructure.setParamValueMap(map);
        factStructure.setStaticFact(false);
        factStructure.setStaticValue("");
        factStructure.setStaticValueDataType(""); // TODO here and elsewhere
        return factStructure;
    }

    private String areRulesTheSame(BusinessRuleInfoDTO originalRule, BusinessRuleInfoDTO currentRule) {
        
        System.out.println("comparing....................................");               
        
        //System.out.println("ID: " + originalRule.getId() + ", " + currentRule.getId());
        /* we don't need to compare rule IDs as user does not have control over them and there are updated by rule management as necessary
        if (!originalRule.getId().equals(currentRule.getId())) {
            return "ID";
        }         
        if (!originalRule.getState().equals(currentRule.getState())) {
            return "State";
        }*/
        
        if (!originalRule.getName().equals(currentRule.getName())) {
            return "Name";
        }
        if (!originalRule.getDesc().equals(currentRule.getDesc())) {
            return "Description";
        }
        if (!originalRule.getSuccessMessage().equals(currentRule.getSuccessMessage())) {
            return "Success Message";
        }
        if (!originalRule.getFailureMessage().equals(currentRule.getFailureMessage())) {
            return "Failure Message";
        }
        if (!originalRule.getType().equals(currentRule.getType())) {
            return "Type";
        }
        if (!originalRule.getAnchorTypeKey().equals(currentRule.getAnchorTypeKey())) {
            return "Anchor Type Key";
        }
        if (!originalRule.getAnchorValue().equals(currentRule.getAnchorValue())) {
            return "Anchor Value";
        }
        if (!originalRule.getEffectiveDate().equals(currentRule.getEffectiveDate())) {           
            return "Effective Date";
        }
        if (!originalRule.getExpirationDate().equals(currentRule.getExpirationDate())) {
            return "Expiration Date";
        }

        MetaInfoDTO orginalMetaInfo = originalRule.getMetaInfo();
        MetaInfoDTO currentMetaInfo = currentRule.getMetaInfo();
        if ((orginalMetaInfo.getCreateTime() == null) && (currentMetaInfo.getCreateTime() != null)) return "Create Time";
        if ((orginalMetaInfo.getCreateTime() != null) && !orginalMetaInfo.getCreateTime().equals(currentMetaInfo.getCreateTime())) {
            return "Create Time";
        }

        if ((orginalMetaInfo.getCreateID() == null) && (currentMetaInfo.getCreateID() != null)) return "Create Rule User ID";         
        if ((orginalMetaInfo.getCreateID() != null) && !orginalMetaInfo.getCreateID().equals(currentMetaInfo.getCreateID())) {
            return "Create ID";
        }
        
        if ((orginalMetaInfo.getCreateComment() == null) && (currentMetaInfo.getCreateComment() != null) && (currentMetaInfo.getCreateComment().length() != 0)) return "Create Comment";
        if ((orginalMetaInfo.getCreateComment() != null) && !orginalMetaInfo.getCreateComment().equals(currentMetaInfo.getCreateComment())) {                         
            return "Create Comment";
        }
        
        if ((orginalMetaInfo.getUpdateTime() == null) && (currentMetaInfo.getUpdateTime() != null)) return "Update Time";
        if ((orginalMetaInfo.getUpdateTime() != null) && !orginalMetaInfo.getUpdateTime().equals(currentMetaInfo.getUpdateTime())) {
            return "Update Time";
        }
        
        if ((orginalMetaInfo.getUpdateID() == null) && (currentMetaInfo.getUpdateID() != null)) return "Update Rule User ID";
        if ((orginalMetaInfo.getUpdateID() != null) && !orginalMetaInfo.getUpdateID().equals(currentMetaInfo.getUpdateID())) {
            return "Update ID";
        }
        
        if ((orginalMetaInfo.getUpdateComment() == null) && (currentMetaInfo.getUpdateComment() != null) && (currentMetaInfo.getUpdateComment().length() != 0)) return "Update Comment";        
        if ((orginalMetaInfo.getUpdateComment() != null) && !orginalMetaInfo.getUpdateComment().equals(currentMetaInfo.getUpdateComment())) {
            return "Update Comment";
        }

        if (originalRule.getBusinessRuleElementList().size() != currentRule.getBusinessRuleElementList().size()) {
            System.out.println("Nof rule elements: " + originalRule.getBusinessRuleElementList().size() + ", " + currentRule.getBusinessRuleElementList().size());            
            return "Number of Rule Elements";
        }
        List<RuleElementDTO> origElementList = originalRule.getBusinessRuleElementList();
        List<RuleElementDTO> curElementList = currentRule.getBusinessRuleElementList();

        RuleElementDTO lowestOrdinalPositionOrig = new RuleElementDTO();
        lowestOrdinalPositionOrig.setOrdinalPosition(-1);
        RuleElementDTO lowestOrdinalPositionCur = new RuleElementDTO();
        lowestOrdinalPositionCur.setOrdinalPosition(-1);
        for (int i = 0; i < curElementList.size(); i++) {

            // in the original rule, find the next lowest rule element by ordinal position
            RuleElementDTO temporarilyLowestOrig = new RuleElementDTO();
            temporarilyLowestOrig.setOrdinalPosition(99);
            for (RuleElementDTO originalRuleElement : origElementList) {
                if ((originalRuleElement.getOrdinalPosition() > lowestOrdinalPositionOrig.getOrdinalPosition()) &&
                        (originalRuleElement.getOrdinalPosition() < temporarilyLowestOrig.getOrdinalPosition())) {                   
                    temporarilyLowestOrig = originalRuleElement;
                }               
            }            
            lowestOrdinalPositionOrig = temporarilyLowestOrig;

            // in the current rule, find the next lowest rule element by ordinal position
            RuleElementDTO temporarilyLowestCur = new RuleElementDTO();
            temporarilyLowestCur.setOrdinalPosition(99);
            for (RuleElementDTO curRuleElement : curElementList) {
                if ((curRuleElement.getOrdinalPosition() > lowestOrdinalPositionCur.getOrdinalPosition()) && (curRuleElement.getOrdinalPosition() < temporarilyLowestCur.getOrdinalPosition())) {
                    temporarilyLowestCur = curRuleElement;
                }
            }
            lowestOrdinalPositionCur = temporarilyLowestCur;                      
                
            if (!lowestOrdinalPositionOrig.getBusinessRuleElemnetTypeKey().equals(lowestOrdinalPositionCur.getBusinessRuleElemnetTypeKey())) {
                return "Element's Type Key";
            }            
                       
            //if rule element is OR/AND/bracket then we are done with this element, otherwise it is proposition and check its fields
            if (!lowestOrdinalPositionOrig.getBusinessRuleElemnetTypeKey().equals(RuleElementType.PROPOSITION.getName())) {
                continue;
            }            
            
            // now compare the 2 propositions, start with element ID that we ignored for OR/AND/bracket
            if ((lowestOrdinalPositionOrig.getId() == null) && (lowestOrdinalPositionCur.getId() != null)) return "Element's ID";
            if ((lowestOrdinalPositionOrig.getId() != null) && !lowestOrdinalPositionOrig.getId().equals(lowestOrdinalPositionCur.getId())) {
                return "Element's ID";
            }            
                       
            if ((lowestOrdinalPositionOrig.getName() != null) && !lowestOrdinalPositionOrig.getName().equals(lowestOrdinalPositionCur.getName())) {
                return "Element's Name";
            }
            if (!lowestOrdinalPositionOrig.getDescription().equals(lowestOrdinalPositionCur.getDescription())) {
                return "Element's Description";
            }         
            if (!lowestOrdinalPositionOrig.getOrdinalPosition().equals(lowestOrdinalPositionCur.getOrdinalPosition())) {
                return "Element's Ordinal Position";
            }            

            RulePropositionDTO originalProposition = lowestOrdinalPositionOrig.getBusinessRuleProposition();
            RulePropositionDTO currentProposition = lowestOrdinalPositionCur.getBusinessRuleProposition();
            if ((originalProposition == null) && (currentProposition == null)) {
                continue;
            }          
            if ((originalProposition == null) || (currentProposition == null)) {
                return "Proposition's Structure";
            }            
         
            if (!originalProposition.getName().equals(currentProposition.getName())) {
                return "Proposition's Name";
            }           
            if (!originalProposition.getDescription().equals(currentProposition.getDescription())) {
                return "Proposition's Description";
            }
            
            /* TODO - new proposition field - need also text box etc.
            System.out.println("Prop's failure msg: " + originalProposition.getFailureMessage() + ", " + currentProposition.getFailureMessage());             
            if ((originalProposition.getFailureMessage() == null) && (currentProposition.getFailureMessage() != null)) return "Proposition's Failure Message";            
            if ((originalProposition.getFailureMessage() != null) && !originalProposition.getFailureMessage().equals(currentProposition.getFailureMessage())) {
                return "Proposition's Failure Message";
            } */            
                                  
            if (!originalProposition.getComparisonDataTypeKey().equals(currentProposition.getComparisonDataTypeKey())) {
                return "Proposition's Comparison Data Type Key";
            }
            if (!originalProposition.getComparisonOperatorTypeKey().equals(currentProposition.getComparisonOperatorTypeKey())) {
                return "Proposition's Comparison Operator Type Key";
            }

            YieldValueFunctionDTO originalYVF = originalProposition.getLeftHandSide().getYieldValueFunction();
            YieldValueFunctionDTO currentYVF = currentProposition.getLeftHandSide().getYieldValueFunction();
            if (!originalYVF.getYieldValueFunctionType().equals(currentYVF.getYieldValueFunctionType())) {
                return "Proposition's YVF Type";
            }

            List<FactStructureDTO> factListOrig = originalYVF.getFactStructureList();
            List<FactStructureDTO> factListCur = currentYVF.getFactStructureList();
            if (factListOrig.size() != factListCur.size()) {
                return "Proposition's number of Facts";
            }

            int ix = -1;
            for (FactStructureDTO originalFact : factListOrig) {

                ix++;
                
                // find the matching current fact
                FactStructureDTO matchingFact = factListCur.get(ix);
                /* we don't keep fact IDs so we shouldn't use it to match...
                for (FactStructureDTO currentFact : factListCur) {
                    System.out.println("FACTS: orig: " + originalFact.getFactStructureId() + ", cur: " + currentFact.getFactStructureId());
                    if (currentFact.getFactStructureId() == originalFact.getFactStructureId()) {
                        matchingFact = currentFact;
                        break;
                    }
                } */
                
                if (matchingFact == null) {
                    return "Proposition's Facts";
                }

                if (!originalFact.getAnchorFlag().equals(matchingFact.getAnchorFlag())) {
                    return "Fact's Anchor Flag";
                }                             
                                
                if (originalFact.isStaticFact() !=  matchingFact.isStaticFact()) return "Static Fact";
                
                if (originalFact.isStaticFact()) {
                    if (!originalFact.getStaticValue().equals(matchingFact.getStaticValue())) {
                        return "Static Fact Value";
                    }
                    if (!originalFact.getStaticValueDataType().equals(matchingFact.getStaticValueDataType())) {
                        return "Static Fact Data Type";
                    }                    
                } else {
                                      
                    if (!originalFact.getFactTypeKey().equals(matchingFact.getFactTypeKey())) {
                        return "Fact's Type Key";
                    }                      
                    
                    if (originalFact.getParamValueMap().equals(matchingFact.getParamValueMap()) == false) {
                        return "Dynamic Fact Param Value Map";
                    }                    
                }

                if ((originalFact.getResultColumnKeyTranslations() == null) && (matchingFact.getResultColumnKeyTranslations() != null)) return "Fact Result Column Key";
                if ((originalFact.getResultColumnKeyTranslations() != null) && originalFact.getResultColumnKeyTranslations().equals(matchingFact.getResultColumnKeyTranslations()) == false) {
                    return "Fact Result Column Key";
                }
            }

            if (!originalProposition.getRightHandSide().getExpectedValue().equals(currentProposition.getRightHandSide().getExpectedValue())) {
                return "Expected Value";
            }

        }

        return "";
    }

    private void retrieveFactTypes() {
        if (definedFactTypesList == null) {
            // load list of factTypes for the given business rule type
            // TODO in proposition form, make indication that the param list boxes are still being loaded...
            loadingFactTypeKeyList = true;
            DevelopersGuiService.Util.getInstance().fetchBusinessRuleType(displayedRule.getType(), displayedRule.getAnchorTypeKey(), new AsyncCallback<BusinessRuleTypeInfoDTO>() {
                public void onFailure(Throwable caught) {
                    // just re-throw it and let the uncaught exception handler deal with it
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(BusinessRuleTypeInfoDTO ruleTypeInfo) {
                    Logger.info("Loading fact type key list: " + ruleTypeInfo.getFactTypeKeyList());
                    loadingFactTypeKeyList = false;
                    definedFactTypesList = new ArrayList<FactTypeInfoDTO>();

                    // get fact type info for each fact type from the fact service
                    DevelopersGuiService.Util.getInstance().fetchFactTypeList(ruleTypeInfo.getFactTypeKeyList(), new AsyncCallback<List<FactTypeInfoDTO>>() {
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        public void onSuccess(List<FactTypeInfoDTO> factTypeInfo) {
                            definedFactTypesList = factTypeInfo;
                            populateYVFFactTypeLists();
                        }
                    });
                }
            });
            return;
        }
        populateYVFFactTypeLists();
    }

    // once we retrieve all Fact Types from Fact Service, we can populate all Fact Types drop down lists
    private void populateYVFFactTypeLists() {
        for (String key : factTypes.keySet()) {
            ListBox factTypeListBox = (ListBox) factTypes.get(key);
            factTypeListBox.clear();
            factTypeListBox.addItem(USER_DEFINED_FACT_TYPE_KEY);

            for (FactTypeInfoDTO factTypeInfo : definedFactTypesList) {
                factTypeListBox.addItem(GuiUtil.removeFactTypeKeyPrefix(factTypeInfo.getFactTypeKey()));
            }

            GuiUtil.setListBoxSelectionByItemName(factTypeListBox, factTypeSelectedValues.get(key));
            factTypeListBox.setEnabled(true);
        }
    }

    private List<DateRange> getActiveTimeGaps(RulesHierarchyInfo rulesGroup) {
        ArrayList<DateRange> result = null;
        DateRange allRange = null;
        List<RulesVersionInfo> activeVersions = null;
        List<DateRange> activeVersionRanges = null;
        DateRange[] arrActiveVersionRanges = null;
        if (rulesGroup == null) {
            return null;
        }
        result = new ArrayList<DateRange>();
        allRange = new DateRange(GuiUtil.ALPHA_DATE, GuiUtil.OMEGA_DATE);
        activeVersions = rulesGroup.getActiveVersions();
        activeVersionRanges = new ArrayList<DateRange>();
        if (activeVersions == null) {
            return null;
        }
        for (RulesVersionInfo activeVersion : activeVersions) {
            java.util.Date effectiveDate = ((activeVersion.getEffectiveDate() == null) ? GuiUtil.ALPHA_DATE : activeVersion.getEffectiveDate());
            java.util.Date expiryDate = (activeVersion.getExpirationDate() == null) ? GuiUtil.OMEGA_DATE : activeVersion.getExpirationDate();
            activeVersionRanges.add(new DateRange(effectiveDate, expiryDate));
        }
        if ((activeVersionRanges != null) && !activeVersionRanges.isEmpty()) {
            arrActiveVersionRanges = new DateRange[activeVersionRanges.size()];
            arrActiveVersionRanges = activeVersionRanges.toArray(arrActiveVersionRanges);
        }
        allRange.excludeRanges(arrActiveVersionRanges, result);
        return result;
    }

    public static String formatDate(Date date) {
        DateTimeFormat formatter = dateFormatter;
        if (date == null) {
            return "";
        }
        return formatter.format(date);
    }

    @Override
    protected void onUnload() {
        super.onUnload();
        // unlink the binding as it is no longer needed
        binding.unlink();
    }

    /******************************************************************************************************************
     * FORMS INITIALIZATION
     *******************************************************************************************************************/

    public void clearRuleForms() {
        // clear Main TAB
        businessRuleID.setText("");
        ruleStatus.setText("");
        nameTextBox.setText("");
        descriptionTextArea.setText("");
        successMessageTextArea.setText("");
        failureMessageTextArea.setText("");
        GuiUtil.setListBoxSelectionByItemName(agendaTypesListBox, ""); // deselect agenda type list box
        businessRuleTypesListBox.clear();
        ruleAnchorType.setText("");
        ruleAnchorTextBox.setText("");

        // Clear Propositions TAB
        clearPropositionFormFields();
        propositionsListBox.clear();
        selectedPropListBoxIndex = -1;
        propCompositionTextArea.setText("");
        completeRuleTextArea.setText("");
        expectedValueTextBox.setText("");
        compositionStatusLabel.setText("");

        // Clear Authoring TAB
        effectiveDateTextBox.setText("");
        expiryDateTextBox.setText("");
        createTimeLabel.setText("");
        createUserIdLabel.setText("");
        updateTimeLabel.setText("");
        updateUserIdLabel.setText("");
    }

    private void clearPropositionFormFields() {
        propNameTextBox.setText("");
        propDescTextBox.setText("");
        yvfListBox.setSelectedIndex(-1);
        clearYVFFactFields();
        operatorsListBox.setSelectedIndex(-1);
        expectedValueTextBox.setText("");
    }

    private void clearYVFFactFields() {
        factTypes.clear();
        factTypeParams.clear();
        factParamsPanel.clear();
        factTypeSelectedValues.clear();
        factDetailsPanel.clear();
    }

    private void populateAgendaAndBusinessRuleTypesListBox() {

        String ruleAgendaType = (displayedRuleInfo == null ? "" : displayedRuleInfo.getAgendaType());
        String businessRuleType = (displayedRule == null ? "" : displayedRule.getType());

        System.out.println("DEBUG: agenda type:" + ruleAgendaType);
        System.out.println("Business rule type: " + businessRuleType);

        // populate agenda type list box if it is empty
        if (agendaTypesListBox.getItemCount() == 0) {
            DevelopersGuiService.Util.getInstance().findAgendaTypes(new AsyncCallback<List<String>>() {
                public void onFailure(Throwable caught) {
                    // just re-throw it and let the uncaught exception handler deal with it
                    Window.alert(caught.getMessage());
                    // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                }

                public void onSuccess(List<String> agendaTypes) {
                    agendaTypesListBox.clear();
                    agendaTypesListBox.addItem(EMPTY_LIST_BOX_ITEM);
                    for (String agendaTypeKey : agendaTypes) {
                        agendaTypesListBox.addItem(agendaTypeKey);
                    }
                }
            });
        }

        System.out.println("STATUS: " + displayedRule.getState());

        // if the rule is stored in database i.e. DRAFT_IN_PROGRESS, ACTIVE, RETIRED then we just populate the drop downs
        if (displayedRule.getState().equals(STATUS_NOT_STORED_IN_DATABASE) == false) {

            if (ruleAgendaType.isEmpty() || businessRuleType.isEmpty()) {
                GuiUtil.showUserDialog("ERROR: Agenda Type and Business Rule Type cannot be empty.");
            }
            agendaTypesListBox.setEnabled(false);
            GuiUtil.setListBoxSelectionByItemName(agendaTypesListBox, ruleAgendaType);
            businessRuleTypesListBox.setEnabled(false);
            businessRuleTypesListBox.clear();
            businessRuleTypesListBox.addItem(businessRuleType);
            GuiUtil.setListBoxSelectionByItemName(businessRuleTypesListBox, businessRuleType);
            return;
        }

        // the rule status is NOT_IN_DATABASE....
        agendaTypesListBox.setEnabled(true);
        businessRuleTypesListBox.setEnabled(true);

        // if user did not select any agenda type then we don't retrieve business rule types and leave the list box disabled
        if (ruleAgendaType.trim().isEmpty()) {
            agendaTypesListBox.setSelectedIndex(0); // show empty agenda
            businessRuleTypesListBox.clear();
            businessRuleTypesListBox.setEnabled(false);
            return;
        }

        GuiUtil.setListBoxSelectionByItemName(agendaTypesListBox, ruleAgendaType);

        // find related business rule types
        DevelopersGuiService.Util.getInstance().findBusinessRuleTypesByAgendaType(ruleAgendaType, new AsyncCallback<List<String>>() {
            public void onFailure(Throwable caught) {
                // just re-throw it and let the uncaught exception handler deal with it
                Window.alert(caught.getMessage());
                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
            }

            public void onSuccess(List<String> businessRuleTypes) {
                businessRuleTypesListBox.clear();
                businessRuleTypesListBox.addItem(EMPTY_LIST_BOX_ITEM);
                for (String businessRuleTypeKey : businessRuleTypes) {
                    businessRuleTypesListBox.addItem(businessRuleTypeKey);
                }

                if (displayedRule.getType().trim().isEmpty() == false) {
                    GuiUtil.setListBoxSelectionByItemName(businessRuleTypesListBox, displayedRule.getType());
                }
            }
        });
    }

    public void displayActiveRule() {

        // populate Main TAB
        businessRuleID.setText(displayedRule.getId());
        setRuleStatus(displayedRule.getState());
        nameTextBox.setText(displayedRule.getName());
        descriptionTextArea.setText(displayedRule.getDesc());
        successMessageTextArea.setText(displayedRule.getSuccessMessage());
        failureMessageTextArea.setText(displayedRule.getFailureMessage());
        populateAgendaAndBusinessRuleTypesListBox();
        ruleAnchorType.setText(displayedRule.getAnchorTypeKey());
        ruleAnchorTextBox.setText(displayedRule.getAnchorValue());

        // populate Propositions TAB
        // populate the proposition details according to first prop selected by default
        propCompositionTextArea.setText(ruleComposition.toString());
        updatePropositionListBoxAndFormFields(0);
        completeRuleTextArea.setText(GuiUtil.assembleRuleFromComposition(propCompositionTextArea.getText(), definedPropositions));

        // populate Authoring TAB
        effectiveDateTextBox.setText(formatDate(displayedRule.getEffectiveDate()));
        expiryDateTextBox.setText(formatDate(displayedRule.getExpirationDate()));
        createTimeLabel.setText(formatDate(displayedRule.getMetaInfo().getCreateTime()));
        createUserIdLabel.setText(displayedRule.getMetaInfo().getCreateID());
        createCommentTextBox.setText(displayedRule.getMetaInfo().getCreateComment());
        updateTimeLabel.setText(formatDate(displayedRule.getMetaInfo().getUpdateTime()));
        updateUserIdLabel.setText(displayedRule.getMetaInfo().getUpdateID());
        updateCommentTextBox.setText(displayedRule.getMetaInfo().getUpdateComment());

        // populate Test TAB
        propCompositionTestTextArea.setText(ruleComposition.toString());
        completeRuleTestTextArea.setText(GuiUtil.assembleRuleFromComposition(propCompositionTestTextArea.getText(), definedPropositions));
    }

    private Widget addRulesForm() {
        rulesFormTabs.add(addRulesVersionPage(), "Versions");
        rulesFormTabs.add(addRulesMainPage(), "Main");
        rulesFormTabs.add(addRulesPropositionPage(), "Propositions");
        rulesFormTabs.add(addRRulesMetaDataPage(), "Authoring");
        rulesFormTabs.add(addRRulesTestPage(), "Test");
        rulesFormTabs.add(addRRulesTestResultsPage(), "Test Results");
        rulesFormTabs.setSize("90%", "550px");
        rulesFormTabs.selectTab(0);

        // show buttons
        HorizontalPanel space = new HorizontalPanel();
        space.setWidth("20px");
        HorizontalPanel hp = new HorizontalPanel();
        hp.setSpacing(8);
        hp.add(submitRuleButton);
        hp.add(updateRuleButton);
        hp.add(activateRuleButton);
        hp.add(createNewVersionButton);
        hp.add(retireRuleButton);
        hp.add(space);
        hp.add(copyRuleButton);
        hp.add(cancelButton);

        final VerticalPanel rulesFormVerticalPanel = new VerticalPanel();
        rulesFormVerticalPanel.setSpacing(5);
        HorizontalPanel selectedVersionPanel = new HorizontalPanel();
        selectedVersionPanel.setSpacing(5);
        copiedVersionIndicator.setStyleName("copied-version");
//        selectedVersionPanel.add(new Label("Version Selected:"));
        selectedVersionPanel.add(rulesVersionsTable);
        selectedVersionPanel.add(copiedVersionIndicator);
        rulesFormVerticalPanel.add(selectedVersionPanel);
        rulesFormVerticalPanel.add(rulesFormTabs);
        rulesFormVerticalPanel.add(hp);
        rulesFormVerticalPanel.setSize("100%", "500");

        return rulesFormVerticalPanel;
    }

    private Widget addRulesVersionPage() {
        final FlexTable rulesVersionFlexTable = new FlexTable();
        rulesVersionFlexTable.setTitle("Versions");
        rulesVersionFlexTable.setSize("100%", "100%");

        final SimplePanel topMargin = new SimplePanel();
        rulesVersionFlexTable.setWidget(0, 0, topMargin);
        rulesVersionFlexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        rulesVersionFlexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
        rulesVersionFlexTable.getCellFormatter().setHeight(0, 0, "5pix");

        final SimplePanel leftMargin = new SimplePanel();
        rulesVersionFlexTable.setWidget(1, 0, leftMargin);
        rulesVersionFlexTable.getCellFormatter().setWidth(1, 0, "5pix");

        // **********************************************************
        // set version page size
        // **********************************************************
        final FormPanel versionPanel = new FormPanel();
        rulesVersionFlexTable.setWidget(1, 1, versionPanel);
        rulesVersionFlexTable.getCellFormatter().setWidth(1, 1, "100%");
        versionPanel.setWidth("100%");
        rulesVersionFlexTable.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);

        final VerticalPanel ruleVersionVerticalPanel = new VerticalPanel();
        ruleVersionVerticalPanel.setSize("100%", "100%");
        ruleVersionVerticalPanel.setSpacing(10);

        // **********************************************************
        // The versions gap analysis
        // **********************************************************
        final HorizontalPanel timeGapsFieldPanel = new HorizontalPanel();
        timeGapsFieldPanel.setWidth("100%");
        activeTimeGapsTextArea.setSize("75%", "93px");
        timeGapsFieldPanel.add(GuiUtil.addLabelAndFieldVertically(messages.get("versionTimeGap"), activeTimeGapsTextArea, "50%"));
        rulesVersionsTable.setRowsPerPage(3);
//        ruleVersionVerticalPanel.add(rulesVersionsTable);
        ruleVersionVerticalPanel.add(timeGapsFieldPanel);
        versionPanel.add(ruleVersionVerticalPanel);
        return rulesVersionFlexTable;
    }

    private Widget addRulesMainPage() {

        // **********************************************************
        // set rules form margins
        // **********************************************************
        final FlexTable rulesFlexTable = new FlexTable();
        rulesFlexTable.setTitle("Rules");
        rulesFlexTable.setSize("100%", "100%");

        final SimplePanel topMargin = new SimplePanel();
        rulesFlexTable.setWidget(0, 0, topMargin);
        rulesFlexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        rulesFlexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
        rulesFlexTable.getCellFormatter().setHeight(0, 0, "5pix");

        final SimplePanel leftMargin = new SimplePanel();
        rulesFlexTable.setWidget(1, 0, leftMargin);
        rulesFlexTable.getCellFormatter().setWidth(1, 0, "5pix");

        // **********************************************************
        // set rules form size
        // **********************************************************
        final FormPanel rulesFormPanel = new FormPanel();
        rulesFlexTable.setWidget(1, 1, rulesFormPanel);
        rulesFlexTable.getCellFormatter().setWidth(1, 1, "100%");
        rulesFormPanel.setWidth("100%");
        rulesFlexTable.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);

        final FlexTable flexFormTable = new FlexTable();
        rulesFormPanel.add(flexFormTable);
        flexFormTable.setSize("100%", "100%");

        // **********************************************************
        // rules form elements
        // **********************************************************

        int ix = 1;
        // business rule ID
        final Label ruleID = new Label(messages.get("ruleId"));
        flexFormTable.setWidget(ix, 0, ruleID);
        flexFormTable.setWidget(ix, 1, businessRuleID);

        // business rule status
        ix++;
        final Label statusLabel = new Label(messages.get("ruleState"));
        flexFormTable.setWidget(ix, 0, statusLabel);
        flexFormTable.getCellFormatter().setWidth(ix, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(ix, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(ix, 1, ruleStatus);
        ruleStatus.setWidth("25%");

        // Name
        ix++;
        final Label nameLabel = new Label(messages.get("ruleName"));
        flexFormTable.setWidget(ix, 0, nameLabel);
        flexFormTable.getCellFormatter().setWidth(ix, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(ix, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(ix, 1, nameTextBox);
        nameTextBox.setWidth("50%");

        // Description
        ix++;
        final Label descriptionLabel = new Label(messages.get("ruleDescription"));
        flexFormTable.setWidget(ix, 0, descriptionLabel);
        flexFormTable.getCellFormatter().setVerticalAlignment(ix, 0, HasVerticalAlignment.ALIGN_TOP);
        flexFormTable.getCellFormatter().setHeight(ix, 0, FORM_ROW_HEIGHT);
        flexFormTable.getCellFormatter().setWidth(ix, 0, "200px");

        flexFormTable.setWidget(ix, 1, descriptionTextArea);
        flexFormTable.getCellFormatter().setWordWrap(ix, 1, true);
        flexFormTable.getCellFormatter().setVerticalAlignment(ix, 1, HasVerticalAlignment.ALIGN_TOP);
        descriptionTextArea.setSize("75%", "100%");
        flexFormTable.getCellFormatter().setHeight(ix, 1, "93px");

        // Success Message
        ix++;
        final Label successMessageLabel = new Label(messages.get("ruleSuccessMessage"));
        flexFormTable.setWidget(ix, 0, successMessageLabel);
        flexFormTable.getCellFormatter().setVerticalAlignment(ix, 0, HasVerticalAlignment.ALIGN_TOP);
        flexFormTable.getCellFormatter().setHeight(ix, 0, FORM_ROW_HEIGHT);
        flexFormTable.getCellFormatter().setWidth(ix, 0, "200px");

        flexFormTable.setWidget(ix, 1, successMessageTextArea);
        successMessageTextArea.setTextAlignment(TextBoxBase.ALIGN_LEFT);
        flexFormTable.getCellFormatter().setVerticalAlignment(ix, 1, HasVerticalAlignment.ALIGN_TOP);
        successMessageTextArea.setSize("75%", "100%");
        flexFormTable.getCellFormatter().setHeight(ix, 1, "93px");

        // Failure Message
        ix++;
        final Label failureMessageLabel = new Label(messages.get("ruleFailureMessage"));
        flexFormTable.setWidget(ix, 0, failureMessageLabel);
        flexFormTable.getCellFormatter().setVerticalAlignment(ix, 0, HasVerticalAlignment.ALIGN_TOP);
        flexFormTable.getCellFormatter().setHeight(ix, 0, FORM_ROW_HEIGHT);
        flexFormTable.getCellFormatter().setWidth(ix, 0, "200px");

        flexFormTable.setWidget(ix, 1, failureMessageTextArea);
        failureMessageTextArea.setSize("75%", "100%");
        flexFormTable.getCellFormatter().setHeight(ix, 1, "93px");

        // Agenda Type
        ix++;
        final Label agendaTypeLabel = new Label(messages.get("ruleAgendaType"));
        flexFormTable.setWidget(ix, 0, agendaTypeLabel);
        flexFormTable.getCellFormatter().setHeight(ix, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(ix, 1, agendaTypesListBox);
        flexFormTable.getCellFormatter().setHeight(ix, 1, FORM_ROW_HEIGHT);

        // Business Rule Type
        ix++;
        final Label businessRuleTypeLabel = new Label(messages.get("businessRuleType"));
        flexFormTable.setWidget(ix, 0, businessRuleTypeLabel);
        flexFormTable.getCellFormatter().setHeight(ix, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(ix, 1, businessRuleTypesListBox);
        flexFormTable.getCellFormatter().setHeight(ix, 1, FORM_ROW_HEIGHT);

        // Anchor Type
        ix++;
        final Label anchorTypeLabel = new Label(messages.get("ruleAnchorType"));
        flexFormTable.setWidget(ix, 0, anchorTypeLabel);
        flexFormTable.getCellFormatter().setHeight(ix, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(ix, 1, ruleAnchorType);
        flexFormTable.getCellFormatter().setHeight(ix, 1, FORM_ROW_HEIGHT);

        // Anchor
        ix++;
        final Label anchorLabel = new Label(messages.get("ruleAnchor"));
        flexFormTable.setWidget(ix, 0, anchorLabel);
        flexFormTable.getCellFormatter().setWidth(ix, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(ix, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(ix, 1, ruleAnchorTextBox);
        ruleAnchorTextBox.setWidth("25%");

        // filler
        ix++;
        final SimplePanel filler = new SimplePanel();
        flexFormTable.setWidget(ix, 0, filler);
        flexFormTable.getFlexCellFormatter().setColSpan(ix, 0, 2);

        ix++;
        flexFormTable.getCellFormatter().setHorizontalAlignment(ix, 0, HasHorizontalAlignment.ALIGN_CENTER);
        flexFormTable.getFlexCellFormatter().setColSpan(ix, 0, 2);

        return rulesFlexTable;
    }

    private Widget addRulesPropositionPage() {

        // **********************************************************
        // set rules form margins
        // **********************************************************
        final FlexTable propositionsFlexTable = new FlexTable();
        propositionsFlexTable.setSize("100%", "100%");

        final SimplePanel topMargin = new SimplePanel();
        propositionsFlexTable.setWidget(0, 0, topMargin);
        propositionsFlexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        propositionsFlexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
        propositionsFlexTable.getCellFormatter().setHeight(0, 0, "5pix");

        final SimplePanel leftMargin = new SimplePanel();
        propositionsFlexTable.setWidget(1, 0, leftMargin);
        propositionsFlexTable.getCellFormatter().setWidth(1, 0, "5pix");

        // **********************************************************
        // set rules form size
        // **********************************************************
        final FormPanel rulesFormPanel = new FormPanel();
        propositionsFlexTable.setWidget(1, 1, rulesFormPanel);
        propositionsFlexTable.getCellFormatter().setWidth(1, 1, "100%");
        propositionsFlexTable.getCellFormatter().setHeight(1, 1, "100%");
        rulesFormPanel.setSize("100%", "100%");
        propositionsFlexTable.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);

        final VerticalPanel verticalPanel = new VerticalPanel();
        rulesFormPanel.add(verticalPanel);
        verticalPanel.setSize("100%", "100%");

        // **********************************************************
        // rules form elements
        // **********************************************************

        // first setup a list of propositions (left panel) and proposition details (right panel)
        final HorizontalPanel horizontalPanel = new HorizontalPanel();
        horizontalPanel.setSize("100%", "100%");

        propositionsListBox.setVisibleItemCount(10);
        propositionsListBox.setWidth("150px");

        // **********************************************************
        // Propositions List
        // **********************************************************
        final VerticalPanel propListPanel = new VerticalPanel();
        final Label propositionsLabel = new Label("Propositions");
        propositionsLabel.setStyleName("propositon_bold");
        propListPanel.add(propositionsLabel);
        propListPanel.add(propositionsListBox);

        HorizontalPanel hpPropListButtons = new HorizontalPanel();
        hpPropListButtons.setSpacing(8);
        hpPropListButtons.add(addPropButton);
        hpPropListButtons.add(removePropButton);
        propListPanel.add(hpPropListButtons);

        horizontalPanel.add(propListPanel);

        // **********************************************************
        // Propositions details
        // **********************************************************
        final SimplePanel propDetailsBorder = new SimplePanel();
        propositionDetailsPanel.setWidth("100%");
        propositionDetailsPanel.setSpacing(5);
        propDetailsBorder.add(propositionDetailsPanel);

        // Proposition Name
        final HorizontalPanel hpPropName = new HorizontalPanel();
        hpPropName.setWidth("100%");
        hpPropName.add(GuiUtil.addLabelAndFieldVertically(messages.get("propositionName"), propNameTextBox, "50%"));
        propositionDetailsPanel.add(hpPropName);

        // Proposition Description
        final HorizontalPanel hpPropDesc = new HorizontalPanel();
        hpPropDesc.setWidth("100%");
        hpPropDesc.add(GuiUtil.addLabelAndFieldVertically(messages.get("propositionDescription"), propDescTextBox, "70%"));

        propositionDetailsPanel.add(hpPropDesc);

        // add proposition left, operator and right hand side
        yvfListBox.addItem(EMPTY_LIST_BOX_ITEM);
        GuiUtil.populateYVFList(yvfListBox);
        operatorsListBox.addItem(EMPTY_LIST_BOX_ITEM);
        GuiUtil.populateComparisonOperatorList(operatorsListBox);
        final HorizontalPanel hpLeftOpRightSide = new HorizontalPanel();

        Label yvfLabel = new Label("YVF");
        yvfLabel.setStyleName("yvf_fields");
        hpLeftOpRightSide.add(GuiUtil.addLabelAndFieldVertically(yvfLabel, yvfListBox, "200px"));
        GuiUtil.addSpaceBesideWidget(hpLeftOpRightSide, "15px");
        hpLeftOpRightSide.add(GuiUtil.addLabelAndFieldVertically("Operator", operatorsListBox, "80px"));
        GuiUtil.addSpaceBesideWidget(hpLeftOpRightSide, "15px");
        hpLeftOpRightSide.add(GuiUtil.addLabelAndFieldVertically(messages.get("yvfExpectedValue"), expectedValueTextBox, "150px"));
        propositionDetailsPanel.add(hpLeftOpRightSide);

        final HorizontalPanel hpYVFFactTypes = new HorizontalPanel();
        propositionDetailsPanel.add(hpYVFFactTypes);

        factDetailsPanel.setSpacing(5);
        propositionDetailsPanel.add(factDetailsPanel);

        /*
         * HorizontalPanel hpButtons = new HorizontalPanel(); hpButtons.setSpacing(8); hpButtons.add(cancelPropButton);
         * propositionDetailsPanel.add(hpButtons);
         */

        horizontalPanel.add(propositionDetailsPanel);
        horizontalPanel.setCellHeight(propListPanel, "75%");
        horizontalPanel.setCellWidth(propListPanel, "180px");
        verticalPanel.add(horizontalPanel);

        // **********************************************************
        // Rule composition and complete text
        // **********************************************************
        final FlexTable ruleCompositionFlexTable = new FlexTable();
        ruleCompositionFlexTable.setSize("100%", "100%");

        final Label propCompositionLabel = new Label("Rule Composition");
        propCompositionLabel.setStyleName("propositon_bold");
        ruleCompositionFlexTable.setWidget(0, 0, propCompositionLabel);
        ruleCompositionFlexTable.getCellFormatter().setHeight(0, 0, FORM_ROW_HEIGHT);
        final Label propCompositionStatusLabel = new Label("Status:");
        ruleCompositionFlexTable.setWidget(0, 1, propCompositionStatusLabel);
        compositionStatusLabel.setText("                                    ");
        ruleCompositionFlexTable.setWidget(0, 2, compositionStatusLabel);

        propCompositionTextArea.setSize("100%", "100%");
        ruleCompositionFlexTable.setWidget(1, 0, propCompositionTextArea);
        ruleCompositionFlexTable.getFlexCellFormatter().setColSpan(1, 0, 4);
        ruleCompositionFlexTable.getCellFormatter().setHeight(1, 0, "33px");
        ruleCompositionFlexTable.getCellFormatter().setWidth(1, 0, "60%");
        ruleCompositionFlexTable.setWidget(2, 2, validateRuleButton);

        // Complete Rule
        final Label completeRuleLabel = new Label("Rule Overview");
        completeRuleLabel.setStyleName("propositon_bold");
        ruleCompositionFlexTable.setWidget(3, 0, completeRuleLabel);
        ruleCompositionFlexTable.getCellFormatter().setHeight(3, 0, FORM_ROW_HEIGHT);

        completeRuleTextArea.setSize("100%", "100%");
        completeRuleTextArea.setReadOnly(true);
        ruleCompositionFlexTable.setWidget(4, 0, completeRuleTextArea);
        ruleCompositionFlexTable.getFlexCellFormatter().setColSpan(4, 0, 3);
        ruleCompositionFlexTable.getCellFormatter().setHeight(4, 0, "93px");
        ruleCompositionFlexTable.getCellFormatter().setWidth(4, 0, "60%");

        verticalPanel.add(ruleCompositionFlexTable);
        verticalPanel.setCellHeight(horizontalPanel, "200px");

        return propositionsFlexTable;
    }

    private Widget addRRulesMetaDataPage() {

        // **********************************************************
        // set rules form margins
        // **********************************************************
        final FlexTable rulesFlexTable = new FlexTable();
        rulesFlexTable.setSize("100%", "100%");

        final SimplePanel topMargin = new SimplePanel();
        rulesFlexTable.setWidget(0, 0, topMargin);
        rulesFlexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        rulesFlexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
        rulesFlexTable.getCellFormatter().setHeight(0, 0, "5pix");

        final SimplePanel leftMargin = new SimplePanel();
        rulesFlexTable.setWidget(1, 0, leftMargin);
        rulesFlexTable.getCellFormatter().setWidth(1, 0, "5pix");

        // **********************************************************
        // set rules form size
        // **********************************************************
        final FormPanel rulesFormPanel = new FormPanel();
        rulesFlexTable.setWidget(1, 1, rulesFormPanel);
        rulesFlexTable.getCellFormatter().setWidth(1, 1, "100%");
        rulesFormPanel.setWidth("100%");
        rulesFlexTable.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);

        final FlexTable flexFormTable = new FlexTable();
        rulesFormPanel.add(flexFormTable);
        flexFormTable.setSize("100%", "100%");

        // **********************************************************
        // rules form elements
        // **********************************************************

        // Effective Date
        flexFormTable.setWidget(2, 0, new Label("Effective Date"));
        flexFormTable.getCellFormatter().setWidth(2, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(2, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(2, 1, effectiveDateTextBox);
        // effectiveDateTextBox.setWidth("30%");

        // Expiry Date
        flexFormTable.setWidget(3, 0, new Label("Expiry Date"));
        flexFormTable.getCellFormatter().setWidth(3, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(3, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(3, 1, expiryDateTextBox);
        // expiryDateTextBox.setWidth("30%");

        // Create Time
        flexFormTable.setWidget(4, 0, new Label("Create Time"));
        flexFormTable.getCellFormatter().setWidth(4, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(4, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(4, 1, createTimeLabel);
        // createTimeLabel.setWidth("30%");

        // Create User ID
        flexFormTable.setWidget(5, 0, new Label("Create Rule User Id"));
        flexFormTable.getCellFormatter().setWidth(5, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(5, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(5, 1, createUserIdLabel);
        // createUserIdLabel.setWidth("30%");

        // Create Comment
        flexFormTable.setWidget(6, 0, new Label("Create Comment"));
        flexFormTable.getCellFormatter().setWidth(6, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(6, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(6, 1, createCommentTextBox);
        createCommentTextBox.setWidth("30%");

        // Update Time
        flexFormTable.setWidget(7, 0, new Label("Update Time"));
        flexFormTable.getCellFormatter().setWidth(7, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(7, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(7, 1, updateTimeLabel);
        // updateTimeLabel.setWidth("30%");

        // Update User ID
        flexFormTable.setWidget(8, 0, new Label("Update Rule User Id"));
        flexFormTable.getCellFormatter().setWidth(8, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(8, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(8, 1, updateUserIdLabel);
        // updateUserIdLabel.setWidth("30%");

        // Update Comment
        flexFormTable.setWidget(9, 0, new Label("Update Comment"));
        flexFormTable.getCellFormatter().setWidth(9, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(9, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(9, 1, updateCommentTextBox);
        updateCommentTextBox.setWidth("30%");

        // filler
        final SimplePanel filler = new SimplePanel();
        flexFormTable.setWidget(10, 0, filler);
        flexFormTable.getFlexCellFormatter().setColSpan(10, 0, 2);

        flexFormTable.getCellFormatter().setHorizontalAlignment(11, 0, HasHorizontalAlignment.ALIGN_CENTER);
        flexFormTable.getFlexCellFormatter().setColSpan(11, 0, 2);

        return rulesFlexTable;
    }

    private Widget addRRulesTestPage() {

        // **********************************************************
        // set rules form margins
        // **********************************************************
        final FlexTable propositionsFlexTable = new FlexTable();
        propositionsFlexTable.setSize("100%", "100%");

        final SimplePanel topMargin = new SimplePanel();
        propositionsFlexTable.setWidget(0, 0, topMargin);
        propositionsFlexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        propositionsFlexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
        propositionsFlexTable.getCellFormatter().setHeight(0, 0, "5pix");

        final SimplePanel leftMargin = new SimplePanel();
        propositionsFlexTable.setWidget(1, 0, leftMargin);
        propositionsFlexTable.getCellFormatter().setWidth(1, 0, "5pix");

        // **********************************************************
        // set rules form size
        // **********************************************************
        final FormPanel rulesFormPanel = new FormPanel();
        propositionsFlexTable.setWidget(1, 1, rulesFormPanel);
        propositionsFlexTable.getCellFormatter().setWidth(1, 1, "100%");
        propositionsFlexTable.getCellFormatter().setHeight(1, 1, "100%");
        rulesFormPanel.setSize("100%", "100%");
        propositionsFlexTable.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);

        rulesFormPanel.add(propositionsTestPanel);
        propositionsTestPanel.setSize("100%", "100%");

        return propositionsFlexTable;
    }

    private Widget addRRulesTestResultsPage() {

        // **********************************************************
        // set rules form margins
        // **********************************************************
        final FlexTable propositionsFlexTable = new FlexTable();
        propositionsFlexTable.setSize("100%", "100%");

        final SimplePanel topMargin = new SimplePanel();
        propositionsFlexTable.setWidget(0, 0, topMargin);
        propositionsFlexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        propositionsFlexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
        propositionsFlexTable.getCellFormatter().setHeight(0, 0, "5pix");

        final SimplePanel leftMargin = new SimplePanel();
        propositionsFlexTable.setWidget(1, 0, leftMargin);
        propositionsFlexTable.getCellFormatter().setWidth(1, 0, "5pix");

        // **********************************************************
        // set rules form size
        // **********************************************************
        final FormPanel rulesFormPanel = new FormPanel();
        propositionsFlexTable.setWidget(1, 1, rulesFormPanel);
        propositionsFlexTable.getCellFormatter().setWidth(1, 1, "100%");
        propositionsFlexTable.getCellFormatter().setHeight(1, 1, "100%");
        rulesFormPanel.setSize("100%", "100%");
        propositionsFlexTable.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);

        testReport.setSize("100%", "550px");

        rulesFormPanel.add(testReport);

        return propositionsFlexTable;
    }

    private void displayTestPageRuleFacts() {

        Label ruleCompositionTestLabel = new Label();
        testDefinitionTimeFactsWidgets.clear();
        testExecutionTimeFactsWidgets.clear();

        propositionsTestPanel.clear();
        propositionsTestPanel.setSpacing(2);

        // Rule Composition
        final HorizontalPanel hpRuleComposition = new HorizontalPanel();
        hpRuleComposition.setWidth("100%");
        ruleCompositionTestLabel.setText(ruleComposition.toString());
        Label ruleCompositionTextLabel = new Label("Rule Composition:   ");
        ruleCompositionTextLabel.setStyleName("propositon_bold");
        hpRuleComposition.add(GuiUtil.addLabelAndFieldHorizontally(ruleCompositionTextLabel, ruleCompositionTestLabel, "100%"));
        propositionsTestPanel.add(hpRuleComposition);
        GuiUtil.addSpaceBelowWidget(propositionsTestPanel, "10px");

        // for each proposition, show YVF, operator and expected value and facts
        RulePropositionDTO prop;
        YieldValueFunctionDTO yvf;
        int ix = 0;
        for (Map.Entry<Integer, RuleElementDTO> entry : definedPropositions.entrySet()) {

            ix++;
            prop = entry.getValue().getBusinessRuleProposition();
            yvf = prop.getLeftHandSide().getYieldValueFunction();
            if (yvf == null) {
                continue;
            }

            final VerticalPanel vp = new VerticalPanel();
            VerticalPanel propPanel = new VerticalPanel();
            propPanel.setBorderWidth(1);
            propPanel.setWidth("100%");

            SimplePanel leftRightSidepanel = new SimplePanel();
            leftRightSidepanel.setWidth("100%");
            leftRightSidepanel.setStyleName("yvf_fields");
            HorizontalPanel leftAndRightSide = new HorizontalPanel();
            leftRightSidepanel.add(leftAndRightSide);
            propositionsTestPanel.add(propPanel);
            Label propIndex = new Label("P" + ix + ":   ");
            propIndex.setStyleName("propositon_bold");
            leftAndRightSide.add(propIndex);
            leftAndRightSide.add(new Label(GuiUtil.getYVFSymbol(yvf.getYieldValueFunctionType()) + "  "));
            leftAndRightSide.add(new Label(GuiUtil.getComparisonOperatorTypeKeySymbol(prop.getComparisonOperatorTypeKey()) + "  "));
            leftAndRightSide.add(new Label(prop.getRightHandSide().getExpectedValue()));
            propPanel.add(leftRightSidepanel);

            // show static or dynamic fact fields based on selection in Fact Type list box if any
            // a) get the first fact type
            List<FactStructureDTO> factStructureList = yvf.getFactStructureList();
            HorizontalPanel factFieldsPanel = new HorizontalPanel();
            factFieldsPanel.setSpacing(2);
            propPanel.add(factFieldsPanel);
            GuiUtil.addSpaceBelowWidget(factFieldsPanel, "10px");

            // display each fact
            int argIx = 0;
            for (FactStructureDTO fact : factStructureList) {
                argIx++;
                Label argLabel = new Label("arg" + argIx + ":");
                argLabel.setStyleName("propositon_bold");

                if (fact.isStaticFact()) {
                    TextArea factValue = new TextArea();
                    factValue.setName(fact.getFactStructureId());
                    factValue.setText(fact.getStaticValue());
                    testDefinitionTimeFactsWidgets.add(factValue);
                    VerticalPanel staticFact = GuiUtil.addLabelAndFieldVertically(argLabel, new Label(" "), "");
                    factFieldsPanel.add(staticFact);
                    GuiUtil.addSpaceBesideWidget(factFieldsPanel, "5px");
                    Widget staticValue = GuiUtil.addLabelAndFieldVertically(GuiUtil.removeFactTypeKeyPrefix(fact.getFactTypeKey()), factValue, "300px");
                    factFieldsPanel.add(staticValue);
                    GuiUtil.addSpaceBesideWidget(factFieldsPanel, "15px");
                } else {

                    // show dynamic fact TYPE
                    factFieldsPanel.add(GuiUtil.addLabelAndFieldVertically(argLabel, new Label(" "), ""));
                    GuiUtil.addSpaceBesideWidget(factFieldsPanel, "5px");

                    // retrieve individual parameters of this fact
                    VerticalPanel dynamicFactParam = new VerticalPanel();
                    GuiUtil.addSpaceBelowWidget(dynamicFactParam, "15px");
                    Map<String, String> map = fact.getParamValueMap();
                    for (String key : map.keySet()) {
                        if (map.get(key) == null) {
                            continue; // execution key TODO use fact finder service
                        }

                        TextArea factValue = new TextArea();
                        factValue.setName(key);
                        if (key.equals("factParam.studentId")) {
                            factValue.setText("student1");
                        } else {
                            factValue.setText(map.get(key));
                        }

                        if (isExecutionTimeKey(fact.getFactTypeKey(), key)) {
                            testExecutionTimeFactsWidgets.add(factValue);
                        } else {
                            testDefinitionTimeFactsWidgets.add(factValue);
                        }

                        Widget dynamicValue = GuiUtil.addLabelAndFieldVertically(GuiUtil.removeFactParamPrefix(key), factValue, "300px");
                        dynamicFactParam.add(dynamicValue);
                        GuiUtil.addSpaceBelowWidget(factFieldsPanel, "15px");
                    }

                    // now add parameters for this dynamic fact
                    Widget dynamicFact = GuiUtil.addLabelAndFieldVertically(GuiUtil.removeFactTypeKeyPrefix(fact.getFactTypeKey()), dynamicFactParam, "300px");
                    factFieldsPanel.add(dynamicFact);
                    GuiUtil.addSpaceBesideWidget(dynamicFactParam, "15px");

                }
            } // for(facts)
            // propFactsTest.put(prop.get)
        } // for (propositions)

        GuiUtil.addSpaceBelowWidget(propositionsTestPanel, "50px");
        Label testResultsLabel = new Label("Test Results:");
        testResultsLabel.setStyleName("propositon_bold");
        propositionsTestPanel.add(GuiUtil.addLabelAndFieldVertically(testResultsLabel, testResult, ""));
        GuiUtil.addSpaceBelowWidget(propositionsTestPanel, "70px");
        propositionsTestPanel.add(testRuleButton);
    }
}
