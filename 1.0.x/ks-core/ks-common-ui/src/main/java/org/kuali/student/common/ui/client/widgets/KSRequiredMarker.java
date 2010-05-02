/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.configurable.mvc.RequiredEnum;
import org.kuali.student.common.ui.client.theme.Theme;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

public class KSRequiredMarker extends Composite{
	
	private SimplePanel content = new SimplePanel();
	//private KSLabel optionalLabel;
    private RequiredEnum required;
    KSImage asterisk = Theme.INSTANCE.getCommonImages().getAsterisk();
    
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
