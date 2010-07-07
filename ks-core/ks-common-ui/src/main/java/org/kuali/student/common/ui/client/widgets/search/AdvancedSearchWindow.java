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

import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ButtonEnum;
import org.kuali.student.common.ui.client.widgets.buttonlayout.ButtonRow;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ActionCancelGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.core.search.dto.SearchRequest;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.SimplePanel;

public class AdvancedSearchWindow {
    
    private VerticalFlowPanel layout = new VerticalFlowPanel();
	private KSLightBox dialog;
	private SearchPanel searchPanel;
		
//	private List<Callback<List<SelectedResults>>> selectedCompleteCallbacks = new ArrayList<Callback<List<SelectedResults>>>();
	private ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.SearchCancelEnum.SEARCH, ButtonEnumerations.SearchCancelEnum.CANCEL);   

    public List<SelectedResults> getSelectedValues() {
        return searchPanel.getSelectedValues();
    }

    public SearchRequest getSearchRequest() {
        return searchPanel.getSearchRequest();
    }
    
    public String getSelectedLookupName() {
        return searchPanel.getSelectedLookupName();
    }

	public AdvancedSearchWindow(String title, SearchPanel panel){
	    
	    actionCancelButtons.addStyleName("KS-Advanced-Search-Buttons");
	    addCancelCompleteCallback(null);
        searchPanel = panel;
        searchPanel.setActionCancelButtonGroup(actionCancelButtons);
        
	    dialog = new KSLightBox(title);	    
	    layout.addStyleName("KS-Advanced-Search-Window");
		layout.add(panel);
		layout.add(actionCancelButtons);
		//dialog.setWidget(layout);
	}
	
    public void show(){
        searchPanel.setupSearch();
        dialog.setWidget(layout);
        dialog.setSize(500, 500);
        dialog.show();
    }

    public void hide(){
        dialog.hide();
    }	

	public void setActionButtonLabel(String actionLabel) {

	    if (actionLabel != null) {
	        actionCancelButtons.getButton(ButtonEnumerations.SearchCancelEnum.SEARCH).setText(actionLabel);
	    }
	    
	    /*
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
	    } */
	} 

	/*
	public void addSelectionCompleteCallback(Callback<List<SelectedResults>> callback){
		selectedCompleteCallbacks.add(callback);
	}

	//allow user of KSPicker to react when user clicks on Action button (Search etc.)
    public void addActionCompleteCallback(final Callback<Boolean> callback){
        //actionCompletedCallbacks.add(callback);
        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnum result) {
                 if (result == ButtonEnumerations.SearchCancelEnum.SEARCH) {
                     callback.exec(true);         
                     
                     List<SelectedResults> selectedItems = searchPanel.getSelectedValues();
                     for(Callback<List<SelectedResults>> callback: selectedCompleteCallbacks){
                         callback.exec(selectedItems);
                     }  
                     
                 }
            }
        });
    } */
    
    public void addCancelCompleteCallback(final Callback<Boolean> callback){
        //actionCompletedCallbacks.add(callback);
        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnum result) {
                 if (result == ButtonEnumerations.SearchCancelEnum.CANCEL) {
                     if (callback != null) {
                         callback.exec(true);   
                     }
                     dialog.hide();
                 }
            }
        });
    }    
    
    public void addActionCompleteCallback(Callback<Boolean> callback){
        searchPanel.addActionCompleteCallback(callback);
    }
    
    //private org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup confirmCancelButtons = new org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){
    /*
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
            } */
        //});    
    
    /*
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
    } */

    /*
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
*/    
}
