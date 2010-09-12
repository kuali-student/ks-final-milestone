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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

public class KSSuggestBoxWAdvSearch extends Composite{
    private VerticalPanel layout = new VerticalPanel();
    private KSSuggestBox suggest;
    private Hyperlink advSearchLink = new Hyperlink("Advanced Search...", "advSearch");
    private KSAdvancedSearchWindow advSearchWindow = null;
    
    //TODO pass in some way to search for these items? and control params you can search on?
    public KSSuggestBoxWAdvSearch(KSSuggestBox suggest, KSAdvancedSearchWindow searchDialog){
        this.suggest = suggest;
        this.suggest.setAutoSelectEnabled(false);
        layout.add(suggest);
        layout.add(advSearchLink);
        
        advSearchWindow = searchDialog;

        advSearchLink.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
               if(advSearchWindow != null){
                   advSearchWindow.show();
               }
            }
        });
        this.initWidget(layout);
    }
    
    public KSSuggestBox getSuggestBox(){
        return suggest;
    }
    
    public KSAdvancedSearchWindow getSearchWindow(){
        return advSearchWindow;
    } 
}
