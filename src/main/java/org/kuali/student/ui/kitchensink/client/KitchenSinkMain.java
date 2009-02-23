package org.kuali.student.ui.kitchensink.client;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.*;

import org.kuali.student.common.ui.client.widgets.KSAccordionPanel;
import org.kuali.student.ui.kitchensink.client.gwtexamples.LayoutExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.gwtexamples.TestExample;
import org.kuali.student.ui.kitchensink.client.kscommons.accordionpanel.AccordionPanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.busywidgetshade.BusyWidgetShadeExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.button.ButtonExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.checkbox.CheckBoxExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.datepicker.DatePickerExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.disclosuresection.DisclosureSectionExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.dropdown.DropDownExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.infopopuppanel.InfoPopupExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.label.LabelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.listbox.ListBoxExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.modalpopuppanel.ModalPopupPanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.radiobutton.RadioButtonExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.richeditor.RichEditorExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.stackpanel.StackPanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.textarea.TextAreaExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.textbox.TextBoxExampleDescriptor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class KitchenSinkMain extends Composite {
    final HorizontalPanel main = new HorizontalPanel();
    final SimplePanel contentPanel = new SimplePanel(); // content panel
    final KSAccordionPanel menuPanel = GWT.create(KSAccordionPanel.class); // menu panel

    boolean loaded = false;

    public KitchenSinkMain() {
        super.initWidget(main);
    }

    protected void onLoad() {
        super.onLoad();
        if (!loaded) {
            loaded = true;
            
            main.setStyleName(STYLE_MAIN_PANEL);
            menuPanel.setStyleName(STYLE_MENU_PANEL);
            contentPanel.setStyleName(STYLE_CONTENT_PANEL);

            main.add(menuPanel);
            main.add(contentPanel);
            main.setCellWidth(menuPanel, "200px");

            initExamples();
        }
    }

    private void initExamples() {
        VerticalPanel gwtExamples = initGroup("GWT Examples");
        initExample(gwtExamples, new TestExample());
        initExample(gwtExamples, new LayoutExampleDescriptor());

        VerticalPanel ksCommons = initGroup("KS Common Widgets");
        initExample(ksCommons, new BusyWidgetShadeExampleDescriptor());
        initExample(ksCommons, new AccordionPanelExampleDescriptor());
        initExample(ksCommons, new ButtonExampleDescriptor());
        initExample(ksCommons, new CheckBoxExampleDescriptor());
        initExample(ksCommons, new DatePickerExampleDescriptor());
        initExample(ksCommons, new DisclosureSectionExampleDescriptor());
        initExample(ksCommons, new DropDownExampleDescriptor());
//        initExample(ksCommons, new HelpLinkExampleDescriptor());
        initExample(ksCommons, new InfoPopupExampleDescriptor());
        initExample(ksCommons, new LabelExampleDescriptor());
        initExample(ksCommons, new ListBoxExampleDescriptor());
        initExample(ksCommons, new ModalPopupPanelExampleDescriptor());
        initExample(ksCommons, new RadioButtonExampleDescriptor());
        initExample(ksCommons, new RichEditorExampleDescriptor());
        initExample(ksCommons, new StackPanelExampleDescriptor());
        initExample(ksCommons, new TextAreaExampleDescriptor());
        initExample(ksCommons, new TextBoxExampleDescriptor());
    }

    private VerticalPanel initGroup(String groupName) {
        VerticalPanel result = new VerticalPanel();
        menuPanel.addPanel(groupName, result);
        return result;
    }
    private void initExample(final VerticalPanel group, 
                             final KitchenSinkExample example) {
        Label label = new Label(example.getTitle());
        
        label.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent arg0) {
                if (contentPanel.getWidget() != null) {
                    contentPanel.remove(contentPanel.getWidget());
                }
                contentPanel.setWidget(example);                
            }
        });
        group.add(label);
    }




}
