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

package org.kuali.student.common.ui.client.widgets.search;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.service.SearchRpcService;
import org.kuali.student.common.ui.client.service.SearchRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.common.ui.client.widgets.table.scroll.Column;
import org.kuali.student.common.ui.client.widgets.table.scroll.DefaultTableModel;
import org.kuali.student.common.ui.client.widgets.table.scroll.Row;
import org.kuali.student.common.ui.client.widgets.table.scroll.RowComparator;
import org.kuali.student.common.ui.client.widgets.table.scroll.Table;
import org.kuali.student.core.assembly.data.LookupResultMetadata;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchResultsTable extends Composite{

    private final int PAGE_SIZE = 10;
    
    private SearchRpcServiceAsync searchRpcServiceAsync = GWT.create(SearchRpcService.class);
    
    private VerticalPanel layout = new VerticalPanel();
    
    private DefaultTableModel tableModel;
    private String resultIdColumnKey;
    private SearchRequest searchRequest;
    
    public SearchResultsTable(){
        super();
        redraw();
        layout.setWidth("100%");
        initWidget(layout);
    }
    
    public void redraw(){
        layout.clear();      
    }    
    
    //FIXME do we really need to recreate the table for every refresh?
    public void initializeTable(List<LookupResultMetadata> listResultMetadata, String resultIdKey){ 

        this.resultIdColumnKey = resultIdKey;
        
        tableModel = new DefaultTableModel();

        //create table heading
        for (LookupResultMetadata r: listResultMetadata){
            if(!r.isHidden()){
                Column col1 = new Column();
                col1.setId(r.getKey());
                String header = Application.getApplicationContext().getUILabel("", null, null, r.getName());
                col1.setName(header);
                col1.setId(r.getKey());
                col1.setWidth("100px");                    
                col1.setAscendingRowComparator(new FieldAscendingRowComparator(r.getKey(), r.getDataType()));
                col1.setDescendingRowComparator(new FieldDescendingRowComparator(r.getKey(), r.getDataType()));                
                
                tableModel.addColumn(col1);
            }
        }      
                     
     // TODO - there's a better way to do this
        if (this.searchRequest.getSearchKey().toLowerCase().contains("cross")) {
        	tableModel.setMoreData(false);
        }
        
        tableModel.installCheckBoxRowHeaderColumn();
        
        final Table table = new Table();
        table.getScrollPanel().setHeight("300px");
        table.setTableModel(tableModel);
        
        table.getScrollPanel().addScrollHandler(new ScrollHandler() {

            @Override
            public void onScroll(ScrollEvent event) {
            	if (tableModel.getMoreData()) {
	                int height = table.getScrollPanel().getOffsetHeight();
	                int scrollHeight = ((ScrollPanel)event.getSource()).getScrollPosition();
	                if ((scrollHeight*100/height) > 10) {
	                    performOnDemandSearch(tableModel.getRowCount(), PAGE_SIZE);
	                    tableModel.fireTableDataChanged();
	                }
            	}
            }        
        });
        
        redraw();
        layout.add(table);
  }    
    
    public void performSearch(SearchRequest searchRequest, List<LookupResultMetadata> listResultMetadata, String resultIdKey){
        this.searchRequest = searchRequest;
        initializeTable(listResultMetadata, resultIdKey);
        if (this.searchRequest.getSearchKey().toLowerCase().contains("cross")) {

            performOnDemandSearch(0, 0);
        }
        performOnDemandSearch(0, PAGE_SIZE);
    }    
    
    private void performOnDemandSearch(int startAt, int size) {
                

        searchRequest.setStartAt(startAt);
        if (size != 0) {
        	searchRequest.setNeededTotalResults(false);
        	searchRequest.setMaxResults(size);
        } else {
        	searchRequest.setNeededTotalResults(true);
        }

        searchRpcServiceAsync.search(searchRequest, new KSAsyncCallback<SearchResult>(){

            @Override
            public void handleFailure(Throwable cause) {
                GWT.log("Failed to perform search", cause); //FIXME more detail info here
                Window.alert("Failed to perform search");
            }

            @Override
            public void onSuccess(SearchResult results) {

                if(results != null && results.getRows() != null && results.getRows().size() != 0){
                    for (SearchResultRow r: results.getRows()){
                        ResultRow theRow = new ResultRow();
                        for(SearchResultCell c: r.getCells()){
                            if(c.getKey().equals(resultIdColumnKey)){
                                theRow.setId(c.getValue());
                            }
                            theRow.setValue(c.getKey(), c.getValue());
                        }
                       tableModel.addRow(new SearchResultsRow(theRow));
                    }
                } else {
                	tableModel.setMoreData(false);
                }
                tableModel.fireTableDataChanged();
            }
        });
    }
    
    public List<ResultRow> getSelectedRows(){
        List<ResultRow> rows = new ArrayList<ResultRow>();
        for(Row row : tableModel.getSelectedRows()){
            rows.add(((SearchResultsRow)row).getResultRow());
        }
        return rows;
    }

    public List<String> getSelectedIds(){
        List<String> ids = new ArrayList<String>();
        for(Row row : tableModel.getSelectedRows()){
            ids.add(((SearchResultsRow)row).getResultRow().getId());
        }                
        return ids;
    }        
}

class SearchResultsRow extends Row {
    
    ResultRow row;
    
    public SearchResultsRow(ResultRow row){
       this.row = row;
    }
    
    @Override
    public Object getCellData(String columnId) {
        return row.getValue(columnId);        
    }
    
    @Override
    public void setCellData(String columnId, Object newValue) {
        row.setValue(columnId, newValue.toString());
    }
    
    @Override
    public String toString(){
        return row.toString();
    }
    
    public ResultRow getResultRow() {
        return row;
    }
}   

class FieldAscendingRowComparator extends RowComparator{
    
    String columnId;
    DataType type;
    
    FieldAscendingRowComparator(String columnId, DataType type) {
        this.columnId = columnId;
        this.type = type;
    }
    
    @Override
    public int compare(Row row0, Row row1) {
        String id0, id1;
        
        if (type.equals(DataType.STRING)) {
            id0 = (String)row0.getCellData(columnId);
            id1 = (String)row1.getCellData(columnId);
        } else {
            id0 = (String)row0.getCellData(columnId);
            id1 = (String)row1.getCellData(columnId);            
        }
        return id0.compareTo(id1);
    }    
}

class FieldDescendingRowComparator extends RowComparator{
    
    String columnId;
    DataType type;    
    
    FieldDescendingRowComparator(String columnId, DataType type) {
        this.columnId = columnId;
        this.type = type;        
    }    
    
    @Override
    public int compare(Row row0, Row row1) {
        String id0, id1;
        
        if (type.equals(DataType.STRING)) {
            id0 = (String)row0.getCellData(columnId);
            id1 = (String)row1.getCellData(columnId);
        } else {
            id0 = (String)row0.getCellData(columnId);
            id1 = (String)row1.getCellData(columnId);            
        }
        return id1.compareTo(id0);
    }    
}
