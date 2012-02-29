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

import java.util.Date;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.helper.PropertyEnum;

public class OrgPersonHelper {
    private static final long serialVersionUID = 1L;

    public enum Properties implements PropertyEnum {
        ID("id"),PERSON_ID("personId"),PERSON_RELATION_TYPE("type"),ORG_ID("orgId"),PERSON_NAME("personName"),
                EFFECTIVE_DATE("effectiveDate"), EXPIRATION_DATE("expirationDate"), STATE("state");

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
    
    private OrgPersonHelper(Data data){
        this.data=data;
    }

    public static OrgPersonHelper wrap (Data data)
    {
        if (data == null)
        {
             return null;
        }
        return new OrgPersonHelper(data);
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
    
    public Date getEffectiveDate() {
        return data.get(Properties.EFFECTIVE_DATE.getKey());
    }
    
    public void setEffectiveDate(Date value)  {
        data.set(Properties.EFFECTIVE_DATE.getKey(), value);
    }
    
    public Date getExpirationDate() {
        return data.get(Properties.EXPIRATION_DATE.getKey());
    }
    
    public void setExpirationDate(Date value)  {
        data.set(Properties.EXPIRATION_DATE.getKey(), value);
    }
    
    public void setTypeKey(String type) {
        data.set(Properties.PERSON_RELATION_TYPE.getKey(), type);
    }
    
    public String getTypeKey() {
        return data.get(Properties.PERSON_RELATION_TYPE.getKey());
    }
    
    public void setPersonId(String personId){
        data.set(Properties.PERSON_ID.getKey(), personId);
    }
    
    public String getPersonId() {
        return data.get(Properties.PERSON_ID.getKey());
    }
    
    public void setOrgId(String orgId){
        data.set(Properties.ORG_ID.getKey(), orgId);
    }
    
    public String getOrgId() {
        return data.get(Properties.ORG_ID.getKey());
    }
    
    public String getPersonName(){
        return data.get(Properties.PERSON_NAME.getKey());
    }
    
    public void setPersonName(String personName){
        data.set(Properties.PERSON_NAME.getKey(), personName);
    }
    public void setState(String state){
        data.set(Properties.STATE.getKey(), state);
    }

    public String getState() {
        return data.get(Properties.STATE.getKey());
    }    
}
