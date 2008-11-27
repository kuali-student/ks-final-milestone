/**
 * 
 */
package org.kuali.student.rules.devgui.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelBinding;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.widgets.tables.ModelTableSelectionListener;
import org.kuali.student.rules.devgui.client.GuiUtil;
import org.kuali.student.rules.devgui.client.IllegalRuleFormatException;
import org.kuali.student.rules.devgui.client.GuiUtil.YieldValueFunctionType;
import org.kuali.student.rules.devgui.client.controller.DevelopersGuiController;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.rules.devgui.client.service.DevelopersGuiService;
import org.kuali.student.rules.factfinder.dto.FactCriteriaTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactParamDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleTypeDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.MetaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.StatusDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.Widget;

import org.kuali.student.commons.ui.logging.client.Logger;

/**
 * @author Zdenek
 */
public class RulesComposite extends Composite {

	//final static Logger logger = LoggerFactory.getLogger(RulesComposite.class);
	
    final String FORM_ROW_HEIGHT = "18px";

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
    final TextBox effectiveStartTimeTextBox = new TextBox();
    final TextBox effectiveEndTimeTextBox = new TextBox();
    final TextBox createTimeTextBox = new TextBox();
    final TextBox createUserIdTextBox = new TextBox();
    final TextBox createCommentTextBox = new TextBox();
    final TextBox updateTimeTextBox = new TextBox();
    final TextBox updateUserIdTextBox = new TextBox();
    final TextBox updateCommentTextBox = new TextBox();

    // Test Rule tab
    final TextBox yvfTestTextBox = new TextBox();
    final TextBox propNameTestTextBox = new TextBox();
    final TextBox propDescTestTextBox = new TextBox();
    final TextBox operatorsTestTextBox = new TextBox();
    final ListBox propositionsTestListBox = new ListBox();
    final TextArea completeRuleTestTextArea = new TextArea();
    final TextArea propCompositionTestTextArea = new TextArea();
    final TextBox expectedValueTestTextBox = new TextBox();
    final Button testRuleButton = new Button("Test");

    boolean loaded = false;
    
    public RulesComposite() {
        super.initWidget(simplePanel);
    }

    // main buttons controlling rule life cycle
    final Button submitDraftButton = new Button("Submit Draft");    
    final Button updateDraftButton = new Button("Update Draft");
    final Button activateDraftButton = new Button("Activate Draft");
    final Button makeNewVersionButton = new Button("Make New Version");
    final Button retireRuleButton = new Button("Retire Rule");    
    final Button copyRuleButton = new Button("Make Rule Copy");
    final Button cancelButton = new Button("Cancel Changes");     
    
    //variables representing client internal state
    private BusinessRuleInfoDTO displayedRule = null; // keep copy of business rule so we can update all fields user
    private RulesHierarchyInfo displayedRuleInfo = null; // keep copy of rule meta info
    private Map<Integer, RulePropositionDTO> definedPropositions = new TreeMap<Integer, RulePropositionDTO>();
    private StringBuffer ruleComposition;
    private List<String> factTypeKeyList = null;  // keep copy of current list of factTypeKeys based on business rule type
    private boolean loadingFactTypeKeyList = false;  //TODO 'loading' icon; also for other drop downs
    private String firstFactTypeKeyListSelectedValue = null;
    private String secondFactTypeKeyListSelectedValue = null;
    private final String STATUS_NOT_STORED_IN_DATABASE = "NOT_STORED_IN_DATABASE";
    private final String EMPTY_AGENDA_TYPE = "drafts";
    private final String EMPTY_LIST_BOX_ITEM = "        ";
    private final String USER_DEFINED_FACT_TYPE_KEY = "STATIC  FACT";


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
            // simplePanel.setSize("100%", "100%");
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
                                             
            submitDraftButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {               	                	                                        	                 
                    
                    // 1) update the displayed rule copy with data entered
                    if (updateCopyOfDisplayedRule() == false) {
                        return;
                    }
                    
                    // 2) validate draft before submitting - we need at least agenda type and business rule type
                    if (isDisplayedRuleValid("Cannot submit draft") == false) {
                    	return;
                    }                        
                    
                    //make sure rule has draft status 
                    displayedRule.setStatus(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());  
                    displayedRuleInfo.setStatus(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
                   
                    // 3) create new draft rule
                    DevelopersGuiService.Util.getInstance().createBusinessRule(displayedRule, new AsyncCallback<String>() {
                        public void onFailure(Throwable caught) {
                            // just re-throw it and let the uncaught exception handler deal with it
                            Window.alert(caught.getMessage());
                            // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
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
                            ruleInfo.setBusinessRuleName(displayedRule.getName());
                            ruleInfo.setBusinessRuleId(newRuleID);
                            rulesTree.add(ruleInfo);
                            displayedRule.setBusinessRuleId(newRuleID);

                            loadExistingRule(displayedRule);
                            rulesFormTabs.selectTab(0);
                            GuiUtil.showUserDialog("Initial data submitted. New draft created.");
                        }
                    });
                }
            });

            updateDraftButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {                	
                	
                    // 1) update the draft with data entered
                    if (updateCopyOfDisplayedRule() == false) {
                        return;
                    }
                   
                    // 2) validate draft before update
                    if (isDisplayedRuleValid("Cannot update draft.") == false) {
                    	return;
                    }                                        
                    
                    DevelopersGuiService.Util.getInstance().updateBusinessRule(displayedRule.getBusinessRuleId(), displayedRule, new AsyncCallback<StatusDTO>() {
                        public void onFailure(Throwable caught) {
                            // just re-throw it and let the uncaught exception handler deal with it
                            Window.alert(caught.getMessage());
                            // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                        }

                        public void onSuccess(StatusDTO updateStatus) {
                            System.out.println("Updated rule DRAFT: " + updateStatus.isSuccess());
                        }
                    });
                    
                    loadExistingRule(displayedRule);
                    
                    GuiUtil.showUserDialog("Draft updated.");
                }
            });
      
            activateDraftButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {

                    // 1) update the active rule with data entered
                    if (updateCopyOfDisplayedRule() == false) {
                        return;
                    }

                    // 2) validate that the rule entered/changed is correct
                    if (isDisplayedRuleValid("Cannot activate draft.") == false) {
                    	return;
                    }
                    
                    //TODO rulesTree.remove(displayedRuleInfo); //TODO fire update event instead
                    
                    // 3) activated Draft will become a rule
                    displayedRule.setStatus(BusinessRuleStatus.ACTIVE.toString());
                    displayedRuleInfo.setStatus(BusinessRuleStatus.ACTIVE.toString());
                    displayedRuleInfo.setBusinessRuleName(displayedRule.getName());
                    
                    DevelopersGuiService.Util.getInstance().updateBusinessRule(displayedRule.getBusinessRuleId(), displayedRule, new AsyncCallback<StatusDTO>() {
                        public void onFailure(Throwable caught) {
                            // just re-throw it and let the uncaught exception handler deal with it
                            Window.alert(caught.getMessage());
                            // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                        }

                        public void onSuccess(StatusDTO updateStatus) {
                            System.out.println("Activated draft: " + updateStatus.isSuccess());
                        }
                    });
                    
                    loadExistingRule(displayedRule);
                    //TODO rulesTree.update(displayedRuleInfo);
                    
                    GuiUtil.showUserDialog("Draft activated.");
                    
                    //TODO...
                    //controller.getEventDispatcher().fireEvent(RulesUpdateEvent.class, displayedRuleInfo);
                }
            });            
            
            makeNewVersionButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {

                    // 1) validate that the rule entered/changed data is correct  
                    if (isDisplayedRuleValid("Cannot make a new rule version.") == false) {
                    	return;
                    }

                    // 2) update the active rule with data entered
                    if (updateCopyOfDisplayedRule() == false) {
                        return;
                    }
                    
                    //TODO retire this rule and make a new version - talk to Kamal
                    
                    DevelopersGuiService.Util.getInstance().updateBusinessRule(displayedRule.getBusinessRuleId(), displayedRule, new AsyncCallback<StatusDTO>() {
                        public void onFailure(Throwable caught) {
                            // just re-throw it and let the uncaught exception handler deal with it
                            Window.alert(caught.getMessage());
                            // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                        }

                        public void onSuccess(StatusDTO updateStatus) {
                            System.out.println("Updated active rule: " + updateStatus.isSuccess());
                        }
                    });
                    
                    loadExistingRule(displayedRule);
                }
            });            
            
            retireRuleButton.addClickListener(new ClickListener() {  
                public void onClick(final Widget sender) {
                    
                    //retire Rule
                    displayedRule.setStatus(BusinessRuleStatus.RETIRED.toString());
                    displayedRuleInfo.setStatus(BusinessRuleStatus.RETIRED.toString());

                    DevelopersGuiService.Util.getInstance().updateBusinessRule(displayedRule.getBusinessRuleId(), displayedRule, new AsyncCallback<StatusDTO>() {
                        public void onFailure(Throwable caught) {
                            // just re-throw it and let the uncaught exception handler deal with it
                            Window.alert(caught.getMessage());
                            // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                        }

                        public void onSuccess(StatusDTO updateStatus) {
                        	if (updateStatus.isSuccess()) {
                        		GuiUtil.showUserDialog("Rule retired.");
                        	} else {
                        		GuiUtil.showUserDialog("ERROR: Failed to retire rule.");
                        	}
                        }
                    });
                    
                    loadExistingRule(displayedRule);
                    GuiUtil.showUserDialog("Rule retired.");
                }
            });    
            
            copyRuleButton.addClickListener(new ClickListener() {  
                public void onClick(final Widget sender) {
                	//new draft will be positioned in the tree
                	//placeNewDraftInTree("");                	
                	
                	//keep original rule values except rule id
                	displayedRule.setBusinessRuleId("");
                	businessRuleID.setText("");
                	displayedRule.setStatus(STATUS_NOT_STORED_IN_DATABASE);
                	ruleStatus.setText(STATUS_NOT_STORED_IN_DATABASE);
                	displayedRule.setName("COPY " + displayedRule.getName());;                                	
                	nameTextBox.setText(displayedRule.getName());
                	updateRulesFormButtons(displayedRule.getStatus());
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
                    if (updateSelectedPropositionDTO(selectedPropListBoxIndex) == false) {
                    	//propositionsListBox.setSelectedIndex(selectedPropIndex); //revert back to current proposition
                    	return;  //proposition edits are invalid  TODO - not necessary???
                    }
                    
                    //second we load the selected proposition details
                    selectedPropListBoxIndex = selectedIndex; 
                    populatePropositionDetails(selectedRuleElement);
                }
            });
            
            /* update proposition is by default at this moment...
            updatePropButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    int selectedProp = propositionsListBox.getSelectedIndex();
                    if (selectedProp != -1) {                        
                    	updateSelectedProposition();
                    }
                }
            }); */

            addPropButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {                    

                    //first we need to update the currently edited proposition (update by default)
                    if (updateSelectedPropositionDTO(propositionsListBox.getSelectedIndex()) == false) {
                    	return;  //proposition edits are invalid
                    }                   
                	                    
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

            testRuleButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                // TODO let user know if the rule was modified but not activated i.e. they are testing current rule
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
                	displayedRule.setBusinessRuleTypeKey("");  
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
                    	displayedRule.setBusinessRuleTypeKey(GuiUtil.getListBoxSelectedValue(businessRuleTypesListBox).trim());
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
        updateRulesFormButtons(displayedRule.getStatus()); 
        rulesTree.unSelect();  //clear current rule tree selection      
    }    

    private void loadExistingRule(BusinessRuleInfoDTO ruleInfo) {
    	
        displayedRule = ruleInfo;
        updateRulesFormButtons(ruleInfo.getStatus());
      
        // store individual propositions in a temporary list & set Rule Composition text
        int propCount = 1;
        ruleComposition = new StringBuffer();
        definedPropositions = new HashMap<Integer, RulePropositionDTO>();

        for (RuleElementDTO elem : ruleInfo.getRuleElementList()) {
            if (elem.getOperation().equals(RuleElementType.PROPOSITION.getName())) {
                definedPropositions.put(propCount, elem.getRuleProposition());
                ruleComposition.append("P" + (propCount++) + " ");
            } else {
                ruleComposition.append(elem.getOperation() + " ");
            }
        }

        retrieveFactTypes();
        displayActiveRule();  
    }
    
    private void setRuleStatus(String status) {
    	displayedRule.setStatus(status);
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
    	return displayedRule.getStatus();
    }
    
    private void updateRulesFormButtons(String ruleStatus) {  
    	
    	if (ruleStatus.equalsIgnoreCase(STATUS_NOT_STORED_IN_DATABASE)) {
        	submitDraftButton.setEnabled(true);
        	updateDraftButton.setEnabled(false);        	
        	activateDraftButton.setEnabled(false);        	
        	makeNewVersionButton.setEnabled(false); 
        	retireRuleButton.setEnabled(false);        	
        	copyRuleButton.setEnabled(false);
        	cancelButton.setEnabled(true);  
        	return;
    	} else if (ruleStatus.equalsIgnoreCase(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString())) {
        	submitDraftButton.setEnabled(false);  
        	updateDraftButton.setEnabled(true); 
        	activateDraftButton.setEnabled(true); 
        	makeNewVersionButton.setEnabled(false);     	
        	retireRuleButton.setEnabled(false);        	
        	copyRuleButton.setEnabled(true);
        	cancelButton.setEnabled(true); 
        } else if (ruleStatus.equalsIgnoreCase(BusinessRuleStatus.ACTIVE.toString())) {
        	submitDraftButton.setEnabled(false);
        	updateDraftButton.setEnabled(false);        	
        	activateDraftButton.setEnabled(false);   
        	makeNewVersionButton.setEnabled(true);
        	retireRuleButton.setEnabled(true);        	
        	copyRuleButton.setEnabled(true);
        	cancelButton.setEnabled(true);             
        } else if (ruleStatus.equalsIgnoreCase(BusinessRuleStatus.RETIRED.toString())) {
        	submitDraftButton.setEnabled(false);
        	updateDraftButton.setEnabled(false);        	
        	activateDraftButton.setEnabled(false);   
        	makeNewVersionButton.setEnabled(false);
        	retireRuleButton.setEnabled(false);        	
        	copyRuleButton.setEnabled(true);
        	cancelButton.setEnabled(true); 
       } else {
        	//TODO error message
        }
    }
   
    private boolean updateCopyOfDisplayedRule() {

        // set rule basic info
    	displayedRule.setName(nameTextBox.getText());
    	displayedRule.setDescription(descriptionTextArea.getText());
    	displayedRule.setSuccessMessage(successMessageTextArea.getText());
    	displayedRule.setFailureMessage(failureMessageTextArea.getText());
    	displayedRule.setBusinessRuleTypeKey(GuiUtil.getListBoxSelectedValue(businessRuleTypesListBox).trim());
    	displayedRule.setAnchorTypeKey(ruleAnchorType.getText());
    	
    	System.out.println("Anchor Type key:" + ruleAnchorType.getText());
    	
    	displayedRule.setAnchorValue(ruleAnchorTextBox.getText());
    	displayedRule.setEffectiveStartTime(new Date()); // TODO - add to form
    	displayedRule.setEffectiveEndTime(new Date()); // TODO - add to form

        // validate and then update DTO of the currently edited proposition
    	if (updateSelectedPropositionDTO(propositionsListBox.getSelectedIndex()) == false) {
    		return false; //proposition edits are invalid
    	}
    	
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
	        displayedRule.setRuleElementList(elemList);
    	}
        
        // set meta info
        final DateTimeFormat formatter = DateTimeFormat.getFormat("HH:mm MMM d, yyyy");
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateID(createUserIdTextBox.getText());
        metaInfo.setCreateTime(new Date()); //TOOD formatter.parse(createTimeTextBox.getText()));
        metaInfo.setCreateComment(createCommentTextBox.getText());
        metaInfo.setUpdateID(updateUserIdTextBox.getText());
        metaInfo.setUpdateTime(new Date()); //formatter.parse(updateTimeTextBox.getText()));
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

        newRule.setBusinessRuleId("");
        newRule.setStatus(STATUS_NOT_STORED_IN_DATABASE);
        newRule.setName("");
        newRule.setDescription("");
        newRule.setSuccessMessage("");
        newRule.setFailureMessage("");
        newRule.setBusinessRuleTypeKey("");
        newRule.setAnchorTypeKey("");
        newRule.setAnchorValue("");
		Date effectiveStartTime = new Date(); //GuiUtil.createDate(2000, 1, 1, 12, 00);
		Date effectiveEndTime = new Date(); //GuiUtil.createDate(2010, 1, 1, 12, 00);
		newRule.setEffectiveStartTime(effectiveStartTime);
		newRule.setEffectiveEndTime(effectiveEndTime);

        // set rule proposition
        LeftHandSideDTO leftSide = new LeftHandSideDTO();
        RightHandSideDTO rightSide = new RightHandSideDTO();
        RulePropositionDTO prop = new RulePropositionDTO();
        prop.setName("");
        prop.setDescription("");
        prop.setLeftHandSide(leftSide);
        prop.setComparisonOperatorType("");
        prop.setRightHandSide(rightSide);
        prop.setComparisonDataType("");

        // set rule elements
        List<RuleElementDTO> elemList = new ArrayList<RuleElementDTO>();
        RuleElementDTO elem = new RuleElementDTO();
        elem.setName("");
        elem.setDescription("");
        elem.setOperation("");
        elem.setOrdinalPosition(6);
        elem.setRuleProposition(prop);
        elemList.add(elem);
        newRule.setRuleElementList(elemList);

        // set meta info
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateID("");
        metaInfo.setCreateComment("");
        metaInfo.setUpdateTime(new Date());
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
    	displayedRuleInfo.setBusinessRuleName(ruleName);
    	displayedRuleInfo.setBusinessRuleId(newRuleID);     	    	
    }
    
   
    /******************************************************************************************************************
     * 
     *                                                     VALIDATIONS 
     *
     *******************************************************************************************************************/        
    
    private boolean isDisplayedRuleValid(String message) {
 	
        // at least one proposition needs to be used
        if (displayedRule.getName().isEmpty()) {
        	GuiUtil.showUserDialog(message + "\n" + "ERROR: Please enter Rule Name.");
            return false;
        }
        
        // at least one proposition needs to be used
        if (displayedRule.getDescription().isEmpty()) {
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
    	
  		if ((displayedRule.getBusinessRuleTypeKey() == null) || displayedRule.getBusinessRuleTypeKey().equals(EMPTY_LIST_BOX_ITEM) ||
  				displayedRule.getBusinessRuleTypeKey().isEmpty()) {
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
        
        // TODO make form read-only while validating/creating/updating the rule???
        //return (GuiUtil.validateRuleComposition(propCompositionTextArea.getText(), definedPropositions.keySet()).equals(GuiUtil.COMPOSITION_IS_VALID_MESSAGE));
    }
    

    private boolean isPropositionValid(RulePropositionDTO prop) {
    	
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
    	System.out.println("YVF Type: " + yvfType);
        if ((yvfType.equals(EMPTY_LIST_BOX_ITEM)) || (yvfType.trim().isEmpty())) {
        	GuiUtil.showUserDialog("ERROR: YVF cannot be empty.");
            return false;
        }    	
        
        if ((prop.getComparisonOperatorType() == null) || (prop.getComparisonOperatorType().trim().isEmpty())
        		|| prop.getComparisonOperatorType().equals(equals(EMPTY_LIST_BOX_ITEM))) {
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
        System.out.println("key " + firstFact.getFactTypeKey().trim());
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
        System.out.println("SYMBOL: " + YieldValueFunctionType.INTERSECTION.symbol());
        System.out.println("NAME: " + YieldValueFunctionType.INTERSECTION.name());
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
        //populateAgendaAndBusinessRuleTypesListBox();
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
        effectiveStartTimeTextBox.setText("");
        effectiveEndTimeTextBox.setText("");
        createTimeTextBox.setText("");
        createUserIdTextBox.setText("");
        updateTimeTextBox.setText("");
        updateUserIdTextBox.setText("");
    }    
    
    private void populateAgendaAndBusinessRuleTypesListBox() {
    	        	
    	String ruleAgendaType = (displayedRuleInfo == null ? "" : displayedRuleInfo.getAgendaType());
    	String businessRuleType = (displayedRule == null ? "" : displayedRule.getBusinessRuleTypeKey()); 
    	
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
                
                if (displayedRule.getBusinessRuleTypeKey().trim().isEmpty() == false) {
                	GuiUtil.setListBoxSelectionByItemName(businessRuleTypesListBox, displayedRule.getBusinessRuleTypeKey());
                }
            }
        });          
    }         
    
    public void displayActiveRule() {

        final DateTimeFormat formatter = DateTimeFormat.getFormat("HH:mm MMM d, yyyy");

        rulesFormTabs.selectTab(0);
        
        // populate Main TAB
        businessRuleID.setText(displayedRule.getBusinessRuleId());
        setRuleStatus(displayedRule.getStatus());
        nameTextBox.setText(displayedRule.getName());
        descriptionTextArea.setText(displayedRule.getDescription());
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
        effectiveStartTimeTextBox.setText(formatter.format(new Date())); //TODO displayedRule.getEffectiveStartTime()));
        effectiveEndTimeTextBox.setText(formatter.format(new Date())); //displayedRule.getEffectiveEndTime()));
        createTimeTextBox.setText(formatter.format(new Date())); //displayedRule.getMetaInfo().getCreateTime()));
        createUserIdTextBox.setText(displayedRule.getMetaInfo().getCreateID());
        createCommentTextBox.setText(displayedRule.getMetaInfo().getCreateComment());
        updateTimeTextBox.setText(formatter.format(new Date())); //displayedRule.getMetaInfo().getUpdateTime()));
        updateUserIdTextBox.setText(displayedRule.getMetaInfo().getUpdateID());
        updateCommentTextBox.setText(displayedRule.getMetaInfo().getUpdateComment());

        // populate Test TAB
        //TODO populatePropositionListBoxAndDetailsTest(0);
        propCompositionTestTextArea.setText(ruleComposition.toString());
        completeRuleTestTextArea.setText(GuiUtil.assembleRuleFromComposition(propCompositionTestTextArea.getText(), definedPropositions));
    }    
    
    private Widget addRulesForm() {
        rulesFormTabs.add(addRulesMainPage(), "Main");
        rulesFormTabs.add(addRulesPropositionPage(), "Propositions");
        rulesFormTabs.add(addRRulesMetaDataPage(), "Meta Data");
        rulesFormTabs.add(addRRulesTestPage(), "Test");
        rulesFormTabs.setSize("90%", "550px");
        rulesFormTabs.selectTab(0);

        //show buttons
        HorizontalPanel space = new HorizontalPanel();
        space.setWidth("20px");
        HorizontalPanel hp = new HorizontalPanel();
        hp.setSpacing(8);                
        hp.add(submitDraftButton);
        hp.add(updateDraftButton);
        hp.add(activateDraftButton);
        hp.add(makeNewVersionButton);
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
        final Label ruleID = new Label("ID");
        flexFormTable.setWidget(ix, 0, ruleID);
        //flexFormTable.getCellFormatter().setWidth(ix, 1, "200px");

        flexFormTable.setWidget(ix, 1, businessRuleID);
        //businessRuleID.setWidth("50%");  
        
        // business rule status
        ix++;        
        final Label statusLabel = new Label("Status");
        flexFormTable.setWidget(ix, 0, statusLabel);
        flexFormTable.getCellFormatter().setWidth(ix, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(ix, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(ix, 1, ruleStatus);
        ruleStatus.setWidth("25%");                           
        
        // Name
        ix++;
        final Label nameLabel = new Label("Name");
        flexFormTable.setWidget(ix, 0, nameLabel);
        flexFormTable.getCellFormatter().setWidth(ix, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(ix, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(ix, 1, nameTextBox);
        nameTextBox.setWidth("50%");     
        
        // Description
        ix++;
        final Label descriptionLabel = new Label("Description");
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
        final Label successMessageLabel = new Label("Success Message");
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
        final Label failureMessageLabel = new Label("Failure Message");
        flexFormTable.setWidget(ix, 0, failureMessageLabel);
        flexFormTable.getCellFormatter().setVerticalAlignment(ix, 0, HasVerticalAlignment.ALIGN_TOP);
        flexFormTable.getCellFormatter().setHeight(ix, 0, FORM_ROW_HEIGHT);
        flexFormTable.getCellFormatter().setWidth(ix, 0, "200px");

        flexFormTable.setWidget(ix, 1, failureMessageTextArea);
        failureMessageTextArea.setSize("75%", "100%");
        flexFormTable.getCellFormatter().setHeight(ix, 1, "93px");

        // Agenda Type
        ix++;
        final Label agendaTypeLabel = new Label("Agenda Type");
        flexFormTable.setWidget(ix, 0, agendaTypeLabel);
        flexFormTable.getCellFormatter().setHeight(ix, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(ix, 1, agendaTypesListBox);
        flexFormTable.getCellFormatter().setHeight(ix, 1, FORM_ROW_HEIGHT);        
        
        // Business Rule Type
        ix++;
        final Label businessRuleTypeLabel = new Label("Business Rule Type");
        flexFormTable.setWidget(ix, 0, businessRuleTypeLabel);
        flexFormTable.getCellFormatter().setHeight(ix, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(ix, 1, businessRuleTypesListBox);
        flexFormTable.getCellFormatter().setHeight(ix, 1, FORM_ROW_HEIGHT);

        // Anchor Type
        ix++;
        final Label anchorTypeLabel = new Label("Anchor Type:");
        flexFormTable.setWidget(ix, 0, anchorTypeLabel);
        flexFormTable.getCellFormatter().setHeight(ix, 0, FORM_ROW_HEIGHT);
        
        flexFormTable.setWidget(ix, 1, ruleAnchorType);
        flexFormTable.getCellFormatter().setHeight(ix, 1, FORM_ROW_HEIGHT);       
        
        // Anchor
        ix++;
        final Label anchorLabel = new Label("Anchor");
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

        // Effective Start Time
        final Label effectiveStartTimeLabel = new Label("Effective Start Time");
        flexFormTable.setWidget(2, 0, effectiveStartTimeLabel);
        flexFormTable.getCellFormatter().setWidth(2, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(2, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(2, 1, effectiveStartTimeTextBox);
        effectiveStartTimeTextBox.setWidth("30%");

        // Effective End Time
        final Label effectiveEndTimeLabel = new Label("Effective End Time");
        flexFormTable.setWidget(3, 0, effectiveEndTimeLabel);
        flexFormTable.getCellFormatter().setWidth(3, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(3, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(3, 1, effectiveEndTimeTextBox);
        effectiveEndTimeTextBox.setWidth("30%");

        // Create Time
        final Label createTimeLabel = new Label("Create Time");
        flexFormTable.setWidget(4, 0, createTimeLabel);
        flexFormTable.getCellFormatter().setWidth(4, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(4, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(4, 1, createTimeTextBox);
        createTimeTextBox.setWidth("30%");

        // Create User ID
        final Label createUserIdLabel = new Label("Create Rule User Id");
        flexFormTable.setWidget(5, 0, createUserIdLabel);
        flexFormTable.getCellFormatter().setWidth(5, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(5, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(5, 1, createUserIdTextBox);
        createUserIdTextBox.setWidth("30%");

        // Create Comment
        final Label createCommentLabel = new Label("Create Comment");
        flexFormTable.setWidget(6, 0, createCommentLabel);
        flexFormTable.getCellFormatter().setWidth(6, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(6, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(6, 1, createCommentTextBox);
        createCommentTextBox.setWidth("30%");

        // Update Time
        final Label updateTimeLabel = new Label("Update Time");
        flexFormTable.setWidget(7, 0, updateTimeLabel);
        flexFormTable.getCellFormatter().setWidth(7, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(7, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(7, 1, updateTimeTextBox);
        updateTimeTextBox.setWidth("30%");

        // Update User ID
        final Label updateUserIdLabel = new Label("Update Rule User Id");
        flexFormTable.setWidget(8, 0, updateUserIdLabel);
        flexFormTable.getCellFormatter().setWidth(8, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(8, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(8, 1, updateUserIdTextBox);
        updateUserIdTextBox.setWidth("30%");

        // Update Comment
        final Label updateCommentLabel = new Label("Update Comment");
        flexFormTable.setWidget(9, 0, updateCommentLabel);
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
        // propositionsFlexTable.getFlexCellFormatter().setColSpan(1, 1, 2);
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

        propositionsTestListBox.setVisibleItemCount(10);
        propositionsTestListBox.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                ListBox box = ((ListBox) sender);
                RulePropositionDTO selectedRuleElement = definedPropositions.get(new Integer(box.getValue(box.getSelectedIndex())));
                if (selectedRuleElement == null) {
                    return;
                }
                populatePropositionDetails(selectedRuleElement); // TODO
            }
        });
        propositionsTestListBox.setWidth("150px");

        // **********************************************************
        // Propositions List
        // **********************************************************
        final VerticalPanel propListPanel = new VerticalPanel();
        final Label propositionsLabel = new Label("Propositions");
        propListPanel.add(propositionsLabel);
        propListPanel.add(propositionsTestListBox);

        horizontalPanel.add(propListPanel);

        // **********************************************************
        // Propositions details
        // **********************************************************
        final SimplePanel propDetailsBorder = new SimplePanel();
        final FlexTable flexPropositionDetailsTable = new FlexTable();
        propDetailsBorder.add(flexPropositionDetailsTable);

        // Proposition Name
        final VerticalPanel vp = new VerticalPanel();
        vp.setWidth("100%");
        final Label propNameLabel = new Label("Name");
        vp.add(propNameLabel);
        propNameTestTextBox.setWidth("50%");
        propNameTestTextBox.setReadOnly(true);
        vp.add(propNameTestTextBox);
        flexPropositionDetailsTable.getFlexCellFormatter().setColSpan(0, 1, 3);
        flexPropositionDetailsTable.setWidget(0, 1, vp);

        // Proposition Description
        final VerticalPanel vp0 = new VerticalPanel();
        vp0.setWidth("100%");
        final Label propDescLabel = new Label("Description");
        vp0.add(propDescLabel);
        propDescTestTextBox.setWidth("100%");
        propDescTestTextBox.setReadOnly(true);
        vp0.add(propDescTestTextBox);
        flexPropositionDetailsTable.getFlexCellFormatter().setColSpan(1, 1, 3);
        flexPropositionDetailsTable.setWidget(1, 1, vp0);

        // YVF
        flexPropositionDetailsTable.getCellFormatter().setWidth(2, 0, "10px");
        final VerticalPanel vp1 = new VerticalPanel();
        final Label yvfLabel = new Label("YVF");
        vp1.add(yvfLabel);
        yvfTestTextBox.setReadOnly(true);
        vp1.add(yvfTestTextBox);
        flexPropositionDetailsTable.setWidget(2, 1, vp1);

        // Operator
        final VerticalPanel vp2 = new VerticalPanel();
        final Label operatorLabel = new Label("Operator");
        vp2.add(operatorLabel);

        operatorsTestTextBox.setReadOnly(true);
        operatorsListBox.setWidth("80px");
        vp2.add(operatorsTestTextBox);
        flexPropositionDetailsTable.setWidget(2, 2, vp2);

        // Expected Value
        final VerticalPanel vp3 = new VerticalPanel();
        final Label expectedValueLabel = new Label("Expected Value");
        vp3.add(expectedValueLabel);
        expectedValueTestTextBox.setWidth("100%");
        expectedValueTestTextBox.setReadOnly(true);
        vp3.add(expectedValueTestTextBox);
        flexPropositionDetailsTable.setWidget(2, 3, vp3);

        // Yield Value Function details
        final VerticalPanel vp4 = new VerticalPanel();
        final Label param1Label = new Label("First YVF Parameter:");
        vp4.add(param1Label);
        ListBox param1ListBox = new ListBox();
        param1ListBox.addItem("   param1");
        param1ListBox.addItem("   param2");
        param1ListBox.addItem("   param3");
        param1ListBox.addItem("   param4");
        param1ListBox.setWidth("80px");
        vp4.add(param1ListBox);
        flexPropositionDetailsTable.setWidget(5, 1, vp4);

        final VerticalPanel vp5 = new VerticalPanel();
        final Label param2Label = new Label("Second YVF Parameter:");
        vp5.add(param2Label);
        ListBox param2ListBox = new ListBox();
        param2ListBox.addItem("   param1");
        param2ListBox.addItem("   param2");
        param2ListBox.addItem("   param3");
        param2ListBox.addItem("   param4");
        param2ListBox.setWidth("80px");
        vp5.add(param2ListBox);
        flexPropositionDetailsTable.setWidget(6, 1, vp5);

        HorizontalPanel hp = new HorizontalPanel();
        hp.setSpacing(8);
        hp.add(testRuleButton);
        flexPropositionDetailsTable.setWidget(7, 1, hp);
        flexPropositionDetailsTable.getFlexCellFormatter().setColSpan(7, 1, 3);

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

        propCompositionTestTextArea.setSize("100%", "100%");
        propCompositionTestTextArea.setReadOnly(true);
        ruleCompositionFlexTable.setWidget(1, 0, propCompositionTestTextArea);
        ruleCompositionFlexTable.getFlexCellFormatter().setColSpan(1, 0, 3);
        ruleCompositionFlexTable.getCellFormatter().setHeight(1, 0, "63px");
        ruleCompositionFlexTable.getCellFormatter().setWidth(1, 0, "60%");

        // Complete Rule
        final Label completeRuleLabel = new Label("Rule Overview");
        ruleCompositionFlexTable.setWidget(3, 0, completeRuleLabel);
        ruleCompositionFlexTable.getCellFormatter().setHeight(3, 0, FORM_ROW_HEIGHT);

        completeRuleTestTextArea.setSize("100%", "100%");
        completeRuleTestTextArea.setReadOnly(true);
        ruleCompositionFlexTable.setWidget(4, 0, completeRuleTestTextArea);
        ruleCompositionFlexTable.getFlexCellFormatter().setColSpan(4, 0, 3);
        ruleCompositionFlexTable.getCellFormatter().setHeight(4, 0, "93px");
        ruleCompositionFlexTable.getCellFormatter().setWidth(4, 0, "60%");

        verticalPanel.add(ruleCompositionFlexTable);
        verticalPanel.setCellHeight(horizontalPanel, "200px");

        return propositionsFlexTable;
    }
    
    
    /******************************************************************************************************************
     * 
     *                                                     PROPOSITIONS 
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
        // propositionsFlexTable.getFlexCellFormatter().setColSpan(1, 1, 2);
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
        hpPropName.add(GuiUtil.addLabelAndField("Name", propNameTextBox, "50%"));
        flexPropositionDetailsTable.add(hpPropName);
        
        // Proposition Description
        final HorizontalPanel hpPropDesc = new HorizontalPanel();        
        hpPropDesc.setWidth("100%");        
        hpPropDesc.add(GuiUtil.addLabelAndField("Description", propDescTextBox, "70%"));
 
        flexPropositionDetailsTable.add(hpPropDesc);
        
        //add proposition left, operator and right hand side  
        yvfListBox.addItem(EMPTY_LIST_BOX_ITEM );
        GuiUtil.populateYVFList(yvfListBox);
        operatorsListBox.addItem(EMPTY_LIST_BOX_ITEM);
        GuiUtil.populateComparisonOperatorList(operatorsListBox);
        final HorizontalPanel hpLeftOpRightSide = new HorizontalPanel();
        
        Label yvfLabel = new Label("YVF");
        yvfLabel.setStyleName("yvf_fields");
        hpLeftOpRightSide.add(GuiUtil.addLabelAndField(yvfLabel, yvfListBox, "200px"));
        GuiUtil.addSpaceAfterWidget(hpLeftOpRightSide, "15px");
        hpLeftOpRightSide.add(GuiUtil.addLabelAndField("Operator", operatorsListBox, "80px"));
        GuiUtil.addSpaceAfterWidget(hpLeftOpRightSide, "15px");
        hpLeftOpRightSide.add(GuiUtil.addLabelAndField("Expected Value", expectedValueTextBox, "150px"));                        
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
    	Label yvfFirstFactLineLabel = new Label("1st YVF Fact:");
    	yvfFirstFactLineLabel.setStyleName("yvf_fields");
    	Label yvfFirstFactTypeLabel = new Label("Fact Type:");
    	yvfFirstFactTypeLabel.setStyleName("yvf_fields");
        hpFirstCriteria.add(GuiUtil.addLabelAndField("",yvfFirstFactLineLabel, "100px"));          
        GuiUtil.addSpaceAfterWidget(hpFirstCriteria, "5px");        
        hpFirstCriteria.add(GuiUtil.addLabelAndField(yvfFirstFactTypeLabel, yvfFirstFactTypeListBox, "150px"));
        //TODO: in future we might need to remove 'Static Fact'option from Fact Types list and instead let user
        // to select known fact types and then select if it is static or dynamically supplied value
        //GuiUtil.addSpaceAfterWidget(hpFirstCriteria, "15px");    
        //hpFirstCriteria.add(GuiUtil.addLabelAndField(yvfFirstFactLookupTypeStatic, yvfFirstFactLookupTypeDynamic, "50px"));
        GuiUtil.addSpaceAfterWidget(hpFirstCriteria, "15px");        
        hpFirstCriteria.add(GuiUtil.addLabelAndField(yvfFirstFactParamOneLabel, yvfFirstFactParamOneTextBox, "150px"));
        GuiUtil.addSpaceAfterWidget(hpFirstCriteria, "15px");
        hpFirstCriteria.add(GuiUtil.addLabelAndField(yvfFirstFactParamTwoLabel, yvfFirstFactParamTwoTextBox, "150px"));
        hpFirstCriteria.add(GuiUtil.addLabelAndField(yvfFirstStaticFactLabel, yvfFirstStaticFactValue, "250px"));
        flexPropositionDetailsTable.add(hpFirstCriteria); 
        
        //second fact criteria fields
        final HorizontalPanel hpSecondCriteria = new HorizontalPanel();
        yvfSecondFactLineLabel.setText("2nd YVF Fact:");
        yvfSecondFactLineLabel.setStyleName("yvf_fields");
        yvfSecondFactTypeLabel.setText("Fact Type:");
        yvfSecondFactTypeLabel.setStyleName("yvf_fields");
        yvfSecondFactParamOneLabel.setText("1st Param");
        yvfSecondFactParamOneLabel.setStyleName("yvf_fields");
        yvfSecondFactParamTwoLabel.setText("2nd Param");
        yvfSecondFactParamTwoLabel.setStyleName("yvf_fields");
    	yvfSecondStaticFactLabel.setText("Value");
    	yvfSecondStaticFactLabel.setStyleName("yvf_fields");
        hpSecondCriteria.add(GuiUtil.addLabelAndField("", yvfSecondFactLineLabel, "100px"));
        GuiUtil.addSpaceAfterWidget(hpSecondCriteria, "5px");
        hpSecondCriteria.add(GuiUtil.addLabelAndField(yvfSecondFactTypeLabel, yvfSecondFactTypeListBox, "150px"));
        //TODO: in future we might need to remove 'Static Fact'option from Fact Types list and instead let user
        // to select known fact types and then select if it is static or dynamically supplied value        
        //GuiUtil.addSpaceAfterWidget(hpSecondCriteria, "15px");    
        //hpSecondCriteria.add(GuiUtil.addLabelAndField(yvfSecondFactLookupTypeStatic, yvfSecondFactLookupTypeDynamic, "50px"));          
        GuiUtil.addSpaceAfterWidget(hpSecondCriteria, "15px");     
        hpSecondCriteria.add(GuiUtil.addLabelAndField(yvfSecondFactParamOneLabel, yvfSecondFactParamOneTextBox, "150px"));
        GuiUtil.addSpaceAfterWidget(hpSecondCriteria, "15px");       
        hpSecondCriteria.add(GuiUtil.addLabelAndField(yvfSecondFactParamTwoLabel, yvfSecondFactParamTwoTextBox, "150px"));
        hpSecondCriteria.add(GuiUtil.addLabelAndField(yvfSecondStaticFactLabel, yvfSecondStaticFactValue, "250px"));        
        flexPropositionDetailsTable.add(hpSecondCriteria);             

        HorizontalPanel hpButtons = new HorizontalPanel();
        hpButtons.setSpacing(8);      
        //hpButtons.add(makeCopyPropButton);
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
        
        // check whether the current composition is valid
        //checkPropositonComposition();

        // update Rule Overview text as well
        //completeRuleTextArea.setText(GuiUtil.assembleRuleFromComposition(propCompositionTextArea.getText(), definedPropositions));
    }    
    
    private void populatePropositionListBoxAndDetailsTest(int selectedPropIx) {
        String propAbreviation;

        propositionsTestListBox.clear();
        for (Map.Entry<Integer, RulePropositionDTO> prop : definedPropositions.entrySet()) {
            propAbreviation = "P" + prop.getKey();
            propositionsTestListBox.addItem(propAbreviation + ":  " + prop.getValue().getName(), prop.getKey().toString());

            if ((prop.getKey().intValue() - 1) == selectedPropIx) {
                populatePropositionDetailsTest(prop.getValue());
            }
        }
        propositionsTestListBox.setSelectedIndex(selectedPropIx);
        if (selectedPropIx == -1) {
            clearPropositionDetails();
        }
    }

    private void populatePropositionDetails(RulePropositionDTO prop) {
    	clearPropositionDetails();
        propNameTextBox.setText(prop.getName());
        propDescTextBox.setText(prop.getDescription());        
        operatorsListBox.setSelectedIndex(GuiUtil.getListBoxIndexByName(operatorsListBox, prop.getComparisonOperatorType()));
        expectedValueTextBox.setText(prop.getRightHandSide().getExpectedValue());        
        populateYVFDetails(prop.getLeftHandSide().getYieldValueFunction(), prop.getName());   
        //updatePropButton.setEnabled(true);
        //removePropButton.setEnabled(true);
    }   
    
    private void populateYVFFactTypeLits() {    	    	
    	GuiUtil.setListBoxSelectionByItemName(yvfFirstFactTypeListBox, firstFactTypeKeyListSelectedValue);
    	
    	//populate second box only if it is applicable:
    	if (secondFactTypeKeyListSelectedValue != null) {
    		GuiUtil.setListBoxSelectionByItemName(yvfSecondFactTypeListBox, secondFactTypeKeyListSelectedValue);
    	}
    }
    
    //ASSUMPTION: all proposition are stored in VALID state in both GUI and in Rule Management service
    private void populateYVFDetails(YieldValueFunctionDTO yvf, String propositionName) {   	
    	
    	String yvfType = (yvf == null ? "" : yvf.getYieldValueFunctionType());
    	
        // do not enable YVF fact parameters if no YVF is selected or we don't know the rule type
        if ((yvf == null) || yvfType.equals(EMPTY_LIST_BOX_ITEM) ||
        	(displayedRule == null) || (displayedRule.getBusinessRuleTypeKey().trim().isEmpty())) {
        	resetYVFFactFields();
        	return;	//no selection of YVF yet
        }
        
        //1. set YVF list box according to set value
        GuiUtil.setListBoxSelectionByItemName(yvfListBox, yvfType);        
        
    	//2. show static or dynamic fact fields based on selection in Fact Type list box if any
    	// a) get the first fact type
    	List<FactStructureDTO> factStructureList = yvf.getFactStructureList();
        if (factStructureList.size() == 0) {
        	//logger.error(e.getMessage(), e);
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
    	
        //get fact type info from fact service - do we need this?
    	/*
        if (firstFact.isStaticFact() == false) {
			DevelopersGuiService.Util.getInstance().fetchFactType(firstFact.getFactTypeKey(),
																	new AsyncCallback<FactTypeInfoDTO>() {
	            public void onFailure(Throwable caught) {
	                // just re-throw it and let the uncaught exception handler deal with it
	                Window.alert(caught.getMessage());
	                //loadingFactTypeKeyList = false;
	                // throw new RuntimeException("Unable to load fact type key list", caught);
	            }
	
	            public void onSuccess(FactTypeInfoDTO factTypeInfo) {
	            	FactCriteriaTypeInfoDTO factCriteriaTypeInfo = factTypeInfo.getFactCriteriaTypeInfo();
	            	System.out.println(factCriteriaTypeInfo.getDescription());
	            	System.out.println(factCriteriaTypeInfo.getKey());
	            	System.out.println(factCriteriaTypeInfo.getName());
	            	Map<String, FactParamDTO> paramMap = factCriteriaTypeInfo.getFactParamMap();
	            	System.out.println("Param Map:" + paramMap.toString());
	            }
	        });            
        } */
               
    	//load a new fact type key list if it is missing (because of new business rule
    	retrieveFactTypes();
    	
    	System.out.println("factTypeKeyList loaded...");
    	populateYVFFactTypeLits();
    }

    private void retrieveFactTypes() {
    	if (factTypeKeyList == null) {    	
	        //load list of factTypeKeys for the given business rule type
	        //TODO in proposition form, make indication that the param list boxes are still being loaded...
        	loadingFactTypeKeyList = true;
    		DevelopersGuiService.Util.getInstance().fetchBusinessRuleType(displayedRule.getBusinessRuleTypeKey(),
    				displayedRule.getAnchorTypeKey(),new AsyncCallback<BusinessRuleTypeDTO>() {
	            public void onFailure(Throwable caught) {
	                // just re-throw it and let the uncaught exception handler deal with it
	                Window.alert(caught.getMessage());
	                //loadingFactTypeKeyList = false;
	                // throw new RuntimeException("Unable to load fact type key list", caught);
	            }
	
	            public void onSuccess(BusinessRuleTypeDTO ruleTypeInfo) {
	            	factTypeKeyList = ruleTypeInfo.getFactTypeKeyList();
	            	if (factTypeKeyList != null) Logger.info("Loaded fact type key list: " + factTypeKeyList.toString());
	            	loadingFactTypeKeyList = false;
	            	
	            	yvfFirstFactTypeListBox.clear();
	            	yvfSecondFactTypeListBox.clear();
	            	yvfFirstFactTypeListBox.addItem(EMPTY_LIST_BOX_ITEM);
	            	yvfFirstFactTypeListBox.addItem(USER_DEFINED_FACT_TYPE_KEY);
	            	yvfSecondFactTypeListBox.addItem(EMPTY_LIST_BOX_ITEM);
	            	yvfSecondFactTypeListBox.addItem(USER_DEFINED_FACT_TYPE_KEY);	            	
	            	for (String factTypeKey : factTypeKeyList) {
	            		factTypeKey = GuiUtil.removeFactTypeKeyPrefix(factTypeKey); 	            		
		                yvfFirstFactTypeListBox.addItem(factTypeKey);
		                yvfSecondFactTypeListBox.addItem(factTypeKey);
	            	}	            	
	            	
	            	populateYVFFactTypeLits(); 
	            }
	        });        	
    	}    	
    }
    
    private void populatePropositionDetailsTest(RulePropositionDTO prop) {
        propNameTestTextBox.setText(prop.getName());
        propDescTestTextBox.setText(prop.getDescription());
        yvfTestTextBox.setText(prop.getLeftHandSide().getYieldValueFunction().getYieldValueFunctionType());
        operatorsTestTextBox.setText(prop.getComparisonOperatorType());
        expectedValueTestTextBox.setText(prop.getRightHandSide().getExpectedValue());
    }
    
    private void clearPropositionDetails() {
        propNameTextBox.setText("");
        propDescTextBox.setText("");
        yvfListBox.setSelectedIndex(-1);
        clearYVFFactFields();            
        operatorsListBox.setSelectedIndex(-1);
        expectedValueTextBox.setText("");
        //propositionsListBox.setSelectedIndex(-1);
        //updatePropButton.setEnabled(false);
        //removePropButton.setEnabled(false);
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
    	
    	//first check that edited proposition is valid
    	//RulePropositionDTO prop = retrievePropositionDTOFromFormFields();
        //if (prop == null) {  
        //	return false;  //invalid proposition
        //}
    	    	
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
    	
    	//first verify YVF fact fields are setup correctly and the proposition has necessary data
    	//if (isYVFFactValid() == false) {
    	//	return null;
    	//}                	

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
	        	prop.setComparisonDataType("java.math.Integer");
	        } else { //if ((yvf.equals(YieldValueFunctionType.SUM.name()) || (yvf.equals(YieldValueFunctionType..name()) {
	        	prop.setComparisonDataType("java.math.BigDecimal");
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
        prop.setComparisonOperatorType(operatorsListBox.getValue(operatorsListBox.getSelectedIndex()));  
    	
        return prop;
    }
    
    //updates stored proposition DTO because we either committing changes or selected a different proposition or a different rule
    private boolean updateSelectedPropositionDTO(int selectedPropListIx) {
    	   	
        if (selectedPropListIx == -1) { 
        	return true; //no proposition selected
        }
        
        int origPropKey = new Integer(propositionsListBox.getValue(selectedPropListIx));    	
        
    	System.out.println("--> UPDATE prop with key: " + origPropKey + ", ix: " + selectedPropListIx);        
        
        RulePropositionDTO selectedRuleElement = definedPropositions.get(origPropKey);
        if (selectedRuleElement == null) {
        	System.out.println("null????");
            return true; //nothing to update
        }
    	
    	RulePropositionDTO newProp = retrievePropositionDTOFromFormFields();
        //if (prop == null) {  
        //	return false;  //invalid proposition
        //}
        
        definedPropositions.remove(origPropKey);
        definedPropositions.put(origPropKey, newProp);                        
        //propositionsListBox.setSelectedIndex(-1);
        //clearPropositionDetails();
        //propositionsListBox.setSelectedIndex(selectedPropListIx);
        //selectedPropIndex = selectedPropListIx;
        
        // update Rule Overview text as well
        completeRuleTextArea.setText(GuiUtil.assembleRuleFromComposition(propCompositionTextArea.getText(), definedPropositions));    	
        
        return true;
    }
   
    private int addEmptyPropositionToDTOList() {
        // create an empty rule proposition
        LeftHandSideDTO leftSide = new LeftHandSideDTO();
        RightHandSideDTO rightSide = new RightHandSideDTO();
        RulePropositionDTO prop = new RulePropositionDTO();
        prop.setName("");
        prop.setDescription("");
        prop.setLeftHandSide(leftSide);
        prop.setComparisonOperatorType("");
        prop.setRightHandSide(rightSide);
        prop.setComparisonDataType("");
        
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
        factStructure.setFactStructureId("");
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
