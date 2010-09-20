package org.kuali.rice.kns.bo;

import java.sql.Date;

public interface KualiTypeCode {

	public String getId();

	public String getName();

	public String getDescription();

	public String getState();

	public KualiType getType();
	
	public String getCreateId();
	
	public Date getCreateDate();
	
	public String getUpdateId();
	
	public Date getUpdateDate();
}
