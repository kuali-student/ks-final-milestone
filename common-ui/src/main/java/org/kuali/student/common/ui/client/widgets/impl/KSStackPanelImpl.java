package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.widgets.KSStackPanel;

import com.google.gwt.user.client.ui.StackPanel;

public class KSStackPanelImpl extends KSStackPanel{ 

	private StackPanel stackPanel;
	
	public KSStackPanelImpl() {
		super();
		StackPanel stackPanel = new StackPanel();
		initWidget(stackPanel);
	}

	@Override
	public StackPanel getStackPanel() {
		return stackPanel;
	}
	
}
