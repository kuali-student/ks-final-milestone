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
package org.kuali.student.core.person.ui.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectableTableList;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.person.ui.client.service.PersonRpcService;
import org.kuali.student.core.person.ui.client.service.PersonRpcServiceAsync;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultRow;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PersonSearchWidget extends Composite implements HasSelectionHandlers<PersonInfo> {
    PersonRpcServiceAsync personRpcService = GWT.create(PersonRpcService.class);
    
    VerticalPanel root = new VerticalPanel();
    
    ListBox orgHierarchyDropDown = null;
    KSTextBox personName = null;
    KSButton selectButton = new KSButton("Select");
    KSLabel noResults = new KSLabel("No organizations found.");
    
    KSSelectableTableList resultTable = null;
    SimplePanel resultPanel = new SimplePanel();
    PersonInfoList personInfoList;
    
    public PersonSearchWidget(){
        this.initWidget(root);
        
        personName = new KSTextBox();
       
        FlexTable tb = new FlexTable();
        
        tb.setWidget(0,0, new KSLabel("Name"));
        tb.setWidget(0,1, personName);
        
        tb.setWidget(0,2, new KSButton("Search", new ClickHandler(){
            public void onClick(ClickEvent event) {
                getSearchResults();
            }
            
            }            
        ));

        tb.addStyleName("KS-Person-Search-Widget");
        
        root.add(tb);
        selectButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                List<String> selectedItems = resultTable.getSelectedItems(); 
                if ( selectedItems.size() == 0){
                    Window.alert("No person selected");
                } else {
                    String personId ;
                    personId = resultTable.getSelectedItems().get(0);

                    personRpcService.fetchPerson(personId, new AsyncCallback<PersonInfo>(){
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        public void onSuccess(PersonInfo personInfo) {
                            fireSelectEvent(personInfo);
                        }            
                    });                
                }
            }
        });
        selectButton.setVisible(false);
        resultPanel.addStyleName("KS-Org-Search-Widget-Results");
        root.add(resultPanel);
        root.add(selectButton);
    }    

    private void fireSelectEvent(PersonInfo selectedPerson){               
        SelectionEvent.fire(this, selectedPerson);
    }
    
    protected void getSearchResults(){
        List<SearchParam> queryParamValues = new ArrayList<SearchParam>();
        SearchParam qpv1 = new SearchParam();
        qpv1.setKey("person.queryParam.personGivenName");
        qpv1.setValue(personName.getText().replace('*', '%'));
        queryParamValues.add(qpv1);
        
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setParams(queryParamValues);
        searchRequest.setSearchKey("person.search.personQuickViewByGivenName");
        personRpcService.search(searchRequest, new AsyncCallback<SearchResult>(){

                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());                        
                    }

                    public void onSuccess(SearchResult result) {
                        if (result == null || result.getRows().size() <=0 ){
                            resultPanel.setWidget(noResults);
                        } else {
                        	personInfoList = new PersonInfoList(result.getRows());
                            resultTable = new KSSelectableTableList();
                            resultTable.setMultipleSelect(false);
                            resultTable.setListItems(personInfoList);
                            resultPanel.setWidget(resultTable);
                            selectButton.setVisible(true);
                        }
                    }            
            }
        );        
    }
    
    public HandlerRegistration addSelectionHandler(SelectionHandler<PersonInfo> selectionHandler){
        return addHandler(selectionHandler,SelectionEvent.getType());
    }       
        
    public class PersonInfoList implements ListItems{
        Map<String, SearchResultRow> personInfoMap = new HashMap<String, SearchResultRow>();
                
        public PersonInfoList(List<SearchResultRow> results){
            for (SearchResultRow r: results){
                personInfoMap.put(r.getCells().get(0).getValue(), r);
            }
        }
        
        public List<String> getAttrKeys() {
            return Arrays.asList("Person Name");
        }

        public String getItemAttribute(String id, String attrkey) {
        	SearchResultRow r = personInfoMap.get(id);
            
            if (attrkey.equals("Person Name")){
                return r.getCells().get(1).getValue(); 
            }
            
            return null;
        }

        public int getItemCount() {
            return personInfoMap.size();
        }

        public List<String> getItemIds() {
            List<String> keys = new ArrayList<String>();

            for (String s:personInfoMap.keySet()){
                keys.add(s);
            }
            
            return keys;
        }

        public String getItemText(String id) {
            return personInfoMap.get(id).getCells().get(1).getValue();
        }
                
    }

}
