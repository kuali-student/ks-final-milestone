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

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;

import com.google.gwt.user.client.ui.Composite;

public class ValidationMessagePanel extends Composite{
	
	private KSListPanel listPanel = new KSListPanel();
	private int count = 0;
	private boolean topMargin = true;
	
	public ValidationMessagePanel(){
		this.initWidget(listPanel);
		
	}
	
	public ValidationMessagePanel(boolean topMargin){
		this.initWidget(listPanel);
		this.topMargin = topMargin;
	}
	
	public void addMessage(KSLabel message){
		if(count == 0 && topMargin){
			message.addStyleName("ks-form-module-single-line-margin");
		}
		listPanel.add(message);
		count++;
	}
	
	public void clear(){
		listPanel.clear();
		count = 0;
	}
	
	public int getMessageCount(){
		return count;
	}
}
