package org.kuali.student.common.ui.client.widgets.list.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;

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
public class KSRadioButtonListImpl extends KSSelectItemWidgetAbstract implements ClickHandler{
    private HorizontalPanel radioPanel = new HorizontalPanel();
    private String name;
    private String selectedValue = null;
    
	public KSRadioButtonListImpl() {
        initWidget(radioPanel);
	}
	
    @Override
    protected void init(String name) {
        this.name = name;
    }
	/**
	 * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#deSelectItem(java.lang.String)
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
	 * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getSelectedItems()
	 */
	public List<String> getSelectedItems() {
	    List<String> items = new ArrayList<String>();
	    if (selectedValue != null){
	        items.add(selectedValue);
	    }
	    return items;
	}


	
	/**
	 * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#selectItem(java.lang.String)
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
   
   @Override
   public void onClick(ClickEvent event) {
       KSRadioButton radioButton = (KSRadioButton)(event.getSource());   
       if (radioButton.getValue() && !radioButton.getFormValue().equals(selectedValue)){
           selectedValue = radioButton.getFormValue();
           fireChangeEvent();
       }
   }

public void setMultipleSelect(boolean isMultipleSelect) {}

public void onLoad() {}
    
}
