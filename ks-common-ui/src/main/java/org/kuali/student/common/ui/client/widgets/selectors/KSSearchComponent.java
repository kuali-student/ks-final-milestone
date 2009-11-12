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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.core.search.dto.QueryParamValue;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

//TODO comments
public class KSSearchComponent extends Composite {
    private VerticalPanel layout = new VerticalPanel();
    final Hyperlink searchLink = new Hyperlink("Search", "Search");
    final Hyperlink browseLink = new Hyperlink("Browse", "Browse");
    final HorizontalPanel searchOrBrowserLink = new HorizontalPanel();
    
    //data
    final KSSuggestBox suggestBox;
    final KSAdvancedSearchWindowComponent advSearchWindow; 
	private List<QueryParamValue> contextCriteria = new ArrayList<QueryParamValue>();  
    
    public KSSearchComponent(BaseRpcServiceAsync searchService, String searchTypeKey, String resultIdKey,
    							List<String> basicSearchCriteria, List<String> advancedSearchCriteria, String searchTitle) {
    	    	
    	final SearchSuggestOracle orgSearchOracle = new SearchSuggestOracle(searchService,
    	        "org.search.orgByShortNameAndType", 
    	        "org.queryParam.orgShortName", //field user is entering and we search on... add '%' the parameter
    	        "org.queryParam.orgId", 		//if one wants to search by ID rather than by name
    	        "org.resultColumn.orgId", 		
    	        "org.resultColumn.orgShortName");
    	  	
		QueryParamValue orgTypeParam = new QueryParamValue();
		orgTypeParam.setKey("org.queryParam.orgOptionalType");
		orgTypeParam.setValue("kuali.org.Department");    //right now hard-coded criteria set to context specific value i.e. Administrative org
		contextCriteria.add(orgTypeParam);    	
		orgSearchOracle.setAdditionalQueryParams(contextCriteria);		
    	
    	suggestBox = new KSSuggestBox(orgSearchOracle); 
    	suggestBox.setAutoSelectEnabled(false);      	          	
        layout.add(suggestBox);
		orgSearchOracle.setTextWidget(suggestBox.getTextBox());
                
        searchOrBrowserLink.add(searchLink);
        searchOrBrowserLink.add(new KSLabel("  or  "));
        searchOrBrowserLink.add(browseLink);
        layout.add(searchOrBrowserLink);
        
        //define advanced search link and window
    	advSearchWindow = new KSAdvancedSearchWindowComponent(searchService, searchTypeKey, resultIdKey, basicSearchCriteria, advancedSearchCriteria, contextCriteria, searchTitle);  
    	advSearchWindow.addSelectionHandler(selectionHandler);
        searchLink.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
               if(advSearchWindow != null){
                   advSearchWindow.show();
               }
            }
        });  
        this.initWidget(layout);
    }
    
    
    SelectionHandler<List<String>> selectionHandler = new SelectionHandler<List<String>>() {
        @Override
        public void onSelection(SelectionEvent<List<String>> event) {

            final List<String> selected = event.getSelectedItem();
            if (selected.size() > 0){
                suggestBox.setText(event.getSelectedItem().get(0));                    
            }                  
        }
    };
}
