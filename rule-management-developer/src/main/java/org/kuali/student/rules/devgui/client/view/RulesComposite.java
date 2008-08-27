/**
 * 
 */
package org.kuali.student.rules.devgui.client.view;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelBinding;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.rules.devgui.client.controller.DevelopersGuiMain;
import org.kuali.student.rules.devgui.client.model.BusinessRuleElement;
import org.kuali.student.rules.devgui.client.model.BusinessRuleInfo;
import org.kuali.student.rules.devgui.client.model.BusinessRuleProposition;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
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

    final String FORM_ROW_HEIGHT = "22px";

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
    final ListBox operatorsListBox = new ListBox();
    final ListBox propositionsListBox = new ListBox();
    final TextArea completeRuleTextArea = new TextArea();
    final TextArea propCompositionTextArea = new TextArea();
    final TextBox expectedValueTextBox = new TextBox();

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

    @Override
    protected void onLoad() {
        super.onLoad();
        if (!loaded) {
            loaded = true;
            // get a reference to our parent controller
            controller = MVC.findParentController(this);

            // get a reference to our view metadata and internationalization messages
            metadata = ApplicationContext.getViews().get(DevelopersGuiMain.VIEW_NAME);
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
            rulesVerticalSplitPanel.setSize("100%", "800px");
            rulesVerticalSplitPanel.setTopWidget(rulesHorizontalSplitPanel);
            rulesVerticalSplitPanel.setBottomWidget(rulesScrollPanel);
            rulesVerticalSplitPanel.setSplitPosition("80%");
            // simplePanel.setSize("100%", "100%");
            simplePanel.add(rulesVerticalSplitPanel);

            // add selection event listener to rulesTree widget
            /* commented out because fix for org.kuali.student.commons.ui.widgets.trees.SimpleTree.java was not yet
             * checked in to ks-commons-ui-dev module
             */
            /*

                rulesTree.addSelectionListener(new ModelTableSelectionListener<RulesHierarchyInfo>() {
                    public void onSelect(RulesHierarchyInfo modelObject) {
                        String ruleId = modelObject.getBusinessRuleId();
                        if (modelObject == null) {
                            // selection was cleared
                            clearRuleForms();
                        } else {
                            // populate fields from new selection
                            DevGuiService.Util.getInstance().fetchDetailedBusinessRuleInfo(ruleId, new AsyncCallback<BusinessRuleInfo>() {
                                public void onFailure(Throwable caught) {
                                    // just rethrow it and let the uncaught exception handler deal with it
                                    Window.alert(caught.getMessage());
                                    // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                                }

                                public void onSuccess(BusinessRuleInfo ruleInfo) {
                                    // add the results to the model
                                    populateRuleForms(ruleInfo);
                                }
                        }
                    }
                });  */
        }
    }

    @Override
    protected void onUnload() {
        super.onUnload();
        // unlink the binding as it is no longer needed
        binding.unlink();
    }

    public void populateRuleForms(BusinessRuleInfo rule) {
        // populate Main TAB
        nameTextBox.setText(rule.getName());
        descriptionTextArea.setText(rule.getDescription());
        successMessageTextArea.setText(rule.getSuccessMessage());
        failureMessageTextArea.setText(rule.getFailureMessage());
        businessRuleTypeReadOnly.setText(rule.getBusinessRuleTypeKey());
        // businessRuleTypeListBox.setValue(0, rule.getBusinessRuleTypeKey());
        // businessRuleTypeListBox.setItemSelected(0, true);
        anchorTypeReadOnly.setText(rule.getAnchorTypeKey());
        anchorTextBox.setText(rule.getAnchorValue());

        // populate Propositions TAB
        StringBuffer ruleComposition = new StringBuffer();
        StringBuffer ruleCompleteText = new StringBuffer();
        for (BusinessRuleElement elem : rule.getRuleElementList()) {

            if (elem.getOperation().equals("PROPOSITION")) {
                BusinessRuleProposition prop = elem.getRuleProposition();
                propositionsListBox.addItem(prop.getName());
                yvfListBox.setItemSelected(1, true); // TODO if..
                operatorsListBox.setValue(0, "GREATER_THAN");
                expectedValueTextBox.setText(prop.getRightHandSide());
                ruleComposition.append("P1");
                ruleCompleteText.append(prop.getLeftHandSide() + " " + prop.getComparisonOperatorType() + " " + prop.getRightHandSide());
            }
        }

        propCompositionTextArea.setText(ruleComposition.toString());
        completeRuleTextArea.setText(ruleCompleteText.toString());

        // populate Authoring TAB
        statusTextBox.setText(rule.getStatus());
        effectiveStartTimeTextBox.setText(rule.getEffectiveStartTime());
        effectiveEndTimeTextBox.setText(rule.getEffectiveEndTime());
        createTimeTextBox.setText(rule.getCreateTime());
        createUserIdTextBox.setText(rule.getCreateUserId());
        updateTimeTextBox.setText(rule.getUpdateTime());
        updateUserIdTextBox.setText(rule.getUpdateUserId());
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
        // yvfListBox
        // operatorsListBox
        propositionsListBox.clear();
        completeRuleTextArea.setText("");
        propCompositionTextArea.setText("");
        expectedValueTextBox.setText("");

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
        rulesFormTabs.setSize("90%", "500px");

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

        final VerticalPanel rulesFormVerticalPanel = new VerticalPanel();
        rulesFormVerticalPanel.setSpacing(5);
        rulesFormVerticalPanel.add(rulesFormTabs);
        rulesFormVerticalPanel.add(hp);
        rulesFormVerticalPanel.setSize("100%", "100%");

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
        propositionsFlexTable.getCellFormatter().setWidth(1, 0, "526px");

        // **********************************************************
        // set rules form size
        // **********************************************************
        final FormPanel rulesFormPanel = new FormPanel();
        propositionsFlexTable.setWidget(1, 0, rulesFormPanel);
        propositionsFlexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
        propositionsFlexTable.getCellFormatter().setWidth(1, 0, "100%");
        rulesFormPanel.setWidth("100%");
        propositionsFlexTable.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);

        final FlexTable flexFormTable = new FlexTable();
        rulesFormPanel.add(flexFormTable);
        flexFormTable.setSize("100%", "100%");

        // **********************************************************
        // rules form elements
        // **********************************************************

        // List of Propositions
        final Label propositionsLabel = new Label("Propositions");
        flexFormTable.setWidget(1, 0, propositionsLabel);
        flexFormTable.getCellFormatter().setWidth(1, 0, "30%");
        flexFormTable.getCellFormatter().setHeight(1, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(2, 0, propositionsListBox);
        propositionsListBox.setSize("75%", "100%");
        propositionsListBox.setVisibleItemCount(5);
        flexFormTable.getCellFormatter().setHeight(2, 0, "93px");

        // YVF
        flexFormTable.getCellFormatter().setWidth(2, 1, "20%");
        final VerticalPanel vp1 = new VerticalPanel();
        final Label yvfLabel = new Label("YVF");
        vp1.add(yvfLabel);
        // flexFormTable.setWidget(1, 1, yvfLabel);
        // flexFormTable.getCellFormatter().setWidth(3, 0, "200px");
        // flexFormTable.getCellFormatter().setHeight(1, 1, FORM_ROW_HEIGHT);

        // flexFormTable.setWidget(2, 1, yvfListBox);
        // flexFormTable.getCellFormatter().setHeight(2, 1, FORM_ROW_HEIGHT);
        yvfListBox.addItem("Sum");
        yvfListBox.addItem("Intersection");
        yvfListBox.addItem("Count");
        vp1.add(yvfListBox);
        flexFormTable.setWidget(2, 1, vp1);

        // Operator
        flexFormTable.getCellFormatter().setWidth(2, 2, "20%");
        final VerticalPanel vp2 = new VerticalPanel();
        final Label operatorLabel = new Label("Operator");
        vp2.add(operatorLabel);
        // flexFormTable.setWidget(1, 2, operatorLabel);
        // flexFormTable.getCellFormatter().setWidth(3, 0, "200px");
        // flexFormTable.getCellFormatter().setHeight(1, 2, FORM_ROW_HEIGHT);

        // flexFormTable.setWidget(2, 2, operatorsListBox);
        // flexFormTable.getCellFormatter().setHeight(2, 2, FORM_ROW_HEIGHT);
        operatorsListBox.addItem(" == ");
        operatorsListBox.addItem(" <> ");
        operatorsListBox.addItem(" > ");
        vp2.add(operatorsListBox);
        flexFormTable.setWidget(2, 2, vp2);

        // Expected Value
        flexFormTable.getCellFormatter().setWidth(2, 3, "20%");
        final VerticalPanel vp3 = new VerticalPanel();
        final Label expectedValueLabel = new Label("Expected Value");
        vp3.add(expectedValueLabel);
        // flexFormTable.setWidget(1, 3, expectedValueLabel);
        // flexFormTable.getCellFormatter().setWidth(3, 0, "200px");
        // flexFormTable.getCellFormatter().setHeight(1, 3, FORM_ROW_HEIGHT);

        // flexFormTable.setWidget(1, 3, expectedValueTextBox);
        expectedValueTextBox.setWidth("80%");
        vp3.add(expectedValueTextBox);
        flexFormTable.setWidget(2, 3, vp3);

        // Propositions Composition
        final Label propCompositionLabel = new Label("Propositions Composition");
        flexFormTable.setWidget(15, 0, propCompositionLabel);
        flexFormTable.getCellFormatter().setWidth(15, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(15, 0, FORM_ROW_HEIGHT);

        propCompositionTextArea.setSize("100%", "100%");
        flexFormTable.getCellFormatter().setHeight(16, 1, "63px");
        flexFormTable.setWidget(16, 0, propCompositionTextArea);
        flexFormTable.getFlexCellFormatter().setColSpan(16, 0, 3);

        // Complete Rule
        final Label completeRuleLabel = new Label("Complete Rule");
        flexFormTable.setWidget(18, 0, completeRuleLabel);
        flexFormTable.getCellFormatter().setWidth(18, 0, "200px");
        flexFormTable.getCellFormatter().setHeight(18, 0, FORM_ROW_HEIGHT);

        completeRuleTextArea.setSize("100%", "100%");
        flexFormTable.getCellFormatter().setHeight(19, 0, "93px");
        flexFormTable.setWidget(19, 0, completeRuleTextArea);
        flexFormTable.getFlexCellFormatter().setColSpan(19, 0, 3);

        // filler
        final SimplePanel filler = new SimplePanel();
        flexFormTable.setWidget(20, 0, filler);
        flexFormTable.getFlexCellFormatter().setColSpan(20, 0, 4);

        flexFormTable.getCellFormatter().setHorizontalAlignment(21, 0, HasHorizontalAlignment.ALIGN_CENTER);
        flexFormTable.getFlexCellFormatter().setColSpan(21, 0, 2);

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
