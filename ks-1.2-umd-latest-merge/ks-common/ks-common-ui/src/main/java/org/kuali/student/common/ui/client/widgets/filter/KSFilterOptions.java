package org.kuali.student.common.ui.client.widgets.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.data.LookupMetadata;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.service.SearchRpcService;
import org.kuali.student.common.ui.client.service.SearchRpcServiceAsync;
import org.kuali.student.common.ui.client.util.SearchUtils;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.list.SearchResultListItems;
import org.kuali.student.common.ui.client.widgets.notification.LoadingDiv;
import org.kuali.student.common.ui.client.widgets.search.CollapsablePanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;

/**
 * This widget can be used to display a list of filter items with a list of options.
 * A selection of filter option will fire a filter select/deselect event which can 
 * be inspected to update a results widget to reflect the filter option selected.
 * 
 */
public class KSFilterOptions extends Composite{
    private VerticalFlowPanel filterPanel = new VerticalFlowPanel();
    private VerticalFlowPanel filterTitlePanel = new VerticalFlowPanel();
    private KSLabel filterTitle = new KSLabel();
    private KSLabel filterDescription = new KSLabel();

    private VerticalFlowPanel filterContainer = new VerticalFlowPanel();
    private LoadingDiv loading = new LoadingDiv();
    List<KSFilterItem> filterItems = new ArrayList<KSFilterItem>();
    private int itemsIntializing = 0;
    private int itemsSelected = 0;

    private SearchRpcServiceAsync searchRpcService = GWT.create(SearchRpcService.class);
    
    public KSFilterOptions(List<LookupMetadata> lookups){
        init(lookups,null);
        this.initWidget(filterContainer);
    }
    
    public KSFilterOptions(List<LookupMetadata> lookups, Map<String,Integer> filterCount){
        init(lookups,filterCount);
        this.initWidget(filterContainer);
    }
    
    protected void init(List<LookupMetadata> lookups, Map<String,Integer> filterCount){	
    	filterTitlePanel.add(filterTitle);
        filterTitlePanel.add(filterDescription);
        filterContainer.add(filterTitlePanel);
        filterContainer.add(filterPanel);
        
        filterContainer.addStyleName("KS-Filter-Options-Parent-Container");
        filterTitlePanel.addStyleName("KS-Filter-Options-Title-Panel");

        filterTitle.addStyleName("KS-Filter-Options-Title-Label");
        filterTitle.addStyleName("KS-Indent" + "-1");

        filterDescription.addStyleName("KS-Basic-Menu-Desc-Label");
        filterDescription.addStyleName("KS-Indent" + "-1");
        filterDescription.addStyleName("KS-Indent" + "-1");
        
        KSButton resetButton = new KSButton("Reset", ButtonStyle.DEFAULT_ANCHOR);
        filterTitlePanel.add(resetButton);
        
        resetButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				for (KSFilterItem filterItem:filterItems){
					filterItem.reset();					
				}
				KSFilterOptions.this.fireEvent(new FilterResetEvent());
				itemsSelected = 0;
			}        	
        });
    	for (LookupMetadata lookup:lookups){
    		itemsIntializing ++;
    		KSFilterItem filterItem = new KSFilterItem(lookup,filterCount);
    		filterPanel.add(filterItem);
    	}    	
    }
        
    /**
     * Add a filter event handler. A filter event gets fired anytime the user checks or unchecks a filter option.
     * A filter event will not get fired if an uncheck of filter item causes results in none of the checkboxes
     * being selected, instead filter reset event will get fired.
     * 
     * @param handler
     */
    public void addFilterEventHandler(FilterEventHandler handler){
    	this.addHandler(handler, FilterEvent.TYPE);
    }

    /**
     * Add a reset handler. A reset event will get fired anytime all the filter checkboxes are not selected, either
     * via click of Reset or unchecking all checkboxes.
     * 
     * @param handler
     */
    public void addFilterResetEventHandler(FilterResetEventHandler handler){
    	this.addHandler(handler, FilterResetEvent.TYPE);
    }

    private class KSFilterItem extends CollapsablePanel{
    	SpanPanel itemContent;
    	String itemKey;
    	List<KSCheckBox> checkboxes = new ArrayList<KSCheckBox>();
    	public KSFilterItem (final LookupMetadata lookup, final Map<String,Integer> filterCount){
    		itemContent = new SpanPanel();
    		this.init(new KSLabel(lookup.getTitle()), itemContent, true, true, ImagePosition.ALIGN_LEFT);
    		this.addStyleName("KS-Filter-Item");
    		super.linkPanel.addStyleName("KS-Filter-Item-Label");
   		
    		itemKey = lookup.getId();
    		
    		SearchRequest sr = SearchUtils.initializeSearchRequest(lookup);
    		
    		searchRpcService.search(sr, new KSAsyncCallback<SearchResult>(){
				@Override
				public void onSuccess(SearchResult result) {
					SearchResultListItems items = new SearchResultListItems(result.getRows(), lookup);
					for (String id:items.getItemIds()){			
						final KSCheckBox checkbox;

						if(filterCount!=null)
						{
							if(filterCount.get(id)==null)
								checkbox = new KSCheckBox(items.getItemText(id)+" (0)");
							else
								checkbox = new KSCheckBox(items.getItemText(id)+" ("+filterCount.get(id)+")");
						}
						else
							checkbox = new KSCheckBox(items.getItemText(id));

						checkbox.setFormValue(id);
						checkboxes.add(checkbox);
						itemContent.add(checkbox);

						checkbox.addValueChangeHandler(new ValueChangeHandler<Boolean>(){

							@Override
							public void onValueChange(ValueChangeEvent<Boolean> event) {
								if (checkbox.getValue()){
									itemsSelected++;
								} else {
									itemsSelected--;
								}
								if (itemsSelected == 0){
									KSFilterOptions.this.fireEvent(new FilterResetEvent());
								} else {
									Map<String, List<String>> selectionMap = getSelectionMap();
									KSFilterOptions.this.fireEvent(new FilterEvent(selectionMap, checkbox.getValue(), itemsSelected == 1, itemKey, checkbox.getFormValue()));									
								}

							}
		                	
		                });
		            }
					itemsIntializing--;
	                filterItems.add(KSFilterItem.this);
				}
    		});    		
    	}
    	
    	/**
    	 * Reset all checkboxes for this filter item.
    	 * 
    	 */
    	public void reset(){
			for (KSCheckBox checkbox:checkboxes){
				checkbox.setValue(false);
			}
		}

    }

    /**
     * Use to reset all filter checkboxes to unselected
     */
    public void reset(){
		for (KSFilterItem filterItem:filterItems){
			filterItem.reset();
		}
		itemsSelected = 0;
    }
    
    /** 
     * @return All the currently selected filter options
     */
    protected Map<String, List<String>> getSelectionMap(){
	    Map<String, List<String>> selectionMap = new HashMap<String, List<String>>();
		for (KSFilterItem filterItem:filterItems){
			List<String> itemValues = new ArrayList<String>();
			for (KSCheckBox itemCheckbox:filterItem.checkboxes){
				if (itemCheckbox.getValue()){
					itemValues.add(itemCheckbox.getFormValue());
				}
			}
			if (!itemValues.isEmpty()){
				selectionMap.put(filterItem.itemKey, itemValues);
			}
		}
		
		return selectionMap;
    }
    
}
