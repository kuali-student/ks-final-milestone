package org.kuali.student.common.ui.client.widgets.list;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSRadioButton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;


/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class KSRadioButtonList extends KSSelectItemWidget implements ClickHandler{
    private HorizontalPanel radioPanel = new HorizontalPanel();
    private String name;
    private String selectedValue = null;
    
	public KSRadioButtonList(String name) {
        this.initWidget(radioPanel);
        this.name = name;
	}
	

	/**
	 * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidget#deSelectItem(java.lang.String)
	 */
	public void deSelectItem(String id) {
        for (int i=0; i < radioPanel.getWidgetCount(); i++){
            KSRadioButton radioButton = (KSRadioButton)radioPanel.getWidget(i);
            if (radioButton.getFormValue().equals(id)){
                this.selectedValue = null;
                radioButton.setValue(false);
                break;
            }
        }		
	}

	/**
	 * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidget#getSelectedItems()
	 */
	public List<String> getSelectedItems() {
	    List<String> items = new ArrayList<String>();
	    if (selectedValue != null){
	        items.add(selectedValue);
	    }
	    return items;
	}


	
	/**
	 * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidget#selectItem(java.lang.String)
	 */
	public void selectItem(String id) {
	    deSelectItem(selectedValue);
	    for (int i=0; i < radioPanel.getWidgetCount(); i++){
	        KSRadioButton radioButton = (KSRadioButton)radioPanel.getWidget(i);
	        if (radioButton.getFormValue().equals(id)){
	            this.selectedValue = id;
	            radioButton.setValue(true);
	            break;
	        }
	    }
	}

    public void setListItems(ListItems listItems) {
        super.setListItems(listItems);
        
        radioPanel.clear();
        for (String id:listItems.getItemIds()){
            KSRadioButton radioButton = new KSRadioButton(name, listItems.getItemText(id));
            radioButton.setFormValue(id);
            radioButton.addClickHandler(this);
            radioPanel.add(radioButton);
        }        
    }
   
   public void onClick(ClickEvent event) {
       KSRadioButton radioButton = (KSRadioButton)(event.getSource());   
       if (radioButton.getValue() && !radioButton.getFormValue().equals(selectedValue)){
           selectedValue = radioButton.getFormValue();
           fireChangeEvent();
       }
   }
    
}
