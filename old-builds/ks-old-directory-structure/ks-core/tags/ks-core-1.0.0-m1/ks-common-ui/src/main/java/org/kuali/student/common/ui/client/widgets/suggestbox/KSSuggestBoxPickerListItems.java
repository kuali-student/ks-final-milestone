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
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.suggestbox.IdableSuggestOracle.IdableSuggestion;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;
import org.kuali.student.core.search.dto.ResultColumnInfo;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class KSSuggestBoxPickerListItems extends Composite{
    final static ApplicationContext context = Application.getApplicationContext();
    private FlexTable layout = new FlexTable();
    private KSButton remove = new KSButton(context.getMessage("remove"));
    private KSButton add = new KSButton(context.getMessage("add"));
    private KSSelectItemWidgetAbstract listWidget;
    private KSSuggestBoxWAdvSearch suggest;
    //private List<AttrDescriptor> attrDescriptors;
    private List<String> resultColumnKeys;
    private BaseRpcServiceAsync searchService;
    private String searchOnIdName;
    private String displayableResultKey;
    
    private List<ListDataItem> listData = new ArrayList<ListDataItem>();
    private HashMap<String, String> resultKeyColumnMap = new HashMap<String, String>();
    
    private class ListDataItem{
        private String id;
        private String displayText;
        private HashMap<String, String> attrValueMap = new HashMap<String, String>();
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getDisplayText() {
            return displayText;
        }
        public void setDisplayText(String displayText) {
            this.displayText = displayText;
        }
        public HashMap<String, String> getAttrValueMap() {
            return attrValueMap;
        }
        public void setAttrValueMap(HashMap<String, String> attrValueMap) {
            this.attrValueMap = attrValueMap;
        }
        
        
    }
    
    private ListItems selectedItems = new ListItems(){

        @Override
        public List<String> getAttrKeys() {
            List<String> attrs = new ArrayList<String>();
            for(String k: resultColumnKeys){
                attrs.add(resultKeyColumnMap.get(k));
            }
            return attrs;
        }

        @Override
        public String getItemAttribute(String id, String attrkey) {
            String attribute = "";
            for(ListDataItem i: listData){
                if(i.getId().equals(id)){
                    attribute = i.getAttrValueMap().get(attrkey);
                    break;
                }
            }
            return attribute;
        }

        @Override
        public int getItemCount() {
            return listData.size();
        }

        @Override
        public List<String> getItemIds() {
            List<String> ids = new ArrayList<String>();
            for(ListDataItem i: listData){
                ids.add(i.getId());
            }
            return ids;
        }

        @Override
        public String getItemText(String id) {
            String text = "";
            for(ListDataItem i: listData){
                if(i.getId().equals(id)){
                    text = i.getDisplayText();
                    break;
                }
            }
            return text;
        }
    };
    private String searchIdKey;
    
    
    public KSSuggestBoxPickerListItems(KSSuggestBoxWAdvSearch suggestWidget, KSSelectItemWidgetAbstract widget, BaseRpcServiceAsync searchService, String searchOnIdName, String searchIdKey, 
            List<String> resultColumnKeys, String displayableResultKey){
        //this.attrDescriptors = attrDescriptors;
        this.resultColumnKeys = resultColumnKeys;
        this.searchService = searchService;
        this.searchOnIdName = searchOnIdName;
        this.searchIdKey = searchIdKey;
        this.displayableResultKey = displayableResultKey;
        
        searchService.getSearchType(searchOnIdName, new AsyncCallback<SearchTypeInfo>(){

            public void onFailure(Throwable caught) {
                //TODO: How to handle this?
            }

            public void onSuccess(SearchTypeInfo searchTypeInfo) {
                SearchResultTypeInfo resultInfo = searchTypeInfo.getSearchResultTypeInfo();
                List<ResultColumnInfo> resultColumns = resultInfo.getResultColumns();
                for (ResultColumnInfo r: resultColumns){
                    //TODO use column name as a token to get a message from the message service instead
                    if(KSSuggestBoxPickerListItems.this.resultColumnKeys.contains(r.getKey())){
                        KSSuggestBoxPickerListItems.this.resultKeyColumnMap.put(r.getKey(), r.getName());
                    }
                   
                }
                listWidget.setListItems(selectedItems);
                listWidget.redraw();
            }
        });
        
        suggest = suggestWidget;
        listWidget = widget;
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
        searchService.searchForResults(searchOnIdName, queryParamValues, new AsyncCallback<List<Result>>(){
            
            @Override
            public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void onSuccess(List<Result> results) {
                //final List<IdableSuggestion> suggestions = createSuggestions(results, request.getLimit());
                //Response response = new Response(suggestions);
                if(results != null){
                    String itemDisplayText = "";
                    Result theResult = results.get(0);
                    HashMap<String,String> columnNameValueMap = new HashMap<String,String>();
                    if(theResult != null){
                        for(ResultCell c: theResult.getResultCells()){
                            if(resultKeyColumnMap.containsKey(c.getKey())){
                                columnNameValueMap.put(resultKeyColumnMap.get(c.getKey()), c.getValue());
                            }
                            
                            
                            if(c.getKey().equals(displayableResultKey)){
                                itemDisplayText = c.getValue();
                            }
                        }
                    }
                    
                    ListDataItem item = new ListDataItem();
                    item.setAttrValueMap(columnNameValueMap);
                    item.setId(id);
                    item.setDisplayText(itemDisplayText);
                    
                    listData.add(item);
                }
                listWidget.redraw();
            }
        });
    }
    
    private void setupHandlers(){
        
        suggest.getSuggestBox().addSelectionHandler(new SelectionHandler<Suggestion>(){

            @Override
            public void onSelection(SelectionEvent<Suggestion> event) {
                if(suggest.getSuggestBox().getSelectedSuggestion() != null){
                    String id = suggest.getSuggestBox().getSelectedId();
                    KSSuggestBoxPickerListItems.this.searchAndAddItem(id);
                    suggest.getSuggestBox().setText("");
                } 
            }
        });
        
        
        suggest.getSearchWindow().addSelectionHandler(new SelectionHandler<List<String>>(){

            @Override
            public void onSelection(SelectionEvent<List<String>> event) {
                List<String> ids = event.getSelectedItem();
                for(String id: ids){
                    KSSuggestBoxPickerListItems.this.searchAndAddItem(id);
                }
                suggest.getSearchWindow().hide();
                suggest.getSuggestBox().getTextBox().setText("");            
            }
        });
        
        remove.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
                List<String> selectedIds = listWidget.getSelectedItems();
                List<ListDataItem> deletedItems = new ArrayList<ListDataItem>();
                if(!selectedIds.isEmpty()){
                    for(String id: selectedIds){
                        for(ListDataItem i: listData){
                            if(id.equals(i.getId())){
                                deletedItems.add(i);
                            }
                        }
                    }
                }
                listData.removeAll(deletedItems);
                for(String id: listWidget.getSelectedItems()){
                    listWidget.deSelectItem(id);
                }
                listWidget.redraw();
            }
        });
        
        suggest.getSuggestBox().getTextBox().addKeyDownHandler(new KeyDownHandler(){

            @Override
            public void onKeyDown(KeyDownEvent event) {
                if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
                    KSSuggestBoxPickerListItems.this.addButtonAction();
                }
                
            }
        });
        
        add.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                KSSuggestBoxPickerListItems.this.addButtonAction();
                
            }
        });
        
    }
    
    private void addButtonAction(){
        String currentText = suggest.getSuggestBox().getText();
        if(currentText != null && currentText != ""){
            IdableSuggestion suggestion = suggest.getSuggestBox().getOracle().getSuggestionByText(currentText);
            if(suggestion != null){
                KSSuggestBoxPickerListItems.this.searchAndAddItem(suggestion.getId());
                suggest.getSuggestBox().getTextBox().setText("");
            }
            else{
                suggest.getSuggestBox().getTextBox().setFocus(true);
                suggest.getSuggestBox().getTextBox().selectAll();
                
            }
        }
    }
    
}
