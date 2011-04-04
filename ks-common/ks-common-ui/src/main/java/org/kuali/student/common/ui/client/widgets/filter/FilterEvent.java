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

import com.google.gwt.event.shared.GwtEvent;

public class FilterEvent extends GwtEvent<FilterEventHandler>{
	public static final Type<FilterEventHandler> TYPE = new Type<FilterEventHandler>();
	
	private boolean initialSelect;

	private boolean selected;
	private String filterKey;
	private String filterValue;
		
	public FilterEvent(boolean selected, boolean initialSelect, String key, String value){
		this.selected = selected;
		this.filterKey = key;
		this.filterValue = value;
		this.initialSelect = initialSelect;
	}
	
	public String getFilterKey() {
		return filterKey;
	}
	
	public String getFilterValue() {
		return filterValue;
	}

	public boolean isInitialSelect() {
		return initialSelect;
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
