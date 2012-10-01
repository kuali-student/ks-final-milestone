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
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * The validation message panel used for field elements, adds validation errors to a list and styles
 * them appropriately.
 * 
 * @author Kuali Student Team
 *
 */
public class ValidationMessagePanel extends Composite{
	
	private VerticalFlowPanel container = new VerticalFlowPanel();
	private KSListPanel errorListPanel = new KSListPanel();
	private KSListPanel warnListPanel = new KSListPanel();
	private int warnCount = 0;
	private int errorCount = 0;
	private boolean topMargin = true;
	
	public ValidationMessagePanel(){
		this.initWidget(container);
		container.add(errorListPanel);
		container.add(warnListPanel);
		errorListPanel.addStyleName("ks-form-module-validation-errors");
		warnListPanel.addStyleName("ks-form-module-validation-warnings");		
	}
	
	public ValidationMessagePanel(boolean topMargin){
		this.initWidget(errorListPanel);
		this.topMargin = topMargin;
	}
	
	public void addErrorMessage(KSLabel message){
		if(getMessageCount() == 0 && topMargin){
			message.addStyleName("ks-form-module-single-line-margin");
		}		
		errorListPanel.add(message);
		errorCount++;
	}
	
	public void addWarnMessage(Widget message){
		if(getMessageCount() == 0 && topMargin){
			message.addStyleName("ks-form-module-single-line-margin");
		}
		warnListPanel.add(message);
		warnCount++;		
	}
	
	public boolean hasWarnings(){
		return (warnCount > 0);
	}
	
	public void clearErrors(){
		errorListPanel.clear();
		errorCount = 0;
	}
	
	public void clearWarnings(){
		warnListPanel.clear();
		warnCount = 0;
	}
	
	public int getMessageCount(){
		return errorCount + warnCount;
	}
}
