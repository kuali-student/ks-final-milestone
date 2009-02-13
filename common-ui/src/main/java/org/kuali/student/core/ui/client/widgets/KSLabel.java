package org.kuali.student.core.ui.client.widgets;

import com.google.gwt.user.client.ui.Label;

public class KSLabel extends Label{
	public KSLabel(){
		super();
	}
	
	public KSLabel(String text){
		super(text);
	}
	
	public KSLabel(String text, boolean wordWrap){
		super(text, wordWrap);
	}
	
	private void setupDefaultStyle(){
		addStyleName(KSStyles.KS_LABEL_STYLE);
		//cant think of why you would need a default hover style for labels
/*		this.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				KSLabel.this.addStyleName(KSStyles.KS_LABEL_HOVER_STYLE);
				
			}		
		});
		
		this.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
				KSLabel.this.removeStyleName(KSStyles.KS_LABEL_HOVER_STYLE);
				
			}
			
		});*/

	}
}
