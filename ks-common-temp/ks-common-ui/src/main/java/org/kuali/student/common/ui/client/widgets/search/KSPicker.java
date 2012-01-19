/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.LookupMetadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.assembly.data.Data.DataValue;
import org.kuali.student.common.assembly.data.Data.StringValue;
import org.kuali.student.common.assembly.data.Data.Value;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.WidgetConfigInfo;
import org.kuali.student.common.ui.client.configurable.mvc.binding.SelectItemWidgetBinding;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.HasCrossConstraints;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.mvc.HasFocusLostCallbacks;
import org.kuali.student.common.ui.client.mvc.TranslatableValueWidget;
import org.kuali.student.common.ui.client.service.CachingSearchService;
import org.kuali.student.common.ui.client.util.SearchUtils;
import org.kuali.student.common.ui.client.util.SearchUtils.SearchRequestWrapper;
import org.kuali.student.common.ui.client.widgets.HasInputWidget;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSErrorDialog;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.common.ui.client.widgets.list.KSRadioButtonList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SearchResultListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.common.ui.client.widgets.suggestbox.IdableSuggestOracle.IdableSuggestion;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;

public class KSPicker extends Composite implements HasFocusLostCallbacks, HasValueChangeHandlers<String>, HasDataValue, TranslatableValueWidget, HasInputWidget, HasCrossConstraints {

    private FlowPanel layout = new FlowPanel();
    private BasicWidget basicWidget;
    private Anchor advSearchLink = new Anchor(getMessage("advSearch"));
    private AdvancedSearchWindow advSearchWindow = null;
    private SearchPanel searchPanel;
    public SearchPanel getSearchPanel() {
		return searchPanel;
	}

	private WidgetConfigInfo config;
    private Callback<List<SelectedResults>> advancedSearchCallback;
	private List<Callback<SelectedResults>> basicSelectionCallbacks =
        new ArrayList<Callback<SelectedResults>>();
    private List<Callback<String>> basicSelectionTextChangeCallbacks =
        new ArrayList<Callback<String>>();
    private CachingSearchService cachingSearchService = CachingSearchService.getSearchService();
    
    private SearchRequestWrapper searchRequestWrapper = new SearchRequestWrapper();
    
    public KSPicker(WidgetConfigInfo config) {
        this.config = config;
		init(config.lookupMeta, config.additionalLookups);
	}

    public KSPicker(LookupMetadata inLookupMetadata, List<LookupMetadata> additionalLookupMetadata){
    	init(inLookupMetadata, additionalLookupMetadata);
    }

    @Override
    public Widget getInputWidget(){
    	if(basicWidget != null){
    		return basicWidget.get();
    	}
    	return null;

    }

    private void init(LookupMetadata inLookupMetadata, List<LookupMetadata> additionalLookupMetadata) {
    	this.initWidget(layout);
        if (inLookupMetadata == null) {
            KSErrorDialog.show(new Throwable(getMessage("invalidLookupConfig")));
            return;
        }

        if(config == null) {
            config = new WidgetConfigInfo();
        }

        setupBasicSearch(inLookupMetadata);
        setupAdvancedSearch(additionalLookupMetadata);
    }

    private static String getMessage(final String messageId) {
        return Application.getApplicationContext().getMessage(messageId);
    }

    private void setupBasicSearch(LookupMetadata inLookupMetadata) {

    	//setup initial search widget such as suggest box, drop down etc.
    	if (inLookupMetadata.getWidget() != null){
	    	switch (inLookupMetadata.getWidget()){
	    		case SUGGEST_BOX:
	    			setupSuggestBox(inLookupMetadata);
	    			break;
	    		case DROP_DOWN:
	    		case CHECKBOX_LIST:
	    			setupListWidget(inLookupMetadata);
	    			break;
	    		case RADIO:
	    			setupListWidget(inLookupMetadata);
	    			break;
	    		case NO_WIDGET:
	                if ((inLookupMetadata.getName() != null) && (inLookupMetadata.getName().trim().length() > 0)) {
	                    advSearchLink.setText(inLookupMetadata.getName().trim());
	                }
	                basicWidget = new BasicWidget(new SelectionContainerWidget());
	                break;
	    		default:
	    			setupDefaultWidget(inLookupMetadata);
	    	}
    	} else {
			setupDefaultWidget(inLookupMetadata);
    	}
    }

