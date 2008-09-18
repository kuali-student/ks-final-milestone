/**
 * 
 */
package org.kuali.student.rules.devgui.client.view;

import java.util.HashMap;
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
import org.kuali.student.rules.devgui.client.controller.DevelopersGuiController;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.rules.devgui.client.service.DevelopersGuiService;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
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
import com.google.gwt.i18n.client.DateTimeFormat;

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
    final TextBox nameTextBox = new TextBox();
    final TextArea descriptionTextArea = new TextArea();
    final TextArea successMessageTextArea = new TextArea();
    final TextArea failureMessageTextArea = new TextArea();
    final Label businessRuleTypeReadOnly = new Label("");
    // final ListBox businessRuleTypeListBox = new ListBox();
    final TextBox anchorTextBox = new TextBox();
    final Label anchorTypeReadOnly = new Label("");

    // Propositions rules tab
    final ListBox yvfListBox = new ListBox();
    final TextBox propNameTextBox = new TextBox();
    final TextBox propDescTextBox = new TextBox();
    final ListBox operatorsListBox = new ListBox();
    final ListBox propositionsListBox = new ListBox();
    final TextArea completeRuleTextArea = new TextArea();
    final TextArea propCompositionTextArea = new TextArea();
    final TextBox expectedValueTextBox = new TextBox();
    final Label compositionStatuLabel = new Label();

    // Authoring tab
    final TextBox statusTextBox = new TextBox();
    final TextBox effectiveStartTimeTextBox = new TextBox();
    final TextBox effectiveEndTimeTextBox = new TextBox();
    final TextBox createTimeTextBox = new TextBox();
    final TextBox createUserIdTextBox = new TextBox();
    final TextBox updateTimeTextBox = new TextBox();
    final TextBox updateUserIdTextBox = new TextBox();

    boolean loaded = false;

    public RulesComposite() {
        super.initWidget(simplePanel);
    }

    private BusinessRuleInfoDTO activeRule; // keep copy of business rule so we can update all fields user can change
    private Map<Integer, RuleElementDTO> definedPropositions;
    private StringBuffer ruleComposition;

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

            // create tree-like rules browser
            rulesTree.setSize("100%", "100%");
            rulesBrowserScrollPanel.add(rulesTree);

            // create panel with a tree on left and a form on the right
            rulesHorizontalSplitPanel.setLeftWidget(rulesTree);
            rulesTree.setStyleName("gwt-Tree-rules");
            rulesHorizontalSplitPanel.setRightWidget(addRulesForm());
            rulesHorizontalSplitPanel.setSize("100%", "100%");
            rulesHorizontalSplitPanel.setSplitPosition("30%");

            // scroll panel on the bottom for log/error messages
            rulesScrollPanel.setSize("100%", "100%");

            // add tree/form and scroll panel together
            rulesVerticalSplitPanel.setSize("100%", "700px");
            rulesVerticalSplitPanel.setTopWidget(rulesHorizontalSplitPanel);
            rulesVerticalSplitPanel.setBottomWidget(rulesScrollPanel);
            rulesVerticalSplitPanel.setSplitPosition("80%");
            // simplePanel.setSize("100%", "100%");
            simplePanel.add(rulesVerticalSplitPanel);

            // add selection event listener to rulesTree widget
            /* commented out because fix for org.kuali.student.commons.ui.widgets.trees.SimpleTree.java was not yet
             * checked in to ks-commons-ui-dev module
             */
            rulesTree.addSelectionListener(new ModelTableSelectionListener<RulesHierarchyInfo>() {
                public void onSelect(RulesHierarchyInfo modelObject) {
                    String ruleId = modelObject.getBusinessRuleId();
                    if (modelObject == null) {
                        // selection was cleared
                        clearRuleForms();
                    } else {
                        // populate fields from new selection
                        DevelopersGuiService.Util.getInstance().fetchDetailedBusinessRuleInfo(ruleId, new AsyncCallback<BusinessRuleInfoDTO>() {
                            public void onFailure(Throwable caught) {
                                // just re-throw it and let the uncaught exception handler deal with it
                                Window.alert(caught.getMessage());
                                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                            }

                            public void onSuccess(BusinessRuleInfoDTO ruleInfo) {
                                // store selected business rule in temporary object
                                activeRule = ruleInfo;

                                // store individual propositions in temporary list & set Rule Composition text
                                int propCount = 1;
                                ruleComposition = new StringBuffer();
                                definedPropositions = new HashMap<Integer, RuleElementDTO>();

                                for (RuleElementDTO elem : ruleInfo.getRuleElementList()) {
                                    if (elem.getOperation().equals("PROPOSITION")) {
                                        definedPropositions.put(propCount, elem);
                                        ruleComposition.append("P" + (propCount++) + " ");
                                    } else {
                                        ruleComposition.append(elem.getOperation() + " ");
                                    }
                                }

                                // populate the proposition details according to first prop selected by default
                                populateRuleFormTabs();
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    protected void onUnload() {
        super.onUnload();
        // unlink the binding as it is no longer needed
        binding.unlink();
    }

    public void populateRuleFormTabs() {

        final DateTimeFormat formatter = DateTimeFormat.getFormat("HH:mm MMM d, yyyy");

        // populate Main TAB
        nameTextBox.setText(activeRule.getName());
        descriptionTextArea.setText(activeRule.getDescription());
        successMessageTextArea.setText(activeRule.getSuccessMessage());
        failureMessageTextArea.setText(activeRule.getFailureMessage());
        businessRuleTypeReadOnly.setText(activeRule.getBusinessRuleTypeKey());
        // businessRuleTypeListBox.setValue(0, rule.getBusinessRuleTypeKey());
        // businessRuleTypeListBox.setItemSelected(0, true);
        anchorTypeReadOnly.setText(activeRule.getAnchorTypeKey());
        anchorTextBox.setText(activeRule.getAnchorValue());

        // populate Propositions TAB
        populatePropositionListBoxAndDetails(0);

        propCompositionTextArea.setText(ruleComposition.toString());
        completeRuleTextArea.setText(GuiUtil.assembleRuleFromComposition(propCompositionTextArea.getText(), definedPropositions));

        // populate Authoring TAB
        statusTextBox.setText(activeRule.getStatus());
        effectiveStartTimeTextBox.setText(formatter.format(activeRule.getEffectiveStartTime()));
        effectiveEndTimeTextBox.setText(formatter.format(activeRule.getEffectiveEndTime()));
        createTimeTextBox.setText(formatter.format(activeRule.getMetaInfo().getCreateTime()));
        createUserIdTextBox.setText(activeRule.getMetaInfo().getCreateID());
        updateTimeTextBox.setText(formatter.format(activeRule.getMetaInfo().getUpdateTime()));
        updateUserIdTextBox.setText(activeRule.getMetaInfo().getUpdateID());
    }

    private void populatePropositionListBoxAndDetails(int selectedPropIx) {
        String propAbreviation;

        propositionsListBox.clear();
        for (Map.Entry<Integer, RuleElementDTO> entry : definedPropositions.entrySet()) {
            propAbreviation = "P" + entry.getKey();
            RulePropositionDTO prop = entry.getValue().getRuleProposition();
            propositionsListBox.addItem(propAbreviation + ":  " + prop.getName(), entry.getKey().toString());

            if ((entry.getKey().intValue() - 1) == selectedPropIx) {
                populatePropositionDetails(entry.getValue());
            }
        }
        propositionsListBox.setSelectedIndex(selectedPropIx);
        if (selectedPropIx == -1) {
            clearPropositionDetails();
        }
    }

    private void populatePropositionDetails(RuleElementDTO elem) {
        RulePropositionDTO prop = elem.getRuleProposition();
        propNameTextBox.setText(prop.getName());
        propDescTextBox.setText(prop.getDescription());
        yvfListBox.setSelectedIndex(GuiUtil.getListBoxIndexByName(yvfListBox, prop.getLeftHandSide().getYieldValueFunction().getYieldValueFunctionType()));
        operatorsListBox.setSelectedIndex(GuiUtil.getListBoxIndexByName(operatorsListBox, prop.getComparisonOperatorType()));
        expectedValueTextBox.setText(prop.getRightHandSide().getExpectedValue());
    }

    private void clearPropositionDetails() {
        propNameTextBox.setText("");
        propDescTextBox.setText("");
        yvfListBox.setSelectedIndex(-1);
        operatorsListBox.setSelectedIndex(-1);
        expectedValueTextBox.setText("");
        propositionsListBox.setSelectedIndex(-1);
    }

    // TODO: check if changes were made to any form field; if changes made, ask user if they
    // want to abandon the changes
    public void clearRuleForms() {
        // clear Main TAB
        nameTextBox.setText("");
        descriptionTextArea.setText("");
        successMessageTextArea.setText("");
        failureMessageTextArea.setText("");
        businessRuleTypeReadOnly.setText("");
        anchorTypeReadOnly.setText("");
        anchorTextBox.setText("");

        // Clear Propositions TAB
        clearPropositionDetails();
        propositionsListBox.clear();
        propCompositionTextArea.setText("");
        completeRuleTextArea.setText("");
        expectedValueTextBox.setText("");
        compositionStatuLabel.setText("");

        // Clear Authoring TAB
        statusTextBox.setText("");
        effectiveStartTimeTextBox.setText("");
        effectiveEndTimeTextBox.setText("");
        createTimeTextBox.setText("");
        createUserIdTextBox.setText("");
        updateTimeTextBox.setText("");
        updateUserIdTextBox.setText("");
    }

    private Widget addRulesForm() {
        TabPanel rulesFormTabs = new TabPanel();
        rulesFormTabs.add(addRulesMainPage(), "Main");
        rulesFormTabs.add(addRulesPropositionPage(), "Propositions");
        rulesFormTabs.add(addRRulesAuthoringPage(), "Authoring");
        rulesFormTabs.setSize("90%", "450px");

        // tabs.setStyleName("rulesBorder");
        rulesFormTabs.selectTab(1);

        final Button updateButton = new Button("Update Rule");
        final Button createButton = new Button("Create Rule");
        HorizontalPanel hp = new HorizontalPanel();
        hp.setSpacing(8);
        hp.add(updateButton);
        hp.add(createButton);
        createButton.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {}
        });
        updateButton.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {}
        });

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

        // heading
        /*
        final SimplePanel heading = new SimplePanel();
        flexFormTable.setWidget(0, 0, heading);
        flexFormTable.getFlexCellFormatter().setColSpan(0, 0, 2);
        flexFormTable.getCellFormatter().setHeight(0, 0, "21px");
        final Label headerLabel = new Label("Rule Definition");
        heading.add(headerLabel);
        headerLabel.setStyleName("gwt-Label-Form-Heading");
        */

        // Name
        final Label nameLabel = new Label("Name");
        flexFormTable.setWidget(1, 0, nameLabel);
        flexFormTable.getCellFormatter().setWidth(1, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(1, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(1, 1, nameTextBox);
        nameTextBox.setWidth("50%");

        // Description
        final Label descriptionLabel = new Label("Description");
        flexFormTable.setWidget(2, 0, descriptionLabel);
        flexFormTable.getCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_TOP);
        flexFormTable.getCellFormatter().setHeight(2, 0, FORM_ROW_HEIGHT);
        flexFormTable.getCellFormatter().setWidth(2, 0, "200px");

        flexFormTable.setWidget(2, 1, descriptionTextArea);
        flexFormTable.getCellFormatter().setWordWrap(2, 1, true);
        flexFormTable.getCellFormatter().setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_TOP);
        descriptionTextArea.setSize("75%", "100%");
        flexFormTable.getCellFormatter().setHeight(2, 1, "93px");

        // Success Message
        final Label successMessageLabel = new Label("Success Message");
        flexFormTable.setWidget(3, 0, successMessageLabel);
        flexFormTable.getCellFormatter().setVerticalAlignment(3, 0, HasVerticalAlignment.ALIGN_TOP);
        flexFormTable.getCellFormatter().setHeight(3, 0, FORM_ROW_HEIGHT);
        flexFormTable.getCellFormatter().setWidth(3, 0, "200px");

        flexFormTable.setWidget(3, 1, successMessageTextArea);
        successMessageTextArea.setTextAlignment(TextBoxBase.ALIGN_LEFT);
        flexFormTable.getCellFormatter().setVerticalAlignment(3, 1, HasVerticalAlignment.ALIGN_TOP);
        successMessageTextArea.setSize("75%", "100%");
        flexFormTable.getCellFormatter().setHeight(3, 1, "93px");

        // Failure Message
        final Label failureMessageLabel = new Label("Failure Message");
        flexFormTable.setWidget(4, 0, failureMessageLabel);
        flexFormTable.getCellFormatter().setVerticalAlignment(4, 0, HasVerticalAlignment.ALIGN_TOP);
        flexFormTable.getCellFormatter().setHeight(4, 0, FORM_ROW_HEIGHT);
        flexFormTable.getCellFormatter().setWidth(4, 0, "200px");

        flexFormTable.setWidget(4, 1, failureMessageTextArea);
        failureMessageTextArea.setSize("75%", "100%");
        flexFormTable.getCellFormatter().setHeight(4, 1, "93px");

        // Business Rule Type
        final Label businessRuleTypeLabel = new Label("Business Rule Type");
        flexFormTable.setWidget(5, 0, businessRuleTypeLabel);
        flexFormTable.getCellFormatter().setHeight(5, 0, FORM_ROW_HEIGHT);

        // flexFormTable.setWidget(5, 1, businessRuleTypeListBox);
        flexFormTable.setWidget(5, 1, businessRuleTypeReadOnly);
        // flexFormTable.getCellFormatter().setHeight(5, 1, FORM_ROW_HEIGHT);
        // businessRuleTypeListBox.addItem("Test1");
        // businessRuleTypeListBox.addItem("Test2");
        // businessRuleTypeListBox.addItem("Test3");

        // Anchor Type
        final Label anchorTypeLabel = new Label("Anchor Type:");
        flexFormTable.setWidget(6, 0, anchorTypeLabel);
        flexFormTable.getCellFormatter().setHeight(6, 0, FORM_ROW_HEIGHT);
        flexFormTable.setWidget(6, 1, anchorTypeReadOnly);

        // Anchor
        final Label anchorLabel = new Label("Anchor");
        flexFormTable.setWidget(7, 0, anchorLabel);
        flexFormTable.getCellFormatter().setWidth(7, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(7, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(7, 1, anchorTextBox);
        anchorTextBox.setWidth("50%");

        // filler
        final SimplePanel filler = new SimplePanel();
        flexFormTable.setWidget(8, 0, filler);
        flexFormTable.getFlexCellFormatter().setColSpan(8, 0, 2);

        flexFormTable.getCellFormatter().setHorizontalAlignment(9, 0, HasHorizontalAlignment.ALIGN_CENTER);
        flexFormTable.getFlexCellFormatter().setColSpan(9, 0, 2);

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
                RuleElementDTO selectedRuleElement = definedPropositions.get(new Integer(box.getValue(box.getSelectedIndex())));
                if (selectedRuleElement == null) {
                    return;
                }
                populatePropositionDetails(selectedRuleElement);
            }
        });
        propositionsListBox.setWidth("150px");

        // List of Propositions table
        final VerticalPanel propListPanel = new VerticalPanel();
        final Label propositionsLabel = new Label("Propositions");
        propListPanel.add(propositionsLabel);
        propListPanel.add(propositionsListBox);

        horizontalPanel.add(propListPanel);

        // proposition details table
        final SimplePanel propDetailsBorder = new SimplePanel();
        // propDetailsBorder.setSize("100%", "100%");
        final FlexTable flexPropositionDetailsTable = new FlexTable();
        // flexPropositionDetailsTable.setSize("100%", "100%");
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
        // flexPropositionDetailsTable.getCellFormatter().setWidth(0, 1, "20%");
        final VerticalPanel vp1 = new VerticalPanel();
        final Label yvfLabel = new Label("YVF");
        vp1.add(yvfLabel);
        GuiUtil.populateYVFList(yvfListBox);
        vp1.add(yvfListBox);
        flexPropositionDetailsTable.setWidget(2, 1, vp1);

        // Operator
        // flexPropositionDetailsTable.getCellFormatter().setWidth(0, 2, "20%");
        final VerticalPanel vp2 = new VerticalPanel();
        final Label operatorLabel = new Label("Operator");
        vp2.add(operatorLabel);

        GuiUtil.populateComparisonOperatorList(operatorsListBox);
        operatorsListBox.setWidth("80px");
        vp2.add(operatorsListBox);
        flexPropositionDetailsTable.setWidget(2, 2, vp2);

        // Expected Value
        // flexPropositionDetailsTable.getCellFormatter().setWidth(0, 3, "30%");
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

        // Proposition Update, Reset, Add, Delete buttons
        final Button updatePropButton = new Button("Update");
        final Button deletePropButton = new Button("Delete");
        final Button resetPropButton = new Button("Reset");
        final Button addPropButton = new Button("Add");

        updatePropButton.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {
                int selectedProp = propositionsListBox.getSelectedIndex();
                if (selectedProp != -1) {
                    RuleElementDTO elem = definedPropositions.get(Integer.parseInt(propositionsListBox.getValue(selectedProp)));
                    elem.setOperation("PROPOSITION");
                    RulePropositionDTO prop = elem.getRuleProposition();
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
                RuleElementDTO elem = new RuleElementDTO();
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
                prop.setComparisonOperatorType(operatorsListBox.getValue(operatorsListBox.getSelectedIndex()));
                elem.setRuleProposition(prop);
                elem.setOperation("PROPOSITION");

                int max = 0;
                for (Integer key : definedPropositions.keySet()) {
                    if (key.intValue() > max) {
                        max = key.intValue();
                    }
                }
                max++;

                elem.setOrdinalPosition(max);
                definedPropositions.put(max, elem);

                populatePropositionListBoxAndDetails(propositionsListBox.getItemCount() - 1);
            }
        });

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

        // Propositions Composition
        final FlexTable ruleCompositionFlexTable = new FlexTable();
        ruleCompositionFlexTable.setSize("100%", "100%");

        final Label propCompositionLabel = new Label("Rule Composition");
        ruleCompositionFlexTable.setWidget(0, 0, propCompositionLabel);
        ruleCompositionFlexTable.getCellFormatter().setHeight(0, 0, FORM_ROW_HEIGHT);
        ruleCompositionFlexTable.setWidget(0, 1, compositionStatuLabel);
        final Button validateCompositionButton = new Button("Validate");
        validateCompositionButton.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {
                compositionStatuLabel.setText(GuiUtil.validateRuleComposition(propCompositionTextArea.getText(), definedPropositions.keySet()));
            }
        });

        ruleCompositionFlexTable.setWidget(0, 2, validateCompositionButton);

        propCompositionTextArea.setSize("100%", "100%");
        propCompositionTextArea.addFocusListener(new FocusListener() {
            public void onFocus(final Widget sender) {

            }

            public void onLostFocus(final Widget sender) {
                // check whether the current composition is valid
                compositionStatuLabel.setText(GuiUtil.validateRuleComposition(propCompositionTextArea.getText(), definedPropositions.keySet()));

                // update Rule Overview text as well
                completeRuleTextArea.setText(GuiUtil.assembleRuleFromComposition(propCompositionTextArea.getText(), definedPropositions));
            }
        });

        ruleCompositionFlexTable.setWidget(1, 0, propCompositionTextArea);
        ruleCompositionFlexTable.getFlexCellFormatter().setColSpan(1, 0, 3);
        ruleCompositionFlexTable.getCellFormatter().setHeight(1, 0, "63px");
        ruleCompositionFlexTable.getCellFormatter().setWidth(1, 0, "60%");

        // Complete Rule
        final Label completeRuleLabel = new Label("Rule Overview");
        ruleCompositionFlexTable.setWidget(2, 0, completeRuleLabel);
        ruleCompositionFlexTable.getCellFormatter().setHeight(2, 0, FORM_ROW_HEIGHT);

        completeRuleTextArea.setSize("100%", "100%");
        completeRuleTextArea.setReadOnly(true);
        ruleCompositionFlexTable.setWidget(3, 0, completeRuleTextArea);
        ruleCompositionFlexTable.getFlexCellFormatter().setColSpan(3, 0, 3);
        ruleCompositionFlexTable.getCellFormatter().setHeight(3, 0, "93px");
        ruleCompositionFlexTable.getCellFormatter().setWidth(3, 0, "60%");

        // filler
        // final SimplePanel filler = new SimplePanel();
        // ruleCompositionFlexTable.setWidget(20, 0, filler);
        // ruleCompositionFlexTable.getFlexCellFormatter().setColSpan(20, 0, 4);

        // ruleCompositionFlexTable.getCellFormatter().setHorizontalAlignment(21, 0, HasHorizontalAlignment.ALIGN_CENTER);
        // ruleCompositionFlexTable.getFlexCellFormatter().setColSpan(21, 0, 2);

        verticalPanel.add(ruleCompositionFlexTable);
        verticalPanel.setCellHeight(horizontalPanel, "200px");

        return propositionsFlexTable;
    }

    private Widget addRRulesAuthoringPage() {

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

        // Status
        final Label statusLabel = new Label("Status");
        flexFormTable.setWidget(1, 0, statusLabel);
        flexFormTable.getCellFormatter().setWidth(1, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(1, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(1, 1, statusTextBox);
        statusTextBox.setWidth("30%");

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

        // Update Time
        final Label updateTimeLabel = new Label("Update Time");
        flexFormTable.setWidget(6, 0, updateTimeLabel);
        flexFormTable.getCellFormatter().setWidth(6, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(6, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(6, 1, updateTimeTextBox);
        updateTimeTextBox.setWidth("30%");

        // Update User ID
        final Label updateUserIdLabel = new Label("Update Rule User Id");
        flexFormTable.setWidget(7, 0, updateUserIdLabel);
        flexFormTable.getCellFormatter().setWidth(7, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(7, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(7, 1, updateUserIdTextBox);
        updateUserIdTextBox.setWidth("30%");

        // filler
        final SimplePanel filler = new SimplePanel();
        flexFormTable.setWidget(8, 0, filler);
        flexFormTable.getFlexCellFormatter().setColSpan(8, 0, 2);

        flexFormTable.getCellFormatter().setHorizontalAlignment(9, 0, HasHorizontalAlignment.ALIGN_CENTER);
        flexFormTable.getFlexCellFormatter().setColSpan(9, 0, 2);

        return rulesFlexTable;
    }

}
