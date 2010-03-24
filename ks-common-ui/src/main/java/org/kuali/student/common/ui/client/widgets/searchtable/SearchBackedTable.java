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
package org.kuali.student.common.ui.client.widgets.searchtable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.pagetable.GenericTableModel;
import org.kuali.student.common.ui.client.widgets.pagetable.PagingScrollTableBuilder;
import org.kuali.student.core.search.dto.ResultColumnInfo;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;

import com.google.gwt.gen2.table.client.AbstractColumnDefinition;
import com.google.gwt.gen2.table.client.PagingOptions;
import com.google.gwt.gen2.table.client.PagingScrollTable;
import com.google.gwt.gen2.table.client.AbstractScrollTable.ResizePolicy;
import com.google.gwt.gen2.table.event.client.RowSelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchBackedTable extends Composite{

    private BaseRpcServiceAsync searchService;
    private String searchTypeKey;
    private List<ResultRow> resultRows = new ArrayList<ResultRow>();
    private List<AbstractColumnDefinition<ResultRow, ?>> columnDefs = new ArrayList<AbstractColumnDefinition<ResultRow, ?>>();
    private GenericTableModel<ResultRow> tableModel = new GenericTableModel<ResultRow>(resultRows);
    private PagingScrollTableBuilder<ResultRow> builder;
    private String resultIdKey;
    protected PagingScrollTable<ResultRow> pagingScrollTable;
    private VerticalPanel layout = new VerticalPanel();
    private PagingOptions pagingOptions;
    
    
    private PagingOptions createPagingOptions(PagingScrollTable<ResultRow> pagingScrollTable) {
        PagingOptions pagingOptions = new PagingOptions(pagingScrollTable); 
        pagingOptions.setPixelSize(pagingScrollTable.getOffsetWidth(), pagingOptions.getOffsetHeight());
        return pagingOptions;
    }
    
    public SearchBackedTable(BaseRpcServiceAsync searchService, String searchTypeKey, String resultIdKey){
        super();
        this.searchService = searchService;
        this.searchTypeKey = searchTypeKey;
        this.resultIdKey = resultIdKey;
        builder = new PagingScrollTableBuilder<ResultRow>();
        builder.tablePixelSize(400, 300);
        //builder.cacheTable(10, 10);
        searchService.getSearchType(searchTypeKey, new AsyncCallback<SearchTypeInfo>(){

            public void onFailure(Throwable caught) {
                //TODO: How to handle this?
            }

            public void onSuccess(SearchTypeInfo searchTypeInfo) {
                SearchResultTypeInfo resultInfo = searchTypeInfo.getSearchResultTypeInfo();
                List<ResultColumnInfo> resultColumns = resultInfo.getResultColumns();
                for (ResultColumnInfo r: resultColumns){
                    //TODO: use this as a token to get a message from message service instead 
                    String header = r.getName();
                    String key = r.getKey();
                    if(!(r.getKey().equals(SearchBackedTable.this.resultIdKey))){
                        columnDefs.add(new SearchColumnDefinition(header, key));
                    }
                }
                if(columnDefs.size() == 1){
                    //FIXME auto adjusting width to fill table does not work with 1 column bug in incubator???
                    columnDefs.get(0).setMinimumColumnWidth(370);
                }
                builder.columnDefinitions(columnDefs);
                
                redraw();
/*                pagingScrollTable = builder.build(tableModel);
                pagingScrollTable.setResizePolicy(ResizePolicy.FILL_WIDTH);
                pagingOptions = createPagingOptions(pagingScrollTable);
                layout.add(pagingOptions);
                layout.add(pagingScrollTable);
                //pagingScrollTable.setWidth("100%");
                pagingScrollTable.fillWidth();*/
                
                
                //pagingScrollTable.redraw();
            }
        });
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
    
    public void performSearch(List<SearchParam> params){
        if(pagingScrollTable != null){
            pagingScrollTable.setEmptyTableWidget(new Label("Processing Search..."));
        }
        SearchRequest request = new SearchRequest();
        request.setParams(params);
        request.setSearchKey(searchTypeKey);
        searchService.search(request, new AsyncCallback<SearchResult>(){

            @Override
            public void onFailure(Throwable caught) {
                // TODO bsmith - THIS METHOD NEEDS JAVADOCS
                
            }

            @Override
            public void onSuccess(SearchResult result) {
                
                if(result != null){
                    for (SearchResultRow r: result.getRows()){
                        ResultRow theRow = new ResultRow();
                        for(SearchResultCell c: r.getCells()){
                            if(c.getKey().equals(resultIdKey)){
                                theRow.setId(c.getValue());
                            }
                            theRow.setValue(c.getKey(), c.getValue());
                        }
                        resultRows.add(theRow);
                    }
                    
                }
                redraw();
                
                /*tableModel.setRows(resultRows);
                //tableModel = new GenericTableModel<ResultRow>(resultRows);
                
                pagingScrollTable = builder.build(tableModel);
                pagingScrollTable.setResizePolicy(ResizePolicy.FILL_WIDTH);
                //pagingScrollTable.reloadPage();
                //pagingScrollTable.redraw();
                pagingOptions = createPagingOptions(pagingScrollTable);
                layout.clear();
                layout.add(pagingOptions);
                layout.add(pagingScrollTable);
                pagingScrollTable.fillWidth();*/
                //pagingScrollTable.setResizePolicy(ResizePolicy.FILL_WIDTH);
                //pagingScrollTable.fillWidth();
                //layout.remove(index)
                //main.add(createTableAndSelectionPanel(createPagingOptions(pagingScrollTable)));
            }
        });
        
        
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
