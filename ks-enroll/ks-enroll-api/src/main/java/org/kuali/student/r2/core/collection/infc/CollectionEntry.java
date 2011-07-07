package org.kuali.student.r2.core.collection.infc;

import java.util.Date;
import java.util.List;

public interface CollectionEntry {

	
	public String getEntryRefObjectId(); 

	public String getEntryRefObjectTypeKey();
	
	public Date getCheckInDate(); 

	public Integer getPosition();

}
