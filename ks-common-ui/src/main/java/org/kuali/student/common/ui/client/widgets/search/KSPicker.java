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
import org.kuali.student.common.ui.client.mvc.TranslatableValueWidget;
import org.kuali.student.common.ui.client.service.SearchRpcService;
import org.kuali.student.common.ui.client.service.SearchRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSErrorDialog;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SearchResultListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.common.ui.client.widgets.suggestbox.IdableSuggestOracle.IdableSuggestion;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.LookupParamMetadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.DataValue;
import org.kuali.student.core.assembly.data.Data.StringValue;
import org.kuali.student.core.assembly.data.Data.Value;
import org.kuali.student.core.assembly.data.Metadata.WriteAccess;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSPicker extends Composite implements HasFocusLostCallbacks, HasValueChangeHandlers<String>, HasDataValue, TranslatableValueWidget {
	
    private VerticalPanel layout = new VerticalPanel();
    private BasicWidget basicWidget;
    private Hyperlink advSearchLink = new Hyperlink("advanced search", "advSearch");
    private AdvancedSearchWindow advSearchWindow = null;
    private SearchPanel searchPanel;
    private WidgetConfigInfo config;
    private List<Callback<SelectedResults>> basicSelectionCallbacks =
        new ArrayList<Callback<SelectedResults>>();
    private List<Callback<String>> basicSelectionTextChangeCallbacks =
        new ArrayList<Callback<String>>();
    
    private SearchRpcServiceAsync searchRpcServiceAsync = GWT.create(SearchRpcService.class);
    
    public KSPicker(WidgetConfigInfo config) {
        this.config = config;
		init(config.lookupMeta, config.additionalLookups);
	}
    
    public KSPicker(LookupMetadata inLookupMetadata, List<LookupMetadata> additionalLookupMetadata){
    	init(inLookupMetadata, additionalLookupMetadata);
    }
    
    private void init(LookupMetadata inLookupMetadata, List<LookupMetadata> additionalLookupMetadata) {
        if (inLookupMetadata == null) {
            KSErrorDialog errorDialog = new KSErrorDialog();
            errorDialog.show(new Throwable("Invalid lookup configuration: missing initial lookup metadata."));
            return;
        }    
        
        if(config == null) {
            config = new WidgetConfigInfo();
        }
        
        setupBasicSearch(inLookupMetadata);
        setupAdvancedSearch(additionalLookupMetadata);
        this.initWidget(layout);
    }    

    private void setupBasicSearch(LookupMetadata inLookupMetadata) {
               
        //setup initial search widget such as suggest box, drop down etc.
        if (inLookupMetadata.getWidget() == LookupMetadata.Widget.SUGGEST_BOX) {
            
            if(config.canEdit) {
                final SearchSuggestOracle orgSearchOracle = new SearchSuggestOracle(inLookupMetadata);
                final KSSuggestBox suggestBox = new KSSuggestBox(orgSearchOracle, true);
                suggestBox.addKeyUpHandler(new KeyUpHandler() {
                    @Override
                    public void onKeyUp(KeyUpEvent event) {
                        for(Callback<String> basicSelectionTextChangeCallback: basicSelectionTextChangeCallbacks){
                            basicSelectionTextChangeCallback.exec("Text Changed");
                        }
                    }
                });
                suggestBox.addSelectionChangeHandler(new SelectionChangeHandler(){

                    @Override
                    public void onSelectionChange(SelectionChangeEvent event) {
                        IdableSuggestion currentSuggestion = suggestBox.getSelectedSuggestion();
                        SelectedResults selectedResults = new SelectedResults(
                                currentSuggestion.getReplacementString(), currentSuggestion.getId());
                        for(Callback<SelectedResults> basicSelectionCallback: basicSelectionCallbacks){
                            basicSelectionCallback.exec(selectedResults);
                        }
                    }
                });
                basicWidget = new BasicWidget(suggestBox);
                ((KSSuggestBox) basicWidget.get()).setAutoSelectEnabled(false);
                orgSearchOracle.setTextWidget(((SuggestBox) basicWidget.get()).getTextBox());       
            } else {
                basicWidget = new BasicWidget(new KSLabel());
            }
            layout.add(basicWidget.get());
            
        } else if (inLookupMetadata.getWidget() == LookupMetadata.Widget.DROP_DOWN) {                       
            
            //FIXME should we search on values to populate drop down here or later when user will access the screen?
            if(config.canEdit) {                              

                KSDropDown typesDropDown = new KSDropDown();
                basicWidget = new BasicWidget(typesDropDown);                
                
                SearchRequest sr = initializeSearchRequest(inLookupMetadata);                
                searchRpcServiceAsync.search(sr, new AsyncCallback<SearchResult>(){

                    @Override
                    public void onFailure(Throwable cause) {
                        KSErrorDialog errorDialog = new KSErrorDialog();
                        errorDialog.show(cause);
                    }

                    @Override
                    public void onSuccess(SearchResult results) {
                        if(results != null){                                                           
                            ((KSDropDown)basicWidget.get()).setListItems(new SearchResultListItems(results.getRows()));                   
                        } else {
                            ((KSDropDown)basicWidget.get()).setListItems(new SearchResultListItems(null));
                            //FIXME is this configuration error?
                        }
                        //((KSDropDown)basicWidget.get()).processOutstandingCallback();  //due to possible race condition
                        ((KSDropDown)basicWidget.get()).setInitialized(true);
                        layout.add(basicWidget.get());
                    }
                });
            } else {
                basicWidget = new BasicWidget(new KSLabel());
                layout.add(basicWidget.get());
            }
            
        } else if (inLookupMetadata.getWidget() == LookupMetadata.Widget.NO_WIDGET) {
            
            if ((inLookupMetadata.getName() != null) && (inLookupMetadata.getName().trim().length() > 0)) {
                advSearchLink.setText(inLookupMetadata.getName().trim());
            }
            basicWidget = new BasicWidget(new SelectionContainerWidget());
            
        } else { //default to text box
            
            basicWidget = new BasicWidget(config != null && config.canEdit ? new KSTextBox() : new KSLabel());
            GWT.log("KSTextBox for " + inLookupMetadata.getSearchTypeId(), null);
            layout.add(basicWidget.get());
            
        }
        
    }
    
    private void setupAdvancedSearch(List<LookupMetadata> additionalLookupMetadata) {
        
        //setup advanced search widget such as advanced search box, browse hierarchy search box etc.
        List<LookupMetadata> advancedLightboxLookupdata = getLookupMetadataBasedOnWidget(additionalLookupMetadata, LookupMetadata.Widget.ADVANCED_LIGHTBOX);
        if ((advancedLightboxLookupdata != null) && config.canEdit) {    
            
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
    
    private class BasicWidget extends Widget implements HasDataValue, HasFocusLostCallbacks, TranslatableValueWidget {
		private Widget basicWidget;
		
		public BasicWidget(Widget basicWidget){
			this.basicWidget = basicWidget;			
		}
		
		public void setResults(List<SelectedResults> results) {
			if (basicWidget instanceof KSTextBox) {
				((KSTextBox)basicWidget).setText(results.get(0).getDisplayKey());  //FIXME: what about the result id?
			} else if (basicWidget.getClass().getName().contains("ContainerWidget")) {
				List<String> selections = new ArrayList<String>();
				for (SelectedResults result: results) {
					selections.add(result.getDisplayKey());						  //FIXME: what about the result ids?
				}				
				((SelectionContainerWidget) basicWidget).setSelections(selections);
			} else if (basicWidget instanceof KSSuggestBox) {
                IdableSuggestion theSuggestion = new IdableSuggestion();
                theSuggestion.setReplacementString(results.get(0).getDisplayKey());
                theSuggestion.setId(results.get(0).getReturnKey());     				
				((KSSuggestBox) basicWidget).setValue(theSuggestion);
			}				
		}
		
		public void setValue(String id, String translation) {
			if(basicWidget instanceof KSTextBox) {
				((KSTextBox)basicWidget).setText(translation);
			} else if(basicWidget instanceof KSSuggestBox) {
				((KSSuggestBox) basicWidget).setValue(id, translation);
			} else if(basicWidget instanceof KSLabel) {
			    ((KSLabel)basicWidget).setText(translation);
			} else if(basicWidget instanceof KSDropDown) {
                ((KSDropDown)basicWidget).selectItem(id);
            } else {
				((KSSuggestBox) basicWidget).setValue("", true);
			}
		}		
		
		public void setValue(final Value value, boolean fireEvents) {
			if (basicWidget instanceof KSTextBox) {
				if(value != null){
					((KSTextBox)basicWidget).setText((String) value.get());
				}
				else{
					((KSTextBox)basicWidget).setText("");
				}
			} else if (basicWidget instanceof KSSuggestBox) {
				//Do check here
				if(value != null){
					if(!config.isRepeating){
						((KSSuggestBox) basicWidget).setValue((String)value.get(), fireEvents);
					}
					else{
						DataValue dataValue = (DataValue)value;
						Data d = dataValue.get();
						QueryPath path = QueryPath.parse("0");
						String v = d.query(path);
						((KSSuggestBox) basicWidget).setValue((String)v, fireEvents);
					}
				}
				else{
					((KSSuggestBox) basicWidget).setValue("", fireEvents);
				}
			} else if(basicWidget instanceof KSDropDown) {
			    
                //make sure the drop down has data loaded before we set the selected value
			    ListItems searchResults = ((KSDropDown)basicWidget).getListItems();
			    if (searchResults != null) {
                    if(value != null){
                        for (String id : searchResults.getItemIds()) { 
                            if (id.equals(((String) value.get()).trim())) {
                                ((KSDropDown)basicWidget).selectItem(id);
                                break;
                            }
                        }
                    } else {
                        ((KSDropDown)basicWidget).clear();
                    } 
			    } else {			    
                    ((KSDropDown)basicWidget).addWidgetReadyCallback(new Callback<Widget>() {
                        @Override
                        public void exec(Widget widget) {
                            final ListItems searchResults = ((KSSelectItemWidgetAbstract)widget).getListItems();
                
                            if(value != null){
                                for (String id : searchResults.getItemIds()) { 
                                    if (id.equals(((String) value.get()).trim())) {
                                        ((KSDropDown)basicWidget).selectItem(id);
                                        break;
                                    }
                                }
                            } else {
                                ((KSDropDown)basicWidget).clear();
                            }                                
                        }
                    });
			    }              
            }			
		}		
		
		public Value getValue() {
			if (basicWidget instanceof KSTextBox) {
				StringValue value = new StringValue(((KSTextBox)basicWidget).getText());
				return value;
			} else if (basicWidget instanceof KSSuggestBox) {
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
			}  else if (basicWidget instanceof KSDropDown) {
                StringValue value = new StringValue(((KSDropDown) basicWidget).getSelectedItem());
                return value;			    
			}
			
			return null;			
		}
		
		public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
			if (basicWidget instanceof KSTextBox) {
				return ((KSTextBox)basicWidget).addValueChangeHandler(handler);
			} else if (basicWidget instanceof KSSuggestBox) {
				return ((KSSuggestBox) basicWidget).addValueChangeHandler(handler);
			}   	
			return null;
		}		
		
        public void addValuesChangeHandler(ValueChangeHandler<List<String>> handler) {
            if (basicWidget.getClass().getName().contains("ContainerWidget")) {
                ((SelectionContainerWidget) basicWidget).addValuesChangeHandler(handler);
            }
        }
		public void addSelectionChangeHandler(SelectionChangeHandler handler) {
		    if (basicWidget instanceof KSDropDown)  {
				((KSDropDown) basicWidget).addSelectionChangeHandler(handler);
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
		public void addFocusLostCallback(final Callback<Boolean> callback) {
			if (basicWidget instanceof KSTextBox) {
				((KSTextBox)basicWidget).addBlurHandler(new BlurHandler(){
					@Override
					public void onBlur(BlurEvent event) {
						callback.exec(true);
						
					}
				});
			} else if (basicWidget instanceof KSSuggestBox) {
				((KSSuggestBox) basicWidget).getTextBox().addBlurHandler(new BlurHandler(){
                    @Override
                    public void onBlur(BlurEvent event) {
                        callback.exec(true);  
                    }
				});
			} else if (basicWidget instanceof KSDropDown) {
                ((KSDropDown) basicWidget).addBlurHandler(new BlurHandler(){
                    @Override
                    public void onBlur(BlurEvent event) {
                        callback.exec(true);
                    }
                });
            }   			
		}
		
        @Override
        public void setValue(Value value) {
            this.setValue(value, true);
        }   		
		
        public Widget get() {
            return basicWidget;
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
    
    private SearchRequest initializeSearchRequest(LookupMetadata lookup) {
        
        SearchRequest sr = new SearchRequest();
        List<SearchParam> params = new ArrayList<SearchParam>();
        
        sr.setSearchKey(lookup.getSearchTypeId());

        //initialize search parameters that are hidden from the UI because they are set to default context specific values
        for(LookupParamMetadata metaParam: lookup.getParams()){
            if(metaParam.getWriteAccess() == WriteAccess.NEVER){
                if ((metaParam.getDefaultValueString() == null || metaParam.getDefaultValueString().isEmpty())&&
                    (metaParam.getDefaultValueList() == null || metaParam.getDefaultValueList().isEmpty())) {
                    //FIXME throw an exception?
                    GWT.log("Key = " + metaParam.getKey() + " has write access NEVER but has no default value!", null);
                    continue;
                }
                SearchParam param = new SearchParam();
                param.setKey(metaParam.getKey());
                if(metaParam.getDefaultValueList()==null){
                    param.setValue(metaParam.getDefaultValueString());
                }else{
                    param.setValue(metaParam.getDefaultValueList());
                }
                params.add(param);
            }
            else if(metaParam.getWriteAccess() == WriteAccess.WHEN_NULL){
                if((metaParam.getDefaultValueString() != null && !metaParam.getDefaultValueString().isEmpty())||
                   (metaParam.getDefaultValueList() != null && !metaParam.getDefaultValueList().isEmpty())){
                    SearchParam param = new SearchParam();
                    param.setKey(metaParam.getKey());
                    if(metaParam.getDefaultValueList()==null){
                        param.setValue(metaParam.getDefaultValueString());
                    }else{
                        param.setValue(metaParam.getDefaultValueList());
                    }
                    params.add(param);
                }
            }
        }
        sr.setParams(params);
        
        return sr;
    }
    
    
    //TODO use for labels lookup
    private String getLabel(String labelKey) {
      //  return Application.getApplicationContext().getUILabel(messageGroup, type, state, labelKey);
    	return null;
    }
    
    public AdvancedSearchWindow getSearchWindow(){
        return advSearchWindow;
    }
    
    public void addBasicSelectionCompletedCallback(Callback<SelectedResults> callback) {
        basicSelectionCallbacks.add(callback);
    }
    
    public void addBasicSelectionTextChangeCallback(Callback<String> callback) {
        basicSelectionTextChangeCallbacks.add(callback);
    }

	@Override
	public void addValueChangeCallback(Callback<Value> callback) {
		basicWidget.addValueChangeCallback(callback);
	}

	@Override
	public void setValue(Value value) {
		setValue(value, true);	
	}
	
    public void setValue(String value){
        basicWidget.setValue(new StringValue(value));
    }	
	
	public void setValue(Value value, boolean fireEvents) {
		//suggest.reset();
		basicWidget.setValue(value, fireEvents);
	}

    @Override
    public void setValue(String id, String translation) {
        basicWidget.setValue(id, translation);      
    }		

    @Override
    public Value getValue() {
        return basicWidget.getValue();
    }	
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
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
