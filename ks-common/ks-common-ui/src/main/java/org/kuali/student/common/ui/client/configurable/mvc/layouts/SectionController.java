package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import org.kuali.student.common.ui.client.mvc.View;

public abstract class SectionController extends ViewLayout{

	public SectionController(String controllerId) {
		super(controllerId);
	}
	
	
	@Override
	public void addView(View view){
		super.addView(view);
	}
	

	@Override
	protected void hideView(View view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void renderView(View view) {
		// TODO Auto-generated method stub
		
	}
	
}
