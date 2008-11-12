/**
 * 
 */
package org.kuali.student.rules.devgui.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.kuali.student.rules.devgui.client.controller.DevelopersGuiController;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.rules.devgui.client.service.DevelopersGuiService;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.MetaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.StatusDTO;
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

    final String FORM_ROW_HEIGHT = "18px";

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
    
    // Main rules tab
    final Label businessRuleID = new Label("");
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
    final ListBox yvfListBox = new ListBox();
    final TextBox propNameTextBox = new TextBox();
    final TextBox propDescTextBox = new TextBox();
    final ListBox operatorsListBox = new ListBox();
    final ListBox propositionsListBox = new ListBox();
    final TextArea completeRuleTextArea = new TextArea();
    final TextArea propCompositionTextArea = new TextArea();
    final Button validateCompositionButton = new Button("Validate");
    final TextBox expectedValueTextBox = new TextBox();
    final Label compositionStatusLabel = new Label();
    // Proposition Update, Reset, Add, Delete buttons
    final Button updatePropButton = new Button("Update");
    final Button deletePropButton = new Button("Delete");
    final Button resetPropButton = new Button("Reset");
    final Button addPropButton = new Button("Add");

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
    final Button updateRuleButton = new Button("Update Rule");
    final Button retireRuleButton = new Button("Retire Rule");    
    final Button copyRuleButton = new Button("Make Rule Copy");
    final Button cancelButton = new Button("Cancel");     
    
    //variables representing client internal state
    private BusinessRuleInfoDTO displayedRule = null; // keep copy of business rule so we can update all fields user
    private RulesHierarchyInfo displayedRuleInfo = null; // keep copy of rule meta info
    private Map<Integer, RulePropositionDTO> definedPropositions = new HashMap<Integer, RulePropositionDTO>();
    private StringBuffer ruleComposition;
    private final String STATUS_NOT_IN_DATABASE = "NOT_IN_DATABASE";
    private final String EMPTY_AGENDA_TYPE = "drafts";
    //private boolean emptyRuleLoaded = true;


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
            //rulesTree.setStyleName("gwt-Tree-rules");
            rulesHorizontalSplitPanel.setRightWidget(addRulesForm());
            rulesHorizontalSplitPanel.setSize("100%", "100%");
            rulesHorizontalSplitPanel.setSplitPosition("30%");

            // scroll panel on the bottom for log/error messages
            rulesScrollPanel.setSize("100%", "0%");    // TODO setSize("100%", "100%");
            

            // add tree/form and scroll panel together
            rulesVerticalSplitPanel.setSize("100%", "800px");
            rulesVerticalSplitPanel.setTopWidget(rulesHorizontalSplitPanel);
            rulesVerticalSplitPanel.setBottomWidget(rulesScrollPanel);
            rulesVerticalSplitPanel.setSplitPosition("80%");
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
                    
                    // populate fields based on a rule selected by user
                    String ruleId = modelObject.getBusinessRuleId();     
                    
                    displayedRuleInfo = modelObject;
                    
                    DevelopersGuiService.Util.getInstance().fetchDetailedBusinessRuleInfo(ruleId, new AsyncCallback<BusinessRuleInfoDTO>() {
                        
                    	public void onFailure(Throwable caught) {
                            // just re-throw it and let the uncaught exception handler deal with it
                            Window.alert(caught.getMessage());
                            // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                        }

                        public void onSuccess(BusinessRuleInfoDTO ruleInfo) {
                        	loadExistingRule(ruleInfo);
                        }
                    });
                    
                    //emptyRuleLoaded = false;
                }
            });

            /****************************************************************************************************************
             * listeners for rule CREATE and UPDATE buttons
             ***************************************************************************************************************/
                                             
            submitDraftButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {

                	if (!displayedRule.getBusinessRuleId().isEmpty()) {
                		System.out.println("DEBUG: expected empty business rule id!");
                		Window.alert("Internal error");  //TODO better message to the user
                		return;
                	}                
                	
                    //make sure rule has draft status 
                    displayedRule.setStatus(BusinessRuleStatus.IN_PROGRESS.toString());
                	
                    // 1) update the displayed rule copy with data entered
                    if (updateDisplayedRuleCopy() == false) {
                        return;
                    }

                    // 2) create new draft rule
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

                            loadExistingRule(displayedRule);
                            // controller.getEventDispatcher().fireEvent(toFire, ruleInfo);
                        }
                    });
                    
                    loadExistingRule(displayedRule);
                }
            });

            updateDraftButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {

                    // second, update the active rule with data entered
                    if (updateDisplayedRuleCopy() == false) {
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
                }
            });
      
            activateDraftButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {

                    // first validate that the rule entered/changed data is correct  //TODO needed for draft?
                    if (isDisplayedRuleValid() == false) {
                        // TODO: show dialog 'Rule is not valid or complete: <error message>"
                        System.out.println("DEBUG: invalid rule DRAFT - it cannot be updated");
                    }

                    // second, update the active rule with data entered
                    if (updateDisplayedRuleCopy() == false) {
                        return;
                    }
                    
                    //activated Draft will become a rule
                    displayedRule.setStatus(BusinessRuleStatus.ACTIVE.toString());

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
                }
            });            
            
            updateRuleButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {

                    // first validate that the rule entered/changed data is correct  
                    if (isDisplayedRuleValid() == false) {
                        // TODO: show dialog 'Rule is not valid or complete: <error message>"
                        System.out.println("DEBUG: invalid rule DRAFT - it cannot be updated");
                    }

                    // second, update the active rule with data entered
                    if (updateDisplayedRuleCopy() == false) {
                        return;
                    }

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

                    // first validate that the rule entered/changed data is correct  //TODO needed for draft?
                    if (isDisplayedRuleValid() == false) {
                        // TODO: show dialog 'Rule is not valid or complete: <error message>"
                        System.out.println("DEBUG: invalid rule - it cannot be updated");
                    }

                    // second, update the active rule with data entered
                    if (updateDisplayedRuleCopy() == false) {
                        return;
                    }
                    
                    //retire Rule
                    displayedRule.setStatus(BusinessRuleStatus.RETIRED.toString());

                    DevelopersGuiService.Util.getInstance().updateBusinessRule(displayedRule.getBusinessRuleId(), displayedRule, new AsyncCallback<StatusDTO>() {
                        public void onFailure(Throwable caught) {
                            // just re-throw it and let the uncaught exception handler deal with it
                            Window.alert(caught.getMessage());
                            // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                        }

                        public void onSuccess(StatusDTO updateStatus) {
                            System.out.println("Retired rule: " + updateStatus.isSuccess());
                        }
                    });
                    
                    loadExistingRule(displayedRule);
                }
            });    
            
            copyRuleButton.addClickListener(new ClickListener() {  
                public void onClick(final Widget sender) {
                	//new draft will be positioned in the tree
                	placeNewDraftInTree("");                	
                	
                	//keep original rule values except rule id
                	BusinessRuleInfoDTO ruleCopy = displayedRule;
                	ruleCopy.setBusinessRuleId("");
                	ruleCopy.setStatus(STATUS_NOT_IN_DATABASE);
                	
                	//now load the draft
                	loadEmptyRule();
                	displayedRule = ruleCopy;                	          	            	                	
                    displayActiveRule();
                }
            });  
            
            // reset rule in order to create a new rule or void changes to the existing rule
            cancelButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    // TODO "Are you sure?' dialog -> see ui common package for widget
                	loadEmptyRule();
                }
            });

            /****************************************************************************************************************
             * listeners for proposition ADD, UPDATE, DELETE, RESET buttons
             ***************************************************************************************************************/

            updatePropButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    int selectedProp = propositionsListBox.getSelectedIndex();
                    if (selectedProp != -1) {
                        RulePropositionDTO prop = definedPropositions.get(Integer.parseInt(propositionsListBox.getValue(selectedProp)));
                        prop.setName(propNameTextBox.getText());
                        prop.setDescription(propDescTextBox.getText());
                        LeftHandSideDTO leftSide = new LeftHandSideDTO();
                        YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
                        yvf.setYieldValueFunctionType(yvfListBox.getValue(yvfListBox.getSelectedIndex()));
                        leftSide.setYieldValueFunction(yvf);
                        prop.setLeftHandSide(leftSide);
                        RightHandSideDTO rightSide = new RightHandSideDTO();
                        rightSide.setExpectedValue(expectedValueTextBox.getText());
                        prop.setComparisonOperatorType(operatorsListBox.getValue(operatorsListBox.getSelectedIndex()));
                        prop.setRightHandSide(rightSide);
                        propositionsListBox.setSelectedIndex(-1);
                        clearPropositionDetails();
                    }
                }
            });

            deletePropButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    int selectedProp = propositionsListBox.getSelectedIndex();
                    if (selectedProp != -1) {
                        // remove the proposition
                        definedPropositions.remove(Integer.parseInt(propositionsListBox.getValue(selectedProp)));

                        // refresh the form
                        populatePropositionListBoxAndDetails(-1);
                    }
                }
            });

            resetPropButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    clearPropositionDetails();
                }
            });

            addPropButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    RulePropositionDTO prop = new RulePropositionDTO();

                    if (yvfListBox.getSelectedIndex() == -1) {
                        // TODO error messages
                        return;
                    }

                    if (operatorsListBox.getSelectedIndex() == -1) {
                        return;
                    }

                    if (expectedValueTextBox.getText().isEmpty()) {
                        return;
                    }

                    prop.setName(propNameTextBox.getText());
                    prop.setDescription(propDescTextBox.getText());
                    LeftHandSideDTO leftSide = new LeftHandSideDTO();
                    YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
                    yvf.setYieldValueFunctionType(yvfListBox.getValue(yvfListBox.getSelectedIndex()));
                    leftSide.setYieldValueFunction(yvf);
                    prop.setLeftHandSide(leftSide);
                    RightHandSideDTO rightSide = new RightHandSideDTO();
                    rightSide.setExpectedValue(expectedValueTextBox.getText());
                    prop.setRightHandSide(rightSide);
                    prop.setComparisonOperatorType(operatorsListBox.getValue(operatorsListBox.getSelectedIndex()));

                    int max = 0;
                    for (Integer key : definedPropositions.keySet()) {
                        if (key.intValue() > max) {
                            max = key.intValue();
                        }
                    }
                    max++;

                    definedPropositions.put(max, prop);

                    populatePropositionListBoxAndDetails(propositionsListBox.getItemCount() - 1);
                }
            });

            /****************************************************************************************************************
             * listeners for COMPOSITION and COMPLETE TEXT elements of a rule
             ***************************************************************************************************************/

            validateCompositionButton.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    compositionStatusLabel.setText(GuiUtil.validateRuleComposition(propCompositionTextArea.getText(), definedPropositions.keySet()));
                }
            });

            propCompositionTextArea.addFocusListener(new FocusListener() {
                public void onFocus(final Widget sender) {

                }

                public void onLostFocus(final Widget sender) {
                    // check whether the current composition is valid
                    compositionStatusLabel.setText(GuiUtil.validateRuleComposition(propCompositionTextArea.getText(), definedPropositions.keySet()));

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

            agendaTypesListBox.addChangeListener(new ChangeListener() {
                public void onChange(final Widget sender) {
                	//changing agenda type will reset the business rule type and related Anchor Key
                	displayedRule.setBusinessRuleTypeKey("");  
                	displayedRule.setAnchorTypeKey("");
                	ruleAnchorType.setText("");
                	populateAgendaAndBusinessRuleTypesListBox();
                }
            });  
            
            businessRuleTypesListBox.addChangeListener(new ChangeListener() {
                public void onChange(final Widget sender) {
                	ruleAnchorType.setText(GuiUtil.getListBoxSelectedValue(businessRuleTypesListBox));
                }
            });             
        }
    }

    private void loadEmptyRule() {    	
        displayedRule = createEmptyBusinessRule();
    	clearRuleForms();
    	setRuleStatus(STATUS_NOT_IN_DATABASE);
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
            if (elem.getOperation().equals("PROPOSITION")) {
                definedPropositions.put(propCount, elem.getRuleProposition());
                ruleComposition.append("P" + (propCount++) + " ");
            } else {
                ruleComposition.append(elem.getOperation() + " ");
            }
        }

        // populate the proposition details according to first prop selected by default
        displayActiveRule();    	
    }
    
    private void setRuleStatus(String status) {
    	displayedRule.setStatus(status);
    	ruleStatus.setText(status);
    	 	
    	if (ruleStatus.getText().equals(STATUS_NOT_IN_DATABASE)) {
    		ruleStatus.setStylePrimaryName("status-empty");
    	} else if (ruleStatus.getText().equalsIgnoreCase(BusinessRuleStatus.IN_PROGRESS.toString())) {
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
    	
    	if (ruleStatus.equalsIgnoreCase(STATUS_NOT_IN_DATABASE)) {
        	submitDraftButton.setEnabled(true);
        	updateDraftButton.setEnabled(false);        	
        	activateDraftButton.setEnabled(false);        	
        	updateRuleButton.setEnabled(false); 
        	retireRuleButton.setEnabled(false);        	
        	copyRuleButton.setEnabled(false);
        	cancelButton.setEnabled(true);  
        	return;
    	} else if (ruleStatus.equalsIgnoreCase(BusinessRuleStatus.IN_PROGRESS.toString())) {
        	submitDraftButton.setEnabled(false);  
        	updateDraftButton.setEnabled(true); 
        	activateDraftButton.setEnabled(true); 
        	updateRuleButton.setEnabled(false);     	
        	retireRuleButton.setEnabled(false);        	
        	copyRuleButton.setEnabled(false);
        	cancelButton.setEnabled(true); 
        } else if (ruleStatus.equalsIgnoreCase(BusinessRuleStatus.ACTIVE.toString())) {
        	submitDraftButton.setEnabled(false);
        	updateDraftButton.setEnabled(false);        	
        	activateDraftButton.setEnabled(false);   
        	updateRuleButton.setEnabled(true);
        	retireRuleButton.setEnabled(true);        	
        	copyRuleButton.setEnabled(true);
        	cancelButton.setEnabled(true);             
        } else if (ruleStatus.equalsIgnoreCase(BusinessRuleStatus.RETIRED.toString())) {
        	submitDraftButton.setEnabled(false);
        	updateDraftButton.setEnabled(false);        	
        	activateDraftButton.setEnabled(false);   
        	updateRuleButton.setEnabled(false);
        	retireRuleButton.setEnabled(false);        	
        	copyRuleButton.setEnabled(true);
        	cancelButton.setEnabled(true); 
       } else {
        	//TODO error message
        }
    }
   
    private boolean updateDisplayedRuleCopy() {

       // BusinessRuleInfoDTO rule = displayedRule;

        // set rule basic info
    	displayedRule.setName(nameTextBox.getText());
    	displayedRule.setDescription(descriptionTextArea.getText());
    	displayedRule.setSuccessMessage(successMessageTextArea.getText());
    	displayedRule.setFailureMessage(failureMessageTextArea.getText());
    	displayedRule.setBusinessRuleTypeKey(BusinessRuleTypeKey.KUALI_CO_REQ.name()); // TODO - this should be a drop down
    	displayedRule.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.name()); // TODO
    	displayedRule.setAnchorValue("course"); // TODO
    	displayedRule.setEffectiveStartTime(new Date()); // TODO - add to form
    	displayedRule.setEffectiveEndTime(new Date()); // TODO - add to form

        // set rule propositions
    	if (ruleComposition != null) {
	        List<RuleElementDTO> elemList;
	        try {
	            elemList = GuiUtil.createRuleElementsFromComposition(ruleComposition.toString(), definedPropositions);
	        } catch (IllegalRuleFormatException e) {
	            // This should not happen as rule suppose to be checked before calling this function
	            // TODO: log into screen log text box
	            return false;
	        }
	        displayedRule.setRuleElementList(elemList);
    	}
        
        // set meta info
        final DateTimeFormat formatter = DateTimeFormat.getFormat("HH:mm MMM d, yyyy");
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        /* test later i.e. when certain fields are empty
        metaInfo.setCreateID(createUserIdTextBox.getText());
        metaInfo.setCreateTime(formatter.parse(createTimeTextBox.getText()));
        metaInfo.setCreateComment(createCommentTextBox.getText());
        metaInfo.setUpdateID(updateUserIdTextBox.getText());
        metaInfo.setUpdateTime(formatter.parse(updateTimeTextBox.getText()));
        metaInfo.setUpdateComment(updateCommentTextBox.getText());
        */
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
        newRule.setStatus(BusinessRuleStatus.IN_PROGRESS.name());
        newRule.setName("");
        newRule.setDescription("");
        newRule.setSuccessMessage("");
        newRule.setFailureMessage("");
        newRule.setBusinessRuleTypeKey("");
        newRule.setAnchorTypeKey("");
        newRule.setAnchorValue("");
        newRule.setEffectiveStartTime(new Date());
        newRule.setEffectiveEndTime(new Date());

        // set meta info
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateID("");
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateComment("");
        metaInfo.setUpdateID("");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateComment("");
        newRule.setMetaInfo(metaInfo);

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

        return newRule;
    }    

    private void placeNewDraftInTree(String newRuleID) {
        String agendaType = GuiUtil.getListBoxSelectedValue(agendaTypesListBox);
        String businessRuleType = GuiUtil.getListBoxSelectedValue(businessRuleTypesListBox);
        String anchor = ruleAnchorTextBox.getText();
        String ruleName = displayedRule.getName();
        
        /* TODO within the existing rules hierarchy?                                              
        displayedRuleInfo = new RulesHierarchyInfo();
    	displayedRuleInfo.setAgendaType(agendaType.isEmpty() ? "empty agenda" : agendaType);
    	displayedRuleInfo.setBusinessRuleType(businessRuleType.isEmpty() ? "empty business rule type" : businessRuleType);
    	displayedRuleInfo.setAnchor(ruleAnchorTextBox.getText().isEmpty() ? "empty anchor" : ruleAnchorTextBox.getText());                            
        */
        
        //TODO for now display draft rules in separate branch
        displayedRuleInfo = new RulesHierarchyInfo();
    	displayedRuleInfo.setAgendaType(EMPTY_AGENDA_TYPE);
    	displayedRuleInfo.setBusinessRuleType(businessRuleType.isEmpty() ? "empty business rule type" : businessRuleType);
    	displayedRuleInfo.setAnchor(anchor.isEmpty() ? "empty anchor" : anchor); 
    	displayedRuleInfo.setBusinessRuleName(ruleName.isEmpty() ? newRuleID : ruleName);
    	displayedRuleInfo.setBusinessRuleId(newRuleID);      	
    }
    
    /******************************************************************************************************************
     * 
     *                                                     VALIDATIONS 
     *
     *******************************************************************************************************************/        
    
    private boolean isDisplayedRuleValid() {

        ruleComposition = new StringBuffer(propCompositionTextArea.getText());

        // each rule should have at least one proposition
        if ((definedPropositions == null) || (definedPropositions.size() == 0)) {
            // TODO error messages
            System.out.println("Rule has no propositions defined");
            return false;
        }

        // at least one proposition needs to be used
        if (ruleComposition.toString().isEmpty()) {
            System.out.println("Rule has no propositions.");
            return false;
        }

        // TODO make form read-only while validating/creating/updating the rule???
        return (GuiUtil.validateRuleComposition(propCompositionTextArea.getText(), definedPropositions.keySet()).equals(GuiUtil.COMPOSITION_IS_VALID_MESSAGE));

        // TODO validate other fields i.e. fields that are required
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
        populateAgendaAndBusinessRuleTypesListBox();
        ruleAnchorType.setText("");
        ruleAnchorTextBox.setText("");

        // Clear Propositions TAB
        clearPropositionDetails();
        propositionsListBox.clear();
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
    	        	
    	String ruleAgendaType;
    	String businessRuleType = (displayedRule == null ? "" : displayedRule.getBusinessRuleTypeKey());    	
    	agendaTypesListBox.setEnabled(false);
    	businessRuleTypesListBox.setEnabled(false);
    	
    	// if a rule is already active or retired, user cannot change agenda type or business rule type
    	if (getRuleStatus().equals(BusinessRuleStatus.ACTIVE.toString()) || getRuleStatus().equals(BusinessRuleStatus.RETIRED.toString())) {
    		ruleAgendaType = (displayedRuleInfo == null ? "" : displayedRuleInfo.getAgendaType());
    		agendaTypesListBox.clear();
    		agendaTypesListBox.addItem(ruleAgendaType);
    		GuiUtil.setListBoxByItemName(agendaTypesListBox, ruleAgendaType);
    		businessRuleTypesListBox.clear();
    		businessRuleTypesListBox.addItem(businessRuleType);
    		GuiUtil.setListBoxByItemName(businessRuleTypesListBox, businessRuleType);
    		return;
    	}
    	
    	ruleAgendaType = GuiUtil.getListBoxSelectedValue(agendaTypesListBox);    	
   	
    	// rule is either empty or draft and user can change both agenda and business rule type
    	agendaTypesListBox.setEnabled(true);
    	 
    	//retrieve all agenda types if necessary
    	if (agendaTypesListBox.getItemCount() == 0) {
	        DevelopersGuiService.Util.getInstance().findAgendaTypes(new AsyncCallback<List<String>>() {
	            public void onFailure(Throwable caught) {
	                // just re-throw it and let the uncaught exception handler deal with it
	                Window.alert(caught.getMessage());
	                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
	            }
	
	            public void onSuccess(List<String> agendaTypes) {
	            	agendaTypesListBox.clear();
	            	agendaTypesListBox.addItem("                      ");
	                for (String agendaTypeKey : agendaTypes) {
	                	agendaTypesListBox.addItem(agendaTypeKey);
	                }	
	            }
	        });                       
    	}
        
        //if user did not select agenda type then we don't retrieve business rule types and leave list box disabled
        if (ruleAgendaType.trim().isEmpty() || ruleAgendaType.equals(EMPTY_AGENDA_TYPE)) {
        	businessRuleTypesListBox.clear();
        	return;
        }
            	
System.out.println("DEBUG: agenda type:" + ruleAgendaType);        
        
  		businessRuleTypesListBox.setEnabled(true);
   		GuiUtil.setListBoxByItemName(agendaTypesListBox, ruleAgendaType);
    	
    	//find related business rule types
        DevelopersGuiService.Util.getInstance().findBusinessRuleTypesByAgendaType(ruleAgendaType, new AsyncCallback<List<String>>() {
            public void onFailure(Throwable caught) {
                // just re-throw it and let the uncaught exception handler deal with it
                Window.alert(caught.getMessage());
                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
            }

            public void onSuccess(List<String> businessRuleTypes) {
            	businessRuleTypesListBox.clear();
            	businessRuleTypesListBox.addItem("                       ");
                for (String businessRuleTypeKey : businessRuleTypes) {
                	businessRuleTypesListBox.addItem(businessRuleTypeKey);
                }	
            }
        });   
        
        if (!businessRuleType.isEmpty()) {
        	GuiUtil.setListBoxByItemName(businessRuleTypesListBox, businessRuleType);
        }
    }         
    
    public void displayActiveRule() {

        final DateTimeFormat formatter = DateTimeFormat.getFormat("HH:mm MMM d, yyyy");

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
        populatePropositionListBoxAndDetails(0);

        propCompositionTextArea.setText(ruleComposition.toString());
        completeRuleTextArea.setText(GuiUtil.assembleRuleFromComposition(propCompositionTextArea.getText(), definedPropositions));

        // populate Authoring TAB
        /* TODO
        effectiveStartTimeTextBox.setText(formatter.format(displayedRule.getEffectiveStartTime()));
        effectiveEndTimeTextBox.setText(formatter.format(displayedRule.getEffectiveEndTime()));
        createTimeTextBox.setText(formatter.format(displayedRule.getMetaInfo().getCreateTime()));
        createUserIdTextBox.setText(displayedRule.getMetaInfo().getCreateID());
        createCommentTextBox.setText(displayedRule.getMetaInfo().getCreateComment());
        updateTimeTextBox.setText(formatter.format(displayedRule.getMetaInfo().getUpdateTime()));
        updateUserIdTextBox.setText(displayedRule.getMetaInfo().getUpdateID());
        updateCommentTextBox.setText(displayedRule.getMetaInfo().getUpdateComment());
        */

        // populate Test TAB
        populatePropositionListBoxAndDetailsTest(0);
        propCompositionTestTextArea.setText(ruleComposition.toString());
        completeRuleTestTextArea.setText(GuiUtil.assembleRuleFromComposition(propCompositionTestTextArea.getText(), definedPropositions));
    }    
    
    private Widget addRulesForm() {
        TabPanel rulesFormTabs = new TabPanel();
        rulesFormTabs.add(addRulesMainPage(), "Main");
        rulesFormTabs.add(addRulesPropositionPage(), "Propositions");
        rulesFormTabs.add(addRRulesMetaDataPage(), "Meta Data");
        rulesFormTabs.add(addRRulesTestPage(), "Test");
        rulesFormTabs.setSize("90%", "450px");
        rulesFormTabs.selectTab(0);

        HorizontalPanel hp = new HorizontalPanel();
        hp.setSpacing(8);                
        hp.add(submitDraftButton);
        hp.add(updateDraftButton);
        hp.add(activateDraftButton);
        hp.add(updateRuleButton);
        hp.add(retireRuleButton);
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
        flexFormTable.getCellFormatter().setWidth(ix, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(ix, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(ix, 1, businessRuleID);
        businessRuleID.setWidth("25%");        

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
        ruleCompositionFlexTable.setWidget(0, 1, compositionStatusLabel);

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
        propositionsListBox.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                ListBox box = ((ListBox) sender);
                RulePropositionDTO selectedRuleElement = definedPropositions.get(new Integer(box.getValue(box.getSelectedIndex())));
                if (selectedRuleElement == null) {
                    return;
                }
                populatePropositionDetails(selectedRuleElement);
            }
        });
        propositionsListBox.setWidth("150px");

        // **********************************************************
        // Propositions List
        // **********************************************************
        final VerticalPanel propListPanel = new VerticalPanel();
        final Label propositionsLabel = new Label("Propositions");
        propListPanel.add(propositionsLabel);
        propListPanel.add(propositionsListBox);

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
        propNameTextBox.setWidth("50%");
        vp.add(propNameTextBox);
        flexPropositionDetailsTable.getFlexCellFormatter().setColSpan(0, 1, 3);
        flexPropositionDetailsTable.setWidget(0, 1, vp);

        // Proposition Description
        final VerticalPanel vp0 = new VerticalPanel();
        vp0.setWidth("100%");
        final Label propDescLabel = new Label("Description");
        vp0.add(propDescLabel);
        propDescTextBox.setWidth("100%");
        vp0.add(propDescTextBox);
        flexPropositionDetailsTable.getFlexCellFormatter().setColSpan(1, 1, 3);
        flexPropositionDetailsTable.setWidget(1, 1, vp0);

        // YVF
        flexPropositionDetailsTable.getCellFormatter().setWidth(2, 0, "10px");
        final VerticalPanel vp1 = new VerticalPanel();
        final Label yvfLabel = new Label("YVF");
        vp1.add(yvfLabel);
        GuiUtil.populateYVFList(yvfListBox);
        vp1.add(yvfListBox);
        flexPropositionDetailsTable.setWidget(2, 1, vp1);

        // Operator
        final VerticalPanel vp2 = new VerticalPanel();
        final Label operatorLabel = new Label("Operator");
        vp2.add(operatorLabel);

        GuiUtil.populateComparisonOperatorList(operatorsListBox);
        operatorsListBox.setWidth("80px");
        vp2.add(operatorsListBox);
        flexPropositionDetailsTable.setWidget(2, 2, vp2);

        // Expected Value
        final VerticalPanel vp3 = new VerticalPanel();
        final Label expectedValueLabel = new Label("Expected Value");
        vp3.add(expectedValueLabel);
        expectedValueTextBox.setWidth("100%");
        vp3.add(expectedValueTextBox);
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
        hp.add(updatePropButton);
        hp.add(resetPropButton);
        hp.add(addPropButton);
        hp.add(deletePropButton);
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
        ruleCompositionFlexTable.setWidget(0, 1, compositionStatusLabel);

        propCompositionTextArea.setSize("100%", "100%");
        ruleCompositionFlexTable.setWidget(1, 0, propCompositionTextArea);
        ruleCompositionFlexTable.getFlexCellFormatter().setColSpan(1, 0, 3);
        ruleCompositionFlexTable.getCellFormatter().setHeight(1, 0, "63px");
        ruleCompositionFlexTable.getCellFormatter().setWidth(1, 0, "60%");
        ruleCompositionFlexTable.setWidget(2, 2, validateCompositionButton);

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
        propNameTextBox.setText(prop.getName());
        propDescTextBox.setText(prop.getDescription());
        yvfListBox.setSelectedIndex(GuiUtil.getListBoxIndexByName(yvfListBox, prop.getLeftHandSide().getYieldValueFunction().getYieldValueFunctionType()));
        operatorsListBox.setSelectedIndex(GuiUtil.getListBoxIndexByName(operatorsListBox, prop.getComparisonOperatorType()));
        expectedValueTextBox.setText(prop.getRightHandSide().getExpectedValue());
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
        operatorsListBox.setSelectedIndex(-1);
        expectedValueTextBox.setText("");
        propositionsListBox.setSelectedIndex(-1);
    }
    
    private void populatePropositionListBoxAndDetails(int selectedPropIx) {
        String propAbreviation;

        propositionsListBox.clear();
        for (Map.Entry<Integer, RulePropositionDTO> prop : definedPropositions.entrySet()) {
            propAbreviation = "P" + prop.getKey();
            propositionsListBox.addItem(propAbreviation + ":  " + prop.getValue().getName(), prop.getKey().toString());

            if ((prop.getKey().intValue() - 1) == selectedPropIx) {
                populatePropositionDetails(prop.getValue());
            }
        }
        propositionsListBox.setSelectedIndex(selectedPropIx);
        if (selectedPropIx == -1) {
            clearPropositionDetails();
        }
    }
    
    @Override
    protected void onUnload() {
        super.onUnload();
        // unlink the binding as it is no longer needed
        binding.unlink();
    }    
    
}
