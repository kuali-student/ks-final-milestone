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

import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class CollapsablePanel extends Composite{
	private KSButton label;
	private VerticalFlowPanel layout = new VerticalFlowPanel();
	private HorizontalBlockFlowPanel linkPanel = new HorizontalBlockFlowPanel();
	private SimplePanel content = new SimplePanel();
	private boolean isOpen;
	private boolean withImages;
	private String buttonLabel;
	
	//TODO add rotating triangle images
    KSImage closedImage = Theme.INSTANCE.getCommonImages().getDisclosureClosedIcon();
    KSImage openedImage = Theme.INSTANCE.getCommonImages().getDisclosureOpenedIcon();

	public CollapsablePanel(String name, Widget content, boolean isOpen, boolean withImages){
		init(name, content, isOpen, true);
		
	}

	public CollapsablePanel(String name, Widget content, boolean isOpen){
		init(name, content, isOpen, false);
	}

	private void init(String name, Widget content, boolean isOpen, boolean withImages) {
		this.isOpen = isOpen;
		this.withImages = withImages;
		this.buttonLabel = name;
		this.content.setWidget(content);
		label = new KSButton(name, ButtonStyle.DEFAULT_ANCHOR);
		linkPanel.add(label);
		if(!isOpen){
			this.content.setVisible(false);
        	if (this.withImages)
        		linkPanel.add(closedImage);
		}
		else {
        	if (this.withImages)
        		linkPanel.add(openedImage);
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
		
		layout.add(linkPanel);
		layout.add(this.content);
		closedImage.addStyleName("ks-image-middle-alignment");
		openedImage.addStyleName("ks-image-middle-alignment");
		this.initWidget(layout);
	}
	
	public KSButton getLabel() {
        return label;
    }

    public boolean isOpen(){
		return isOpen;
	}
	
	public void open(){
		content.setVisible(true);
		if (withImages) {
			linkPanel.remove(closedImage);
	    	linkPanel.add(openedImage);
		}
		isOpen = true;
	}
	
	public void close(){
		content.setVisible(false);
		if (withImages) {
    		linkPanel.remove(openedImage);
	    	linkPanel.add(closedImage);
		}
		isOpen = false;
	}
}
