package org.kuali.student.common.ui.client.widgets.search;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.DefaultWidgetFactory;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.LookupParamMetadata;
import org.kuali.student.core.assembly.data.Data.Value;
import org.kuali.student.core.assembly.data.Metadata.WriteAccess;
import org.kuali.student.core.search.newdto.SearchParam;
import org.kuali.student.core.search.newdto.SearchRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class SearchPanel extends Composite{
	
	//Layout configuration
	private VerticalFlowPanel layout = new VerticalFlowPanel();
	private SimplePanel searchCriteriaPanel = new SimplePanel();
	private VerticalFlowPanel tablePanel = new VerticalFlowPanel();
	private HorizontalBlockFlowPanel criteria = new HorizontalBlockFlowPanel();
	private CollapsablePanel modifySearchPanel;
	private boolean resultsShown = false;
	
	//Search data
	private LookupMetadata lookupMetadata;
    private TempSearchBackedTable table = null;				//FIXME: Swap with new table implementation
	private static enum SearchStyle{ADVANCED, CUSTOM};
	
	//TODO messages
	private String advInstructions = "Enter one or more fields";
	private KSLabel searchCriteria = new KSLabel("Search Criteria:");
	private boolean multiSelect = false;
	
	private class ParamListItems implements ListItems{

		private List<LookupParamMetadata> params = new ArrayList<LookupParamMetadata>();
		
		public ParamListItems(LookupMetadata meta){
			params = meta.getParams();
		}
		
		@Override
		public List<String> getAttrKeys() {
			return new ArrayList<String>();
		}

		@Override
		public String getItemAttribute(String id, String attrkey) {
			return "";
		}

		@Override
		public int getItemCount() {
			return params.size();
		}

		@Override
		public List<String> getItemIds() {
			List<String> ids = new ArrayList<String>();
			for(LookupParamMetadata param: params){
				ids.add(param.getKey());
			}
			return ids;
		}

		@Override
		public String getItemText(String id) {
			String itemText = id;
			for(LookupParamMetadata param: params){
				if(param.getKey().equals(id)){
					//TODO this should be a message key
					itemText = param.getName();
					break;
				}
				
			}
			return itemText;
		}
		
		public Widget getWidget(String id){
			Widget w = null;
			for(LookupParamMetadata param: params){
				if(param.getKey().equals(id)){
					w = DefaultWidgetFactory.getInstance().getWidget(param);
					break;
				}
				
			}
			return w;
		}
		
	}
	
	public SearchPanel(LookupMetadata meta){
		lookupMetadata = meta;		
		layout.add(createSearchParamPanel(meta));
		table = new TempSearchBackedTable(meta.getResults(), meta.getResultReturnKey());		
		tablePanel.add(searchCriteria);
		tablePanel.add(criteria);
		//TODO new table goes here
		tablePanel.add(table);
		layout.add(tablePanel);
		tablePanel.setVisible(false);
		this.initWidget(layout);
	}
	
	//TODO constructor for list of lookupMeata, is it always a single searchrpc class?
	public SearchPanel(List<LookupMetadata> metas){
		//lookupMetadata = metas;		
		
		LinkedHashMap<String, Widget> searches = new LinkedHashMap<String, Widget>(); 
		for(LookupMetadata meta: metas){
			//TODO check for default (?)
			searches.put(meta.getKey(), createSearchParamPanel(meta));
		}
		SwappablePanel swapPanel = new SwappablePanel(searches);
		searchCriteriaPanel.setWidget(swapPanel);
		layout.add(searchCriteriaPanel);
		tablePanel.add(searchCriteria);
		tablePanel.add(criteria);
		//TODO new table goes here
		//tablePanel.add(table);
		layout.add(tablePanel);
		tablePanel.setVisible(false);
		this.initWidget(layout);
	}
	
	private Widget createSearchParamPanel(LookupMetadata meta){
		ParamListItems listItems = new ParamListItems(meta);
		LinkPanel panel = new LinkPanel(SearchStyle.ADVANCED, new AdvancedSearch(meta));
		//TODO use messages here and link styling
		panel.addLinkToPanel(SearchStyle.ADVANCED, "Customize this Search", SearchStyle.CUSTOM);
		panel.addPanel(SearchStyle.CUSTOM, new CustomizedSearch(meta, listItems));
		panel.addLinkToPanel(SearchStyle.CUSTOM, "Return to Advanced", SearchStyle.ADVANCED);
		return panel;
	}
	
	private class CustomizedSearch extends Composite{
		
		private List<CustomLine> lines = new ArrayList<CustomLine>();
		private VerticalFlowPanel layout = new VerticalFlowPanel();
		private VerticalFlowPanel linePanel = new VerticalFlowPanel();
		private VerticalFlowPanel buttonPanel = new VerticalFlowPanel();
		
		public CustomizedSearch(final LookupMetadata meta, final ParamListItems listItems){
			layout.add(linePanel);
			CustomLine line = new CustomLine(meta, listItems);
			linePanel.add(line);
			lines.add(line);

			KSButton addCriteria = new KSButton("+ Add Criteria");
			addCriteria.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					CustomLine line = new CustomLine(meta, listItems);
					linePanel.add(line);
					lines.add(line);
				}
			});
			layout.add(addCriteria);
			
			KSButton searchButton = new KSButton("Search");
			searchButton.setPrimary(true);
			searchButton.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					//Create search request and then pass it to the table
					//TODO pass search to the table
					SearchRequest sr = new SearchRequest();
					List<SearchParam> params = new ArrayList<SearchParam>();
					List<HasSearchParam> userCriteria = new ArrayList<HasSearchParam>();
					for(CustomLine field: lines){
						SearchParam param = field.getSearchParam();
						//TODO is this check needed here? probably. assuming string here
						if((param.getValue() != null)){
							params.add(param);
							userCriteria.add(field);
						}
					}
					for(LookupParamMetadata metaParam: meta.getParams()){
						
						if(metaParam.getWriteAccess() == WriteAccess.NEVER){
							SearchParam param = new SearchParam();
							param.setKey(metaParam.getKey());
							param.setValue(metaParam.getDefaultValue().toString());
							params.add(param);
						}
						else if(metaParam.getWriteAccess() == WriteAccess.WHEN_NULL){
							if(metaParam.getDefaultValue() != null && !(metaParam.getDefaultValue().toString().isEmpty())){
								SearchParam param = new SearchParam();
								param.setKey(metaParam.getKey());
								param.setValue(metaParam.getDefaultValue().toString());
								params.add(param);
							}
						}
					}
					sr.setParams(params);
					//TODO remove this
					for(SearchParam p: params){
						GWT.log("Key = " + p.getKey() + "  Value = " + p.getValue().toString(), null);
					}
					sr.setSearchKey(meta.getKey());
					
					//TODO remove this
					if(table != null){
						table.performSearch(sr);
					}
					tablePanel.setVisible(true);
					showCriteriaChosen(userCriteria);
					if(!resultsShown){
						searchCriteriaPanel.removeFromParent();
						modifySearchPanel = new CollapsablePanel("Modify Search", searchCriteriaPanel, false);
						SearchPanel.this.layout.insert(modifySearchPanel, 0);
					}
					else{
						modifySearchPanel.close();
					}
					resultsShown = true;
				}
			});
			layout.add(searchButton);
			this.initWidget(layout);
		}
	}
		
	private interface HasSearchParam{
		public SearchParam getSearchParam();
		public String getFieldName();
	}
	
	private class CustomLine extends Composite implements HasSearchParam{
		private KSDropDown paramSelector = new KSDropDown();
		private SimplePanel widgetPanel = new SimplePanel();
		private Widget widget = null;
		private String key;
		private HorizontalBlockFlowPanel layout = new HorizontalBlockFlowPanel();
		private ParamListItems listItems;
		
		public CustomLine(LookupMetadata meta, final ParamListItems listItems){
			this.listItems = listItems;
			paramSelector.setBlankFirstItem(false);
			paramSelector.setListItems(listItems);
			String id = meta.getParams().get(0).getKey();
			paramSelector.selectItem(id);
			widget = listItems.getWidget(id);
			key = id;
			widgetPanel.setWidget(widget);
			paramSelector.addSelectionChangeHandler(new SelectionChangeHandler(){

				@Override
				public void onSelectionChange(SelectionChangeEvent event) {
					String id = ((KSSelectItemWidgetAbstract)event.getWidget()).getSelectedItem();
					widget = listItems.getWidget(id);
					widgetPanel.setWidget(widget);
					key = id;
					
				}
			});
			layout.add(paramSelector);
			layout.add(widgetPanel);
			this.initWidget(layout);
		}
		
		public SearchParam getSearchParam(){
			SearchParam param = new SearchParam();
			param.setKey(key);
			if(widget instanceof HasText){
				param.setValue(((HasText) widget).getText());
			}
			else if(widget instanceof HasValue){
				Object value = ((HasValue) widget).getValue();
				//TODO need to handle date here
				if(value instanceof String){
					param.setValue((String)value);
				}
				else{
					param.setValue(value.toString());
					GWT.log("Fields in search probably(?) shouldnt have values other than string", null);
				}
			}
			else{
				param.setValue("");
			}
			return param;
		}
		
		public String getKey(){
			return key;
		}
		
		public String getFieldName(){
			String id = paramSelector.getSelectedItem();
			return listItems.getItemText(id);
		}
	}
	
	private class AdvancedSearch extends Composite{
		private List<SearchField> searchFields = new ArrayList<SearchField>();
		
		public AdvancedSearch(final LookupMetadata meta){
			VerticalFlowPanel panel = new VerticalFlowPanel();
			KSLabel instrLabel = new KSLabel(advInstructions);
			panel.add(instrLabel);
			for(LookupParamMetadata param: meta.getParams()){
				if(param.getWriteAccess() == WriteAccess.ALWAYS){
					SearchField paramField = new SearchField(param);
					searchFields.add(paramField);
					panel.add(paramField);
				}
				else if(param.getWriteAccess() == WriteAccess.WHEN_NULL){
					if(param.getDefaultValue() == null){
						SearchField paramField = new SearchField(param);
						searchFields.add(paramField);
						panel.add(paramField);
					}
				}
			}
			KSButton searchButton = new KSButton("Search");
			searchButton.setPrimary(true);
			searchButton.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					//Create search request and then pass it to the table
					//TODO pass search to the table
					SearchRequest sr = new SearchRequest();
					List<SearchParam> params = new ArrayList<SearchParam>();
					List<HasSearchParam> userCriteria = new ArrayList<HasSearchParam>();
					for(SearchField field: searchFields){
						SearchParam param = field.getSearchParam();
						//TODO is this null check needed here? probably. assuming string here
						//TODO make check more robust here/inserting params more robust
						//do not pass to the search parameters that are empty
						if ((param.getValue() != null) && (param.getValue().toString().trim().isEmpty() == false)){
							params.add(param);
							userCriteria.add(field);
						}						
					}
					
					for(LookupParamMetadata metaParam: meta.getParams()){
						if(metaParam.getWriteAccess() == WriteAccess.NEVER){
							Value defaultValue = metaParam.getDefaultValue();
							if ((defaultValue == null) || (defaultValue.toString().isEmpty())) {
								//FIXME throw an exception?
								GWT.log("Key = " + metaParam.getKey() + " has write access NEVER but has no default value!", null);
								continue;
							}
							SearchParam param = new SearchParam();
							param.setKey(metaParam.getKey());
							param.setValue(defaultValue.toString());
							params.add(param);
						}
						else if(metaParam.getWriteAccess() == WriteAccess.WHEN_NULL){
							if(metaParam.getDefaultValue() != null && !(metaParam.getDefaultValue().toString().isEmpty())){
								SearchParam param = new SearchParam();
								param.setKey(metaParam.getKey());
								param.setValue(metaParam.getDefaultValue().toString());
								params.add(param);
							}
						}
					}
					sr.setParams(params);
					//TODO remove this
					for(SearchParam p: params){
						GWT.log("Key = " + p.getKey() + "  Value = " + p.getValue().toString(), null);
					}
					sr.setSearchKey(meta.getKey());
					
					//TODO remove this
					if(table != null){
						table.performSearch(sr);
					}
					tablePanel.setVisible(true);
					showCriteriaChosen(userCriteria);
					if(!resultsShown){
						searchCriteriaPanel.removeFromParent();
						modifySearchPanel = new CollapsablePanel("Modify Search", searchCriteriaPanel, false);
						SearchPanel.this.layout.insert(modifySearchPanel, 0);
					}
					else{
						modifySearchPanel.close();
					}
					resultsShown = true;
				}
			});
			panel.add(searchButton);
			this.initWidget(panel);
		}
	}
	
	private class SearchField extends Composite implements HasSearchParam{
		
		private Widget widget = null;
		private LookupParamMetadata meta = null;
		private VerticalFlowPanel panel = new VerticalFlowPanel();
		private String fieldName;
		
		public SearchParam getSearchParam(){
			SearchParam param = new SearchParam();
			param.setKey(meta.getKey());
			if(widget instanceof HasText){
				param.setValue(((HasText) widget).getText());
			}
			else if(widget instanceof HasValue){
				Object value = ((HasValue) widget).getValue();
				if(value != null){
				//TODO need to handle date and other types here, how they are converted for search, etc
					if(value instanceof String){
						param.setValue((String)value);
					}
					else{
						param.setValue(value.toString());
						GWT.log("Fields in search probably(?) shouldnt have values other than string", null);
					}
				}
			}
			else{
				param.setValue("");
			}
			
			return param;
		}
		
		public SearchField(LookupParamMetadata param){
			meta = param;
			//TODO use message call here
			fieldName = param.getName();
			KSLabel searchParamLabel = new KSLabel(fieldName);
			widget = DefaultWidgetFactory.getInstance().getWidget(param);
			if(param.getDefaultValue() != null){
				if(widget instanceof HasText){
					((HasText) widget).setText(param.getDefaultValue().toString());
				}
				else if(widget instanceof HasValue){
					((HasValue) widget).setValue(param.getDefaultValue().get());
				}
			}
			panel.add(searchParamLabel);
			panel.add(widget);
			this.initWidget(panel);
		}
		
		public Widget getFieldPanel(){
			return panel;
		}
		
		public String getFieldName() {
			return fieldName;
		}

		
	}
	
	private void showCriteriaChosen(List<HasSearchParam> fields){
		criteria.clear();
		for(HasSearchParam field: fields){
			String name = field.getFieldName();
			//TODO Should be string only, needs type safety
			String value = field.getSearchParam().getValue().toString();
			if(!value.isEmpty()){
				HTMLPanel label = new HTMLPanel(" " + name + ": <b>" + value + "</b>");
				criteria.add(label);
			}
		}
	}
	
	public List<String> getSelectedIds(){
		List<String> ids = new ArrayList<String>();
		if(table != null){
			ids = table.getSelectedIds();
		}
		return ids;
	}
	
	public List<SelectedResults> getSelectedValues() {
				
		List<SelectedResults> selectedValues = new ArrayList<SelectedResults>();
		if (table != null) {			
			List<ResultRow> selectedRows = table.getSelectedRows();
			for (ResultRow row : selectedRows) {
				String displayKey = row.getValue(lookupMetadata.getResultDisplayKey());
				String returnKey = row.getValue(lookupMetadata.getResultReturnKey());
				selectedValues.add(new SelectedResults(displayKey, returnKey));
				if (multiSelect == false) {
					break;
				}
			}
		}
			
		return selectedValues;
	}
	
	public boolean isMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		this.multiSelect = multiSelect;
	}
}
