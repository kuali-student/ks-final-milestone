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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.LookupParamMetadata;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;

public class BrowsePanel extends Composite
{
	//Layout configuration

	private VerticalFlowPanel layout;
	private VerticalFlowPanel tablePanel;
	private LookupMetadata lookupMetadata = null;
	private SearchBackedTable searchBackedTable = null;
	private boolean multiSelect = false;

	public interface OnSelectedCallback
	{

		public void selected (List<String> selectedIds);

	}
	private OnSelectedCallback onSelectectedCallback;

	public BrowsePanel (LookupMetadata lookupMetadata)
	{
		this.lookupMetadata = lookupMetadata;
		layout = new VerticalFlowPanel ();
		layout.addStyleName ("KS-Picker-Border");
		layout.addStyleName ("KS-Advanced-Search-Panel");
		tablePanel = new VerticalFlowPanel ();
		this.initWidget (layout);
	}

	public Map<String, Object> parameters;

	public Map<String, Object> getParameters ()
	{
		if (parameters == null)
		{
			parameters = new LinkedHashMap<String, Object> ();
		}
		return parameters;
	}

	public void setParameters (Map<String, Object> parameters)
	{
		this.parameters = parameters;
	}

	private Map<String, Object> getDefaultParameters ()
	{
		Map<String, Object> defParms = new LinkedHashMap<String, Object> ();
		for (LookupParamMetadata paramMeta : lookupMetadata.getParams ())
		{
			if (paramMeta.getDefaultValueString () != null)
			{
				defParms.put (paramMeta.getKey (), paramMeta.getDefaultValueString ());
				continue;
			}
			if (paramMeta.getDefaultValueList () != null)
			{
				defParms.put (paramMeta.getKey (), paramMeta.getDefaultValueList ());
			}
		}
		return defParms;
	}

	public OnSelectedCallback getOnSelectectedCallback ()
	{
		return onSelectectedCallback;
	}

	public void setOnSelectectedCallback (OnSelectedCallback onSelectectedCallback)
	{
		this.onSelectectedCallback = onSelectectedCallback;
	}

	private class SelectButtonClickHandler implements ClickHandler
	{

		private OnSelectedCallback callback;
		private SearchBackedTable searchBackedTable;

		public SelectButtonClickHandler (OnSelectedCallback callback,
				SearchBackedTable searchBackedTable)
		{
			this.callback = callback;
			this.searchBackedTable = searchBackedTable;
		}

		@Override
		public void onClick (ClickEvent event)
		{
			this.callback.selected (this.searchBackedTable.getSelectedIds ());
		}

	}


	@SuppressWarnings("unchecked")
	public void executeSearch ()
	{

		tablePanel.clear ();
		layout.clear ();
		tablePanel.setVisible (false);
		layout.add (tablePanel);
		//  layout.addStyleName ("KS-Picker-Border");
		//  layout.addStyleName (Style.BROWSE.getStyle ());
		searchBackedTable = new SearchBackedTable ();
		searchBackedTable.addStyleName ("KS-Advanced-Search-Results-Table");
		KSButton selectButton =
			new KSButton ("Select",
					new SelectButtonClickHandler (this.onSelectectedCallback,
							this.searchBackedTable));
		tablePanel.add (searchBackedTable);
		tablePanel.add (selectButton);  
		tablePanel.setVisible (false);

		SearchRequest searchRequest = new SearchRequest ();
		List<SearchParam> searchParams = new ArrayList<SearchParam> ();
		Map<String, Object> parms = getDefaultParameters ();
		parms.putAll (getParameters ());
		for (Entry<String, Object> entry : parms.entrySet())
		{
			Object value = entry.getValue();
			SearchParam searchParam = new SearchParam ();
			searchParam.setKey (entry.getKey());
			if (value instanceof String){
				searchParam.setValue ((String) value);
			} else {
				searchParam.setValue ((List<String>) value);
			}
			searchParams.add (searchParam);
		}
		searchRequest.setParams (searchParams);
		searchRequest.setSearchKey (lookupMetadata.getSearchTypeId ());

		//  StringBuilder builder = new StringBuilder ();
		//  builder.append ("About to invoke search: type=");
		//  builder.append (lookupMetadata.getSearchTypeId ());
		//  builder.append (" with ");
		//  builder.append (searchParams.size ());
		//  builder.append (" parametrs.");
		//  String comma = "\n";
		//  for (SearchParam param : searchParams)
		//  {
		//   builder.append (comma);
		//   builder.append (param.getKey ());
		//   builder.append ("=");
		//   builder.append (param.getValue ());
		//  }
		//  Window.alert (builder.toString ());


		searchBackedTable.performSearch (searchRequest, lookupMetadata.getResults (), lookupMetadata.
				getResultReturnKey ());
		tablePanel.setVisible (true);
		layout.setVisible (true);
	}

	public List<String> getSelectedIds ()
	{
		List<String> ids = new ArrayList<String> ();
		if (searchBackedTable != null)
		{
			ids = searchBackedTable.getSelectedIds ();
		}
		return ids;
	}

	public List<SelectedResults> getSelectedValues ()
	{

		List<SelectedResults> selectedValues = new ArrayList<SelectedResults> ();
		if (searchBackedTable != null)
		{
			List<ResultRow> selectedRows = searchBackedTable.getSelectedRows ();
			for (ResultRow row : selectedRows)
			{
				String displayKey = row.getValue (lookupMetadata.getResultDisplayKey ());
				String returnKey = row.getValue (lookupMetadata.getResultReturnKey ());
				selectedValues.add (new SelectedResults (displayKey, returnKey));
				if (multiSelect == false)
				{
					break;
				}
			}
		}

		return selectedValues;
	}

	public boolean isMultiSelect ()
	{
		return multiSelect;
	}

	public void setMultiSelect (boolean multiSelect)
	{
		this.multiSelect = multiSelect;
	}

}
