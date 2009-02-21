package org.kuali.student.common.ui.client.widgets;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RadioButton;

public abstract class KSRadioButton extends Composite {

	public abstract void init(String name, String label, boolean asHTML);
	public abstract RadioButton getRadioButton();

}
