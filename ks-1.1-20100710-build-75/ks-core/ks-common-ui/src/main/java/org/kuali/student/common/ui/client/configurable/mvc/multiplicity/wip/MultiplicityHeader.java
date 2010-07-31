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

package org.kuali.student.common.ui.client.configurable.mvc.multiplicity.wip;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class MultiplicityHeader extends Composite{

	private FlowPanel header = new FlowPanel();
	private FlowPanel actions = new FlowPanel();
	private FlowPanel clearDiv = new FlowPanel();
	private SectionTitle title;
	private KSButton help;
	private KSButton delete = null;

    /**
     * 
     *      !!!!!! WORK IN PROGRESS  !!!!!!
     *     
     */
	public MultiplicityHeader(SectionTitle title, boolean updateable){
		this.title = title;
		header.add(title);

		help = new KSButton("?", ButtonStyle.HELP);
		actions.add(help);

		if(updateable){
			delete = new KSButton("X", ButtonStyle.DELETE);
			actions.add(delete);
		}

		actions.setStyleName("ks-form-header-title-actions");

		header.add(actions);

		clearDiv.setStyleName("clear");
		header.add(clearDiv);
		this.initWidget(header);
	}

	public void addDeleteHandler(ClickHandler handler){
		if(delete!=null){
			delete.addClickHandler(handler);
		}
	}

	public void addHelpHandler(ClickHandler handler){
		help.addClickHandler(handler);
	}

}