package org.kuali.student.common.ui.client.widgets.list;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSRadioButton;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HorizontalPanel;


/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class RadioButtonList extends SelectItemWidget implements ClickHandler{
    private HorizontalPanel radioPanel = new HorizontalPanel();
    private String name;
    private String selectedValue = null;
    private List<ChangeHandler> changeHandlers = new ArrayList<ChangeHandler>();
    
	public RadioButtonList(String name) {
        this.initWidget(radioPanel);
        this.name = name;
	}
	

	/**
	 * @see org.kuali.student.common.ui.client.widgets.list.SelectItemWidget#deSelectItem(java.lang.String)
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
	 * @see org.kuali.student.common.ui.client.widgets.list.SelectItemWidget#getSelectedItems()
	 */
	public List<String> getSelectedItems() {
	    List<String> items = new ArrayList<String>();
	    if (selectedValue != null){
	        items.add(selectedValue);
	    }
	    return items;
	}


	
	/**
	 * @see org.kuali.student.common.ui.client.widgets.list.SelectItemWidget#selectItem(java.lang.String)
	 */
	public void selectItem(String id) {
	    deSelectItem(id);
	    
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
       KSRadioButton b = (KSRadioButton)(event.getSource());
            
        if (b.getValue() && !b.getFormValue().equals(selectedValue)){
            selectedValue = b.getFormValue();
            fireChangeEvent();
        }
   }
    
}
