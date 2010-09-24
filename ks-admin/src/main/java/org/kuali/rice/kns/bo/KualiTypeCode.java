package org.kuali.rice.kns.bo;

import java.sql.Date;

public interface KualiTypeCode extends PersistableBusinessObject{

	public String getId();

	public String getName();

	public String getDescription();

	public String getState();

	public String getCreateId();
	
	public Date getCreateDate();
	
	public String getUpdateId();
	
	public Date getUpdateDate();
}
