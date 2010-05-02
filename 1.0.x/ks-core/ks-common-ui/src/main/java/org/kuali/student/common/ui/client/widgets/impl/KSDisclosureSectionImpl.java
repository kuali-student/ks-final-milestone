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

import java.util.Iterator;

import org.kuali.student.common.ui.client.widgets.KSDisclosureSectionAbstract;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class KSDisclosureSectionImpl extends KSDisclosureSectionAbstract{
	
	private DisclosurePanel panel; 
	private FlowPanel content = new FlowPanel();
	
	public KSDisclosureSectionImpl(){
	}
	
	@Override
    protected void init(String headerText, Widget headerWidget, boolean isOpen) {
	    if(headerText != null) {
	    	panel = new DisclosurePanel(headerText, isOpen);
		} else if (headerWidget != null){
			panel = new DisclosurePanel(headerWidget,isOpen);
		} else {
			panel = new DisclosurePanel();
		}
		this.initWidget(panel);
		panel.setContent(content);
		setupDefaultStyle(isOpen);
	}
	private void setupDefaultStyle(boolean isOpen) {
		panel.getHeader().addStyleName(KSStyles.KS_DISCLOSURE_HEADER_STYLE);
		panel.addStyleName(KSStyles.KS_DISCLOSURE_SECTION_PANEL);
		content.addStyleName(KSStyles.KS_DISCLOSURE_CONTENTPANEL_STYLE);
		
		if(isOpen) {
            panel.getHeader().addStyleName(KSStyles.KS_DISCLOSURE_OPEN_HEADER_STYLE);
            panel.addStyleName(KSStyles.KS_DISCLOSURE_OPEN_SECTION_PANEL);
            content.addStyleName(KSStyles.KS_DISCLOSURE_OPEN_CONTENTPANEL_STYLE);
		}
		
		panel.addCloseHandler(new CloseHandler<DisclosurePanel>(){

			public void onClose(CloseEvent<DisclosurePanel> event) {
				panel.getHeader().removeStyleName(KSStyles.KS_DISCLOSURE_OPEN_HEADER_STYLE);
				panel.removeStyleName(KSStyles.KS_DISCLOSURE_OPEN_SECTION_PANEL);
				content.removeStyleName(KSStyles.KS_DISCLOSURE_OPEN_CONTENTPANEL_STYLE);
			}
			
		});
		
		panel.addOpenHandler(new OpenHandler<DisclosurePanel>(){

			public void onOpen(OpenEvent<DisclosurePanel> event) {
				panel.getHeader().addStyleName(KSStyles.KS_DISCLOSURE_OPEN_HEADER_STYLE);
				panel.addStyleName(KSStyles.KS_DISCLOSURE_OPEN_SECTION_PANEL);
				content.addStyleName(KSStyles.KS_DISCLOSURE_OPEN_CONTENTPANEL_STYLE);
			}
			
		});
		
		
	}
	
	@Override
    public void add(Widget w){
		content.add(w);
	}

	public Widget getContent() {
		return content;
	}

	public Iterator<Widget> iterator() {
		return content.iterator();
	}

	@Override
    public boolean remove(Widget w) {
		return content.remove(w);
	}
	
	@Override
    public void clear() {
		content.clear();
	}
	
	
	public HandlerRegistration addCloseHandler(
			CloseHandler<DisclosurePanel> handler) {
		return panel.addCloseHandler(handler);
	}

	public HandlerRegistration addOpenHandler(
			OpenHandler<DisclosurePanel> handler) {
		return panel.addOpenHandler(handler);
	}

	public void addStyleDependentName(String styleSuffix) {
		panel.addStyleDependentName(styleSuffix);
	}

	public void addStyleName(String style) {
		panel.addStyleName(style);
	}

	public Widget getHeader() {
		return panel.getHeader();
	}

	public HasText getHeaderTextAccessor() {
		return panel.getHeaderTextAccessor();
	}

	public String getTitle() {
		return panel.getTitle();
	}

	@Override
    public boolean isAnimationEnabled() {
		return panel.isAnimationEnabled();
	}

	@Override
    public boolean isOpen() {
		return panel.isOpen();
	}

	@Override
    public boolean isVisible() {
		return panel.isVisible();
	}

	public void removeStyleDependentName(String styleSuffix) {
		panel.removeStyleDependentName(styleSuffix);
	}

	public void removeStyleName(String style) {
		panel.removeStyleName(style);
	}

	public void setAnimationEnabled(boolean enable) {
		panel.setAnimationEnabled(enable);
	}

	public void setHeader(Widget headerWidget) {
		panel.setHeader(headerWidget);
	}

	@Override
    public void setOpen(boolean isOpen) {
		panel.setOpen(isOpen);
	}

	public void setStyleName(String style) {
		panel.setStyleName(style);
	}

	public void setStylePrimaryName(String style) {
		panel.setStylePrimaryName(style);
	}

	public void setTitle(String title) {
		panel.setTitle(title);
	}

	@Override
    public void setVisible(boolean visible) {
		panel.setVisible(visible);
	}

	

}
