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
import org.kuali.student.commons.ui.widgets.tables.ModelTableSelectionListener;
import org.kuali.student.rules.devgui.client.controller.DevelopersGuiController;
import org.kuali.student.rules.devgui.client.model.RuleTypesHierarchyInfo;
import org.kuali.student.rules.devgui.client.service.DevelopersGuiService;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleTypeInfoDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Zdenek
 */
public class RuleTypesComposite extends Composite {

    final String FORM_ROW_HEIGHT = "22px";

    // events to be fired to parent controller
    public static class RuleTypesEvent extends MVCEvent {}

    public static class RuleTypesAddEvent extends RuleTypesEvent {}

    public static class RuleTypesUpdateEvent extends RuleTypesEvent {}

    public static class RuleTypesTestEvent extends RuleTypesEvent {}

    // singleton instances of the events
    public static final RuleTypesEvent RULES_EVENT = GWT.create(RuleTypesEvent.class);
    public static final RuleTypesAddEvent RULES_ADD_EVENT = GWT.create(RuleTypesAddEvent.class);
    public static final RuleTypesUpdateEvent RULES_UPDATE_EVENT = GWT.create(RuleTypesUpdateEvent.class);
    public static final RuleTypesTestEvent RULES_REMOVE_EVENT = GWT.create(RuleTypesTestEvent.class);

    // controller and metadata to be looked up externally
    Controller controller;
    ViewMetaData metadata;
    Messages messages;

    // class that binds a widget to a model, instantiation is deferred
    // until application state is guaranteed to be ready
    ModelBinding<RuleTypesHierarchyInfo> binding;

    // widgets used for Rules forms.....
    final BusinessRuleTypesTree ruleTypesTree = new BusinessRuleTypesTree(); // used to browse Rule Types
    final ScrollPanel rulesBrowserScrollPanel = new ScrollPanel();
    final HorizontalSplitPanel ruleTypesHorizontalSplitPanel = new HorizontalSplitPanel();
    final ScrollPanel ruleTypesScrollPanel = new ScrollPanel();
    final VerticalSplitPanel ruleTypesVerticalSplitPanel = new VerticalSplitPanel();
    final SimplePanel simplePanel = new SimplePanel();

    // busines rule type form
    final TextBox nameTextBox = new TextBox();
    final TextBox anchorTypeTextBox = new TextBox();
    final TextArea descriptionTextArea = new TextArea();
    final ListBox availableFactsListBox = new ListBox();
    final ListBox selectedFactsListBox = new ListBox();

    boolean loaded = false;

    private BusinessRuleTypeInfoDTO activeRuleType; // keep copy of business rule type so we can update all fields user can

    // change

