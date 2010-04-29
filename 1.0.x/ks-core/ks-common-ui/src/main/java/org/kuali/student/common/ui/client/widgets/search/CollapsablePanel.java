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

package org.kuali.student.common.ui.client.widgets.search;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class CollapsablePanel extends Composite{
	private KSLinkButton label;
	private VerticalFlowPanel layout = new VerticalFlowPanel();
	private SimplePanel content = new SimplePanel();
	private boolean isOpen;
	
	//TODO add rotating triangle images
	
	public CollapsablePanel(String name, Widget content, boolean isOpen){
		label = new KSLinkButton(name, ButtonStyle.DEFAULT_ANCHOR);
		this.content.setWidget(content);
		this.isOpen = isOpen;
		if(!isOpen){
			this.content.setVisible(false);
		}
		
		label.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(CollapsablePanel.this.isOpen){
					CollapsablePanel.this.close();
				}
				else{
					CollapsablePanel.this.open();
				}				
			}
		});
		
		layout.add(label);
		layout.add(this.content);
		this.initWidget(layout);
	}
	
	public KSLinkButton getLabel() {
        return label;
    }

    public boolean isOpen(){
		return isOpen;
	}
	
	public void open(){
		content.setVisible(true);
		isOpen = true;
	}
	
	public void close(){
		content.setVisible(false);
		isOpen = false;
	}
}
