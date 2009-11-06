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
package org.kuali.student.common.ui.client.widgets.selectors;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchRpc;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class KSAdvancedSearchWindowComponent implements HasSelectionHandlers<List<String>>{
    private KSLightBox dialog = new KSLightBox();
    private VerticalPanel mainPanel = new VerticalPanel();
    private KSAdvancedSearchRpc advancedSearch;
    private HandlerManager handlers = new HandlerManager(this);
    
    //Title Bar with Cancel button
    private final HorizontalPanel titlePanel = new HorizontalPanel();
    private KSLabel titleLabel = new KSLabel();
    private KSButton cancelButton = new KSButton("Cancel");    
    
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
    
    public KSAdvancedSearchWindowComponent(BaseRpcServiceAsync searchService, String searchTypeKey, String resultIdKey){
        init(searchService, searchTypeKey, resultIdKey);       
    }

    public KSAdvancedSearchWindowComponent(BaseRpcServiceAsync searchService, String searchTypeKey, String resultIdKey, String title){
        init(searchService, searchTypeKey, resultIdKey);
    }
    
    private void init(BaseRpcServiceAsync searchService, String searchTypeKey, String resultIdKey){
        advancedSearch = new KSAdvancedSearchRpc(searchService, searchTypeKey, resultIdKey);
        mainPanel.add(titlePanel);
        mainPanel.add(advancedSearch);
        mainPanel.add(buttonPanel);
        dialog.setWidget(mainPanel);
        initTitleBar(Application.getApplicationContext().getMessage("advSearch"));        
    }
    
    public void initTitleBar(String text){
        titlePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
        titleLabel = new KSLabel(text, false);
        titlePanel.add(titleLabel);
        titlePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
        titlePanel.add(cancelButton);
        titlePanel.addStyleName(KSStyles.KS_POPUP_HEADER);  //TODO header should be grey
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
}
