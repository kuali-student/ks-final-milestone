package org.kuali.student.common.ui.client.widgets.search;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.assembly.client.LookupMetadata;
import org.kuali.student.common.assembly.client.LookupParamMetadata;
import org.kuali.student.common.assembly.client.Data.DataType;
import org.kuali.student.common.assembly.client.Metadata.WriteAccess;
import org.kuali.student.common.ui.client.configurable.mvc.DefaultWidgetFactory;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.searchtable.SearchBackedTable;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.newdto.SearchParam;
import org.kuali.student.core.search.newdto.SearchRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class SearchPanel extends Composite{
	private VerticalFlowPanel layout = new VerticalFlowPanel();
	private SimplePanel searchCriteriaPanel = new SimplePanel();
	private VerticalFlowPanel searchResultsPanel = new VerticalFlowPanel();
	private VerticalFlowPanel tablePanel = new VerticalFlowPanel();
	//Swap with new table implementation
	private SearchBackedTable table = null;
	
	//TODO messages
	private String advInstructions = "Enter one or more fields";
	private KSLabel searchCriteria = new KSLabel("Search Criteria:");
	private boolean multiSelect = false;
	private ParamListItems listItems = null;
	
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
					//TODO this should be a message key
					w = DefaultWidgetFactory.getInstance().getWidget(param);
					break;
				}
				
			}
			return w;
		}
		
	}
	
	//TODO get rid of this, only in here as intermediate solution
	@Deprecated
	public SearchPanel(BaseRpcServiceAsync searchService, LookupMetadata meta){
		listItems = new ParamListItems(meta);
		layout.add(new AdvancedSearch(null, meta));
		layout.add(new CustomizedSearch(null, meta));
		table = new SearchBackedTable(searchService, meta.getKey(), meta.getResultReturnKey());
		tablePanel.add(searchCriteria);
		tablePanel.add(table);
		layout.add(tablePanel);
		tablePanel.setVisible(false);
		this.initWidget(layout);
	}

	public SearchPanel(SearchBaseRpc search, LookupMetadata meta){
		listItems = new ParamListItems(meta);
		layout.add(new AdvancedSearch(search, meta));
		layout.add(new CustomizedSearch(search, meta));
		//TODO new table goes here
		//tablePanel.add(table);
		tablePanel.add(searchCriteria);
		layout.add(tablePanel);
		tablePanel.setVisible(false);
		this.initWidget(layout);
	}
	
	private class CustomizedSearch extends Composite{
		
		private List<CustomLine> lines = new ArrayList<CustomLine>();
		private VerticalFlowPanel layout = new VerticalFlowPanel();
		private VerticalFlowPanel linePanel = new VerticalFlowPanel();
		private VerticalFlowPanel buttonPanel = new VerticalFlowPanel();
		
		public CustomizedSearch(SearchBaseRpc search, final LookupMetadata meta){
			layout.add(linePanel);
			CustomLine line = new CustomLine(meta);
			linePanel.add(line);
			lines.add(line);
			
			KSButton addCriteria = new KSButton("+ Add Criteria");
			addCriteria.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					CustomLine line = new CustomLine(meta);
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
					for(CustomLine field: lines){
						SearchParam param = field.getSearchParam();
						//TODO is this check needed here? probably. assuming string here
						if((param.getValue() != null)){
							params.add(param);
						}
					}
					for(LookupParamMetadata metaParam: meta.getParams()){
						
						if(metaParam.getWriteAccess() == WriteAccess.NEVER){
							SearchParam param = new SearchParam();
							param.setKey(metaParam.getKey());
							param.setKey(metaParam.getDefaultValue().toString());
							params.add(param);
						}
						else if(metaParam.getWriteAccess() == WriteAccess.WHEN_NULL){
							if(metaParam.getDefaultValue() != null && !(metaParam.getDefaultValue().toString().isEmpty())){
								SearchParam param = new SearchParam();
								param.setKey(metaParam.getKey());
								param.setKey(metaParam.getDefaultValue().toString());
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
						table.performSearch(convertParams(params));
					}
					tablePanel.setVisible(true);
				}
			});
			layout.add(searchButton);
			this.initWidget(layout);
		}
	}
	
	//TODO Temporary solution, remove this
	@Deprecated
	private List<QueryParamValue> convertParams(List<SearchParam> params){
		List<QueryParamValue> queryParams = new ArrayList<QueryParamValue>();
		for(SearchParam p: params){
			QueryParamValue q = new QueryParamValue();
			q.setKey(p.getKey());
			q.setValue((String)p.getValue());
			queryParams.add(q);
		}
		
		return queryParams;
	}
	
	private class CustomLine extends Composite{
		private KSDropDown paramSelector = new KSDropDown();
		private SimplePanel widgetPanel = new SimplePanel();
		private Widget widget = null;
		private String key;
		private HorizontalBlockFlowPanel layout = new HorizontalBlockFlowPanel();
		
		public CustomLine(LookupMetadata meta){
			paramSelector.setBlankFirstItem(false);
			paramSelector.setListItems(listItems);
			String id = meta.getParams().get(0).getKey();
			paramSelector.selectItem(id);
			widget = listItems.getWidget(id);
			key = id;
			widgetPanel.setWidget(widget);
			paramSelector.addSelectionChangeHandler(new SelectionChangeHandler(){

				@Override
				public void onSelectionChange(KSSelectItemWidgetAbstract w) {
					String id = w.getSelectedItem();
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
	}
	
	private class AdvancedSearch extends Composite{
		private List<SearchField> searchFields = new ArrayList<SearchField>();
		
		public AdvancedSearch(SearchBaseRpc search, final LookupMetadata meta){
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
					for(SearchField field: searchFields){
						SearchParam param = field.getSearchParam();
						//TODO is this check needed here? probably. assuming string here
						if(param.getValue() != null){
							params.add(param);
						}
					}
					
					for(LookupParamMetadata metaParam: meta.getParams()){
						if(metaParam.getWriteAccess() == WriteAccess.NEVER){
							SearchParam param = new SearchParam();
							param.setKey(metaParam.getKey());
							param.setKey(metaParam.getDefaultValue().toString());
							params.add(param);
						}
						else if(metaParam.getWriteAccess() == WriteAccess.WHEN_NULL){
							if(metaParam.getDefaultValue() != null && !(metaParam.getDefaultValue().toString().isEmpty())){
								SearchParam param = new SearchParam();
								param.setKey(metaParam.getKey());
								param.setKey(metaParam.getDefaultValue().toString());
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
						table.performSearch(convertParams(params));
					}
					tablePanel.setVisible(true);
				}
			});
			panel.add(searchButton);
			this.initWidget(panel);
		}
	}
	
	private class SearchField extends Composite{
		
		private Widget widget = null;
		private LookupParamMetadata meta = null;
		private VerticalFlowPanel panel = new VerticalFlowPanel();
		
		public SearchParam getSearchParam(){
			SearchParam param = new SearchParam();
			param.setKey(meta.getKey());
			if(widget instanceof HasText){
				param.setValue(((HasText) widget).getText());
			}
			else if(widget instanceof HasValue){
				Object value = ((HasValue) widget).getValue();
				if(value != null){
				//TODO need to handle date and other types here
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
			KSLabel searchParamLabel = new KSLabel(param.getName());
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

		
	}
	
	//public void search
	
	//TODO context criteria panel if the it is configured to show some never fields
	//private void contextCriteriaPanel()
	
	//TODO constructor for list of lookupMeata, is it always a single searchrpc class?
	public SearchPanel(SearchBaseRpc search, List<LookupMetadata> meta){
		
	}
	
	public List<String> getSelectedIds(){
		List<String> ids = new ArrayList<String>();
		if(table != null){
			ids = table.getSelectedIds();
		}
		return ids;
	}
	
	public boolean isMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		this.multiSelect = multiSelect;
	}
}
