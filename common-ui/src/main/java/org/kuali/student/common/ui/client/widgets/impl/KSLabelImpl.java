package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.user.client.ui.Label;

public  class KSLabelImpl extends KSLabel{
	
	private Label label;
	


	public KSLabelImpl(){
		super();
	}
	
	@Override
	public void init(String text, boolean wordWrap){
		label = new Label(text, wordWrap);
		initWidget(label);
		setupDefaultStyle();
	}
	
	@Override
	public Label getLabel() {
		return label;
	}
	
	private void setupDefaultStyle(){
		addStyleName(KSStyles.KS_LABEL_STYLE);
		//cant think of why you would need a default hover style for labels
/*		this.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				KSLabelImpl.this.addStyleName(KSStyles.KS_LABEL_HOVER_STYLE);
				
			}		
		});
		
		this.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
				KSLabelImpl.this.removeStyleName(KSStyles.KS_LABEL_HOVER_STYLE);
				
			}
			
		});*/

	}
}
