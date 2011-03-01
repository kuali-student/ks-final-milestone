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

package org.kuali.student.core.organization.assembly.data.server.org;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.helper.PropertyEnum;

public class OrgPositionHelper {
    private static final long serialVersionUID = 1L;

    public enum Properties implements PropertyEnum {
        ID("id"),ORG_ID("orgId"),PERSON_RELATION_TYPE("orgPersonRelationTypeKey"),TITLE("title"),
        DESC("desc"),MIN_NUM_RELATIONS("minNumRelations"), MAX_NUM_RELATIONS("maxNumRelations");

        private final String key;

        private Properties(final String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return this.key;
        }
    }
    
    private Data data;
    
    private OrgPositionHelper(Data data){
        this.data=data;
    }

    public static OrgPositionHelper wrap (Data data)
    {
        if (data == null)
        {
             return null;
        }
        return new OrgPositionHelper(data);
    }
    
    public Data getData(){
        return data;
    }
    
    public void setId(String id){
        data.set(Properties.ID.getKey(), id);
    }
    
    public String getId() {
        return data.get(Properties.ID.getKey());
    }
    
    public void setOrgId(String orgId){
        data.set(Properties.ORG_ID.getKey(), orgId);
    }
    
    public String getOrgId() {
        return data.get(Properties.ORG_ID.getKey());
    }
    
    public void setPersonRelationType(String personRelationType){
        data.set(Properties.PERSON_RELATION_TYPE.getKey(), personRelationType);
    }
    
    public String getPersonRelationType() {
        return data.get(Properties.PERSON_RELATION_TYPE.getKey());
    }
    
    public void setTitle(String title) {
        data.set(Properties.TITLE.getKey(), title);
    }
    
    public String getTitle() {
        return data.get(Properties.TITLE.getKey());
    }
    
    public void setDesc(String desc) {
        data.set(Properties.DESC.getKey(), desc);
    }
    
    public String getDesc() {
        return data.get(Properties.DESC.getKey());
    }
    
    public Integer getMinNumRelations() {
        return Integer.parseInt((String)data.get(Properties.MIN_NUM_RELATIONS.getKey()));
    }
    
    public void setMinNumRelations(Integer value)  {
        data.set(Properties.MIN_NUM_RELATIONS.getKey(), value);
    }
    
    public String getMaxNumRelations() {
        return data.get(Properties.MAX_NUM_RELATIONS.getKey());
    }
    
    public void setMaxNumRelations(String value)  {
        data.set(Properties.MAX_NUM_RELATIONS.getKey(), value);
    }
    

    
}
