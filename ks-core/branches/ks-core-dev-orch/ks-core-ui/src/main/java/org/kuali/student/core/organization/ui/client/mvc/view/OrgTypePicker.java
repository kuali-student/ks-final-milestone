package org.kuali.student.core.organization.ui.client.mvc.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
//import org.kuali.student.core.organization.ui.client.view.SingleListItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class OrgTypePicker extends KSDropDown{

	//How to tie in with model to select hierarchy?
	
	private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);

	@Override
	public void onLoad() {
		super.onLoad();
		
		orgRpcServiceAsync.getOrgTypes(new AsyncCallback<List<OrgTypeInfo>>(){
			public void onFailure(Throwable caught) {
				
			}
			public void onSuccess(final List<OrgTypeInfo> orgTypes) {
	               final Map<String, String> ids = new LinkedHashMap<String, String>();
	                for (OrgTypeInfo orgTypeInfo : orgTypes) {
	                    ids.put(orgTypeInfo.getId(), orgTypeInfo.getName());
	                }
	                ListItems list = new ListItems() {

	                    @Override
	                    public List<String> getAttrKeys() {
	                        return null; // apparently unused
	                    }

	                    @Override
	                    public String getItemAttribute(String id, String attrkey) {
	                        return null; // apparently unused
	                    }

	                    @Override
	                    public int getItemCount() {
	                        return orgTypes.size();
	                    }

	                    @Override
	                    public List<String> getItemIds() {
	                        return new ArrayList<String>(ids.keySet());
	                    }

	                    @Override
	                    public String getItemText(String id) {
	                        return ids.get(id);
	                    }

	                };
	                
	                setEnabled(true);
	                setListItems(list);
/*	                ListItems items = orgTypeDropDown.getListItems();
	                
	                if (items != null) {
	                    orgTypeDropDown.selectItem(((SingleListItem) items).getItemIds().get(0));
	                }*/
			}
		});
	}
	
	
}
