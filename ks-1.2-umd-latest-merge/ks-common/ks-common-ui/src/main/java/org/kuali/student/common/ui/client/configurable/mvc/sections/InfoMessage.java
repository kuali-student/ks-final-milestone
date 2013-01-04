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

package org.kuali.student.common.ui.client.configurable.mvc.sections;

import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 * A widget used to show important information to the user often used at the top of sections.
 * 
 * @author Kuali Student Team
 *
 */
public class InfoMessage extends Composite{
	private HorizontalBlockFlowPanel layout = new HorizontalBlockFlowPanel();
	private Image icon = Theme.INSTANCE.getCommonImages().getWarningDiamondIcon();
	private HTMLPanel message = new HTMLPanel("");

	public InfoMessage(String text, boolean visible){
		layout.add(icon);
        message.getElement().setInnerHTML(text);
		layout.add(message);
		icon.addStyleName("ks-message-static-image");
		layout.addStyleName("ks-message-static");
		this.initWidget(layout);
		this.setVisible(visible);
	}


	public InfoMessage(){
		icon.addStyleName("ks-message-static-image");
		layout.add(icon);
		layout.add(message);
		this.initWidget(layout);
		this.setVisible(false);
	}
	
	public InfoMessage(boolean visible, boolean showStyling){
		icon.addStyleName("ks-message-static-image");
		layout.add(icon);
		layout.add(message);
		this.initWidget(layout);
		this.showWarnStyling(showStyling);
		this.setVisible(visible);
	}

	public void setMessage(String text, boolean messageVisible){
	    message.getElement().setInnerHTML(text);
		this.setVisible(messageVisible);
	}
	
	public void insert(Widget w, int beforeIndex){
		layout.insert(w, beforeIndex);
	}
	
	public void add(Widget w){
		layout.add(w);
	}
	
	public void showWarnStyling(boolean showStyling){
		icon.setVisible(showStyling);
		if(showStyling){
			layout.addStyleName("ks-message-static");
		}
		else{
			layout.removeStyleName("ks-message-static");
		}
	}
}
