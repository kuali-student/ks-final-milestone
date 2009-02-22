package org.kuali.student.common.ui.client.widgets;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

public abstract class KSDropDown extends Composite{ 
	public abstract void init(boolean isMultipleSelect);

	public abstract ListBox getListBox();
}
