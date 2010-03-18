package org.kuali.student.core.organization.assembly.data.client.org;

import java.util.Date;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;


public class OrgorgRelationHelper{
	private static final long serialVersionUID = 1L;

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
    

	
}
