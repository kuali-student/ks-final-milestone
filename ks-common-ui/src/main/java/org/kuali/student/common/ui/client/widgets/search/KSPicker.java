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

import org.kuali.student.common.ui.client.configurable.mvc.WidgetConfigInfo;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.mvc.HasFocusLostCallbacks;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.common.ui.client.widgets.suggestbox.IdableSuggestOracle.IdableSuggestion;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.DataValue;
import org.kuali.student.core.assembly.data.Data.StringValue;
import org.kuali.student.core.assembly.data.Data.Value;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSPicker extends Composite implements HasFocusLostCallbacks, HasValueChangeHandlers<String>, HasDataValue{
	
    private VerticalPanel layout = new VerticalPanel();
    private BasicWidget basicWidget;
    private Hyperlink advSearchLink = new Hyperlink("advanced search", "advSearch");
    private AdvancedSearchWindow advSearchWindow = null;
    private SearchPanel searchPanel;
    private WidgetConfigInfo config = new WidgetConfigInfo();
    
    public KSPicker(WidgetConfigInfo config) {
		this(config.lookupMeta, config.additionalLookups);
    	this.config = config;
	}
    
    public KSPicker(LookupMetadata inLookupMetadata, List<LookupMetadata> additionalLookupMetadata){
    	if (inLookupMetadata == null) {
    		//FIXME throw error?
    		return;
    	}    
    	
    	//1) setup initial search widget such as suggest box, drop down etc.
    	
        //setup suggest box if required
    	if (inLookupMetadata.getWidget() == LookupMetadata.Widget.SUGGEST_BOX) {
			final SearchSuggestOracle orgSearchOracle = new SearchSuggestOracle(inLookupMetadata);     	
			basicWidget = new BasicWidget(new KSSuggestBox(orgSearchOracle)); 
			((KSSuggestBox) basicWidget.get()).setAutoSelectEnabled(false);
	    	orgSearchOracle.setTextWidget(((SuggestBox) basicWidget.get()).getTextBox());		
	        layout.add(basicWidget.get());
    	} else if (inLookupMetadata.getWidget() == LookupMetadata.Widget.NO_WIDGET) {
    		if ((inLookupMetadata.getName() != null) && (inLookupMetadata.getName().trim().length() > 0)) {
    			advSearchLink.setText(inLookupMetadata.getName().trim());
    		}
    		basicWidget = new BasicWidget(new SelectionContainerWidget());
    	} else { //default to text box
    		basicWidget = new BasicWidget(new KSTextBox());
    		layout.add(basicWidget.get());
    		GWT.log("KSTextBox for " + inLookupMetadata.getKey(), null);
    	}
        
    	//2) setup advanced search widget such as advanced search box, browse hierarchy search box etc.
    	
        //setup advanced search if required
    	List<LookupMetadata> advancedLightboxLookupdata = getLookupMetadataBasedOnWidget(additionalLookupMetadata, LookupMetadata.Widget.ADVANCED_LIGHTBOX);
    	if (advancedLightboxLookupdata != null) {    
    		
    		//for multiple searches, show a drop down for user to select from
    		if (advancedLightboxLookupdata.size() == 1) {
    			searchPanel = new SearchPanel(advancedLightboxLookupdata.get(0));
    			advSearchWindow = new AdvancedSearchWindow(advancedLightboxLookupdata.get(0).getTitle(), searchPanel);
    		} else {
    			searchPanel = new SearchPanel(advancedLightboxLookupdata);
    			advSearchWindow = new AdvancedSearchWindow(advancedLightboxLookupdata.get(0).getTitle(), searchPanel);
    		}
    		searchPanel.setMultiSelect(true);
    		
	        advSearchWindow.addSelectionCompleteCallback(new Callback<List<SelectedResults>>(){
	            public void exec(List<SelectedResults> results) {
	                if (results.size() > 0){               	           	
                        basicWidget.setResults(results);                        
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
    
    private class BasicWidget extends Widget implements HasDataValue, HasFocusLostCallbacks{
		private Widget basicWidget;
		
		public BasicWidget(Widget basicWidget){
			this.basicWidget = basicWidget;			
		}
		
		public void setResults(List<SelectedResults> results) {
			if (basicWidget.getClass().getName().contains("KSTextBox")) {
				((KSTextBox)basicWidget).setText(results.get(0).getDisplayKey());  //FIXME: what about the result id?
			} else if (basicWidget.getClass().getName().contains("ContainerWidget")) {
				List<String> selections = new ArrayList<String>();
				for (SelectedResults result: results) {
					selections.add(result.getDisplayKey());						  //FIXME: what about the result ids?
				}				
				((SelectionContainerWidget) basicWidget).setSelections(selections);
			} else if (basicWidget.getClass().getName().contains("KSSuggestBox")) {
                IdableSuggestion theSuggestion = new IdableSuggestion();
                theSuggestion.setReplacementString(results.get(0).getDisplayKey());
                theSuggestion.setId(results.get(0).getReturnKey());     				
				((KSSuggestBox) basicWidget).setValue(theSuggestion);
			}				
		}
		
		public void setValue(Value value, boolean fireEvents) {
			if (basicWidget.getClass().getName().contains("KSTextBox")) {
				if(value != null){
					((KSTextBox)basicWidget).setText((String) value.get());
				}
				else{
					((KSTextBox)basicWidget).setText("");
				}
			} else if (basicWidget.getClass().getName().contains("KSSuggestBox")) {
				//Do check here
				if(value != null){
					if(!config.isRepeating){
						((KSSuggestBox) basicWidget).setValue((String)value.get(), fireEvents);
					}
					else{
						DataValue dataValue = (DataValue)value;
						Data d = dataValue.get();
						//final QueryPath path = new QueryPath();
						//path.add(new Data.IntegerKey("0"));
						QueryPath path = QueryPath.parse("0");
						String v = d.query(path);
						((KSSuggestBox) basicWidget).setValue((String)v, fireEvents);
					}
				}
				else{
					((KSSuggestBox) basicWidget).setValue("", fireEvents);
				}
			}			
		}		
		
		public Value getValue() {
			if (basicWidget.getClass().getName().contains("KSTextBox")) {
				StringValue value = new StringValue(((KSTextBox)basicWidget).getText());
				return value;
			} else if (basicWidget.getClass().getName().contains("KSSuggestBox")) {
				//Do check here
				if(!config.isRepeating){
					StringValue value = new StringValue(((KSSuggestBox) basicWidget).getValue());
					return value;
				}
				else{
					Data data = new Data();
					data.set(new Data.IntegerKey(0),((KSSuggestBox) basicWidget).getValue());
					DataValue value = new DataValue(data);
					return value;
				}
			}
			return null;			
		}
		
		public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
			if (basicWidget.getClass().getName().contains("KSTextBox")) {
				return ((KSTextBox)basicWidget).addValueChangeHandler(handler);
			} else if (basicWidget.getClass().getName().contains("KSSuggestBox")) {
				return ((KSSuggestBox) basicWidget).addValueChangeHandler(handler);
			}	
			return null;
		}		
		
		public Widget get() {
			return basicWidget;
		}

		public void addValuesChangeHandler(ValueChangeHandler<List<String>> handler) {
			if (basicWidget.getClass().getName().contains("ContainerWidget")) {
				((SelectionContainerWidget) basicWidget).addValuesChangeHandler(handler);
			}
		}

		@Override
		public void addValueChangeCallback(final Callback<Value> callback) {
			ValueChangeHandler<String> handler = new ValueChangeHandler<String>(){
				@Override
				public void onValueChange(ValueChangeEvent<String> event) {
					StringValue value = new StringValue(event.getValue());
					callback.exec(value);
				}
			};
			addValueChangeHandler(handler);
		}

		@Override
		public void setValue(Value value) {
			this.setValue(value, true);
		}

		@Override
		public void addFocusLostCallback(final Callback<Boolean> callback) {
			if (basicWidget.getClass().getName().contains("KSTextBox")) {
				((KSTextBox)basicWidget).addBlurHandler(new BlurHandler(){
					@Override
					public void onBlur(BlurEvent event) {
						callback.exec(true);
						
					}
				});
			} else if (basicWidget.getClass().getName().contains("KSSuggestBox")) {
				((KSSuggestBox) basicWidget).addSelectionChangeHandler(new SelectionChangeHandler(){
					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						callback.exec(true);
					}
				});
			}	
			
		}

    }
    
    private class SelectionContainerWidget extends Widget implements HasValue<List<String>> {
    	//ValueChangeHandler<List<String>> handler;
    	private List<String> selections = new ArrayList<String>();

		public List<String> getSelections() {
			return selections;
		}

		public void addValuesChangeHandler(ValueChangeHandler<List<String>> handler) {
			super.addHandler(handler, ValueChangeEvent.getType());
		}

		public void setSelections(List<String> selections) {
			this.selections = selections;
			ValueChangeEvent.fire(this, this.selections);
		}

		@Override
		public List<String> getValue() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setValue(List<String> value) {
			// TODO Auto-generated method stub			
		}

		@Override
		public void setValue(List<String> value, boolean fireEvents) {
			// TODO Auto-generated method stub			
		}

		@Override
		public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<String>> handler) {
			// TODO Auto-generated method stub
			return null;
		}    	    	
    }
    
    //TODO use for labels lookup
    private String getLabel(String labelKey) {
      //  return Application.getApplicationContext().getUILabel(messageGroup, type, state, labelKey);
    	return null;
    }
    
    public AdvancedSearchWindow getSearchWindow(){
        return advSearchWindow;
    }

/*	@Override
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
	}*/

/*	public HandlerRegistration addStringValueChangeHandler(ValueChangeHandler<String> handler) {
		return basicWidget.addValueChangeHandler(handler);
	} */

/*	public void addStringValuesChangeHandler(ValueChangeHandler<List<String>> handler) {
		basicWidget.addValuesChangeHandler(handler);
	}*/

	@Override
	public void addValueChangeCallback(Callback<Value> callback) {
		basicWidget.addValueChangeCallback(callback);
	}

	@Override
	public void setValue(Value value) {
		setValue(value, true);
		
	}
	
	public void setValue(Value value, boolean fireEvents) {
		//suggest.reset();
		basicWidget.setValue(value, fireEvents);
	}

	@Override
	public Value getValue() {
		return basicWidget.getValue();
	}
	
	public void setValue(String value){
		basicWidget.setValue(new StringValue(value));
	}

	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> handler) {
		return basicWidget.addValueChangeHandler(handler);
	}
	
	public void addValuesChangeHandler(ValueChangeHandler<List<String>> handler) {
		basicWidget.addValuesChangeHandler(handler);
	}

	@Override
	public void addFocusLostCallback(Callback<Boolean> callback) {
		basicWidget.addFocusLostCallback(callback);
	}

}
