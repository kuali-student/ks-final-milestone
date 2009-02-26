package org.kuali.student.ui.kitchensink.client.kscommons;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSAccordionPanel;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
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
        KSAccordionPanel w = GWT.create(KSAccordionPanel.class);
//      w.init();
        return w;       
    }

    public static KSAccordionMenu getAccordionMenuInstance() {        
        KSAccordionMenu w = GWT.create(KSAccordionMenu.class);
//      w.init();
        return w;       
    }

    public static KSMenuItemData getMenuItemDataInstance(String label) {        
        KSMenuItemData w = new KSMenuItemData(label);
        //        KSMenuItemData w = GWT.create(KSMenuItemData.class);
//      w.setLabel(label);
//      w.init();
        return w;       
    }

    public static KSButton getButtonInstance(String html,
            ClickHandler handler) {        
        KSButton w = new KSButton(html, handler);
        return w;       
    }

    public static KSCheckBox getCheckBoxInstance(String label) {       
        KSCheckBox w = new KSCheckBox(label);
        return w;          
    }

    public static KSDatePicker getDatePickerInstance() {       
        KSDatePicker w = GWT.create(KSDatePicker.class);
//      w.init();
        return w;          
    }
/*
    public static KSDisclosureSection getDisclosureSectionInstance(String headerText,
            Widget headerWidget,
            boolean isOpen) {       
        KSDisclosureSection w = GWT.create(KSDisclosureSection.class);
        w.init(headerText, headerWidget, isOpen);
        return w;          
    }
*/
    public static KSDropDown getDropDownInstance(boolean isMultipleSelect) {       
        KSDropDown w = GWT.create(KSDropDown.class);
        w.init(isMultipleSelect);
        return w;          
    }

    /**
     * @deprecated
     * 
     * @param url
     * @return
     */
    public static KSImage getImageInstance(String url) {       
        KSImage w = new KSImage(url);
        return w;          
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
        KSRadioButton w = new KSRadioButton(name, label, asHTML);
        return w;          
    }

    public static KSRichEditor getRichEditorInstance() {       
        KSRichEditor w = null;

        try {
//          w = new KSRichEditor();
            w = GWT.create(KSRichEditor.class);
            //      w.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return w;          
    }

    public static KSStackPanel getStackPanelInstance() {       
        KSStackPanel w = GWT.create(KSStackPanel.class);
//      w.init();
        return w;          
    }

    public static KSTextArea getTextAreaInstance() {       
        KSTextArea w = GWT.create(KSTextArea.class);
//      w.init();
        return w;          
    }


    public static KSTextBox getTextBoxInstance() {       
        KSTextBox w = GWT.create(KSTextBox.class);
//      w.init();
        return w;          
    }
}
