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

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ButtonEnum;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ActionCancelGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.core.search.dto.SearchRequest;

import java.util.List;

public class AdvancedSearchWindow {
    
    private VerticalFlowPanel layout = new VerticalFlowPanel();
	private KSLightBox dialog;
	private SearchPanel searchPanel;
		
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
        searchPanel.setupButtons();
        
	    dialog = new KSLightBox(title);	    
	    layout.addStyleName("KS-Advanced-Search-Window");
		layout.add(panel);
		layout.add(actionCancelButtons);
		//dialog.setWidget(layout);
		dialog.setMaxHeight(600);
		ButtonGroup buttons = panel.getButtons();
		buttons.removeFromParent();
		dialog.addButtonGroup(buttons);
	}
	
    public void show(){
        searchPanel.setupSearch();
        dialog.setWidget(layout);
        dialog.setMaxHeight(600);
        dialog.show();
    }

    public void hide(){
        dialog.hide();
    }
    
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
}
