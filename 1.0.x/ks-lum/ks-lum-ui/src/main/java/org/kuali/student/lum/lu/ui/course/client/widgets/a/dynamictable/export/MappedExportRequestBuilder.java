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

/**
 * 
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.ColumnDefinition;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.ColumnPicker;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.themes.Theme;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.Callback;

/**
 * @author wilj
 *
 */
public class MappedExportRequestBuilder<T> extends ExportRequestBuilder<T> {

	private String rowMapperClassName;

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export.ExportRequestBuilder#build(org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export.ExportRequestBuilder.BuilderCallback)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void build(final ExportRequestBuilder.BuilderCallback<T> callback) {
		super.build(new ExportRequestBuilder.BuilderCallback<T>() {
			@Override
			public void onBuildCanceled() {
				callback.onBuildCanceled();
			}

			@Override
			public void onBuildComplete(final ExportRequest<T> request) {
				showColumnPicker(callback, (MappedExportRequest) request);
			}

		});
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export.ExportRequestBuilder#getRequestInstance()
	 */
	@Override
	protected ExportRequest<T> getRequestInstance() {
		return new MappedExportRequest<T>();
	}

	/**
	 * Returns the class name of the server-side row exporter to use
	 * @return the class name of the server-side row exporter to use
	 */
	public String getRowMapperClassName() {
		return rowMapperClassName;
	}

	/**
	 * Sets the class name of the server-side row exporter to use
	 * @param rowMapperClassName the class name of the server-side row exporter to use
	 */
	public void setRowMapperClassName(final String rowMapperClassName) {
		this.rowMapperClassName = rowMapperClassName;
	}

	protected void showColumnPicker(final ExportRequestBuilder.BuilderCallback<T> callback,
			final MappedExportRequest<T> request) {

/*		final ColumnPicker<T> picker = new ColumnPicker<T>(super.getColumns(), Theme.INSTANCE.getExportMessages()
				.getSelectColumns(),
				new ColumnPicker.IsSelectedAdapter<T>() {
			@Override
			public boolean isSelected(final ColumnDefinition<T> column) {
				return column.isVisible();
			}
		});

		picker.show(new Callback<Boolean>() {
			@Override
			public void exec(final Boolean value) {
				if (value) {
					final Map<String, String> selectedColumns = new LinkedHashMap<String, String>();
					for (final Entry<ColumnDefinition<T>, Boolean> e : picker.getSelection().entrySet()) {
						if (e.getValue()) {
							selectedColumns.put(e.getKey().getName(), e.getKey().getRenderer().getDisplayName());
						}
					}
					request.setColumns(selectedColumns);
					request.setRowMapperClassName(rowMapperClassName);
					callback.onBuildComplete(request);
				} else {
					callback.onBuildCanceled();
				}
			}
		});
*/
	}

}
