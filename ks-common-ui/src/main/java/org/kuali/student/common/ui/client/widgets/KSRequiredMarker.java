package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.configurable.mvc.RequiredEnum;
import org.kuali.student.common.ui.client.images.KSImages;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

public class KSRequiredMarker extends Composite{
	
	private SimplePanel content = new SimplePanel();
	//private KSLabel optionalLabel;
    private RequiredEnum required;
    Image asterisk = KSImages.INSTANCE.asterisk().createImage();
    
    public KSRequiredMarker(RequiredEnum required){
    	this.required = required;
    	content.addStyleName(KSStyles.KS_REQUIRED_MARKER_PANEL);
        initWidget(content);
        setupLayout();
    }
    
    

	public RequiredEnum getRequired() {
		return required;
	}

	public void setRequired(RequiredEnum required) {
		this.required = required;
		setupLayout();
	}

	private void setupLayout(){
		switch(required){
			case REQUIRED:
				this.setVisible(true);
				//Image asterisk = createImage();
				asterisk.addStyleName(KSStyles.KS_REQUIRED_MARKER_ASTERISK);
				asterisk.setTitle(required.toString());
				content.setWidget(asterisk);
				break;
			case OPTIONAL:
				this.setVisible(true);
				KSLabel optionalLabel = new KSLabel(required.toString());
				optionalLabel.addStyleName(KSStyles.KS_REQUIRED_MARKER_OPTIONAL_TEXT);
				content.setWidget(optionalLabel);
				break;
			case NOT_MARKED:
				content.clear();
				this.setVisible(false);
				break;
		}
	}
	
/*	
	@Override
    protected void onLoad() {
        super.onLoad();
    }*/
	
}
