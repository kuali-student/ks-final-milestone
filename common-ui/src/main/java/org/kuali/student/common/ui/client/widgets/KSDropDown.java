package org.kuali.student.common.ui.client.widgets;


import org.kuali.student.common.ui.client.widgets.impl.KSDropDownImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;

public class KSDropDown extends KSDropDownAbstract{ 
    
    KSDropDownAbstract dropDown = GWT.create(KSDropDownImpl.class);
    /**
     * This constructs a KSDropDown that wraps an impl
     * 
     */    
	public KSDropDown(boolean isMultipleSelect){
	    dropDown.init(isMultipleSelect);
	    this.initWidget(dropDown);
	}

    public void addChangeHandler(ChangeHandler handler){
        dropDown.addChangeHandler(handler);
    }

    public void addItem(String item){
        dropDown.addItem(item);
    }

    public void removeItem(int index){
        dropDown.removeItem(index);
    }

    public void clear(){
        dropDown.clear();
    }

    public int getSelectedIndex(){
        return dropDown.getSelectedIndex();
    }

}
