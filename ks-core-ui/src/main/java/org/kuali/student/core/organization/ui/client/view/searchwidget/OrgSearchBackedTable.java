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

package org.kuali.student.core.organization.ui.client.view.searchwidget;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.pagetable.GenericTableModel;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.core.search.dto.ResultColumnInfo;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;

import com.google.gwt.gen2.table.client.AbstractColumnDefinition;
import com.google.gwt.gen2.table.client.CellRenderer;
import com.google.gwt.gen2.table.client.PagingOptions;
import com.google.gwt.gen2.table.client.PagingScrollTable;
import com.google.gwt.gen2.table.client.RowRenderer;
import com.google.gwt.gen2.table.client.AbstractScrollTable.ResizePolicy;
import com.google.gwt.gen2.table.event.client.RowSelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OrgSearchBackedTable extends Composite{

    private BaseRpcServiceAsync searchService;
    private String searchTypeKey;
    private List<ResultRow> resultRows = new ArrayList<ResultRow>();
    private List<AbstractColumnDefinition<ResultRow, ?>> columnDefs = new ArrayList<AbstractColumnDefinition<ResultRow, ?>>();
    private GenericTableModel<ResultRow> tableModel = new GenericTableModel<ResultRow>(resultRows);
    private OrgPagingScrollTableBuilder<ResultRow> builder;
    private String resultIdKey;
    protected PagingScrollTable<ResultRow> pagingScrollTable;
    private VerticalPanel layout = new VerticalPanel();
    private PagingOptions pagingOptions;
    
    
    private PagingOptions createPagingOptions(PagingScrollTable<ResultRow> pagingScrollTable) {
        PagingOptions pagingOptions = new PagingOptions(pagingScrollTable); 
        pagingOptions.setPixelSize(pagingScrollTable.getOffsetWidth(), pagingOptions.getOffsetHeight());
        return pagingOptions;
    }
    
    private void init(BaseRpcServiceAsync searchService, String searchTypeKey, 
            String resultIdKey, final RowRenderer<ResultRow> rowRenderer, 
            final CellRenderer<ResultRow, String> cellRenderer){
        this.searchService = searchService;
        this.searchTypeKey = searchTypeKey;
        this.resultIdKey = resultIdKey;
        builder = new OrgPagingScrollTableBuilder<ResultRow>();
        builder.tablePixelSize(400, 300);
        if (rowRenderer != null) {
            builder.setRowRenderer(rowRenderer);
        }
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
                    if(!(r.getKey().equals(OrgSearchBackedTable.this.resultIdKey))){
                        columnDefs.add(new OrgSearchColumnDefinition(header, key, cellRenderer));
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
    
    public OrgSearchBackedTable(BaseRpcServiceAsync searchService, String searchTypeKey, String resultIdKey, 
            RowRenderer<ResultRow> rowRenderer, CellRenderer<ResultRow, String> cellRenderer
            ){
        super();
        init(searchService, searchTypeKey, resultIdKey, rowRenderer, cellRenderer);
    }
    
    public OrgSearchBackedTable(BaseRpcServiceAsync searchService, String searchTypeKey, String resultIdKey){
        super();
        init(searchService, searchTypeKey, resultIdKey, null, null);
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
    
    public void performSearch(List<SearchParam> queryParamValues){
        if(pagingScrollTable != null){
            pagingScrollTable.setEmptyTableWidget(new Label("Processing Search..."));
        }
//        searchService.searchForResults(searchTypeKey, queryParamValues, new AsyncCallback<List<Result>>(){
//
//            @Override
//            public void onFailure(Throwable caught) {
//                // TODO bsmith - THIS METHOD NEEDS JAVADOCS
//                
//            }
//
//            @Override
//            public void onSuccess(List<Result> results) {
//                
//                if(results != null){
//                    for (Result r: results){
//                        ResultRow theRow = new ResultRow();
//                        for(ResultCell c: r.getResultCells()){
//                            if(c.getKey().equals(resultIdKey)){
//                                theRow.setId(c.getValue());
//                            }
//                            theRow.setValue(c.getKey(), c.getValue());
//                        }
//                        resultRows.add(theRow);
//                    }
//                    
//                }
//                redraw();
//                
//                /*tableModel.setRows(resultRows);
//                //tableModel = new GenericTableModel<ResultRow>(resultRows);
//                
//                pagingScrollTable = builder.build(tableModel);
//                pagingScrollTable.setResizePolicy(ResizePolicy.FILL_WIDTH);
//                //pagingScrollTable.reloadPage();
//                //pagingScrollTable.redraw();
//                pagingOptions = createPagingOptions(pagingScrollTable);
//                layout.clear();
//                layout.add(pagingOptions);
//                layout.add(pagingScrollTable);
//                pagingScrollTable.fillWidth();*/
//                //pagingScrollTable.setResizePolicy(ResizePolicy.FILL_WIDTH);
//                //pagingScrollTable.fillWidth();
//                //layout.remove(index)
//                //main.add(createTableAndSelectionPanel(createPagingOptions(pagingScrollTable)));
//            }
//        });
        
        
    }
    
    public void redraw(){
        tableModel.setRows(resultRows);
        pagingScrollTable = builder.build(tableModel);
//        pagingScrollTable.getHeaderTable().
//        getElement().getStyle().setProperty("background", "white");
        pagingScrollTable.getHeaderTable().
        getElement().getStyle().setProperty("color", "black");
        pagingScrollTable.getHeaderTable().
        getElement().getStyle().setProperty("fontWeight", "bold");
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
