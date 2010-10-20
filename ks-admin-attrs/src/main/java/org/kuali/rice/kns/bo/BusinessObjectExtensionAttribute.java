package org.kuali.rice.kns.bo;

public interface BusinessObjectExtensionAttribute extends PersistableBusinessObject{
	public String getId();
	
	public String getName();
	
	public String getValue();
	
	public String getOwnerObjectId();
	
	public void setId(String id);
	
	public void setName(String name);
	
	public void setValue(String value);
	
	public void setOwnerObjectId(String ownerId);
}
