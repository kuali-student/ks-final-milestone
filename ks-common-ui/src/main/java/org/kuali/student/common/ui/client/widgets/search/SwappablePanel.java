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

import java.util.LinkedHashMap;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class SwappablePanel extends Composite{
	private VerticalFlowPanel panel = new VerticalFlowPanel();
	private ListBox panelSelector = new ListBox();
	private LinkedHashMap<String, Widget> panelMap = new LinkedHashMap<String, Widget>();
	private SimplePanel content = new SimplePanel();
	//FIXME: eventually we will need 'Search For' label as well. can the use of 'Search By' or 'Search For' be automatically
	// implied or do we need another lookup configuration parameter?
	private KSLabel searchForLabel = new KSLabel(Application.getApplicationContext().getMessage("swappablePanelSearchBy"));

	public SwappablePanel(LinkedHashMap<String, Widget> panelMap){
		this.panelMap = panelMap;
		drawPanel();
		panelSelector.addChangeHandler(new ChangeHandler(){

			@Override
			public void onChange(ChangeEvent event) {
				int i = panelSelector.getSelectedIndex();
				Widget p = SwappablePanel.this.panelMap.get(panelSelector.getItemText(i));
				content.setWidget(p);
			}
		});

		this.initWidget(panel);
	}

	private void drawPanel(){
	    panel.add(searchForLabel);
		panel.add(panelSelector);
		panel.add(content);

        boolean contentSet = false;
        for (String text : panelMap.keySet()) {
            panelSelector.addItem(text);
            if (!contentSet) {
                content.setWidget(panelMap.get(text));
                contentSet = true;
            }
        }
	}

}
