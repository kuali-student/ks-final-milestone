package org.kuali.student.ui.kitchensink.client.kscommons;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSAccordionPanel;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSCollapsableFloatPanel;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSFloatPanel;
import org.kuali.student.common.ui.client.widgets.KSHelpLink;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSInfoPopupPanel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSListBox;
import org.kuali.student.common.ui.client.widgets.KSModalPopupPanel;
import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSStackPanel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.menus.KSAccordionMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

public class KSWidgetFactory {

    public static KSAccordionPanel getAccordionPanelInstance() {      
        return new KSAccordionPanel();
//        KSAccordionPanel w = GWT.create(KSAccordionPanel.class);
////      w.init();
//        return w;       
    }

    public static KSAccordionMenu getAccordionMenuInstance() {
        return new KSAccordionMenu();
//        KSAccordionMenu w = GWT.create(KSAccordionMenu.class);
////      w.init();
//        return w;       
    }

    public static KSMenuItemData getMenuItemDataInstance(String label) {        
        return new KSMenuItemData(label);
        //        KSMenuItemData w = GWT.create(KSMenuItemData.class);
//      w.setLabel(label);
//      w.init();  
    }

    public static KSButton getButtonInstance(String html,
            ClickHandler handler) {        
        return new KSButton(html, handler);     
    }

    public static KSCheckBox getCheckBoxInstance(String label) {       
        return new KSCheckBox(label);        
    }

    public static KSCollapsableFloatPanel getCollapsablePanelInstance(boolean expanded) {       
        KSCollapsableFloatPanel w = GWT.create(KSCollapsableFloatPanel.class);
        //TODO setExpanded causes error - not sure why yet?
//        w.setExpanded(expanded);
        return w;        
      }
    
    public static KSDatePicker getDatePickerInstance() {       
        KSDatePicker w = GWT.create(KSDatePicker.class);
//      w.init();
        return w;          
    }

    public static KSDisclosureSection getDisclosureSectionInstance(String headerText,
            Widget headerWidget,
            boolean isOpen) {       
        return new KSDisclosureSection(headerText, headerWidget, isOpen);
         
    }

    public static KSDropDown getDropDownInstance(boolean isMultipleSelect) {
        return new KSDropDown(isMultipleSelect);
//        KSDropDown w = GWT.create(KSDropDown.class);
//        w.init(isMultipleSelect);
//        return w;          
    }


    public static KSFloatPanel getFloatPanelInstance() {       
      KSFloatPanel w = GWT.create(KSFloatPanel.class);
//      w.init( );
      return w;        
    }

    public static KSHelpLink getHelpLinkInstance() {       
        return new KSHelpLink();       
    }
    
    public static KSImage getImageInstance(String url) {       
        return new KSImage(url);       
    }

    public static KSInfoPopupPanel getInfoPopupPanelInstance() {       
        KSInfoPopupPanel w = GWT.create(KSInfoPopupPanel.class);
//      w.init();
        return w;          
    }

    public static KSLabel getLabelInstance(String text,
            boolean wordWrap) {       
        KSLabel w = new KSLabel(text);
        w.setWordWrap(true);
        return w;          
    }

    public static KSListBox getListBoxInstance(List <String> list,
            boolean multipleSelect) {       
        KSListBox w = new KSListBox(list);
        w.setMultipleSelect(multipleSelect);
        return w;          
    }

    public static KSModalPopupPanel getModalPopupPanelInstance() {       
        KSModalPopupPanel w = GWT.create(KSModalPopupPanel.class);
//      w.init();
        return w;          
    }

    public static KSRadioButton getRadioButtonInstance(String name,
            String label,
            boolean asHTML) {       
        return new KSRadioButton(name, label, asHTML);
      
    }

    public static KSRichEditor getRichEditorInstance() {
        return new KSRichEditor();
        
    }

    public static KSStackPanel getStackPanelInstance() {       
        KSStackPanel w = GWT.create(KSStackPanel.class);
//      w.init();
        return w;          
    }

    public static KSTextArea getTextAreaInstance() {       
        return new KSTextArea();
        
    }


    public static KSTextBox getTextBoxInstance() {       
        return new KSTextBox();
       
    }
}
