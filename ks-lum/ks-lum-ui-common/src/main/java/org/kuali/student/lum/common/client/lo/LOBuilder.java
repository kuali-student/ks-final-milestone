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
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.core.assembly.data.Metadata;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This class manages the users interactions when building/updating Learning
 * Objectives within the context of managing CLUs. It allows the user to type in
 * LO text directly or execute a search and select one or more of the returned
 * LOs.
 * 
 * Users can then re-organize LOs on the screen including altering the sequence
 * and creating sub LOs
 * 
 * @author Kuali Student Team
 * 
 */
public class LOBuilder extends Composite implements	HasValue<List<OutlineNode<LOPicker>>> {

	private static String type;
	private static String state;
	private static String repoKey;
	private static String messageGroup;

	VerticalPanel main = new VerticalPanel();
	HorizontalPanel searchMainPanel = new HorizontalPanel();
	KSPicker searchWindow;
	VerticalPanel loPanel = new VerticalPanel();

	LearningObjectiveList loList;
	KSLabel instructions;

	protected LOBuilder() {
	}

	public LOBuilder(String luType, String luState, String luGroup,	String loRepoKey, final Metadata metadata) {
		super();
		initWidget(main);

		type = luType;
		state = luState;
		repoKey = loRepoKey;
		messageGroup = luGroup;

		// searchLink = new KSLabel(getLabel(LUConstants.LO_SEARCH_LINK_KEY));
		// picker needs to handle labels like this
		/*
		 * TODO - KSLUM-527: temporally comment out link to LO Search for M9.
		 * Need to be fixed later.
		 */
		/*
		 * if(metadata.getInitialLookup() != null){ searchWindow = new
		 * KSPicker(metadata.getInitialLookup(),
		 * metadata.getAdditionalLookups());
		 * searchWindow.addValuesChangeHandler(new
		 * ValueChangeHandler<List<String>>(){ public void
		 * onValueChange(ValueChangeEvent<List<String>> event) { List<String>
		 * selection = (List<String>)event.getValue();
		 * loList.addSelectedLOs(selection); } });
		 * searchMainPanel.add(searchWindow); }
		 */

		// adding search icon - should this be part of search link? coordinate
		// with UX
		// searchImage.addClickHandler(searchClickHandler);
		// Image searchImage = Theme.INSTANCE.getCommonImages().getSearchIcon();
		// searchLinkPanel.add(searchImage);
		instructions = new KSLabel(getLabel(LUConstants.LO_INSTRUCTIONS_KEY));

		loList = new LearningObjectiveList();
		loPanel.add(loList);

		// searchImage.addStyleName("KS-LOBuilder-Search-Image");
		searchMainPanel.addStyleName("KS-LOBuilder-Search-Panel");
		loPanel.addStyleName("KS-LOBuilder-LO-Panel");
		instructions.addStyleName("KS-LOBuilder-Instructions");

		main.add(searchMainPanel);
		main.add(instructions);
		main.add(loPanel);
	}

	/**
	 * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object,
	 *      boolean)
	 */
	@Override
	public void setValue(List<OutlineNode<LOPicker>> value, boolean fireEvents) {
		setValue(value);
	}

	/**
	 * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(List<OutlineNode<LOPicker>> data) {
		loList.setValue(data);
	}

	/**
	 * @see com.google.gwt.user.client.ui.HasValue#getValue()
	 */
	@Override
	public List<OutlineNode<LOPicker>> getValue() {
		return loList.getValue();
	}

