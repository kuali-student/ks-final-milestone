package org.kuali.student.lum.lu.ui.main.client.configuration;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

public class AcknowledgeView extends ViewComposite{
	private static AcknowledgeUiBinder uiBinder = GWT.create(AcknowledgeUiBinder.class);

	interface AcknowledgeUiBinder extends UiBinder<Widget, AcknowledgeView> {
	}
	  
	public AcknowledgeView(Controller controller, Enum<?> viewType) {
		super(controller, "Acknowledgements", viewType);
	    this.initWidget(uiBinder.createAndBindUi(this));
	}


}
