package org.kuali.student.common.ui.client.widgets.headers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class KSHeader extends Composite {

	private static KSHeaderUiBinder uiBinder = GWT
			.create(KSHeaderUiBinder.class);

	interface KSHeaderUiBinder extends UiBinder<Widget, KSHeader> {
	}


	public KSHeader() {
		initWidget(uiBinder.createAndBindUi(this));

	}


}
