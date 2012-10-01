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

import static org.kuali.student.core.organization.ui.client.mvc.view.CommonConfigurer.getLabel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.widgets.pagetable.GenericTableModel;
import org.kuali.student.common.ui.client.widgets.pagetable.PagingScrollTableBuilder;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.common.ui.client.widgets.searchtable.SearchColumnDefinition;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.MembershipInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.table.client.AbstractColumnDefinition;
import com.google.gwt.gen2.table.client.PagingScrollTable;
import com.google.gwt.gen2.table.client.AbstractScrollTable.ResizePolicy;
import com.google.gwt.gen2.table.event.client.RowSelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MembersTable extends Composite{
    private static final String POSITION = "position";
    private static final String LNAME = "lname";
    private static final String FNAME = "fname";

    private List<ResultRow> resultRows = new ArrayList<ResultRow>();
    private List<AbstractColumnDefinition<ResultRow, ?>> columnDefs = new ArrayList<AbstractColumnDefinition<ResultRow, ?>>();
    private GenericTableModel<ResultRow> tableModel = new GenericTableModel<ResultRow>(resultRows);
    private PagingScrollTableBuilder<ResultRow> builder = new PagingScrollTableBuilder<ResultRow>();
    protected PagingScrollTable<ResultRow> pagingScrollTable;
    private VerticalPanel layout = new VerticalPanel();
    private String orgId;
    private HashMap<String,String> positionTypes;
    private List<String> personIds;
    private List<MembershipInfo> members;

    private OrgRpcServiceAsync orgProposalRpcServiceAsync = GWT.create(OrgRpcService.class);

    public MembersTable(){
        super();
        redraw();
        layout.setWidth("100%");
        layout.addStyleName("KS-Org-MemberTable");
        initWidget(layout);
        initializeTable();

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
        columnDefs.add(new SearchColumnDefinition(getLabel("orgMembersTableFirstName"), FNAME));
        columnDefs.add(new SearchColumnDefinition(getLabel("orgMembersTableLastName"), LNAME));
        columnDefs.add(new SearchColumnDefinition(getLabel("orgMembersTablePoistionName"), POSITION));

        builder.columnDefinitions(columnDefs);
        tableModel.setColumnDefs(columnDefs);
        redraw();
    }



    public void addSelectionHandler(RowSelectionHandler selectionHandler){
        pagingScrollTable.getDataTable().addRowSelectionHandler(selectionHandler);
    }
    public void redraw(){
        tableModel.setRows(resultRows);
        pagingScrollTable = builder.build(tableModel);
        pagingScrollTable.setResizePolicy(ResizePolicy.FILL_WIDTH);
        layout.clear();
        layout.add(pagingScrollTable);
        pagingScrollTable.reloadPage();
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


    public void fetchMemberTable(){
        if(orgId!=null){
            orgProposalRpcServiceAsync.getOrgPersonRelationTypes(new KSAsyncCallback<List<OrgPersonRelationTypeInfo>>(){

                @Override
                public void onSuccess(List<OrgPersonRelationTypeInfo> result) {
                    positionTypes = new HashMap<String, String>();
                    for(OrgPersonRelationTypeInfo positionType:result){
                        positionTypes.put(positionType.getId(), positionType.getName());
                    }
                    fetchOrgPersonRelations();

                }

            });

        }

    }

    public void fetchOrgPersonRelations(){
        if(orgId!=null){
            orgProposalRpcServiceAsync.getOrgPersonRelationsByOrg(orgId, new KSAsyncCallback<List<OrgPersonRelationInfo>>(){

                @Override
                public void onSuccess(List<OrgPersonRelationInfo> result) {
                    members = new ArrayList<MembershipInfo>();
                    personIds = new ArrayList<String>();
                    for(OrgPersonRelationInfo relation: result){
                        MembershipInfo member = new MembershipInfo();
                        member.setPositionTypeKey(relation.getType());
                        member.setPositionName(positionTypes.get(relation.getType()));
                        member.setEntityNameId(relation.getPersonId());
                        personIds.add(relation.getPersonId());
                        members.add(member);
                    }

                    fetchPersonInfo();
                }

            });
        }
    }

    private void fetchPersonInfo(){
        if (personIds.size() > 0) {
            orgProposalRpcServiceAsync.getNamesForPersonIds(personIds, new KSAsyncCallback<Map<String, MembershipInfo>>() {

                @Override
                public void handleFailure(Throwable caught) {
                    GWT.log("fetch failed", caught);
                }

                @Override
                public void onSuccess(Map<String, MembershipInfo> result) {
                    resultRows.clear();
                    for (MembershipInfo member : members) {
                        ResultRow theRow = new ResultRow();
                        MembershipInfo retrievedMember = result.get(member.getEntityNameId());
                        member.setFirstName(retrievedMember.getFirstName());
                        member.setLastName(retrievedMember.getLastName());

                        theRow.setValue(FNAME, member.getFirstName());
                        theRow.setValue(LNAME, member.getLastName());
                        theRow.setValue(POSITION, member.getPositionName());
                        resultRows.add(theRow);
                    }
                    redraw();
                }

            });
        }
    }

}
