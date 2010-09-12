package org.kuali.student.core.organization.ui.client.mvc.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
//import org.kuali.student.core.organization.ui.client.view.SingleListItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class OrgPositionTypePicker extends KSDropDown{

	//How to tie in with model to select hierarchy?
    private ListItems orgPosTypeList;
	private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);

	@Override
	public void onLoad() {
		super.onLoad();
		
		orgRpcServiceAsync.getOrgPersonRelationTypes(new AsyncCallback<List<OrgPersonRelationTypeInfo>>(){
			public void onFailure(Throwable caught) {
				
			}
            public void onSuccess(List<OrgPersonRelationTypeInfo> posTypes) {
                final Map<String,String> map = new LinkedHashMap<String, String>();
                for(OrgPersonRelationTypeInfo info : posTypes) {
                    map.put(info.getId(), info.getName());
                }
                ListItems list = new ListItems()  {

                    @Override
                    public List<String> getAttrKeys() {
                        return null; //apparently unused
                    }

                    @Override
                    public String getItemAttribute(String id, String attrkey) {
                        return null; //apparently unused
                    }

                    @Override
                    public int getItemCount() {
                        return map.size();
                    }

                    @Override
                    public List<String> getItemIds() {
                        return new ArrayList<String>(map.keySet());
                    }

                    @Override
                    public String getItemText(String id) {
                        return map.get(id);
                    }
                };
                setEnabled(true);
                setListItems(list);
            }
		});
	}
	
	
}
