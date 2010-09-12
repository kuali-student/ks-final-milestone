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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.searchtable.SearchBackedTable;
import org.kuali.student.common.ui.client.widgets.suggestbox.IdableSuggestOracle.IdableSuggestion;
import org.kuali.student.core.search.dto.QueryParamValue;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class KSSuggestBoxPicker extends Composite{
    final static ApplicationContext context = Application.getApplicationContext();
    private FlexTable layout = new FlexTable();
    private KSButton remove = new KSButton(context.getMessage("remove"));
    private KSButton add = new KSButton(context.getMessage("add"));
    private SearchBackedTable listWidget;
    private KSSuggestBoxWAdvSearch suggest;
    private List<String> resultColumnKeys;
/*    private BaseRpcServiceAsync searchService;
    private String searchOnIdName;*/
    
    private HashMap<String, String> resultKeyColumnMap = new HashMap<String, String>();
    
    private String searchIdKey;
    
    public KSSuggestBoxPicker(KSSuggestBoxWAdvSearch suggestWidget, BaseRpcServiceAsync searchService, String searchOnIdName, String searchIdKey, 
            List<String> resultColumnKeys, String resultIdKey){
        this.resultColumnKeys = resultColumnKeys;
/*        this.searchService = searchService;
        this.searchOnIdName = searchOnIdName;*/
        this.searchIdKey = searchIdKey;

        listWidget = new SearchBackedTable(searchService, searchOnIdName, resultIdKey);
        
        suggest = suggestWidget;
        layout.setWidget(0, 0, suggest);
        layout.setWidget(0, 1, add);
        layout.setWidget(1, 0, listWidget);
        layout.setWidget(1, 1, remove);
        layout.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);

        this.setupHandlers();
        layout.getRowFormatter().addStyleName(1, KSStyles.KS_SUGGEST_PICKER_LIST_ROW);
        layout.addStyleName(KSStyles.KS_SUGGEST_PICKER_LAYOUT_TABLE);
        this.initWidget(layout);
    }
    
    private void searchAndAddItem(final String id){
        List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>();
        QueryParamValue qv = new QueryParamValue();
        qv.setKey(searchIdKey);
        qv.setValue(id);
        queryParamValues.add(qv);
        listWidget.performSearch(queryParamValues);
    }
    
    private void setupHandlers(){
        
        suggest.getSuggestBox().addSelectionHandler(new SelectionHandler<Suggestion>(){

            @Override
            public void onSelection(SelectionEvent<Suggestion> event) {
                if(suggest.getSuggestBox().getSelectedSuggestion() != null){
                    String id = suggest.getSuggestBox().getSelectedId();
                    KSSuggestBoxPicker.this.searchAndAddItem(id);
                    suggest.getSuggestBox().reset();
                } 
            }
        });
        
        
        suggest.getSearchWindow().addSelectionHandler(new SelectionHandler<List<String>>(){

            @Override
            public void onSelection(SelectionEvent<List<String>> event) {
                List<String> ids = event.getSelectedItem();
                for(String id: ids){
                    KSSuggestBoxPicker.this.searchAndAddItem(id);
                }
                suggest.getSearchWindow().hide();
                suggest.getSuggestBox().reset();      
            }
        });
        
        remove.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
                listWidget.removeSelected();
            }
        });
        
        suggest.getSuggestBox().getTextBox().addKeyDownHandler(new KeyDownHandler(){

            @Override
            public void onKeyDown(KeyDownEvent event) {
                if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
                    KSSuggestBoxPicker.this.enterAction();
                }
                
            }
        });
        
        add.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                KSSuggestBoxPicker.this.addButtonAction();
                
            }
        });
        
    }
    
    private void enterAction(){
        String currentText = suggest.getSuggestBox().getText();
        if(currentText != null && currentText != ""){
            IdableSuggestion suggestion = suggest.getSuggestBox().getOracle().getSuggestionByText(currentText);
            if(suggestion != null){
                KSSuggestBoxPicker.this.searchAndAddItem(suggestion.getId());
                suggest.getSuggestBox().reset();
            }
            else{
                suggest.getSuggestBox().getTextBox().setFocus(true);
                suggest.getSuggestBox().getTextBox().selectAll();
                
            }
        }
    }
    
    private void addButtonAction(){
        IdableSuggestion currentSuggestion = suggest.getSuggestBox().getSelectedSuggestion();
        if(currentSuggestion != null){
            KSSuggestBoxPicker.this.searchAndAddItem(currentSuggestion.getId());
            suggest.getSuggestBox().reset();
        }
        else{
            suggest.getSuggestBox().getTextBox().setFocus(true);
            suggest.getSuggestBox().getTextBox().selectAll();
        }
    }
    
    public void getListIds(){
        listWidget.getAllIds();
    }
    
    public void setListIds(List<String> ids){
        listWidget.clearTable();
        for(String id: ids){
            searchAndAddItem(id);
        }
    }
    
}