    public RuleTypesComposite() {
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
            metadata = ApplicationContext.getViews().get(DevelopersGuiController.VIEW_NAME);
            messages = metadata.getMessages();

            // bind the PeopleTable to the parent controller's Model of BusinesRule objects
            Model<RuleTypesHierarchyInfo> model = (Model<RuleTypesHierarchyInfo>) controller.getModel(RuleTypesHierarchyInfo.class);
            binding = new ModelBinding<RuleTypesHierarchyInfo>(model, ruleTypesTree);

            // add selection event listener to ruleTypesTree widget
            /* commented out because fix for org.kuali.student.commons.ui.widgets.trees.SimpleTree.java was not yet
             * checked in to ks-commons-ui-dev module
             */
            ruleTypesTree.addSelectionListener(new ModelTableSelectionListener<RuleTypesHierarchyInfo>() {
                public void onSelect(RuleTypesHierarchyInfo modelObject) {

                    if (modelObject == null) {
                        // selection was cleared
                        clearBusinessRuleTypeForm();
                    } else {
                        String businessRuleTypeKey = modelObject.getBusinessRuleTypeKey();
                        String anchorTypeKey = modelObject.getAnchorTypeKey();
                        // populate fields from new selection
                        DevelopersGuiService.Util.getInstance().fetchBusinessRuleType(businessRuleTypeKey, anchorTypeKey, new AsyncCallback<BusinessRuleTypeInfoDTO>() {
                            public void onFailure(Throwable caught) {
                                // just re-throw it and let the uncaught exception handler deal with it
                                Window.alert(caught.getMessage());
                                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                            }

                            public void onSuccess(BusinessRuleTypeInfoDTO ruleTypeInfo) {
                                // store selected business rule in temporary object
                                activeRuleType = ruleTypeInfo;

                                // populate the business rule type details
                                populateRuleTypeForm();
                            }
                        });
                    }
                }
            });

            // create tree-like rules browser
            ruleTypesTree.setSize("100%", "100%");
            rulesBrowserScrollPanel.add(ruleTypesTree);

            ruleTypesHorizontalSplitPanel.setLeftWidget(ruleTypesTree);
            ruleTypesTree.setStyleName("gwt-Tree-rules");
            ruleTypesHorizontalSplitPanel.setRightWidget(getBusinessRuleTypesForm());
            ruleTypesHorizontalSplitPanel.setSplitPosition("30%");
            // rulesHorizontalSplitPanel.setSize("100%", "100%");

            ruleTypesScrollPanel.setSize("100%", "100%");
            ruleTypesVerticalSplitPanel.setSize("100%", "800px");
            ruleTypesVerticalSplitPanel.setTopWidget(ruleTypesHorizontalSplitPanel);
            ruleTypesVerticalSplitPanel.setBottomWidget(ruleTypesScrollPanel);
            ruleTypesScrollPanel.setStyleName("rulesBorder");
            ruleTypesVerticalSplitPanel.setSplitPosition("80%");
            simplePanel.add(ruleTypesVerticalSplitPanel);
        }
    }

    @Override
    protected void onUnload() {
        super.onUnload();
        // unlink the binding as it is no longer needed
        // binding.unlink();
    }

    private void clearBusinessRuleTypeForm() {
        nameTextBox.setText("");
        anchorTypeTextBox.setText("");
        descriptionTextArea.setText("");
        availableFactsListBox.clear();
        selectedFactsListBox.clear();
    }

    private void populateRuleTypeForm() {
        nameTextBox.setText(activeRuleType.getBussinessRuleTypeKey());
        anchorTypeTextBox.setText(activeRuleType.getAnchorTypeKey());
        // descriptionTextArea.setText(activeRuleType.getDescription());  Kamal TODO
        availableFactsListBox.clear();
        selectedFactsListBox.clear();
    }

    private Widget getBusinessRuleTypesForm() {

        // **********************************************************
        // set rules form margins
        // **********************************************************
        final FlexTable businesRuleTypesFlexTable = new FlexTable();
        businesRuleTypesFlexTable.setTitle("Rule Types");
        businesRuleTypesFlexTable.setSize("100%", "100%");

        final SimplePanel topMargin = new SimplePanel();
        businesRuleTypesFlexTable.setWidget(0, 0, topMargin);
        businesRuleTypesFlexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        businesRuleTypesFlexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
        businesRuleTypesFlexTable.getCellFormatter().setHeight(0, 0, "5px");

        final SimplePanel leftMargin = new SimplePanel();
        businesRuleTypesFlexTable.setWidget(1, 0, leftMargin);
        businesRuleTypesFlexTable.getCellFormatter().setWidth(1, 0, "5px");

        // **********************************************************
        // set rules form size
        // **********************************************************
        final FormPanel rulesFormPanel = new FormPanel();
        businesRuleTypesFlexTable.setWidget(1, 1, rulesFormPanel);
        businesRuleTypesFlexTable.getCellFormatter().setWidth(1, 1, "100%");
        rulesFormPanel.setWidth("100%");
        businesRuleTypesFlexTable.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);

        final FlexTable flexFormTable = new FlexTable();
        rulesFormPanel.add(flexFormTable);
        flexFormTable.setSize("100%", "100%");

        // **********************************************************
        // rules form elements
        // **********************************************************

        // heading
        final SimplePanel heading = new SimplePanel();
        flexFormTable.setWidget(0, 0, heading);
        flexFormTable.getFlexCellFormatter().setColSpan(0, 0, 6);
        flexFormTable.getCellFormatter().setHeight(0, 0, "21px");
        final Label headerLabel = new Label("Business Rule Type Definition");
        heading.add(headerLabel);
        headerLabel.setStyleName("gwt-Label-Form-Heading");

        // Key
        final Label keyLabel = new Label("Key");
        flexFormTable.setWidget(1, 0, keyLabel);
        flexFormTable.getCellFormatter().setWidth(1, 0, "251px");
        flexFormTable.getCellFormatter().setHeight(1, 0, FORM_ROW_HEIGHT);

        flexFormTable.setWidget(1, 2, nameTextBox);
        flexFormTable.getFlexCellFormatter().setColSpan(1, 2, 4);
        nameTextBox.setWidth("50%");

        // Anchor Type Key
        final Label anchorTypeKeyLabel = new Label("Anchor Type Key");
        flexFormTable.setWidget(2, 0, anchorTypeKeyLabel);
        flexFormTable.getCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_TOP);
        flexFormTable.getCellFormatter().setHeight(2, 0, FORM_ROW_HEIGHT);
        flexFormTable.getCellFormatter().setWidth(2, 0, "251px");

        flexFormTable.setWidget(2, 2, anchorTypeTextBox);
        flexFormTable.getFlexCellFormatter().setColSpan(2, 2, 4);
        anchorTypeTextBox.setWidth("50%");

        // description
        final Label descriptionLabel = new Label("Decription");
        flexFormTable.setWidget(3, 0, descriptionLabel);
        flexFormTable.getCellFormatter().setVerticalAlignment(3, 0, HasVerticalAlignment.ALIGN_TOP);
        flexFormTable.getCellFormatter().setHeight(3, 0, FORM_ROW_HEIGHT);
        flexFormTable.getCellFormatter().setWidth(3, 0, "251px");

        flexFormTable.setWidget(3, 2, descriptionTextArea);
        flexFormTable.getFlexCellFormatter().setColSpan(3, 2, 4);
        flexFormTable.getCellFormatter().setWordWrap(3, 2, true);
        flexFormTable.getCellFormatter().setVerticalAlignment(3, 2, HasVerticalAlignment.ALIGN_TOP);
        descriptionTextArea.setSize("75%", "100%");
        flexFormTable.getCellFormatter().setHeight(3, 2, "93px");

        // filler
        final SimplePanel filler = new SimplePanel();
        flexFormTable.setWidget(4, 0, filler);
        flexFormTable.getFlexCellFormatter().setColSpan(4, 0, 6);

        final Button saveRuleButton = new Button();
        flexFormTable.setWidget(7, 0, saveRuleButton);
        flexFormTable.getCellFormatter().setHorizontalAlignment(7, 0, HasHorizontalAlignment.ALIGN_CENTER);
        flexFormTable.getCellFormatter().setHorizontalAlignment(10, 0, HasHorizontalAlignment.ALIGN_CENTER);
        flexFormTable.getFlexCellFormatter().setColSpan(7, 0, 6);
        saveRuleButton.setText("Save");

        // Available and Selected Facts
        final Label availableFactsLabel = new Label("Available Facts");
        flexFormTable.setWidget(5, 0, availableFactsLabel);

        final Label selectedFactsLabel = new Label("Selected Facts");
        flexFormTable.setWidget(5, 5, selectedFactsLabel);

        flexFormTable.setWidget(6, 0, availableFactsListBox);
        availableFactsListBox.setSize("75%", "100%");
        availableFactsListBox.setVisibleItemCount(10);

        flexFormTable.setWidget(6, 5, selectedFactsListBox);
        selectedFactsListBox.setSize("75%", "100%");
        selectedFactsListBox.setVisibleItemCount(10);

        final FlexTable flexTable = new FlexTable();
        flexFormTable.setWidget(6, 2, flexTable);

        final Button button_1 = new Button();
        flexTable.setWidget(0, 1, button_1);
        flexTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
        button_1.setText("   <<<   ");

        final Button button = new Button();
        flexTable.setWidget(2, 1, button);
        button.setText("   >>>   ");

        return businesRuleTypesFlexTable;
    }

}
