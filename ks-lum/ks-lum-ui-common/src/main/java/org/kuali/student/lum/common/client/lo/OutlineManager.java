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
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;

import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class OutlineManager extends VerticalSection implements HasValue<OutlineNodeModel> {
    OutlineNodeModel<?> outlineModel;
    String startOfPath;
    String endOfPath;
    String middleOfPath;

    public OutlineManager(String pathStart, String pathMiddle, String pathEnd) {
        startOfPath = pathStart;
        middleOfPath = pathMiddle;
        endOfPath = pathEnd;
	}

	public void render() {
        Map<Integer, Integer> levelIndexes = new HashMap<Integer, Integer>();
        int currIndent = 0;

        levelIndexes.put(0, -1); // first node increments the 0'th indent's index
        OutlineNode[] outlineNodes = outlineModel.toOutlineNodes();
        for (final OutlineNode aNode : outlineNodes) {
			NodePanel nodePanel = new NodePanel();
			nodePanel.setStyleName("KS-LONodePanel");
			nodePanel.setOutlineNode(aNode);
            if (aNode.getIndentLevel() > currIndent) {
                currIndent = aNode.getIndentLevel();
                levelIndexes.put(currIndent, 0);
            } else if (aNode.getIndentLevel() < currIndent) {
                currIndent = aNode.getIndentLevel();
                levelIndexes.put(currIndent, levelIndexes.get(currIndent) + 1);
            } else {
                levelIndexes.put(currIndent, levelIndexes.get(currIndent) + 1);
            }

            addField(getFieldKey(currIndent, levelIndexes), null, nodePanel, null);

			showAllToolbar();
		}
	}

    private String getFieldKey(int currIndent, Map<Integer, Integer> levelIndexes) {
        StringBuilder keyBuilder = new StringBuilder(startOfPath);

        keyBuilder.append("/").append(levelIndexes.get(0)).append("/");
        for (int idx = 1; idx <= currIndent; idx++) {
            keyBuilder.append(middleOfPath).append("/").append(levelIndexes.get(idx)).append("/");
        }
        keyBuilder.append(endOfPath);

        return keyBuilder.toString();
    }

    private FieldDescriptor addField(String fieldKey, MessageKeyInfo messageKey, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);

        FieldDescriptor fd = new FieldDescriptor(path.toString(), messageKey, null);
        if (widget != null) {
            fd.setFieldWidget(widget);
            fd.setHasHadFocus(true);
        }
        this.addField(fd);
        return fd;
    }

	public void closeAllToolbar(){
        for (FieldDescriptor fd : this.getFields()) {
            if (fd.getFieldWidget() instanceof NodePanel) {
                ((NodePanel) fd.getFieldWidget()).hideToolbar();
            }
        }
	}
	
	public void showAllToolbar(){
        for (FieldDescriptor fd : this.getFields()) {
            if (fd.getFieldWidget() instanceof NodePanel) {
                ((NodePanel) fd.getFieldWidget()).showToolbar();
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
			Image ieHack = Theme.INSTANCE.getCommonImages().getSpacer();
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
		@Override
        public void onBrowserEvent(Event event) {
			switch (DOM.eventGetType(event)) {
			case Event.ONMOUSEMOVE: {
                    outlineModel.setCurrentNode(currentNode);
				break;
			}
			case Event.ONMOUSEOUT:
				break;
			}
			super.onBrowserEvent(event);
		}

        @Override
        protected void onEnsureDebugId(String baseID) {
            super.onEnsureDebugId(baseID);
            toolbar.ensureDebugId(baseID);
            if (currentNode != null) {
                Object userObject = currentNode.getUserObject();
                if (userObject instanceof Widget) {
                    ((Widget) userObject).ensureDebugId(baseID);
                }
            }
        }
		
		
	}
	
	@Override
    public OutlineNodeModel getValue() {
		return outlineModel;
	}

	@Override
    public void setValue(OutlineNodeModel value) {
		outlineModel = value;		
	}

	@Override
    public void setValue(OutlineNodeModel value, boolean fireEvents) {
		setValue(value);
	}

	@Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<OutlineNodeModel> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}
}