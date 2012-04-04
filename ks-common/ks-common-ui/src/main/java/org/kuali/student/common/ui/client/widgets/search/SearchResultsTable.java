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
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.SearchRpcServiceAsync;
import org.kuali.student.common.ui.client.service.SearchServiceFactory;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.FieldLayoutComponent;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.common.ui.client.widgets.table.scroll.Column;
import org.kuali.student.common.ui.client.widgets.table.scroll.DefaultTableModel;
import org.kuali.student.common.ui.client.widgets.table.scroll.RetrieveAdditionalDataHandler;
import org.kuali.student.common.ui.client.widgets.table.scroll.Row;
import org.kuali.student.common.ui.client.widgets.table.scroll.RowComparator;
import org.kuali.student.common.ui.client.widgets.table.scroll.Table;
import org.kuali.student.r1.common.assembly.data.Data.DataType;
import org.kuali.student.r1.common.assembly.data.LookupResultMetadata;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultCell;
import org.kuali.student.r1.common.search.dto.SearchResultRow;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

@Deprecated
public class SearchResultsTable extends Composite{

    protected final int PAGE_SIZE = 10;
    
    protected SearchRpcServiceAsync searchRpcServiceAsync = SearchServiceFactory.getSearchService();
    
    protected VerticalPanel layout = new VerticalPanel();
    
    private DefaultTableModel tableModel;
    protected String resultIdColumnKey;
    protected String resultDisplayKey;  
    protected SearchRequest searchRequest;
    private Table table = new Table();
    protected boolean isMultiSelect = true;
    protected boolean withMslable = true;
    protected KSButton mslabel = new KSButton("Modify your search?", ButtonStyle.DEFAULT_ANCHOR);
    
    protected List<Callback<List<SelectedResults>>> selectedCompleteCallbacks = new ArrayList<Callback<List<SelectedResults>>>();
	
    public KSButton getMslabel() {
		return mslabel;
	}

	public void setMslabel(KSButton mslabel) {
		this.mslabel = mslabel;
	}

    public void removeContent() {
        table.removeContent();
	}
	public SearchResultsTable(){
        super();
        redraw();
        layout.setWidth("100%");
        initWidget(layout);
    }
    
    public void redraw(){
        layout.clear();      
    }
    
    public void setMutipleSelect(boolean isMultiSelect){
    	this.isMultiSelect = isMultiSelect;
    }
 
	public void setWithMslable(boolean withMslable) {
		this.withMslable = withMslable;
	}
	
	public void initializeTable(List<LookupResultMetadata> listResultMetadata, String resultIdKey, String resultDisplayKey){
        initializeTable("", listResultMetadata, resultIdKey, resultDisplayKey);
	}

