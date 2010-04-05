package org.kuali.student.core.organization.ui.client.mvc.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
//import org.kuali.student.core.organization.ui.client.view.SingleListItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class OrgRelationTypePicker extends KSDropDown{

	//How to tie in with model to select hierarchy?
	
	private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
	private ListItems orgRelTypeList;

	@Override
	public void onLoad() {
		super.onLoad();
		
		orgRpcServiceAsync.getOrgOrgRelationTypes(new AsyncCallback<List<OrgOrgRelationTypeInfo>>(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
			public void onSuccess(final List<OrgOrgRelationTypeInfo> orgRelTypes) {
			    final Map<String,String> map = new LinkedHashMap<String, String>();
                for(OrgOrgRelationTypeInfo info : orgRelTypes) {
                    map.put(info.getId(), info.getName());
                    //Add the reverse relation types in the dropdown and add the prefix REV to differentiate from the original key
                    map.put("REV_" + info.getId(), info.getRevName());
                }
                orgRelTypeList = new ListItems() {

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
                    setListItems(orgRelTypeList);
	                setEnabled(true);
	                setInitialized(true);
	                
/*	                ListItems items = orgTypeDropDown.getListItems();
	                
	                if (items != null) {
	                    orgTypeDropDown.selectItem(((SingleListItem) items).getItemIds().get(0));
	                }*/
			}
		});
	}
	
	
}