	/**
	 * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
	 */
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<OutlineNode<LOPicker>>> handler) {
		return loList.addValueChangeHandler(handler);
	}

	private static String getLabel(String labelKey) {
		return Application.getApplicationContext().getUILabel(messageGroup,
				type, state, labelKey);
	}

	/**
	 * @return the type
	 */
	public static String getType() {
		return type;
	}

	/**
	 * @return the state
	 */
	public static String getState() {
		return state;
	}

	public static String getRepoKey() {
		return repoKey;
	}

	/**
	 * @return the messageGroup
	 */
	public static String getMessageGroup() {
		return messageGroup;
	}

	public static class LearningObjectiveList extends Composite implements HasValue<List<OutlineNode<LOPicker>>> {
		OutlineNodeModel<LOPicker> outlineModel = new OutlineNodeModel<LOPicker>();
		OutlineManager outlineComposite = new OutlineManager();
		VerticalPanel mainPanel = new VerticalPanel();
		
		SelectionChangeHandler loPickerChangeHandler = new SelectionChangeHandler(){
			public void onSelectionChange(SelectionChangeEvent event) {
				fireChangeEvent();
			}			
		};
		
		public LearningObjectiveList() {
			mainPanel.add(outlineComposite);
			KSButton addnew = new KSButton("Add item", new ClickHandler() {
				public void onClick(ClickEvent event) {
					setValue(getValue());
					appendLO("");
					reDraw();
				}
			});
			
			addnew.addStyleName("KS-LOBuilder-New");
			mainPanel.add(addnew);
			super.initWidget(mainPanel);

			outlineComposite.setValue(outlineModel);
			outlineModel.addChangeHandler(new ChangeHandler() {
				public void onChange(ChangeEvent event) {
					outlineComposite.render();
					fireChangeEvent();
				}
			});

			initEmptyLoList();
		}

		protected void initEmptyLoList(){
			List<String> list = new ArrayList<String>();
			list.add("");
			list.add("");
			list.add("");
			list.add("");
			list.add("");
			addSelectedLOs(list);			
		}
		
		protected void fireChangeEvent(){
			ValueChangeEvent.fire(this, outlineModel.getOutlineNodes());
		}
		
		public List<OutlineNode<LOPicker>> getValue() {
			return outlineModel.getOutlineNodes();
		}

		public void setValue(List<OutlineNode<LOPicker>> value) {
			outlineModel.clearNodes();
			outlineModel.getOutlineNodes().addAll(value);

			if (value == null || value.isEmpty()){
				initEmptyLoList();
			} else {
				//Add selection change handler to LOPickers if not set
				for (OutlineNode<LOPicker> node:value){
					LOPicker picker = node.getUserObject();
					if (!picker.hasChangeHandler()){
						picker.addSelectionChangeHandler(loPickerChangeHandler);
					}
				}
				
				reDraw();
			}
		}

		private void appendLO(String loValue) {
			OutlineNode<LOPicker> aNode = new OutlineNode<LOPicker>();
			LOPicker newPicker = new LOPicker(messageGroup, type, state,
					repoKey);

			newPicker.addSelectionChangeHandler(loPickerChangeHandler);
			newPicker.setLOText(loValue);
			aNode.setUserObject(newPicker);
			aNode.setModel(outlineModel);

			outlineModel.addOutlineNode(aNode);
		}

		// add one or more description by going through existing LO box and
		// populating the empty ones
		// if not enough empty LO boxes then add new ones
		public void addSelectedLOs(List<String> loDescription) {

			List<OutlineNode<LOPicker>> existingLOs = outlineModel
					.getOutlineNodes();

			int ix = existingLOs.size();
			for (String strValue : loDescription) {

				boolean foundEmptyBox = false;
				while (ix > 0) {
					ix--;
					if (existingLOs.get(ix).getUserObject().getLOText().trim()
							.length() == 0) {
						existingLOs.get(ix).getUserObject().setLOText(strValue);
						foundEmptyBox = true;
						break;
					}
				}

				// we didn't find empty LO box so add a new one
				if (foundEmptyBox == false) {
					appendLO(strValue);
				}
			}
			reDraw();
		}

		private void reDraw() {
			outlineComposite.render();
		}

		public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<OutlineNode<LOPicker>>> handler) {
			return addHandler(handler, ValueChangeEvent.getType());
		}
		
		public SelectionChangeHandler getChangeHandlerForLOPicker(){
			return loPickerChangeHandler;
		}

		@Override
		public void setValue(List<OutlineNode<LOPicker>> value,
				boolean fireEvents) {
			setValue(value);
		}
	}
}
