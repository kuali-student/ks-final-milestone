package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class OrgDropdown extends KSDropDown{
	private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
	private String selectedId;
	
	@Override
	public void selectItem(String id) {
		selectedId = id;
		super.selectItem(id);
	}

	@Override
	public void onLoad() {
		
		ArrayList<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>(1);
		QueryParamValue queryParamValue = new QueryParamValue();
		queryParamValue.setKey("org.queryParam.orgType");
		queryParamValue.setValue("kuali.org.Department");
		queryParamValues.add(queryParamValue);
		orgRpcServiceAsync.searchForResults("org.search.orgQuickViewByOrgType", queryParamValues, new AsyncCallback<List<Result>>(){
			public void onFailure(Throwable caught) {
				
			}
			public void onSuccess(List<Result> results) {
				SimpleKeyValueListItems listItems = new SimpleKeyValueListItems();
				if(results!=null){
					for(Result result:results){
						String id="";
						String shortName="";
						for(ResultCell resultCell :result.getResultCells()){
							if("org.resultColumn.orgId".equals(resultCell.getKey())){
								id=resultCell.getValue();
							}
							else if("org.resultColumn.orgShortName".equals(resultCell.getKey())){
								shortName=resultCell.getValue();
							}
						}
						listItems.put(id,shortName);
					}
				}
				setListItems(listItems);
				selectItem(selectedId);				
			}
		});
		super.onLoad();
	}

	public class SimpleKeyValueListItems implements ListItems{
		HashMap<String,String> hashMap = new HashMap<String,String>();
		
		public void put(String key, String value){
			hashMap.put(key, value);
		}
		
		@Override
		public List<String> getAttrKeys() {
			return new ArrayList<String>(hashMap.keySet());
		}

		@Override
		public String getItemAttribute(String id, String attrkey) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getItemCount() {
			return hashMap.size();
		}

		@Override
		public List<String> getItemIds() {
			return getAttrKeys();
		}

		@Override
		public String getItemText(String id) {
			return hashMap.get(id);
		}
		
	};
}