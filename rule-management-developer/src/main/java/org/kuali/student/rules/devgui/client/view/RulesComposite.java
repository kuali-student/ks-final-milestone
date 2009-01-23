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
import org.kuali.student.rules.devgui.client.GuiUtil;
import org.kuali.student.rules.devgui.client.IllegalRuleFormatException;
import org.kuali.student.rules.devgui.client.GuiUtil.YieldValueFunctionType;
import org.kuali.student.rules.devgui.client.controller.DevelopersGuiController;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.rules.devgui.client.service.DevelopersGuiService;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.ruleexecution.dto.ExecutionResultDTO;
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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
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

	//final static Logger logger = LoggerFactory.getLogger(RulesComposite.class);
	
    final String FORM_ROW_HEIGHT = "18px";
    final long future_date = 1262376000325L;  // 2010-01-01 12:00 

    // events to be fired to parent controller
    public static class RulesEvent extends MVCEvent{}

    public static class RulesAddEvent extends RulesEvent{}

    public static class RulesUpdateEvent extends RulesEvent{}

    public static class RulesTestEvent extends RulesEvent{}

    // singleton instances of the events
    public static final RulesEvent RULES_EVENT = GWT.create(RulesEvent.class);
    public static final RulesAddEvent RULES_ADD_EVENT = GWT.create(RulesAddEvent.class);
    public static final RulesUpdateEvent RULES_UPDATE_EVENT = GWT.create(RulesUpdateEvent.class);
    public static final RulesTestEvent RULES_REMOVE_EVENT = GWT.create(RulesTestEvent.class);

    // controller and metadata to be looked up externally
    Controller controller;
    ViewMetaData metadata;
    Messages messages;

    // class that binds a widget to a model, instantiation is deferred
    // until application state is guaranteed to be ready
    ModelBinding<RulesHierarchyInfo> binding;

    // widgets used for Rules forms.....
    final BusinessRulesTree rulesTree = new BusinessRulesTree(); // used to browse Rules
    final ScrollPanel rulesBrowserScrollPanel = new ScrollPanel();
    final HorizontalSplitPanel rulesHorizontalSplitPanel = new HorizontalSplitPanel();
    final ScrollPanel rulesScrollPanel = new ScrollPanel();
    final VerticalSplitPanel rulesVerticalSplitPanel = new VerticalSplitPanel();
    final SimplePanel simplePanel = new SimplePanel();
    final TabPanel rulesFormTabs = new TabPanel();
    
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
    //YVF - for now, hard code the number of parameters (1 or 2) and number of facts (up to 2)
    final ListBox yvfListBox = new ListBox();
    
    //first FACT line
    final ListBox yvfFirstFactTypeListBox = new ListBox();
    //final RadioButton yvfFirstFactLookupTypeStatic = new RadioButton("firstFactLookupType", "static", true);
    //final RadioButton yvfFirstFactLookupTypeDynamic = new RadioButton("firstFactLookupType", "dynamic", true);    
    final Label yvfFirstStaticFactLabel = new Label();	//'value'
    final TextArea yvfFirstStaticFactValue = new TextArea();
    final Label yvfFirstFactParamOneLabel = new Label();	//'1st Key'
    final TextBox yvfFirstFactParamOneTextBox = new TextBox();
    final Label yvfFirstFactParamTwoLabel = new Label();	//'2nd Key'
    final TextBox yvfFirstFactParamTwoTextBox = new TextBox();        
    
    //second FACT line
    final Label yvfSecondFactLineLabel = new Label();	//'1st Fact'
    final Label yvfSecondFactTypeLabel = new Label();	//'Fact Type:'
    final ListBox yvfSecondFactTypeListBox = new ListBox();	
    //final RadioButton yvfSecondFactLookupTypeStatic = new RadioButton("secondFactLookupType", "static", true);
    //final RadioButton yvfSecondFactLookupTypeDynamic = new RadioButton("secondFactLookupType", "dynamic", true);    
    final Label yvfSecondStaticFactLabel = new Label();  //'value'
    final TextArea yvfSecondStaticFactValue = new TextArea();
    final Label yvfSecondFactParamOneLabel = new Label();	//'1st Key'
    final TextBox yvfSecondFactParamOneTextBox = new TextBox();    
    final Label yvfSecondFactParamTwoLabel = new Label();	//'2nd Key'
    final TextBox yvfSecondFactParamTwoTextBox = new TextBox();     
    
    //Propositions
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
    final Button cancelPropButton = new Button("Cancel Changes");

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
    final List<TextArea> testStaticFactsWidgets = new ArrayList<TextArea>();    
    final Map<String, String> factValuesForTest = new HashMap<String, String>();
    final List<TextArea> testDynamicFactsWidgets = new ArrayList<TextArea>();
    final Button testRuleButton = new Button("Test Rule");
    final TextArea testReport = new TextArea();
    final TextArea completeRuleTestTextArea = new TextArea();
    final TextArea propCompositionTestTextArea = new TextArea();
    final Label testResult = new Label();

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
    
    //variables representing client internal state
    private BusinessRuleInfoDTO displayedRule = null; // keep copy of business rule so we can update all fields user
    private RulesHierarchyInfo displayedRuleInfo = null; // keep copy of rule meta info
    private Map<Integer, RulePropositionDTO> definedPropositions = new TreeMap<Integer, RulePropositionDTO>();
    private StringBuffer ruleComposition;
    private List<FactTypeInfoDTO> factTypeKeyList = null;  // keep copy of current list of factTypeKeys based on business rule type
    private boolean loadingFactTypeKeyList = false;  //TODO 'loading' icon; also for other drop downs
    private String firstFactTypeKeyListSelectedValue = null;
    private String secondFactTypeKeyListSelectedValue = null;
    private final String STATUS_NOT_STORED_IN_DATABASE = "TO_BE_ENTERED";
    private final String EMPTY_AGENDA_TYPE = "drafts";
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

            // get a reference to our view metadata and internationalization messages
            metadata = ApplicationContext.getViews().get(DevelopersGuiController.VIEW_NAME);
            messages = metadata.getMessages();

            // bind the list to the parent controller's Model of BusinessRuleInfo objects
            Model<RulesHierarchyInfo> model = (Model<RulesHierarchyInfo>) controller.getModel(RulesHierarchyInfo.class);
            binding = new ModelBinding<RulesHierarchyInfo>(model, rulesTree);

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
            rulesScrollPanel.setSize("100%", "0%");    // TODO setSize("100%", "100%");            

            // add tree/form and scroll panel together
            rulesVerticalSplitPanel.setSize("100%", "800px");
            rulesVerticalSplitPanel.setTopWidget(rulesHorizontalSplitPanel);
            rulesVerticalSplitPanel.setBottomWidget(rulesScrollPanel);
            rulesVerticalSplitPanel.setSplitPosition("90%");
            simplePanel.add(rulesVerticalSplitPanel);                   
            
            // add selection event listener to rulesTree widget
            rulesTree.addSelectionListener(new ModelTableSelectionListener<RulesHierarchyInfo>() {
            	
            	//user click on rules tree to select or deselect rules
                public void onSelect(RulesHierarchyInfo modelObject) {

                	// selection was cleared so remove current active rule
                    if (modelObject == null) {                        
                        loadEmptyRule();
                        System.out.println("DEBUG: no selection....");
                        return;
                    }

                    //TODO are you sure? esp. if user changed something - your changes will be lost                   
                    
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
                }
            });

            /****************************************************************************************************************
             * listeners for rule CREATE and UPDATE buttons
             ***************************************************************************************************************/
                                             
            submitRuleButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {               	                	                                        	                 
                    
                    // 1) update the displayed rule copy with data entered
                    if (updateCopyOfDisplayedRule() == false) {
                        return;
                    }
                    
                    // 2) validate draft before submitting - we need at least agenda type and business rule type
                    if (isDisplayedRuleValid("Cannot submit rule") == false) {
                    	return;
                    }                        
                    
                    //make sure rule has draft status 
                    displayedRule.setState(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());  
                    displayedRuleInfo.setStatus(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
                    displayedRule.getMetaInfo().setCreateTime(new Date());
                   
                    // 3) create new draft rule
                    DevelopersGuiService.Util.getInstance().createBusinessRule(displayedRule, new AsyncCallback<String>() {
                        public void onFailure(Throwable caught) {
                            // just re-throw it and let the uncaught exception handler deal with it
                            Window.alert(caught.getMessage());
                            // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                            
                            //now revert the rule status back
                            displayedRule.setState(STATUS_NOT_STORED_IN_DATABASE);  
                            displayedRuleInfo.setStatus(STATUS_NOT_STORED_IN_DATABASE);                            
                        }

                        public void onSuccess(String newRuleID) {
                            System.out.println("Created new rule: " + newRuleID);

                            // update the model
                            RulesEvent toFire = RULES_ADD_EVENT;

                            placeNewDraftInTree(newRuleID);
                            
                            // fire the event and the updated modelobject to the parent controller
                            RulesHierarchyInfo ruleInfo = new RulesHierarchyInfo();
                            ruleInfo.setAgendaType(displayedRuleInfo.getAgendaType());
                            ruleInfo.setBusinessRuleType(displayedRuleInfo.getBusinessRuleType());
                            ruleInfo.setAnchor(displayedRuleInfo.getAnchor());
                            ruleInfo.setBusinessRuleDisplayName(displayedRule.getName());
                            ruleInfo.setBusinessRuleId(newRuleID);
                            rulesTree.add(ruleInfo);
                            displayedRule.setId(newRuleID);

                            loadExistingRule(displayedRule);
                            rulesFormTabs.selectTab(0);
                            GuiUtil.showUserDialog("New rule submitted.");
                        }
                    });
                }
            });

            updateRuleButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {

                  // 1) update the draft with data entered
                    if (updateCopyOfDisplayedRule() == false) {
                        return;
                    }
                   
                    // 2) validate draft before update
                    if (isDisplayedRuleValid("Cannot update rule.") == false) {
                    	return;
                    }                                        
                    
                    // 3) update rule
                    displayedRule.getMetaInfo().setUpdateTime(new Date());
                    
                    DevelopersGuiService.Util.getInstance().updateBusinessRule(displayedRule.getId(), displayedRule, new AsyncCallback<Void>() {
                        public void onFailure(Throwable caught) {
                            // just re-throw it and let the uncaught exception handler deal with it
                            Window.alert(caught.getMessage());
                            // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                        }

                        public void onSuccess(Void voidObj) {
                            GuiUtil.showUserDialog("Rule updated.");
                        }
                    });
                    
                    loadExistingRule(displayedRule);                                        
                }
            });
      
            activateRuleButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {

                    // 1) update the active rule with data entered
                    if (updateCopyOfDisplayedRule() == false) {
                        return;
                    }

                    // 2) validate that the rule entered/changed is correct
                    if (isDisplayedRuleValid("Cannot activate rule.") == false) {
                    	return;
                    }
                    
                    //TODO rulesTree.remove(displayedRuleInfo); //TODO fire update event instead
                    
                    // 3) rule status changes to ACTIVE
                    displayedRule.setState(BusinessRuleStatus.ACTIVE.toString());
                    displayedRuleInfo.setStatus(BusinessRuleStatus.ACTIVE.toString());
                    displayedRuleInfo.setBusinessRuleDisplayName(displayedRule.getName());
                    
                    DevelopersGuiService.Util.getInstance().updateBusinessRule(displayedRule.getId(), displayedRule, new AsyncCallback<Void>() {
                        public void onFailure(Throwable caught) {
                            // just re-throw it and let the uncaught exception handler deal with it
                            Window.alert(caught.getMessage());
                            // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                        }

                        public void onSuccess(Void voidObj) {
                            System.out.println("Rule Activated");
                        }
                    });
                    
                    loadExistingRule(displayedRule);
                    //TODO rulesTree.update(displayedRuleInfo);
                    
                    //TODO...
                    //controller.getEventDispatcher().fireEvent(RulesUpdateEvent.class, displayedRuleInfo);
                }
            });            
            
            createNewVersionButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {

                    // 1) validate that the rule entered/changed data is correct  
                    if (isDisplayedRuleValid("Cannot make a new rule version.") == false) {
                    	return;
                    }

                    // 2) update the active rule with data entered
                    if (updateCopyOfDisplayedRule() == false) {
                        return;
                    }
                    
                    // 3) create new version of this rule
                    DevelopersGuiService.Util.getInstance().updateBusinessRule(displayedRule.getId(), displayedRule, new AsyncCallback<Void>() {
                        public void onFailure(Throwable caught) {
                            // just re-throw it and let the uncaught exception handler deal with it
                            Window.alert(caught.getMessage());
                            // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                        }

                        public void onSuccess(Void voidObj) {
                            GuiUtil.showUserDialog("New Rule Version Created.");
                        }
                    });
                    
                    loadExistingRule(displayedRule);
                }
            });            
            
            retireRuleButton.addClickListener(new ClickListener() {  
                public void onClick(final Widget sender) {
                    displayedRule.setState(BusinessRuleStatus.RETIRED.toString());
                    displayedRuleInfo.setStatus(BusinessRuleStatus.RETIRED.toString());

                    DevelopersGuiService.Util.getInstance().updateBusinessRule(displayedRule.getId(), displayedRule, new AsyncCallback<Void>() {
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        public void onSuccess(Void obj) {
                        	GuiUtil.showUserDialog("Rule retired.");
                        }
                    });
                    
                    loadExistingRule(displayedRule);
                    GuiUtil.showUserDialog("Rule retired.");
                }
            });    
            
            copyRuleButton.addClickListener(new ClickListener() {  
                public void onClick(final Widget sender) {
                	//keep original rule values except rule id, original rule id, and compiled id
                	displayedRule.setId("");
                	displayedRule.setCompiledId("");
                	businessRuleID.setText("");
                    displayedRule.setName("COPY " + displayedRule.getName()); 
                    nameTextBox.setText(displayedRule.getName());                    
                	displayedRule.setState(STATUS_NOT_STORED_IN_DATABASE);
                	ruleStatus.setText(STATUS_NOT_STORED_IN_DATABASE);                              	
                	updateRulesFormButtons(displayedRule.getState());
                	rulesFormTabs.selectTab(0);  
                	GuiUtil.showUserDialog("Rule copied.");
                }
            });  
            
            //  rule in order to create a new rule or void changes to the existing rule
            cancelButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    // TODO "Are you sure?' dialog -> see ui common package for widget
                	loadEmptyRule();
                }
            });

            /****************************************************************************************************************
             * listeners for proposition ADD, UPDATE, DELETE, RESET buttons
             ***************************************************************************************************************/
            propositionsListBox.addChangeListener(new ChangeListener() {
                public void onChange(Widget sender) {
                    ListBox box = ((ListBox) sender);
                    int selectedIndex = box.getSelectedIndex();
                    if (selectedIndex == -1) {
                    	return;
                    }
                    
                    if (definedPropositions.size() > 1) {
                    	removePropButton.setEnabled(true);
                    }
                    
                    System.out.println("--> LIST BOX CHANGE: selected index:" + selectedIndex);
                    
                    //extra check
                    RulePropositionDTO selectedRuleElement = definedPropositions.get(new Integer(box.getValue(selectedIndex)));
                    if (selectedRuleElement == null) {
                    	System.out.println("Selected elemetn NULL?");
                        return;
                    }
                    //first we need to update the currently edited proposition before we switch to new one (update by default)
                    updateSelectedPropositionDTO(selectedPropListBoxIndex);
                    
                    //second we load the selected proposition details
                    selectedPropListBoxIndex = selectedIndex; 
                    populatePropositionDetails(selectedRuleElement);
                }
            });           

            addPropButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {                    

                    //first we need to update the currently edited proposition (update by default)
                    updateSelectedPropositionDTO(propositionsListBox.getSelectedIndex());                  
                	                    
                    int newPropIx = addEmptyPropositionToDTOList();
                    
                    //reorganize propositions
                    updatePropositionListBoxAndDetails(newPropIx);                    
                }
            });            
            
            removePropButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                	
                    if (definedPropositions.size() < 2) {
                    	removePropButton.setEnabled(false);
                    	GuiUtil.showUserDialog("Cannot remove last proposition.");
                    }
                	
                	//TODO are you sure? dialog
                    int selectedProp = propositionsListBox.getSelectedIndex();
                    if ((selectedProp != -1)){
                        // remove the proposition
                        definedPropositions.remove(Integer.parseInt(propositionsListBox.getValue(selectedProp)));

                        // refresh the form
                        updatePropositionListBoxAndDetails(-1);
                        //updatePropButton.setEnabled(false);
                        removePropButton.setEnabled(false);
                    }                    
                }
            });

            cancelPropButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    //updatePropButton.setEnabled(false);
                    //removePropButton.setEnabled(false); 
                    updatePropositionListBoxAndDetails(propositionsListBox.getSelectedIndex());
                }
            });

            propNameTextBox.addFocusListener(new FocusListener() {
                public void onFocus(final Widget sender) {
                }

                public void onLostFocus(final Widget sender) {
                	String propAbreviation = "P" + propositionsListBox.getValue(selectedPropListBoxIndex) +  ":  ";
                	propositionsListBox.setItemText(selectedPropListBoxIndex, propAbreviation + propNameTextBox.getText());
                }
            });
            

            /****************************************************************************************************************
             * listeners for YVF drop downs, COMPOSITION and COMPLETE TEXT elements of a rule
             ***************************************************************************************************************/

            // Yield Value Function list box
            yvfListBox.addChangeListener(new ChangeListener() {            	            	
                public void onChange(final Widget sender) {
                	//TODO: are you sure? dialog
                	clearYVFFactFields();
                	setYVFFactFields(GuiUtil.getListBoxSelectedValue(yvfListBox), false, false);
                }
            });            
            
            yvfFirstFactTypeListBox.addChangeListener(new ChangeListener() {
                public void onChange(final Widget sender) {
                	firstFactTypeKeyListSelectedValue = GuiUtil.getListBoxSelectedValue(yvfFirstFactTypeListBox);
                	if (firstFactTypeKeyListSelectedValue.equals(EMPTY_LIST_BOX_ITEM)) {
                		setFirstFactParamFields(false, false, false);
                	} else if (firstFactTypeKeyListSelectedValue.equals(USER_DEFINED_FACT_TYPE_KEY)) {
                		setFirstFactParamFields(true, true, true);
                	} else {
                		setFirstFactParamFields(true, true, false);
                	} 
                }
            });            

            yvfSecondFactTypeListBox.addChangeListener(new ChangeListener() {
                public void onChange(final Widget sender) {
                	secondFactTypeKeyListSelectedValue = GuiUtil.getListBoxSelectedValue(yvfSecondFactTypeListBox);                	
                	if (secondFactTypeKeyListSelectedValue.equals(EMPTY_LIST_BOX_ITEM)) {
                		setSecondFactParamFields(false, false, false);
                	} else if (secondFactTypeKeyListSelectedValue.equals(USER_DEFINED_FACT_TYPE_KEY)) {
                		setSecondFactParamFields(true, true, true);
                	} else {
                		setSecondFactParamFields(true, true, false);
                	}
                }
            });             
            
            validateRuleButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
               	   //first update the DTO of the proposition being edited
               	   updateSelectedPropositionDTO(selectedPropListBoxIndex);
               	   // check whether the current composition is valid
               	   if (validateRuleComposition() == false) {
                       return; //invalid proposition being edited
              	   }
                }
            });

            propCompositionTextArea.addFocusListener(new FocusListener() {
                public void onFocus(final Widget sender) {
                }

                public void onLostFocus(final Widget sender) {
                	
                	//first update the DTO of the proposition being edited
                	updateSelectedPropositionDTO(selectedPropListBoxIndex);                	
                	
                    // check whether the current composition is valid
                    if (validateRuleComposition() == false) {
                    	return; //invalid proposition being edited
                    }
                    
                    // update Rule Overview text as well
                    completeRuleTextArea.setText(GuiUtil.assembleRuleFromComposition(propCompositionTextArea.getText(), definedPropositions));
                }
            });

            /****************************************************************************************************************
             * various listeners
             ***************************************************************************************************************/

            rulesFormTabs.addTabListener(new TabListener() {

				public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
					return true;
				}

				public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
					//show rule facts for test tab
					if (tabIndex == 3) { //TODO hard coded number
				    	
	                    // 1) update the displayed rule copy with data entered
	                    if (updateCopyOfDisplayedRule() == false) {
	                        return;
	                    }				    	
				    	
				    	//now validate each proposition
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
                	System.out.println("Rule id: " + displayedRule.getId());
                	
                	factValuesForTest.clear();
                	
                	//first, retrieve all static fact values from test tab, assuming empty fields means use current rule values
                	for (TextArea widget : testStaticFactsWidgets) {  
                		if (widget.getText().trim().isEmpty() == false) {
                			factValuesForTest.put(widget.getName(), widget.getText());
                			System.out.println("Adding :" + widget.getName() + " with value " + widget.getText());
                		}
                    }
                	
                	//next, retrieve dynamic facts i.e. go through all propositions and update dynamic facts values
                	for (TextArea widget : testDynamicFactsWidgets) {  
                        if (widget.getText().trim().isEmpty() == false) {
                			factValuesForTest.put(widget.getName(), widget.getText());
                            System.out.println("Adding :" + widget.getName() + " with value " + widget.getText());
                		}
                    }              	
                	
                    DevelopersGuiService.Util.getInstance().executeBusinessRuleTest(displayedRule, factValuesForTest, new AsyncCallback<ExecutionResultDTO>() {
                        public void onFailure(Throwable caught) {
                            // just re-throw it and let the uncaught exception handler deal with it
                            Window.alert(caught.getMessage());
                            // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                        }

                        public void onSuccess(ExecutionResultDTO executionResult) {
                        	StringBuffer executionLog = new StringBuffer();
                        	if (executionResult.getExecutionResult()) {
                        		GuiUtil.showUserDialog("Rule executed.");
                        		if (executionResult.getReport().isSuccessful()) {
                        			executionLog.append("\nSUCCESS: " + executionResult.getReport().getSuccessMessage());
                        		} else {
                        			executionLog.append("\nFAILURE: " + executionResult.getReport().getFailureMessage());
                        		}          
                        		testReport.setText(executionResult.getExecutionLog());
                        	} else {
                        		GuiUtil.showUserDialog("ERROR: Failed to execute rule.");
                        		executionLog.append("\nFailed to execute rule.");
                        		executionLog.append("\nERROR: " + executionResult.getErrorMessage());
                        	}
                        	
                        	testResult.setText(executionLog.toString());
                        }
                    });                	
                }
            });           

            //agenda type can be changed ONLY if draft is in STATE_NOT_IN_DATABASE
            agendaTypesListBox.addChangeListener(new ChangeListener() {
                public void onChange(final Widget sender) {
                	//changing agenda type will  the business rule type and related Anchor Key
                	if (displayedRuleInfo == null) {
                		displayedRuleInfo = new RulesHierarchyInfo();
                	}
                	
                	displayedRuleInfo.setAgendaType(GuiUtil.getListBoxSelectedValue(agendaTypesListBox).trim());
                	displayedRule.setType("");  
                	displayedRule.setAnchorTypeKey("");
                	ruleAnchorType.setText("");
                	populateAgendaAndBusinessRuleTypesListBox();
                }
            });  
            
            //business rule type can be changed ONLY if draft is in STATE_NOT_IN_DATABASE
            businessRuleTypesListBox.addChangeListener(new ChangeListener() {
                public void onChange(final Widget sender) {                	
                    if (businessRuleTypesListBox.getSelectedIndex() == 0) {
                    	//we need to clear related Anchor Type
                    	displayedRule.setAnchorTypeKey("");
                    	ruleAnchorType.setText("");
                    	factTypeKeyList = null;
                    } else {
                    	displayedRule.setType(GuiUtil.getListBoxSelectedValue(businessRuleTypesListBox).trim());
                    	ruleAnchorType.setText(AnchorTypeKey.KUALI_COURSE.name());  //TODO lookup based on business rule type
                    	displayedRule.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.name());
                    	retrieveFactTypes();
                    }                	                	                	
                }
            });             
        }
    }
    
    private void loadEmptyRule() {    	
        displayedRule = createEmptyBusinessRule();
    	clearRuleForms();
        
        //set an empty proposition
        ruleComposition = new StringBuffer();
        definedPropositions.clear();
        int newPropIx = addEmptyPropositionToDTOList();
        updatePropositionListBoxAndDetails(newPropIx);

    	displayedRuleInfo = null;
    	factTypeKeyList = null;
    	firstFactTypeKeyListSelectedValue = null;
    	setRuleStatus(STATUS_NOT_STORED_IN_DATABASE);
    	populateAgendaAndBusinessRuleTypesListBox();
        updateRulesFormButtons(displayedRule.getState()); 
        rulesTree.unSelect();  //clear current rule tree selection      
    }    

    private void loadExistingRule(BusinessRuleInfoDTO ruleInfo) {
    	
        displayedRule = ruleInfo;
        updateRulesFormButtons(ruleInfo.getState());
      
        // store individual propositions in a temporary list & set Rule Composition text
        int propCount = 1;
        ruleComposition = new StringBuffer();
        definedPropositions = new HashMap<Integer, RulePropositionDTO>();

        for (RuleElementDTO elem : ruleInfo.getBusinessRuleElementList()) {
            if (elem.getBusinessRuleElemnetTypeKey().equals(RuleElementType.PROPOSITION.getName())) {
                definedPropositions.put(propCount, elem.getBusinessRuleProposition());
                ruleComposition.append("P" + (propCount++) + " ");
            } else {
                ruleComposition.append(elem.getBusinessRuleElemnetTypeKey() + " ");
            }
        }

        retrieveFactTypes();
        displayActiveRule();  
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
        	ruleStatus.setStylePrimaryName("status-empty"); //TODO warning
        }
    }
    
    private String getRuleStatus() {
    	return displayedRule.getState();
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
        	retireRuleButton.setEnabled(true);        	
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
        	//TODO error message
        }
    }
   
    //populate displayedRule object with data from rule forms
    private boolean updateCopyOfDisplayedRule() {

        // set rule basic info
    	displayedRule.setName(nameTextBox.getText());
    	displayedRule.setDesc(descriptionTextArea.getText());
    	displayedRule.setSuccessMessage(successMessageTextArea.getText());
    	displayedRule.setFailureMessage(failureMessageTextArea.getText());
    	displayedRule.setType(GuiUtil.getListBoxSelectedValue(businessRuleTypesListBox).trim());
    	displayedRule.setAnchorTypeKey(ruleAnchorType.getText());
    	
    	System.out.println("Anchor Type key:" + ruleAnchorType.getText());
    	
    	displayedRule.setAnchorValue(ruleAnchorTextBox.getText());
    	
        // update DTO of the currently edited proposition
    	updateSelectedPropositionDTO(propositionsListBox.getSelectedIndex());
    	
    	//now validate each proposition
        if (validateRuleComposition() == false) {
        	GuiUtil.showUserDialog("ERROR: Invalid rule composition.");
        	return false;
        }
    	
    	//
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
        final DateTimeFormat formatter = DateTimeFormat.getFormat("HH:mm MMM d, yyyy");
        displayedRule.setEffectiveDate(formatter.parse(effectiveDateTextBox.getText())); 
        displayedRule.setExpirationDate(formatter.parse(expiryDateTextBox.getText()));
        
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        //metaInfo.setCreateID(createUserIdTextBox.getText());
        metaInfo.setCreateComment(createCommentTextBox.getText());
        //metaInfo.setUpdateID(updateUserIdTextBox.getText());
        metaInfo.setUpdateComment(updateCommentTextBox.getText());
        
        displayedRule.setMetaInfo(metaInfo);
        
        return true;
    }

    
    /******************************************************************************************************************
     * 
     *                                                     VARIOUS 
     *
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
		Date effectiveDate = new Date();
		Date effectiveEndTime = new Date(future_date);
		newRule.setEffectiveDate(effectiveDate);
		newRule.setExpirationDate(effectiveEndTime);

        // set rule proposition
        LeftHandSideDTO leftSide = new LeftHandSideDTO();
        RightHandSideDTO rightSide = new RightHandSideDTO();
        RulePropositionDTO prop = new RulePropositionDTO();
        prop.setName("");
        prop.setDescription("");
        prop.setLeftHandSide(leftSide);
        prop.setComparisonOperatorTypeKey("");
        prop.setRightHandSide(rightSide);
        prop.setComparisonDataTypeKey("");

        // set rule elements
        List<RuleElementDTO> elemList = new ArrayList<RuleElementDTO>();
        RuleElementDTO elem = new RuleElementDTO();
        elem.setName("");
        elem.setDescription("");
        elem.setBusinessRuleElemnetTypeKey("");
        elem.setOrdinalPosition(6);
        elem.setBusinessRuleProposition(prop);
        elemList.add(elem);
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

    private void placeNewDraftInTree(String newRuleID) {
        String agendaType = GuiUtil.getListBoxSelectedValue(agendaTypesListBox);
        String businessRuleType = GuiUtil.getListBoxSelectedValue(businessRuleTypesListBox);
        String anchor = ruleAnchorTextBox.getText();
        String ruleName = displayedRule.getName();
                       
        //show drafts with other rules for now, marking them with [D]
        displayedRuleInfo = new RulesHierarchyInfo();
    	displayedRuleInfo.setAgendaType(agendaType);
    	displayedRuleInfo.setBusinessRuleType(businessRuleType);
    	displayedRuleInfo.setAnchor(anchor); 
    	displayedRuleInfo.setBusinessRuleDisplayName(ruleName);
    	displayedRuleInfo.setBusinessRuleId(newRuleID);     	    	
    }
    
   
    /******************************************************************************************************************
     * 
     *                                                     VALIDATIONS 
     *
     *******************************************************************************************************************/        
    
    private boolean validateRulesMain() {
      StringBuilder messages = new StringBuilder();
      boolean valid = true;
      boolean fieldValid = true;

      // the if statement after each field validation
      // updates the valid status if valid is still true.
      // In effect valid status will be false if one or more
      // of the fields is invalid
      fieldValid = validate("ruleName", 
          this.nameTextBox.getText(), messages);
      if (valid) {
        valid = fieldValid;
      }
      fieldValid = validate("ruleDescription", 
          this.descriptionTextArea.getText(), messages);
      if (valid) {
        valid = fieldValid;
      }
      fieldValid = validate("ruleSuccessMessage", 
          this.successMessageTextArea.getText(), messages);
      if (valid) {
        valid = fieldValid;
      }
      fieldValid = validate("ruleFailureMessage", 
          this.failureMessageTextArea.getText(), messages);
      if (valid) {
        valid = fieldValid;
      }
      fieldValid = validate("ruleAnchor", 
          this.ruleAnchorTextBox.getText(), messages);
      if (valid) {
        valid = fieldValid;
      }
      // result = ...
      if (!valid) {
        GuiUtil.showUserDialog(messages.toString());
      }
      return valid;
    }
    
    private boolean validate(
        String fieldName, String value, StringBuilder output) {
      boolean valid = true;
      Validator v = metadata.getFields()
          .get(fieldName).getValidatorInstance();
      ValidationResult vr = v.validate(value, fieldName);
      if (vr.isError()) {
        valid = false;
      }
      if (!vr.isOk()) {
        for (String s : vr.getMessages()) {
          output.append(s);
          output.append("\n");
        }
      }
      return valid;
    }

    private boolean isDisplayedRuleValid(String message) {
      
        if (!validateRulesMain()) {
            return false;
        }
 	
        // at least one proposition needs to be used
        if (displayedRule.getName().isEmpty()) {
        	GuiUtil.showUserDialog(message + "\n" + "ERROR: Please enter Rule Name.");
            return false;
        }
        
        // at least one proposition needs to be used
        if (displayedRule.getDesc().isEmpty()) {
        	GuiUtil.showUserDialog(message + "\n" + "ERROR: Please enter Rule Description.");
            return false;
        }     	
        
        if (displayedRuleInfo == null) {
        	GuiUtil.showUserDialog(message + "\n" + "ERROR: Please select Agenda Type.");
            return false;        	
        }
    	
    	//all rules need Agenda Type, Business Rule Type
  		if ((displayedRuleInfo.getAgendaType() == null) || displayedRuleInfo.getAgendaType().equals(EMPTY_LIST_BOX_ITEM) ||
  				displayedRuleInfo.getAgendaType().isEmpty()) {
        	GuiUtil.showUserDialog(message + "\n" + "ERROR: Please select Agenda Type.");
            return false;  
    	}
    	
  		if ((displayedRule.getType() == null) || displayedRule.getType().equals(EMPTY_LIST_BOX_ITEM) ||
  				displayedRule.getType().isEmpty()) {
        	GuiUtil.showUserDialog(message + "\n" + "ERROR: Please select Business Rule Type.");
            return false;  
    	}  		
  		
        ruleComposition = new StringBuffer(propCompositionTextArea.getText());

        // each rule should have at least one proposition
        if ((definedPropositions == null) || (definedPropositions.size() == 0)) {
        	GuiUtil.showUserDialog(message + "\n" + "ERROR: Please add at least one Proposition.");
            return false;
        }

        // at least one proposition needs to be used
        if (ruleComposition.toString().isEmpty()) {
        	GuiUtil.showUserDialog(message + "\n" + "ERROR: Please use at least one defined Proposition.");
            return false;
        }              
        
        return true;
    }
    

    private boolean isPropositionValid(RulePropositionDTO prop) {
        StringBuilder messages = new StringBuilder();

        if (!validate("propositionName", 
            this.propNameTextBox.getText(), messages)) {
          GuiUtil.showUserDialog(messages.toString());
        }
        if (!validate("propositionDescription", 
            this.propDescTextBox.getText(), messages)) {
          GuiUtil.showUserDialog(messages.toString());
        }

        if (prop.getName().trim().isEmpty()) {
        	GuiUtil.showUserDialog("ERROR: Proposition needs name.");
            return false;     		
    	}    	
        
		//we don't want duplicate names
		//if (propNameTextBox.getText() TODO
    	
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
        
        if ((prop.getComparisonOperatorTypeKey() == null) || (prop.getComparisonOperatorTypeKey().trim().isEmpty())
        		|| prop.getComparisonOperatorTypeKey().equals(equals(EMPTY_LIST_BOX_ITEM))) {
        	GuiUtil.showUserDialog("ERROR: Missing Operator.");
            return false;        	
        }        
        
        if ((prop.getRightHandSide() == null) || prop.getRightHandSide().getExpectedValue().isEmpty()) {
        	GuiUtil.showUserDialog("ERROR: Missing Expected Value.");
            return false;
        }    	        
            	
        //verify we are not missing first Fact parameters
        List<FactStructureDTO> factStructureList = yvf.getFactStructureList();

        //1. Fact Type cannot be empty
        if ((factStructureList == null) || (factStructureList.size() == 0)) {
        	GuiUtil.showUserDialog("ERROR: No Fact Type found.");
            return false;    		
    	}
    	
        FactStructureDTO firstFact = factStructureList.get(0);
        if ((firstFact == null) || (firstFact.getFactTypeKey().trim().isEmpty()) || (firstFact.getFactTypeKey().trim().equals(EMPTY_LIST_BOX_ITEM))) {
        	GuiUtil.showUserDialog("ERROR: Missing 1st Fact Type.");
            return false;        	
        }
        System.out.println("key " + firstFact.getFactTypeKey().trim());
        
        //determine whether we have static or dynamic fact
        if (firstFact.isStaticFact()) {
        	if (firstFact.getStaticValue().trim().isEmpty()) {
            	GuiUtil.showUserDialog("ERROR: Missing 1st Fact static value.");
                return false;
        	}
        } else { //dynamic fact - at least the first one has to be available
        	/* not required at this moment...
        	if (yvfFirstFactParamOneTextBox.getText().isEmpty() && yvfFirstFactParamTwoTextBox.getText().isEmpty()) {
            	GuiUtil.showUserDialog("ERROR: Missing 1st Fact dynamic key.");
                return false;
        	} 	*/
        }
        
        //if YVF is INTERSECTION, check on the second Fact parameters
        //System.out.println("SYMBOL: " + YieldValueFunctionType.INTERSECTION.symbol());
        //System.out.println("NAME: " + YieldValueFunctionType.INTERSECTION.name());
        if (yvfType.equals(YieldValueFunctionType.INTERSECTION.name())) { 
            //1. Fact Type cannot be empty
            if (factStructureList.size() == 0) {
            	GuiUtil.showUserDialog("ERROR: Missing 2nd Fact Type.");
                return false;    		
        	}
        	
            FactStructureDTO secondFact = factStructureList.get(1);
            if ((secondFact == null) || (secondFact.getFactTypeKey().trim().isEmpty()) || (secondFact.getFactTypeKey().trim().equals(EMPTY_LIST_BOX_ITEM))) {
            	GuiUtil.showUserDialog("ERROR: Missing 2nd Fact Type.");
                return false;        	
            }
            
            //determine whether we have static or dynamic fact
            if (secondFact.isStaticFact()) {
            	if (secondFact.getStaticValue().trim().isEmpty()) {
                	GuiUtil.showUserDialog("ERROR: Missing 2nd Fact static value.");
                    return false;
            	}
            } else { //dynamic fact - at least the first one has to be available
            	/* not required at this moment...
            	if (secondFact.getText().isEmpty() && yvfFirstFactParamTwoTextBox.getText().isEmpty()) {
                	GuiUtil.showUserDialog("ERROR: Missing 2nd Fact dynamic key.");
                    return false;
            	} 	*/
            }                  	
        }
        
        return true;
    }
    
    
    /******************************************************************************************************************
     * 
     *                                                     FORMS INITIALIZATION 
     *
     *******************************************************************************************************************/    
 
    // TODO: check if changes were made to any form field; if changes made, ask user if they want really want to clear form
    public void clearRuleForms() {
        // clear Main TAB
    	businessRuleID.setText("");
    	ruleStatus.setText("");
        nameTextBox.setText("");
        descriptionTextArea.setText("");
        successMessageTextArea.setText("");
        failureMessageTextArea.setText("");
        GuiUtil.setListBoxSelectionByItemName(agendaTypesListBox, "");  //unselect agenda type list box
        businessRuleTypesListBox.clear();
        ruleAnchorType.setText("");
        ruleAnchorTextBox.setText("");

        // Clear Propositions TAB
        clearPropositionDetails();
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
    
    private void populateAgendaAndBusinessRuleTypesListBox() {
    	        	
    	String ruleAgendaType = (displayedRuleInfo == null ? "" : displayedRuleInfo.getAgendaType());
    	String businessRuleType = (displayedRule == null ? "" : displayedRule.getType()); 
    	
    	System.out.println("DEBUG: agenda type:" + ruleAgendaType);     	
    	System.out.println("Business rule type: " + businessRuleType);       	
    	
    	//populate agenda type list box if it is empty
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
    	
    	System.out.println("STATUS: " + getRuleStatus());
    	
    	//if the rule is stored in database i.e. DRAFT_IN_PROGRESS, ACTIVE, RETIRED then we just populate the drop downs
    	if (getRuleStatus().equals(STATUS_NOT_STORED_IN_DATABASE) == false) {
    		
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
    		
    	//the rule status is NOT_IN_DATABASE....
    	agendaTypesListBox.setEnabled(true);
	    businessRuleTypesListBox.setEnabled(true);    	   	 
        
        //if user did not select any agenda type then we don't retrieve business rule types and leave the list box disabled
        if (ruleAgendaType.trim().isEmpty() || ruleAgendaType.equals(EMPTY_AGENDA_TYPE)) {
        	agendaTypesListBox.setSelectedIndex(0); //show empty agenda
        	businessRuleTypesListBox.clear();
        	businessRuleTypesListBox.setEnabled(false);
        	return;
        }                   
        
   		GuiUtil.setListBoxSelectionByItemName(agendaTypesListBox, ruleAgendaType);
    	
    	//find related business rule types
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
        rulesFormTabs.selectTab(0);
        
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
        updatePropositionListBoxAndDetails(0);
        completeRuleTextArea.setText(GuiUtil.assembleRuleFromComposition(propCompositionTextArea.getText(), definedPropositions));

        // populate Authoring TAB
        effectiveDateTextBox.setText(GuiUtil.formatDate(displayedRule.getEffectiveDate()));
        expiryDateTextBox.setText(GuiUtil.formatDate(displayedRule.getExpirationDate()));
        createTimeLabel.setText(GuiUtil.formatDate(displayedRule.getMetaInfo().getCreateTime()));
        createUserIdLabel.setText(displayedRule.getMetaInfo().getCreateID());
        createCommentTextBox.setText(displayedRule.getMetaInfo().getCreateComment());
        updateTimeLabel.setText(GuiUtil.formatDate(displayedRule.getMetaInfo().getUpdateTime()));
        updateUserIdLabel.setText(displayedRule.getMetaInfo().getUpdateID());
        updateCommentTextBox.setText(displayedRule.getMetaInfo().getUpdateComment());

        // populate Test TAB
        //TODO populatePropositionListBoxAndDetailsTest(0);
        propCompositionTestTextArea.setText(ruleComposition.toString());
        completeRuleTestTextArea.setText(GuiUtil.assembleRuleFromComposition(propCompositionTestTextArea.getText(), definedPropositions));
    }    
    
    private Widget addRulesForm() {
        rulesFormTabs.add(addRulesMainPage(), "Main");
        rulesFormTabs.add(addRulesPropositionPage(), "Propositions");
        rulesFormTabs.add(addRRulesMetaDataPage(), "Authoring");
        rulesFormTabs.add(addRRulesTestPage(), "Test");
        rulesFormTabs.add(addRRulesTestResultsPage(), "Test Results");
        rulesFormTabs.setSize("90%", "550px");
        rulesFormTabs.selectTab(0);        

        //show buttons
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
        rulesFormVerticalPanel.add(rulesFormTabs);
        rulesFormVerticalPanel.add(hp);
        rulesFormVerticalPanel.setSize("100%", "500");

        return rulesFormVerticalPanel;
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
        final Label statusLabel = new Label(messages.get("ruleStatus"));
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
        //effectiveDateTextBox.setWidth("30%");

        // Expiry Date
        flexFormTable.setWidget(3, 0, new Label("Expiry Date"));
        flexFormTable.getCellFormatter().setWidth(3, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(3, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(3, 1, expiryDateTextBox);
        //expiryDateTextBox.setWidth("30%");

        // Create Time
        flexFormTable.setWidget(4, 0, new Label("Create Time"));
        flexFormTable.getCellFormatter().setWidth(4, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(4, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(4, 1, createTimeLabel);
        //createTimeLabel.setWidth("30%");

        // Create User ID
        flexFormTable.setWidget(5, 0, new Label("Create Rule User Id"));
        flexFormTable.getCellFormatter().setWidth(5, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(5, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(5, 1, createUserIdLabel);
        //createUserIdLabel.setWidth("30%");

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
        //updateTimeLabel.setWidth("30%");

        // Update User ID
        flexFormTable.setWidget(8, 0, new Label("Update Rule User Id"));
        flexFormTable.getCellFormatter().setWidth(8, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(8, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(8, 1, updateUserIdLabel);
        //updateUserIdLabel.setWidth("30%");

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
    	
    	FactStructureDTO factStructure = new FactStructureDTO();
        Label ruleCompositionTestLabel = new Label();
        testStaticFactsWidgets.clear();
        testDynamicFactsWidgets.clear();
        
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
        for (Map.Entry<Integer, RulePropositionDTO> entry : definedPropositions.entrySet()) {        	
        	
        	ix++;
        	prop = entry.getValue();        	        	     
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

        	
        	//show static or dynamic fact fields based on selection in Fact Type list box if any
        	// a) get the first fact type
        	List<FactStructureDTO> factStructureList = yvf.getFactStructureList();
        	HorizontalPanel factFieldsPanel = new HorizontalPanel();
        	factFieldsPanel.setSpacing(2);
        	propPanel.add(factFieldsPanel);
        	GuiUtil.addSpaceBelowWidget(factFieldsPanel, "10px");
        	
        	//display each fact
        	int argIx = 0;
            for (FactStructureDTO fact : factStructureList) { 
                argIx++;
                Label argLabel = new Label("arg" + argIx + ":");
                argLabel.setStyleName("propositon_bold");
            	
            	if (fact.isStaticFact()) {
            	    TextArea factValue = new TextArea();
            		factValue.setText(fact.getStaticValue());
                	factValue.setName(fact.getFactStructureId());
                	testStaticFactsWidgets.add(factValue);
                	VerticalPanel staticFact = GuiUtil.addLabelAndFieldVertically(argLabel, new Label(" "), "");
                	factFieldsPanel.add(staticFact);
                	GuiUtil.addSpaceBesideWidget(factFieldsPanel, "5px");                
                	Widget staticValue = GuiUtil.addLabelAndFieldVertically(GuiUtil.removeFactTypeKeyPrefix(fact.getFactTypeKey()), factValue, "300px");
                	factFieldsPanel.add(staticValue); 
                	GuiUtil.addSpaceBesideWidget(factFieldsPanel, "15px");
            	} else {
            		
            	    //show dynamic fact TYPE
                    factFieldsPanel.add(GuiUtil.addLabelAndFieldVertically(argLabel, new Label(" "), ""));
                    GuiUtil.addSpaceBesideWidget(factFieldsPanel, "5px");             	                	                   
                    
                    //retrieve individual parameters of this fact
                    VerticalPanel dynamicFactParam = new VerticalPanel();
                    GuiUtil.addSpaceBelowWidget(dynamicFactParam, "15px");
                    Map<String, String> map = fact.getParamValueMap();
                    for (String key : map.keySet()) {            		
                    	if (map.get(key) == null) {
                    		continue; //execution key  TODO use fact finder service
                    	}            		
                    	
                    	System.out.println("DYN. FACT: key - " + key);
                        System.out.println("DYN. FACT: type - " + fact.getFactTypeKey());
                    	
                        TextArea factValue = new TextArea();
	            		factValue.setText(map.get(key));
	                	factValue.setName(key);
	                	testDynamicFactsWidgets.add(factValue);  //(fact.getFactTypeKey(), factValue);
	                	
	                    Widget dynamicValue = GuiUtil.addLabelAndFieldVertically(key, factValue, "300px");
	                    dynamicFactParam.add(dynamicValue); 
	                    GuiUtil.addSpaceBelowWidget(factFieldsPanel, "15px");   	                		                	                         		            		            	          	
                    }                  
                    
                    //now add parameters for this dynamic fact
                    Widget dynamicFact = GuiUtil.addLabelAndFieldVertically(GuiUtil.removeFactTypeKeyPrefix(fact.getFactTypeKey()), dynamicFactParam, "300px");
                    factFieldsPanel.add(dynamicFact); 
                    GuiUtil.addSpaceBesideWidget(dynamicFactParam, "15px");                        
 
            	}            	            	
            } // for(facts)
            //propFactsTest.put(prop.get)
        } //for (propositions)  
        
 
        GuiUtil.addSpaceBelowWidget(propositionsTestPanel, "50px");
        Label testResultsLabel = new Label("Test Results:");
        testResultsLabel.setStyleName("propositon_bold");
        propositionsTestPanel.add(GuiUtil.addLabelAndFieldVertically(testResultsLabel , testResult, ""));
        GuiUtil.addSpaceBelowWidget(propositionsTestPanel, "70px");
        propositionsTestPanel.add(testRuleButton); 
    }
    
    
    /******************************************************************************************************************
     * 
     *                          PROPOSITIONS 
     *
     *******************************************************************************************************************/

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
        final VerticalPanel flexPropositionDetailsTable = new VerticalPanel();
        flexPropositionDetailsTable.setWidth("100%");
        flexPropositionDetailsTable.setSpacing(5);
        propDetailsBorder.add(flexPropositionDetailsTable);
       

        // Proposition Name
        final HorizontalPanel hpPropName = new HorizontalPanel(); 
        hpPropName.setWidth("100%");
        hpPropName.add(GuiUtil.addLabelAndFieldVertically(messages.get("propositionName"), propNameTextBox, "50%"));
        flexPropositionDetailsTable.add(hpPropName);
        
        // Proposition Description
        final HorizontalPanel hpPropDesc = new HorizontalPanel();        
        hpPropDesc.setWidth("100%");        
        hpPropDesc.add(GuiUtil.addLabelAndFieldVertically(messages.get("propositionDescription"), propDescTextBox, "70%"));
 
        flexPropositionDetailsTable.add(hpPropDesc);
        
        //add proposition left, operator and right hand side  
        yvfListBox.addItem(EMPTY_LIST_BOX_ITEM );
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
        flexPropositionDetailsTable.add(hpLeftOpRightSide);        

        // Yield Value Function details  
        resetYVFFactFields();
        yvfFirstFactParamOneLabel.setText("First Parameter");
        yvfFirstFactParamTwoLabel.setText("Second Parameter");        
        
        //first fact criteria fields        
        final HorizontalPanel hpFirstCriteria = new HorizontalPanel();
    	yvfFirstFactParamOneLabel.setText("1st Param");
    	yvfFirstFactParamOneLabel.setStyleName("yvf_fields");
    	yvfFirstFactParamTwoLabel.setText("2nd Param"); 
    	yvfFirstFactParamTwoLabel.setStyleName("yvf_fields");
    	yvfFirstStaticFactLabel.setText("Value");
    	yvfFirstStaticFactLabel.setStyleName("yvf_fields");
    	Label yvfFirstFactLineLabel = new Label("Arg1:");
    	yvfFirstFactLineLabel.setStyleName("yvf_fields");
    	Label yvfFirstFactTypeLabel = new Label("Fact Type:");
    	yvfFirstFactTypeLabel.setStyleName("yvf_fields");
        hpFirstCriteria.add(GuiUtil.addLabelAndFieldVertically("",yvfFirstFactLineLabel, "100px"));          
        GuiUtil.addSpaceBesideWidget(hpFirstCriteria, "5px");        
        hpFirstCriteria.add(GuiUtil.addLabelAndFieldVertically(yvfFirstFactTypeLabel, yvfFirstFactTypeListBox, "150px"));
        GuiUtil.addSpaceBesideWidget(hpFirstCriteria, "15px");        
        hpFirstCriteria.add(GuiUtil.addLabelAndFieldVertically(yvfFirstFactParamOneLabel, yvfFirstFactParamOneTextBox, "150px"));
        GuiUtil.addSpaceBesideWidget(hpFirstCriteria, "15px");
        hpFirstCriteria.add(GuiUtil.addLabelAndFieldVertically(yvfFirstFactParamTwoLabel, yvfFirstFactParamTwoTextBox, "150px"));
        hpFirstCriteria.add(GuiUtil.addLabelAndFieldVertically(yvfFirstStaticFactLabel, yvfFirstStaticFactValue, "250px"));
        flexPropositionDetailsTable.add(hpFirstCriteria); 
        
        //second fact criteria fields
        final HorizontalPanel hpSecondCriteria = new HorizontalPanel();
        yvfSecondFactLineLabel.setText("Arg2:");
        yvfSecondFactLineLabel.setStyleName("yvf_fields");
        yvfSecondFactTypeLabel.setText("Fact Type:");
        yvfSecondFactTypeLabel.setStyleName("yvf_fields");
        yvfSecondFactParamOneLabel.setText("1st Param");
        yvfSecondFactParamOneLabel.setStyleName("yvf_fields");
        yvfSecondFactParamTwoLabel.setText("2nd Param");
        yvfSecondFactParamTwoLabel.setStyleName("yvf_fields");
    	yvfSecondStaticFactLabel.setText("Value");
    	yvfSecondStaticFactLabel.setStyleName("yvf_fields");
        hpSecondCriteria.add(GuiUtil.addLabelAndFieldVertically("", yvfSecondFactLineLabel, "100px"));
        GuiUtil.addSpaceBesideWidget(hpSecondCriteria, "5px");
        hpSecondCriteria.add(GuiUtil.addLabelAndFieldVertically(yvfSecondFactTypeLabel, yvfSecondFactTypeListBox, "150px"));        
        GuiUtil.addSpaceBesideWidget(hpSecondCriteria, "15px");     
        hpSecondCriteria.add(GuiUtil.addLabelAndFieldVertically(yvfSecondFactParamOneLabel, yvfSecondFactParamOneTextBox, "150px"));
        GuiUtil.addSpaceBesideWidget(hpSecondCriteria, "15px");       
        hpSecondCriteria.add(GuiUtil.addLabelAndFieldVertically(yvfSecondFactParamTwoLabel, yvfSecondFactParamTwoTextBox, "150px"));
        hpSecondCriteria.add(GuiUtil.addLabelAndFieldVertically(yvfSecondStaticFactLabel, yvfSecondStaticFactValue, "250px"));        
        flexPropositionDetailsTable.add(hpSecondCriteria);             

        HorizontalPanel hpButtons = new HorizontalPanel();
        hpButtons.setSpacing(8);      
        hpButtons.add(cancelPropButton);
        flexPropositionDetailsTable.add(hpButtons);        

        horizontalPanel.add(flexPropositionDetailsTable);
        horizontalPanel.setCellHeight(propListPanel, "75%");
        horizontalPanel.setCellWidth(propListPanel, "180px");
        verticalPanel.add(horizontalPanel);

        // **********************************************************
        // Rule composition and complete text
        // **********************************************************
        final FlexTable ruleCompositionFlexTable = new FlexTable();
        ruleCompositionFlexTable.setSize("100%", "100%");

        final Label propCompositionLabel = new Label("Rule Composition");
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
    
   
    private void setFirstFactParamFields(boolean visible, boolean enabled, boolean staticFact) {
    	boolean staticVisible = false;
    	boolean dynamicVisible = false;
    	
    	if (visible) {
	    	if (staticFact) {
	    		staticVisible = true;    		
	    	} else {
	    		dynamicVisible = true;
	    	}
    	}
    	
    	yvfFirstFactParamOneLabel.setVisible(dynamicVisible);
    	yvfFirstFactParamTwoLabel.setVisible(dynamicVisible);
    	yvfFirstFactParamOneTextBox.setVisible(dynamicVisible);
    	yvfFirstFactParamTwoTextBox.setVisible(dynamicVisible);
    	yvfFirstStaticFactLabel.setVisible(staticVisible);
    	yvfFirstStaticFactValue.setVisible(staticVisible);
    	yvfFirstFactParamOneTextBox.setEnabled(enabled);
    	yvfFirstFactParamTwoTextBox.setEnabled(enabled);
    	yvfFirstStaticFactValue.setEnabled(enabled);    	
    }
    
    private void setSecondFactParamFields(boolean visible, boolean enabled, boolean staticFact) {
    	boolean staticVisible = false;
    	boolean dynamicVisible = false;
    	
    	if (visible) {
	    	if (staticFact) {
	    		staticVisible = true;    		
	    	} else {
	    		dynamicVisible = true;
	    	}
    	}
    	
    	yvfSecondFactParamOneLabel.setVisible(dynamicVisible);
    	yvfSecondFactParamTwoLabel.setVisible(dynamicVisible);
    	yvfSecondFactParamOneTextBox.setVisible(dynamicVisible);
    	yvfSecondFactParamTwoTextBox.setVisible(dynamicVisible);
    	yvfSecondStaticFactLabel.setVisible(staticVisible);
    	yvfSecondStaticFactValue.setVisible(staticVisible);
    	yvfSecondFactParamOneTextBox.setEnabled(enabled);
    	yvfSecondFactParamTwoTextBox.setEnabled(enabled);
    	yvfSecondStaticFactValue.setEnabled(enabled);    	
    }
    
    private void resetYVFFactFields() {
    	//no YVF selected from a list box and facts will be by default dynamic
    	setYVFFactFields(null, false, false);
    }
         
    private void setYVFFactFields(String yvfType, boolean firstFactStatic, boolean secondFactStatic) {
    	
    	boolean firstFactTypeListVisible = true;
    	boolean secondFactTypeListVisible = true;
    	
    	if (yvfType != null) {
    		yvfType = yvfType.trim();
    	}
    	
    	if (firstFactTypeKeyListSelectedValue == null) {
    		firstFactTypeListVisible = false;
    	}
    	
    	if (secondFactTypeKeyListSelectedValue == null) {
    		secondFactTypeListVisible = false;
    	}
    	
    	yvfFirstFactTypeListBox.setEnabled(false);
    	yvfFirstFactTypeListBox.setVisible(true);
    	
        yvfSecondFactLineLabel.setVisible(false);
        yvfSecondFactTypeLabel.setVisible(false);        
    	yvfSecondFactTypeListBox.setEnabled(false);    		
		yvfSecondFactTypeListBox.setVisible(false);
    	
    	if ((yvfType == null) || (yvfType.length() == 0) || yvfType.equals(EMPTY_LIST_BOX_ITEM)) {
    		setFirstFactParamFields(false, false, firstFactStatic);
    		setSecondFactParamFields(false, false, secondFactStatic);
    	} else {
        	yvfFirstFactTypeListBox.setEnabled(true);
    		if (yvfType.equals(YieldValueFunctionType.INTERSECTION.symbol())) {    		
	        	//Intersection is assumed to have 2 parameters
	    		setFirstFactParamFields(firstFactTypeListVisible, true, firstFactStatic);
	    		setSecondFactParamFields(secondFactTypeListVisible, true, secondFactStatic); 
	        	yvfSecondFactTypeListBox.setEnabled(true);    		
	    		yvfSecondFactTypeListBox.setVisible(true);
	            yvfSecondFactLineLabel.setVisible(true);
	            yvfSecondFactTypeLabel.setVisible(true);	    		
    		} else {    	
		    	//for other YVF functions, show only the first parameter
				setFirstFactParamFields(firstFactTypeListVisible, true, firstFactStatic);
				setSecondFactParamFields(false, false, secondFactStatic);		
    		}
    	}
    }
    
    
    private void updatePropositionListBoxAndDetails(int selectedPropIx) {
        String propAbreviation;

        //resequence the propositions again and update both proposition list and details of selected one
        propositionsListBox.clear();
        int ix = -1;
        for (Map.Entry<Integer, RulePropositionDTO> entry : definedPropositions.entrySet()) {
            propAbreviation = "P" + entry.getKey() +  ":  ";
            propositionsListBox.addItem(propAbreviation + entry.getValue().getName(), entry.getKey().toString());

            ix++;
            if (ix == selectedPropIx) {
                populatePropositionDetails(entry.getValue());
            }
        }

        //if we are removing a proposition then clear proposition fields but make sure we have
        //at least one empty proposition
        if (selectedPropIx == -1) {
            clearPropositionDetails();
            if (ix == -1) {
            	selectedPropIx = addEmptyPropositionToDTOList();
            }
        }

        propositionsListBox.setSelectedIndex(selectedPropIx); 
        selectedPropListBoxIndex = selectedPropIx;
    }    
    

    private void populatePropositionDetails(RulePropositionDTO prop) {
    	clearPropositionDetails();
        propNameTextBox.setText(prop.getName());
        propDescTextBox.setText(prop.getDescription());        
        operatorsListBox.setSelectedIndex(GuiUtil.getListBoxIndexByName(operatorsListBox, prop.getComparisonOperatorTypeKey()));
        expectedValueTextBox.setText(prop.getRightHandSide().getExpectedValue());        
        populateYVFDetails(prop.getLeftHandSide().getYieldValueFunction(), prop.getName());   
    }   
    
    
    private void populateYVFFactTypeLits() {    	    	
    	GuiUtil.setListBoxSelectionByItemName(yvfFirstFactTypeListBox, firstFactTypeKeyListSelectedValue);
    	
    	//populate second box only if it is applicable:
    	if (secondFactTypeKeyListSelectedValue != null) {
    		GuiUtil.setListBoxSelectionByItemName(yvfSecondFactTypeListBox, secondFactTypeKeyListSelectedValue);
    	}
    }
    
    
    //ASSUMPTION: all proposition are stored in VALID state in both GUI and in the Rule Management service
    private void populateYVFDetails(YieldValueFunctionDTO yvf, String propositionName) {   	
    	
    	String yvfType = (yvf == null ? "" : yvf.getYieldValueFunctionType());
    	
        // do not enable YVF fact parameters if no YVF is selected or we don't know the rule type
        if ((yvf == null) || yvfType.equals(EMPTY_LIST_BOX_ITEM) ||
        	(displayedRule == null) || (displayedRule.getType().trim().isEmpty())) {
        	resetYVFFactFields();
        	return;	//no selection of YVF yet
        }
        
        //1. set YVF list box according to set value
        GuiUtil.setListBoxSelectionByItemName(yvfListBox, yvfType);        
        
    	//2. show static or dynamic fact fields based on selection in Fact Type list box if any
    	// a) get the first fact type
    	List<FactStructureDTO> factStructureList = yvf.getFactStructureList();
        if (factStructureList.size() == 0) {
        	System.out.println("INVALID STATE: did not find any facts for proposition: '" + propositionName + "'");
        	GuiUtil.showUserDialog("ERROR: Missing facts for proposition: '" + propositionName + "'");
        	return; 
        }        	
    	
        // b) find the fact type mode: static or dynamic
        FactStructureDTO firstFact = factStructureList.get(0);
        FactStructureDTO secondFact = null;
        System.out.println("======================================================");
        if (firstFact.isStaticFact()) {
        	//TODO Debug logging  here and elsewhere: 
            System.out.println("Static fact id: " + firstFact.getFactStructureId()); 
        	System.out.println("Static fact value data type: " + firstFact.getStaticValueDataType());  //TODO use somehow
        	System.out.println("Static fact type key: " + firstFact.getFactTypeKey());
        	System.out.println("Static fact VALUE: " + firstFact.getStaticValue()); 
        	firstFactTypeKeyListSelectedValue = USER_DEFINED_FACT_TYPE_KEY;
        	yvfFirstStaticFactValue.setText(firstFact.getStaticValue());
    	} else {
            Map<String, String> map = firstFact.getParamValueMap();
            int ix = 0;
            for (String key : map.keySet()) {
            	if (map.get(key) == null) {
            		continue; //execution key
            	}
            	if (ix++ == 0) {
            		 yvfFirstFactParamOneTextBox.setText(GuiUtil.removeFactParamPrefix(key));
            	} else {
            		yvfFirstFactParamTwoTextBox.setText(GuiUtil.removeFactParamPrefix(key));
            	}            	
            }
            //TODO: query Fact Service on whether it is execution/definition key and what type etc.
            System.out.println("Dynamic Fact Map: " + map.toString());        
            System.out.println("Dynamic Fact Type Key: " + firstFact.getFactTypeKey());
            firstFactTypeKeyListSelectedValue = GuiUtil.removeFactTypeKeyPrefix(firstFact.getFactTypeKey());
    	}
        System.out.println("======================================================");
    	
    	// for INTERSECTION we need two fact types
        secondFactTypeKeyListSelectedValue = null; //assume we don't have second FACT
    	if (yvfType.equals(YieldValueFunctionType.INTERSECTION.name())) {
            if (factStructureList.size() != 2) {
            	//logger.error(e.getMessage(), e);
            	System.out.println("INVALID STATE: did not find 2 facts for INTERSECTION: '" + propositionName + "'");
            	GuiUtil.showUserDialog("ERROR: Missing second Fact for proposition: '" + propositionName + "'");
            	return;  //no facts? TODO log error/user message; throw exception
            }   
            
            //retrieve data from second fact
            secondFact = factStructureList.get(1);
            if (secondFact.isStaticFact()) {		        
		    	secondFactTypeKeyListSelectedValue = USER_DEFINED_FACT_TYPE_KEY;
		    	yvfSecondStaticFactValue.setText(secondFact.getStaticValue());
            } else {            	
                Map<String, String> map = secondFact.getParamValueMap();
                int ix = 0;
                for (String key : map.keySet()) {
                	if (map.get(key) == null) {
                		continue; //execution key
                	}
                	if (ix++ == 0) {
                		 yvfSecondFactParamOneTextBox.setText(GuiUtil.removeFactParamPrefix(key));
                	} else {
                		yvfSecondFactParamTwoTextBox.setText(GuiUtil.removeFactParamPrefix(key));
                	}            	
                }                
                secondFactTypeKeyListSelectedValue = GuiUtil.removeFactTypeKeyPrefix(secondFact.getFactTypeKey());
            }            
    	}
    	
        //3. set fact type list boxes and their values              
    	setYVFFactFields(GuiUtil.getYVFSymbol(yvfType), firstFact.isStaticFact(), (secondFact == null ? false : secondFact.isStaticFact()));
    	               
    	//load a new fact type key list if it is missing (because of new business rule)
    	retrieveFactTypes();
    	
    	System.out.println("factTypeKeyList loaded...");
    	populateYVFFactTypeLits();
    }
    

    private void retrieveFactTypes() {
    	if (factTypeKeyList == null) {    	
	        //load list of factTypeKeys for the given business rule type
	        //TODO in proposition form, make indication that the param list boxes are still being loaded...
        	loadingFactTypeKeyList = true;
    		DevelopersGuiService.Util.getInstance().fetchBusinessRuleType(displayedRule.getType(),
    				displayedRule.getAnchorTypeKey(),new AsyncCallback<BusinessRuleTypeInfoDTO>() {
	            public void onFailure(Throwable caught) {
	                // just re-throw it and let the uncaught exception handler deal with it
	                Window.alert(caught.getMessage());
	            }
	
	            public void onSuccess(BusinessRuleTypeInfoDTO ruleTypeInfo) {
	            	Logger.info("Loading fact type key list: " + ruleTypeInfo.getFactTypeKeyList());
	            	loadingFactTypeKeyList = false;
	            	factTypeKeyList = new ArrayList<FactTypeInfoDTO>();
	            	
	            	yvfFirstFactTypeListBox.clear();
	            	yvfSecondFactTypeListBox.clear();
	            	yvfFirstFactTypeListBox.addItem(EMPTY_LIST_BOX_ITEM);
	            	yvfFirstFactTypeListBox.addItem(USER_DEFINED_FACT_TYPE_KEY);
	            	yvfSecondFactTypeListBox.addItem(EMPTY_LIST_BOX_ITEM);
	            	yvfSecondFactTypeListBox.addItem(USER_DEFINED_FACT_TYPE_KEY);	            	
	            	for (String factTypeKey : ruleTypeInfo.getFactTypeKeyList()) {
	            		factTypeKey = GuiUtil.removeFactTypeKeyPrefix(factTypeKey); 	            		
		                yvfFirstFactTypeListBox.addItem(factTypeKey);
		                yvfSecondFactTypeListBox.addItem(factTypeKey);
	            	}	            	
	            	
	            	populateYVFFactTypeLits(); 
	            	
	                //get fact type info for each fact type from the fact service	            	
        			DevelopersGuiService.Util.getInstance().fetchFactTypeList(ruleTypeInfo.getFactTypeKeyList(),
        																	new AsyncCallback<List<FactTypeInfoDTO>>() {
        	            public void onFailure(Throwable caught) {
        	                // just re-throw it and let the uncaught exception handler deal with it
        	                Window.alert(caught.getMessage());
        	                //loadingFactTypeKeyList = false;
        	                // throw new RuntimeException("Unable to load fact type key list", caught);
        	            }
        	
        	            public void onSuccess(List<FactTypeInfoDTO> factTypeInfo) {        	            	
        	            	factTypeKeyList = factTypeInfo;
        	            }
        	        });   
	            }
	        });        	
    	}    	
    }
    
    
    private void clearPropositionDetails() {
        propNameTextBox.setText("");
        propDescTextBox.setText("");
        yvfListBox.setSelectedIndex(-1);
        clearYVFFactFields();            
        operatorsListBox.setSelectedIndex(-1);
        expectedValueTextBox.setText("");
    }
    
    
    private void clearYVFFactFields() {
    	firstFactTypeKeyListSelectedValue = null;
    	secondFactTypeKeyListSelectedValue = null;
    	GuiUtil.setListBoxSelectionByItemName(yvfFirstFactTypeListBox, "");    	
    	yvfFirstFactParamOneTextBox.setText("");
    	yvfFirstFactParamTwoTextBox.setText("");
    	yvfFirstStaticFactValue.setText("");
    	GuiUtil.setListBoxSelectionByItemName(yvfSecondFactTypeListBox, "");
    	yvfSecondFactParamOneTextBox.setText("");
    	yvfSecondFactParamTwoTextBox.setText("");
    	yvfSecondStaticFactValue.setText("");    	
    	resetYVFFactFields();
    }
           
    private boolean validateRuleComposition() {    	
    	//check that every defined proposition is valid
    	int propIx = -1;
        for (Map.Entry<Integer, RulePropositionDTO> entry : definedPropositions.entrySet()) {
        	propIx++;
        	if (isPropositionValid(entry.getValue()) == false) {
                selectedPropListBoxIndex = propIx; 
                propositionsListBox.setSelectedIndex(propIx);
                populatePropositionDetails(entry.getValue());
        		return false;
        	}
        }    	
    	
    	//now check the proposition composition
    	compositionStatusLabel.setText(GuiUtil.validateRuleComposition(propCompositionTextArea.getText(), definedPropositions.keySet()));
    	if (compositionStatusLabel.getText().equals(GuiUtil.COMPOSITION_IS_VALID_MESSAGE)) {
    		compositionStatusLabel.setStyleName("prop_composition_valid");
    		return true;
    	}

   		compositionStatusLabel.setStyleName("prop_composition_invalid");
   		return false;
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
        if (yvfType.equals(EMPTY_LIST_BOX_ITEM) || yvfType.length() == 0)
        {
        	yvfSelected = false;
        	yvf = null;
        }
        
    	if (yvfSelected) {         
	        yvf.setYieldValueFunctionType(yvfListBox.getValue(yvfListBox.getSelectedIndex()));
	        
	        if (yvf.equals(YieldValueFunctionType.INTERSECTION.name())) {
	        	prop.setComparisonDataTypeKey("java.math.Integer");
	        } else { //if ((yvf.equals(YieldValueFunctionType.SUM.name()) || (yvf.equals(YieldValueFunctionType..name()) {
	        	prop.setComparisonDataTypeKey("java.math.BigDecimal");
	        }  
	        
	        //store set facts in YVF
	    	List<FactStructureDTO> factStructureList = new ArrayList<FactStructureDTO>(); 
	    	yvf.setFactStructureList(factStructureList);                	
	    	FactStructureDTO firstFact = getBlankFactSturectureDTO();
	    	factStructureList.add(firstFact);    	   	
	    	
	        //populate either static or dynamic facts
	    	if (firstFactTypeKeyListSelectedValue != null) {
		        if (firstFactTypeKeyListSelectedValue.trim().equals(USER_DEFINED_FACT_TYPE_KEY)) {
		        	firstFact.setFactTypeKey("fact.static_key");
		        	firstFact.setStaticFact(true);
		        	firstFact.setStaticValue(yvfFirstStaticFactValue.getText());		        		        
		        	firstFact.setStaticValueDataType(GuiUtil.YieldValueFunctionType.fromSymbol(yvfType));
		    	} else {  //dynamic fact...
		    		firstFact.setFactTypeKey(GuiUtil.addFactTypeKeyPrefix(firstFactTypeKeyListSelectedValue));
		    		if (yvfFirstFactParamOneTextBox.getText().isEmpty() == false) {
		    			firstFact.getParamValueMap().put(yvfFirstFactParamOneTextBox.getText(), "");
		    		}
		    		if (yvfFirstFactParamTwoTextBox.getText().isEmpty() == false) {
		    			firstFact.getParamValueMap().put(yvfFirstFactParamTwoTextBox.getText(), "");
		    		}                		                		
		    	}
	    	}
	    	
	    	// for INTERSECTION we need second fact type                    
	    	if (GuiUtil.getListBoxSelectedValue(yvfListBox).trim().equals(YieldValueFunctionType.INTERSECTION.symbol())) {
	        	FactStructureDTO secondFact = getBlankFactSturectureDTO();
	        	factStructureList.add(secondFact);                    
	        	
	            //populate either static or dynamic facts
		    	if (secondFactTypeKeyListSelectedValue != null) {
		            if (secondFactTypeKeyListSelectedValue.trim().equals(USER_DEFINED_FACT_TYPE_KEY)) {
		            	secondFact.setFactTypeKey("fact.static_key");
		            	secondFact.setStaticFact(true);
		            	secondFact.setStaticValue(yvfSecondStaticFactValue.getText());
		            	secondFact.setStaticValueDataType(GuiUtil.YieldValueFunctionType.fromSymbol(YieldValueFunctionType.INTERSECTION.symbol()));
		        	} else {  //dynamic fact...
		        		secondFact.setFactTypeKey(GuiUtil.addFactTypeKeyPrefix(secondFactTypeKeyListSelectedValue));
		        		if (yvfSecondFactParamOneTextBox.getText().isEmpty() == false) {
		        			secondFact.getParamValueMap().put(yvfSecondFactParamOneTextBox.getText(), "");
		        		}
		        		if (yvfSecondFactParamTwoTextBox.getText().isEmpty() == false) {
		        			secondFact.getParamValueMap().put(yvfSecondFactParamTwoTextBox.getText(), "");
		        		}                		                		
		        	}
		    	}
	    	}	             	
    	}
    	
        //populate rest of the proposition
        leftSide.setYieldValueFunction(yvf);
        prop.setLeftHandSide(leftSide);
        RightHandSideDTO rightSide = new RightHandSideDTO();
        rightSide.setExpectedValue(expectedValueTextBox.getText());
        prop.setRightHandSide(rightSide);
        prop.setComparisonOperatorTypeKey(operatorsListBox.getValue(operatorsListBox.getSelectedIndex()));  
    	
        return prop;
    }
    
    //updates stored proposition DTO because we either committing changes or selected a different proposition or a different rule
    private void updateSelectedPropositionDTO(int selectedPropListIx) {
    	   	
        if (selectedPropListIx == -1) { 
        	return; //no proposition selected
        }
        
        int origPropKey = new Integer(propositionsListBox.getValue(selectedPropListIx));    	

        RulePropositionDTO selectedRuleElement = definedPropositions.get(origPropKey);
        if (selectedRuleElement == null) {
            return; //nothing to update
        }
    	
    	RulePropositionDTO newProp = retrievePropositionDTOFromFormFields();
        definedPropositions.remove(origPropKey);
        definedPropositions.put(origPropKey, newProp);                        
        
        // update Rule Overview text as well
        completeRuleTextArea.setText(GuiUtil.assembleRuleFromComposition(propCompositionTextArea.getText(), definedPropositions));    	
    }
    
   
    private int addEmptyPropositionToDTOList() {
        // create an empty rule proposition
        LeftHandSideDTO leftSide = new LeftHandSideDTO();
        RightHandSideDTO rightSide = new RightHandSideDTO();
        RulePropositionDTO prop = new RulePropositionDTO();
        prop.setName("");
        prop.setDescription("");
        prop.setLeftHandSide(leftSide);
        prop.setComparisonOperatorTypeKey("");
        prop.setRightHandSide(rightSide);
        prop.setComparisonDataTypeKey("");
        
    	//finally find the new rule proposition place in the list (at the end)
        int max = 0; int lastPropIx = -1;
        for (Integer key : definedPropositions.keySet()) {
        	lastPropIx++;
            if (key.intValue() > max) {
                max = key.intValue();
            }
        }
        max++;
        lastPropIx++;
        definedPropositions.put(max, prop); 
        
        System.out.println("--> ADDED NEW PROP: key: " + max + ", ix: " + lastPropIx);
        
        removePropButton.setEnabled(true);
        if (lastPropIx == 0) {
        	removePropButton.setEnabled(false);
        }
        
        return lastPropIx;
    }
    
    
    private FactStructureDTO getBlankFactSturectureDTO() {
        FactStructureDTO factStructure = new FactStructureDTO();
        factStructure.setAnchorFlag(false);
        factStructure.setCriteriaTypeInfo(null);
        factStructure.setFactStructureId(Integer.toString(factStructureTemporaryID++));  //java.util.UUID.randomUUID().toString()) or UUIDHelper.genStringUUID());
        factStructure.setFactTypeKey("");
        Map<String, String> map = new HashMap<String, String>();
        factStructure.setParamValueMap(map);
        factStructure.setStaticFact(false);
        factStructure.setStaticValue("");
        factStructure.setStaticValueDataType("");  //TODO here and elsewhere
        return factStructure;
    }
    
    
    @Override
    protected void onUnload() {
        super.onUnload();
        // unlink the binding as it is no longer needed
        binding.unlink();
    }        
}
