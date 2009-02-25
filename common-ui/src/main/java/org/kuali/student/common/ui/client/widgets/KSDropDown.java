package org.kuali.student.common.ui.client.widgets;


import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;

public abstract class KSDropDown extends Composite{ 
	public abstract void init(boolean isMultipleSelect);

    public abstract void addChangeHandler(ChangeHandler handler);

    public abstract void addItem(String item);

    public abstract void removeItem(int index);

    public abstract void clear();

    public abstract int getSelectedIndex();

}
