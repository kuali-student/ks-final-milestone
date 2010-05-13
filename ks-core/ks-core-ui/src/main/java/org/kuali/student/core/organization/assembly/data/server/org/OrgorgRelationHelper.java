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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;



public class OrgorgRelationHelper{
	private static final long serialVersionUID = 1L;
	final Logger LOG = Logger.getLogger(OrgorgRelationHelper.class);
	
	public enum Properties implements PropertyEnum {
		ID("id"),ORG_ID("orgId"),RELATED_ORG_ID("relatedOrgId"),TYPE("orgOrgRelationTypeKey"),
				EFFECTIVE_DATE("effectiveDate"), EXPIRATION_DATE("expirationDate");

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
	
	private OrgorgRelationHelper(Data data){
	    this.data=data;
	}

	public static OrgorgRelationHelper wrap (Data data)
    {
        if (data == null)
        {
             return null;
        }
        return new OrgorgRelationHelper(data);
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
    
    public void setRelatedOrgId(String relatedOrgId){
        data.set(Properties.RELATED_ORG_ID.getKey(), relatedOrgId);
    }
    
    public String getRelatedOrgId() {
        return data.get(Properties.RELATED_ORG_ID.getKey());
    }
    
	public void setOrgOrgRelationTypeKey(String orgOrgRelationTypeKey) {
	    data.set(Properties.TYPE.getKey(), orgOrgRelationTypeKey);
    }
    
    public String getOrgOrgRelationTypeKey() {
        return data.get(Properties.TYPE.getKey());
    }
    
    public Date getEffectiveDate() {
        if(data.get(Properties.EFFECTIVE_DATE.getKey()) instanceof String){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
          
                try {
                    return df.parse((String) data.get(Properties.EFFECTIVE_DATE.getKey()));
                } catch (Exception e) {
                    LOG.error(e);
                }
            
        }
        return data.get(Properties.EFFECTIVE_DATE.getKey());
    }
    
    public void setEffectiveDate(Date value)  {
        data.set(Properties.EFFECTIVE_DATE.getKey(), value);
    }
    
    public Date getExpirationDate() {
        if(data.get(Properties.EXPIRATION_DATE.getKey()) instanceof String){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return df.parse((String) data.get(Properties.EXPIRATION_DATE.getKey()));
            } catch (Exception e) {
                LOG.error(e);
            }
        }
        return data.get(Properties.EXPIRATION_DATE.getKey());
    }
    
    public void setExpirationDate(Date value)  {
        data.set(Properties.EXPIRATION_DATE.getKey(), value);
    }
    

	
}
