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
	private boolean loaded = false;

	@Override
	public void onLoad() {
		super.onLoad();
		
		if (!loaded) {
            orgRpcServiceAsync.getOrgPersonRelationTypes(new AsyncCallback<List<OrgPersonRelationTypeInfo>>() {
                public void onFailure(Throwable caught) {

                }

                public void onSuccess(List<OrgPersonRelationTypeInfo> posTypes) {
                    final Map<String, String> map = new LinkedHashMap<String, String>();
                    for (OrgPersonRelationTypeInfo info : posTypes) {
                        map.put(info.getId(), info.getName());
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
                    loaded=true;
                    setEnabled(true);
                    setListItems(list);
                }
            });
        }
	}
	
	
}
