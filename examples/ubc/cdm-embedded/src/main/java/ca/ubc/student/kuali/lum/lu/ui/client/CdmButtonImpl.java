package ca.ubc.student.kuali.lum.lu.ui.client;

import org.kuali.student.common.ui.client.widgets.impl.KSButtonImpl;

public class CdmButtonImpl extends KSButtonImpl {

	public void init(String text, ButtonStyle style) {
		super.init(text + " - CDM", style);		
	}
}
