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
import java.util.List;
import java.util.Set;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.SearchRpcServiceAsync;
import org.kuali.student.common.ui.client.service.SearchServiceFactory;
import org.kuali.student.common.ui.client.widgets.pagetable.GenericTableModel;
import org.kuali.student.common.ui.client.widgets.pagetable.PagingScrollTableBuilder;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.common.ui.client.widgets.searchtable.SearchColumnDefinition;
import org.kuali.student.r1.common.assembly.data.LookupResultMetadata;

import com.google.gwt.gen2.table.client.AbstractColumnDefinition;
import com.google.gwt.gen2.table.client.AbstractScrollTable.ResizePolicy;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.gen2.table.client.PagingScrollTable;
import com.google.gwt.gen2.table.event.client.RowSelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

public class SearchBackedTable extends Composite {

	private final List<ResultRow> resultRows = new ArrayList<ResultRow>();
	private final List<ResultRow> allResults = new ArrayList<ResultRow>();
	private List<AbstractColumnDefinition<ResultRow, ?>> columnDefs = new ArrayList<AbstractColumnDefinition<ResultRow, ?>>();
	private GenericTableModel<ResultRow> tableModel = new GenericTableModel<ResultRow>(
			resultRows);
	private PagingScrollTableBuilder<ResultRow> builder = new PagingScrollTableBuilder<ResultRow>();
	private String resultIdColumnKey;
	protected PagingScrollTable<ResultRow> pagingScrollTable;
	private VerticalPanel layout = new VerticalPanel();

	private SearchRpcServiceAsync searchRpcServiceAsync = SearchServiceFactory.getSearchService();
	
	private int defaultHeight = 200;

	private SelectionPolicy selectionPolicy = SelectionPolicy.MULTI_ROW;
	private String tableStyleName = "";

	public SearchBackedTable() {
		super();
		redraw();
		layout.setWidth("100%");
		initWidget(layout);
	}

	public SearchBackedTable(int tableHeight) {
		this();
		this.defaultHeight = tableHeight;
	}

	public void clearTable() {
		resultRows.clear();
		this.redraw();
	}

	public void removeSelected() {
		for (ResultRow r : getSelectedRows()) {
			resultRows.remove(r);
		}
		this.redraw();
	}
	
	public void performSearch(SearchRequestInfo searchRequest,
			List<LookupResultMetadata> listResultMetadata, String resultIdKey,
			final Callback<Boolean> callback) {

		initializeTable(listResultMetadata, resultIdKey);

		searchRequest.setNeededTotalResults(false);

		if (pagingScrollTable != null) {
			pagingScrollTable.setEmptyTableWidget(new Label(
					"Processing Search..."));
		}
		
		// Window.alert ("About to invoke asynch search...");
		searchRpcServiceAsync.search(searchRequest,
				new KSAsyncCallback<SearchResultInfo>() {

					@Override
					public void onSuccess(SearchResultInfo searchResults) {
						// Window.alert ("Got back search results...");
						resultRows.clear();
						if (searchResults != null) {
							for (SearchResultRowInfo searchResultRow : searchResults
									.getRows()) {
								// Window.alert ("adding row");
								ResultRow resultRow = new ResultRow();
								for (SearchResultCellInfo searchResultCell : searchResultRow
										.getCells()) {
									if (searchResultCell.getKey().equals(
											resultIdColumnKey)) {
										resultRow.setId(searchResultCell
												.getValue());
									}
									resultRow.setValue(
											searchResultCell.getKey(),
											searchResultCell.getValue());
								}
								resultRows.add(resultRow);
							}
						}
						// Window.alert ("about to redraw...");
						allResults.addAll(resultRows);
						redraw();
						callback.exec(true);
					}

				});
	}
	
	private void initializeTable(List<LookupResultMetadata> listResultMetadata,
			String resultIdKey) {
		clearTable();

		this.resultIdColumnKey = resultIdKey;
		builder = new PagingScrollTableBuilder<ResultRow>();
		builder.tablePixelSize(900, defaultHeight); // width, height
		builder.setSelectionPolicy(selectionPolicy);

		columnDefs = new ArrayList<AbstractColumnDefinition<ResultRow, ?>>();
		for (LookupResultMetadata r : listResultMetadata) {
			// TODO: use this as a token to get a message from message service
			// instead
			String header = r.getName();
			String key = r.getKey();
			if (!r.isHidden()) {
				columnDefs.add(new SearchColumnDefinition(header, key));
			}
		}
		if (columnDefs.size() == 1) {
			columnDefs.get(0).setMinimumColumnWidth(370);
		}
		builder.columnDefinitions(columnDefs);
		tableModel.setColumnDefs(columnDefs);

		redraw();
	}

	public void redraw() {
		tableModel.setRows(resultRows);
		pagingScrollTable = builder.build(tableModel);
		pagingScrollTable.setResizePolicy(ResizePolicy.FILL_WIDTH);		
		if(!"".equals(tableStyleName))
			pagingScrollTable.setStyleName(tableStyleName);
		layout.clear();
		layout.add(pagingScrollTable);
		pagingScrollTable.fillWidth();
		pagingScrollTable.reloadPage();
	}

	public void addSelectionHandler(RowSelectionHandler selectionHandler) {
		pagingScrollTable.getDataTable().addRowSelectionHandler(
				selectionHandler);
	}

	public List<ResultRow> getSelectedRows() {
		List<ResultRow> rows = new ArrayList<ResultRow>();
		Set<Integer> selectedRows = pagingScrollTable.getDataTable()
				.getSelectedRows();
		for (Integer i : selectedRows) {
			rows.add(pagingScrollTable.getRowValue(i));
		}
		return rows;
	}

	public List<String> getSelectedIds() {
		List<String> ids = new ArrayList<String>();
		Set<Integer> selectedRows = pagingScrollTable.getDataTable()
				.getSelectedRows();
		for (Integer i : selectedRows) {
			ids.add(pagingScrollTable.getRowValue(i).getId());
		}
		return ids;
	}

	public List<String> getAllIds() {
		List<String> ids = new ArrayList<String>();
		for (ResultRow r : resultRows) {
			ids.add(r.getId());
		}
		return ids;
	}

	public List<ResultRow> getAllRows() {
		List<ResultRow> rows = new ArrayList<ResultRow>();
		for (ResultRow r : resultRows) {
			rows.add(r);
		}
		return rows;
	}

	public List<ResultRow> getAllResults() {
		return allResults;
	}

	public List<ResultRow> getResultRows() {
		return resultRows;
	}

	public void setDefaultHeight(int defaultHeight) {
		this.defaultHeight = defaultHeight;
	}

    public void setSelectionPolicy(SelectionPolicy selectionPolicy){
    	this.selectionPolicy = selectionPolicy;
    }

    public void setTableStyleName(String tableStyleName){
    	this.tableStyleName = tableStyleName;
    }
}
