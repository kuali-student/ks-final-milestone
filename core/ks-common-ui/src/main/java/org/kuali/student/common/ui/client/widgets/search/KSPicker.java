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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.common.ui.client.widgets.suggestbox.IdableSuggestOracle.IdableSuggestion;
import org.kuali.student.core.assembly.data.LookupMetadata;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSPicker extends Composite implements HasValue<String> {
	
    private VerticalPanel layout = new VerticalPanel();
    private BasicWidget basicWidget;
    private Hyperlink advSearchLink = new Hyperlink("advanced search", "advSearch");
    private AdvancedSearchWindow advSearchWindow = null;
    
    public KSPicker(LookupMetadata inLookupMetadata, List<LookupMetadata> additionalLookupMetadata){
    	    	
    	if (inLookupMetadata == null) {
    		//FIXME throw error?
    		return;
    	}
    	
    	//create a single list of lookup metadata
    	List<LookupMetadata> lookupMetadata = new ArrayList<LookupMetadata>();
    	lookupMetadata.add(inLookupMetadata);    		
    	for (LookupMetadata addLookupData : additionalLookupMetadata) {
    		lookupMetadata.add(addLookupData);
    	}
    	
        //setup suggest box if required
    	List<LookupMetadata> suggestBoxLookupdata = getLookupMetadataBasedOnWidget(lookupMetadata, LookupMetadata.Widget.SUGGEST_BOX);
    	if (suggestBoxLookupdata != null) {
			final SearchSuggestOracle orgSearchOracle = new SearchSuggestOracle(suggestBoxLookupdata.get(0));     	
			basicWidget = new BasicWidget(new KSSuggestBox(orgSearchOracle)); 
			((SuggestBox) basicWidget.get()).setAutoSelectEnabled(false);
	    	orgSearchOracle.setTextWidget(((SuggestBox) basicWidget.get()).getTextBox());		
	        layout.add(basicWidget.get());
    	} else {
    		basicWidget = new BasicWidget(new KSTextBox());
    		layout.add(basicWidget.get());
    		GWT.log("KSTextBox for " + inLookupMetadata.getKey(), null);
    	}
        
        //setup advanced search if required
    	List<LookupMetadata> advancedLightboxLookupdata = getLookupMetadataBasedOnWidget(lookupMetadata, LookupMetadata.Widget.ADVANCED_LIGHTBOX);
    	if (advancedLightboxLookupdata != null) {    	
	        advSearchWindow = new AdvancedSearchWindow(advancedLightboxLookupdata.get(0).getName(), new SearchPanel(advancedLightboxLookupdata));
	        advSearchWindow.addSelectionCompleteCallback(new Callback<List<SelectedResults>>(){
	            public void exec(List<SelectedResults> results) {
	                if (results.size() > 0){
	                	//suggestBox.setText(results.get(0).getDisplayKey());
	                	//suggestBox.setValue(results.get(0).getReturnKey());
	                	
                        IdableSuggestion theSuggestion = new IdableSuggestion();
                        theSuggestion.setReplacementString(results.get(0).getDisplayKey());
                        theSuggestion.setId(results.get(0).getReturnKey());                	
                        basicWidget.setValue(theSuggestion);
                        
	                	advSearchWindow.hide();
	                }                
	            }            
	        });      
	        
	        advSearchLink.addClickHandler(new ClickHandler(){
	            @Override
	            public void onClick(ClickEvent event) {
	               if(advSearchWindow != null){
	                   advSearchWindow.show();
	               }
	            }
	        });
	        
	        layout.add(advSearchLink);        
    	}
        
        this.initWidget(layout);
    }
    
    private List<LookupMetadata> getLookupMetadataBasedOnWidget(List<LookupMetadata> additionalLookupMetadata, LookupMetadata.Widget widgetType) {
    	List<LookupMetadata> lookups = new ArrayList<LookupMetadata>();
    	for (LookupMetadata addLookupData : additionalLookupMetadata) {
    		if (addLookupData.getWidget() == widgetType) {
    			lookups.add(addLookupData);
    		}
    	}
    	return (lookups.size() > 0 ? lookups : null);
    }
    
    private class BasicWidget extends Widget {
		private Widget basicWidget;
		
		public BasicWidget(Widget basicWidget){
			this.basicWidget = basicWidget;			
		}
		
		public void setValue(Object value) {
			if (basicWidget.getClass().getName().contains("KSTextBox")) {
				((KSTextBox)basicWidget).setText((String) value);
			} else if (basicWidget.getClass().getName().contains("KSSuggestBox")) {
				((KSSuggestBox) basicWidget).setValue((IdableSuggestion)value);
			}			
		}
		
		public void setValue(String value, boolean fireEvents) {
			if (basicWidget.getClass().getName().contains("KSTextBox")) {
				((KSTextBox)basicWidget).setText((String) value);
			} else if (basicWidget.getClass().getName().contains("KSSuggestBox")) {
				((KSSuggestBox) basicWidget).setValue((String)value, fireEvents);
			}			
		}		
		
		public String getValue() {
			if (basicWidget.getClass().getName().contains("KSTextBox")) {
				return ((KSTextBox)basicWidget).getText();
			} else if (basicWidget.getClass().getName().contains("KSSuggestBox")) {
				return ((KSSuggestBox) basicWidget).getValue();
			}
			return null;			
		}
		
		public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
			if (basicWidget.getClass().getName().contains("KSTextBox")) {
				((KSTextBox)basicWidget).addValueChangeHandler(handler);
			} else if (basicWidget.getClass().getName().contains("KSSuggestBox")) {
				((KSSuggestBox) basicWidget).addValueChangeHandler(handler);
			}	
			return null;
		}		
		
		public Widget get() {
			return basicWidget;
		}
    }
    
    public AdvancedSearchWindow getSearchWindow(){
        return advSearchWindow;
    }

	@Override
	public String getValue() {
		return basicWidget.getValue();
	}

	@Override
	public void setValue(String value) {
		setValue(value, true);		
	}

	@Override
	public void setValue(String value, boolean fireEvents) {
		//suggest.reset();
		basicWidget.setValue(value, fireEvents);
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return basicWidget.addValueChangeHandler(handler);
	} 
}
