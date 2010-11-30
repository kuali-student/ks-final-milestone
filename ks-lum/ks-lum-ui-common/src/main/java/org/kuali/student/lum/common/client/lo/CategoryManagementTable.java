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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.service.ServerPropertiesRpcService;
import org.kuali.student.common.ui.client.service.ServerPropertiesRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.pagetable.GenericTableModel;
import org.kuali.student.common.ui.client.widgets.pagetable.PagingScrollTableBuilder;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.common.ui.client.widgets.searchtable.SearchColumnDefinition;
import org.kuali.student.lum.common.client.lo.rpc.LoCategoryRpcService;
import org.kuali.student.lum.common.client.lo.rpc.LoCategoryRpcServiceAsync;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.table.client.AbstractColumnDefinition;
import com.google.gwt.gen2.table.client.PagingScrollTable;
import com.google.gwt.gen2.table.client.AbstractScrollTable.ResizePolicy;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is a description of what this class does - Gary Struthers don't forget to fill this in. 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public class CategoryManagementTable extends Composite {
    static String NAME_COLUMN_HEADER = "Name";
    static String TYPE_COLUMN_HEADER = "Type";
    static String STATE_COLUMN_HEADER = "State";
    static String ID_COLUMN_KEY = "id";
    static String NAME_COLUMN_KEY = "name";
    static String TYPE_COLUMN_KEY = "type";
    static String STATE_COLUMN_KEY = "state";
    private List<ResultRow> resultRows = new ArrayList<ResultRow>();
    private GenericTableModel<ResultRow> tableModel = new GenericTableModel<ResultRow>(resultRows);
    private PagingScrollTableBuilder<ResultRow> builder = new PagingScrollTableBuilder<ResultRow>();
    protected PagingScrollTable<ResultRow> pagingScrollTable;
    private VerticalPanel layout = new VerticalPanel();
    private static Boolean displayOnlyActiveCategories; // static global
    private boolean hideInactiveCategories = false;

    private LoCategoryRpcServiceAsync loCatRpcServiceAsync = GWT.create(LoCategoryRpcService.class);
    private static ServerPropertiesRpcServiceAsync serverProperties = GWT.create(ServerPropertiesRpcService.class);

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
    
    private void initCategoryManagementTable(SelectionPolicy selectionPolicy){
        layout.setWidth("100%");
        initWidget(layout);
        builder = new PagingScrollTableBuilder<ResultRow>();
        createColumnDefs();
        builder.tablePixelSize(400, 300);
        builder.setSelectionPolicy(selectionPolicy);
    }
    public CategoryManagementTable() {
        super();
        initCategoryManagementTable(SelectionPolicy.ONE_ROW);
    }
    /**
     * This constructs a CategoryManagementTable with an instance option
     * 
     * @param hideInactiveCategories
     */
    public CategoryManagementTable(boolean hideInactiveCategories, SelectionPolicy selectionPolicy) {
        super();
        this.hideInactiveCategories = hideInactiveCategories;
        initCategoryManagementTable(selectionPolicy);
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
        tableModel.setRows(resultRows);
        pagingScrollTable = builder.build(tableModel);
        pagingScrollTable.setResizePolicy(ResizePolicy.FILL_WIDTH);
        layout.clear();
        layout.add(pagingScrollTable);
        pagingScrollTable.fillWidth();
        pagingScrollTable.reloadPage();//FIXME Undesirable solution to work with GWT 2.0
    }
    
    public void redraw(List<ResultRow> filteredRows){
        tableModel.setRows(filteredRows);
        pagingScrollTable = builder.build(tableModel);
        pagingScrollTable.setResizePolicy(ResizePolicy.FILL_WIDTH);
        layout.clear();
        layout.add(pagingScrollTable);
        pagingScrollTable.fillWidth();
        pagingScrollTable.reloadPage();//FIXME Undesirable solution to work with GWT 2.0
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
        Set<Integer> selectedRows = pagingScrollTable.getDataTable().getSelectedRows();
        for(Integer i: selectedRows){
            rows.add(pagingScrollTable.getRowValue(i));
        }
        return rows;
    }
    public List<LoCategoryInfo> getSelectedLoCategoryInfos(){
        ResultRow resultRow = null;
        List<LoCategoryInfo> loCategoryInfos = new ArrayList<LoCategoryInfo>();
        Set<Integer> selectedRows = pagingScrollTable.getDataTable().getSelectedRows();
        if(selectedRows.isEmpty()) {
            return loCategoryInfos;
        }
        for(Integer i: selectedRows){
            resultRow = pagingScrollTable.getRowValue(i);
            LoCategoryInfo loCategoryInfo = new LoCategoryInfo();
            loCategoryInfo.setId(resultRow.getValue(ID_COLUMN_KEY));
            loCategoryInfo.setName(resultRow.getValue(NAME_COLUMN_KEY));
            loCategoryInfo.setType(resultRow.getValue(TYPE_COLUMN_KEY));
            loCategoryInfo.setState(resultRow.getValue(STATE_COLUMN_KEY));
            loCategoryInfos.add(loCategoryInfo);
        }
        return loCategoryInfos;
    }    

    public String getSelectedLoCategoryInfoId(){
        ResultRow resultRow = null;
        
        Set<Integer> selectedRows = pagingScrollTable.getDataTable().getSelectedRows();
        if(selectedRows.isEmpty()) {
            return null;
        }
        String id = null;
        for(Integer i: selectedRows){
            resultRow = pagingScrollTable.getRowValue(i);
            id = resultRow.getValue(ID_COLUMN_KEY);
/*            loCategoryInfo.setId(resultRow.getValue(ID_COLUMN_KEY));
            loCategoryInfo.setName(resultRow.getValue(NAME_COLUMN_KEY));
            loCategoryInfo.setType(resultRow.getValue(TYPE_COLUMN_KEY));
            loCategoryInfo.setState(resultRow.getValue(STATE_COLUMN_KEY));
*/
            break; // just get first one
        }
        return id;

    }   
    
    private void createColumnDefs() {
        List<AbstractColumnDefinition<ResultRow, ?>> columnDefs=new ArrayList<AbstractColumnDefinition<ResultRow, ?>>();
        SearchColumnDefinition columnDef = new SearchColumnDefinition(NAME_COLUMN_HEADER, NAME_COLUMN_KEY);
        columnDef.setColumnSortable(false);
        columnDefs.add(columnDef);
        columnDef = new SearchColumnDefinition(TYPE_COLUMN_HEADER, TYPE_COLUMN_KEY);
        columnDef.setColumnSortable(false);
        columnDefs.add(columnDef);            
        if (!isHideInactiveCategories()) {
            columnDef = new SearchColumnDefinition(STATE_COLUMN_HEADER, STATE_COLUMN_KEY);
            columnDef.setColumnSortable(false);
            columnDefs.add(columnDef);            
        }
        if(columnDefs.size() == 1){
            columnDefs.get(0).setMinimumColumnWidth(370);
        }
        builder.columnDefinitions(columnDefs);
    }
    

    
    private List<LoCategoryInfo> filterResults(List<LoCategoryInfo> result) {

       if(isHideInactiveCategories()) {
            List<LoCategoryInfo> filteredResult = new ArrayList<LoCategoryInfo>();
            for(LoCategoryInfo info : result) {
                if (info.getState().equals("active") ) {
                    filteredResult.add(info);
                }
            }
            return filteredResult;
        } 
        return result;   
    }
    
    public void loadTable() {
        loCatRpcServiceAsync.getLoCategories("kuali.loRepository.key.singleUse", new KSAsyncCallback<List<LoCategoryInfo>>() {
            @Override
            public void handleFailure(Throwable caught) {
                GWT.log("getLoCategories failed", caught);
                Window.alert("Get LoCategories failed");
            }

            @Override
            public void onSuccess(List<LoCategoryInfo> results) {

                List<LoCategoryInfo> filteredResults = filterResults(results);
                loadTable(filteredResults);
                /*
                List<LoCategoryInfo> filteredResults = filterResults(results);
                loadTable(filteredResults);
                */
            }
        }); 
    }
    
    private void loadTable(List<LoCategoryInfo> loCategoryInfos) {
        resultRows.clear();
        HashSet<String> hashSet = new HashSet<String>();
        String id = null;
        for(LoCategoryInfo info : loCategoryInfos) {
            ResultRow resultRow = new ResultRow();
            id = info.getId();
            if(!hashSet.contains(id)) {
                hashSet.add(id);
                resultRow.setValue(ID_COLUMN_KEY, id);
                resultRow.setValue(NAME_COLUMN_KEY, info.getName());
                resultRow.setValue(TYPE_COLUMN_KEY, info.getType());
                resultRow.setValue(STATE_COLUMN_KEY, info.getState());
                resultRows.add(resultRow);                
            }
        }
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
