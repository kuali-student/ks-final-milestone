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

package org.kuali.student.common.ui.client.widgets.filter;

import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.GwtEvent;

public class FilterEvent extends GwtEvent<FilterEventHandler>{
	public static final Type<FilterEventHandler> TYPE = new Type<FilterEventHandler>();
	
	private boolean initialSelect;

	private boolean selected;
	private String filterKey;
	private String filterValue;
	
	private Map<String, List<String>> selections;
		
	public FilterEvent(Map<String, List<String>> selections, boolean selected, boolean initialSelect, String key, String value){
		this.selected = selected;
		this.filterKey = key;
		this.filterValue = value;
		this.initialSelect = initialSelect;
		this.selections = selections;		
	}
	
	/**
	 * @return Filter key of latest selected filter option.
	 */
	public String getFilterKey() {
		return filterKey;
	}

	/** 
	 * @return Value of the latest filter option checked
	 */
	public String getFilterValue() {
		return filterValue;
	}

	/**
	 * Use this to check if this is the first filter option to be selected. When true, the selections map should
	 * of size 1 with the associated list entry of size 1.
	 *  
	 * @return true if this is the first filter item to be selected 
	 */
	public boolean isInitialSelect() {
		return initialSelect;
	}
	
	/** 
	 * @return List of all filter options selected with map key = filter option key amd map value a list of filter option values 
	 * selected for that key.
	 */
	public Map<String, List<String>> getSelections() {
		return selections;
	}

	@Override
	protected void dispatch(FilterEventHandler handler) {
		if (selected){
			handler.onSelect(this);
		} else {
			handler.onDeselect(this);
		}
	}

	@Override
	public Type<FilterEventHandler> getAssociatedType() {
		return TYPE;
	}

		
}
