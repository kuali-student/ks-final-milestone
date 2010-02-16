/*
 * Copyright 2009 Johnson Consulting Services
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event fired by a TableModel when a change is detected
 * @author wilj
 *
 */
public class ModelChangeEvent extends GwtEvent<ModelChangeHandler> {
	public static final Type<ModelChangeHandler> TYPE = new Type<ModelChangeHandler>();

	private final int rowCount;

	/**
	 * Creates a new ModelChangeEvent with the model's current row count
	 * @param rowCount the model's current row count
	 */
	public ModelChangeEvent(final int rowCount) {
		this.rowCount = rowCount;
	}

	@Override
	protected void dispatch(final ModelChangeHandler handler) {
		handler.onRowCountChange(this);
	}

	@Override
	public Type<ModelChangeHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * Returns the model's current row count
	 * @return the model's current row count
	 */
	public int getRowCount() {
		return rowCount;
	}

}
