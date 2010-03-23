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
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.LightBox;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.ColumnDefinition;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.DynamicTable;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.Selection;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableModel;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.Selection.SelectionType;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export.ExportFormatDefinition;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export.ExportRequest;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export.ExportRequestBuilder;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export.ExportRuntimeException;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export.ExportServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export.ExportStatus;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export.MappedExportRequestBuilder;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export.download.ExportDownloader;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.themes.Theme;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.Callback;

/**
 * Displays an "export records" popup from which the user can select an export format.
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public class ExportAction<T> implements ActionHandler<T> {
	private class FormatSelector extends Composite {
		public FormatSelector(final Callback<ExportFormatDefinition<T>> selectionCallback) {
			final VerticalPanel panel = new VerticalPanel();
			final Set<RadioButton> formats = new HashSet<RadioButton>();
			final Label warning = new Label("NoOptionSelected");
			final HorizontalPanel buttons = new HorizontalPanel();

			panel.add(new Label("SelectExportFormat"));
			for (final String key : supportedFormats.keySet()) {
				final RadioButton radio = new RadioButton("EXPORT_FORMAT", key);
				panel.add(radio);
				formats.add(radio);
			}

			warning.addStyleName("ExportWarning");
			warning.setVisible(false);
			panel.add(warning);

			buttons.add(new Button("OK", new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					ExportFormatDefinition<T> selected = null;
					for (final RadioButton radio : formats) {
						if (radio.getValue()) {
							selected = supportedFormats.get(radio.getText());
							break;
						}
					}
					if (selected == null) {
						warning.setVisible(true);
					} else {
						selectionCallback.exec(selected);
					}
				}
			}));
			buttons.add(new Button("Cancel", new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					selectionCallback.exec(null);
				}
			}));

			panel.add(buttons);

			super.initWidget(panel);
		}
	}

	public static final ColumnDefinition.ColumnAttribute NON_EXPORTABLE = new ColumnDefinition.ColumnAttribute() {
	};

	private final Map<String, ExportFormatDefinition<T>> supportedFormats = new LinkedHashMap<String, ExportFormatDefinition<T>>();
	private final ExportServiceAsync<T> service;

	private final String rowMapperClassName;

	private final ExportDownloader downloader = GWT.create(ExportDownloader.class);

	/**
	 * Creates a new ExportAction
	 * @param service the ExportService to which to send the export request
	 */
	public ExportAction(final ExportServiceAsync<T> service) {
		this(service, null);
	}

	/**
	 * Creates a new ExportAction
	 * @param service the ExportService to which to send the export request
	 * @param rowMapperClassName the class name of the server-side RowMapper to use
	 */
	public ExportAction(final ExportServiceAsync<T> service, final String rowMapperClassName) {
		this.service = service;
		this.rowMapperClassName = rowMapperClassName;
	}

	/**
	 * Adds an export format to the action's format list
	 * @param format ExportFormatDefinition representing the export format
	 */
	public void addExportFormat(final ExportFormatDefinition<T> format) {
		supportedFormats.put(format.getLabel(), format);
	}

	private void handleResult(final ExportStatus result, final LightBox box) {
		switch (result.getStatus()) {
			case COMPLETE:
				final String url = ((ServiceDefTarget) service).getServiceEntryPoint() + "?exportId="
						+ result.getExportId();
				if (result.isAttachment()) {
					downloader.download(url);
				} else {
					Window.open(url, "_blank", "");
				}
				box.hide();
				break;
			case PENDING:
				final Timer t = new Timer() {
					@Override
					public void run() {
						service.getExportStatus(result.getExportId(), new AsyncCallback<ExportStatus>() {
							@Override
							public void onFailure(final Throwable caught) {
								box.hide();
								throw new ExportRuntimeException("Export failed", caught);
							}

							@Override
							public void onSuccess(final ExportStatus result) {
								handleResult(result, box);
							}
						});
					}
				};
				t.schedule(10000);
				break;
			case FAILED:
				box.hide();
				throw new ExportRuntimeException("Export failed", result.getError());

		}

	}

	/** (non-Javadoc)
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.actions.ActionHandler#isEnabledFor(org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.DynamicTable)
	 */
	@Override
	public boolean isEnabledFor(final DynamicTable<T> table) {
		boolean result = false;
		if (table.getModel().getRowCount() > 0) {
			final Selection<T> sel = table.getModel().getSelection();
			result = (sel.getType() == SelectionType.EXCLUSIVE && sel.getIds().isEmpty())
					|| (sel.getType() == SelectionType.INCLUSIVE && !sel.getIds().isEmpty());
		}
		return result;

	}

	/**
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.actions.ActionHandler#onAction(org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.DynamicTable)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void onAction(final DynamicTable<T> table) {
		final TableModel<T> model = table.getModel();

		final LightBox box = new LightBox(RootPanel.get());
		final FormatSelector selector = new FormatSelector(new Callback<ExportFormatDefinition<T>>() {

			@Override
			public void exec(final ExportFormatDefinition<T> definition) {
				if (definition == null) {
					// user canceled
					box.hide();
				} else {
					//box.setWidget(ExportSpinner());

					final ExportRequestBuilder<T> builder = definition.getBuilder();
					builder.setTable(table)
							.setExportFormatClassName(definition.getExporterClassName())
							.setFilter(model.getFilter())
							.setSelectedIds(model.getSelection().getIds())
							.setSelectionType(model.getSelection().getType())
							.setSort(model.getSortInfo());
					final List<ColumnDefinition<T>> cols = new ArrayList<ColumnDefinition<T>>();
					for (final ColumnDefinition<T> col : table.getDefinition().getColumns()) {
						if (!col.hasAttribute(NON_EXPORTABLE.getClass())) {
							cols.add(col);
						}
					}
					builder.setColumns(cols);

					if (builder instanceof MappedExportRequestBuilder) {
						((MappedExportRequestBuilder) builder).setRowMapperClassName(rowMapperClassName);
					}

					builder.build(new ExportRequestBuilder.BuilderCallback<T>() {

						@Override
						public void onBuildCanceled() {
							box.hide();
						}

						@Override
						public void onBuildComplete(final ExportRequest<T> request) {
							service.export(request, new AsyncCallback<ExportStatus>() {
								@Override
								public void onFailure(final Throwable caught) {
									box.hide();
									throw new ExportRuntimeException("Export failed", caught);
								}

								@Override
								public void onSuccess(final ExportStatus result) {
									handleResult(result, box);
								}
							});
						}
					});
				}
			}

		});

		box.setWidget(selector);
		box.show();
	}
}
