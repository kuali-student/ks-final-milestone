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

import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.user.client.ui.Composite;

public class KSTitleDescPanel extends Composite{
	private VerticalFlowPanel layout = new VerticalFlowPanel();
	private KSLabel title = new KSLabel();
	private KSLabel desc = new KSLabel();

	public KSTitleDescPanel(String title, String desc){
		this.title.setText(title);
		this.desc.setText(desc);
		this.title.setStyleName("KS-Common-Title");
		this.desc.setStyleName("KS-Common-Desc");
		layout.add(this.title);
		layout.add(this.desc);
		this.initWidget(layout);
	}
	
	public KSTitleDescPanel(String title){
		this.title.setText(title);
		this.title.setStyleName("KS-Common-Title");
		this.desc.setStyleName("KS-Common-Desc");
		layout.add(this.title);
		layout.add(this.desc);
		this.desc.setVisible(false);
		this.initWidget(layout);
	}
	
	public KSTitleDescPanel(){
		layout.add(this.title);
		layout.add(this.desc);
		this.title.setStyleName("KS-Common-Title");
		this.desc.setStyleName("KS-Common-Desc");
		this.desc.setVisible(false);
		this.title.setVisible(false);
		this.initWidget(layout);
	}
	
	public void setTitleText(String title){
		this.title.setText(title);
		this.title.setVisible(true);
	}
	
	public void setDesc(String desc){
		this.desc.setText(desc);
		this.desc.setVisible(true);
	}

	public KSLabel getTitleWidget() {
		return title;
	}
	
	public KSLabel getDescWidget(){
		return desc;
	}
	
	
}
