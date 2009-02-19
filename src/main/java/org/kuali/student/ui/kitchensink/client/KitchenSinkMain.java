package org.kuali.student.ui.kitchensink.client;

import org.kuali.student.ui.kitchensink.client.gwtexamples.LayoutExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.gwtexamples.TestExample;
import org.kuali.student.ui.kitchensink.client.kscommons.accordionpanel.AccordionPanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.busywidgetshade.BusyWidgetShadeExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.button.ButtonExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.checkbox.CheckBoxExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.datepicker.DatePickerExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.disclosuresection.DisclosureSectionExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.dropdown.DropDownExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.help.HelpExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.helplink.HelpLinkExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.infopopuppanel.InfoPopupExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.label.LabelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.listbox.ListBoxExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.radiobutton.RadioButtonExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.richeditor.RichEditorExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.stackpanel.StackPanelExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.textarea.TextAreaExampleDescriptor;
import org.kuali.student.ui.kitchensink.client.kscommons.textbox.TextBoxExampleDescriptor;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class KitchenSinkMain extends Composite {
    final HorizontalPanel panel = new HorizontalPanel();
    final SimplePanel leftPanel = new SimplePanel();
    final SimplePanel rightPanel = new SimplePanel();
    final VerticalPanel listPanel = new VerticalPanel();
    
    boolean loaded = false;
    
    public KitchenSinkMain() {
        super.initWidget(panel);
    }
    
    protected void onLoad() {
        super.onLoad();
        if (!loaded) {
            loaded = true;
            panel.setHeight("100%");
            panel.setWidth("100%");
            
            leftPanel.setHeight("100%");
            leftPanel.setWidth("200px");
            leftPanel.setStyleName("ExampleListPanel");
            
            rightPanel.setHeight("100%");
            rightPanel.setStyleName("ExampleContentPanel");
            
            leftPanel.add(listPanel);
            panel.add(leftPanel);
            panel.add(rightPanel);
            panel.setCellWidth(leftPanel, "200px");
            
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
        initExample(ksCommons, new HelpExampleDescriptor());
//      initExample(ksCommons, new HelpLinkExampleDescriptor());
        initExample(ksCommons, new InfoPopupExampleDescriptor());
        initExample(ksCommons, new LabelExampleDescriptor());
        initExample(ksCommons, new ListBoxExampleDescriptor());
//        initExample(ksCommons, new ModalPopupPanelExampleDescriptor());
        initExample(ksCommons, new RadioButtonExampleDescriptor());
        initExample(ksCommons, new RichEditorExampleDescriptor());
        initExample(ksCommons, new StackPanelExampleDescriptor());
        initExample(ksCommons, new TextAreaExampleDescriptor());
        initExample(ksCommons, new TextBoxExampleDescriptor());
    }
    
    private VerticalPanel initGroup(String groupName) {
        DisclosurePanel panel = new DisclosurePanel(groupName);
        VerticalPanel result = new VerticalPanel();
        panel.add(result);
        listPanel.add(panel);
        return result;
    }
    private void initExample(final VerticalPanel group, final KitchenSinkExample example) {
        Label label = new Label(example.getTitle());
        label.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                if (rightPanel.getWidget() != null) {
                    rightPanel.remove(rightPanel.getWidget());
                }
                rightPanel.setWidget(example);
            }
        });
        group.add(label);
    }
    

    
    
}
