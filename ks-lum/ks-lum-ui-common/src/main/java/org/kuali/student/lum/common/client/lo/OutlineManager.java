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

package org.kuali.student.lum.common.client.lo;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSImage;

import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class OutlineManager extends Composite {
	OutlineNodeModel outlineModel;


	VerticalPanel mainPanel = new VerticalPanel();

	public OutlineManager() {
		super.initWidget(mainPanel);
		mainPanel.setStyleName("KS-LOMainPanel");
	}

	public void setModel(OutlineNodeModel model) {
		outlineModel = model;
	}

	public void render() {
		mainPanel.clear();
		OutlineNode[] outlineNodes = outlineModel.toOutlineNodes();
		for (final OutlineNode aNode : outlineNodes) {
			NodePanel nodePanel = new NodePanel();
			nodePanel.setStyleName("KS-LONodePanel");
			nodePanel.setOutlineNode(aNode);

			mainPanel.add(nodePanel);
			showAllToolbar();
		}
	}
	public void closeAllToolbar(){
		for(int i=0;i< mainPanel.getWidgetCount();i++){
			if(mainPanel.getWidget(i) instanceof NodePanel){
				NodePanel p = (NodePanel)mainPanel.getWidget(i);
				p.hideToolbar();
			}
		}
	}
	public void showAllToolbar(){
		for(int i=0;i< mainPanel.getWidgetCount();i++){
			if(mainPanel.getWidget(i) instanceof NodePanel){
				NodePanel p = (NodePanel)mainPanel.getWidget(i);
				p.showToolbar();
			}
		}
	}
	class NodePanel extends  VerticalPanel{
		OutlineManagerToolbar toolbar = new OutlineManagerToolbar();
		HorizontalPanel emptySpacePanel = new HorizontalPanel();
		ArrayList<MouseMoveHandler> mouseMoveHandlerList = new ArrayList<MouseMoveHandler>();
		HorizontalPanel horitonalPanel = new HorizontalPanel();
		OutlineNode currentNode;
		NodePanel() {
			toolbar.setModel(outlineModel);
			horitonalPanel.setStyleName("KS-LOHNodePanel");
			super.sinkEvents(Event.ONMOUSEMOVE);
			super.sinkEvents(Event.ONMOUSEOUT);
			emptySpacePanel.setStyleName("KS-LOOutlineManagerToolbar");
			KSImage ieHack = Theme.INSTANCE.getCommonImages().getSpacer();
			emptySpacePanel.add(ieHack);
			super.insert(emptySpacePanel,0);
		}

		public void setOutlineNode(OutlineNode aNode) {
			currentNode = aNode;
			for (int i = 0; i < aNode.getIndentLevel(); i++) {
				Label label = new Label();
				label.setStyleName("KS-LONodeIndent");
				horitonalPanel.add(label);

				// space for toolbar
				label = new Label();
				label.setStyleName("KS-LONodeIndentToolbar");
				toolbar.insert(label, 0);
			}
			Widget userWidget = (Widget) aNode.getUserObject();
			userWidget.setStyleName("KS-LOaNode");
			horitonalPanel.add(userWidget);

			add(horitonalPanel);
		}

		public void addMouseMoveHandler(MouseMoveHandler handler) {
			mouseMoveHandlerList.add(handler);
		}

		public void setToolbar(OutlineManagerToolbar t) {
			toolbar = t;
		}
		public void showToolbar(){
			super.remove(emptySpacePanel);
			super.insert(toolbar, 0);
		}
		public void hideToolbar(){
			super.remove(toolbar);
			super.insert(emptySpacePanel,0);
		}
		public void onBrowserEvent(Event event) {
			switch (DOM.eventGetType(event)) {
			case Event.ONMOUSEMOVE: {
//				closeAllToolbar();


				outlineModel.setCurrentNode(currentNode);

//				showToolbar();
//				toolbar.updateButtonStates();
				break;
			}
			case Event.ONMOUSEOUT:
				break;
			}
			super.onBrowserEvent(event);
		}
	}
}