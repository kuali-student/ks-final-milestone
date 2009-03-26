package org.kuali.student.ui.kitchensink.client;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_CONTENT_PANEL;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_MAIN_PANEL;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_MENU_PANEL;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_WELCOME_IMAGE;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_WELCOME_PANEL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.menus.KSAccordionMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.ui.kitchensink.client.kscommons.accordionmenu.AccordionMenuExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.accordionpanel.AccordionPanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.blockingprogressindicator.BlockingProgressIndicatorExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.breadcrumb.BreadcrumbExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.button.ButtonExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.checkbox.CheckBoxExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.checkboxlist.CheckBoxListDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.collapsablefloatpanel.CollapsableFloatPanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.confirmationdialog.ConfirmationDialogExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.confirmbuttonpanel.ConfirmButtonPanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.datepicker.DatePickerExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.dialogpanel.DialogPanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.disclosuresection.DisclosureSectionExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.dropdown.DropDownExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.floatpanel.FloatPanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.formlayoutpanel.FormLayoutPanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.helplink.HelpLinkExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.image.ImageExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.infodialogpanel.InfoDialogPanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.label.LabelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.listbox.ListBoxExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.modaldialogpanel.ModalDialogPanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.picklist.PickListDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.progressindicator.ProgressIndicatorExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.radiobutton.RadioButtonExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.radiobuttonlist.RadioButtonListDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.resizablepanel.ResizablePanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.richeditor.RichEditorExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.ruletable.RuleTableExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.selectabletable.SelectableTableDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.sidebar.SidebarExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.tabpanel.TabPanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.textarea.TextAreaExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.textbox.TextBoxExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.tooltip.ToolTipExampleDescriptor;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class KitchenSinkMain extends Composite {

    private final static String WELCOME_MSG = "\n\n Welcome to the Kuali Student Kitchen Sink \n\n" +
    "This is a catalog of all widgets developed by Kuali Student. Click the menus on the left to view the widgets";

    private static final String BASIC_WIDGET_MSG = "These are the basic building block widgets, such as buttons, labels, checkboxes and more.";
    private static final String INFO_WIDGET_MSG = "These are widgets used to display information such as additional help or tool tips";
    private static final String NAVIGATION_WIDGET_MSG = "These are widgets that can be used to allow users to navigate  a set of options";
    private static final String TEXT_WIDGET_MSG = "These are widgets used for entering and updating text and include a simple text box to a full featured rich text editor.";
    private static final String PANEL_WIDGET_MSG = "These are widgets that display a content panel either inline or in a separate popup window.  They may be modal, resizeable, moveable or not , depending on the requirements.  These panels will act as containers for other widgets.";
    private static final String STATUS_WIDGET_MSG = "These are widgets that indicate some action in progress";
    private static final String LIST_WIDGET_MSG = "These are widgets that display some list of data";
    private static final String DIALOG_WIDGET_MSG = "These are widgets that popup a panel over other widgets for user interaction or to display some information.";
    private static final String RULE_WIDGET_MSG = "These are widgets that are used for rule maintenance including rule builder widgets.";

    private static final String COMMON_WIDGET_MSG = "\n\nClick on the menu options on the left to try out the widgets of this type.";

    private static final String BASIC_WIDGETS = "Basic Widgets";
    private static final String INFO_WIDGETS = "Information Widgets";
    private static final String NAVIGATION_WIDGETS = "Navigation Widgets";
    private static final String TEXT_WIDGETS = "Text Widgets";
    private static final String PANEL_WIDGETS = "Panel Widgets";
    private static final String STATUS_WIDGETS = "Status Widgets";
    private static final String LIST_WIDGETS = "List Widgets";
    private static final String DIALOG_WIDGETS = "Dialog Widgets";
    private static final String RULE_WIDGETS = "Rule Widgets";
    private static final String FORM_WIDGETS = "Form Widgets";

    final HorizontalPanel main = new HorizontalPanel();
    final SimplePanel contentPanel = new SimplePanel(); // content panel
    VerticalPanel welcomePanel = new VerticalPanel();
    final KSMenu menuPanel = new KSAccordionMenu(); // TODO update deferred binding in common-ui
    KSLabel welcomeMsg;
    final KSImage ksImage = new KSImage("images/KULSTP.jpg");



    boolean loaded = false;

    private final Map<String, KitchenSinkExample> exampleMap = new HashMap<String, KitchenSinkExample>();

    public KitchenSinkMain() {
        super.initWidget(main);
        initExamples();
    }

    public KitchenSinkExample getExample(String className) {
        return exampleMap.get(className);
    }

    protected void onLoad() {
        super.onLoad();
        if (!loaded) {
            loaded = true;

            ksImage.setStyleName(STYLE_WELCOME_IMAGE);

            main.setStyleName(STYLE_MAIN_PANEL);
            menuPanel.setStyleName(STYLE_MENU_PANEL);
            contentPanel.setStyleName(STYLE_CONTENT_PANEL);     

            welcomePanel.add(ksImage);
            welcomeMsg = new KSLabel(WELCOME_MSG, true);
            welcomePanel.addStyleName(STYLE_WELCOME_PANEL);
            welcomePanel.add(welcomeMsg);              
            contentPanel.add(welcomePanel);

            main.add(menuPanel);
            main.add(contentPanel);
            main.setCellWidth(menuPanel, "200px");

        }
    }

    private void initExamples() {
        List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();

        KSMenuItemData ksCommons = initTopGroup("KS Common Widgets");

        KSMenuItemData ksBasic = initGroup(BASIC_WIDGETS);
        KSMenuItemData ksStatus = initGroup(STATUS_WIDGETS);
        KSMenuItemData ksInfo = initGroup(INFO_WIDGETS);
        KSMenuItemData ksPanels = initGroup(PANEL_WIDGETS);
        KSMenuItemData ksText = initGroup(TEXT_WIDGETS);
        KSMenuItemData ksList = initGroup(LIST_WIDGETS);
        KSMenuItemData ksNav = initGroup(NAVIGATION_WIDGETS);
        KSMenuItemData ksDialog = initGroup(DIALOG_WIDGETS);
        KSMenuItemData ksRule = initGroup(RULE_WIDGETS);
        KSMenuItemData ksForm = initGroup(FORM_WIDGETS);

        initExample(ksBasic, new ButtonExampleDescriptor());
        initExample(ksBasic, new CheckBoxExampleDescriptor());
        initExample(ksBasic, new DatePickerExampleDescriptor());
        initExample(ksBasic, new DropDownExampleDescriptor());
        initExample(ksBasic, new ImageExampleDescriptor());
        initExample(ksBasic, new LabelExampleDescriptor());
        initExample(ksBasic, new ListBoxExampleDescriptor());
        initExample(ksBasic, new RadioButtonExampleDescriptor());

        initExample(ksText, new RichEditorExampleDescriptor());
        initExample(ksText, new TextAreaExampleDescriptor());
        initExample(ksText, new TextBoxExampleDescriptor());

        initExample(ksStatus, new BlockingProgressIndicatorExampleDescriptor());
        initExample(ksStatus, new ProgressIndicatorExampleDescriptor());
        initExample(ksStatus, new HelpLinkExampleDescriptor());  

        initExample(ksPanels, new AccordionPanelExampleDescriptor());
        initExample(ksPanels, new DisclosureSectionExampleDescriptor());
        initExample(ksPanels, new ConfirmButtonPanelExampleDescriptor());
        initExample(ksPanels, new TabPanelExampleDescriptor());
        initExample(ksPanels, new ResizablePanelExampleDescriptor());

        initExample(ksDialog, new CollapsableFloatPanelExampleDescriptor());
        initExample(ksDialog, new FloatPanelExampleDescriptor());
        initExample(ksDialog, new InfoDialogPanelExampleDescriptor());
        initExample(ksDialog, new DialogPanelExampleDescriptor());
        initExample(ksDialog, new ModalDialogPanelExampleDescriptor());
        initExample(ksDialog, new ConfirmationDialogExampleDescriptor());
        initExample(ksDialog, new SidebarExampleDescriptor());

        initExample(ksInfo, new ToolTipExampleDescriptor());
        initExample(ksInfo, new HelpLinkExampleDescriptor()); 

        initExample(ksNav, new AccordionMenuExampleDescriptor());
        initExample(ksNav, new BreadcrumbExampleDescriptor());
        initExample(ksNav, new TabPanelExampleDescriptor());
        initExample(ksNav, new SidebarExampleDescriptor());

        initExample(ksList, new SelectableTableDescriptor());
        initExample(ksList, new PickListDescriptor());
        initExample(ksList, new RadioButtonListDescriptor());
        initExample(ksList, new CheckBoxListDescriptor());
        
        initExample(ksRule, new RuleTableExampleDescriptor());
        
        initExample(ksForm, new FormLayoutPanelExampleDescriptor());

        ksCommons.addSubItem(ksBasic);
        ksCommons.addSubItem(ksDialog);
        ksCommons.addSubItem(ksInfo);
        ksCommons.addSubItem(ksNav);
        ksCommons.addSubItem(ksPanels);
        ksCommons.addSubItem(ksStatus);
        ksCommons.addSubItem(ksText);
        ksCommons.addSubItem(ksList);
        ksCommons.addSubItem(ksRule);
        ksCommons.addSubItem(ksForm);
        
        items.add(ksCommons);

        menuPanel.setItems(items);

    }

    private KSMenuItemData initTopGroup(String groupName) {

        KSMenuItemData groupItem = new KSMenuItemData(groupName);

        groupItem.setClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent arg0) {

                if (contentPanel.getWidget() != null) {
                    contentPanel.remove(contentPanel.getWidget());
                }
                contentPanel.setWidget(welcomePanel);                
            }
        }); 

        return groupItem;
    }

    private KSMenuItemData initGroup(String groupName) {

        KSMenuItemData groupItem = new KSMenuItemData(groupName);


        groupItem.setClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent arg0) {

                if (contentPanel.getWidget() != null) {
                    contentPanel.remove(contentPanel.getWidget());
                }

                contentPanel.setWidget(buildGroupMessage(arg0));                
            }
        });       
        return groupItem;
    }

    private void initExample(final KSMenuItemData group, 
            final KitchenSinkExample example) {

        KSMenuItemData item = new KSMenuItemData(example.getTitle());

        item.setClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent arg0) {
                if (contentPanel.getWidget() != null) {
                    contentPanel.remove(contentPanel.getWidget());
                }
                contentPanel.setWidget(example);                
            }
        });
        group.addSubItem(item);
        exampleMap.put(example.getClass().getName(), example);
    }

    private KSLabel buildGroupMessage(ClickEvent arg0) {
        FocusPanel panel = (FocusPanel)arg0.getSource();
        String source = ((Label)panel.getWidget()).getText();

        KSLabel label = new KSLabel();;

        if (source.equals(BASIC_WIDGETS)) {
            label.setText(BASIC_WIDGET_MSG + ' ' + COMMON_WIDGET_MSG);
        }
        else if (source.equals(TEXT_WIDGETS)) {
            label.setText(TEXT_WIDGET_MSG + ' ' + COMMON_WIDGET_MSG);
        }
        else if (source.equals(PANEL_WIDGETS)) {
            label.setText(PANEL_WIDGET_MSG + ' ' + COMMON_WIDGET_MSG);
        }
        else if (source.equals(INFO_WIDGETS)) {
            label.setText(INFO_WIDGET_MSG + ' ' + COMMON_WIDGET_MSG);
        }
        else if (source.equals(STATUS_WIDGETS)) {
            label.setText(STATUS_WIDGET_MSG + ' ' + COMMON_WIDGET_MSG);
        }
        else if (source.equals(NAVIGATION_WIDGETS)) {
            label.setText(NAVIGATION_WIDGET_MSG + ' ' + COMMON_WIDGET_MSG);
        }
        else if (source.equals(DIALOG_WIDGETS)) {
            label.setText(DIALOG_WIDGET_MSG + ' ' + COMMON_WIDGET_MSG);
        }
        else if (source.equals(LIST_WIDGETS)) {
            label.setText(LIST_WIDGET_MSG + ' ' + COMMON_WIDGET_MSG);
        }
        else if (source.equals(RULE_WIDGETS)) {
            label.setText(RULE_WIDGET_MSG + ' ' + COMMON_WIDGET_MSG);
        }

        return label;
    }



}
