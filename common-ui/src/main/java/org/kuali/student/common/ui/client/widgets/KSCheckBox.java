package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;

public abstract class KSCheckBox extends Composite{
	
	/**
	 * Sets label
	 * @param label
	 */
	public abstract void init(String label);

	public abstract CheckBox getCheckBox();
}
