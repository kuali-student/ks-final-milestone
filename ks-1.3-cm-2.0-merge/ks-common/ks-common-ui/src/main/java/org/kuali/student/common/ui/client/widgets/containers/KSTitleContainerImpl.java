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

package org.kuali.student.common.ui.client.widgets.containers;

import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

@Deprecated
public class KSTitleContainerImpl extends Composite{
	private String title;
	private String status;
	private String linkText;
	private KSLabel titleLabel = new KSLabel();
	private KSLabel statusLabel = new KSLabel();
	private KSLabel linkLabel = new KSLabel();
	private SimplePanel parentContainer = new SimplePanel();
	private SimplePanel toolbar = new SimplePanel();
	private VerticalFlowPanel messagePanel = new VerticalFlowPanel();
	private SimplePanel content = new SimplePanel();
	private VerticalPanel layout = new VerticalPanel();
	private VerticalPanel rightContent = new VerticalPanel();
	private HorizontalPanel top = new HorizontalPanel();
	
	public KSTitleContainerImpl(){
		createLayout();
	}
	
	public KSTitleContainerImpl(String title, String status, String link){
		this.setTitle(title);
		this.setStatus(status);
		this.setLinkText(link);
		createLayout();
	}
	
	public KSTitleContainerImpl(String title, String status){
		this.setTitle(title);
		this.setStatus(status);
		createLayout();
	}
	
	public KSTitleContainerImpl(String title){
		this.setTitle(title);
		createLayout();
	}
	
	private void createLayout(){
		top.add(titleLabel);
		rightContent.add(statusLabel);
		rightContent.add(linkLabel);
		rightContent.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		rightContent.setCellHorizontalAlignment(linkLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		rightContent.setCellHorizontalAlignment(statusLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		top.add(rightContent);
		layout.add(top);
		layout.add(toolbar);
		layout.add(messagePanel);
		layout.add(content);
		linkLabel.addStyleName("KS-TitleContainer-Link");
		statusLabel.addStyleName("KS-TitleContainer-Status");
		titleLabel.addStyleName("KS-TitleContainer-Title");
		top.addStyleName("KS-TitleContainer-Top-Row");
		toolbar.addStyleName("KS-TitleContainer-Toolbar");
		messagePanel.addStyleName("KS-TitleContainer-Messages");
		rightContent.addStyleName("KS-TitleContainer-Right-Panel");
		layout.addStyleName("KS-TitleContainer-Layout");
		parentContainer.addStyleName("KS-Drop-Shadow");
		parentContainer.addStyleName("KS-TitleContainer");
		parentContainer.setWidget(layout);
		this.initWidget(parentContainer);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
		titleLabel.setText(title);
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
		statusLabel.setText(status);
	}
	
	public String getLinkText() {
		return linkText;
	}

	public void setLinkText(String linkText) {
		this.linkText = linkText;
		linkLabel.setText(linkText);
	}

	public void setContent(Widget contentWidget) {
		content.setWidget(contentWidget);		
	}
	
	public void setToolbar(Widget toolbarWidget) {
		toolbar.setWidget(toolbarWidget);
	}
	
	public void addMessage(String message){
		HorizontalBlockFlowPanel staticMessagePanel = new HorizontalBlockFlowPanel();
		staticMessagePanel.addStyleName("KS-Message-Static");
		staticMessagePanel.add(Theme.INSTANCE.getCommonImages().getWarningDiamondIcon());
		staticMessagePanel.add(new KSLabel(message));
		messagePanel.add(staticMessagePanel);		
	}
	
	public void clearMessages(){
		messagePanel.clear();
	}
}