    private void setupDefaultWidget(LookupMetadata inLookupMetadata){
        basicWidget = new BasicWidget(config != null && config.canEdit ? new KSTextBox() : new KSLabel());
        GWT.log("KSTextBox for " + inLookupMetadata.getSearchTypeId(), null);
        layout.add(basicWidget.get());
    }

    private void setupSuggestBox(LookupMetadata inLookupMetadata){
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
    }

    private void setupListWidget(LookupMetadata inLookupMetadata){

        //FIXME should we search on values to populate drop down here or later when user will access the screen?
        if(config.canEdit) {

            KSSelectItemWidgetAbstract listItemWidget = null;
            switch (inLookupMetadata.getWidget()){
            	case DROP_DOWN:
            		listItemWidget = new KSDropDown();
            		break;
            	case CHECKBOX_LIST:
            		listItemWidget = new KSCheckBoxList();
            		((KSCheckBoxList)listItemWidget).setIgnoreMultipleAttributes(true);
            		break;
            	case RADIO:
            		listItemWidget = new KSRadioButtonList();
            		break;
            }
            basicWidget = new BasicWidget(listItemWidget);
            SearchUtils.initializeSearchRequest(inLookupMetadata, searchRequestWrapper);
            if(!searchRequestWrapper.isDeferSearch()) populateListWidget(searchRequestWrapper.getSearchRequest());
        } else {
        	if (inLookupMetadata.getWidget() == LookupMetadata.Widget.DROP_DOWN || inLookupMetadata.getWidget() == LookupMetadata.Widget.RADIO){
                basicWidget = new BasicWidget(new KSLabel());
        	} else {
        		//FIXME: This method of creating read is very inefficient, need better solution
        		basicWidget = new BasicWidget(new KSLabelList());
                SearchUtils.initializeSearchRequest(inLookupMetadata, searchRequestWrapper);
                if(!searchRequestWrapper.isDeferSearch()) populateListWidget(searchRequestWrapper.getSearchRequest());
        	}
            layout.add(basicWidget.get());
        }
    }

