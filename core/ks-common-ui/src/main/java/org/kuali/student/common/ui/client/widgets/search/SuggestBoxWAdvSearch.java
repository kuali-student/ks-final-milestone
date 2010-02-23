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
package org.kuali.student.common.ui.client.widgets.search;

import org.kuali.student.common.ui.client.widgets.KSTextBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SuggestBoxWAdvSearch extends Composite implements HasValue<String> {
    private VerticalPanel layout = new VerticalPanel();
    private KSTextBox suggest;
    private Hyperlink advSearchLink = new Hyperlink("advanced search", "advSearch");
    private AdvancedSearchWindow advSearchWindow = null;
    
    //TODO pass in some way to search for these items? and control params you can search on?
    public SuggestBoxWAdvSearch(KSTextBox suggest, AdvancedSearchWindow searchDialog){  //FIXME: make KSTextBox to be KSSuggestBox
        this.suggest = suggest;
       // this.suggest.setAutoSelectEnabled(false);
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
    
    public KSTextBox getSuggestBox(){
        return suggest;
    }
    
    public AdvancedSearchWindow getSearchWindow(){
        return advSearchWindow;
    }

	@Override
	public String getValue() {
		return suggest.getValue();
	}

	@Override
	public void setValue(String value) {
		setValue(value, true);		
	}

	@Override
	public void setValue(String value, boolean fireEvents) {
		//suggest.reset();
		suggest.setValue(value, fireEvents);
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return suggest.addValueChangeHandler(handler);
	} 
}
