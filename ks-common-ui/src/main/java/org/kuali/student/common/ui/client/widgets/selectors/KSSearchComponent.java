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

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

//TODO comments
public class KSSearchComponent extends Composite {
    private VerticalPanel layout = new VerticalPanel();
    final Hyperlink searchLink = new Hyperlink("Search", "Search");
    final Hyperlink browseLink = new Hyperlink("Browse", "Browse");    
    
    //data
    final KSSuggestBox suggestBox;
    final KSAdvancedSearchWindow advSearchWindow;
    
    
    public KSSearchComponent(BaseRpcServiceAsync searchService, String searchTypeKey, String resultIdKey) {
    	
    	final SearchSuggestOracle orgSearchOracle = new SearchSuggestOracle(searchService,
    	        "org.search.orgByShortNameAndType", 
    	        "org.queryParam.orgShortName",
    	        "org.queryParam.orgId", 
    	        "org.resultColumn.orgId", 
    	        "org.resultColumn.orgShortName");     	
    	suggestBox = new KSSuggestBox(orgSearchOracle); 
    	suggestBox.setAutoSelectEnabled(false);
    	advSearchWindow = new KSAdvancedSearchWindow(searchService, searchTypeKey, resultIdKey);   
    	      	

    	
        layout.add(suggestBox);
   /*     layout.add(advSearchLink);
       
        advSearchLink.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
               if(advSearchWindow != null){
                   advSearchWindow.show();
               }
            }
        });  */
        this.initWidget(layout);
    }

}
