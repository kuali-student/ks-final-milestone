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

package org.kuali.student.lum.common.client.lo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.SearchRpcService;
import org.kuali.student.common.ui.client.service.SearchRpcServiceAsync;
import org.kuali.student.common.ui.client.service.ServerPropertiesRpcService;
import org.kuali.student.common.ui.client.service.ServerPropertiesRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.common.ui.client.widgets.table.scroll.Column;
import org.kuali.student.common.ui.client.widgets.table.scroll.DefaultTableModel;
import org.kuali.student.common.ui.client.widgets.table.scroll.Row;
import org.kuali.student.common.ui.client.widgets.table.scroll.Table;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultCell;
import org.kuali.student.r1.common.search.dto.SearchResultRow;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;


/**
 * This is a description of what this class does - Gary Struthers don't forget to fill this in. 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public class CategoryManagementTable extends Composite {
    static String NAME_COLUMN_HEADER = "Category";
    static String TYPE_COLUMN_HEADER = "Type";
    static String STATE_COLUMN_HEADER = "State";
    static String ID_COLUMN_KEY = "id";
    static String NAME_COLUMN_KEY = "name";
    static String TYPE_COLUMN_KEY = "type";
    static String TYPE_NAME_COLUMN_KEY = "typeName";
    static String STATE_COLUMN_KEY = "state";
    private List<ResultRow> resultRows = new ArrayList<ResultRow>();
    private DefaultTableModel model = new DefaultTableModel();	//Contains Category rows that go into 'Select Categories' KSLightBox [KSLAB-2091]
    private Table table = new Table();
    //private GenericTableModel<ResultRow> tableModel = new GenericTableModel<ResultRow>(resultRows);
    //private PagingScrollTableBuilder<ResultRow> builder = new PagingScrollTableBuilder<ResultRow>();
    //protected PagingScrollTable<ResultRow> pagingScrollTable;
    private FlowPanel layout = new FlowPanel();
    private static Boolean displayOnlyActiveCategories; // static global
    private boolean hideInactiveCategories = false;

    private SearchRpcServiceAsync searchDispatcherAsync = GWT.create(SearchRpcService.class); //KSLAB-2091

    private static ServerPropertiesRpcServiceAsync serverProperties = GWT.create(ServerPropertiesRpcService.class);

    // Categories to filter out after the are loaded from the backend
    // See KSLAB-1871
    private List<LoCategoryInfo> loCategoriesToFilter = new ArrayList<LoCategoryInfo>();
    
    class CategoryRow extends Row{
    	ResultRow row;
    	
    	public CategoryRow(ResultRow row){
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
		
		public ResultRow getResultRowData(){
			return row;
		}
	}
    
    public Table getTable(){
    	return table;
    }
    
    /**
     * This method should be called before constructor so config flag is pre-set
     * only needs to be called once. It's a static flag that only changes when the 
     * server is started
     * 
     */
    public static void setDisplayOnlyActiveCategories() {
        if (null == displayOnlyActiveCategories) {
            serverProperties.get("ks.lum.ui.displayOnlyActiveLoCategories", new KSAsyncCallback<String>() {
                @Override
                public void handleFailure(Throwable caught) {
                    GWT.log("get displayOnlyActiveLoCategories failed", caught);
                    Window.alert("Failed to get displayOnlyActiveLoCategories setting");
                }
    
                @Override
                public void onSuccess(String result) {
                    if (result != null) {
                        displayOnlyActiveCategories = Boolean.parseBoolean(result);
                    }
                }
            });
        }  
    }
    
    private void initCategoryManagementTable(boolean isMultiSelect){
        layout.setWidth("100%");
        table.setWidth("550px");
        table.getScrollPanel().setHeight("400px");
        initWidget(layout);
        createColumnDefs();
        if(isMultiSelect){
        	model.setMultipleSelectable(true);
        	model.installCheckBoxRowHeaderColumn();
        }
        else{
        	model.setMultipleSelectable(false);
        }
        table.setTableModel(model);
        
        layout.add(table);
    }
    public CategoryManagementTable() {
        super();
        initCategoryManagementTable(false);
    }
    /**
     * Constructor that allows us to filter categories.
     * <p>
     * Currently used to filter categories that are already in the picker. 
     * <p>
     *  See KSLAB-1871
     *
     * @param hideInactiveCategories
     * @param isMultiSelect
     * @param loCategoriesToFilter categories to filter out
     */
    public CategoryManagementTable(boolean hideInactiveCategories, boolean isMultiSelect, List<LoCategoryInfo> loCategoriesToFilter) {
        super();
        this.hideInactiveCategories = hideInactiveCategories;
        this.loCategoriesToFilter = loCategoriesToFilter;  // needed in constructor due to async
        initCategoryManagementTable(isMultiSelect);
    }
    /**
     * This constructs a CategoryManagementTable with an instance option
     * 
     * @param hideInactiveCategories
     */
    public CategoryManagementTable(boolean hideInactiveCategories, boolean isMultiSelect) {
        super();
        this.hideInactiveCategories = hideInactiveCategories;
        initCategoryManagementTable(isMultiSelect);
    }
    /**
     * Two flags control whether to show rows with inactive categories and the state column.
     * hideInactiveCategories can be set per table instance
     * displayOnlyActiveCategories is set at Lum startup
     * hideInactiveCategories overrides displayOnlyActiveCategories
     * @return true to show all rows and State column
     */
    public boolean isHideInactiveCategories() {
        if(hideInactiveCategories){
            return true;
        } 
        if((displayOnlyActiveCategories == null)||( displayOnlyActiveCategories.booleanValue() == false)){
            return false;
        }else {
            return true;
        }
    }
    /**
     * @param show or hide inactive rows and State column 
     */
    public void setHideInactiveCategories(boolean show) {
        this.hideInactiveCategories = show;
    }
    public void redraw(){
    	model.clearRows();
    	for(ResultRow row: resultRows){
    		model.addRow(new CategoryRow(row));
    	}
    	model.fireTableDataChanged();
    }
    
    public void redraw(List<ResultRow> filteredRows){
    	model.clearRows();
    	table.removeAllRows();
    	for(ResultRow row: filteredRows){
    		model.addRow(new CategoryRow(row));
    	}
    	model.setCurrentIndex(0);
    	model.fireTableDataChanged();
    }

    public void clearTable(){
        resultRows.clear();
        redraw();        
    }
    
    public void removeSelected(){
        for(ResultRow r: getSelectedRows()){
            resultRows.remove(r);
        }
        this.redraw();
    }
    public List<ResultRow> getAllRows(){
        List<ResultRow> rows = new ArrayList<ResultRow>();
        for(ResultRow r: resultRows){
            rows.add(r);
        }
        return rows;
    }    
    public List<ResultRow> getSelectedRows(){
        List<ResultRow> rows = new ArrayList<ResultRow>();
        List<Row> selectedRows = model.getSelectedRows();
        for(Row r: selectedRows){
            rows.add(((CategoryRow)r).getResultRowData());
        }
        return rows;
    }
    public List<LoCategoryInfo> getSelectedLoCategoryInfos(){
        List<LoCategoryInfo> loCategoryInfos = new ArrayList<LoCategoryInfo>();
        List<Row> selectedRows = model.getSelectedRows();
        if(selectedRows.isEmpty()) {
            return loCategoryInfos;
        }
        for(Row r: selectedRows){
            LoCategoryInfo loCategoryInfo = new LoCategoryInfo();
            loCategoryInfo.setId(r.getCellData(ID_COLUMN_KEY).toString());
            loCategoryInfo.setName(r.getCellData(NAME_COLUMN_KEY).toString());
            loCategoryInfo.setTypeKey(r.getCellData(TYPE_COLUMN_KEY).toString());
            loCategoryInfo.setStateKey(r.getCellData(STATE_COLUMN_KEY).toString());
            loCategoryInfos.add(loCategoryInfo);
        }
        return loCategoryInfos;
    }    

    public String getSelectedLoCategoryInfoId(){ 
        List<Row> selectedRows = model.getSelectedRows();
        if(selectedRows.isEmpty()) {
            return null;
        }
        String id = null;
        for(Row r: selectedRows){
            id = r.getCellData(ID_COLUMN_KEY).toString();
            break;
        }
        return id;

    }   
    
    private void createColumnDefs() {
    	
    	Column name = new Column();
    	name.setName(NAME_COLUMN_HEADER);
    	name.setId(NAME_COLUMN_KEY);
    	name.setSortable(false);
    	model.addColumn(name);
    	name.setWidth("250px");
    	
    	Column type = new Column();
    	type.setName(TYPE_COLUMN_HEADER);
    	type.setId(TYPE_NAME_COLUMN_KEY);
    	type.setSortable(false);
    	model.addColumn(type);
    	
        if (!isHideInactiveCategories()) {
        	Column state = new Column();
        	state.setName(STATE_COLUMN_HEADER);
        	state.setId(STATE_COLUMN_KEY);
        	state.setSortable(false);
        	model.addColumn(state);          
        }
    }
    
    /**
     * 
     * This method will filter out categories that should be excluded 
     * from the list (e.g. those already in the picker).
     * <p>
     * It is called from the filterResults method.
     * <p>
     * See KSLAB-1871
     * 
     * @param results
     * @return
     */
    private List<LoCategoryInfo> filterResultsWithExcludedCategories(List<LoCategoryInfo> results){
        if (loCategoriesToFilter == null || loCategoriesToFilter.size() == 0){
            // If nothing to filter just return results passed in
            return results;
        }
        List<LoCategoryInfo> filteredResults = new ArrayList<LoCategoryInfo>();
        for(LoCategoryInfo result : results) {
            boolean shouldExcludeRow = false;
            for (LoCategoryInfo toFilter : loCategoriesToFilter) {
                String name = toFilter.getName();
                String type = toFilter.getTypeKey();
                if (result.getName().equals(name) && result.getTypeKey().equals(type)){
                    shouldExcludeRow = true;
                    break;
                }
            } 
            if (!shouldExcludeRow){
                filteredResults.add(result);
            }
        }
        return filteredResults;
    }

    
    private List<LoCategoryInfo> filterResults(List<LoCategoryInfo> result) {

       // Filter if already in picker etc
       result = filterResultsWithExcludedCategories(result);
  
       if(isHideInactiveCategories()) {
            List<LoCategoryInfo> filteredResult = new ArrayList<LoCategoryInfo>();
            for(LoCategoryInfo info : result) {
                if ("Active".equals(info.getStateKey())) {
                    filteredResult.add(info);
                }
            }
            return filteredResult;
        } 
        return result;   
    }
    
    public void loadTable(final Callback<Boolean> callback) {
    	table.displayLoading(true);
    	/* KSLAB-2091
    	 * 
    	 * This is the new way to initiate a query for loCategories:
    	 * 		It is a searchDispatcherAsync call that returns a SearchResult on success;
    	 * 			the SearchResult has the necessary CategoryType name info.
    	 * 			Furthermore this is the usual way scenarios similar to Browse Categories are implemented.
    	 * 
    	 * 	[The old way was a loCatRpcServiceAsync call that returned LoCategoryInfos on success;
    	 * 			the loCategoryInfos did not have necessary CategoryType name info,
    	 * 				and loCategories cannot be changed without affecting versioning 
    	 * 				(which does not seem worth it given this present fix with searchDispatcherAsync).
    	 * 			Furthermore the old way was not the usual way scenarios similar to Browse Categories are implemented.]
    	 * 
    	 * KSLAB-2091	 
    	 */
    	searchDispatcherAsync.search(new SearchRequest("lo.search.loCategories"), new KSAsyncCallback<SearchResult>() {
            @Override
            public void handleFailure(Throwable caught) {
                GWT.log("lo.search.loCategories failed", caught);
                Window.alert("lo.search.loCategories failed");
                callback.exec(false);
            }

			@Override
			public void onSuccess(SearchResult results) {
				loadTable(results);
                callback.exec(true);
                table.displayLoading(false);
			}
        });
    	//KSLAB-2091
    }
    
    private void loadTable(SearchResult results) {	/*Additional overload of loadTableto accommodate call from new loadTable(final Callback<Boolean> callback), 
    																											which is directly above [KSLAB-2091]*/
    	
        resultRows.clear();
        
        HashSet<String> hashSet = new HashSet<String>();
        String curCatID = null, curCatNAME= null, curCatTYPEID= null, curCatTYPENAME= null, curCatSTATE=null;
        String curSearchResultCellKEY= null;
        
        for(SearchResultRow curSearchResultRow: results.getRows()) {
        	for(SearchResultCell curSearchResultCell: curSearchResultRow.getCells()){	//Extracts necessary fields that will be added to resultRow below.
        		curSearchResultCellKEY= curSearchResultCell.getKey();
        		if(curSearchResultCellKEY.equals("lo.resultColumn.categoryId")){
        			curCatID= curSearchResultCell.getValue();
        		}else if(curSearchResultCellKEY.equals("lo.resultColumn.categoryName")){
        			curCatNAME= curSearchResultCell.getValue();
        		}else if(curSearchResultCellKEY.equals("lo.resultColumn.categoryType")){
        			curCatTYPEID= curSearchResultCell.getValue();
        		}else if(curSearchResultCellKEY.equals("lo.resultColumn.categoryTypeName")){
        		    curCatTYPENAME= curSearchResultCell.getValue();
                }else if(curSearchResultCellKEY.equals("lo.resultColumn.categoryState")){	//From new bean in lo-search-config.xml
        			curCatSTATE= curSearchResultCell.getValue();
                }
        	}
        	
            if (isHideInactiveCategories() && curCatSTATE.equalsIgnoreCase("inactive")) {
                continue;
            }

            ResultRow resultRow = new ResultRow();
            
            if (!hashSet.contains(curCatID)) {
                hashSet.add(curCatID);
                resultRow.setValue(ID_COLUMN_KEY, curCatID);
                resultRow.setValue(NAME_COLUMN_KEY, curCatNAME);
                resultRow.setValue(TYPE_COLUMN_KEY, curCatTYPEID);
                resultRow.setValue(TYPE_NAME_COLUMN_KEY, curCatTYPENAME);
                resultRow.setValue(STATE_COLUMN_KEY, curCatSTATE);
                resultRows.add(resultRow);                
            }
        }	//Correct resultRows now loaded [KSLAB-2091]
        Collections.sort(resultRows);
        redraw();
    }

    public List<ResultRow> getRowsByType(String type){
        List<ResultRow> bufferList = new ArrayList<ResultRow>();
        for(ResultRow row : resultRows) {
            if(row.getValue(TYPE_COLUMN_KEY).contains(type)){
                bufferList.add(row);
            }
        }
        return bufferList;
    }
    
    public List<ResultRow> getRowsLikeName(String name){
        List<ResultRow> bufferList = new ArrayList<ResultRow>();
        for(ResultRow row : resultRows) {
            String nameValue = row.getValue(NAME_COLUMN_KEY);
            if(nameValue != null) {
                String[] words = nameValue.split("\\W");
                for(String word : words){
                    if(word.toUpperCase().startsWith(name.toUpperCase())){
                        bufferList.add(row);
                        break;
                    }                    
                }
            }
        }
        return bufferList;
    }
}
