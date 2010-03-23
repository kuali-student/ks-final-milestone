/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.client.widgets.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.kuali.student.common.ui.client.service.SearchRpcService;
import org.kuali.student.common.ui.client.service.SearchRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.pagetable.GenericTableModel;
import org.kuali.student.common.ui.client.widgets.pagetable.PagingScrollTableBuilder;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.common.ui.client.widgets.searchtable.SearchColumnDefinition;
import org.kuali.student.core.assembly.data.LookupResultMetadata;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.table.client.AbstractColumnDefinition;
import com.google.gwt.gen2.table.client.PagingOptions;
import com.google.gwt.gen2.table.client.PagingScrollTable;
import com.google.gwt.gen2.table.client.AbstractScrollTable.ResizePolicy;
import com.google.gwt.gen2.table.event.client.RowSelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TempSearchBackedTable extends Composite{

    private List<ResultRow> resultRows = new ArrayList<ResultRow>();
    private List<AbstractColumnDefinition<ResultRow, ?>> columnDefs = new ArrayList<AbstractColumnDefinition<ResultRow, ?>>();
    private GenericTableModel<ResultRow> tableModel = new GenericTableModel<ResultRow>(resultRows);
    private PagingScrollTableBuilder<ResultRow> builder = new PagingScrollTableBuilder<ResultRow>();
    private String resultIdColumnKey;
    protected PagingScrollTable<ResultRow> pagingScrollTable;
    private VerticalPanel layout = new VerticalPanel();
    private PagingOptions pagingOptions;
    
    private SearchRpcServiceAsync searchRpcServiceAsync = GWT.create(SearchRpcService.class);
    
    private PagingOptions createPagingOptions(PagingScrollTable<ResultRow> pagingScrollTable) {
        PagingOptions pagingOptions = new PagingOptions(pagingScrollTable); 
        pagingOptions.setPixelSize(pagingScrollTable.getOffsetWidth(), pagingOptions.getOffsetHeight());
        return pagingOptions;
    }
    
    public TempSearchBackedTable(){
        super();                
        redraw();               
        layout.setWidth("100%");
        initWidget(layout);
    }
    
    public void clearTable(){
        resultRows.clear();
        this.redraw();        
    }
    
    public void removeSelected(){
        for(ResultRow r: getSelectedRows()){
            resultRows.remove(r);
        }
        this.redraw();
    }
    
    public void performSearch(SearchRequest searchRequest, List<LookupResultMetadata> listResultMetadata, String resultIdKey){
    	
    	initializeTable(listResultMetadata, resultIdKey);
    	
    	searchRequest.setNeededTotalResults(false);
    	
        if(pagingScrollTable != null){
            pagingScrollTable.setEmptyTableWidget(new Label("Processing Search..."));
        }       
                
		searchRpcServiceAsync.search(searchRequest, new AsyncCallback<SearchResult>(){

		    @Override
		    public void onFailure(Throwable cause) {
		    	GWT.log("Failed to perform search", cause); //FIXME more detail info here
		    	Window.alert("Failed to perform search");            	
		    }

		    @Override
		    public void onSuccess(SearchResult results) {
		        
		    	resultRows.clear();
		        if(results != null){
		            for (SearchResultRow r: results.getRows()){
		                ResultRow theRow = new ResultRow();
		                for(SearchResultCell c: r.getCells()){
		                    if(c.getKey().equals(resultIdColumnKey)){
		                        theRow.setId(c.getValue());
		                    }
		                    theRow.setValue(c.getKey(), c.getValue());
		                }
		                resultRows.add(theRow);
		            }                    
		        }
		        redraw();
		    }
		});
    }
    
    public void initializeTable(List<LookupResultMetadata> listResultMetadata, String resultIdKey) {
    	clearTable();
    	
        this.resultIdColumnKey = resultIdKey;
        builder = new PagingScrollTableBuilder<ResultRow>();
        builder.tablePixelSize(400, 300);

        columnDefs = new ArrayList<AbstractColumnDefinition<ResultRow, ?>>();        
        for (LookupResultMetadata r: listResultMetadata){
            //TODO: use this as a token to get a message from message service instead 
            String header = r.getName();
            String key = r.getKey();
            if(!r.isHidden()){
                columnDefs.add(new SearchColumnDefinition(header, key));
            }
        }
        if(columnDefs.size() == 1){
            //FIXME auto adjusting width to fill table does not work with 1 column bug in incubator???
            columnDefs.get(0).setMinimumColumnWidth(370);
        }
        builder.columnDefinitions(columnDefs);
        
        redraw(); 
    }
    
    public void redraw(){
        tableModel.setRows(resultRows);
        pagingScrollTable = builder.build(tableModel);
        pagingScrollTable.setResizePolicy(ResizePolicy.FILL_WIDTH);
        pagingOptions = createPagingOptions(pagingScrollTable);
        layout.clear();
        layout.add(pagingOptions);
        layout.add(pagingScrollTable);
        pagingScrollTable.fillWidth();
    }
    
    public void addSelectionHandler(RowSelectionHandler selectionHandler){
        pagingScrollTable.getDataTable().addRowSelectionHandler(selectionHandler);
    }
    
    public List<ResultRow> getSelectedRows(){
        List<ResultRow> rows = new ArrayList<ResultRow>();
        Set<Integer> selectedRows = pagingScrollTable.getDataTable().getSelectedRows();
        for(Integer i: selectedRows){
            rows.add(pagingScrollTable.getRowValue(i));
        }
        return rows;
    }
    
    public List<String> getSelectedIds(){
        List<String> ids = new ArrayList<String>();
        Set<Integer> selectedRows = pagingScrollTable.getDataTable().getSelectedRows();
        for(Integer i: selectedRows){
            ids.add(pagingScrollTable.getRowValue(i).getId());
        }
        return ids;
    }
    
    public List<String> getAllIds(){
        List<String> ids = new ArrayList<String>();
        for(ResultRow r: resultRows){
            ids.add(r.getId());
        }
        return ids;
    }
    
    public List<ResultRow> getAllRows(){
        List<ResultRow> rows = new ArrayList<ResultRow>();
        for(ResultRow r: resultRows){
            rows.add(r);
        }
        return rows;
    }
    
}
