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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ButtonEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.buttonlayout.ButtonRow;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.core.search.dto.SearchRequest;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.SimplePanel;

public class AdvancedSearchWindow {
	private KSLightBox dialog = new KSLightBox();
	private KSLabel titleBar = null;
	private List<Callback<List<SelectedResults>>> selectedCompleteCallbacks = new ArrayList<Callback<List<SelectedResults>>>();
	private List<Callback<Boolean>> actionCompletedCallbacks = new ArrayList<Callback<Boolean>>();
	private SearchPanel searchPanel;
	private VerticalFlowPanel layout = new VerticalFlowPanel();
	private SimplePanel buttons = new SimplePanel();

    enum ActionCancelEnum implements ButtonEnum {
        ACTION, CANCEL;
        @Override
        public String getText() {
            switch(this){
                case ACTION:
                    return "Action";
                case CANCEL:
                    return ConfirmCancelEnum.CANCEL.getText();
            }
            return null;
        }

		@Override
		public ButtonStyle getStyle() {
			return ButtonStyle.PRIMARY_SMALL;
		}
    }

    private static class ActionCancelGroup extends ButtonGroup<ActionCancelEnum> {
        public ActionCancelGroup(String actionLabel, Callback<ActionCancelEnum> callback) {
            layout = new ButtonRow();
            this.addCallback(callback);

            addButton(ActionCancelEnum.CANCEL);
            addButtonToSecondaryGroup(actionLabel, ActionCancelEnum.ACTION);

            this.initWidget(layout);
        }
        private void addButton(final ActionCancelEnum type){
            KSButton button = new KSButton(type.getText(), new ClickHandler(){

                @Override
                public void onClick(ClickEvent event) {
                    sendCallbacks(type);
                }
            });
            layout.addButton(button);
            buttonMap.put(type, button);
        }

        private void addButtonToSecondaryGroup(final String actionLabel,
                final ActionCancelEnum type){
            KSButton button = new KSButton(actionLabel, new ClickHandler(){

                @Override
                public void onClick(ClickEvent event) {
                    sendCallbacks(type);
                }
            });
            ((ButtonRow)layout).addButtonToSecondaryGroup(button);
            buttonMap.put(type, button);
        }
    }

    public List<SelectedResults> getSelectedValues() {
        return searchPanel.getSelectedValues();
    }

    public SearchRequest getSearchRequest() {
        return searchPanel.getSearchRequest();
    }
    
    public String getSelectedLookupName() {
        return searchPanel.getSelectedLookupName();
    }

    private ConfirmCancelGroup confirmCancelButtons = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){

        @Override
        public void exec(ConfirmCancelEnum result) {
            switch(result){
                case CONFIRM:
                    List<SelectedResults> selectedItems = searchPanel.getSelectedValues();
                    for(Callback<List<SelectedResults>> callback: selectedCompleteCallbacks){
                    	callback.exec(selectedItems);
                    }
                    dialog.hide();
                    break;
                case CANCEL:
                    dialog.hide();
                    break;
            }
        }
    });

	public AdvancedSearchWindow(String fieldMessageKey, SearchPanel panel){
	    layout.addStyleName("KS-Advanced-Search-Window");
		searchPanel = panel;
		titleBar = new KSLabel(fieldMessageKey);
		titleBar.addStyleName("KS-Advanced-Search-Header-Label");

		layout.add(titleBar);
		layout.add(panel);
		buttons.clear();
		buttons.setWidget(confirmCancelButtons);
		layout.add(buttons);
		dialog.setWidget(layout);
	}

	public void setActionButtonLabel(String actionLabel) {
	    if (actionLabel != null) {
	        ActionCancelGroup actionCancelGroup = new ActionCancelGroup(
	                actionLabel,
	                new Callback<ActionCancelEnum>(){

	                    @Override
	                    public void exec(ActionCancelEnum result) {
	                        switch(result){
	                            case ACTION:
	                                for(Callback<Boolean> callback: actionCompletedCallbacks){
	                                    callback.exec( Boolean.valueOf(true));
	                                }
	                                dialog.hide();
	                                break;
	                            case CANCEL:
	                                dialog.hide();
	                                break;
	                        }
	                    }
	                });
	        buttons.clear();
	        buttons.setWidget(actionCancelGroup);
	    } else {
	        buttons.clear();
	        buttons.setWidget(confirmCancelButtons);
	    }
	}

	public void addSelectionCompleteCallback(Callback<List<SelectedResults>> callback){
		selectedCompleteCallbacks.add(callback);
	}

    public void addActionCompleteCallback(Callback<Boolean> callback){
        actionCompletedCallbacks.add(callback);
    }

    public void show(){
		searchPanel.setupSearch();
		dialog.show();
	}

	public void hide(){
		dialog.hide();
	}
}