    private void setupAdvancedSearch(List<LookupMetadata> additionalLookupMetadata) {

        //setup advanced search widget such as advanced search box, browse hierarchy search box etc.
        List<LookupMetadata> advancedLightboxLookupdata = getLookupMetadataBasedOnWidget(additionalLookupMetadata, LookupMetadata.Widget.ADVANCED_LIGHTBOX);
        if ((advancedLightboxLookupdata != null) && config.canEdit) {

            //for multiple searches, show a drop down for user to select from
            if (advancedLightboxLookupdata.size() == 1) {
                String actionLabel = advancedLightboxLookupdata.get(0).getWidgetOptionValue(LookupMetadata.WidgetOption.ADVANCED_LIGHTBOX_ACTION_LABEL);
                searchPanel = new SearchPanel(advancedLightboxLookupdata.get(0));
                searchPanel.setActionLabel(actionLabel);
                advSearchWindow = new AdvancedSearchWindow("Advanced Search: " + advancedLightboxLookupdata.get(0).getTitle(), searchPanel);
            } else {
                searchPanel = new SearchPanel(advancedLightboxLookupdata);
                advSearchWindow = new AdvancedSearchWindow("Advanced Search: " + advancedLightboxLookupdata.get(0).getTitle(), searchPanel);
                searchPanel.addLookupChangedCallback(new Callback<LookupMetadata>() {
                    @Override
                    public void exec(LookupMetadata selectedLookup) {
                        String actionLabel = (selectedLookup == null)? null : selectedLookup
                                .getWidgetOptionValue(LookupMetadata.WidgetOption.ADVANCED_LIGHTBOX_ACTION_LABEL);
                        searchPanel.setActionLabel(actionLabel);
                    }
                });
                LookupMetadata initialLookupMetaData = advancedLightboxLookupdata.get(0);
                String actionLabel = (initialLookupMetaData == null)? null : initialLookupMetaData
                        .getWidgetOptionValue(LookupMetadata.WidgetOption.ADVANCED_LIGHTBOX_ACTION_LABEL);
                searchPanel.setActionLabel(actionLabel);
            }
            searchPanel.setMultiSelect(true);

            /*
            advSearchWindow.addActionCompleteCallback(new Callback<Boolean>() {
                @Override
                public void exec(Boolean result) {
                    searchPanel.getActionCompleteCallback().exec(true);
                }
            }); */

            String previewMode = additionalLookupMetadata.get(0).getWidgetOptionValue(LookupMetadata.WidgetOption.ADVANCED_LIGHTBOX_PREVIEW_MODE);
            if (previewMode != null && previewMode.equals("true")) {
                searchPanel.setActionLabel("Preview");
            }

            searchPanel.addSelectionCompleteCallback(new Callback<List<SelectedResults>>() {
                public void exec(List<SelectedResults> results) {
                	if (results != null && results.size() > 0) {
	                    if (advancedSearchCallback != null) {
	                        advancedSearchCallback.exec(results);
	                    }
	                    else {
	                        basicWidget.setResults(results);
	                    }
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

    private void populateListWidget(SearchRequest sr){
        
        cachingSearchService.search(sr, new KSAsyncCallback<SearchResult>(){

            @Override
            public void onSuccess(SearchResult results) {
                if(results != null){
                    ((KSSelectItemWidgetAbstract)basicWidget.get()).setListItems(new SearchResultListItems(results.getRows(), config.lookupMeta));
                } else {
                    ((KSSelectItemWidgetAbstract)basicWidget.get()).setListItems(new SearchResultListItems(null, config.lookupMeta));
                    //FIXME is this configuration error?
                }
                //((KSDropDown)basicWidget.get()).processOutstandingCallback();  //due to possible race condition
                ((KSSelectItemWidgetAbstract)basicWidget.get()).setInitialized(true);
                layout.add(basicWidget.get());
            }
        });
    }

	private List<LookupMetadata> getLookupMetadataBasedOnWidget(List<LookupMetadata> additionalLookupMetadata, LookupMetadata.Widget widgetType) {

	    //lookup does not need to have additional lookup e.g. if the lookup is for suggest box within advanced search lightbox
	    if (additionalLookupMetadata == null) {
	        return null;
	    }

    	List<LookupMetadata> lookups = new ArrayList<LookupMetadata>();
    	for (LookupMetadata addLookupData : additionalLookupMetadata) {
    		if (addLookupData.getWidget() == widgetType) {
    			lookups.add(addLookupData);
    		}
    	}
    	return (lookups.size() > 0 ? lookups : null);
    }

    public class BasicWidget implements HasDataValue, HasFocusLostCallbacks, TranslatableValueWidget {
		private Widget basicWidget;

		public BasicWidget(Widget basicWidget){
			this.basicWidget = basicWidget;
            initAccessibility();
		}

        private void initAccessibility() {
            Element element = basicWidget.getElement();
            element.setAttribute("role", "combobox");
            element.setAttribute("aria-autocomplete", "list");
            element.setAttribute("aria-haspopup", "true");
        }

        public void setResults(List<SelectedResults> results) {
			if (basicWidget instanceof KSTextBox) {
				((KSTextBox)basicWidget).setText(results.get(0).getDisplayKey());  //FIXME: what about the result id?
			} else if (basicWidget.getClass().getName().contains("ContainerWidget")) {
				List<String> selections = new ArrayList<String>();
				for (SelectedResults result: results) {
					//selections.add(result.getDisplayKey());						  //FIXME: what about the result ids?
					selections.add(result.getReturnKey());                            //we don't need display key, at least for now
				}
				((SelectionContainerWidget) basicWidget).setSelections(selections);
			} else if (basicWidget instanceof KSSuggestBox) {
                IdableSuggestion theSuggestion = new IdableSuggestion();
                theSuggestion.setReplacementString(results.get(0).getDisplayKey());
                theSuggestion.setId(results.get(0).getReturnKey());
				((KSSuggestBox) basicWidget).setValue(theSuggestion);
			}
		}
		public void clear() {
		    if(basicWidget instanceof KSTextBox) {
                ((KSTextBox)basicWidget).setText(null);
            } else if(basicWidget instanceof KSSuggestBox) {
                ((KSSuggestBox) basicWidget).setValue((String)null);
            } else if(basicWidget instanceof KSLabel) {
                ((KSLabel)basicWidget).setText(null);
            } else if(basicWidget instanceof KSDropDown) {
                ((KSDropDown)basicWidget).clear();
            } else {
                ((KSSuggestBox) basicWidget).setValue(null, false);
            }
		}
		public void setValue(final String id, String translation) {
			if(basicWidget instanceof KSTextBox) {
				((KSTextBox)basicWidget).setText(translation);
			} else if(basicWidget instanceof KSSuggestBox) {
				((KSSuggestBox) basicWidget).setValue(id, translation);
			} else if(basicWidget instanceof KSLabel) {
			    ((KSLabel)basicWidget).setText(translation);
			} else if(basicWidget instanceof KSSelectItemWidgetAbstract) {
			    ListItems searchResults = ((KSSelectItemWidgetAbstract)basicWidget).getListItems();
			    if (searchResults != null) {
			        ((KSSelectItemWidgetAbstract)basicWidget).selectItem(id);
			    }
			    else {
                    ((KSSelectItemWidgetAbstract)basicWidget).addWidgetReadyCallback(new Callback<Widget>() {
                        @Override
                        public void exec(Widget widget) {
                            final ListItems searchResults = ((KSSelectItemWidgetAbstract)widget).getListItems();

                            if(id != null){
                                for (String itemId : searchResults.getItemIds()) {
                                    if (itemId.equals(id.trim())) {
                                        ((KSSelectItemWidgetAbstract)basicWidget).selectItem(itemId);
                                        break;
                                    }
                                }
                            } else {
                                ((KSSelectItemWidgetAbstract)basicWidget).clear();
                            }
                        }
                    });
                }

            } else {
				((KSSuggestBox) basicWidget).setValue("", true);
			}
		}

		public void setValue(final Value value, boolean fireEvents) {
			if (basicWidget instanceof KSTextBox || basicWidget instanceof KSLabel) {
				if(value != null){
					((HasText)basicWidget).setText((String) value.get());
				}
				else{
					((HasText)basicWidget).setText("");
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
			} else if(basicWidget instanceof KSSelectItemWidgetAbstract) {
				SelectItemWidgetBinding.INSTANCE.setWidgetValue((KSSelectItemWidgetAbstract)basicWidget, value);
            }
		}
		public String getDisplayValue() {
		    String result = "";
		    if (basicWidget instanceof KSTextBox) {
                result = ((KSTextBox)basicWidget).getText();
            } else if (basicWidget instanceof KSSuggestBox) {
                IdableSuggestion suggestion = ((KSSuggestBox)basicWidget).getCurrentSuggestion();
                if(suggestion != null) {
                    result = suggestion.getReplacementString();
                }
            }  else if (basicWidget instanceof KSDropDown) {
                KSDropDown dropDown = (KSDropDown)basicWidget;
                StringValue value = new StringValue(((KSDropDown) basicWidget).getSelectedItem());
                String itemId = dropDown.getSelectedItem();

                if(itemId != null && !itemId.isEmpty()) {
                    result = dropDown.getListItems().getItemText(itemId);
                }
            }
		    return result;
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
			}  else if (basicWidget instanceof KSSelectItemWidgetAbstract) {
				return SelectItemWidgetBinding.INSTANCE.getWidgetValue((KSSelectItemWidgetAbstract)basicWidget);
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
                ((SelectionContainerWidget) basicWidget).addValueChangeHandler(handler);
            }
        }
		public void addSelectionChangeHandler(SelectionChangeHandler handler) {
		    if (basicWidget instanceof KSSelectItemWidgetAbstract)  {
				((KSSelectItemWidgetAbstract) basicWidget).addSelectionChangeHandler(handler);
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
                    	//Don't blur the widget if the suggestion list is showing
                    	if(!((KSSuggestBox) basicWidget).isSuggestionListShowing()){
                            callback.exec(true);
                    	}
                    }
				});
			} else if (basicWidget instanceof KSSelectItemWidgetAbstract) {
                ((KSSelectItemWidgetAbstract) basicWidget).addBlurHandler(new BlurHandler(){
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

        @Override
        public void setValue(final Map<String, String> translations) {
        	//TODO: Reviewed in M6 cleanup, unknown:  Update to also work with a KSLabelList that doesn't require pre-population of all list items
        	if (basicWidget instanceof KSSelectItemWidgetAbstract){
        		Callback<Widget> widgetReadyCallback = new Callback<Widget>(){
					public void exec(Widget widget) {
						((KSSelectItemWidgetAbstract)widget).clear();
		        		for (String id:translations.keySet()){
		        			((KSSelectItemWidgetAbstract)widget).selectItem(id);
		        		}
					}
        		};
        		if (!((KSSelectItemWidgetAbstract)basicWidget).isInitialized()){
        			((KSSelectItemWidgetAbstract)basicWidget).addWidgetReadyCallback(widgetReadyCallback);
        		} else{
        			widgetReadyCallback.exec(basicWidget);
        		}
        	} else {
        		GWT.log("Basic picker widget not coded to handle multiple translations", null);
        	}
        }

    }

    private class SelectionContainerWidget extends Widget implements HasValueChangeHandlers<List<String>> {
    	private List<String> selections = new ArrayList<String>();

    	public SelectionContainerWidget(){
    		this.setElement(DOM.createSpan());
    	}

		public List<String> getSelections() {
			return selections;
		}

		public void setSelections(List<String> selections) {
			this.selections = selections;
			ValueChangeEvent.fire(this, this.selections);
		}

		@Override
		public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<String>> handler) {
			return super.addHandler(handler, ValueChangeEvent.getType());
		}
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
	public void clear() {
	    basicWidget.clear();
	}

    @Override
    public void setValue(String id, String translation) {
        basicWidget.setValue(id, translation);
    }

    @Override
    public Value getValue() {
        return basicWidget.getValue();
    }
	public String getDisplayValue() {
	    return basicWidget.getDisplayValue();
	}
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return basicWidget.addValueChangeHandler(handler);
	}

	public void addValuesChangeHandler(ValueChangeHandler<List<String>> handler) {
        if(basicWidget != null)
		basicWidget.addValuesChangeHandler(handler);
	}

	public void addSelectionChangeHandler(SelectionChangeHandler handler) {
        if(basicWidget != null)
		basicWidget.addSelectionChangeHandler(handler);
	}

	
	@Override
	public void addFocusLostCallback(Callback<Boolean> callback) {
		basicWidget.addFocusLostCallback(callback);
	}

    public void setAdvancedSearchCallback(Callback<List<SelectedResults>> advancedSearchCallback) {
        this.advancedSearchCallback = advancedSearchCallback;
    }

    @Override
    public void setValue(Map<String, String> translations) {
        basicWidget.setValue(translations);
    }

	@Override
    public HashSet<String> getCrossConstraints() {
		return searchRequestWrapper.getCrossConstraints();
	}

	@Override
	public void reprocessWithUpdatedConstraints() {
        SearchUtils.initializeSearchRequest(config.lookupMeta, searchRequestWrapper);
        populateListWidget(searchRequestWrapper.getSearchRequest());
	}

}
