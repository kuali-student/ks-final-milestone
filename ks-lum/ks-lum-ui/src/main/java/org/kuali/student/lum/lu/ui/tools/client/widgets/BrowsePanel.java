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

package org.kuali.student.lum.lu.ui.tools.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.r1.common.assembly.data.LookupMetadata;
import org.kuali.student.r1.common.assembly.data.LookupParamMetadata;
import org.kuali.student.r1.common.search.dto.SearchParam;
import org.kuali.student.r1.common.search.dto.SearchRequest;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class BrowsePanel extends Composite {
	// Layout configuration

	private FlowPanel layout;
	private FlowPanel tablePanel;
	private LookupMetadata lookupMetadata = null;
	private SearchBackedTable searchBackedTable = null;
	private boolean multiSelect = false;
	public Map<String, Object> parameters;
	private int tableHeight = 200;;

	public interface OnSelectedCallback {
		public void selected(List<String> selectedIds);
	}

	private OnSelectedCallback onSelectectedCallback;

	public BrowsePanel(LookupMetadata lookupMetadata) {
		this.lookupMetadata = lookupMetadata;
		layout = new FlowPanel();
		layout.addStyleName("KS-Picker-Border");
		layout.addStyleName("KS-Advanced-Search-Panel");
		tablePanel = new FlowPanel();
		this.initWidget(layout);
	}

	public BrowsePanel(LookupMetadata lookupMetadata, int tableHeight) {
		this(lookupMetadata);
		this.tableHeight = tableHeight;
	}

	public Map<String, Object> getParameters() {
		if (parameters == null) {
			parameters = new LinkedHashMap<String, Object>();
		}
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	private Map<String, Object> getDefaultParameters() {
		Map<String, Object> defParms = new LinkedHashMap<String, Object>();
		for (LookupParamMetadata paramMeta : lookupMetadata.getParams()) {
			if (paramMeta.getDefaultValueString() != null) {
				defParms.put(paramMeta.getKey(),
						paramMeta.getDefaultValueString());
				continue;
			}
			if (paramMeta.getDefaultValueList() != null) {
				defParms.put(paramMeta.getKey(),
						paramMeta.getDefaultValueList());
			}
		}
		return defParms;
	}

	public OnSelectedCallback getOnSelectectedCallback() {
		return onSelectectedCallback;
	}

	public void setOnSelectectedCallback(
			OnSelectedCallback onSelectectedCallback) {
		this.onSelectectedCallback = onSelectectedCallback;
	}

	private class SelectButtonClickHandler implements ClickHandler {

		private OnSelectedCallback callback;
		private SearchBackedTable searchBackedTable;

		public SelectButtonClickHandler(OnSelectedCallback callback,
				SearchBackedTable searchBackedTable) {
			this.callback = callback;
			this.searchBackedTable = searchBackedTable;
		}

		@Override
		public void onClick(ClickEvent event) {
			this.callback.selected(this.searchBackedTable.getSelectedIds());
		}

	}

	@SuppressWarnings("unchecked")
	public void executeSearch(Callback<Boolean> callback) {

		tablePanel.clear();
		layout.clear();
		tablePanel.setVisible(false);
		layout.add(tablePanel);
		// layout.addStyleName ("KS-Picker-Border");
		// layout.addStyleName (Style.BROWSE.getStyle ());
		searchBackedTable = new SearchBackedTable(tableHeight);
		searchBackedTable.addStyleName("KS-Advanced-Search-Results-Table");
		searchBackedTable.setTableStyleName("gwt-PagingScrollTable");
		searchBackedTable.setSelectionPolicy(SelectionPolicy.ONE_ROW);
		KSButton selectButton = new KSButton("Select",
				new SelectButtonClickHandler(this.onSelectectedCallback,
						this.searchBackedTable));
		tablePanel.add(searchBackedTable);
		tablePanel.add(selectButton);
		tablePanel.setVisible(false);

		SearchRequest searchRequest = new SearchRequest();
		List<SearchParam> searchParams = new ArrayList<SearchParam>();
		Map<String, Object> parms = getDefaultParameters();
		parms.putAll(getParameters());
		for (Entry<String, Object> entry : parms.entrySet()) {
			Object value = entry.getValue();
			SearchParam searchParam = new SearchParam();
			searchParam.setKey(entry.getKey());
			if (value instanceof String) {
				searchParam.setValue((String) value);
			} else {
				searchParam.setValue((List<String>) value);
			}
			searchParams.add(searchParam);
		}
		searchRequest.setParams(searchParams);
		searchRequest.setSearchKey(lookupMetadata.getSearchTypeId());
        searchRequest.setSortColumn(this.lookupMetadata.getResultSortKey());
        searchRequest.setSortDirection(this.lookupMetadata.getSortDirection());

		// StringBuilder builder = new StringBuilder ();
		// builder.append ("About to invoke search: type=");
		// builder.append (lookupMetadata.getSearchTypeId ());
		// builder.append (" with ");
		// builder.append (searchParams.size ());
		// builder.append (" parametrs.");
		// String comma = "\n";
		// for (SearchParam param : searchParams)
		// {
		// builder.append (comma);
		// builder.append (param.getKey ());
		// builder.append ("=");
		// builder.append (param.getValue ());
		// }
		// Window.alert (builder.toString ());

		searchBackedTable.performSearch(searchRequest,
				lookupMetadata.getResults(),
				lookupMetadata.getResultReturnKey(), callback);
		tablePanel.setVisible(true);
		layout.setVisible(true);
	}
	/*
	 * Parameter is a list of column names from backed table.
	 * Method checks the values of these columns names and tabulates the
	 * number of occurrences of each value.
	 * Returns a map of each value and it's occurrence count
	 */
	public Map<String,Integer> getFilterCount()
	{
		Map<String,Integer> filterCount=new HashMap<String,Integer>();
		for(ResultRow resultRow:getAllResultRows())
		{
			for(String columnName:resultRow.getColumnValues().keySet())
			{
					String columnValue=resultRow.getValue(columnName);
					/*
					 * Some values are a string separated by </br> statements.
					 * The loop removes the </br> statements so values are stored correctly.
					 * Ex:Campus Location is "NO</br>SO"
					 */

					while(columnValue!=null&&columnValue.indexOf("<br/>")!=-1)
					{
						int f=columnValue.indexOf("<br/>");
						if(!filterCount.containsKey(columnValue.substring(0,f)))
						{
							filterCount.put(columnValue.substring(0,f), 1);
						}
						else{
							int a=filterCount.get(columnValue.substring(0,f)).intValue();
							a++;
							filterCount.put(columnValue.substring(0,f), a);
						}
						columnValue=columnValue.substring(f+5);
					}


					if(!filterCount.containsKey(columnValue))
					{
						filterCount.put(columnValue, 1);
					}
					else{
						int a=filterCount.get(columnValue).intValue();
						a++;
						filterCount.put(columnValue, a);
					}
			}
		}
		return filterCount;
	}

	public List<String> getSelectedIds() {
		List<String> ids = new ArrayList<String>();
		if (searchBackedTable != null) {
			ids = searchBackedTable.getSelectedIds();
		}
		return ids;
	}

	public List<SelectedResults> getSelectedValues() {

		List<SelectedResults> selectedValues = new ArrayList<SelectedResults>();
		if (searchBackedTable != null) {
			List<ResultRow> selectedRows = searchBackedTable.getSelectedRows();
			for (ResultRow row : selectedRows) {
				String displayKey = row.getValue(lookupMetadata
						.getResultDisplayKey());
				String returnKey = row.getValue(lookupMetadata
						.getResultReturnKey());
				selectedValues.add(new SelectedResults(displayKey, returnKey));
				if (multiSelect == false) {
					break;
				}
			}
		}

		return selectedValues;
	}

	public void showAllRows() {
		searchBackedTable.getResultRows().clear();
		searchBackedTable.getResultRows().addAll(
				searchBackedTable.getAllResults());
		searchBackedTable.redraw();
	}

	public void showOnlyRows(HashSet<String> rowKeys) {
		searchBackedTable.getResultRows().clear();
		for (ResultRow resultRow : searchBackedTable.getAllResults()) {
			if (rowKeys.contains(resultRow.getId())) {
				searchBackedTable.getResultRows().add(resultRow);
			}
		}
		searchBackedTable.redraw();
	}

	public List<ResultRow> getAllResultRows() {
		return searchBackedTable.getAllResults();
	}

	public boolean isMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		this.multiSelect = multiSelect;
	}

}
