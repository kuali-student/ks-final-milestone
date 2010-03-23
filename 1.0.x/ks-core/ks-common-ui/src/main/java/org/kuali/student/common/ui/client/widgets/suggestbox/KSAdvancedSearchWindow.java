/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.client.widgets.suggestbox;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSThinTitleBar;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.VerticalPanel;

public class KSAdvancedSearchWindow implements HasSelectionHandlers<List<String>>{
    private KSLightBox dialog = new KSLightBox();
    private KSThinTitleBar titleBar = null;
    private VerticalPanel mainPanel = new VerticalPanel();
    private KSAdvancedSearchRpc advancedSearch;
    private HandlerManager handlers = new HandlerManager(this);
    
    private ConfirmCancelGroup buttonPanel = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){

        @Override
        public void exec(ConfirmCancelEnum result) {
            switch(result){
                case CONFIRM:
                    List<String> selectedItems = advancedSearch.getSelectedIds();
                    if (selectedItems != null && selectedItems.size() > 0){
                        fireSelectEvent(selectedItems);
                    }
                    dialog.hide();
                    break;
                case CANCEL:
                    dialog.hide();
                    break;
            }
        }
    });
    
    public KSAdvancedSearchWindow(BaseRpcServiceAsync searchService, String searchTypeKey, String resultIdKey){
        init(searchService, searchTypeKey, resultIdKey);       
    }

    public KSAdvancedSearchWindow(BaseRpcServiceAsync searchService, String searchTypeKey, String resultIdKey, String title){
        init(searchService, searchTypeKey, resultIdKey);
        titleBar.setTitle(title);
    }
    
    private void init(BaseRpcServiceAsync searchService, String searchTypeKey, String resultIdKey){
        advancedSearch = new KSAdvancedSearchRpc(searchService, searchTypeKey, resultIdKey);
        titleBar = new KSThinTitleBar(Application.getApplicationContext().getMessage("advSearch"));
        mainPanel.add(titleBar);
        mainPanel.add(advancedSearch);
        mainPanel.add(buttonPanel);
        dialog.setWidget(mainPanel);
    }
    
    public void show(){
        advancedSearch.reset();
        dialog.show();
    }
    
    public void hide(){
        dialog.hide();
    }
    
    //TODO re-enable this
/*    public void setMultipleSelect(boolean enable){
        advancedSearch.setMultipleSelect(enable);
    }*/
    
    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<List<String>> handler) {
        return handlers.addHandler(SelectionEvent.getType(), handler);
    }

    /**
     * @see com.google.gwt.event.shared.HasHandlers#fireEvent(com.google.gwt.event.shared.GwtEvent)
     */
    @Override
    public void fireEvent(GwtEvent<?> event) {
        handlers.fireEvent(event);
    }
    
    private void fireSelectEvent(List<String> selectedItems){
        SelectionEvent.fire(this, selectedItems);
    }

    public boolean isPartialMatch() {
        return advancedSearch.isPartialMatch();
    }

    public void setPartialMatch(boolean partialMatch) {
        advancedSearch.setPartialMatch(partialMatch);
    }
    
    public boolean isIgnoreCase() {
        return advancedSearch.isIgnoreCase();
    }

    public void setIgnoreCase(boolean ignoreCase) {
        advancedSearch.setIgnoreCase(ignoreCase);
    }

    public KSLabel getInstructions() {
        return advancedSearch.getSearchInstructions();
    }

    public void setInstructions(String text) {
        advancedSearch.setSearchInstructions(text);
    }
    public void reset() {
        advancedSearch.reset();
    }
}
