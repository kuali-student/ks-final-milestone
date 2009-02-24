package org.kuali.student.ui.kitchensink.client;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_CONTENT_PANEL;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_MAIN_PANEL;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_MENU_PANEL;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_WELCOME_PANEL;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.menus.KSAccordionMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.ui.kitchensink.client.gwtexamples.LayoutExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.gwtexamples.TestExample;
import org.kuali.student.ui.kitchensink.client.kscommons.accordionpanel.AccordionPanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.blockingprogressindicator.BlockingProgressIndicatorExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.button.ButtonExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.checkbox.CheckBoxExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.datepicker.DatePickerExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.disclosuresection.DisclosureSectionExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.dropdown.DropDownExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.image.ImageExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.infopopuppanel.InfoPopupExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.label.LabelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.listbox.ListBoxExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.modalpopuppanel.ModalPopupPanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.radiobutton.RadioButtonExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.richeditor.RichEditorExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.textarea.TextAreaExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.textbox.TextBoxExampleDescriptor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class KitchenSinkMain extends Composite {
    final HorizontalPanel main = new HorizontalPanel();
    final SimplePanel contentPanel = new SimplePanel(); // content panel
    final VerticalPanel welcomePanel = new VerticalPanel();
    final KSMenu menuPanel = GWT.create(KSAccordionMenu.class); // TODO update deferred binding in common-ui
    final KSLabel welcomeMsg = GWT.create(KSLabel.class);
    
    private final static String WELCOME_MSG = "Welcome to the Kuali Student Kitchen Sink \n\n" +
        "This is a catalog of all widgets developed by Kuali Student. \n\n"  +
         "Explore the menus on the left to view the widgets";

    boolean loaded = false;

    public KitchenSinkMain() {
        super.initWidget(main);
        initExamples();
    }

    protected void onLoad() {
        super.onLoad();
        if (!loaded) {
            loaded = true;
            
            welcomeMsg.init(WELCOME_MSG, true);
            welcomePanel.addStyleName(STYLE_WELCOME_PANEL);
            welcomePanel.add(welcomeMsg);
//            welcomePanel.add(new Image("images/kitchenSink.png"));
            
            main.setStyleName(STYLE_MAIN_PANEL);
            menuPanel.setStyleName(STYLE_MENU_PANEL);

            contentPanel.setStyleName(STYLE_CONTENT_PANEL);            
            contentPanel.add(welcomePanel);

            main.add(menuPanel);
            main.add(contentPanel);
            main.setCellWidth(menuPanel, "200px");

        }
    }

    private void initExamples() {
        List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
        
        KSMenuItemData gwtExamples = initGroup("GWT Examples");
        initExample(gwtExamples, new TestExample());
        initExample(gwtExamples, new LayoutExampleDescriptor());

        KSMenuItemData ksCommons = initGroup("KS Common Widgets");
//        initExample(ksCommons, new BusyWidgetShadeExampleDescriptor());
        initExample(ksCommons, new AccordionPanelExampleDescriptor());
        initExample(ksCommons, new BlockingProgressIndicatorExampleDescriptor());
        initExample(ksCommons, new ButtonExampleDescriptor());
        initExample(ksCommons, new CheckBoxExampleDescriptor());
        initExample(ksCommons, new DatePickerExampleDescriptor());
        initExample(ksCommons, new DisclosureSectionExampleDescriptor());
        initExample(ksCommons, new DropDownExampleDescriptor());
//        initExample(ksCommons, new HelpLinkExampleDescriptor());
        initExample(ksCommons, new ImageExampleDescriptor());
        initExample(ksCommons, new InfoPopupExampleDescriptor());
        initExample(ksCommons, new LabelExampleDescriptor());
        initExample(ksCommons, new ListBoxExampleDescriptor());
        initExample(ksCommons, new ModalPopupPanelExampleDescriptor());
        initExample(ksCommons, new RadioButtonExampleDescriptor());
        initExample(ksCommons, new RichEditorExampleDescriptor());
//        initExample(ksCommons, new StackPanelExampleDescriptor());
        initExample(ksCommons, new TextAreaExampleDescriptor());
        initExample(ksCommons, new TextBoxExampleDescriptor());
        
        items.add(gwtExamples);
        items.add(ksCommons);
        menuPanel.setItems(items);
        
    }

    private KSMenuItemData initGroup(String groupName) {
        return new KSMenuItemData(groupName);
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
    }




}
