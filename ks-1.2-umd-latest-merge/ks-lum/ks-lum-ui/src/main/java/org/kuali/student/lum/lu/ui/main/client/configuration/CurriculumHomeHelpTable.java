package org.kuali.student.lum.lu.ui.main.client.configuration;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CurriculumHomeHelpTable extends Composite{
	
	private static HelpTableUiBinder uiBinder = GWT.create(HelpTableUiBinder.class);

	interface HelpTableUiBinder extends UiBinder<Widget, CurriculumHomeHelpTable> {
	}
	  
	public CurriculumHomeHelpTable() {
	    initialize();
	}
	
    protected void initialize() {
        initWidget(uiBinder.createAndBindUi(this));
    }


}
