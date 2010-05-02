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

package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSHelpDialogAbstract;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class KSHelpDialogImpl extends KSHelpDialogAbstract{  
	
	private Frame webFrame = new Frame();
	private HorizontalPanel header = new HorizontalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private VerticalPanel main = new VerticalPanel();
	private KSButton ok = new KSButton("OK");
	private HelpInfo helpInfo;
	
/**
 * 
 * @deprecated ?
 *
 */
public KSHelpDialogImpl() {}
	
	public KSHelpDialogImpl(HelpInfo helpInfo) {
		init(helpInfo);
	}
	
	/**
	 * 
	 * @param helpInfo
	 */
	@Override
    protected void init(HelpInfo helpInfo) {
		this.helpInfo = helpInfo;
		
		
		this.setHeader(helpInfo.getTitle());
		
		main.add(header);
		
		webFrame.setUrl(helpInfo.getUrl());
		webFrame.setHeight((Window.getClientHeight() * .50) + "px");
		webFrame.setWidth((Window.getClientWidth() * .50) + "px");
		Window.addResizeHandler(new ResizeHandler(){

			public void onResize(ResizeEvent event) {
				webFrame.setHeight((Window.getClientHeight() * .50) + "px");
				webFrame.setWidth((Window.getClientWidth() * .50) + "px");
			}
		});
		
		//webFrame.addStyleName(KSStyles.KS_HELP_FRAME);
		main.add(webFrame);
		
		buttonPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		this.ok.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				KSHelpDialogImpl.this.hide();
				
			}
		});
		buttonPanel.add(this.ok);

		main.add(buttonPanel);
		
		setupDefaultStyles();
		this.setWidget(main);
	}
	
	private void setupDefaultStyles() {
		webFrame.addStyleName(KSStyles.KS_HELP_POPUP_FRAME);
	}
	
	
	
}
