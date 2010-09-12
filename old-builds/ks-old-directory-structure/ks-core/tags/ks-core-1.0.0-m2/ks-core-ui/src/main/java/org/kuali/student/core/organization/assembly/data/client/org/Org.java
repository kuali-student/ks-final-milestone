package org.kuali.student.core.organization.assembly.data.client.org;

import java.io.Serializable;
import java.util.Date;

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.core.organization.assembly.data.client.ModifiableData;
import org.kuali.student.core.organization.assembly.data.client.PropertyEnum;


public class Org extends ModifiableData implements Serializable{
	private static final long serialVersionUID = 1L;

	public enum Properties implements PropertyEnum {
		ID("id"),TYPE("orgType"),NAME("orgName"),ABBREVIATION("orgAbbr"),DESCRIPTION("orgDesc"),
				EFFECTIVE_DATE("effectiveDate"), EXPIRATION_DATE("expirationDate"),
				VERSION_CODES("versionCodes"),ORG("orgInfo");

		private final String key;

		private Properties(final String key) {
			this.key = key;
		}

		@Override
		public String getKey() {
			return this.key;
		}
	}

	public Org(){
	    super(Org.class.getName());
	}
	
	public void setId(String id){
	    super.set(Properties.ID.getKey(), id);
	}
	
    public String getId() {
        return super.get(Properties.ID.getKey());
    }
    
	public void setType(String type) {
        super.set(Properties.TYPE.getKey(), type);
    }
    
    public String getType() {
        return super.get(Properties.TYPE.getKey());
    }
    
    public Data getOrgInfo(){
        return super.get(Properties.ORG.getKey());
    }
    public void setName(String name) {
        super.set(Properties.NAME.getKey(), name);
    }
    
    public String getName() {
        return super.get(Properties.NAME.getKey());
    }
    
    public void setAbbreviation(String abbr) {
        super.set(Properties.ABBREVIATION.getKey(), abbr);
    }
    
    public String getAbbreviation() {
        return super.get(Properties.ABBREVIATION.getKey());
    }
    
    public void setDescription(String description) {
        super.set(Properties.DESCRIPTION.getKey(), description);
    }
    
    public String getDescription() {
        return super.get(Properties.DESCRIPTION.getKey());
    }
    
    public Date getEffectiveDate() {
        return super.get(Properties.EFFECTIVE_DATE.getKey());
    }
    
    public void setEffectiveDate(Date value)  {
        super.set(Properties.EFFECTIVE_DATE.getKey(), value);
    }
    
    public Date getExpirationDate() {
        return super.get(Properties.EXPIRATION_DATE.getKey());
    }
    
    public void setExpirationDate(Date value)  {
        super.set(Properties.EXPIRATION_DATE.getKey(), value);
    }
    

	
}