	//FIXME do we really need to recreate the table for every refresh?
	public void initializeTable(String searchId, List<LookupResultMetadata> listResultMetadata, String resultIdKey, String resultDisplayKey){  
    	
    	//creating a new table because stale data was corrupting new searches
    	table = new Table();
    	table.removeAllRows();
        this.resultIdColumnKey = resultIdKey;
        this.resultDisplayKey = resultDisplayKey;
        
        tableModel = new DefaultTableModel();
        tableModel.setMultipleSelectable(isMultiSelect);

        //create table heading
        for (LookupResultMetadata r: listResultMetadata){
            if(!r.isHidden()){
                Column col1 = new Column();
                col1.setId(r.getKey());                
                String header = "";                
                // KSLAB2571 KSCM1326 - adds SerachID to message override hierarchy
                if (Application.getApplicationContext().getMessage(searchId + ":"+ r.getKey() + FieldLayoutComponent.NAME_MESSAGE_KEY) != null) {
                    header = Application.getApplicationContext().getMessage(searchId + ":"+ r.getKey() + FieldLayoutComponent.NAME_MESSAGE_KEY);
                } else if (Application.getApplicationContext().getMessage(r.getKey() + FieldLayoutComponent.NAME_MESSAGE_KEY) != null) {
                    header = Application.getApplicationContext().getMessage(r.getKey() + FieldLayoutComponent.NAME_MESSAGE_KEY);
                } else {
                    header = Application.getApplicationContext().getUILabel("", null, null, r.getName());
                }     
                          
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
        if(isMultiSelect){
        	tableModel.installCheckBoxRowHeaderColumn();
        }
        
        table.getScrollPanel().setHeight("300px");
        table.setTableModel(tableModel);
        
        table.addRetrieveAdditionalDataHandler(new RetrieveAdditionalDataHandler(){
			@Override
			public void onAdditionalDataRequest() {
				 performOnDemandSearch(tableModel.getRowCount(), PAGE_SIZE);
                 //tableModel.fireTableDataChanged();
			}
		});

        redraw();
        layout.add(table);
  }   
    
    public void performSearch(SearchRequest searchRequest, List<LookupResultMetadata> listResultMetadata, String resultIdKey, String resultDisplayKey, boolean pagedResults) {
        this.searchRequest = searchRequest;
        initializeTable(listResultMetadata, resultIdKey, resultDisplayKey);
        if (this.searchRequest.getSearchKey().toLowerCase().contains("cross")) {
            //FIXME Do we still need this if condition?
            // Added an else to the if(pagedResults) line to prevent searches being executed
            // twice if the search name includes cross
            performOnDemandSearch(0, 0);
        }
        else if(pagedResults){
        	performOnDemandSearch(0, PAGE_SIZE);
        }
        else{
        	performOnDemandSearch(0, 0);
        }
    }
    
    // KSLAB2571 KSCM1326 - Overloaded method to add SerachID to message override hierarchy 
    public void performSearch(String searchId, SearchRequest searchRequest, List<LookupResultMetadata> listResultMetadata, String resultIdKey, String resultDisplayKey, boolean pagedResults) {
        this.searchRequest = searchRequest;
        initializeTable(searchId, listResultMetadata, resultIdKey, resultDisplayKey);
        if (this.searchRequest.getSearchKey().toLowerCase().contains("cross")) {
            //FIXME Do we still need this if condition?
            // Added an else to the if(pagedResults) line to prevent searches being executed
            // twice if the search name includes cross
            performOnDemandSearch(0, 0);
        }
        else if(pagedResults){
            performOnDemandSearch(0, PAGE_SIZE);
        }
        else{
            performOnDemandSearch(0, 0);
        }
    }
    
    public void performSearch(SearchRequest searchRequest, List<LookupResultMetadata> listResultMetadata, String resultIdKey, boolean pagedResults){
        this.performSearch(searchRequest, listResultMetadata, resultIdKey, null, true);
    }    
    
    public void performSearch(SearchRequest searchRequest, List<LookupResultMetadata> listResultMetadata, String resultIdKey){
        this.performSearch(searchRequest, listResultMetadata, resultIdKey, true);
    }    
    
    protected void performOnDemandSearch(int startAt, int size) {
                
    	table.displayLoading(true);
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
                table.displayLoading(false);
            }

            @Override
            public void onSuccess(SearchResult results) {
            	table.addContent();
            	
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
                	
                	//add no matches found if no search results
                	if(searchRequest.getStartAt() == 0){
	                	table.removeContent();
	                	VerticalFlowPanel noResultsPanel = new VerticalFlowPanel();
	                	noResultsPanel.add(new KSLabel("No matches found"));
	                	if(withMslable) noResultsPanel.add(mslabel);
	                	noResultsPanel.addStyleName("ks-no-results-message");
	                	table.getScrollPanel().add(noResultsPanel);
                	}
                }
//                tableModel.selectFirstRow();
                tableModel.fireTableDataChanged();
                table.displayLoading(false);
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
    
    public void addSelectionCompleteCallback(Callback<List<SelectedResults>> callback){
        selectedCompleteCallbacks.add(callback);
    }
}

class SearchResultsRow extends Row {

    ResultRow row;

    public SearchResultsRow(ResultRow row) {
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
    public String toString() {
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
