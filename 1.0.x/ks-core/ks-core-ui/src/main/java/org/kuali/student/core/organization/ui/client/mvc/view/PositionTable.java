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

package org.kuali.student.core.organization.ui.client.mvc.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.kuali.student.common.ui.client.widgets.pagetable.GenericTableModel;
import org.kuali.student.common.ui.client.widgets.pagetable.PagingScrollTableBuilder;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.common.ui.client.widgets.searchtable.SearchColumnDefinition;
import org.kuali.student.core.organization.ui.client.mvc.model.OrgPositionPersonRelationInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.table.client.AbstractColumnDefinition;
import com.google.gwt.gen2.table.client.PagingOptions;
import com.google.gwt.gen2.table.client.PagingScrollTable;
import com.google.gwt.gen2.table.client.AbstractScrollTable.ResizePolicy;
import com.google.gwt.gen2.table.event.client.RowSelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PositionTable extends Composite{
    private List<ResultRow> resultRows = new ArrayList<ResultRow>();
    private List<AbstractColumnDefinition<ResultRow, ?>> columnDefs = new ArrayList<AbstractColumnDefinition<ResultRow, ?>>();
    private GenericTableModel<ResultRow> tableModel = new GenericTableModel<ResultRow>(resultRows);
    private PagingScrollTableBuilder<ResultRow> builder = new PagingScrollTableBuilder<ResultRow>();
    private String resultIdColumnKey;
    protected PagingScrollTable<ResultRow> pagingScrollTable;
    private VerticalPanel layout = new VerticalPanel();
    private PagingOptions pagingOptions;
    private String orgId;
    
    private OrgRpcServiceAsync orgProposalRpcServiceAsync = GWT.create(OrgRpcService.class);
    
    
    private PagingOptions createPagingOptions(PagingScrollTable<ResultRow> pagingScrollTable) {
        PagingOptions pagingOptions = new PagingOptions(pagingScrollTable); 
        pagingOptions.setPixelSize(pagingScrollTable.getOffsetWidth(), pagingOptions.getOffsetHeight());
        return pagingOptions;
    }
    
    public PositionTable(){
        super();                
        redraw();               
        layout.setWidth("100%");
        initWidget(layout);
        initializeTable();
        fetchPosition();
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
    
    public void initializeTable() {
        clearTable();
        
        builder = new PagingScrollTableBuilder<ResultRow>();
        builder.tablePixelSize(1100, 100);

        columnDefs = new ArrayList<AbstractColumnDefinition<ResultRow, ?>>();        
        columnDefs.add(new SearchColumnDefinition("Person ID", "person"));
        columnDefs.add(new SearchColumnDefinition("Position Name", "postion"));
        columnDefs.add(new SearchColumnDefinition("Position Description", "desc"));
//        columnDefs.add(new SearchColumnDefinition("Maximum No.", "max"));
//        columnDefs.add(new SearchColumnDefinition("Minimum No.", "min"));

        if(columnDefs.size() == 1){
            //FIXME auto adjusting width to fill table does not work with 1 column bug in incubator???
            columnDefs.get(0).setMinimumColumnWidth(370);
        }
        builder.columnDefinitions(columnDefs);
        tableModel.setColumnDefs(columnDefs);
        redraw(); 
    }
    
    public void fetchPosition(){
        if (orgId != null) {
            orgProposalRpcServiceAsync.getOrgPositionPersonRelation(orgId, new AsyncCallback<List<OrgPositionPersonRelationInfo>>() {

                @Override
                public void onFailure(Throwable caught) {
                    GWT.log("fetch failed", caught);

                }

                @Override
                public void onSuccess(List<OrgPositionPersonRelationInfo> result) {
                    resultRows.clear();
                    if (result != null) {
                        for (OrgPositionPersonRelationInfo positionRelation : result) {
                            if (positionRelation.getPersonId().size() > 0) {
                                for (Object personId : positionRelation.getPersonId()) {
                                    ResultRow theRow = new ResultRow();
                                    theRow.setValue("postion", positionRelation.getTitle());
                                    theRow.setValue("desc", positionRelation.getDesc());
//                                    theRow.setValue("max", positionRelation.getMaxNumRelations());
//                                    theRow.setValue("min", positionRelation.getMinNumRelations().toString());
                                    theRow.setValue("person", (String) personId);
                                    resultRows.add(theRow);
                                }
                            } else {
                                ResultRow theRow = new ResultRow();
                                theRow.setValue("postion", positionRelation.getTitle());
                                theRow.setValue("desc", positionRelation.getDesc());
                                theRow.setValue("person", " ");
//                                theRow.setValue("max", positionRelation.getMaxNumRelations());
//                                theRow.setValue("min", positionRelation.getMinNumRelations().toString());
                                resultRows.add(theRow);
                            }

                        }
                    }
                    redraw();
                }

            });

        }
    }
    
    public void addSelectionHandler(RowSelectionHandler selectionHandler){
        pagingScrollTable.getDataTable().addRowSelectionHandler(selectionHandler);
    }
    public void redraw(){
        tableModel.setRows(resultRows);
        pagingScrollTable = builder.build(tableModel);
        pagingScrollTable.setResizePolicy(ResizePolicy.FILL_WIDTH);
//        pagingOptions = createPagingOptions(pagingScrollTable);
        layout.clear();
//        layout.add(pagingOptions);
        layout.add(pagingScrollTable);
        pagingScrollTable.fillWidth();
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
    
    public String getOrgId(){
        return this.orgId;
    }
    
    public void setOrgId(String orgId){
        this.orgId=orgId;
    }
    
}
