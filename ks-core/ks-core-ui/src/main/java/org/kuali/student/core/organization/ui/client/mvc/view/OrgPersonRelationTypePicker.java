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

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.r2.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;

import com.google.gwt.core.client.GWT;

public class OrgPersonRelationTypePicker extends KSDropDown{
    private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
    private ListItems orgRelTypeList;
    private String orgId;
    
    public void setOrgId(String orgId){
        this.orgId=orgId;
    }
    
    @Override
    public void onLoad() {
        super.onLoad();
        if (orgId != null) {
            orgRpcServiceAsync.getPositionRestrictionsByOrg(orgId,new KSAsyncCallback<List<OrgPositionRestrictionInfo>>() {

                public void onSuccess(final List<OrgPositionRestrictionInfo> orgRelTypes) {
                    final Map<String, String> map = new LinkedHashMap<String, String>();
                    for (OrgPositionRestrictionInfo info : orgRelTypes) {
                    	map.put(info.getOrgPersonRelationTypeKey(), info.getTitle());
                    }
                    orgRelTypeList = new ListItems() {

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

                    setEnabled(true);
                    setListItems(orgRelTypeList);
                    /*
                     * ListItems items = orgTypeDropDown.getListItems(); if (items != null) {
                     * orgTypeDropDown.selectItem(((SingleListItem) items).getItemIds().get(0)); }
                     */
                }
            });
        }
    }

}
