package org.kuali.student.ui.personidentity.client.view.lu;

import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LuSearchDetailsWidget extends FlexTable {

	final protected Hyperlink 	header	= new Hyperlink();
	final protected Label		shortDesc	= new Label();
	
	protected		GwtLuiInfo	lui;
	
	ClickListener setCurrLui = new ClickListener(){		
		public void onClick(Widget sender) {			
			LearningUnitController.setCurrentLui(lui.getLuiId());		
			LearningUnitController.displayCourseDetails();
		}};
	
	public LuSearchDetailsWidget(GwtLuiInfo lui) {
		this.lui = lui;
		this.arrangeWidget();
		this.populateWidget(lui);
		shortDesc.addStyleName("KS-Label");
	}
	
	
	protected void arrangeWidget(){
		header.addClickListener(setCurrLui);
		setWidget(0,0, header);
		setWidget(1,0, shortDesc);
	}

	public void populateWidget(GwtLuiInfo lui){
		//header.setHTML(lui.getCluDisplay().getCluShortName());
		header.setText(lui.getCluDisplay().getCluShortName());
		// This should be updated to a description.
		shortDesc.setText(lui.getCluInfo().getDescription());
	}
}
