package org.kuali.student.common.ui.client.widgets.filter;

import java.util.ArrayList;
import java.util.List;

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
    List<KSCheckBox> filterItems = new ArrayList<KSCheckBox>();
        
    private int itemsIntializing = 0;
    private boolean isInitialSelect = true;

    private SearchRpcServiceAsync searchRpcService = GWT.create(SearchRpcService.class);
    
    public KSFilterOptions(List<LookupMetadata> lookups){
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
				for (KSCheckBox checkbox:filterItems){
					checkbox.setValue(false);
					KSFilterOptions.this.fireEvent(new FilterResetEvent());
					isInitialSelect = true;
				}
			}        	
        });
        init(lookups);
        this.initWidget(filterContainer);
    }
    
    protected void init(List<LookupMetadata> lookups){
    	for (LookupMetadata lookup:lookups){
    		itemsIntializing ++;
    		KSFilterItem filterItem = new KSFilterItem(lookup);
    		filterPanel.add(filterItem);
    	}    	
    }
        
    public void addFilterEventHandler(FilterEventHandler handler){
    	this.addHandler(handler, FilterEvent.TYPE);
    }

    public void addFilterResetEventHandler(FilterResetEventHandler handler){
    	this.addHandler(handler, FilterResetEvent.TYPE);
    }

    private class KSFilterItem extends CollapsablePanel{
    	SpanPanel itemContent;
    	String itemKey;
    	
    	public KSFilterItem (final LookupMetadata lookup){
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
		                final KSCheckBox checkbox = new KSCheckBox(items.getItemText(id));
		                checkbox.setFormValue(id);
		                filterItems.add(checkbox);
		                itemContent.add(checkbox);
		                
		                checkbox.addValueChangeHandler(new ValueChangeHandler<Boolean>(){

							@Override
							public void onValueChange(ValueChangeEvent<Boolean> event) {
								KSFilterOptions.this.fireEvent(new FilterEvent(checkbox.getValue(), isInitialSelect, itemKey, checkbox.getFormValue()));
								isInitialSelect = false;
							}
		                	
		                });
		            }
					itemsIntializing--;
				}
    		});
    		    		
    	}
    }

}
