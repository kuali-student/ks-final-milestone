package org.kuali.student.common.ui.client.widgets;

import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

public abstract class KSListBox extends Composite{ 
	public abstract void init(List<String> list);

	public abstract ListBox getListBox();
}
